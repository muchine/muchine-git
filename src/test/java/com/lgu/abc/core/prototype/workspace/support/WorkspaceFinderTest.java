package com.lgu.abc.core.prototype.workspace.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.fixture.WorkspaceFixtureGenerator;
import com.lgu.abc.core.prototype.workspace.support.WorkspaceFinder;
import com.lgu.abc.test.common.base.BaseTest;

public class WorkspaceFinderTest extends BaseTest {
	
	@Autowired
	private WorkspaceFinder finder;
	
	@Autowired
	private WorkspaceFixtureGenerator generator;
	
	@Test
	public void testFindByWorkspaceId() {
		Workspace workspace = generator.generate();
		
		Workspace found = finder.findByWorkspaceId(workspace.getId());
		logger.debug("found: " + found);
		assertThat(found.identical(workspace), is(true));
	}

}
