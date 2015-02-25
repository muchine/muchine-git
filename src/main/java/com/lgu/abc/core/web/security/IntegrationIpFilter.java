package com.lgu.abc.core.web.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.common.domain.security.IpAccess;
import com.lgu.abc.core.exception.http.UnauthorizedException;
import com.lgu.abc.core.web.support.WebUtils;

/**
 * Filter access ip for internal integration. If a client sends a request from unallowable ip address, this filter 
 * returns 401 HTTP status code.
 * @author Sejoon Lim
 * @since 2014. 3. 18.
 *
 */
public class IntegrationIpFilter {
	
	protected static final Log logger = LogFactory.getLog(IntegrationIpFilter.class);
	
	public static void validate(HttpServletRequest request) throws UnauthorizedException {
		final String ip = WebUtils.ip(request);
		logger.info("accessing ip: " + ip);
		
		if (!isIntegratedIp(ip))
			throw new UnauthorizedException();
	}
	
	public static boolean isIntegratedIp(String ip) {
		List<IpAccess> ipAccesses = AbcConfig.instance().system().integration().ipAccesses();
		for (IpAccess access : ipAccesses) {
			if (access.allows(ip)) return true;
		}
		
		return false;
	}
	
}
