package com.lgu.abc.core.common.infra.file.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/common/file/batch")
public class BatchTempFileRemoverController {

	@Autowired
	private BatchTempFileRemover remover;
	
	@RequestMapping(method = RequestMethod.POST, value = "/process")
	@ResponseStatus(HttpStatus.OK)
	public void process() {
		remover.process();
	}
	
}
