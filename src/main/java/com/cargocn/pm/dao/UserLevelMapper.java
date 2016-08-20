package com.cargocn.pm.dao;

import java.util.List;
import java.util.Map;

import com.cargocn.pm.bean.UserLevel;
import com.cargocn.pm.bean.VoUserLevel;

public interface UserLevelMapper {
	int deleteByPrimaryKey(Long userLevelId);

	int insert(UserLevel record);

	int insertSelective(UserLevel record);

	UserLevel selectByPrimaryKey(Long userLevelId);

	int updateByPrimaryKeySelective(UserLevel record);

	int updateByPrimaryKey(UserLevel record);

	List<VoUserLevel> findUserLevels(Long userId);

	List<UserLevel> findUserLevel(Map<String, Object> paras);
}