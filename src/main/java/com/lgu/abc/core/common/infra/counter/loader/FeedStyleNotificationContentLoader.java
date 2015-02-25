package com.lgu.abc.core.common.infra.counter.loader;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.common.infra.counter.view.NotificationContent;

public class FeedStyleNotificationContentLoader implements NotificationContentLoadable {

	private final List<NotificationContent> contents;
	
	private final String messageForNoData;
	
	public FeedStyleNotificationContentLoader(List<NotificationContent> contents, String messageForNoData) {
		this.contents = contents;
		this.messageForNoData = messageForNoData;
	}
	
	@Override
	public ModelAndView load() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("contents", contents);
		mav.addObject("messageForNoData", messageForNoData);
		mav.setViewName("/common/counter/notification.content.jstl");
		
		return mav;
	}

}
