package com.cargocn.pm.bean;

import java.math.BigDecimal;
import java.util.Date;

public class Expenses {
	private Long expensesId;

	private Long userId;

	private Long projectId;

	private Date applyDate;
	private Date applyDate2;
	private Date invoiceDate;

	private String applyContent;

	private BigDecimal invoiceAmount;

	private BigDecimal applyAmount;

	private Long expensesType;

	private ExpensesStatus expensesStatus;

	private String operateUser;

	private Date operateTime;

	private String memo;

	public static enum ExpensesStatus {
		created("新建"), pending("待确认"), confirmed("已确认");

		private final String info;

		private ExpensesStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	public Long getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(Long expensesId) {
		this.expensesId = expensesId;
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

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent == null ? null : applyContent.trim();
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Long getExpensesType() {
		return expensesType;
	}

	public void setExpensesType(Long expensesType) {
		this.expensesType = expensesType;
	}

	public ExpensesStatus getExpensesStatus() {
		return expensesStatus;
	}

	public void setExpensesStatus(ExpensesStatus expensesStatus) {
		this.expensesStatus = expensesStatus;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public Date getApplyDate2() {
		return applyDate2;
	}

	public void setApplyDate2(Date applyDate2) {
		this.applyDate2 = applyDate2;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
}