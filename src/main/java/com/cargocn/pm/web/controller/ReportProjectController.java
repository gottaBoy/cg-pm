package com.cargocn.pm.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import com.cargocn.pm.bean.Expenses.ExpensesStatus;
import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.Timesheet.TimesheetStatus;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoProject;
import com.cargocn.pm.service.IExpensesService;
import com.cargocn.pm.service.IProjectService;
import com.cargocn.pm.service.ITimesheetService;
import com.cargocn.pm.service.IUserService;
import com.cargocn.pm.web.bind.annotation.CurrentUser;
import com.github.pagehelper.PageInfo;

/** 
 */
@Controller
@RequestMapping("/reportproject")
public class ReportProjectController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IExpensesService expensesService;

	@Autowired
	private ITimesheetService timesheetService;

	@RequiresPermissions("report:project:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "report/listProject";
	}

	@RequiresPermissions("report:project:view")
	@RequestMapping(value = "/j_list")
	public @ResponseBody DataTablesResponse<VoProject> jlist(Model model, String dtParaJson) {
		logger.debug(dtParaJson);

		DataTablesRequest req = DataTablesRequest.getInstance(dtParaJson);
		PageInfo<VoProject> page = projectService.findProject(null, req.getPageNum(), req.getiDisplayLength());

		DataTablesResponse<VoProject> ret = new DataTablesResponse<VoProject>();
		ret.setData(page.getList());
		ret.setDraw(req.getsEcho() + 1);
		ret.setRecordsTotal(page.getTotal());
		ret.setRecordsFiltered(page.getTotal());
		return ret;
	}

	@RequiresPermissions("report:project:view")
	@RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
	public String showViewForm(@PathVariable("id") Long id, Model model) {
		VoProject voProject = new VoProject();
		Project p = projectService.findOne(id);
		try {
			BeanUtils.copyProperties(voProject, p);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		voProject.setConfirmedExpenses(expensesService.sumProjectExpenses(id, ExpensesStatus.confirmed));
		voProject.setPendingExpenses(expensesService.sumProjectExpenses(id, ExpensesStatus.pending));
		voProject.setCreatedExpenses(expensesService.sumProjectExpenses(id, ExpensesStatus.created));
		voProject.setConfirmedCosts(timesheetService.sumProjectCosts(id, TimesheetStatus.confirmed));
		voProject.setPendingCosts(timesheetService.sumProjectCosts(id, TimesheetStatus.pending));
		voProject.setCreatedCosts(timesheetService.sumProjectCosts(id, TimesheetStatus.created));
		voProject.setTotal();
		model.addAttribute("project", voProject);

		return "report/viewProject";
	}

}
