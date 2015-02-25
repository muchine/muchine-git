package com.lgu.abc.core.mybatis.handlers.graph.data;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.lgu.abc.core.base.domain.action.ActionEntity;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.mybatis.handlers.graph.policy.StatementNamePolicy;
import com.lgu.abc.core.mybatis.handlers.graph.policy.StatementNamePolicyFactory;
import com.lgu.abc.core.support.id.IdGenerator;

public class SqlSessionDataHandler implements DataHandler {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final SqlSessionTemplate template;
	
	private final IdGenerator idGenerator;
	
	private StatementNamePolicy policy = StatementNamePolicyFactory.getDefaultPolicy();
	
	public SqlSessionDataHandler(SqlSessionTemplate template, IdGenerator sequenceManager) {
		Validate.notNull(template, "template is null.");
		Validate.notNull(sequenceManager, "sequence manager is null.");
		
		this.template = template;
		this.idGenerator = sequenceManager;
	}
	
	@Override
	public int insert(Object parent, Object entity, String fieldName) {
		if (entity == null) return 0;
		
		final String statement = policy.insert(parent.getClass(), fieldName);
		
		logger.debug("insert aggregated entity: " + entity);
		logger.trace("insert statement: " + statement);
		
		ActionEntity wrapped = wrap(parent, entity);
		if (wrapped.isNull()) wrapped.setId(idGenerator.generateId(entity));
		wrapped.prepareCreation();
		
		return template.insert(statement, wrapped);
	}
	
	@Override
	public int update(Object parent, Object entity, String fieldName) {
		final String statement = policy.update(parent.getClass(), fieldName);
		
		logger.debug("update aggregated entity: " + entity);
		logger.trace("update statement: " + statement);
		
		ActionEntity wrapped = wrap(parent, entity);
		wrapped.prepareUpdate();
		
		return template.update(statement, wrapped);
	}
	
	@Override
	public int delete(Object parent, Object entity, String fieldName) {
		if (entity == null) return 0;
		
		final String statement = policy.delete(parent.getClass(), fieldName);
		
		logger.debug("delete aggregated entity: " + entity);
		logger.trace("delete statement: " + statement);
		
		return template.delete(statement, wrap(parent, entity));
	}
	
	@Override
	public List<Identifiable> findAll(Object parent, String fieldName) {
		String statement = policy.findAll(parent.getClass(), fieldName);
		return template.selectList(statement, parent);
	}
	
	@Override
	public void deleteAll(Object parent, String fieldName) {
		String deleteAll = policy.deleteAll(parent.getClass(), fieldName);
		template.delete(deleteAll, parent);
	}
	
	@Override
	public void upsert(Object parent, Object entity, String fieldName) {
		if (update(parent, entity, fieldName) == 0) insert(parent, entity, fieldName);
	}
	
	private ActionEntity wrap(Object parent, Object entity) {
		if (entity instanceof ActionEntity) return (ActionEntity) entity;
		
		ActionEntity wrapper = new DomainWrapper(entity, parent);
		wrapper.setActor(((ActionEntity) parent).getActor());
		
		return wrapper;
	}

}
