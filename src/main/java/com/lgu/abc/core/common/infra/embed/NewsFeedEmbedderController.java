package com.lgu.abc.core.common.infra.embed;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.controller.AbstractHomeController;
import com.lgu.abc.core.base.domain.action.ActionResult;
import com.lgu.abc.core.plugin.ui.feed.content.ContentProperty;
import com.lgu.abc.core.plugin.ui.feed.content.ContentProvidable;
import com.lgu.abc.core.plugin.ui.feed.content.ContentProviderRegistry;
import com.lgu.abc.core.plugin.ui.feed.embed.NewsFeedEmbeddable;
import com.lgu.abc.core.plugin.ui.feed.embed.NewsFeedEmbedderRegistry;
import com.lgu.abc.core.plugin.ui.feed.exception.ContentException;
import com.lgu.abc.core.plugin.ui.feed.exception.ContentNotFoundException;
import com.lgu.abc.core.prototype.org.user.User;

@Controller
@RequestMapping("/common/embedded/")
public class NewsFeedEmbedderController extends AbstractHomeController {

	private final ContentExceptionHandler contentExceptionHandler = new ContentExceptionHandler();
	
	@RequestMapping("/{embedderId}")
	public ModelAndView loadEmbeddedContent(@PathVariable String embedderId, NewsFeedEmbedderCommand command, BindingResult errors)
			throws Exception{
		ContentProperty property = getContentProperty(command, actor());
		
		NewsFeedEmbeddable embedder = NewsFeedEmbedderRegistry.find(embedderId, actor().getCompany());
		return embedder.load(property, actor());
	}
	
	private ContentProperty getContentProperty(NewsFeedEmbedderCommand command, User actor) {
		ContentProvidable provider = ContentProviderRegistry.find(command.getProviderId(), actor().getCompany());
		if (provider == null) throw new ContentNotFoundException();
		
		return provider.provide(command.getContentId(), actor);
	}
	
	@ExceptionHandler(ContentException.class)
    public @ResponseBody ActionResult handleFeedContentException(ContentException e, HttpServletRequest request, HttpServletResponse response) 
    		throws Exception {
		return contentExceptionHandler.handle(e, request, response, actor());
    }
	
}
