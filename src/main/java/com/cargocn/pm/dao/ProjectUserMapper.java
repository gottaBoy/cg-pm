package com.cargocn.pm.dao;

import java.util.List;

import com.cargocn.pm.bean.ProjectUserKey;

public interface ProjectUserMapper {
	int deleteByPrimaryKey(ProjectUserKey key);

	int deleteByProjectId(Long projectId);

	int insert(ProjectUserKey record);

	int insertSelective(ProjectUserKey record);

	List<ProjectUserKey> findProjectUserIds(Long projectId);
}