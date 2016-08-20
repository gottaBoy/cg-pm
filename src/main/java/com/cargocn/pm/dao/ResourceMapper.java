package com.cargocn.pm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cargocn.pm.bean.Resource;

public interface ResourceMapper {
	public int deleteByPrimaryKey(Long id);

	public int insert(Resource record);

	public int insertSelective(Resource record);

	public Resource selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(Resource record);

	public int updateByPrimaryKey(Resource record);

	public void deleteDescendants(@Param("selfAsParentIds") String selfAsParentIds);

	public List<Resource> findAll();
}