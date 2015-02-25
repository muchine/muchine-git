package com.lgu.abc.core.mybatis.handlers.graph;


/**
 * Handles object graph persistence on the database. If main action entity holds
 * child entities, this handler inserts, updates and deletes them.
 * 
 * @author sejoon
 */
public interface AggregateHandler {
	
	void create(Object entity);

	void update(Object entity);
	
	void delete(Object entity);
	
	public enum Type {
		DELETE_INSERT(true), UPSERT(true), DIRTY_CHECK(false), NO_UPDATE(true), AUTHORITY(true);
		
		/*
		 * When populating entity, decide whether to preserve list in the request 
		 * even though the property has null value because null is meaningful for this strategy.
		 */
		private final boolean preserve;
		
		private Type(boolean preserve) {
			this.preserve = preserve;
		}
		
		public boolean preserve() {
			return preserve;
		}
	}

}
