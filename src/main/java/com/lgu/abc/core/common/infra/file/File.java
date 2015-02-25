package com.lgu.abc.core.common.infra.file;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.file.FileLocator;
import com.lgu.abc.core.common.file.policy.FileExpirationPolicy;
import com.lgu.abc.core.common.infra.file.domain.FileLink;
import com.lgu.abc.core.common.infra.file.domain.FileProperty;
import com.lgu.abc.core.common.infra.file.domain.FileVolume;
import com.lgu.abc.core.common.vo.Size;
import com.lgu.abc.core.support.annotation.Domain;
import com.lgu.abc.core.support.id.annotation.Sequence;

@Domain
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
@Sequence(table = "cmn_atch_file", field = "atch_file_seq")
public @Data class File extends RootEntity {
	
	private FileProperty property = new FileProperty();
	
	private FileVolume volume = new FileVolume();
	
	private FileLink link = new FileLink();
	
	private boolean temp = true;
	
	private Date expiredDate;
	
	public File() {}
	
	public File(String id) {
		super(id);
	}
	
	public File(FileProperty property, FileVolume volume, boolean temp) {
		this.property = property;
		this.volume = volume;
		this.temp = temp;
		
		this.expiredDate = FileExpirationPolicy.expiredAt(temp, volume.isLarge());
		createLink();
	}
	
	public File(File file) {
		super(file);
		
		this.property = file.property;
		this.volume = file.volume;
		this.link = file.link;
		this.temp = file.temp;
		this.expiredDate = file.expiredDate;
	}
	
	public void confirm() {
		temp = false;
		expiredDate = FileExpirationPolicy.expiredAt(temp, volume.isLarge());
		
		clearLink();
	}
	
	public void invalidate() {
		temp = true;
		expiredDate = FileExpirationPolicy.expiredAt(temp, volume.isLarge());
	}
	
	public final String name() {
		return property.getName();
	}
	
	public String location() {
		return FileLocator.location(volume.isLarge());
	}
	
	public final String path() {
		return property.getPath();
	}
	
	public final String fullpath() {
		return location() + path();
	}
	
	public boolean exists() {
		return new java.io.File(fullpath()).exists();
	}
	
	public final void path(String filepath) {
		property.setPath(filepath.replace(location(), ""));
	}
	
	public Size size() {
		return volume.getSize();
	}
	
	public String link() {
		return link == null ? null : link.getLink();
	}
	
	public void createLink(Date expired) {
		link = new FileLink(uuid(), expired);
	}
	
	private void createLink() {
		if (temp) createLink(expiredDate);
	}
	
	private void clearLink() {
		link = new FileLink();
	}
	
	private String uuid() {
		return UUID.randomUUID().toString();
	}
	
}
