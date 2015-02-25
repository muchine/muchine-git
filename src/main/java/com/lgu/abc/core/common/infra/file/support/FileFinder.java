package com.lgu.abc.core.common.infra.file.support;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.FileQuery;
import com.lgu.abc.core.common.infra.file.domain.FileLink;
import com.lgu.abc.core.common.infra.file.service.FileService;

@Component
public class FileFinder {

	private static FileFinder instance;
	
	@Autowired
	private FileService service;
	
	private FileFinder() {
		initialize();
	}
	
	private synchronized void initialize() {
		if (instance == null) instance = this;
	}
	
	public File findById(String fileId) {
		return service.read(fileId);
	}
	
	public File findByLink(String link) {
		FileQuery query = new FileQuery();
		query.setLink(new FileLink(link, new Date()));
		
		Iterable<File> found = service.find(query, null);
		return IterableUtils.isEmpty(found) ? null : found.iterator().next();
	}
	
	public File findByPath(String path) {
		FileQuery query = new FileQuery();
		query.setPath(path);
		
		Iterable<File> found = service.find(query, null);
		return IterableUtils.isEmpty(found) ? null : found.iterator().next();
	}
	
	public static FileFinder instance() {
		return instance;
	}
	
	public static File get(String fileId) {
		return instance == null ? null : instance.findById(fileId);
	}
	
}
