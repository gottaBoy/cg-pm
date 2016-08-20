package com.cargocn.pm.dao;

import java.util.List;

import com.cargocn.pm.bean.Level;

public interface LevelMapper {
	int deleteByPrimaryKey(Long levelId);

	int insert(Level record);

	int insertSelective(Level record);

	Level selectByPrimaryKey(Long levelId);

	int updateByPrimaryKeySelective(Level record);

	int updateByPrimaryKey(Level record);

	List<Level> findAll();
}