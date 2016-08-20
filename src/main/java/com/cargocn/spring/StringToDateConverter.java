package com.cargocn.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

	private static final DateFormat ACCEPT_DATE_FORMATS10 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final DateFormat ACCEPT_DATE_FORMATS20 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat ACCEPT_DATE_FORMATS30 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // 支持转换的日期格式

	private static final DateFormat ACCEPT_DATE_FORMATS11 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final DateFormat ACCEPT_DATE_FORMATS21 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final DateFormat ACCEPT_DATE_FORMATS31 = new SimpleDateFormat("yyyy/MM/dd HH:mm"); // 支持转换的日期格式

	private static final DateFormat ACCEPT_DATE_FORMATS12 = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat ACCEPT_DATE_FORMATS22 = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat ACCEPT_DATE_FORMATS32 = new SimpleDateFormat("yyyy/MM/dd"); // 支持转换的日期格式

	private static final DateFormat ACCEPT_DATE_FORMATS23 = new SimpleDateFormat("yyyy-MM");

	private static final DateFormat ACCEPT_DATE_FORMATS24 = new SimpleDateFormat("yyyy");

	public StringToDateConverter() {
	}

	@Override
	public Date convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}
		String dateString = source.trim();
		DateFormat df = null;
		if (dateString.length() == 19) {
			if (dateString.indexOf("/") == 2) {
				df = ACCEPT_DATE_FORMATS10;
			} else if (dateString.indexOf("/") == 4) {
				df = ACCEPT_DATE_FORMATS30;
			} else if (dateString.indexOf("-") == 4) {
				df = ACCEPT_DATE_FORMATS20;
			}
		} else if (dateString.length() == 16) {
			if (dateString.indexOf("/") == 2) {
				df = ACCEPT_DATE_FORMATS11;
			} else if (dateString.indexOf("/") == 4) {
				df = ACCEPT_DATE_FORMATS31;
			} else if (dateString.indexOf("-") == 4) {
				df = ACCEPT_DATE_FORMATS21;
			}
		} else if (dateString.length() == 10) {
			if (dateString.indexOf("/") == 2) {
				df = ACCEPT_DATE_FORMATS12;
			} else if (dateString.indexOf("/") == 4) {
				df = ACCEPT_DATE_FORMATS32;
			} else if (dateString.indexOf("-") == 4) {
				df = ACCEPT_DATE_FORMATS22;
			}
		} else if (dateString.length() == 7) {
			if (dateString.indexOf("-") == 4) {
				df = ACCEPT_DATE_FORMATS23;
			}
		} else if (dateString.length() == 4) {
			df = ACCEPT_DATE_FORMATS24;
		}
		if (df != null) {
			try {
				return df.parse(dateString);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
