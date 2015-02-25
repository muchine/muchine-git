package com.lgu.abc.core.base.security;

import com.lgu.abc.core.base.security.visitor.PermissibleVisitor;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.vo.id.ResourceID;

public interface Permissible {

	Privilege privilege(ResourceID assetId);
	
	void accept(PermissibleVisitor visitor);
	
}
