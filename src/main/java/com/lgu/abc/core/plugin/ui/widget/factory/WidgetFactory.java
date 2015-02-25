package com.lgu.abc.core.plugin.ui.widget.factory;

import java.util.List;

import com.lgu.abc.core.module.Module;
import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.workspace.Workspace;

public interface WidgetFactory {

	List<Widget> create(User user);
	
	List<Widget> create(Workspace workspace);
	
	Module module(Company company);
	
}
