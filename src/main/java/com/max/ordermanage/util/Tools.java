package com.max.ordermanage.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

	// 构造模糊查询字符串
	public static String fuzzyKeyword(String keyword) {
		return "%" + keyword + "%";
	}
	
	// 字符串转日期
	public static Date stringToDate(String string) {
		DateFormat dateFormat = new SimpleDateFormat(Constants.Date_Format);
		try {
			return dateFormat.parse(string);
		} catch (ParseException e) {
			return null;
		}
	}
	
	// 日期转字符串
	public static String dateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(Constants.Date_Format);
		return dateFormat.format(date);
	}
	
	// 页码和页长判断
	public static boolean verifyPage(int currentPage, int pageSize) {
		// 判断页码信息正确性
		if (currentPage <= 0 || pageSize < 0) {
			return false;
		}
		return true;
	}
	
	// 调整页码，从零开始算起
	public static int adjustPageNum(int currentPage) {
		return currentPage - 1;
	}
}
