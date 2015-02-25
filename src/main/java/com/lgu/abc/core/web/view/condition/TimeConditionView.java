package com.lgu.abc.core.web.view.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.Data;

import com.lgu.abc.core.common.vo.time.Time;

public @Data class TimeConditionView {

	private final List<ConditionItem> hours = new ArrayList<ConditionItem>();
	
	private final List<ConditionItem> minutes = new ArrayList<ConditionItem>();
	
	public TimeConditionView(Locale locale) {
		this(locale, new Time());
	}
	
	public TimeConditionView(Locale locale, Time time) {
		for (int i = 0; i < 24; i++) {
			String padding = i < 10 ? "0" : "";
			String code = padding + String.valueOf(i);
			hours.add(new ConditionItem(code, code, i == Integer.parseInt(time.hours())));
		}
		
		int minute = Integer.parseInt(time.minutes());
		minutes.add(new ConditionItem("00", "00", minute >= 0 && minute < 30));
		minutes.add(new ConditionItem("30", "30", minute >= 30 && minute < 60));
	}
	
}
