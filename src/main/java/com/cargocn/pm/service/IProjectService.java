package com.cargocn.pm.service;

import java.util.List;

import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.VoProject;
import com.github.pagehelper.PageInfo;

public interface IProjectService {
	public PageInfo<VoProject> findProject(Long pmUserId, int pageNum, int pageSize);

	public List<Project> findUserProjects(Long userId);

	public Project findOne(Long projectId);

	public void updateProject(Project p);

	public void createProject(Project p);

	public List<Long> findProjectUserIds(Long projectId);

	public void authUser(Long projectId, List<Long> userIds);
}
