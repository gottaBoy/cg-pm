package com.cargocn.pm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期开始 开始日期0:0:0 包含开始日期 比较是使用>= 日期结束，为结束后一天的 0:0:0 不包含结束日期 比较时使用<
 * 
 * @author alex
 *
 */
public class DateHelper {
	public static Date getWeekBegin() {
		return getWeekBegin(Calendar.getInstance().getTime());
	}

	public static Date getWeekBegin(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		int dw = c.get(Calendar.DAY_OF_WEEK);
		switch (dw) {
		case Calendar.MONDAY:
			break;
		case Calendar.TUESDAY:
			c.add(Calendar.DATE, -1);
			break;
		case Calendar.WEDNESDAY:
			c.add(Calendar.DATE, -2);
			break;
		case Calendar.THURSDAY:
			c.add(Calendar.DATE, -3);
			break;
		case Calendar.FRIDAY:
			c.add(Calendar.DATE, -4);
			break;
		case Calendar.SATURDAY:
			c.add(Calendar.DATE, -5);
			break;
		case Calendar.SUNDAY:
			c.add(Calendar.DATE, -6);
			break;
		}
		return c.getTime();
	}

	public static Date getWeekEnd() {
		return getWeekEnd(Calendar.getInstance().getTime());
	}

	public static Date getWeekEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		int dw = c.get(Calendar.DAY_OF_WEEK);
		switch (dw) {
		case Calendar.MONDAY:
			c.add(Calendar.DATE, 7);
			break;
		case Calendar.TUESDAY:
			c.add(Calendar.DATE, 6);
			break;
		case Calendar.WEDNESDAY:
			c.add(Calendar.DATE, 5);
			break;
		case Calendar.THURSDAY:
			c.add(Calendar.DATE, 4);
			break;
		case Calendar.FRIDAY:
			c.add(Calendar.DATE, 3);
			break;
		case Calendar.SATURDAY:
			c.add(Calendar.DATE, 2);
			break;
		case Calendar.SUNDAY:
			c.add(Calendar.DATE, 1);
			break;
		}
		return c.getTime();
	}

	public static Date getMonthBegin() {
		return getMonthBegin(Calendar.getInstance().getTime());
	}

	public static Date getMonthBegin(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getMonthEnd() {
		return getMonthEnd(Calendar.getInstance().getTime());
	}

	public static Date getMonthEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getYearBegin() {
		return getYearBegin(Calendar.getInstance().getTime());
	}

	public static Date getYearBegin(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getYearEnd() {
		return getYearEnd(Calendar.getInstance().getTime());
	}

	public static Date getYearEnd(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.YEAR, 1);
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getQuarterBegin(Date d, int quarter) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, (quarter - 1) * 3);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getQuarterBegin(Date d) {
		return getQuarterBegin(d, getQuarter(d));
	}

	public static int getQuarter(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int month = c.get(Calendar.MONTH);
		int quarter = (month / 3) + 1;
		return quarter;
	}

	public static Date getQuarterEnd(Date d) {
		return getQuarterEnd(d, getQuarter(d));
	}

	public static Date getQuarterEnd(Date d, int quarter) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MONTH, quarter * 3);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static boolean isMonday(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
	}

	public static void main(String[] args) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("getMonthBegin " + sd.format(getMonthBegin()));
		System.out.println("getMonthEnd " + sd.format(getMonthEnd()));
		System.out.println("getQuarterBegin "
				+ sd.format(getQuarterBegin(new Date())));
		System.out.println("getQuarterEnd "
				+ sd.format(getQuarterEnd(new Date())));
		System.out.println("getWeekBegin " + sd.format(getWeekBegin()));
		System.out.println("getWeekEnd " + sd.format(getWeekEnd()));
		System.out.println("getYearBegin " + sd.format(getYearBegin()));
		System.out.println("getYearEnd " + sd.format(getYearEnd()));
	}

}
