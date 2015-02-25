package com.lgu.abc.core.common.infra.share.web;

import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.domain.Listable;
import com.lgu.abc.core.common.infra.share.Authority;

public @Data class AuthorityList implements Listable<Authority> {
	
	private List<Authority> entities;

}
