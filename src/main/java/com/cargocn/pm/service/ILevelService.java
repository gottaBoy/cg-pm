package com.cargocn.pm.service;

import java.util.List;

import com.cargocn.pm.bean.Level;
import com.cargocn.pm.bean.LevelCost;
import com.github.pagehelper.PageInfo;

public interface ILevelService {
	public PageInfo<Level> findLevel(int pageNum, int pageSize);

	public Level findOne(Long levelId);

	public void updateLevel(Level l);

	public void createLevel(Level l);

	public List<LevelCost> findLevelCosts(Long levelId);

	public void createLevelCost(LevelCost levelCost, Boolean chgSheetCost);
}
