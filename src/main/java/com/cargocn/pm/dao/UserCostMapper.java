package com.cargocn.pm.dao;

import java.util.List;
import java.util.Map;

import com.cargocn.pm.bean.UserCost;

public interface UserCostMapper {
	int deleteByPrimaryKey(Long costId);

	int insert(UserCost record);

	int insertSelective(UserCost record);

	UserCost selectByPrimaryKey(Long costId);

	int updateByPrimaryKeySelective(UserCost record);

	int updateByPrimaryKey(UserCost record);

	List<UserCost> findUserCosts(Long userId);
	
	List<UserCost> findUserCost(Map<String,Object> paras);
}