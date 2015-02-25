package com.lgu.abc.core.plugin.proto.workspace;

public abstract class AbstractWorkspaceInitializer implements WorkspaceInitializable {

	protected AbstractWorkspaceInitializer(GlobalWorkspaceInitializer initializer) {
		initializer.add(this);
	}
	
}
