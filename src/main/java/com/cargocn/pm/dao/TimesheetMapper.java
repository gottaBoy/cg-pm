package com.cargocn.pm.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cargocn.pm.bean.Timesheet;
import com.cargocn.pm.bean.VoTimesheet;

public interface TimesheetMapper {
	int deleteByPrimaryKey(Long sheetId);

	int insert(Timesheet record);

	int insertSelective(Timesheet record);

	Timesheet selectByPrimaryKey(Long sheetId);

	int updateByPrimaryKeySelective(Timesheet record);

	int updateByPrimaryKey(Timesheet record);

	Timesheet selectByUidPidDate(Timesheet record);

	int pendingTimesheet(Map<String, Object> period);

	List<VoTimesheet> findApproveByUserId(Map<String, Object> paras);

	List<Timesheet> findAllByTsDate(Map<String, Object> paras);

	BigDecimal sumProjectCosts(Map<String, Object> paras);
}