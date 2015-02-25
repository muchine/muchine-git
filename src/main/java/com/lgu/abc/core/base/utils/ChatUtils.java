package com.lgu.abc.core.base.utils;

import com.lgu.abc.core.common.configuration.AbcConfig;
import com.lgu.abc.core.prototype.org.company.Company;
import com.lgu.abc.core.prototype.org.user.User;

public class ChatUtils {

	public static String messengerId(User user) {
		return user.getEmail().replace("@", ".");
	}
	
	public static String jid(User user) {
		return messengerId(user) + "@" + xmppDomain(user.getCompany());
	}
	
	public static String loginIdFrom(String messengerId, String domain) {
		AbcConfig configuration = AbcConfig.instance();
		String suffix = "." + domain + "@" + configuration.messenger().domain();
		return new String(messengerId).replace(suffix, "");
	}
	
	public static String xmppDomain(Company company) {
		AbcConfig configuration = AbcConfig.instance();
		return configuration.messenger().domain();
	}
	
	public static String messengerDomainSuffix(Company company) {
		return "." + company.getDomain() + "@" + xmppDomain(company);
	}
	
}
