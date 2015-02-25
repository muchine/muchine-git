package com.lgu.abc.core.plugin.ui.completer;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lgu.abc.core.common.domain.tree.TreeNodeId;
import com.lgu.abc.core.common.vo.contact.Contact;

public @Data class AutoCompleteNode implements Comparable<AutoCompleteNode> {
	
	@JsonUnwrapped
	private final TreeNodeId nodeId;

	@JsonProperty("name")
	private final String name;
		
	@JsonProperty("contact")
	private final Contact contact;
	
	@JsonIgnore
	private final int order;

	public AutoCompleteNode(TreeNodeId nodeId, String name, Contact contact, int order) {
		this.nodeId = nodeId;
		this.name = name;
		this.contact = contact;
		this.order = order;
	}

	@Override
	public int compareTo(AutoCompleteNode o) {
		return Integer.compare(order, o.order);
	}

}
