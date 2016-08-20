package com.cargocn.pm;

import javax.servlet.http.HttpServletRequest;

/**
 * DataTables 翻页请求参数封装类
 * 
 * @author aimee
 *
 */
public class DataTablesPagerHelper {
	private int startRecord = 0;
	private int pageSize = 10;
	private String sortOrder;
	private String sortDir;
	private String searchValue;
	private int draw;

	public DataTablesPagerHelper(HttpServletRequest request) {
		String size = request.getParameter("length");
		if (!"".equals(size) && size != null) {
			pageSize = Integer.parseInt(size);
		}
		String currentRecord = request.getParameter("start");
		if (!"".equals(currentRecord) && currentRecord != null) {
			startRecord = Integer.parseInt(currentRecord);
		}
		// For sortable
		String sortOrder = request.getParameter("order[0][column]");
		String sortDir = request.getParameter("order[0][dir]");
		System.out.println("sortOrder: " + sortOrder);
		System.out.println("sortDir: " + sortDir);

		// For search
		String searchValue = request.getParameter("search[value]");
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

}
