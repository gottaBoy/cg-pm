package com.cargocn.pm.bean;

public class VoTimesheet extends Timesheet {
	private String realname;
	private String projectName;
	private String tsStatusName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
 
	public String getTsStatusName() {
		return getTsStatus() == null ? null : getTsStatus().getInfo();
	}

	public void setTsStatusName(String tsStatusName) {
		this.tsStatusName = tsStatusName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}
