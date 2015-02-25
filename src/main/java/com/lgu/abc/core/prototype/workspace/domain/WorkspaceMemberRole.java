package com.lgu.abc.core.prototype.workspace.domain;

import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.common.vo.Code;

public class WorkspaceMemberRole extends Code {
	
	public static final ClassId CLASS = new ClassId("00704");
		
	public static final String NORMAL		= "1";
	public static final String ADMIN		= "2";
	public static final String SUB_ADMIN	= "3";
	public static final String LEADER		= "4";
	
	public WorkspaceMemberRole() {
		this.classId = CLASS;
	}
	
	public WorkspaceMemberRole(String code) {
		this();
		this.setCode(code);
	}
	
}
