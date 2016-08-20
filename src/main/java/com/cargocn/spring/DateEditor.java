package com.cargocn.spring;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditor extends PropertyEditorSupport {

	public void setAsText(String value) {
		setValue((new StringToDateConverter()).convert(value));
	}

	public String getAsText() {
		String s = "";
		if (getValue() != null) {
			s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) getValue());
		}
		return s;
	}
}