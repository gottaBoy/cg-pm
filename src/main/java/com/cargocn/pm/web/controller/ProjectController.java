package com.cargocn.pm.web.controller;

import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cargocn.pm.DataTablesRequest;
import com.cargocn.pm.DataTablesResponse;
import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoProject;
import com.cargocn.pm.service.IProjectService;
import com.cargocn.pm.service.IUserService;
import com.cargocn.pm.web.bind.annotation.CurrentUser;
import com.github.pagehelper.PageInfo;

/** 
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IProjectService projectService;
	@Autowired
	private IUserService userService;

	@RequiresPermissions("system:project:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "project/list";
	}

	@RequiresPermissions("system:project:view")
	@RequestMapping(value = "/j_list")
	public @ResponseBody DataTablesResponse<VoProject> jlist(@CurrentUser User loginUser, Model model,
			String dtParaJson) {
		logger.debug(dtParaJson);

		DataTablesRequest req = DataTablesRequest.getInstance(dtParaJson);
		PageInfo<VoProject> page = projectService.findProject(loginUser.getId(), req.getPageNum(),
				req.getiDisplayLength());

		DataTablesResponse<VoProject> ret = new DataTablesResponse<VoProject>();
		ret.setData(page.getList());
		ret.setDraw(req.getsEcho() + 1);
		ret.setRecordsTotal(page.getTotal());
		ret.setRecordsFiltered(page.getTotal());
		return ret;
	}

	@RequiresPermissions("system:project:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		VoProject p = new VoProject();
		model.addAttribute("project", p);
		model.addAttribute("op", "新增");
		return "project/edit";
	}

	@RequiresPermissions("system:project:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@CurrentUser User loginUser, Project project, RedirectAttributes redirectAttributes) {
		project.setOperateUser(loginUser.getUsername());
		project.setOperateTime(Calendar.getInstance().getTime());
		project.setPmUserId(loginUser.getId());
		projectService.createProject(project);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/project";
	}

	@RequiresPermissions("system:project:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("project", projectService.findOne(id));
		model.addAttribute("op", "修改");
		return "project/edit";
	}

	@RequiresPermissions("system:project:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(@CurrentUser User loginUser, Project project, RedirectAttributes redirectAttributes)
			throws IOException {
		project.setOperateUser(loginUser.getUsername());
		project.setOperateTime(Calendar.getInstance().getTime());
		if (project.getPmUserId() == null)
			project.setPmUserId(loginUser.getId());
		projectService.updateProject(project);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/project";
	}

	@RequiresPermissions("system:project:auth")
	@RequestMapping(value = "/{id}/auth", method = RequestMethod.GET)
	public String showAuthForm(@PathVariable("id") Long id, Model model) {
		Project project = projectService.findOne(id);
		VoProject p = new VoProject();
		if (project != null) {
			try {
				BeanUtils.copyProperties(p, project);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		p.setUserIds(projectService.findProjectUserIds(id));
		model.addAttribute("project", p);
		model.addAttribute("userList", userService.findAll());

		return "project/auth";
	}

	@RequiresPermissions("system:project:auth")
	@RequestMapping(value = "/{id}/auth", method = RequestMethod.POST)
	public String auth(@CurrentUser User loginUser, VoProject project, RedirectAttributes redirectAttributes) {
		projectService.authUser(project.getProjectId(), project.getUserIds());
		redirectAttributes.addFlashAttribute("msg", "授权成功");
		return "redirect:/project";
	}

}
