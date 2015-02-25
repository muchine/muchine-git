package com.lgu.abc.core.common.infra.counter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgu.abc.core.base.controller.AbstractHomeController;
import com.lgu.abc.core.exception.http.ResourceNotFoundException;
import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounter;
import com.lgu.abc.core.plugin.ui.notification.counter.NotificationCounterRegistry;
import com.lgu.abc.core.plugin.ui.notification.manager.NotificationManagerRegistry;

@Controller
@RequestMapping("/common/counter")
public class NotificationCounterController extends AbstractHomeController {

	private static final int MAX_CONTENT_COUNT = 4;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{counterId}/count")
	@ResponseBody
	public String count(@PathVariable String counterId) {
		NotificationCounter counter = NotificationCounterRegistry.find(counterId, actor().getCompany());
		if (counter == null) throw new ResourceNotFoundException();
		
		return String.valueOf(counter.count(actor()));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{counterId}/content")
	public ModelAndView content(@PathVariable String counterId) {
		NotificationCounter counter = NotificationCounterRegistry.find(counterId, actor().getCompany());
		if (counter == null) throw new ResourceNotFoundException();
		
		return counter.content(actor(), MAX_CONTENT_COUNT);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/synchronize")
	@ResponseStatus(HttpStatus.OK)
	public void refresh() {
		NotificationManagerRegistry.synchronize(actor());
	}
	
}
