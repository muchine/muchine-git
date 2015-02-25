package com.lgu.abc.core.common.code;

import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.common.vo.Code;

public class LabelType extends Code {

	public static final ClassId CLASS = new ClassId("99100");
	
	public static final String DEFAULT	= "1";
	public static final String USER		= "2";
			
	public LabelType() {
		this.classId = CLASS;
	}
	
	public LabelType(String code) {
		this();
		this.setCode(code);
	}
	
	public boolean isDefault() {
		return DEFAULT.equals(getCode());
	}
	
	public static LabelType defaultType() {
		return new LabelType(DEFAULT);
	}
	
	public static LabelType userType() {
		return new LabelType(USER);
	}
	
}
