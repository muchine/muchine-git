package com.lgu.abc.core.base.repository;

import java.util.List;

import com.lgu.abc.core.base.domain.BaseEntity;
import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.prototype.org.user.User;

public class RepositoryUtils {

	public static <T extends ActionEntity> void initializeEntities(Object query, List<T> found) {
		if (found == null) return;
		
		for (T entity : found) {
			if (entity == null) continue;
			
			injectActionContext(query, entity);
			entity.initialize();
		}
	}
	
	public static void injectActionContext(Object query, ActionEntity entity) {
		if (!(query instanceof BaseEntity) || entity == null) return;
		
		BaseEntity casted = (BaseEntity) query;
		entity.setAction(casted.getAction());
		
		User actor = casted.getActor();
		if (actor != null) entity.setActor(actor);
	}
	
	public static User getActorFrom(Object object) {
		if (!(object instanceof BaseEntity)) return null;
		return ((BaseEntity) object).getActor();
	}
	
}
