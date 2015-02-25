package com.lgu.abc.core.transport.mail;

public class MockMailAgentFactory {

	public static MailAgent create() {
		MockMailer mailer = new MockMailer();
		String[] tos = {
			"sejoonlim@lguplus.co.kr", "hyhjus@medialog.co.kr"
		};
		
		return new MailAgent(mailer, "systemadin@abc.onnet21.com", tos);
	}
	
}
