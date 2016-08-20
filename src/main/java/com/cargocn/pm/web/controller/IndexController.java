package com.cargocn.pm.web.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cargocn.pm.bean.Resource;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.service.IResourceService;
import com.cargocn.pm.service.IUserService;
import com.cargocn.pm.web.bind.annotation.CurrentUser;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-14
 * <p>
 * Version: 1.0
 */
@Controller
public class IndexController {

	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IUserService userService;

	@RequestMapping("/")
	public String index(@CurrentUser User loginUser, Model model) {
		Set<String> permissions = userService.findPermissions(loginUser.getUsername());
		List<Resource> menus = resourceService.findMenus(permissions);
		model.addAttribute("menus", menus);
		return "index";
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

}
