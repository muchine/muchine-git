package com.lgu.abc.core.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.day.Day;
import com.lgu.abc.core.support.bundle.MessageBundleManager;

public class TimeUtils {
	
	private static final String ELAPSED_MINUTES = "time.elapsed.minutes";
	private static final String ELAPSED_HOURS	= "time.elapsed.hours";
	private static final String ELAPSED_DAYS	= "time.elapsed.days";
	
	public static String getCurrentMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date()).substring(0, 6);
	}
	
	public static Day getFirstDayOfMonth(String month) {
		return new Day(month + "01");
	}
	
	public static Day getLastDayOfMonth(String month) {
		Time time = getFirstDayOfMonth(month).toTime();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time.toDate());
		
		return new Day(month + String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
	}
		
	public static Day getFirstDayOfCurrentMonth() {
		return getFirstDayOfMonth(getCurrentMonth());
	}
	
	public static Day getLastDayOfCurrentMonth() {
		return getLastDayOfMonth(getCurrentMonth());
	}
	
	public static Day getFirstDayOfCurrentWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return new Day(formatter.format(calendar.getTime()).substring(0, 8));
	}
	
	public static Day getLastDayOfCurrentWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return new Day(formatter.format(calendar.getTime()).substring(0, 8));
	}
	
	/**
	 * returns elapsed time from the given time
	 * @param time
	 * @return elapsed time (seconds)
	 */
	public static long elapsed(Date time) {
		Validate.notNull(time, "given time is null.");
		
		Date now = new Date();
		return (now.getTime() - time.getTime()) / 1000;
	}
	
	public static String formatElapsed(Date time, Locale locale) {
		Validate.notNull(time, "given time is null.");
		Validate.notNull(locale, "locale is null.");
		
		long minutes = elapsed(time) / 60;
		
		if (minutes < 60) return minutes + MessageBundleManager.get(ELAPSED_MINUTES, locale);
		
		final long hours = minutes / 60;
		if (hours < 24) return hours + MessageBundleManager.get(ELAPSED_HOURS, locale);
		
		return (minutes / 1440) + MessageBundleManager.get(ELAPSED_DAYS, locale);
	}
	
	public static void main(String[] args) {
		System.out.println(TimeUtils.getLastDayOfMonth("201211"));
		
		System.out.println(TimeUtils.getFirstDayOfCurrentMonth());
		System.out.println(TimeUtils.getLastDayOfCurrentMonth());
		System.out.println(TimeUtils.getFirstDayOfCurrentWeek());
		System.out.println(TimeUtils.getLastDayOfCurrentWeek());
	}
	
}
