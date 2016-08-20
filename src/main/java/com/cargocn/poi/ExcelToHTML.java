package com.cargocn.poi;

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER_SELECTION;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_FILL;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_GENERAL;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_JUSTIFY;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_LEFT;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_RIGHT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASHED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DASH_DOT_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DOTTED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_DOUBLE;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_HAIR;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASHED;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_MEDIUM_DASH_DOT_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_NONE;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_SLANTED_DASH_DOT;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THICK;
import static org.apache.poi.ss.usermodel.CellStyle.BORDER_THIN;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_BOTTOM;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_CENTER;
import static org.apache.poi.ss.usermodel.CellStyle.VERTICAL_TOP;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.examples.html.HSSFHtmlHelper;
import org.apache.poi.ss.examples.html.HtmlHelper;
import org.apache.poi.ss.examples.html.XSSFHtmlHelper;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example shows how to display a spreadsheet in HTML using the classes for
 * spreadsheet display.
 * 
 * @author Ken Arnold, Industrious Media LLC
 */
public class ExcelToHTML {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Workbook wb;
	private final Appendable output;
	private boolean completeHTML;
	private Formatter out;
	private boolean gotBounds;
	private int firstColumn;
	private int endColumn;
	private HtmlHelper helper;

	private static final String DEFAULTS_CLASS = "excelDefaults";
	private static final String COL_HEAD_CLASS = "colHeader";
	private static final String ROW_HEAD_CLASS = "rowHeader";
	private static final String HTML_SPACE = "&nbsp;";

	private static final Map<Short, String> ALIGN = mapFor(ALIGN_LEFT, "left", ALIGN_CENTER, "center", ALIGN_RIGHT,
			"right", ALIGN_FILL, "left", ALIGN_JUSTIFY, "left", ALIGN_CENTER_SELECTION, "center");

	private static final Map<Short, String> VERTICAL_ALIGN = mapFor(VERTICAL_BOTTOM, "bottom", VERTICAL_CENTER,
			"middle", VERTICAL_TOP, "top");

	private static final Map<Short, String> BORDER = mapFor(BORDER_DASH_DOT, "dashed 1pt", BORDER_DASH_DOT_DOT,
			"dashed 1pt", BORDER_DASHED, "dashed 1pt", BORDER_DOTTED, "dotted 1pt", BORDER_DOUBLE, "double 3pt",
			BORDER_HAIR, "solid 1px", BORDER_MEDIUM, "solid 2pt", BORDER_MEDIUM_DASH_DOT, "dashed 2pt",
			BORDER_MEDIUM_DASH_DOT_DOT, "dashed 2pt", BORDER_MEDIUM_DASHED, "dashed 2pt", BORDER_NONE, "none",
			BORDER_SLANTED_DASH_DOT, "dashed 2pt", BORDER_THICK, "solid 3pt", BORDER_THIN, "dashed 1pt");

