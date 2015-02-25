package com.lgu.abc.core.web.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Inet4Address;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.common.infra.error.Error;
import com.lgu.abc.core.common.infra.error.repo.ErrorRepository;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.web.session.SessionManager;
import com.lgu.abc.core.web.support.WebUtils;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
	
	private static final String MDC_KEY = "location";
	
	private final EntryPointLogger logger = new EntryPointLogger();
	
	private static String ip;
	
	@Autowired
	private ErrorRepository repository;
	
	public LoggingInterceptor() throws Exception {
		ip = Inet4Address.getLocalHost().getHostAddress();
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User session = SessionManager.getSession(request);
		if (session != null) MDC.put(MDC_KEY, getLogLocation(session));
		
		logger.logEntryPoint(request, ">>>>>>>> beginning of request : ");
		logger.logParameters(request);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (ex != null) {
			User user = SessionManager.getSession(request);
			logToDatabase(ex, WebUtils.domain(request), WebUtils.ip(request), user == null ? null : user.getId());
			logger.logException(ex);
		}
		
		logger.logEntryPoint(request, "<<<<<<<< end of request : ");
		MDC.remove(MDC_KEY);
	}
	
	private String getLogLocation(User user) {
		return user.getCompany().getDomain() + "/" + user.getLoginId();
	}
		
	private void logToDatabase(Exception ex, String domain, String clientIp, String userId) {
		StringWriter writer = new StringWriter();
		ex.printStackTrace(new PrintWriter(writer));
		
		repository.create(new Error(ip, writer.toString(), domain, clientIp, userId));
	}
	
}
