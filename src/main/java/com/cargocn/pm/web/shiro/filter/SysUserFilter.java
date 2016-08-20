package com.cargocn.pm.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cargocn.pm.Constants;
import com.cargocn.pm.service.IUserService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-15
 * <p>
 * Version: 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

	@Autowired
	private IUserService userService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		String username = (String) SecurityUtils.getSubject().getPrincipal();
		request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
		return true;
	}
}
