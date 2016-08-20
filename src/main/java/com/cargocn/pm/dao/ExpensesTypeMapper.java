package com.cargocn.pm.dao;

import java.util.List;

import com.cargocn.pm.bean.ExpensesType;

public interface ExpensesTypeMapper {
	int deleteByPrimaryKey(Long typeId);

	int insert(ExpensesType record);

	int insertSelective(ExpensesType record);

	ExpensesType selectByPrimaryKey(Long typeId);

	int updateByPrimaryKeySelective(ExpensesType record);

	int updateByPrimaryKey(ExpensesType record);

	List<ExpensesType> findAll();

	ExpensesType selectByTypeName(String typeName);
}