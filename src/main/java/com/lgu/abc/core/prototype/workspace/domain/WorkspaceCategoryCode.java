package com.lgu.abc.core.prototype.workspace.domain;

import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.common.vo.Code;

public class WorkspaceCategoryCode extends Code {

	public static final ClassId CLASS = new ClassId("00701");
	
	public static final String TEAM		= "1";
	public static final String PROJECT	= "2";
	public static final String CLUB		= "3";
	
	public WorkspaceCategoryCode() {
		this.classId = CLASS;
	}
	
	public WorkspaceCategoryCode(String code) {
		this();
		setCode(code);
	}
	
}
