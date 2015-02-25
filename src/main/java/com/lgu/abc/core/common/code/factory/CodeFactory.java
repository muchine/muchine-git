package com.lgu.abc.core.common.code.factory;

import com.lgu.abc.core.common.code.vo.CodeEntity;
import com.lgu.abc.core.common.vo.Code;

public interface CodeFactory {

	Code create(CodeEntity code);
	
}
