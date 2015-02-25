package com.lgu.abc.core.prototype.org;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.lang.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.domain.IdentifiableEntity;
import com.lgu.abc.core.base.security.NullRole;
import com.lgu.abc.core.base.security.Permissible;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.LazyLoadable;
import com.lgu.abc.core.common.interfaces.Nullable;
import com.lgu.abc.core.common.interfaces.Protectable;
import com.lgu.abc.core.common.interfaces.Replicable;
import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.common.vo.id.ResourceID;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data abstract class Party extends IdentifiableEntity implements LazyLoadable, Nullable, Replicable {
	
	@JsonIgnore
	private boolean loaded = false;
	
	@NotNull
	private Name name = Name.create("");
	
	public Party() {}
	
	public Party(String id) {
		super(id);
	}
	
	public Party(String id, Name name) {
		this(id);
		
		Validate.notNull(name, "party name is null.");
		this.name = name;
	}
	
	public Party(Party e) {
		super(e);
		this.name = e.name;
	}
	
	@Override
	public void replicate(Object source) {
		if (source == null) return;
		Party casted = (Party) source;
		
		setId(casted.getId());
		name = new Name(casted.getName());
	}
	
	@JsonIgnore
	@Override
	public boolean isNull() {
		return getId() == null;
	}
	
	@JsonIgnore
	public boolean isEmpty() {
		return !isNull() && getName() == null;
	}
		
	public ResourceID resourceId() {
		Class<?> type = Enhancer.isEnhanced(getClass()) ? getClass().getSuperclass() : getClass();
		return new ResourceID(type, getId());
	}
	
	@JsonIgnore
	public Permissible role() {
		// Hook this method if needed.
		return new NullRole();
	}
	
	@Override
	public void load() {}
	
	public Party update(Party retrieved) {
		setId(retrieved.getId());
		return this;
	}
	
	public final boolean canRead(Protectable entity) {
		Privilege priv =  privilege(entity);
		return priv == null ? false : priv.isRead();
	}
	
	public final boolean canWrite(Protectable entity) {
		Privilege priv =  privilege(entity);
		return priv == null ? false : priv.isWrite();
	}
	
	public final boolean canManage(Protectable entity) {
		Privilege priv =  privilege(entity);
		return priv == null ? false : priv.isManage();
	}
	
	public abstract Privilege privilege(Protectable protectable);
	
	public abstract Privilege privilege(Containable containable);
	
	/**
	 * Return upper party in the organization hierarchy. For example, user should returns her department, department should return 
	 * its parent department.
	 * @return
	 */
	public abstract Party upper();
	
}
