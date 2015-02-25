package com.lgu.abc.core.common.file;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lgu.abc.core.common.file.download.FileResource;
import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.file.exception.IllegalFileStatusException;
import com.lgu.abc.core.common.file.exception.UnknownFileTypeException;
import com.lgu.abc.core.common.file.transfer.FileTransferable;
import com.lgu.abc.core.common.file.transfer.FileUpload;
import com.lgu.abc.core.common.file.transfer.JavaFileTransference;
import com.lgu.abc.core.common.file.validation.spec.FileSpecificationFactory;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.service.FileService;
import com.lgu.abc.core.common.infra.log.Logger;
import com.lgu.abc.core.common.infra.log.LoggerFactory;
import com.lgu.abc.core.common.spec.Specification;
import com.lgu.abc.core.plugin.file.FileUploadable;
import com.lgu.abc.core.plugin.file.FileUploadableRegistry;
import com.lgu.abc.core.prototype.org.user.User;

/**
 * Default FileManager implementation 
 * @author Sejoon Lim
 * @since 2014. 2. 5.
 *
 */
@Component
public class DefaultFileManager implements FileManager {
	
	protected final Logger logger;
	
	@Autowired
	private FileStorage keeper;
	
	@Autowired
	private FileUploadableRegistry registry;
	
	@Autowired
	private FileService service;
	
	@Autowired
	public DefaultFileManager(LoggerFactory factory) {
		this.logger = factory.getLog(getClass());
	}
	
	@Transactional
	@Override
	public File upload(String type, FileUpload uploaded) throws FileTransferException, UnknownFileTypeException {
		final User actor = uploaded.getActor();
		Validate.notNull(actor, "actor is null.");
		logger.write(actor.getId(), "DefaultFileManager", "upload", actor + "is uploading a file " + uploaded.name());
		
		final FileUploadable uploadable = uploadable(type);
		
		final String path = keeper.save(uploadable, uploaded);
		final File created = service.create(uploaded.create(path, uploadable.threshold()));
		
		return uploadable.complete(created);
	}
	
	@Transactional
	@Override
	public File copy(String type, File file) throws FileTransferException, UnknownFileTypeException {
		final FileTransferable transferable = new JavaFileTransference(file.name(), file.fullpath());
		return upload(type, new FileUpload(file.getActor(), transferable));
	}
	
	@Transactional
	@Override
	public void confirm(File file) {
		Validate.isTrue(!StringUtils.isEmpty(file.path()), "file path is null.");
		if (!file.isTemp()) return;
		
		file.confirm();
		file.prepareUpdate();
		
		service.update(file);
	}
	
	@Override
	public void update(File file) throws IllegalFileStatusException {
		if (!file.isTemp()) throw new IllegalFileStatusException();
		
		file.prepareUpdate();
		service.update(file);
	}
	
	@Override
	public FileResource download(String fileId, User actor) {
		File found = service.read(fileId);
		
		Specification spec = FileSpecificationFactory.download(actor);
		List<File> files = new ArrayList<File>();
		files.add(found);
		
		return found == null || !spec.isSatisfiedBy(files) ? null : new FileResource(found, new java.io.File(found.fullpath())); 
	}
	
	@Transactional
	@Override
	public void invalidate(File file, User actor) {
		if (file == null || file.isNull()) return;
		
		file.setActor(actor);
		if (service.read(file) == null) return;
		
		file.invalidate();
		file.prepareUpdate();
		
		service.update(file);
	}
	
	@Override
	public String createExternalLink(String fileId, Date expired) {
		File file = service.read(fileId);
		file.createLink(expired);
		
		file.prepareUpdate(file.getOwner().getUser());
		service.update(file);
		
		return file.getLink().getLink();
	}
	
	private FileUploadable uploadable(String type) {
		FileUploadable uploadable = registry.find(type);
		if (uploadable == null) {
			logger.error("unknown file uploadable name: " + type);
			throw new UnknownFileTypeException();
		}
		
		return uploadable;
	}
		
}
