package com.lgu.abc.core.common.file;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.file.File;
import com.lgu.abc.core.common.interfaces.FileAttachable;
import com.lgu.abc.core.plugin.serivce.AbstractDomainEventListener;
import com.lgu.abc.core.plugin.serivce.EventListenerRegistry;

public abstract class AbstractFileInvalidator<T extends RootEntity> extends AbstractDomainEventListener<T> {
		
	@Autowired
	private FileManager manager;
		
	protected AbstractFileInvalidator(EventListenerRegistry<T> registry) {
		super(registry);
	}
	
	@Override
	public void updated(T oldEntity, T newEntity) {
		Validate.isTrue(newEntity instanceof FileAttachable, "entity should be instance of FileAttachable");
		
		Collection<File> retrieved = ((FileAttachable) oldEntity).files();
		if (CollectionUtils.isEmpty(retrieved)) return;
		
		Collection<File> updating = ((FileAttachable) newEntity).files();
		for (File file : retrieved) {
			logger.debug("retrieved file: " + file);
			if (!exists(file, updating)) manager.invalidate(file, newEntity.getActor());
		}
	}
	
	@Override
	public void removing(T entity) {
		Validate.isTrue(entity instanceof FileAttachable, "entity should be instance of FileAttachable");
		
		Collection<File> removing = ((FileAttachable) entity).files();
		if (CollectionUtils.isEmpty(removing)) return;
		
		for (File file : removing) {
			logger.debug("invalidating file: " + file);
			manager.invalidate(file, entity.getActor());
		}
	}

	private boolean exists(File object, Collection<File> files) {
		if (CollectionUtils.isEmpty(files)) return false;
		for (File file : files) {
			if (file.identical(object)) return true;
		}
		
		return false;
	}

}
