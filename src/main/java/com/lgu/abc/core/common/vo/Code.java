package com.lgu.abc.core.common.vo;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

import com.lgu.abc.core.common.code.CodeRegistry;
import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.exception.common.InvalidAccessException;

@ToString(includeFieldNames=true)
public class Code {

	public Code() {}
		
	public Code(Code code) {
		this(code.classId, code.code, code.name);
	}
		
	public Code(ClassId classId, String code, Name name) {
		this.classId = classId;
		this.code = code;
		this.name = name;
	}
	
	protected ClassId classId;
	
	@Getter
	private String code;
	
	@Getter
	private Name name;
	
	public final void setCode(String code) {
		if (this.code != null) 
			throw new InvalidAccessException("Can't set code because already has a value. Value: " + this.code);
		
		this.clone(this.resolve(code));
	}
	
	protected void clone(Object entity) {
		if (entity == null) return;
		if (!Code.class.isAssignableFrom(entity.getClass())) return;
		
		Code casted = (Code) entity;
		
		this.code = casted.code;
		this.name = casted.name;
	}
	
	private Code resolve(String code) {
		List<Code> codes = CodeRegistry.codes(this.classId);
		if (codes == null)
			throw new IllegalStateException("Can't resolve name. Codes are not initialized.");
			
		for (Code element : codes) {
			if (element.getCode().equals(code)) return element;
		}
		
		throw new IllegalArgumentException("Invalid code: " + code);
	}
	
	public boolean equals(Object code) {
		if (code == null) return false;
		if (!Code.class.isAssignableFrom(code.getClass())) return false;
		
		Code casted = (Code) code;
		
		return this.code != null && this.code.equals(casted.getCode());
	}
	
}
