package com.cargocn.pm.dao;

import java.util.List;
import java.util.Map;

import com.cargocn.pm.bean.LevelCost;

public interface LevelCostMapper {
	int deleteByPrimaryKey(Long costId);

	int insert(LevelCost record);

	int insertSelective(LevelCost record);

	LevelCost selectByPrimaryKey(Long costId);

	int updateByPrimaryKeySelective(LevelCost record);

	int updateByPrimaryKey(LevelCost record);

	List<LevelCost> findLevelCosts(Long levelId);

	List<LevelCost> findLevelCost(Map<String, Object> paras);
}