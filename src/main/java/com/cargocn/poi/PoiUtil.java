package com.cargocn.poi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

/**
 * POI工具类
 * 
 * @author Yuri
 * 
 */
public class PoiUtil {
	/**
	 * 由于Excel当中的单元格Cell存在类型,若获取类型错误就会产生异常, 所以通过此方法将Cell内容全部转换为String类型
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String str = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			str = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			str = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			str = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			str = String.valueOf((long) cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			str = String.valueOf(cell.getStringCellValue());
			break;
		default:
			str = null;
			break;
		}
		return str;
	}

	/**
	 * 对外提供的工具方法，功能：不同的excel间的sheet复制，包含数据、公式、样式、批注。<br/>
	 * 未实现功能：复制数据有效性DataValidation
	 * 
	 * @param fromExcel
	 *            源文件（模板）
	 * @param fromSheetName
	 *            源sheet name
	 * @param toWorkbook
	 *            目标workbook
	 * @param toSheetName
	 *            目标sheet的名称。null时，取源sheet name
	 * @throws ExcelException
	 */
	public static void copySheet(String fromExcel, String fromSheetName, Workbook toWorkbook, String toSheetName)
			throws ExcelException {
		if (StringUtils.isEmpty(fromSheetName)) {
			throw new ExcelException("源文件sheet name不能为空");
		}
		if (StringUtils.isEmpty(toSheetName)) {
			copySheet(ExcelUtil.getWorkbook(fromExcel).getSheet(fromSheetName), toWorkbook.createSheet(fromExcel));
		} else {
			copySheet(ExcelUtil.getWorkbook(fromExcel).getSheet(fromSheetName), toWorkbook.createSheet(toSheetName));
		}
	}

	public static void copySheet(Sheet from, Sheet to) {
		int maxColumnNum = 0;// 最大列数
		Drawing drawing = to.createDrawingPatriarch();// 批注
		Class<? extends Workbook> clazz = to.getWorkbook().getClass();// 创建批注时，需要用到的变量

		for (int i = from.getFirstRowNum(); i <= from.getLastRowNum(); i++) {
			Row fromRow = from.getRow(i);
			Row toRow = to.createRow(i);
			if (fromRow != null) {
				copyRow(fromRow, toRow, drawing, clazz);// 复制数据
				if (fromRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = fromRow.getLastCellNum();
				}
			}
		}
		// 设置单元格的宽度
		for (int i = 0; i <= maxColumnNum; i++) {
			to.setColumnWidth(i, from.getColumnWidth(i));
		}
		copySheetStyle(from, to);// 复制style
	}

	private static void copySheetStyle(Sheet from, Sheet to) {
		for (int i = 0; i < from.getNumMergedRegions(); i++) {
			CellRangeAddress merged = from.getMergedRegion(i);
			to.addMergedRegion(merged);
		}
	}

	private static void copyRow(Row fromRow, Row toRow, Drawing drawing, Class<? extends Workbook> clazz) {
		toRow.setHeight(fromRow.getHeight());
		for (int j = fromRow.getFirstCellNum(); j <= fromRow.getLastCellNum(); j++) {
			Cell fromCell = fromRow.getCell(j);
			Cell toCell = toRow.getCell(j);
			if (fromCell != null) {
				if (toCell == null) {
					toCell = toRow.createCell(j);
				}
				copyCell(fromCell, toCell, drawing, clazz);
			}
		}
	}

	/**
	 * @param to
	 * @param drawing
	 * @param fromCell
	 * @param toCell
	 */
	private static void copyComment(Class<? extends Workbook> clazz, Comment fromComment, Drawing drawing,
			Cell fromCell, Cell toCell) {
		Comment newComment = drawing.createCellComment(createClientAnchor(clazz));
		newComment.setAuthor(fromComment.getAuthor());
		newComment.setColumn(fromComment.getColumn());
		newComment.setRow(fromComment.getRow());
		newComment.setString(fromComment.getString());
		newComment.setVisible(fromComment.isVisible());
		toCell.setCellComment(newComment);
	}

	private static ClientAnchor createClientAnchor(Class<? extends Workbook> clazz) {
		ClientAnchor aClientAnchor = null;
		switch (clazz.getSimpleName()) {
		case "HSSFWorkbook":
			aClientAnchor = new HSSFClientAnchor();
			break;
		default:
			aClientAnchor = new XSSFClientAnchor();
			break;
		}
		return aClientAnchor;
	}

	/**
	 * @param fromCell
	 * @param toCell
	 * @param styleMap
	 */
	private static void copyCell(Cell fromCell, Cell toCell, Drawing drawing, Class<? extends Workbook> clazz) {
		if (fromCell.getSheet().getWorkbook() == toCell.getSheet().getWorkbook()) {// 同一个excel可以直接复制cellStyle
			toCell.setCellStyle(fromCell.getCellStyle());
		} else {
			// 不同的excel之间，需要调用cloneStyleFrom方法
			CellStyle newCellStyle = toCell.getSheet().getWorkbook().createCellStyle();
			newCellStyle.cloneStyleFrom(fromCell.getCellStyle());
			toCell.setCellStyle(newCellStyle);
		}
		copyCellValue(fromCell, toCell);
		Comment fromComment = fromCell.getCellComment();
		if (fromComment != null) {
			copyComment(clazz, fromComment, drawing, fromCell, toCell);
		}
	}

	/**
	 * @param from
	 * @param to
	 */
	public static void copyCellValue(Cell from, Cell to) {
		switch (from.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			to.setCellValue(from.getStringCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			to.setCellValue(from.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			to.setCellType(Cell.CELL_TYPE_BLANK);
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			to.setCellValue(from.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			to.setCellErrorValue(from.getErrorCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			to.setCellFormula(from.getCellFormula());
			break;
		default:
			break;
		}
	}
}