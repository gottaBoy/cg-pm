package com.cargocn.pm.web.controller;

import java.util.Calendar;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.UserCost;
import com.cargocn.pm.bean.UserLevel;
import com.cargocn.pm.service.ILevelService;
import com.cargocn.pm.service.IOrganizationService;
import com.cargocn.pm.service.IRoleService;
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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private ILevelService levelService;

	@RequiresPermissions("system:user:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("userList", userService.findAll());
		return "user/list";
	}

	@RequiresPermissions("system:user:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		setCommonData(model);
		model.addAttribute("userObj", new User());
		model.addAttribute("op", "新增");
		return "user/edit";
	}

	@RequiresPermissions("system:user:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(User user, RedirectAttributes redirectAttributes) {
		userService.createUser(user);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/user";
	}

	@RequiresPermissions("system:user:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		setCommonData(model);
		model.addAttribute("userObj", userService.findOne(id));
		model.addAttribute("op", "修改");
		return "user/edit";
	}

	@RequiresPermissions("system:user:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(User user, RedirectAttributes redirectAttributes) {
		userService.updateUser(user);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/user";
	}

	@RequiresPermissions("system:user:delete")
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String showDeleteForm(@PathVariable("id") Long id, Model model) {
		setCommonData(model);
		model.addAttribute("userObj", userService.findOne(id));
		model.addAttribute("op", "删除");
		return "user/edit";
	}

	@RequiresPermissions("system:user:delete")
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/user";
	}

	@RequiresPermissions("system:user:update")
	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
	public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userObj", userService.findOne(id));
		model.addAttribute("op", "修改密码");
		return "user/changePassword";
	}

	@RequiresPermissions("system:user:update")
	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
	public String changePassword(@PathVariable("id") Long id, String newPassword,
			RedirectAttributes redirectAttributes) {
		userService.changePassword(id, newPassword);
		redirectAttributes.addFlashAttribute("msg", "修改密码成功");
		return "redirect:/user";
	}

	private void setCommonData(Model model) {
		model.addAttribute("organizationList", organizationService.findAll());
		model.addAttribute("roleList", roleService.findAll());
	}

	@RequiresPermissions("system:user:cost")
	@RequestMapping(value = "/{id}/cost", method = RequestMethod.GET)
	public String showCostForm(@PathVariable("id") Long id, Model model) {
		User user = userService.findOne(id);
		UserCost userCost = new UserCost();
		userCost.setUserId(id);
		model.addAttribute("userObj", user);
		model.addAttribute("userCost", userCost);
		model.addAttribute("userCosts", userService.findUserCosts(id));

		return "user/cost";
	}

	@RequiresPermissions("system:user:cost")
	@RequestMapping(value = "/{id}/cost", method = RequestMethod.POST)
	public String auth(@CurrentUser User loginUser, UserCost userCost, Boolean chgSheetCost,
			RedirectAttributes redirectAttributes) {
		userCost.setOperateUser(loginUser.getUsername());
		userCost.setOperateTime(Calendar.getInstance().getTime());
		userService.createUserCost(userCost, chgSheetCost);
		redirectAttributes.addFlashAttribute("msg", "设定成功");
		return "redirect:/user";
	}

	@RequiresPermissions("system:user:level")
	@RequestMapping(value = "/{id}/level", method = RequestMethod.GET)
	public String showLevelForm(@PathVariable("id") Long id, Model model) {
		User user = userService.findOne(id);
		UserLevel userLevel = new UserLevel();
		userLevel.setUserId(id);
		model.addAttribute("userObj", user);
		model.addAttribute("userLevel", userLevel);
		model.addAttribute("levels", levelService.findLevel(1, Integer.MAX_VALUE).getList());
		model.addAttribute("userLevels", userService.findUserLevels(id));

		return "user/level";
	}

	@RequiresPermissions("system:user:level")
	@RequestMapping(value = "/{id}/level", method = RequestMethod.POST)
	public String auth(@CurrentUser User loginUser, UserLevel userLevel, Boolean chgSheetCost,
			RedirectAttributes redirectAttributes) {
		userLevel.setOperateUser(loginUser.getUsername());
		userLevel.setOperateTime(Calendar.getInstance().getTime());
		userService.createUserLevel(userLevel, chgSheetCost);
		redirectAttributes.addFlashAttribute("msg", "设定成功");
		return "redirect:/user";
	}
}
