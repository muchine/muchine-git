package com.lgu.abc.core.mybatis.handlers.graph.strategy.collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.common.infra.share.repo.AuthorityRepository;
import com.lgu.abc.core.mybatis.handlers.graph.AggregateHandler.Type;
import com.lgu.abc.core.mybatis.handlers.graph.data.AuthorityDataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AbstractAggregateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.AggregateStrategy;
import com.lgu.abc.core.support.annotation.mapper.Aggregate;

@Component
public class AccessorStrategy extends AbstractAggregateStrategy implements AggregateStrategy {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	public AccessorStrategy(AuthorityRepository repository) {
		super(new AuthorityDataHandler(repository));
	}
	
	@Override
	public void update(Object parent, Object field, String fieldName) {
		// Use delete-insert strategy
		delete(parent, field, fieldName);
		create(parent, field, fieldName);
	}

	@Override
	protected boolean canSupport(Aggregate annotation) {
		return Type.AUTHORITY.equals(annotation.value());
	}
	
}
