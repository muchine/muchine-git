package com.lgu.abc.core.plugin.ui.widget.loader;

import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * General interface that represents a widget that can be attached on the portal.  
 * @author Sejoon Lim
 * @since 2014. 3. 7.
 *
 */
public interface WidgetLoadable extends ModulePluggable {

	/**
	 * Load the widget with the given user information on the portal page.
	 * @param actor the user who requests the widget to be loaded
	 * @return the model and view of the widget
	 */
	ModelAndView load(User actor, String itemId);
	
}
