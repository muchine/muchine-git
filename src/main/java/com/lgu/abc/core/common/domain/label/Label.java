package com.lgu.abc.core.common.domain.label;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.Length;

import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.base.domain.support.OwnerFactory;
import com.lgu.abc.core.common.code.Color;
import com.lgu.abc.core.common.code.LabelType;
import com.lgu.abc.core.common.vo.Name;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public abstract @Data class Label extends ShareableEntity {
	
	@NotNull
	private Name name;
	
	@Length(max = 500)
	private String description;
	
	private Color color;
	
	private LabelType type;
	
	private Boolean used;
	
	public Label() {}
	
	public Label(OwnerFactory creater) {
		super(creater);
	}
	
	public Label(Label entity) {
		super(entity);
		
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.color = entity.getColor();
		this.type = entity.getType();
		this.used = entity.getUsed();
	}
	
	public final boolean isSystemLabel() {
		return type != null && LabelType.DEFAULT.equals(type.getCode());
	}

	@Override
	public RootEntity create() {
		super.create();
		
		this.color = Color.random();
		this.type = new LabelType(LabelType.USER);
		this.used = true;
		
		return this;
	}

	@Override
	public RootEntity update(RootEntity entity) {
		super.update(entity);
		
		Label casted = (Label) entity;
		this.type = casted.getType();
		if (casted.isSystemLabel()) this.name = casted.getName();
		
		return this;
	}
	
}
