package com.lgu.abc.core.common.infra.file.service;

import com.lgu.abc.core.base.service.DomainService;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.FileQuery;
import com.lgu.abc.core.prototype.org.user.User;

public interface FileService extends DomainService<File, FileQuery> {

	void shrink(User user);
	
}
