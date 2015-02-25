package com.lgu.abc.core.web.view.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.Data;

import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.support.bundle.MessageBundleManager;

public @Data class MonthConditionView {

	private static final int INITIAL_YEAR = 2011;
	
	private final List<ConditionItem> years = new ArrayList<ConditionItem>();
	
	private final List<ConditionItem> months = new ArrayList<ConditionItem>();
	
	public MonthConditionView(Locale locale) {
		Time now = new Time();
		int currentYear = Integer.parseInt(now.year());
		for (int i = INITIAL_YEAR; i <= currentYear; i++) {
			String code = String.valueOf(i);
			String name = code + MessageBundleManager.get("common.date.year", locale); 
					
			years.add(new ConditionItem(code, name, i == currentYear));
		}
		
		int currentMonth = Integer.parseInt(now.month());
		for (int i = 1; i < 13; i++) {
			String padding = i < 10 ? "0" : "";
			String code = padding + String.valueOf(i);
			String name = code + MessageBundleManager.get("common.date.month", locale); 
			months.add(new ConditionItem(code, name, i == currentMonth));
		}
	}
	
}