	@SuppressWarnings({ "unchecked" })
	private static <K, V> Map<K, V> mapFor(Object... mapping) {
		Map<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < mapping.length; i += 2) {
			map.put((K) mapping[i], (V) mapping[i + 1]);
		}
		return map;
	}

	/**
	 * Creates a new converter to HTML for the given workbook.
	 * 
	 * @param wb
	 *            The workbook.
	 * @param output
	 *            Where the HTML output will be written.
	 * 
	 * @return An object for converting the workbook to HTML.
	 */
	public static ExcelToHTML create(Workbook wb, Appendable output) {
		return new ExcelToHTML(wb, output);
	}

	/**
	 * Creates a new converter to HTML for the given workbook. If the path ends
	 * with "<tt>.xlsx</tt>" an {@link XSSFWorkbook} will be used; otherwise
	 * this will use an {@link HSSFWorkbook}.
	 * 
	 * @param path
	 *            The file that has the workbook.
	 * @param output
	 *            Where the HTML output will be written.
	 * 
	 * @return An object for converting the workbook to HTML.
	 */
	public static ExcelToHTML create(String path, Appendable output) throws IOException {
		return create(new FileInputStream(path), output);
	}

	/**
	 * Creates a new converter to HTML for the given workbook. This attempts to
	 * detect whether the input is XML (so it should create an
	 * {@link XSSFWorkbook} or not (so it should create an {@link HSSFWorkbook}
	 * ).
	 * 
	 * @param in
	 *            The input stream that has the workbook.
	 * @param output
	 *            Where the HTML output will be written.
	 * 
	 * @return An object for converting the workbook to HTML.
	 */
	public static ExcelToHTML create(InputStream in, Appendable output) throws IOException {
		try {
			Workbook wb = WorkbookFactory.create(in);
			return create(wb, output);
		} catch (InvalidFormatException e) {
			throw new IllegalArgumentException("Cannot create workbook from stream", e);
		}
	}

	private ExcelToHTML(Workbook wb, Appendable output) {
		if (wb == null)
			throw new NullPointerException("wb");
		if (output == null)
			throw new NullPointerException("output");
		this.wb = wb;
		this.output = output;
		setupColorMap();
	}

	private void setupColorMap() {
		if (wb instanceof HSSFWorkbook)
			helper = new HSSFHtmlHelper((HSSFWorkbook) wb);
		else if (wb instanceof XSSFWorkbook)
			helper = new XSSFHtmlHelper();
		else
			throw new IllegalArgumentException("unknown workbook type: " + wb.getClass().getSimpleName());
	}

	/**
	 * Run this class as a program
	 * 
	 * @param args
	 *            The command line arguments.
	 * 
	 * @throws Exception
	 *             Exception we don't recover from.
	 */
	public static void main(String[] args) throws Exception {
		// if (args.length < 2) {
		// System.err.println("usage: ToHtml inputWorkbook outputHtmlFile");
		// return;
		// }
		// String xlsfile = args[0];
		// String htmfile = args[1];
		String xlsfile = "d:\\a.xls";
		String htmfile = "d:\\a.html";
		ExcelToHTML toHtml = create(xlsfile, new PrintWriter(new FileWriter(htmfile)));
		toHtml.setCompleteHTML(true);
		toHtml.printPage();
	}

	/**
	 * 判断是否属于合并单元格 如果是，则返回属于合并区域的地址，如果不是则返回-1
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static int isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int isMergedRegion(Cell cell) {
		return isMergedRegion(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex());
	}

	public void setCompleteHTML(boolean completeHTML) {
		this.completeHTML = completeHTML;
	}

	public void printPage() throws IOException {
		try {
			ensureOut();
			if (completeHTML) {
				// out.format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>%n");
				out.format("<html>%n");
				out.format("<head>%n");
				out.format("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />%n");
				out.format("</head>%n");
				out.format("<body>%n");
			}

			print();

			if (completeHTML) {
				out.format("</body>%n");
				out.format("</html>%n");
			}
		} finally {
			if (out != null)
				out.close();
			if (output instanceof Closeable) {
				Closeable closeable = (Closeable) output;
				closeable.close();
			}
		}
	}

	public void print() {
		printInlineStyle();
		printSheets();
	}

	private void printInlineStyle() {
		// out.format("<link href=\"excelStyle.css\" rel=\"stylesheet\"
		// type=\"text/css\">%n");
		out.format("<style type=\"text/css\">%n");
		printStyles();
		out.format("</style>%n");
	}

	private void ensureOut() {
		if (out == null)
			out = new Formatter(output);
	}

	public void printStyles() {
		ensureOut();

		// First, copy the base css
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(helper.getClass().getResourceAsStream("excelStyle.css")));
			String line;
			while ((line = in.readLine()) != null) {
				out.format("%s%n", line);
			}
		} catch (IOException e) {
			throw new IllegalStateException("Reading standard css", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// noinspection ThrowFromFinallyBlock
					throw new IllegalStateException("Reading standard css", e);
				}
			}
		}

		// now add css for each used style
		Set<CellStyle> seen = new HashSet<CellStyle>();
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				for (Cell cell : row) {
					CellStyle style = cell.getCellStyle();
					if (!seen.contains(style)) {
						printStyle(style);
						seen.add(style);
					}
				}
			}
		}
	}

	private void printStyle(CellStyle style) {
		out.format(".%s .%s {%n", DEFAULTS_CLASS, styleName(style));
		styleContents(style);
		out.format("}%n");
	}

	private void styleContents(CellStyle style) {
		styleOut("text-align", style.getAlignment(), ALIGN);
		styleOut("vertical-align", style.getAlignment(), VERTICAL_ALIGN);
		fontStyle(style);
		borderStyles(style);
		helper.colorStyles(style, out);
	}

	private void borderStyles(CellStyle style) {
		styleOut("border-left", style.getBorderLeft(), BORDER);
		styleOut("border-right", style.getBorderRight(), BORDER);
		styleOut("border-top", style.getBorderTop(), BORDER);
		styleOut("border-bottom", style.getBorderBottom(), BORDER);
	}

	private void fontStyle(CellStyle style) {
		Font font = wb.getFontAt(style.getFontIndex());

		if (font.getBoldweight() >= HSSFFont.BOLDWEIGHT_NORMAL)
			out.format("  font-weight: bold;%n");
		if (font.getItalic())
			out.format("  font-style: italic;%n");

		int fontheight = font.getFontHeightInPoints();
		if (fontheight == 9) {
			// fix for stupid ol Windows
			fontheight = 10;
		}
		out.format("  font-size: %dpt;%n", fontheight);

		// Font color is handled with the other colors
	}

	@SuppressWarnings("resource")
	private String styleName(CellStyle style) {
		if (style == null)
			style = wb.getCellStyleAt((short) 0);
		StringBuilder sb = new StringBuilder();
		Formatter fmt = new Formatter(sb);
		fmt.format("style_%02x", style.getIndex());
		return fmt.toString();
	}

	private <K> void styleOut(String attr, K key, Map<K, String> mapping) {
		String value = mapping.get(key);
		if (value != null) {
			out.format("  %s: %s;%n", attr, value);
		}
	}

	private static int ultimateCellType(Cell c) {
		int type = c.getCellType();
		if (type == Cell.CELL_TYPE_FORMULA)
			type = c.getCachedFormulaResultType();
		return type;
	}

	private void printSheets() {
		ensureOut();
		int ss = wb.getNumberOfSheets();
		for (int i = 0; i < ss; i++) {
			Sheet sheet = wb.getSheetAt(i);
			logger.debug("print sheet " + i);
			gotBounds = false;
			printSheet(sheet);
		}
	}

	public void printSheet(Sheet sheet) {
		ensureOut();
		out.format("<table class=%s>%n", DEFAULTS_CLASS);
		printCols(sheet);
		printSheetContent(sheet);
		out.format("</table>%n");
	}

	private void printCols(Sheet sheet) {
		out.format("<col/>%n");
		ensureColumnBounds(sheet);
		for (int i = firstColumn; i < endColumn; i++) {
			out.format("<col/>%n");
		}
	}

	private void ensureColumnBounds(Sheet sheet) {
		if (gotBounds)
			return;
		logger.debug(sheet.getSheetName());
		Iterator<Row> iter = sheet.rowIterator();
		firstColumn = (iter.hasNext() ? Integer.MAX_VALUE : 0);
		endColumn = 0;
		while (iter.hasNext()) {
			Row row = iter.next();
			short firstCell = row.getFirstCellNum();

			if (firstCell >= 0) {
				firstColumn = Math.min(firstColumn, firstCell);
				endColumn = Math.max(endColumn, row.getLastCellNum());
			}
		}
		gotBounds = true;
	}

	private void printColumnHeads() {
		out.format("<thead>%n");
		out.format("  <tr class=%s>%n", COL_HEAD_CLASS);
		out.format("    <th class=%s>&#x25CA;</th>%n", COL_HEAD_CLASS);
		// noinspection UnusedDeclaration
		StringBuilder colName = new StringBuilder();
		for (int i = firstColumn; i < endColumn; i++) {
			colName.setLength(0);
			int cnum = i;
			do {
				colName.insert(0, (char) ('A' + cnum % 26));
				cnum /= 26;
			} while (cnum > 0);
			out.format("    <th class=%s>%s</th>%n", COL_HEAD_CLASS, colName);
		}
		out.format("  </tr>%n");
		out.format("</thead>%n");
	}

	private void printSheetContent(Sheet sheet) {
		printColumnHeads();
		List<String> mergedIndexs = new ArrayList<String>();
		out.format("<tbody>%n");
		Iterator<Row> rows = sheet.rowIterator();
		logger.debug(sheet.getSheetName());
		while (rows.hasNext()) {
			Row row = rows.next();
			out.format("  <tr>%n");
			out.format("    <td class=%s>%d</td>%n", ROW_HEAD_CLASS, row.getRowNum() + 1);
			for (int i = firstColumn; i < endColumn; i++) {
				String content = HTML_SPACE;
				String attrs = "";
				CellStyle style = null;
				boolean isBeginMergin = false;
				int mi = -1;
				if (i >= row.getFirstCellNum() && i < row.getLastCellNum()) {
					Cell cell = row.getCell(i);
					if (cell != null) {
						mi = isMergedRegion(cell);
						if (mi >= 0) {
							// 属于合并单元格
							if (mergedIndexs.contains(String.valueOf(mi))) {
								// 已经处理过合并
							} else {
								// 合并开始位置
								isBeginMergin = true;
								mergedIndexs.add(String.valueOf(mi));
							}
						}
						style = cell.getCellStyle();
						attrs = tagStyle(cell, style);
						// Set the value that is rendered for the cell
						// also applies the format
						content = getCellContent(cell, style);
					}
				}
				// logger.debug("row " + ri + " " + i + ":" + content);
				if (isBeginMergin) {
					CellRangeAddress ca = sheet.getMergedRegion(mi);
					int firstColumn = ca.getFirstColumn();
					int lastColumn = ca.getLastColumn();
					int firstRow = ca.getFirstRow();
					int lastRow = ca.getLastRow();
					int colspan = lastColumn - firstColumn + 1;
					int rowspan = lastRow - firstRow + 1;
					out.format("    <td rowspan=" + rowspan + " colspan=" + colspan + " class=%s %s>%s</td>%n",
							styleName(style), attrs, content);
				} else {
					if (mi >= 0) {
						// 属于合并区域，单不属于合并开始位置
					} else {
						// 不属于合并区域
						// 如果当前单元格内容非空判断后续是否空格，自动做合并处理

						int colspan = 1;
						if (content != null && !HTML_SPACE.equals(content)) {
							boolean forEnd = false;
							int ni = i + 1;
							for (; ni < endColumn && ni < row.getLastCellNum() && !forEnd; ni++) {
								if (ni >= row.getFirstCellNum() && ni < row.getLastCellNum()) {
									Cell cell = row.getCell(ni);
									if (cell != null) {
										String ncontent = getCellContent(cell);
										if (ncontent != null && !HTML_SPACE.equals(ncontent)) {
											forEnd = true;
										}
									}
								}
							}
							if (forEnd) {
								// 发现非空单元格位置
								colspan = ni - i - 1;
							} else {
								// 未发现非空单元格--后续均为空单元格
								colspan = endColumn - i;
							}
						}
						if (colspan > 1) {
							if (attrs == null || "".equals(attrs)) {
								attrs = "style=\"text-align: left;\"";
							}
							out.format("    <td colspan=" + colspan + " class=%s %s>%s</td>%n", styleName(style), attrs,
									content);
							i += colspan - 1;
						} else {
							out.format("    <td class=%s %s>%s</td>%n", styleName(style), attrs, content);
						}
					}
				}
			}
			out.format("  </tr>%n");
		}
		out.format("</tbody>%n");
	}

	private String getCellContent(Cell cell, CellStyle style) {
		try {
			CellFormat cf = (style == null || style.getDataFormatString() == null) ? CellFormat.GENERAL_FORMAT
					: CellFormat.getInstance(style.getDataFormatString());
			CellFormatResult result = cf.apply(cell);
			String content = result.text;
			if (content == null || "".equals(content))
				content = HTML_SPACE;
			return content;
		} catch (Exception e) {
			return HTML_SPACE;
		}
	}

	private String getCellContent(Cell cell) {
		if (cell == null) {
			return HTML_SPACE;
		}
		CellStyle style = cell.getCellStyle();
		return getCellContent(cell, style);
	}

	private String tagStyle(Cell cell, CellStyle style) {
		if (style.getAlignment() == ALIGN_GENERAL) {
			switch (ultimateCellType(cell)) {
			case HSSFCell.CELL_TYPE_STRING:
				return "style=\"text-align: left;\"";
			case HSSFCell.CELL_TYPE_BOOLEAN:
			case HSSFCell.CELL_TYPE_ERROR:
				return "style=\"text-align: center;\"";
			case HSSFCell.CELL_TYPE_NUMERIC:
			default:
				// "right" is the default
				break;
			}
		}
		return "";
	}
}
