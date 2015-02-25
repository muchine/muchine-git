package com.lgu.abc.core.common.infra.file;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.service.FileEventListenerRegistry;
import com.lgu.abc.core.plugin.serivce.AbstractDomainEventListener;

@Component
public class TestUploadMonitor extends AbstractDomainEventListener<File> {

	@Getter
	private final List<File> uploaded = new ArrayList<File>();
	
	@Autowired
	public TestUploadMonitor(FileEventListenerRegistry registry) {
		super(registry);
	}

	@Override
	public void created(File entity) {
		uploaded.add(entity);
	}
	
	public void clear() {
		uploaded.clear();
	}

}
