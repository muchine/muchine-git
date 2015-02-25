package com.lgu.abc.core.plugin.ui.notification.counter;

import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.plugin.ModulePluggable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * A representation of module notification counter on the top of web page. This interface is used to paint and control the counter
 * in notification-counters.jspf page. The module that wants to add its own counters needs to implement this interface and add a registry. 
 * @author Sejoon Lim
 * @since 2014. 3. 19.
 * @see com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounterRegistry
 *
 */
public interface NotificationCounter extends ModulePluggable {
	
	/**
	 * Return the title of notification counter. The title is a label of notification list layer which is shown when a user clicks a counter. 
	 * @return the title of notification counter
	 */
	Name title();
	
	/**
	 * Return the icon name of notification counter. The icon name is matched with style class of a main &lt;div&gt; tag.
	 * @return the icon name of notification counter
	 */
	String icon();
	
	/**
	 * Return the uri where a user is redirect to when clicking a "more" link. If null value is returned, "more" link is not shown.
	 * @return the redirection uri of counter
	 */
	String uri();
	
	/**
	 * Return the current notification count for the user
	 * @param actor the actor who requests count
	 * @return the notification count
	 */
	int count(User actor);
	
	/**
	 * Return the model and view for the list of notification content
	 * @param actor the actor who requests notification content
	 * @param maxContentCount the max number of notification content to be shown
	 * @return model and view for the content
	 */
	ModelAndView content(User actor, int maxContentCount);
		
}
