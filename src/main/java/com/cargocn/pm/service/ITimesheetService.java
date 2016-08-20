package com.cargocn.pm.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cargocn.pm.bean.Timesheet;
import com.cargocn.pm.bean.Timesheet.TimesheetStatus;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoTimesheet;
import com.github.pagehelper.PageInfo;

public interface ITimesheetService {

	public Timesheet findOne(Long userId, Long projectId, Date tsDate);

	public void saveTimesheet(List<Timesheet> tl, User loginUser);

	public void pendingTimesheet(String tsDate, List<Timesheet> tl, User loginUser);

	public PageInfo<VoTimesheet> findTimesheetApprove(Long userId, int pageNum, int pageSize);

	public void batchApprove(String username, Long[] selectedId);

	public void batchReject(String username, Long[] selectedId);

	public int updateByPrimaryKey(Timesheet record);

	public List<Timesheet> findAllByTsDate(Long userId, Date tsDate);

	public BigDecimal sumProjectCosts(Long projectId, TimesheetStatus status);
}
