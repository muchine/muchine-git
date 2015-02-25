package com.lgu.abc.core.web.mobile;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ROW")
public class MobileActionResult extends MobileItemView {

	@XStreamAlias("RESULT")
	private final String code;
	
	@XStreamAlias("MSG")
	private final String message;
	
	public MobileActionResult(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
