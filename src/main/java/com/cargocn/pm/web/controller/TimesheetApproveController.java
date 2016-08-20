package com.cargocn.pm.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cargocn.pm.DataTablesRequest;
import com.cargocn.pm.DataTablesResponse;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoTimesheet;
import com.cargocn.pm.service.IProjectService;
import com.cargocn.pm.service.ITimesheetService;
import com.cargocn.pm.web.bind.annotation.CurrentUser;
import com.github.pagehelper.PageInfo;

/** 
 */
@Controller
@RequestMapping("/timesheetapprove")
public class TimesheetApproveController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ITimesheetService timesheetService;
	@Autowired
	private IProjectService projectService;

	@RequiresPermissions("approve:timesheet:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "timesheet/listapprove";
	}

	@RequiresPermissions("approve:timesheet:view")
	@RequestMapping(value = "/j_list")
	public @ResponseBody DataTablesResponse<VoTimesheet> jlist(@CurrentUser User loginUser, Model model,
			String dtParaJson) {
		logger.debug(dtParaJson);

		DataTablesRequest req = DataTablesRequest.getInstance(dtParaJson);
		PageInfo<VoTimesheet> page = timesheetService.findTimesheetApprove(loginUser.getId(), req.getPageNum(),
				req.getiDisplayLength());

		DataTablesResponse<VoTimesheet> ret = new DataTablesResponse<VoTimesheet>();
		ret.setData(page.getList());
		ret.setDraw(req.getsEcho() + 1);
		ret.setRecordsTotal(page.getTotal());
		ret.setRecordsFiltered(page.getTotal());
		return ret;
	}

	@RequiresPermissions("approve:timesheet:approve")
	@RequestMapping(value = "/{id}/approve", method = RequestMethod.GET)
	public String approve(@CurrentUser User loginUser, @PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		timesheetService.batchApprove(loginUser.getUsername(), new Long[] { id });
		redirectAttributes.addFlashAttribute("msg", "审批成功");
		return "redirect:/timesheetapprove";
	}

	@RequiresPermissions("approve:timesheet:approve")
	@RequestMapping(value = "/batchApprove", method = RequestMethod.GET)
	public String batchApprove(@CurrentUser User loginUser, @RequestParam(value = "selectedId[]") Long[] selectedId,
			RedirectAttributes redirectAttributes) {
		timesheetService.batchApprove(loginUser.getUsername(), selectedId);
		redirectAttributes.addFlashAttribute("msg", "批量审批成功");
		return "redirect:/timesheetapprove";
	}

	@RequiresPermissions("approve:timesheet:approve")
	@RequestMapping(value = "/{id}/reject", method = RequestMethod.GET)
	public String reject(@CurrentUser User loginUser, @PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		timesheetService.batchReject(loginUser.getUsername(), new Long[] { id });
		redirectAttributes.addFlashAttribute("msg", "拒绝成功");
		return "redirect:/timesheetapprove";
	}

	@RequiresPermissions("approve:timesheet:approve")
	@RequestMapping(value = "/batchReject", method = RequestMethod.GET)
	public String batchReject(@CurrentUser User loginUser, @RequestParam(value = "selectedId[]") Long[] selectedId,
			RedirectAttributes redirectAttributes) {
		timesheetService.batchReject(loginUser.getUsername(), selectedId);
		redirectAttributes.addFlashAttribute("msg", "批量拒绝成功");
		return "redirect:/timesheetapprove";
	}

}
