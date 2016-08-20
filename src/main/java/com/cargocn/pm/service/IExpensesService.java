package com.cargocn.pm.service;

import java.io.IOException;
import java.math.BigDecimal;

import com.cargocn.pm.bean.Expenses;
import com.cargocn.pm.bean.Expenses.ExpensesStatus;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoExpenses;
import com.github.pagehelper.PageInfo;

public interface IExpensesService {
	public PageInfo<VoExpenses> findExpenses(Long userId, int pageNum, int pageSize);

	public PageInfo<VoExpenses> findExpensesApprove(Long userId, int pageNum, int pageSize);

	public Expenses findOne(Long expensesId);

	public void updateExpenses(Expenses e);

	public void createExpenses(Expenses e);

	public void batchSubmit(String username, Long[] selectedId);

	public void batchApprove(String username, Long[] selectedId);

	public void batchReject(String username, Long[] selectedId);

	public void upload(byte[] fb, User loginUser) throws IOException;

	public BigDecimal sumProjectExpenses(Long projectId, ExpensesStatus status);

}
