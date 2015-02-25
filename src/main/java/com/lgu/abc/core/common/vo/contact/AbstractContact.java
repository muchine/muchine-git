package com.lgu.abc.core.common.vo.contact;

import lombok.Data;

import com.lgu.abc.core.common.vo.contact.domain.Email;
import com.lgu.abc.core.common.vo.contact.domain.PhoneNumber;

public @Data abstract class AbstractContact {

	private String address = "";
	
	private PhoneNumber phone = new PhoneNumber();
	
	private Email mail = new Email();
	
}
