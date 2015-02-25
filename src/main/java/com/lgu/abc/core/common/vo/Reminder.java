package com.lgu.abc.core.common.vo;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

public @Data class Reminder {

	@Length(min = 2, max = 2)
	private String pre;
	
	@Length(min = 2, max = 2)
	private String post;
	
	public void setPre(String pre) {
		if (pre != null && pre.length() != 2)
			throw new IllegalArgumentException("Length of pre-reminder should be 2.");
		
		this.pre = pre;
	}
	
	public void setPost(String post) {
		if (post != null && post.length() != 2)
			throw new IllegalArgumentException("Length of post-reminder should be 2.");
		
		this.post = post;
	}
}
