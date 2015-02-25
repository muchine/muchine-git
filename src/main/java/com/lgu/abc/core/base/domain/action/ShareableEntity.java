package com.lgu.abc.core.base.domain.action;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.domain.support.OwnerFactory;
import com.lgu.abc.core.common.infra.share.annotation.Share;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data abstract class ShareableEntity extends RootEntity {

	@Aggregate(Type.AUTHORITY)
	private List<Accessor> accessors;
	
	public ShareableEntity() {}
	
	public ShareableEntity(OwnerFactory creater) {
		super(creater);
	}
	
	public ShareableEntity(ShareableEntity entity) {
		super(entity);
		this.accessors = entity.getAccessors();
	}
	
	public final void addAccessor(Accessor accessor) {
		if (accessors == null) accessors = new ArrayList<Accessor>();
		accessors.add(accessor);
	}
	
	public final Accessor findAccessor(Party party) {
		if (party == null || getAccessors() == null) return null;
		
		for (Accessor a : getAccessors()) {
			if (party.identical(a.getParty())) return a;
		}
		
		return null;
	}
	
	@Override
	public RootEntity update(RootEntity retrieved) {
		super.update(retrieved);
		
		ShareableEntity casted = (ShareableEntity) retrieved;
		keepShownField(casted);
				
		return this;
	}
	
	public int getSharedCount() {
		return CollectionUtils.isEmpty(accessors) ? 0 : accessors.size();
	}
	
	public boolean isShared() {
		return getClass().isAnnotationPresent(Share.class);
	}
	
	@JsonIgnore
	public String getShareTableName() {
		Share annotation = getClass().getAnnotation(Share.class);
		if (annotation == null) throw new IllegalStateException("Share annotation is not declared.");
		
		return annotation.table();
	}
	
	private void keepShownField(ShareableEntity entity) {
		if (entity.getAccessors() == null) return;
		
		// shown field in accessor is defined by sharer so it should be kept even though entity owner updates the entity.
		for (Accessor a : entity.getAccessors()) {
			Accessor found = findAccessor(a.getParty());
			if (found != null) found.setShown(a.getShown());
		}
	}
	
}
