package com.cargocn.poi;

public class ExcelException extends Exception {

	private static final long serialVersionUID = 1430460211241270213L;

	public ExcelException(String string) {
		super(string);
	}

	public ExcelException(String string, Exception e) {
		super(string, e);
	}

}
