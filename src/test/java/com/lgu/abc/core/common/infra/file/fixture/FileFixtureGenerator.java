package com.lgu.abc.core.common.infra.file.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.FileQuery;
import com.lgu.abc.core.common.infra.file.service.FileService;
import com.lgu.abc.test.support.fixture.DefaultFixtureGenerator;

@Component
public class FileFixtureGenerator extends DefaultFixtureGenerator<File, FileQuery> {
	
	@Autowired
	protected FileFixtureGenerator(FileFixtureFactory factory, FileService service) {
		super(factory, service);
	}

}
