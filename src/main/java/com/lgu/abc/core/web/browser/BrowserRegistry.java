package com.lgu.abc.core.web.browser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.web.browser.type.Chrome;
import com.lgu.abc.core.web.browser.type.Firefox;
import com.lgu.abc.core.web.browser.type.IE10;
import com.lgu.abc.core.web.browser.type.IE11;
import com.lgu.abc.core.web.browser.type.IE6;
import com.lgu.abc.core.web.browser.type.IE7;
import com.lgu.abc.core.web.browser.type.IE8;
import com.lgu.abc.core.web.browser.type.IE9;
import com.lgu.abc.core.web.browser.type.Safari;
import com.lgu.abc.core.web.support.WebUtils;

public class BrowserRegistry {

	private static final List<BrowserSpecification> specifications = new ArrayList<BrowserSpecification>();
	
	private static final BrowserSpecification[] unsupported = new BrowserSpecification[] {
		new IE6(), new IE7(), new IE9()
	};
	
	private static BrowserSpecification ieFamily;
	
	static {
		initialize();
	}
	
	public static List<BrowserSpecification> all() {
		return specifications;
	}
	
	public static boolean accept(HttpServletRequest request) {
		return accept(WebUtils.browser(request));
	}
	
	public static boolean accept(BrowserSpecification browser) {
		if (browser == null) return false;
		
		for (BrowserSpecification spec : unsupported) {
			if (browser.identical(spec)) return false;
		}
		
		return true;
	}
	
	public static BrowserSpecification ie() {
		return ieFamily;
	}
		
	private static void initialize() {
		specifications.add(new Chrome());
		specifications.add(new Firefox());
		specifications.add(new Safari());
		specifications.add(new IE11());
		specifications.add(new IE10());
		specifications.add(new IE9());
		specifications.add(new IE8());
		specifications.add(new IE7());
		specifications.add(new IE6());
		
		initializeIEFamily();
	}
	
	private static void initializeIEFamily() {
		Specification ie = new Chrome().or(new Firefox()).or(new Safari()).not();
		
		ieFamily = new CompositeBrowserSpecification(ie);
	}
	
}
