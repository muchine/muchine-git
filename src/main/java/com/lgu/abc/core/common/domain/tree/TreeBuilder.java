package com.lgu.abc.core.common.domain.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class TreeBuilder {

	private final List<TreeNodeFactory> factories;
	
	private final List<TreeNode> roots = new ArrayList<TreeNode>();
		
	public TreeBuilder(List<TreeNodeFactory> factories) {
		this.factories = factories;
	}
	
	public TreeBuilder(TreeNodeFactory... factories) {
		this(Arrays.asList(factories));
	}
	
	public List<TreeNode> build() {
		for (TreeNodeFactory factory : factories) {
			if (CollectionUtils.isEmpty(roots)) {
				addRoots(factory);
			} else {
				addChildren(factory);
			}
		}
		
		return roots;
	}
	
	private void addRoots(TreeNodeFactory factory) {
		roots.addAll(factory.create());
	}
	
	private void addChildren(TreeNodeFactory factory) {
		List<TreeNode> nodes = factory.create();
		for (TreeNode node : nodes) {
			addChild(node);
		}
	}
	
	private void addChild(TreeNode node) {
		TreeNode parent = findParentNode(node);
		if (parent != null) 
			parent.addChild(node);
		else
			roots.add(node);
	}
	
	private TreeNode findParentNode(TreeNode node) {
		for (TreeNode root : roots) {
			TreeNode parent = root.find(node.parentId());
			if (parent != null) return parent;
		}
		
		return null;
	}
	
}
