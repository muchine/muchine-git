package com.lgu.abc.core.prototype.workspace.support;

import java.util.Comparator;

import com.lgu.abc.core.prototype.workspace.Workspace;

public class WorkspaceComparator implements Comparator<Workspace> {

	private static final WorkspaceComparator instance = new WorkspaceComparator();
	
	private WorkspaceComparator() {}
	
	@Override
	public int compare(Workspace o1, Workspace o2) {
		return o1.getName().compareTo(o2.getName());
	}
	
	public static WorkspaceComparator getInstance() {
		return instance;
	}

}
