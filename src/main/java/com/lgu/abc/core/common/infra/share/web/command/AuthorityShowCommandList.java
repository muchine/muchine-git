package com.lgu.abc.core.common.infra.share.web.command;

import java.util.List;

import lombok.Data;

import com.lgu.abc.core.base.domain.Listable;

public @Data class AuthorityShowCommandList implements Listable<AuthorityShowCommand> {

	private List<AuthorityShowCommand> entities;

}
