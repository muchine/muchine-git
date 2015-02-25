package com.lgu.abc.core.common.code;

import java.util.List;
import java.util.Random;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.common.code.vo.ClassId;
import com.lgu.abc.core.common.vo.Code;
import com.lgu.abc.core.common.vo.Name;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Color extends Code{
	
	public static final ClassId CLASS = new ClassId("99001");
	
	private static final String DEFAULT_COLOR = "01";  
	
	private static final Random random = new Random();
	
	public Color() {
		this.classId = CLASS;
	}
	
	public Color(String code) {
		this();
		setCode(code);
	}
	
	public Color(String code, Name name, String rgb) {
		super(CLASS, code, name);
		this.rgb = rgb;
	}
	
	private String rgb;

	@Override
	protected void clone(Object entity) {
		super.clone(entity);
		
		if (entity instanceof Color) 
			this.rgb = ((Color) entity).rgb;
	}
	
	public static Color random() {
		List<Code> codes = CodeRegistry.codes(CLASS);

		int index = random.nextInt(codes.size() - 1);
		return (Color) codes.get(index);
	}
	
	public static Color fromCode(String code) {
		return (Color) CodeRegistry.code(CLASS, code);
	}
	
	public static Color init() {
		return (Color) CodeRegistry.code(CLASS, DEFAULT_COLOR);
	}
	
	public static void main(String[] args) {
		Random rand = new Random();
		int random = rand.nextInt(30); 
		System.out.println(random);
	}
	
}
