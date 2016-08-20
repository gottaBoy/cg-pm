package com.cargocn.pm.web.controller;

import java.io.IOException;

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
import com.cargocn.pm.bean.ExpensesType;
import com.cargocn.pm.service.IExpensesTypeService;
import com.github.pagehelper.PageInfo;

/** 
 */
@Controller
@RequestMapping("/expensestype")
public class ExpensesTypeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IExpensesTypeService expensesTypeService;

	@RequiresPermissions("system:expensestype:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "expenses/listType";
	}

	@RequiresPermissions("system:expensestype:view")
	@RequestMapping(value = "/j_list")
	public @ResponseBody DataTablesResponse<ExpensesType> jlist(Model model, String dtParaJson) {
		logger.debug(dtParaJson);

		DataTablesRequest req = DataTablesRequest.getInstance(dtParaJson);
		PageInfo<ExpensesType> page = expensesTypeService.findExpensesType(req.getPageNum(), req.getiDisplayLength());

		DataTablesResponse<ExpensesType> ret = new DataTablesResponse<ExpensesType>();
		ret.setData(page.getList());
		ret.setDraw(req.getsEcho() + 1);
		ret.setRecordsTotal(page.getTotal());
		ret.setRecordsFiltered(page.getTotal());
		return ret;
	}

	@RequiresPermissions("system:expensestype:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		ExpensesType t = new ExpensesType();
		model.addAttribute("expensesType", t);
		model.addAttribute("op", "新增");
		return "expenses/editType";
	}

	@RequiresPermissions("system:expensestype:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(ExpensesType t, RedirectAttributes redirectAttributes) {
		expensesTypeService.createExpensesType(t);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/expensestype";
	}

	@RequiresPermissions("system:expensestype:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("expensesType", expensesTypeService.findOne(id));
		model.addAttribute("op", "修改");
		return "expenses/editType";
	}

	@RequiresPermissions("system:expensestype:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(ExpensesType t, RedirectAttributes redirectAttributes) throws IOException {
		expensesTypeService.updateExpensesType(t);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/expensestype";
	}

}
