package com.lgu.abc.core.common.vo.contact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.common.vo.contact.domain.PhoneNumber;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class PersonalContact extends AbstractContact {

	private PhoneNumber mobile = new PhoneNumber();
	
	private PhoneNumber other = new PhoneNumber();
	
}
