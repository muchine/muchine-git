package com.lgu.abc.core.plugin.proto.workspace;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;

/**
 * Global implementation that contains all local implementation class of each module. The local implementation in each module should 
 * take the reference of this class via Spring bean injection and add itself in order to get workspace initialization event notification.
 * @author Sejoon Lim
 * @since 2014. 3. 10.
 *
 */
@Component
public class GlobalWorkspaceInitializer implements WorkspaceInitializable {

	private Set<WorkspaceInitializable> initializables = new HashSet<WorkspaceInitializable>(); 
	
	public synchronized void add(WorkspaceInitializable initializable) {
		initializables.add(initializable);
	}
	
	@Override
	public void initialize(User user, Workspace workspace) {
		for (WorkspaceInitializable initializable : initializables)
			initializable.initialize(user, workspace);
	}

}
