package com.cargocn.pm.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Timesheet {
	private Long sheetId;

	private Long userId;

	private Long projectId;

	private Date tsDate;

	private BigDecimal tsHour;

	private TimesheetStatus tsStatus;

	private BigDecimal tsCost;

	private String operateUser;

	private Date operateTime;

	public static enum TimesheetStatus {
		created("新建"), pending("待确认"), confirmed("已确认");

		private final String info;

		private TimesheetStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Date getTsDate() {
		return tsDate;
	}

	public void setTsDate(Date tsDate) {
		this.tsDate = tsDate;
	}

	public BigDecimal getTsHour() {
		return tsHour;
	}

	public void setTsHour(BigDecimal tsHour) {
		this.tsHour = tsHour;
	}

	public TimesheetStatus getTsStatus() {
		return tsStatus;
	}

	public void setTsStatus(TimesheetStatus tsStatus) {
		this.tsStatus = tsStatus;
	}

	public BigDecimal getTsCost() {
		return tsCost;
	}

	public void setTsCost(BigDecimal tsCost) {
		this.tsCost = tsCost;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser == null ? null : operateUser.trim();
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}