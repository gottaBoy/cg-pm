package com.cargocn.pm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.Organization;
import com.cargocn.pm.dao.OrganizationMapper;
import com.cargocn.pm.service.IOrganizationService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-14
 * <p>
 * Version: 1.0
 */
@Service
public class OrganizationServiceImpl implements IOrganizationService {
	@Autowired
	private OrganizationMapper organizationDao;

	@Override
	public Organization createOrganization(Organization organization) {
		int ret = organizationDao.insert(organization);
		return organization;
	}

	@Override
	public Organization updateOrganization(Organization organization) {
		int ret = organizationDao.updateByPrimaryKey(organization);
		return organization;
	}

	@Override
	public void deleteOrganization(Long organizationId) {
		Organization organization = findOne(organizationId);
		organizationDao.deleteByPrimaryKey(organizationId);
		organizationDao.deleteDescendants(organization.makeSelfAsParentIds() + "%");
	}

	@Override
	public Organization findOne(Long organizationId) {
		return organizationDao.selectByPrimaryKey(organizationId);
	}

	@Override
	public List<Organization> findAll() {
		return organizationDao.findAll();
	}

	@Override
	public List<Organization> findAllWithExclude(Organization excludeOraganization) {
		return organizationDao.findAllWithExclude(excludeOraganization.getId(),
				excludeOraganization.makeSelfAsParentIds() + "%");
	}

	@Override
	public void move(Organization source, Organization target) {
		organizationDao.moveSource(source.getId(), target.getId(), target.getParentIds());
		organizationDao.moveSourceDescendants(target.makeSelfAsParentIds(), source.makeSelfAsParentIds(),
				source.makeSelfAsParentIds() + "%");
	}
}
