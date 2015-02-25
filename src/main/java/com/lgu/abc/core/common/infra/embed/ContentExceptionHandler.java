package com.lgu.abc.core.common.infra.embed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgu.abc.core.base.domain.action.ActionResult;
import com.lgu.abc.core.base.domain.action.ActionResult.ResultType;
import com.lgu.abc.core.plugin.ui.feed.exception.ContentException;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.support.bundle.MessageBundleManager;
import com.lgu.abc.core.web.support.WebUtils;

public class ContentExceptionHandler {

	public ActionResult handle(ContentException e, HttpServletRequest request, HttpServletResponse response, User actor) 
    		throws Exception {
		if (!WebUtils.isAjax(request)) throw e;
		
		response.setStatus(e.getStatusCode());
		String message = MessageBundleManager.get(e.getBundleKey(), actor.getLocale()); 
        return new ActionResult(ResultType.FAILURE, message);
    }
	
}
