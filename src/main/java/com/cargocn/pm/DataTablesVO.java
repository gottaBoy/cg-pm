package com.cargocn.pm;

import java.util.List;

/**
 * This VO used to generate the JSON data for data table, so please ensure that
 * the attribute name is correct. If you want to define the fields name by
 * yourself, please visit: https://datatables.net
 * 
 * @author aimee
 *
 * @param <T>
 */
public class DataTablesVO<T> {
	private int draw; // Client request times
	private int recordsTotal; // Total records number without conditions
	private int recordsFiltered; // Total records number with conditions
	private List<T> data; // The data we should display on the page
	// getter and setter method

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
