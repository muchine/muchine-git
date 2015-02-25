package com.lgu.abc.core.prototype.workspace.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import com.lgu.abc.core.common.vo.Name;
import com.lgu.abc.core.prototype.base.PrototypeRegistryTest;
import com.lgu.abc.core.prototype.workspace.Workspace;
import com.lgu.abc.core.prototype.workspace.fixture.WorkspaceFixtureGenerator;
import com.lgu.abc.core.prototype.workspace.repo.WorkspaceRegistry;
import com.lgu.abc.core.prototype.workspace.repo.WorkspaceRepository;

public class WorkspaceRegistryTest extends PrototypeRegistryTest<Workspace> {

	@Autowired
	private WorkspaceFixtureGenerator generator;
	
	private String workspaceId;
	
	@Override
	public void setup() throws Exception {
		workspaceId = generator.generate().getId();
		super.setup();	
	}

	@Override
	protected void evict() {
		WorkspaceRegistry.evict(workspaceId);
	}

	@Override
	protected Workspace get() {
		return WorkspaceRegistry.get(workspaceId);
	}

	@Override
	protected String id() {
		return workspaceId;
	}

	@Override
	protected String cacheName() {
		return WorkspaceRepository.CACHE_NAME;
	}
	
	@Test
	public void testGetWorkspaceFromCache() {
		Cache cache = cacheManager.getCache(cacheName());
		final Name name = Name.create("Updated name...");
		
		Workspace entity = get();
		entity.setName(name);
				
		cache.put(id(), entity);
		
		Workspace found = get();
		logger.debug("found: " + found);
		assertThat(found.getName(), is(name));
	}

}
