package com.lgu.abc.core.prototype.ui.widget;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgu.abc.core.base.domain.action.RootEntity;
import com.lgu.abc.core.base.domain.support.OwnerFactory;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.plugin.ui.widget.Widget;
import com.lgu.abc.core.plugin.ui.widget.WidgetID;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetCategory;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetLocation;
import com.lgu.abc.core.prototype.ui.widget.domain.WidgetPosition;

@ToString(callSuper=true, includeFieldNames=true) @EqualsAndHashCode(callSuper=true)
public abstract @Data class WidgetOption extends RootEntity {

	@NotNull
	private WidgetCategory category;
	
	private String type;
	
	private String itemId = "0";
	
	@NotNull
	private WidgetPosition position;
	
	@NotNull
	private int order;
	
	public WidgetOption() {}
	
	public WidgetOption(OwnerFactory factory) {
		super(factory);
	}
	
	public WidgetOption(WidgetCategory category, Widget widget, WidgetLocation location) {
		this.category = category;
		this.type = widget.id().getType();
		this.itemId = widget.id().getItemId();
		this.position = location.getPosition();
		this.order = location.getOrder();
	}
	
	public WidgetOption(WidgetOption option) {
		super(option);
		
		this.category = option.getCategory();
		this.type = option.getType();
		this.itemId = option.getItemId();
		this.position = option.getPosition();
		this.order = option.getOrder();
	}
	
	public void setLocation(WidgetLocation location) {
		this.position = location.getPosition();
		this.order = location.getOrder();
	}
	
	public WidgetLocation getLocation() {
		return new WidgetLocation(position, order);
	}
	
	public WidgetID widgetId() {
		return new WidgetID(type, itemId);
	}
	
	@Deprecated @JsonIgnore
	public String getWidgetId() {
		return type + "_" + itemId;
	}
	
	@Override
	public boolean identical(Identifiable entity) {
		if (!(entity instanceof WidgetOption)) return false;
		
		WidgetOption casted = (WidgetOption) entity;
		return getOwnable().identical(casted.getOwnable()) && widgetId().equals(casted.widgetId());
	}
	
}
