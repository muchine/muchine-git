package com.lgu.abc.core.web.security.xss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class XssSanitizer {
	
	protected static final Log logger = LogFactory.getLog(XssSanitizer.class);
	
	public static String sanitize(String input) {
		logger.trace("sanitize param: " + input);
		return input == null ? null : Jsoup.clean(input, "", XssWhiteList.supported(), new Document.OutputSettings().prettyPrint(false));
	}
	
}
