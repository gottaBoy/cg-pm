package com.cargocn.pm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.Level;
import com.cargocn.pm.bean.LevelCost;
import com.cargocn.pm.bean.Timesheet;
import com.cargocn.pm.bean.UserLevel;
import com.cargocn.pm.dao.LevelCostMapper;
import com.cargocn.pm.dao.LevelMapper;
import com.cargocn.pm.dao.TimesheetMapper;
import com.cargocn.pm.service.ILevelService;
import com.cargocn.pm.service.ITimesheetService;
import com.cargocn.pm.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class LevelServiceImpl implements ILevelService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LevelMapper levelDao;
	@Autowired
	private LevelCostMapper levelCostDao;

	@Autowired
	private ITimesheetService timesheetService;

	@Autowired
	private IUserService userService;

	@Override
	public PageInfo<Level> findLevel(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Level> list = levelDao.findAll();
		PageInfo<Level> page = new PageInfo<Level>(list);
		logger.debug(page.toString());
		return page;
	}

	@Override
	public Level findOne(Long levelId) {
		return levelDao.selectByPrimaryKey(levelId);
	}

	@Override
	public void updateLevel(Level l) {
		levelDao.updateByPrimaryKey(l);
	}

	@Override
	public void createLevel(Level l) {
		levelDao.insert(l);
	}

	@Override
	public List<LevelCost> findLevelCosts(Long levelId) {
		return levelCostDao.findLevelCosts(levelId);
	}

	@Override
	public void createLevelCost(LevelCost levelCost, Boolean chgSheetCost) {
		if (levelCost != null && levelCost.getBeginDate() != null) {
			levelCostDao.insert(levelCost);

			if (chgSheetCost != null && chgSheetCost.booleanValue()) {
				List<Timesheet> tsl = timesheetService.findAllByTsDate(null, levelCost.getBeginDate());
				if (tsl != null) {
					for (Timesheet ts : tsl) {
						BigDecimal uc = userService.findUserCost(ts.getUserId(), ts.getTsDate());
						ts.setTsCost(CostCalHelper.cal(uc, ts.getTsHour()));
						timesheetService.updateByPrimaryKey(ts);
					}
				}
			}
		}
	}

}
