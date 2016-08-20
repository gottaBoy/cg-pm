package com.cargocn.pm.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.Timesheet;
import com.cargocn.pm.bean.Timesheet.TimesheetStatus;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoTimesheet;
import com.cargocn.pm.dao.TimesheetMapper;
import com.cargocn.pm.service.ITimesheetService;
import com.cargocn.pm.service.IUserService;
import com.cargocn.spring.StringToDateConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TimesheetServiceImpl implements ITimesheetService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TimesheetMapper timesheetDao;

	@Autowired
	private IUserService userService;

	@Override
	public Timesheet findOne(Long userId, Long projectId, Date tsDate) {
		Timesheet t = new Timesheet();
		t.setUserId(userId);
		t.setProjectId(projectId);
		t.setTsDate(tsDate);
		return timesheetDao.selectByUidPidDate(t);
	}

	@Override
	public void saveTimesheet(List<Timesheet> tl, User loginUser) {
		if (tl != null) {
			for (Timesheet t : tl) {
				if (t != null && t.getTsDate() != null) {
					Timesheet old = findOne(loginUser.getId(), t.getProjectId(), t.getTsDate());
					BigDecimal uc = userService.findUserCost(loginUser.getId(), t.getTsDate());
					if (old != null) {
						if (old.getTsStatus() == null || old.getTsStatus() == TimesheetStatus.created) {
							old.setOperateTime(Calendar.getInstance().getTime());
							old.setOperateUser(loginUser.getUsername());
							old.setTsHour(t.getTsHour());
							old.setTsStatus(TimesheetStatus.created);
							old.setTsCost(CostCalHelper.cal(uc, t.getTsHour()));
							timesheetDao.updateByPrimaryKey(old);
						}
					} else {
						t.setOperateTime(Calendar.getInstance().getTime());
						t.setOperateUser(loginUser.getUsername());
						t.setTsStatus(TimesheetStatus.created);
						t.setUserId(loginUser.getId());
						t.setTsCost(CostCalHelper.cal(uc, t.getTsHour()));
						timesheetDao.insert(t);
					}
				}
			}
		}
	}

	@Override
	public void pendingTimesheet(String tsDate, List<Timesheet> tl, User loginUser) {
		saveTimesheet(tl, loginUser);
		Date d = new StringToDateConverter().convert(tsDate);
		if (d != null) {
			Date monday = DateHelper.getWeekBegin(d);
			Calendar c = Calendar.getInstance();
			c.setTime(monday);
			c.add(Calendar.DATE, 4);
			Date friday = c.getTime();
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("beginDate", monday);
			paras.put("endDate", friday);
			paras.put("userId", loginUser.getId());
			paras.put("tsStatus", TimesheetStatus.pending);
			paras.put("tsStatusNew", TimesheetStatus.created);
			int ret = timesheetDao.pendingTimesheet(paras);
			logger.debug("pending " + ret);
		}
	}

	@Override
	public PageInfo<VoTimesheet> findTimesheetApprove(Long userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("userId", userId);
		paras.put("tsStatus", TimesheetStatus.pending);
		List<VoTimesheet> list = timesheetDao.findApproveByUserId(paras);
		PageInfo<VoTimesheet> page = new PageInfo<VoTimesheet>(list);
		logger.debug(page.toString());
		return page;
	}

	@Override
	public void batchApprove(String username, Long[] selectedId) {
		if (selectedId != null) {
			for (Long id : selectedId) {
				Timesheet t = timesheetDao.selectByPrimaryKey(id);
				if (t != null) {
					if (t.getTsStatus() != null && TimesheetStatus.pending.equals(t.getTsStatus())) {
						t.setTsStatus(TimesheetStatus.confirmed);
						t.setOperateUser(username);
						t.setOperateTime(Calendar.getInstance().getTime());
						timesheetDao.updateByPrimaryKey(t);
					}
				}
			}
		}
	}

	@Override
	public void batchReject(String username, Long[] selectedId) {
		if (selectedId != null) {
			for (Long id : selectedId) {
				Timesheet t = timesheetDao.selectByPrimaryKey(id);
				if (t != null) {
					if (t.getTsStatus() != null && TimesheetStatus.pending.equals(t.getTsStatus())) {
						t.setTsStatus(TimesheetStatus.created);
						t.setOperateUser(username);
						t.setOperateTime(Calendar.getInstance().getTime());
						timesheetDao.updateByPrimaryKey(t);
					}
				}
			}
		}
	}

	@Override
	public List<Timesheet> findAllByTsDate(Long userId, Date tsDate) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("userId", userId);
		paras.put("tsDate", tsDate);
		return timesheetDao.findAllByTsDate(paras);
	}

	@Override
	public int updateByPrimaryKey(Timesheet record) {
		return timesheetDao.updateByPrimaryKey(record);
	}

	@Override
	public BigDecimal sumProjectCosts(Long projectId, TimesheetStatus status) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("projectId", projectId);
		paras.put("tsStatus", status);
		return timesheetDao.sumProjectCosts(paras);
	}

}
