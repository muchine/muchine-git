package com.lgu.abc.core.common.vo.time.day;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.common.validation.DayValidator;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Anniversary extends AbstractDay {

	public Anniversary() {}
	
	public Anniversary(String date) {
		this(date, false);
	}
	
	public Anniversary(String date, boolean lunar) {
		super(date, lunar);
	}
	
	@Override
	public String parse(String date) {
		String parsed = super.parse(date);
		
		DayValidator validator = DayValidator.instance();
		return validator.isValid(parsed) ? parsed : null;
	}

	@Override
	public String getText() {
		if (isEmpty()) return "";
		
		StringBuilder builder = new StringBuilder();
		builder
			.append(getDate().substring(0, 4))
			.append(DELIMITER)
			.append(getDate().substring(4, 6))
			.append(DELIMITER)
			.append(getDate().substring(6, 8));
		
		return builder.toString();
	}
	
}
