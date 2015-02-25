package com.lgu.abc.core.base.transform.importer;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.DataImportException;

public interface RowImportable {

	boolean process(User actor, String[] row) throws DataImportException;
	
	int getCellSize();
		
}
