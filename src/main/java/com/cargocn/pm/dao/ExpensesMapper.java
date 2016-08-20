package com.cargocn.pm.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cargocn.pm.bean.Expenses;
import com.cargocn.pm.bean.VoExpenses;

public interface ExpensesMapper {
	int deleteByPrimaryKey(Long expensesId);

	int insert(Expenses record);

	int insertSelective(Expenses record);

	Expenses selectByPrimaryKey(Long expensesId);

	int updateByPrimaryKeySelective(Expenses record);

	int updateByPrimaryKey(Expenses record);

	List<VoExpenses> findByUserId(Long userId);

	List<VoExpenses> findApproveByUserId(Map<String, Object> paras);

	BigDecimal sumProjectExpenses(Map<String, Object> paras);
}