package com.cargocn.pm.web.taglib;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.cargocn.pm.bean.Organization;
import com.cargocn.pm.bean.Resource;
import com.cargocn.pm.bean.Role;
import com.cargocn.pm.service.IOrganizationService;
import com.cargocn.pm.service.IResourceService;
import com.cargocn.pm.service.IRoleService;
import com.cargocn.spring.SpringUtils;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-15
 * <p>
 * Version: 1.0
 */
public class Functions {

	public static boolean in(Iterable iterable, Object element) {
		if (iterable == null) {
			return false;
		}
		return CollectionUtils.contains(iterable.iterator(), element);
	}

	public static String organizationName(Long organizationId) {
		Organization organization = getOrganizationService().findOne(organizationId);
		if (organization == null) {
			return "";
		}
		return organization.getName();
	}

	public static String organizationNames(Collection<Long> organizationIds) {
		if (CollectionUtils.isEmpty(organizationIds)) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (Long organizationId : organizationIds) {
			Organization organization = getOrganizationService().findOne(organizationId);
			if (organization == null) {
				return "";
			}
			s.append(organization.getName());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	public static String roleName(Long roleId) {
		Role role = getRoleService().findOne(roleId);
		if (role == null) {
			return "";
		}
		return role.getDescription();
	}

	public static String roleNames(Collection<Long> roleIds) {
		if (CollectionUtils.isEmpty(roleIds)) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (Long roleId : roleIds) {
			Role role = getRoleService().findOne(roleId);
			if (role == null) {
				return "";
			}
			s.append(role.getDescription());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	public static String resourceName(Long resourceId) {
		Resource resource = getResourceService().findOne(resourceId);
		if (resource == null) {
			return "";
		}
		return resource.getName();
	}

	public static String resourceNames(Collection<Long> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (Long resourceId : resourceIds) {
			Resource resource = getResourceService().findOne(resourceId);
			if (resource == null) {
				return "";
			}
			s.append(resource.getName());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	private static IOrganizationService organizationService;
	private static IRoleService roleService;
	private static IResourceService resourceService;

	public static IOrganizationService getOrganizationService() {
		if (organizationService == null) {
			organizationService = SpringUtils.getBean(IOrganizationService.class);
		}
		return organizationService;
	}

	public static IRoleService getRoleService() {
		if (roleService == null) {
			roleService = SpringUtils.getBean(IRoleService.class);
		}
		return roleService;
	}

	public static IResourceService getResourceService() {
		if (resourceService == null) {
			resourceService = SpringUtils.getBean(IResourceService.class);
		}
		return resourceService;
	}
}
