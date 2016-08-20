package com.cargocn.pm.dao;

import java.util.List;

import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.VoProject;

public interface ProjectMapper {
	int deleteByPrimaryKey(Long projectId);

	int insert(Project record);

	int insertSelective(Project record);

	Project selectByPrimaryKey(Long projectId);

	int updateByPrimaryKeySelective(Project record);

	int updateByPrimaryKey(Project record);

	List<VoProject> findByAdminId(Long pmUserId);

	List<Project> findUserProjects(Long userId);

	Project selectByProjectCode(String projectCode);

	Project selectByProjectName(String projectName);
}