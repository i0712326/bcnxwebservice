package com.bcnx.web.app.utility;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UtilityService {
	public static Date str2Date(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date date = formatter.parse(str);
			return new Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Date str2Date2(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		try {
			java.util.Date date = formatter.parse(str);
			return new Date(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String date2Str(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		java.util.Date dd = new java.util.Date(date.getTime());
		return format.format(dd);
	}
	public static List<String> dates2Strs(List<Date> dates){
		List<String> list = new ArrayList<String>();
		for(Date date : dates){
			String dd = date2Str(date);
			list.add(dd);
		}
		return list;
	}
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		return new java.sql.Date(cal.getTime().getTime());
	}
	public static Date getBackDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return new java.sql.Date(cal.getTime().getTime());
	}
}
