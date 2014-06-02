package com.report.manage.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	public Object initCap(String str) {
		if (str != null && str != "")
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		else
			return str;
	}

	public String convertDateToString(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		String reportDate = df.format(date);
		return reportDate;
	}

}
