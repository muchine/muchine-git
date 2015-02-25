package com.lgu.abc.core.common.vo.contact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.common.vo.contact.domain.PhoneNumber;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class BusinessContact extends AbstractContact {

	private String extension;
	
	private PhoneNumber fax = new PhoneNumber();
	
}
