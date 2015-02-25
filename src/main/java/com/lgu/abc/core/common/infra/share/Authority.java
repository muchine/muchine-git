package com.lgu.abc.core.common.infra.share;

import javax.validation.Valid;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.support.id.annotation.Sequence;

@Sequence(table = "cmn_pwr_info", field = "pwr_seq")
@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public @Data class Authority extends RootEntity {
	
	private String tableName;
	
	private String entityId;
	
	@Valid
	private Accessor accessor;
	
	public Authority() {}
	
	public Authority(String tableName, String entityId, Accessor accessor) {
		Validate.notNull(tableName, "table name is null.");
		Validate.notNull(entityId, "entity id is null.");
		Validate.notNull(accessor, "accessor is null.");
		
		this.tableName = tableName;
		this.entityId = entityId;
		this.accessor = accessor;
	}
	
	public Authority(RootEntity entity, Accessor accessor) {
		this(entity.getSequenceTable(), entity.getId(), accessor);
	}
	
	public boolean hasParty(Party party) {
		return accessor == null ? null : accessor.hasParty(party);
	}
	
	public Party getParty() {
		load();
		return accessor.getParty();
	}

	@Override
	public RootEntity update(RootEntity retrieved) {
		super.update(retrieved);
		
		Authority casted = (Authority) retrieved;
		setTableName(casted.getTableName());
		setEntityId(casted.getEntityId());
		
		if (getAccessor() != null) {
			getAccessor().setParty(casted.getAccessor().getParty());
		}		
		
		return this;
	}
			
}
