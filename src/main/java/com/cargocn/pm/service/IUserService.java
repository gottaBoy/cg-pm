package com.cargocn.pm.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.UserCost;
import com.cargocn.pm.bean.UserLevel;
import com.cargocn.pm.bean.VoUserLevel;

public interface IUserService {
	/**
	 * 创建用户
	 * 
	 * @param user
	 */
	public User createUser(User user);

	public User updateUser(User user);

	public void deleteUser(Long userId);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId, String newPassword);

	User findOne(Long userId);

	List<User> findAll();

	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 根据用户名查找其角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);

	/**
	 * 根据用户名查找其权限
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);

	public List<UserCost> findUserCosts(Long userId);

	public void createUserCost(UserCost userCost, Boolean chgSheetCost);

	public List<VoUserLevel> findUserLevels(Long userId);

	public void createUserLevel(UserLevel userLevel, Boolean chgSheetCost);

	public BigDecimal findUserCost(Long userId, Date costDate);

	public UserLevel findUserLevel(Long userId, Date levelDate);
}
