package com.lgu.abc.core.web.interceptor.component;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.plugin.SortableComparator;
import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounter;
import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounterRegistry;
import com.lgu.abc.core.plugin.ui.notification.counter.view.NotificationCounterViewSet;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.session.SessionManager;

@Component
public final class NotificationCounterFilter implements InterceptorFilter {
	
	public static final String ATTRIBUTE = "counters";
		
	@Autowired
	private NotificationCounterFilter(ComponentInterceptor interceptor) {
		interceptor.add(this);
	}

	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = SessionManager.getSession(request);
		
		List<NotificationCounter> counters = NotificationCounterRegistry.enabled(user.getCompany());
		Collections.sort(counters, SortableComparator.instance());
		
		request.setAttribute(ATTRIBUTE, new NotificationCounterViewSet(counters, user));
		
		return true;
	}
	
	@Override
	public int order() {
		return 3;
	}

}
