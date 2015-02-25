package com.lgu.abc.core.plugin.ui.completer;

import java.util.List;

import com.lgu.abc.core.base.plugin.Sortable;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * This interface represents a data source for auto completion functionality on browsers.
 * @author Sejoon Lim
 * @since 2014. 10. 16.
 *
 */
public interface AutoCompleteDataSource extends Sortable {
	
	String id();

	List<AutoCompleteNode> nodes(User user);
	
}
