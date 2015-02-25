package com.lgu.abc.core.prototype.base;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.lgu.abc.core.common.interfaces.Identifiable;
import com.lgu.abc.test.common.base.BaseTest;

public abstract class PrototypeRegistryTest<T extends Identifiable> extends BaseTest {

	@Autowired
	protected EhCacheCacheManager cacheManager;
	
	@Override
	public void setup() throws Exception {
		super.setup();
		
		evict();
	}
	
	protected abstract void evict();
	
	protected abstract T get();
	
	protected abstract String id();
	
	protected abstract String cacheName();

	@Test
	public void testGetEntity() {
		T entity = get();
		logger.debug("entity found: " + entity);
		
		assertThat(entity.getId(), is(id()));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testStoreEntityInCache() {
		Cache cache = cacheManager.getCache(cacheName());
		logger.debug("cache name: " + cache.getName());
		assertThat(cache.get(id()), is(nullValue()));
		
		T entity = get();
		logger.debug("entity got: " + entity);
		
		Object cached = cache.get(id()).get();
		logger.debug("value in cache: " + cached);
		assertThat(cached, is(notNullValue()));
		assertThat(((T) cached).identical(entity), is(true));
	}
	
}
