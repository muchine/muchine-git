package com.lgu.abc.core.plugin.session.destroy;

import javax.servlet.http.HttpSession;

public interface SessionFinalizable {

	void finalize(HttpSession session);
	
}
