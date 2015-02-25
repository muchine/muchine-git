package com.lgu.abc.core.base.security;

import java.util.Collection;

import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;

public class NullRole extends AbstractRole implements Permissible {

	@Override
	public Privilege privilege(ResourceID assetId) {
		return null;
	}

	@Override
	public void add(Permissible permissible) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(Collection<Permissible> permissibles) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(Permissible permissible) {
		throw new UnsupportedOperationException();
	}

}
