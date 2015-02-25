package com.lgu.abc.core.mybatis.handlers.type;

import org.apache.ibatis.type.MappedTypes;

import com.lgu.abc.core.common.vo.Size;

@MappedTypes(Size.class)
public class SizeTypeHandler extends AbstractTypeHandler<Size> {

	@Override
	protected String print(Size object) {
		return String.valueOf(object.bytes());
	}

	@Override
	protected Size parse(String text) {
		return new Size(Long.parseLong(text));
	}

}
