package com.cargocn.pm.service;

import com.cargocn.pm.bean.ExpensesType;
import com.github.pagehelper.PageInfo;

public interface IExpensesTypeService {
	public PageInfo<ExpensesType> findExpensesType(int pageNum, int pageSize);

	public ExpensesType findOne(Long typeId);

	public void updateExpensesType(ExpensesType t);

	public void createExpensesType(ExpensesType t);

}
