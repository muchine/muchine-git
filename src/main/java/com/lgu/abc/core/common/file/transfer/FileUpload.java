package com.lgu.abc.core.common.file.transfer;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.common.file.exception.FileTransferException;
import com.lgu.abc.core.common.file.id.FileIdGeneratable;
import com.lgu.abc.core.common.file.id.UUIDGenerator;
import com.lgu.abc.core.common.file.policy.LargeVolumeFilePolicy;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.infra.file.domain.FileProperty;
import com.lgu.abc.core.common.infra.file.domain.FileVolume;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.prototype.org.user.User;

public @Data class FileUpload {

	private final User actor;
	
	@JsonIgnore
	private final FileTransferable file;
	
	private final boolean large;
	
	private FileIdGeneratable generator = UUIDGenerator.instance();
	
	public FileUpload(User actor, FileTransferable transferable) {
		this(actor, transferable, false);
	}
	
	public FileUpload(User actor, FileTransferable transferable, boolean large) {
		this.actor = actor;
		this.file = transferable;
		this.large = large;
	}
	
	public FileUpload(User actor, MultipartFile file) {
		this(actor, new MultipartFileTransference(file));
	}
	
	public String name() {
		return file.name();
	}
	
	public Size size() {
		return file.size();
	}
		
	public File create(String path, Size threshold) {
		File file = file(path, threshold, true);
		file.prepareCreation(actor);
		
		return file;
	}
		
	private File file(String path, Size threshold, boolean temp) {
		final FileProperty property = new FileProperty(name(), path);
		final FileVolume volume = new FileVolume(size(), LargeVolumeFilePolicy.isLarge(this, threshold));
		return new File(property, volume, temp);
	}

	public void transferTo(java.io.File destination) throws FileTransferException {
		try {
			this.file.transferTo(destination);	
		}
		catch (Exception e) {
			throw new FileTransferException(e);
		}
	}
	
}
