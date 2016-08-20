package com.cargocn.pm.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.Timesheet;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.service.IProjectService;
import com.cargocn.pm.service.ITimesheetService;
import com.cargocn.pm.service.impl.DateHelper;
import com.cargocn.pm.web.bind.annotation.CurrentUser;
import com.cargocn.spring.StringToDateConverter;

/** 
 */
@Controller
@RequestMapping("/timesheetcreate")
public class TimesheetController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ITimesheetService timesheetService;
	@Autowired
	private IProjectService projectService;

	@RequiresPermissions("cost:timesheet:create")
	@RequestMapping(method = RequestMethod.GET)
	public String showCreateForm(@CurrentUser User loginUser, HttpServletRequest request, Timesheet timesheet,
			Model model) {
		model.addAttribute("timesheet", timesheet);
		logger.debug("date " + timesheet.getTsDate());
		if (timesheet.getTsDate() != null) {

			List<Project> ps = projectService.findUserProjects(loginUser.getId());
			if (ps == null || ps.size() == 0) {
				model.addAttribute("msg", "填报成功");
			} else {

				// init datagrid
				String[] cols = new String[5];
				Date[] colDates = new Date[5];
				SimpleDateFormat df = new SimpleDateFormat("MM-dd");
				Date d1 = DateHelper.getWeekBegin(timesheet.getTsDate());
				cols[0] = df.format(d1);
				colDates[0] = d1;
				Calendar c = Calendar.getInstance();
				c.setTime(d1);
				c.add(Calendar.DATE, 1);
				cols[1] = df.format(c.getTime());
				colDates[1] = c.getTime();
				c.add(Calendar.DATE, 1);
				cols[2] = df.format(c.getTime());
				colDates[2] = c.getTime();
				c.add(Calendar.DATE, 1);
				cols[3] = df.format(c.getTime());
				colDates[3] = c.getTime();
				c.add(Calendar.DATE, 1);
				cols[4] = df.format(c.getTime());
				colDates[4] = c.getTime();

				request.setAttribute("cols", cols);
				logger.debug("cols " + cols);

				// gen json
				String tableJson = "[";
				int i = 0;
				for (Project p : ps) {
					i++;
					if (i > 1)
						tableJson += ",";
					tableJson += "{\"col0\":\"" + p.getProjectName() + "\"";
					tableJson += ",\"colid\":" + p.getProjectId();
					for (int j = 0; j < colDates.length; j++) {
						Timesheet t1 = timesheetService.findOne(loginUser.getId(), p.getProjectId(), colDates[j]);
						if (t1 != null && t1.getTsHour() != null) {
							tableJson += ",\"colm" + j + "\":" + t1.getTsHour() + ",\"cols" + j + "\":" + "\""
									+ t1.getTsStatus() + "\"";
						} else {
							tableJson += ",\"colm" + j + "\":" + "\"\"";
						}
					}
					tableJson += "}";
				}
				tableJson += "]";

				logger.debug("tableJson:" + tableJson);
				request.setAttribute("tableJson", tableJson);
			}
		}
		return "timesheet/edit";
	}

	@RequiresPermissions("cost:timesheet:create")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@CurrentUser User loginUser, String tsDate, String updated, Model model) {
		List<Timesheet> tl = processEasyuiData(loginUser, tsDate, updated);
		timesheetService.saveTimesheet(tl, loginUser);
		model.addAttribute("msg", "填报成功");
		return "timesheet/success";
	}

	private List<Timesheet> processEasyuiData(User loginUser, String tsDate, String updated) {
		List<Timesheet> tl = new ArrayList<Timesheet>();
		if (updated != null && !"".equals(updated)) {
			try {
				String decodedUpdated = URLDecoder.decode(updated, "UTF-8");
				logger.debug("updated " + decodedUpdated);
				logger.debug("tsDate " + tsDate);
				Date dTsDate = new StringToDateConverter().convert(tsDate);
				JSONArray ja = JSON.parseArray(decodedUpdated);
				if (ja != null) {

					for (int i = 0; i < ja.size(); i++) {
						JSONObject jo = ja.getJSONObject(i);
						Long projectId = jo.getLong("colid");
						Set<String> cols = jo.keySet();
						for (String col : cols) {
							if (jo.containsKey(col) && jo.get(col) != null && !"".equals(jo.getString(col))
									&& col.startsWith("colm") && col.length() > 4) {
								Long weekDay = Long.valueOf(col.substring(4));
								Date monsday = DateHelper.getWeekBegin(dTsDate);
								Calendar c = Calendar.getInstance();
								c.setTime(monsday);
								c.add(Calendar.DATE, weekDay.intValue());
								double gridValue = jo.getDoubleValue(col);
								logger.debug("projectId " + projectId + " date " + c.getTime() + " value " + gridValue);
								Timesheet t = new Timesheet();
								t.setOperateTime(Calendar.getInstance().getTime());
								t.setOperateUser(loginUser.getUsername());
								t.setProjectId(projectId);
								t.setTsDate(c.getTime());
								t.setTsHour(BigDecimal.valueOf(gridValue));
								tl.add(t);
							}
						}
					}

				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return tl;
	}

	@RequiresPermissions("cost:timesheet:pending")
	@RequestMapping(value = "/pending", method = RequestMethod.POST)
	public String pending(@CurrentUser User loginUser, String tsDate, String updated, Model model) {
		List<Timesheet> tl = processEasyuiData(loginUser, tsDate, updated);
		timesheetService.pendingTimesheet(tsDate, tl, loginUser);
		model.addAttribute("msg", "填报成功");
		return "timesheet/success";
	}

}
