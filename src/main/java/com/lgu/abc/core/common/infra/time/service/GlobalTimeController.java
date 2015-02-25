package com.lgu.abc.core.common.infra.time.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common/time")
public class GlobalTimeController {

	@RequestMapping(method = RequestMethod.GET, value = "/timestamp")
	@ResponseBody
	public String timestamp() {
		return GlobalTimeService.getTimeStamp();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/current")
	@ResponseBody
	public String currentTime() {
		return GlobalTimeService.getGMTString();
	}
	
}
