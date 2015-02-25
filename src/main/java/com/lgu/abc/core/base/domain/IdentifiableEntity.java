package com.lgu.abc.core.base.domain;

import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.utils.EntityUtils;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.vo.id.ResourceID;

@ToString(exclude={"logger"}) @EqualsAndHashCode(exclude={"logger"})
public @Data class IdentifiableEntity implements Identifiable {
	
	// When an action entity is serialized into json, Jackson mapper can't convert this Logger object.
	@JsonIgnore @Transient
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Id
	private String id;
	
	public IdentifiableEntity() {}
	
	public IdentifiableEntity(String id) {
		this.id = id;
	}
	
	public IdentifiableEntity(IdentifiableEntity object) {
		this.id = object.id;
	}
	
	public void id(Long id) {
		this.id = id == null ? null : id.toString();
	}
		
	public ResourceID resourceId() {
		Class<?> type = Enhancer.isEnhanced(getClass()) ? getClass().getSuperclass() : getClass();
		return new ResourceID(type, getId());
	}

	@Override
	public boolean identical(Identifiable object) {
		return canCompareIdentity(object) && getId().equals(object.getId());
	}
	
	private boolean canCompareIdentity(Identifiable entity) {
		if (entity == null) {
			logger.trace("entity to compare is null.");
			return false;
		}
			
		if (id == null) {
			logger.trace("this instance id is null.");
			return false;
		}
		
		/*
		 * NOTE THe reason why using assignable method instead of equals() is that entity parameter can be a lazy loaded proxy class.
		 */
		if (!getActualClass(this).equals(getActualClass(entity))) {
			logger.trace("class is different. this: " + getClass() + ", entity: " + entity.getClass());
			return false;
		}
		
		return true;
	}
	
	private Class<?> getActualClass(Object object) {
		return EntityUtils.getActualClass(object.getClass());
	}

}
