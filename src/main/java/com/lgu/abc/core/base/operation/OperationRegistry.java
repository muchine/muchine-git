package com.lgu.abc.core.base.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lombok.Data;

/**
 * Data store for operations controllers and/or services handle. 
 * 
 * @author sejoon
 *
 */
public @Data class OperationRegistry {
	
	private Map<OperationType, Boolean> operations;
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	public OperationRegistry() {
		initialize();
	}
	
	public boolean canSupport(OperationType type) {
		return operations.get(type);
	}
	
	public void setSupporOperations(OperationType[] types) {
		if (types == null) return;
		
		for (OperationType type : types) operations.put(type, true);
	}
	
	public List<OperationType> getSupportOperations() {
		List<OperationType> supported = new ArrayList<OperationType>();
		
		for (OperationType type : operations.keySet()) {
			if (operations.get(type)) supported.add(type);
		}
		
		return supported;
	}
	
	private void initialize() {
		this.operations = new HashMap<OperationType, Boolean>();
		
		// All operations are not supported by default. This map is overrided by controller during instantiation.
		for (OperationType type : OperationType.values()) {
			this.operations.put(type, false);
		}
	}
	
}
