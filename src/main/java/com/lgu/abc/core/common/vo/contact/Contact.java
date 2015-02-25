package com.lgu.abc.core.common.vo.contact;

import lombok.Data;

public @Data class Contact {

	private BusinessContact company = new BusinessContact();
	
	private PersonalContact personal = new PersonalContact();
	
	public Contact() {}
	
	public Contact(BusinessContact company, PersonalContact personal) {
		this.company = company;
		this.personal = personal;
	}
	
}
