package com.lgu.abc.core.common.infra.share.web;

import com.lgu.abc.core.base.view.AbstractMetamodel;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.AuthorityQuery;

public class AuthorityMetamodel extends AbstractMetamodel<Authority, AuthorityQuery> {

	public static final String BASE_URL = "/common/share";
	
	AuthorityMetamodel() {
		super(Authority.class, AuthorityQuery.class, BASE_URL);
	}

}
