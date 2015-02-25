package com.lgu.abc.core.mybatis.handlers.type;

import org.apache.ibatis.type.MappedTypes;

import com.lgu.abc.core.common.vo.contact.domain.PhoneNumber;
import com.lgu.abc.core.support.formatter.PhoneNumberFormatter;

@MappedTypes(PhoneNumber.class)
public class PhoneNumberTypeHandler extends	SpringFormatterTypeHandler<PhoneNumber> {

	public PhoneNumberTypeHandler() {
		super(new PhoneNumberFormatter(), null);
	}

}
