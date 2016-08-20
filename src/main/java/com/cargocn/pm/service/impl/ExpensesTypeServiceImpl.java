package com.cargocn.pm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.ExpensesType;
import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.ProjectUserKey;
import com.cargocn.pm.dao.ExpensesTypeMapper;
import com.cargocn.pm.service.IExpensesTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ExpensesTypeServiceImpl implements IExpensesTypeService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExpensesTypeMapper expensesTypeDao;

	@Override
	public PageInfo<ExpensesType> findExpensesType(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ExpensesType> list = expensesTypeDao.findAll();
		PageInfo<ExpensesType> page = new PageInfo<ExpensesType>(list);
		logger.debug(page.toString());
		return page;
	}

	@Override
	public ExpensesType findOne(Long typeId) {
		return expensesTypeDao.selectByPrimaryKey(typeId);
	}

	@Override
	public void updateExpensesType(ExpensesType t) {
		expensesTypeDao.updateByPrimaryKey(t);
	}

	@Override
	public void createExpensesType(ExpensesType t) {
		expensesTypeDao.insert(t);
	}

}
