package com.cargocn.pm.web.controller;

import java.io.IOException;
import java.util.Calendar;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cargocn.pm.DataTablesRequest;
import com.cargocn.pm.DataTablesResponse;
import com.cargocn.pm.bean.Expenses;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoExpenses;
import com.cargocn.pm.service.IExpensesService;
import com.cargocn.pm.service.IExpensesTypeService;
import com.cargocn.pm.service.IProjectService;
import com.cargocn.pm.web.bind.annotation.CurrentUser;
import com.github.pagehelper.PageInfo;

/** 
 */
@Controller
@RequestMapping("/expensescreate")
public class ExpensesController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IExpensesService expensesService;
	@Autowired
	private IExpensesTypeService expensesTypeService;
	@Autowired
	private IProjectService projectService;

	@RequiresPermissions("cost:expenses:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "expenses/list";
	}

	@RequiresPermissions("cost:expenses:view")
	@RequestMapping(value = "/j_list")
	public @ResponseBody DataTablesResponse<VoExpenses> jlist(@CurrentUser User loginUser, Model model,
			String dtParaJson) {
		logger.debug(dtParaJson);

		DataTablesRequest req = DataTablesRequest.getInstance(dtParaJson);
		PageInfo<VoExpenses> page = expensesService.findExpenses(loginUser.getId(), req.getPageNum(),
				req.getiDisplayLength());

		DataTablesResponse<VoExpenses> ret = new DataTablesResponse<VoExpenses>();
		ret.setData(page.getList());
		ret.setDraw(req.getsEcho() + 1);
		ret.setRecordsTotal(page.getTotal());
		ret.setRecordsFiltered(page.getTotal());
		return ret;
	}

	@RequiresPermissions("cost:expenses:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(@CurrentUser User loginUser, Model model) {
		setCommonData(loginUser, model);
		Expenses e = new Expenses();
		model.addAttribute("expenses", e);
		model.addAttribute("op", "新增");
		return "expenses/edit";
	}

	@RequiresPermissions("cost:expenses:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@CurrentUser User loginUser, Expenses e, RedirectAttributes redirectAttributes) {
		e.setUserId(loginUser.getId());
		e.setOperateUser(loginUser.getUsername());
		e.setOperateTime(Calendar.getInstance().getTime());
		expensesService.createExpenses(e);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/expensescreate";
	}

	@RequiresPermissions("cost:expenses:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@CurrentUser User loginUser, @PathVariable("id") Long id, Model model) {
		setCommonData(loginUser, model);
		model.addAttribute("expenses", expensesService.findOne(id));
		model.addAttribute("op", "修改");
		return "expenses/edit";
	}

	@RequiresPermissions("cost:expenses:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(@CurrentUser User loginUser, Expenses e, RedirectAttributes redirectAttributes)
			throws IOException {
		e.setUserId(loginUser.getId());
		e.setOperateUser(loginUser.getUsername());
		e.setOperateTime(Calendar.getInstance().getTime());
		expensesService.updateExpenses(e);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/expensescreate";
	}

	@RequiresPermissions("cost:expenses:pending")
	@RequestMapping(value = "/{id}/pending", method = RequestMethod.GET)
	public String pending(@CurrentUser User loginUser, @PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		expensesService.batchSubmit(loginUser.getUsername(), new Long[] { id });
		redirectAttributes.addFlashAttribute("msg", "提交成功");
		return "redirect:/expensescreate";
	}

	@RequiresPermissions("cost:expenses:pending")
	@RequestMapping(value = "/batchPending", method = RequestMethod.GET)
	public String batchPending(@CurrentUser User loginUser, @RequestParam(value = "selectedId[]") Long[] selectedId,
			RedirectAttributes redirectAttributes) {
		expensesService.batchSubmit(loginUser.getUsername(), selectedId);
		redirectAttributes.addFlashAttribute("msg", "批量提交成功");
		return "redirect:/expensescreate";
	}

	private void setCommonData(User loginUser, Model model) {
		model.addAttribute("projectList", projectService.findUserProjects(loginUser.getId()));
		model.addAttribute("expensesTypeList", expensesTypeService.findExpensesType(1, Integer.MAX_VALUE).getList());
	}

	@RequiresPermissions("cost:expenses:create")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() {
		logger.debug("enter file upload");
		return "expenses/upload";
	}

	@RequiresPermissions("cost:expenses:create")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@CurrentUser User loginUser, Model model, MultipartFile myfile,
			RedirectAttributes redirectAttributes) throws Exception {
		if (myfile.isEmpty()) {
			logger.debug("file not upload");
			model.addAttribute("msg", "上傳失敗");
			return "expenses/upload";
		} else {
			expensesService.upload(myfile.getBytes(), loginUser);
			redirectAttributes.addFlashAttribute("msg", "上傳成功");
			return "redirect:/expensescreate";
		}
	}
}
