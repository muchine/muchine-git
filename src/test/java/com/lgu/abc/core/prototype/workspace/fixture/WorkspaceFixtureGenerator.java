package com.lgu.abc.core.prototype.workspace.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.support.WorkspaceFinder;
import com.lgu.abc.test.common.base.fixture.SessionFactory;
import com.lgu.abc.test.support.fixture.FixtureGenerator;

@Component
public class WorkspaceFixtureGenerator implements FixtureGenerator<Workspace, Workspace> {
	
	private static final String WORKSPACE_ID = "55";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private WorkspaceFinder finder;

	@Override
	public Workspace generate() {
		return finder.findByWorkspaceId(WORKSPACE_ID);
	}

	@Override
	public Workspace generate(User user) {
		return generate();
	}

	@Override
	public void set(Workspace entity) {
		throw new UnsupportedOperationException();
	}

}
