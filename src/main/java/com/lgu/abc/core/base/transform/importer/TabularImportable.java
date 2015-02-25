package com.lgu.abc.core.base.transform.importer;

import java.util.List;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.TabularDataImportException;

public interface TabularImportable {

	int process(User actor, List<String[]> data) throws TabularDataImportException;
	
}
