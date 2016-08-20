package com.cargocn.pm.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cargocn.pm.bean.Expenses;
import com.cargocn.pm.bean.Expenses.ExpensesStatus;
import com.cargocn.pm.bean.ExpensesType;
import com.cargocn.pm.bean.Project;
import com.cargocn.pm.bean.User;
import com.cargocn.pm.bean.VoExpenses;
import com.cargocn.pm.dao.ExpensesMapper;
import com.cargocn.pm.dao.ExpensesTypeMapper;
import com.cargocn.pm.dao.ProjectMapper;
import com.cargocn.pm.service.IExpensesService;
import com.cargocn.spring.StringToDateConverter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ExpensesServiceImpl implements IExpensesService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExpensesMapper expensesDao;
	@Autowired
	private ProjectMapper projectDao;
	@Autowired
	private ExpensesTypeMapper expensesTypeDao;

	@Override
	public PageInfo<VoExpenses> findExpenses(Long userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<VoExpenses> list = expensesDao.findByUserId(userId);
		PageInfo<VoExpenses> page = new PageInfo<VoExpenses>(list);
		logger.debug(page.toString());
		return page;
	}

	@Override
	public PageInfo<VoExpenses> findExpensesApprove(Long userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("userId", userId);
		paras.put("expensesStatus", ExpensesStatus.pending);
		List<VoExpenses> list = expensesDao.findApproveByUserId(paras);
		PageInfo<VoExpenses> page = new PageInfo<VoExpenses>(list);
		logger.debug(page.toString());
		return page;
	}

	@Override
	public Expenses findOne(Long expensesId) {
		return expensesDao.selectByPrimaryKey(expensesId);
	}

	@Override
	public void updateExpenses(Expenses e) {
		if (e.getExpensesStatus() == null)
			e.setExpensesStatus(ExpensesStatus.created);
		expensesDao.updateByPrimaryKey(e);
	}

	@Override
	public void createExpenses(Expenses e) {
		e.setExpensesStatus(ExpensesStatus.created);
		expensesDao.insert(e);
	}

	@Override
	public void batchSubmit(String username, Long[] selectedId) {
		if (selectedId != null) {
			for (Long id : selectedId) {
				Expenses e = findOne(id);
				if (e != null) {
					if (e.getExpensesStatus() == null || ExpensesStatus.created.equals(e.getExpensesStatus())) {
						e.setExpensesStatus(ExpensesStatus.pending);
						e.setOperateUser(username);
						e.setOperateTime(Calendar.getInstance().getTime());
						updateExpenses(e);
					}
				}
			}
		}
	}

	@Override
	public void batchApprove(String username, Long[] selectedId) {
		if (selectedId != null) {
			for (Long id : selectedId) {
				Expenses e = findOne(id);
				if (e != null) {
					if (e.getExpensesStatus() != null && ExpensesStatus.pending.equals(e.getExpensesStatus())) {
						e.setExpensesStatus(ExpensesStatus.confirmed);
						e.setOperateUser(username);
						e.setOperateTime(Calendar.getInstance().getTime());
						updateExpenses(e);
					}
				}
			}
		}
	}

	@Override
	public void batchReject(String username, Long[] selectedId) {
		if (selectedId != null) {
			for (Long id : selectedId) {
				Expenses e = findOne(id);
				if (e != null) {
					if (e.getExpensesStatus() != null && ExpensesStatus.pending.equals(e.getExpensesStatus())) {
						e.setExpensesStatus(ExpensesStatus.created);
						e.setOperateUser(username);
						e.setOperateTime(Calendar.getInstance().getTime());
						updateExpenses(e);
					}
				}
			}
		}
	}

	private static final String HeadHang = "行";
	private static final String HeadApplyDate = "申请日期";
	private static final String HeadApplyDate2 = "出差起止日期";
	private static final String HeadInvoiceDate = "发票日期";
	private static final String HeadApplyContent = "申请事由";
	private static final String HeadInvoiceAmount = "发票金额";
	private static final String HeadApplyAmount = "报销金额";
	private static final String HeadExpensesType = "费用类型";
	private static final String HeadProject = "项目名称/编号";
	private static final String HeadMemo = "备注";

	private static final String HeadString = HeadHang + ";" + HeadApplyDate + ";" + HeadApplyDate2 + ";"
			+ HeadInvoiceDate + ";" + HeadApplyContent + ";" + HeadInvoiceAmount + ";" + HeadApplyAmount + ";"
			+ HeadExpensesType + ";" + HeadProject + ";" + HeadMemo + ";";

	@Override
	public void upload(byte[] fb, User loginUser) throws IOException {
		if (fb != null && fb.length > 0) {
			ByteArrayInputStream bi = new ByteArrayInputStream(fb);
			Workbook wb = null;
			try {
				wb = WorkbookFactory.create(bi);

				Sheet sheet = wb.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				boolean headFound = false;
				boolean hasData = true;
				int rowCount = 0;
				Map<String, Integer> keyMap = new HashMap<String, Integer>();
				while (rows.hasNext() && hasData) {
					System.out.println("row " + (rowCount++));
					Row row = rows.next();
					if (!headFound) {
						// search head
						int headFoundCount = 0;
						for (int cellCount = 0; cellCount <= row.getLastCellNum(); cellCount++) {
							Cell cell = row.getCell(cellCount);
							if (cell != null) {
								String headName = getCellValue(cell);
								if (headName != null && !"".equals(headName) && HeadString.contains(headName)) {
									keyMap.put(headName, cellCount);
									headFoundCount++;
								}
							}
						}
						if (headFoundCount >= 5) {
							headFound = true;
							System.out.println("head found in row " + (rowCount - 1));
						}
					} else {
						Expenses e = new Expenses();
						if (keyMap.containsKey(HeadApplyDate) && keyMap.get(HeadApplyDate) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadApplyDate));
							String dateStr = getCellValue(cell);
							e.setApplyDate(new StringToDateConverter().convert(dateStr));
							if (e.getApplyDate() == null) {
								e.setApplyDate(Calendar.getInstance().getTime());
							}
						}
						if (keyMap.containsKey(HeadApplyDate2) && keyMap.get(HeadApplyDate2) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadApplyDate2));
							String dateStr = getCellValue(cell);
							e.setApplyDate(new StringToDateConverter().convert(dateStr));
							if (e.getApplyDate() == null) {
								e.setApplyDate(Calendar.getInstance().getTime());
							}
							cell = row.getCell(keyMap.get(HeadApplyDate2) + 1);
							dateStr = getCellValue(cell);
							e.setApplyDate2(new StringToDateConverter().convert(dateStr));
						}
						if (keyMap.containsKey(HeadInvoiceDate)
								&& keyMap.get(HeadInvoiceDate) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadInvoiceDate));
							String dateStr = getCellValue(cell);
							e.setInvoiceDate(new StringToDateConverter().convert(dateStr));
						}
						if (keyMap.containsKey(HeadApplyContent)
								&& keyMap.get(HeadApplyContent) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadApplyContent));
							e.setApplyContent(cell.getStringCellValue());
						}
						if (keyMap.containsKey(HeadInvoiceAmount)
								&& keyMap.get(HeadInvoiceAmount) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadInvoiceAmount));
							e.setInvoiceAmount(formatJiage(getCellValue(cell)));
						}
						if (keyMap.containsKey(HeadApplyAmount)
								&& keyMap.get(HeadApplyAmount) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadApplyAmount));
							e.setApplyAmount(formatJiage(getCellValue(cell)));
						}
						if (keyMap.containsKey(HeadExpensesType)
								&& keyMap.get(HeadExpensesType) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadExpensesType));
							String cv = cell.getStringCellValue();
							ExpensesType et = expensesTypeDao.selectByTypeName(cv);
							if (et == null) {
								et = expensesTypeDao.selectByTypeName("其他");
							}
							if (et != null) {
								e.setExpensesType(et.getTypeId());
							}
						}
						if (keyMap.containsKey(HeadProject) && keyMap.get(HeadProject) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadProject));
							if (cell != null) {
								cell.setCellType(Cell.CELL_TYPE_STRING);
								String cv = cell.getStringCellValue();
								Project p = projectDao.selectByProjectCode(cv);
								if (p == null) {
									p = projectDao.selectByProjectName(cv);
								}
								if (p != null) {
									e.setProjectId(p.getProjectId());
								}
							}
						}
						if (keyMap.containsKey(HeadMemo) && keyMap.get(HeadMemo) <= row.getLastCellNum()) {
							Cell cell = row.getCell(keyMap.get(HeadMemo));
							e.setMemo(cell.getStringCellValue());
						}

						// check end line
						if (e.getApplyAmount() == null) {
							hasData = false;
							System.out.println("end line in row " + (rowCount - 1));
						}
						if (e.getApplyAmount() != null && e.getApplyDate() != null && e.getProjectId() != null) {
							e.setExpensesStatus(ExpensesStatus.pending);
							e.setUserId(loginUser.getId());
							e.setOperateUser(loginUser.getUsername());
							e.setOperateTime(Calendar.getInstance().getTime());
							expensesDao.insert(e);
						}
					}
				}

			} catch (EncryptedDocumentException e1) {
				e1.printStackTrace();
			} catch (InvalidFormatException e1) {
				e1.printStackTrace();
			} finally {
				if (wb != null) {
					wb.close();
				}
				if (bi != null) {
					bi.close();
				}
			}
		}
	}

	private String getCellValue(Cell cell) {
		String content = null;
		try {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						content = df.format(date);
					}
				} else {
					content = String.valueOf(cell.getNumericCellValue());
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				content = String.valueOf(cell.getNumericCellValue());
			} else {
				content = cell.getStringCellValue();
			}
		} catch (Exception e) {
			content = null;
		}
		return content;
	}

	private BigDecimal formatJiage(String s) {
		if (s == null)
			return null;
		BigDecimal d = null;
		try {
			d = BigDecimal.valueOf(Double.parseDouble(s));
		} catch (Exception e) {
		}
		if (d == null)
			return null;
		DecimalFormat df = new DecimalFormat("00000000.00");
		String ss = df.format(d.doubleValue());
		return new BigDecimal(ss);
	}

	@Override
	public BigDecimal sumProjectExpenses(Long projectId, ExpensesStatus status) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("projectId", projectId);
		paras.put("expensesStatus", status);
		return expensesDao.sumProjectExpenses(paras);
	}

}
