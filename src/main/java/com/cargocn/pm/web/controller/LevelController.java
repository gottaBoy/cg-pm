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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cargocn.pm.DataTablesRequest;
import com.cargocn.pm.DataTablesResponse;
import com.cargocn.pm.bean.Level;
import com.cargocn.pm.bean.LevelCost;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.service.ILevelService;
import com.cargocn.pm.web.bind.annotation.CurrentUser;
import com.github.pagehelper.PageInfo;

/** 
 */
@Controller
@RequestMapping("/level")
public class LevelController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ILevelService levelService;

	@RequiresPermissions("system:level:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		return "userLevel/list";
	}

	@RequiresPermissions("system:level:view")
	@RequestMapping(value = "/j_list")
	public @ResponseBody DataTablesResponse<Level> jlist(Model model, String dtParaJson) {
		logger.debug(dtParaJson);

		DataTablesRequest req = DataTablesRequest.getInstance(dtParaJson);
		PageInfo<Level> page = levelService.findLevel(req.getPageNum(), req.getiDisplayLength());

		DataTablesResponse<Level> ret = new DataTablesResponse<Level>();
		ret.setData(page.getList());
		ret.setDraw(req.getsEcho() + 1);
		ret.setRecordsTotal(page.getTotal());
		ret.setRecordsFiltered(page.getTotal());
		return ret;
	}

	@RequiresPermissions("system:level:create")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		Level l = new Level();
		model.addAttribute("level", l);
		model.addAttribute("op", "新增");
		return "userLevel/edit";
	}

	@RequiresPermissions("system:level:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@CurrentUser User loginUser, Level level, RedirectAttributes redirectAttributes) {
		level.setOperateUser(loginUser.getUsername());
		level.setOperateTime(Calendar.getInstance().getTime());
		levelService.createLevel(level);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/level";
	}

	@RequiresPermissions("system:level:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("level", levelService.findOne(id));
		model.addAttribute("op", "修改");
		return "userLevel/edit";
	}

	@RequiresPermissions("system:level:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(@CurrentUser User loginUser, Level level, RedirectAttributes redirectAttributes)
			throws IOException {
		level.setOperateUser(loginUser.getUsername());
		level.setOperateTime(Calendar.getInstance().getTime());
		levelService.updateLevel(level);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/level";
	}

	@RequiresPermissions("system:level:cost")
	@RequestMapping(value = "/{id}/cost", method = RequestMethod.GET)
	public String showCostForm(@PathVariable("id") Long id, Model model) {
		Level level = levelService.findOne(id);
		LevelCost levelCost = new LevelCost();
		levelCost.setLevelId(id);
		model.addAttribute("level", level);
		model.addAttribute("levelCost", levelCost);
		model.addAttribute("levelCosts", levelService.findLevelCosts(id));

		return "userLevel/cost";
	}

	@RequiresPermissions("system:level:cost")
	@RequestMapping(value = "/{id}/cost", method = RequestMethod.POST)
	public String auth(@CurrentUser User loginUser, LevelCost levelCost, Boolean chgSheetCost,
			RedirectAttributes redirectAttributes) {

		levelCost.setOperateUser(loginUser.getUsername());
		levelCost.setOperateTime(Calendar.getInstance().getTime());
		levelService.createLevelCost(levelCost, chgSheetCost);
		redirectAttributes.addFlashAttribute("msg", "设定成功");
		return "redirect:/level";
	}

}
