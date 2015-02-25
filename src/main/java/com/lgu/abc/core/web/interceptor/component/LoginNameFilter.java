package com.lgu.abc.core.web.interceptor.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.formatter.UserNameFormatter;
import com.lgu.abc.core.web.interceptor.InterceptorFilter;
import com.lgu.abc.core.web.session.SessionManager;

@Component
public class LoginNameFilter implements InterceptorFilter {

	public static final String ATTRIBUTE = "loginName";
	
	@Autowired
	private UserNameFormatter formatter;
	
	@Autowired
	private LoginNameFilter(ComponentInterceptor interceptor) {
		interceptor.add(this);
	}
	
	@Override
	public boolean process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = SessionManager.getSession(request);
		request.setAttribute(ATTRIBUTE, formatter.designate(user, user.getLocale()));
		return true;
	}

	@Override
	public int order() {
		return 4;
	}

}
