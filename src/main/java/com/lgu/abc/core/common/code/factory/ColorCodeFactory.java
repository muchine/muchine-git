package com.lgu.abc.core.common.code.factory;

import com.lgu.abc.core.common.code.Color;
import com.lgu.abc.core.common.code.vo.CodeEntity;
import com.lgu.abc.core.common.vo.Code;
import com.lgu.abc.core.common.vo.Name;

public class ColorCodeFactory implements CodeFactory {
	
	@Override
	public Code create(CodeEntity code) {
		return new Color(code.getCode(), Name.create(code.getName()), code.getDescription());
	}
	
}
