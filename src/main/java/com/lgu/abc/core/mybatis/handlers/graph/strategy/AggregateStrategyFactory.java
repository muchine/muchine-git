package com.lgu.abc.core.mybatis.handlers.graph.strategy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.mybatis.handlers.graph.data.DataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.data.SqlSessionDataHandler;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.association.AssociationStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.AccessorStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.DeleteInsertStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.DirtyCheckStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.NoUpdateStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.collection.UpsertStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.container.ContainerStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.file.FileStrategy;
import com.lgu.abc.core.mybatis.handlers.graph.strategy.label.LabelStrategy;
import com.lgu.abc.core.support.id.IdGenerator;

@Component
public class AggregateStrategyFactory {

	private final List<AggregateStrategy> registry;

	@Autowired
	public AggregateStrategyFactory(SqlSessionTemplate template, AccessorStrategy accessorStrategy, IdGenerator sequenceManager) {
		DataHandler handler = new SqlSessionDataHandler(template, sequenceManager);
		
		registry = new ArrayList<AggregateStrategy>();
		
		registry.add(new DeleteInsertStrategy(handler));
		registry.add(new UpsertStrategy(handler));
		registry.add(new DirtyCheckStrategy(handler));
		registry.add(new NoUpdateStrategy(handler));
		registry.add(accessorStrategy);
		registry.add(new AssociationStrategy(handler));
		registry.add(new ContainerStrategy(handler));
		registry.add(new LabelStrategy(handler));
		registry.add(new FileStrategy(handler));
	}
	
	public AggregateStrategy create(Field field) {
		for (AggregateStrategy strategy : registry) {
			if (strategy.canSupport(field))	return strategy;
		}
		
		return null;
	}
		
}
