package com.lgu.abc.core.common.batch.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/common/batch/report")
public class BatchStatusReportController {

	@Autowired
	private BatchStatusReporter reporter;
	
	@RequestMapping(method = RequestMethod.POST, value = "/process")
	@ResponseStatus(HttpStatus.OK)
	public void process() {
		reporter.process();
	}
	
}
