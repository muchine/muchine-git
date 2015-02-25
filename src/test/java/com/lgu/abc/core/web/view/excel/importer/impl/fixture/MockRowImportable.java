package com.lgu.abc.core.web.view.excel.importer.impl.fixture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.base.transform.importer.RowImportable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.DataImportException;
import com.mchange.v1.util.ArrayUtils;

public class MockRowImportable implements RowImportable {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public boolean process(User actor, String[] row) throws DataImportException {
		logger.debug("actor: " + actor + ", row: " + ArrayUtils.toString(row));
		return true;
	}

	@Override
	public int getCellSize() {
		return 24;
	}
	
}
