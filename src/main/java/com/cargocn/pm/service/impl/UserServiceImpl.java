package com.cargocn.pm.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.LevelCost;
import com.cargocn.pm.bean.Timesheet;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.UserCost;
import com.cargocn.pm.bean.UserLevel;
import com.cargocn.pm.bean.VoUserLevel;
import com.cargocn.pm.dao.LevelCostMapper;
import com.cargocn.pm.dao.TimesheetMapper;
import com.cargocn.pm.dao.UserCostMapper;
import com.cargocn.pm.dao.UserLevelMapper;
import com.cargocn.pm.dao.UserMapper;
import com.cargocn.pm.service.IRoleService;
import com.cargocn.pm.service.ITimesheetService;
import com.cargocn.pm.service.IUserService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-1-28
 * <p>
 * Version: 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserMapper userDao;
	@Autowired
	private UserCostMapper userCostDao;
	@Autowired
	private UserLevelMapper userLevelDao;
	@Autowired
	private LevelCostMapper levelCostDao;
	@Autowired
	private PasswordHelper passwordHelper;
	@Autowired
	private IRoleService roleService;

	@Autowired
	private ITimesheetService timesheetService;

	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	public User createUser(User user) {
		// 加密密码
		passwordHelper.encryptPassword(user);
		int ret = userDao.insert(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		int ret = userDao.updateByPrimaryKey(user);
		return user;
	}

	@Override
	public void deleteUser(Long userId) {
		userDao.deleteByPrimaryKey(userId);
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.selectByPrimaryKey(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		userDao.updateByPrimaryKey(user);
	}

	@Override
	public User findOne(Long userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username) {
		User user = findByUsername(username);
		if (user == null) {
			return Collections.EMPTY_SET;
		}
		return roleService.findRoles(user.getRoleIds().toArray(new Long[0]));
	}

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username) {
		User user = findByUsername(username);
		if (user == null) {
			return Collections.EMPTY_SET;
		}
		return roleService.findPermissions(user.getRoleIds().toArray(new Long[0]));
	}

	@Override
	public List<UserCost> findUserCosts(Long userId) {
		return userCostDao.findUserCosts(userId);
	}

	@Override
	public void createUserCost(UserCost userCost, Boolean chgSheetCost) {
		if (userCost != null && userCost.getBeginDate() != null) {
			userCostDao.insert(userCost);
			if (chgSheetCost != null && chgSheetCost.booleanValue()) {
				List<Timesheet> tsl = timesheetService.findAllByTsDate(userCost.getUserId(), userCost.getBeginDate());
				if (tsl != null) {
					for (Timesheet ts : tsl) {
						BigDecimal uc = findUserCost(ts.getUserId(), ts.getTsDate());
						ts.setTsCost(CostCalHelper.cal(uc, ts.getTsHour()));
						timesheetService.updateByPrimaryKey(ts);
					}
				}
			}
		}
	}

	@Override
	public List<VoUserLevel> findUserLevels(Long userId) {
		return userLevelDao.findUserLevels(userId);
	}

	@Override
	public void createUserLevel(UserLevel userLevel, Boolean chgSheetCost) {
		if (userLevel != null && userLevel.getBeginDate() != null) {
			userLevelDao.insert(userLevel);
			if (chgSheetCost != null && chgSheetCost.booleanValue()) {
				List<Timesheet> tsl = timesheetService.findAllByTsDate(userLevel.getUserId(), userLevel.getBeginDate());
				if (tsl != null) {
					for (Timesheet ts : tsl) {
						BigDecimal uc = findUserCost(ts.getUserId(), ts.getTsDate());
						ts.setTsCost(CostCalHelper.cal(uc, ts.getTsHour()));
						timesheetService.updateByPrimaryKey(ts);
					}
				}
			}
		}
	}

	@Override
	public BigDecimal findUserCost(Long userId, Date costDate) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("userId", userId);
		paras.put("beginDate", costDate);
		logger.debug("userId " + userId + " beginDate " + costDate);
		List<UserCost> ucl = userCostDao.findUserCost(paras);
		if (ucl != null && ucl.size() > 0) {
			UserCost uc = ucl.get(0);
			if (uc != null && uc.getCost() != null) {
				logger.debug("user cost " + uc.getCost());
				return uc.getCost();
			}
		}

		// user level
		UserLevel ul = findUserLevel(userId, costDate);
		if (ul != null) {
			// level cost
			paras.put("levelId", ul.getLevelId());
			logger.debug("level id " + ul.getLevelId());
			List<LevelCost> lcl = levelCostDao.findLevelCost(paras);
			if (lcl != null && lcl.size() > 0) {
				LevelCost lc = lcl.get(0);
				if (lc != null && lc.getCost() != null) {
					logger.debug("level cost " + lc.getCost());
					return lc.getCost();
				}
			}
		}

		logger.debug("cost null");
		return null;
	}

	@Override
	public UserLevel findUserLevel(Long userId, Date levelDate) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("userId", userId);
		paras.put("beginDate", levelDate);
		List<UserLevel> ull = userLevelDao.findUserLevel(paras);
		if (ull != null && ull.size() > 0) {
			return ull.get(0);
		}
		return null;
	}

}
