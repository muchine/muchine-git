package com.lgu.abc.core.common.vo.time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.lgu.abc.core.support.bundle.MessageBundleManager;

public enum DayOfWeek {

	SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7);
	
	private static final String BUNDLE_PREFIX = "common.date.";
	
	private int code;
	
	private DayOfWeek(int code) {
		this.code = code;
	}
	
	public int toInt() {
		return this.code;
	}
	
	public String text(Locale locale) {
		return MessageBundleManager.get(BUNDLE_PREFIX + toString().toLowerCase(), locale);
	}
	
	public static DayOfWeek value(int code) {
		for (DayOfWeek day : DayOfWeek.values())
			if (day.code == code) return day;
		
		return null;
	}
	
	public static List<DayOfWeek> list(DayOfWeek...days) {
		return Arrays.asList(days);		
	}
	
	public static List<DayOfWeek> businessDays() {
		return list(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
	}
	
	public static void main(String[] args) {
		List<DayOfWeek> days = new ArrayList<DayOfWeek>();
		
		days.add(THURSDAY);
		days.add(FRIDAY);
		days.add(MONDAY);
		days.add(SUNDAY);
		
		System.out.println(days);
		Collections.sort(days);
		System.out.println(days);
		
		System.out.println(DayOfWeek.THURSDAY.text(Locale.KOREAN));
	}
	
}
