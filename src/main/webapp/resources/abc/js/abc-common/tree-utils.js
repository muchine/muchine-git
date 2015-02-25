var TreeUtils =  {

	USER : "user",
	DEPARTMENT : "dept",
	VIRTUAL : "virt",
	COMPANY : "corp",
	
	/**
	 * Get position of the given node in the tree
	 * @param selected Tree node object a user has selected
	 * @param tree Tree object
	 */
	position : function(selected, tree) {
		var position = null;
		var items = tree.items();
		var count = 0;
    	$.each(items, function(i, item) {
    		var node = tree.dataItem(item);
    		if (!node.parentNode()) return;
    		
    		if (TreeUtils.identical(node, selected)) {
    			position = count;
    		}    			
    		else if (TreeUtils.sibling(node, selected)) {
    			console.log("increase count. key: " + node.parentNode().key);
    			count++;
    		}
    	});
    	
    	return position;
	},
	
	identical : function(node1, node2) {
		return node1.type == node2.type && node1.key == node2.key;
	},
	
	sibling : function(node1, node2) {
		return TreeUtils.hasSameParent(node1, node2) && TreeUtils.equivalentType(node1, node2);
	},
	
	hasSameParent : function(node1, node2) {
		return (!node1.parentNode() && !node2.parentNode()) || node1.parentNode() == node2.parentNode();
	},
	
	equivalentType : function(node1, node2) {
		if (TreeUtils.isDepartment(node1)) return TreeUtils.isDepartment(node2);
		return node1.type == node2.type;
	},
	
	isUser : function(node) {
		return node.type == TreeUtils.USER;
	},
	
	isDepartment : function(node) {
		return node.type == TreeUtils.DEPARTMENT || node.type == TreeUtils.VIRTUAL;
	},
	
	isVirtual : function(node) {
		return node.type == TreeUtils.VIRTUAL;
	},
	
	isCompany : function(node) {
		return node.type == TreeUtils.COMPANY;
	},
	
	/**
	 * Get the size of children from the given node
	 * @param parent Tree node object for getting the number of children
	 * @param tree Tree object
	 */
	children : function(parent, tree) {
		var children = [];
		$.each(tree.items(), function(i, item) {
    		var node = tree.dataItem(item);
    		if (node.parentNode().key == parent.key) children.push(node);
    	});
		
		return children;
	}
	
};

