package com.lgu.abc.core.prototype.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.cache.Cache;

import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.org.Party;

public abstract class PartyRegistryTest<T extends Party> extends PrototypeRegistryTest<T> {

	@Test
	public void testGetPartyFromCache() {
		Cache cache = cacheManager.getCache(cacheName());
		final Name name = Name.create("Updated name...");
		
		T entity = get();
		entity.setName(name);
				
		cache.put(id(), entity);
		
		T found = get();
		logger.debug("found: " + found);
		assertThat(found.getName(), is(name));
	}
	
}
