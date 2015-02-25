package com.lgu.abc.core.common.infra.share.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.ToString;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lgu.abc.core.base.domain.query.ResolvableQuery;
import com.lgu.abc.core.common.infra.share.Authority;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.common.infra.share.support.AuthorityFinder;
import com.lgu.abc.core.common.interfaces.Containable;
import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.core.common.vo.id.ResourceID;
import com.lgu.abc.core.mybatis.handlers.query.AbstractQueryResolver;
import com.lgu.abc.core.mybatis.handlers.query.QueryResolver;
import com.lgu.abc.core.mybatis.handlers.query.QueryResolverFactory;
import com.lgu.abc.core.prototype.org.Party;
import com.lgu.abc.core.support.annotation.Query.Type;

@Component
public class AuthorityQueryResolver extends AbstractQueryResolver implements QueryResolver {
	
	protected final Log logger = LogFactory.getLog(this.getClass());	
	
	@Autowired
	private AuthorityFinder finder;
	
	@Autowired
	protected AuthorityQueryResolver(QueryResolverFactory factory) {
		factory.add(this);
	}
		
	@Override
	public void resolve(Object query) {
		if (!canResolve(query)) return;
		
		ResolvableQuery casted = (ResolvableQuery) query;
		logger.debug("Looking for shared items with user: " + casted.getActor());
		
		Iterable<Authority> authorities = finder.findAllAccessibleAuthorities(casted.queryTable(), casted.getActor());
		
		/*
		 * TODO Need to improve algorithm. There are two solutions for authorize users for shared entities.
		 * 1) Authorize here and make a query putting outside unreadable entities
		 * 2) Retrieve all entities first and authorize users on authorizer when returning entities in controller
		 * 
		 * Obviously, solution #1 has much better performance but requires complex algorithm. 
		 * The biggest problem of solution #2 is that party information of all entities should be lazily loaded one by one. 
		 * This may occurs severe performance degradation but caching functionality had introduced to mitigate this.
		 * Therefore, do not worry about performance.
		 */
		Map<String, Container> entities = getContainers(authorities);
		if (entities.isEmpty()) return;
		
		Set<String> shared = new HashSet<String>();
		for (String entityId : entities.keySet()) {
			Container entity = entities.get(entityId);
			if (casted.canQuery(entity)) {
				shared.add(entityId);
			}
		}
		
		casted.resolveAuthorities(new ArrayList<String>(shared));
	}
		
	private Map<String, Container> getContainers(Iterable<Authority> authorities) {
		Map<String, Container> entities = new HashMap<String, Container>();
		
		for (Authority authority : authorities) {
			if (authority == null) continue;
			
			Accessor accessor = authority.getAccessor(); 
			
			Container entity = entities.get(authority.getEntityId());
			if (entity == null)
				entities.put(authority.getEntityId(), new Container(authority));
			else
				entity.addAccessor(authority);
			
			logger.trace("accessor: " + accessor);
		}
		
		return entities;
	}

	@Override
	public boolean canResolve(Object query) {
		if (!super.canResolve(query)) return false;
		
		ResolvableQuery casted = (ResolvableQuery) query;
		
		for (Type type : casted.queryTypes()) {
			if (type.equals(Type.SHARED)) return true;
		}
		
		return false;
	}

	@ToString
	private static class Container implements Containable {

		protected final Log logger = LogFactory.getLog(this.getClass());
		
		private List<Authority> authorities= new ArrayList<Authority>();
		
		private final String id;
		
		public Container(Authority authority) {
			this.id = authority.getEntityId();
			authorities.add(authority);
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public boolean identical(Identifiable object) {
			return id.equals(object.getId());
		}

		@Override
		public ResourceID resourceId() {
			throw new UnsupportedOperationException();
		}
		
		public void addAccessor(Authority authority) {
			authorities.add(authority);
			for (Authority a : authorities) logger.debug("add accessor: " + a.getAccessor());
		}

		@Override
		public Privilege authorize(Party party) {
			if (authorities.isEmpty()) return null;
			if (authorities.size() == 1) return authorities.get(0).getAccessor().privilege();
			
			for (Authority a : authorities) {
				if (a.hasParty(party)) return a.getAccessor().privilege();
			}
			
			return null;
		}
		
	}
	
}
