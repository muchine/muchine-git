package com.lgu.abc.core.plugin.proto.workspace;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;

/**
 * Represents a listener that listens the workspace creation event and initializes the workspace environment of the module. For example, 
 * workspace initialization can include creation of default label, workspace options, etc.
 * @author Sejoon Lim
 * @since 2014. 3. 10.
 *
 */
public interface WorkspaceInitializable {

	/**
	 * Initialize default workspace environment of the module for the given workspace.
	 * @param user the user who has created the workspace
	 * @param workspace the workspace that has been created
	 */
	void initialize(User user, Workspace workspace);
	
}
