package com.lgu.abc.core.web.view.excel.importer.impl.fixture;

import java.util.List;

import com.lgu.abc.core.base.transform.importer.TabularImportable;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.view.base.exception.TabularDataImportException;

public class MockExceptionTabularImportable implements TabularImportable {

	private final int exceptionOnRowCount;
	
	public MockExceptionTabularImportable(int exceptionOnRowCount) {
		this.exceptionOnRowCount = exceptionOnRowCount;
	}
	
	@Override
	public int process(User actor, List<String[]> data) throws TabularDataImportException {
		for (int i = 0, n = data.size(); i < n; i++) {
			if (i == exceptionOnRowCount) throw new TabularDataImportException(new RuntimeException(), exceptionOnRowCount);
		}
		
		return data.size();
	}

}
