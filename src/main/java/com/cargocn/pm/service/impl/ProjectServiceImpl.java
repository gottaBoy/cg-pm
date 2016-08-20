package com.cargocn.pm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.ProjectUserKey;
import com.cargocn.pm.bean.VoProject;
import com.cargocn.pm.dao.ProjectMapper;
import com.cargocn.pm.dao.ProjectUserMapper;
import com.cargocn.pm.service.IProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ProjectServiceImpl implements IProjectService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProjectMapper projectDao;
	@Autowired
	private ProjectUserMapper projectUserDao;

	@Override
	public PageInfo<VoProject> findProject(Long pmUserId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<VoProject> list = projectDao.findByAdminId(pmUserId);
		PageInfo<VoProject> page = new PageInfo<VoProject>(list);
		logger.debug(page.toString());
		return page;
	}

	@Override
	public Project findOne(Long projectId) {
		return projectDao.selectByPrimaryKey(projectId);
	}

	@Override
	public void updateProject(Project p) {
		projectDao.updateByPrimaryKey(p);
	}

	@Override
	public void createProject(Project p) {
		projectDao.insert(p);
	}

	@Override
	public List<Long> findProjectUserIds(Long projectId) {
		List<ProjectUserKey> tmp = projectUserDao.findProjectUserIds(projectId);
		List<Long> ret = new ArrayList<Long>();
		if (tmp != null) {
			for (ProjectUserKey pu : tmp) {
				ret.add(pu.getUserId());
			}
		}
		return ret;
	}

	@Override
	public void authUser(Long projectId, List<Long> userIds) {
		projectUserDao.deleteByProjectId(projectId);
		if (userIds != null) {
			for (Long userId : userIds) {
				ProjectUserKey pu = new ProjectUserKey();
				pu.setProjectId(projectId);
				pu.setUserId(userId);
				projectUserDao.insert(pu);
			}
		}
	}

	@Override
	public List<Project> findUserProjects(Long userId) {
		return projectDao.findUserProjects(userId);
	}

}
