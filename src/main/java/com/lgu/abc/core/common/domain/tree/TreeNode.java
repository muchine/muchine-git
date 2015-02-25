package com.lgu.abc.core.common.domain.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.Data;
import lombok.ToString;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@ToString(exclude="logger")
public abstract @Data class TreeNode {
	
	@JsonIgnore
	protected final Log logger = LogFactory.getLog(getClass());
	
	@JsonUnwrapped
	private final TreeNodeId nodeId;

	@JsonProperty("name")
	private final String name;
	
	@JsonProperty("text")
	private final String text;
	
	@JsonProperty("imageUrl")
	private final String iconUrl;
	
	private boolean expanded = false;
	
	@JsonProperty("child")
	private List<TreeNode> children;
	
	protected TreeNode(TreeNodeId nodeId, String name, String iconUrl) {
		this(nodeId, name, name, iconUrl);
	}
	
	protected TreeNode(TreeNodeId nodeId, String name, String text, String iconUrl) {
		this.nodeId = nodeId;
		this.name = StringEscapeUtils.unescapeHtml(name);
		this.text = StringEscapeUtils.unescapeHtml(text);
		this.iconUrl = iconUrl;
	}
	
	public void addChild(TreeNode node) {
		if (children == null) children = new ArrayList<TreeNode>();
		children.add(node);
	}

	public TreeNode find(TreeNodeId id) {
		if (this.nodeId.equals(id)) return this;
		
		if (children == null) return null;
		for (TreeNode node : children) {
			TreeNode found = node.find(id);
			if (found != null) return found;
		}
		
		return null;
	}
	
	public boolean identical(TreeNode object) {
		return nodeId.equals(object.nodeId);
	}
	
	public boolean hasChildren() {
		return !CollectionUtils.isEmpty(children);
	}
	
	public void sort(Comparator<TreeNode> comparator) {
		if (children == null) return;
		
		Collections.sort(children, comparator);
		for (TreeNode node : children) node.sort(comparator);
	}
	
	protected abstract TreeNodeId parentId();
	
}
