package com.demo.pashzo.musicdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 时间格式
 */
public class FormatTime {
	/**
	 * 时间格式
	 * 
	 * @param long
	 * @return 时间
	 */

	public static String formatTime(double sec) {
		int h = (int) sec / 3600;
		int m = (int) (sec % 3600) / 60;
		int s = (int) sec % 60;
		if (h == 0)
			return String.format("%02d:%02d", m, s);
		else
			return String.format("%d:%02d:%02d", h, m, s);
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date(time);
		return format.format(d1);
	}

	public static String getDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = new Date(time);
		return format.format(d1);
	}

	/**
	 * 日期格式字符串转换成时间戳
	 *
	 * @param date_str
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @param format
	 * @return
	 */
	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty())
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}
}
