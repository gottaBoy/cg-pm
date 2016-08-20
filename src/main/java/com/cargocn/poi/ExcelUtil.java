package com.cargocn.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtil {
	/**
	 * 获取文件的后缀名
	 * 
	 * @param filePath
	 * @return
	 * @throws ExcelException
	 */
	public static String getExtension(String filePath) throws ExcelException {
		String extension = null;
		if (!StringUtils.isEmpty(filePath)) {
			int dot = filePath.lastIndexOf('.');
			if ((dot > -1) && (dot < (filePath.length() - 1))) {
				extension = filePath.substring(dot + 1);
			}
		}
		if (StringUtils.isEmpty(extension)) {
			throw new ExcelException("获取excel文件扩展名失败");
		}
		return extension;
	}

	public static String getExcelFileName(String filePath) throws ExcelException {
		isXls(filePath);// 根据文件后缀，判断是否excel文件
		String fileName = null;
		int lastIndex = filePath.lastIndexOf('/');
		if ((lastIndex > -1) && (lastIndex < (filePath.length() - 1))) {
			fileName = filePath.substring(lastIndex + 1);
		}
		if (StringUtils.isEmpty(fileName)) {
			throw new ExcelException("获取excel文件名失败");
		}
		return fileName;
	}

	/**
	 * 2003 返回true
	 * 
	 * @param excelFilePath
	 * @return true：2003；false：2007/2010
	 * @throws ExcelException
	 */
	public static Boolean isXls(String excelFilePath) throws ExcelException {
		String extension = getExtension(excelFilePath).toLowerCase();
		if ("xls".equals(extension)) {// 2003，后缀xls；
			return true;
		} else if ("xlsx".equals(extension)) {// 2007、2010后缀xlsx
			return false;
		} else {
			throw new ExcelException("不是excel文件，文件全路径：" + excelFilePath);
		}
	}

	/**
	 * 
	 * @param fromExcelPath
	 *            excel文件的全路径
	 * @return 返回Workbook对象（每个excel文件，被Apache POI定义为Workbook对象）
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ExcelException
	 */
	public static Workbook getWorkbook(String fromExcelPath) throws ExcelException {
		Workbook workbook = null;
		try {
			if (isXls(fromExcelPath)) {
				workbook = new HSSFWorkbook(new FileInputStream(fromExcelPath));
			} else {
				workbook = new XSSFWorkbook(new FileInputStream(fromExcelPath));
			}
		} catch (FileNotFoundException e) {
			throw new ExcelException("找不到指定的excel文件", e);
		} catch (IOException e) {
			throw new ExcelException("加载excel时，文件读写错误", e);
		}
		return workbook;
	}

	/**
	 * 创建excel
	 * 
	 * @param fromExcelPath
	 *            全路径（包括文件名后缀）
	 * @return
	 * @throws ExcelException
	 */
	public static Workbook createWorkbook(String fromExcelPath) throws ExcelException {
		File file = new File(fromExcelPath);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs(); // 创建目录
		}
		Workbook workbook = null;
		if (isXls(fromExcelPath)) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
		return workbook;
	}

	/**
	 * 按顺序获取所有sheet name
	 * 
	 * @param workbook
	 * @return 按顺序获取所有sheet name
	 */
	public static List<String> getSheetNames(Workbook workbook) {
		int size = workbook.getNumberOfSheets();
		List<String> sheetNames = new ArrayList<String>(size);

		for (int i = 0; i < size; i++) {
			sheetNames.add(workbook.getSheetName(i));
		}

		return sheetNames;
	}

	/**
	 * 按顺序获取所有sheet name
	 * 
	 * @param workbook
	 * @return 按顺序获取所有sheet name
	 */
	public static List<String> getSheetNames(Workbook workbook, String name) {
		int size = workbook.getNumberOfSheets();
		List<String> sheetNames = new ArrayList<String>(size);

		for (int i = 0; i < size; i++) {
			if (workbook.getSheetName(i).matches(name + "(_\\d*)?$")) {
				sheetNames.add(workbook.getSheetName(i));
			}
		}

		return sheetNames;
	}

	/**
	 * 根据给定的sheet name，查找在workbook中的位置
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws ExcelException
	 */
	public static int getSheetIndexByName(Workbook workbook, String sheetName) throws ExcelException {
		int index = workbook.getNameIndex(sheetName);
		if (index == -1) {
			throw new ExcelException("导入的excel文件没有sheet name：" + sheetName);
		}
		return index;
	}

	public static Workbook getWorkbook(File excelFile) throws ExcelException {
		return getWorkbook(excelFile.getPath());
	}

	public static Workbook getWorkbook(File file, String extension) throws ExcelException {
		Workbook workbook = null;
		try {
			if (extension.equals("xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(file));

			} else {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
		} catch (FileNotFoundException e) {
			throw new ExcelException("找不到指定的excel文件", e);
		} catch (IOException e) {
			throw new ExcelException("加载excel时，文件读写错误", e);
		}
		return workbook;
	}

	public static List<Sheet> getSheets(Workbook fromWorkbook, String sheetName) {
		List<String> sheetNames = getSheetNames(fromWorkbook, sheetName);
		if (CollectionUtils.isEmpty(sheetNames)) {
			return null;
		}
		List<Sheet> sheets = new ArrayList<Sheet>();
		for (String name : sheetNames) {
			sheets.add(fromWorkbook.getSheet(name));
		}
		return sheets;
	}

	public static Workbook createWorkbook(File file) throws ExcelException {
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs(); // 创建目录
		}
		Workbook workbook = null;
		if (isXls(file.getPath())) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
		return workbook;
	}

	/**
	 * 填充大量数据到excel时，使用SXSSFWorkbook，工具默认每2000行数据，刷新数据+格式到硬盘上，避免内存泄露
	 * 
	 * @param file
	 * @return
	 * @throws ExcelException
	 */
	public static Workbook createSXSSFWorkbook(File file) throws ExcelException {
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs(); // 创建目录
		}
		Workbook workbook = null;
		if (isXls(file.getPath())) {
			throw new ExcelException("xls格式不支持海量数据存储");
		} else {
			workbook = new SXSSFWorkbook(2000);
		}
		return workbook;
	}

	public static Workbook createSXSSFWorkbook(String path) throws ExcelException {
		return createSXSSFWorkbook(new File(path));
	}

	/**
	 * 根据给定的sheet name，查找在workbook中的位置
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws ExcelException
	 */
	public static List<Integer> getSheetIndexsByName(Workbook workbook, String sheetName) throws ExcelException {
		int size = workbook.getNumberOfSheets();
		List<Integer> sheetIndexs = new ArrayList<Integer>();
		StringBuffer regex = null;
		if ("Sheet".equals(sheetName)) {
			regex = new StringBuffer(sheetName);
			regex.append("(\\d*)?$");
		} else {
			for (String value : sheetName.split("_")) {
				if (regex == null) {
					regex = new StringBuffer("\\S*" + value);
				} else {
					regex.append("_\\S*").append(value);
				}
			}
			regex.append("(_\\d*)?$");
		}

		for (int i = 0; i < size; i++) {
			if (workbook.getSheetName(i).matches(regex.toString())) {
				sheetIndexs.add(i);
			}
		}
		if (CollectionUtils.isEmpty(sheetIndexs)) {
			throw new ExcelException("找不到指定的sheet name：" + sheetName);
		}
		return sheetIndexs;
	}

	/**
	 * 根据给定的sheet name，查找在workbook中的位置
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws ExcelException
	 */
	public static List<Integer> getSheetIndexs(Workbook workbook) {
		int size = workbook.getNumberOfSheets();
		List<Integer> sheetIndexs = new ArrayList<Integer>(size);

		for (int i = 0; i < size; i++) {
			sheetIndexs.add(i);
		}
		return sheetIndexs;
	}

	/**
	 * 筛选excel的sheet，删除不需要的sheet
	 * 
	 * @param workbook
	 * @param existSheetIndexs
	 *            存在的sheet index List
	 */
	@SuppressWarnings("unchecked")
	public static void copySheet(Workbook workbook, List<Integer> existSheetIndexs) {
		List<Integer> removeSheetIndexs = (List<Integer>) org.apache.commons.collections.CollectionUtils
				.subtract(ExcelUtil.getSheetIndexs(workbook), existSheetIndexs);
		int count = 0;
		for (int index : removeSheetIndexs) {
			workbook.removeSheetAt(index - (count++));
		}
	}

}