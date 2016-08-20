package com.cargocn.pm.bean;

public class VoExpenses extends Expenses {
	private String realname;
	private String projectName;
	private String expensesTypeName;
	private String expensesStatusName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getExpensesTypeName() {
		return expensesTypeName;
	}

	public void setExpensesTypeName(String expensesTypeName) {
		this.expensesTypeName = expensesTypeName;
	}

	public String getExpensesStatusName() {
		return getExpensesStatus() == null ? null : getExpensesStatus().getInfo();
	}

	public void setExpensesStatusName(String expensesStatusName) {
		this.expensesStatusName = expensesStatusName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}
