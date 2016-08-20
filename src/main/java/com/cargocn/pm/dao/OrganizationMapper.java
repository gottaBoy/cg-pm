package com.cargocn.pm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cargocn.pm.bean.Organization;

public interface OrganizationMapper {
	public int deleteByPrimaryKey(Long id);

	public int deleteDescendants(@Param("selfAsParentIds") String selfAsParentIds);

	public int insert(Organization record);

	public int insertSelective(Organization record);

	public Organization selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(Organization record);

	public int updateByPrimaryKey(Organization record);

	public List<Organization> findAll();

	public List<Organization> findAllWithExclude(@Param("id") Long id,
			@Param("selfAsParentIds") String selfAsParentIds);

	public void moveSource(@Param("sourceId") Long sourceId, @Param("targetId") Long targetId,
			@Param("targetParentIds") String targetParentIds);

	public void moveSourceDescendants(@Param("targetSelfAsParentIds") String targetSelfAsParentIds,
			@Param("sourceSelfAsParentIds") String sourceSelfAsParentIds,
			@Param("sourceSelfAsParentIds2") String sourceSelfAsParentIds2);
}