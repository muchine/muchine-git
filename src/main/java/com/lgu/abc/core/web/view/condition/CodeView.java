package com.lgu.abc.core.web.view.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

import com.lgu.abc.core.common.vo.Code;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class CodeView {

	private final List<ConditionItem> codes = new ArrayList<ConditionItem>();
	
	public CodeView(String selectedCode, User actor, List<Code> codes) {
		for (Code code : codes) {
			String name = code.getName().getValue(actor.getLocale());
			boolean selected = selectedCode.equals(code.getCode());
			
			this.codes.add(new ConditionItem(code.getCode(), name, selected));
		}
	}
	
	public CodeView(String selectedCode, User actor, Code... codes) {
		this(selectedCode, actor, Arrays.asList(codes));
	}
	
}
