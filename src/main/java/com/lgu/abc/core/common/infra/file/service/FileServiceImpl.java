package com.lgu.abc.core.common.infra.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgu.abc.core.base.service.DomainServiceImpl;
import com.lgu.abc.core.base.utils.IterableUtils;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.FileQuery;
import com.lgu.abc.core.common.infra.file.repo.FileRepository;
import com.lgu.abc.core.prototype.org.user.User;

@Service
public class FileServiceImpl extends DomainServiceImpl<File, FileQuery> implements FileService, FileEventListenerRegistry {

	private final FileRepository repository;
	
	@Autowired
	public FileServiceImpl(FileRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	@Override
	public void shrink(User user) {
		removeAllStoredFiles(user);
		repository.shrink(user);
	}
	
	private void removeAllStoredFiles(User user) {
		Iterable<File> files = findAllFiles(user);
		if (IterableUtils.isEmpty(files)) return;
		
		for (File file : files) {
			new java.io.File(file.fullpath()).delete();
		}
	}
	
	private Iterable<File> findAllFiles(User user) {
		FileQuery query = new FileQuery();
		query.setActor(user);
		query.setOwnable(user);
		
		return find(query, null);
	}
	
}
