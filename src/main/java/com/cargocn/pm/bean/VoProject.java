package com.cargocn.pm.bean;

import java.math.BigDecimal;
import java.util.List;

public class VoProject extends Project {
	private List<Long> userIds;

	private String realname;

	private BigDecimal totalExpenses;
	private BigDecimal confirmedExpenses;
	private BigDecimal pendingExpenses;
	private BigDecimal createdExpenses;
	private BigDecimal totalCosts;
	private BigDecimal confirmedCosts;
	private BigDecimal pendingCosts;
	private BigDecimal createdCosts;

	private BigDecimal totalAll;
	private BigDecimal totalConfirmed;
	private BigDecimal totalPending;
	private BigDecimal totalCreated;

	public void setTotal() {
		totalExpenses = new BigDecimal(0);
		totalCosts = new BigDecimal(0);
		totalConfirmed = new BigDecimal(0);
		totalPending = new BigDecimal(0);
		totalCreated = new BigDecimal(0);
		totalAll = new BigDecimal(0);
		if (confirmedExpenses == null) {
			confirmedExpenses = new BigDecimal(0);
		}
		totalExpenses = totalExpenses.add(confirmedExpenses);
		totalConfirmed = totalConfirmed.add(confirmedExpenses);
		if (pendingExpenses == null) {
			pendingExpenses = new BigDecimal(0);
		}
		totalExpenses = totalExpenses.add(pendingExpenses);
		totalPending = totalPending.add(pendingExpenses);
		if (createdExpenses == null) {
			createdExpenses = new BigDecimal(0);
		}
		totalExpenses = totalExpenses.add(createdExpenses);
		totalCreated = totalCreated.add(createdExpenses);
		if (confirmedCosts == null) {
			confirmedCosts = new BigDecimal(0);
		}
		totalCosts = totalCosts.add(confirmedCosts);
		totalConfirmed = totalConfirmed.add(confirmedCosts);
		if (pendingCosts == null) {
			pendingCosts = new BigDecimal(0);
		}
		totalCosts = totalCosts.add(pendingCosts);
		totalPending = totalPending.add(pendingCosts);
		if (createdCosts == null) {
			createdCosts = new BigDecimal(0);
		}
		totalCosts = totalCosts.add(createdCosts);
		totalCreated = totalCreated.add(createdCosts);
		totalAll = totalAll.add(totalExpenses);
		totalAll = totalAll.add(totalCosts);

	}

	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public BigDecimal getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(BigDecimal totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public BigDecimal getConfirmedExpenses() {
		return confirmedExpenses;
	}

	public void setConfirmedExpenses(BigDecimal confirmedExpenses) {
		this.confirmedExpenses = confirmedExpenses;
	}

	public BigDecimal getPendingExpenses() {
		return pendingExpenses;
	}

	public void setPendingExpenses(BigDecimal pendingExpenses) {
		this.pendingExpenses = pendingExpenses;
	}

	public BigDecimal getCreatedExpenses() {
		return createdExpenses;
	}

	public void setCreatedExpenses(BigDecimal createdExpenses) {
		this.createdExpenses = createdExpenses;
	}

	public BigDecimal getTotalCosts() {
		return totalCosts;
	}

	public void setTotalCosts(BigDecimal totalCosts) {
		this.totalCosts = totalCosts;
	}

	public BigDecimal getConfirmedCosts() {
		return confirmedCosts;
	}

	public void setConfirmedCosts(BigDecimal confirmedCosts) {
		this.confirmedCosts = confirmedCosts;
	}

	public BigDecimal getPendingCosts() {
		return pendingCosts;
	}

	public void setPendingCosts(BigDecimal pendingCosts) {
		this.pendingCosts = pendingCosts;
	}

	public BigDecimal getCreatedCosts() {
		return createdCosts;
	}

	public void setCreatedCosts(BigDecimal createdCosts) {
		this.createdCosts = createdCosts;
	}

	public BigDecimal getTotalAll() {
		return totalAll;
	}

	public void setTotalAll(BigDecimal totalAll) {
		this.totalAll = totalAll;
	}

	public BigDecimal getTotalConfirmed() {
		return totalConfirmed;
	}

	public void setTotalConfirmed(BigDecimal totalConfirmed) {
		this.totalConfirmed = totalConfirmed;
	}

	public BigDecimal getTotalPending() {
		return totalPending;
	}

	public void setTotalPending(BigDecimal totalPending) {
		this.totalPending = totalPending;
	}

	public BigDecimal getTotalCreated() {
		return totalCreated;
	}

	public void setTotalCreated(BigDecimal totalCreated) {
		this.totalCreated = totalCreated;
	}
}
