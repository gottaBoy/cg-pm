package com.cargocn.pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataTablesRequest {
	private int sEcho; // 请求服务器端次数
	private int iDisplayStart;// 其实记录，第一条为0
	private int iDisplayLength;
	private int iColumns;
	private String sSearch;
	private List<String> mDataProps; // 列的Name列表
	private List<Boolean> bSortables;// 列对应是否能排序
	private int iSortingCols;
	private List<Integer> iSortCols; // 排序列的编号
	private List<String> iSortColsName; // 排序列的名称
	private List<String> sSortDirs; // 排布列排序形式 Asc/Desc

	public static DataTablesRequest getInstance(String dtParaJson) {
		if (dtParaJson == null || "".equals(dtParaJson.trim())) {
			return new DataTablesRequest(1, 0, 10, 10, "", new ArrayList<String>(), new ArrayList<Boolean>(), 1,
					new ArrayList<Integer>(), new ArrayList<String>(), new ArrayList<String>());
		}
		return getDataTableParameterByJsonParam(dtParaJson);
	}

	public int getPageNum() {
		if (iDisplayLength <= 0)
			return 0;
		return (iDisplayStart / iDisplayLength) + 1;
	}

	public DataTablesRequest(int sEcho, int iDisplayStart, int iDisplayLength, int iColumns, String sSearch,
			List<String> mDataProps, List<Boolean> bSortables, int iSortingCols, List<Integer> iSortCols,
			List<String> sSortDirs, List<String> iSortColsName) {
		this.sEcho = sEcho;
		this.iDisplayStart = iDisplayStart;
		this.iDisplayLength = iDisplayLength;
		this.iColumns = iColumns;
		this.sSearch = sSearch;
		this.mDataProps = mDataProps;
		this.bSortables = bSortables;
		this.iSortingCols = iSortingCols;
		this.iSortCols = iSortCols;
		this.iSortColsName = iSortColsName;
		this.sSortDirs = sSortDirs;
	}

	protected static Map<String, Object> covertJsonStringToHashMap(String jsonParam) {
		JSONArray jsonArray = JSONArray.parseArray(jsonParam);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			map.put(jsonObj.getString("name"), jsonObj.get("value"));
		}
		return map;
	}

	protected static DataTablesRequest getDataTableParameterByJsonParam(String jsonParam) {
		Map<String, Object> map = covertJsonStringToHashMap(jsonParam);
		int sEcho = (int) map.get("sEcho");
		int iDisplayStart = (int) map.get("iDisplayStart");
		int iDisplayLength = (int) map.get("iDisplayLength");
		int iColumns = (int) map.get("iColumns");
		int iSortingCols = 0;
		if (map.containsKey("iSortingCols")) {
			iSortingCols = (int) map.get("iSortingCols");
		}
		String sSearch = (String) map.get("sSearch");

		List<String> mDataProps = new ArrayList<String>();
		List<Boolean> bSortables = new ArrayList<Boolean>();
		for (int i = 0; i < iColumns; i++) {
			String dataProp = (String) map.get("mDataProp_" + i);
			Boolean sortable = (Boolean) map.get("bSortable_" + i);
			mDataProps.add(dataProp);
			bSortables.add(sortable);
		}

		List<Integer> iSortCols = new ArrayList<Integer>();
		List<String> sSortDirs = new ArrayList<String>();
		List<String> iSortColsName = new ArrayList<String>();
		for (int i = 0; i < iSortingCols; i++) {
			Integer sortCol = (Integer) map.get("iSortCol_" + i);
			String sortColName = mDataProps.get(sortCol);
			String sortDir = (String) map.get("sSortDir_" + i);
			iSortCols.add(sortCol);
			sSortDirs.add(sortDir);
			iSortColsName.add(sortColName);
		}

		return new DataTablesRequest(sEcho, iDisplayStart, iDisplayLength, iColumns, sSearch, mDataProps, bSortables,
				iSortingCols, iSortCols, sSortDirs, iSortColsName);
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiColumns() {
		return iColumns;
	}

	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}

	public List<String> getmDataProps() {
		return mDataProps;
	}

	public void setmDataProps(List<String> mDataProps) {
		this.mDataProps = mDataProps;
	}

	public List<Boolean> getbSortables() {
		return bSortables;
	}

	public void setbSortables(List<Boolean> bSortables) {
		this.bSortables = bSortables;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public List<Integer> getiSortCols() {
		return iSortCols;
	}

	public void setiSortCols(List<Integer> iSortCols) {
		this.iSortCols = iSortCols;
	}

	public List<String> getiSortColsName() {
		return iSortColsName;
	}

	public void setiSortColsName(List<String> iSortColsName) {
		this.iSortColsName = iSortColsName;
	}

	public List<String> getsSortDirs() {
		return sSortDirs;
	}

	public void setsSortDirs(List<String> sSortDirs) {
		this.sSortDirs = sSortDirs;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

}
