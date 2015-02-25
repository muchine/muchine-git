package com.lgu.abc.core.prototype.org.user.support;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.lgu.abc.core.prototype.org.user.UserQuery;

public @Data class UserSearcher {

	@NotNull
	private String text;
		
	private String companyId;
	
	private String departmentId;
	
	public UserSearcher() {}
	
	public UserSearcher(String text, String companyId) {
		this.text = text;
		this.companyId = companyId;
	}
	
	public UserQuery query() {
		Validate.notNull(text, "text is null.");
		Validate.notNull(companyId, "company id is null.");
		
		UserQueryBuilder builder = new UserQueryBuilder()
			.company(companyId)
			.stopped(false)
			.search(text);
		
		if (!StringUtils.isEmpty(departmentId)) builder.department(departmentId);
		
		return builder.build();
	}
	
}
