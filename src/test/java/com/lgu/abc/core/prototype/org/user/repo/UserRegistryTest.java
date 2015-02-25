package com.lgu.abc.core.prototype.org.user.repo;

import com.lgu.abc.core.prototype.base.PartyRegistryTest;
import com.lgu.abc.core.prototype.org.user.User;
import com.lgu.abc.core.prototype.org.user.repo.UserRegistry;
import com.lgu.abc.core.prototype.org.user.repo.UserRepository;
import com.lgu.abc.test.common.base.fixture.SessionFactory;

public class UserRegistryTest extends PartyRegistryTest<User> {

//	@Autowired
//	private EhCacheCacheManager cacheManager;
	
	private final String userId = SessionFactory.ACTOR_ID;

	@Override
	protected void evict() {
		UserRegistry.evict(userId);
	}

	@Override
	protected User get() {
		return UserRegistry.get(userId);
	}

	@Override
	protected String id() {
		return userId;
	}

	@Override
	protected String cacheName() {
		return UserRepository.CACHE_NAME;
	}
	
	
	
//	@Override
//	public void setup() throws Exception {
//		super.setup();
//		
//		UserRegistry.evict(userId);
//	}
//
//	@Test
//	public void testGetUser() {
//		User user = UserRegistry.get(userId);
//		logger.debug("user found: " + user);
//		
//		assertThat(user.getId(), is(userId));
//	}
//	
//	@Test
//	public void testStoreUserInCache() {
//		Cache cache = cacheManager.getCache(UserRepository.CACHE_NAME);
//		assertThat(cache.get(userId), is(nullValue()));
//		
//		User user = UserRegistry.get(userId);
//		logger.debug("user: " + user);
//		
//		Object cached = cache.get(userId).get();
//		logger.debug("value in cache: " + cached);
//		assertThat(cached, is(notNullValue()));
//		assertThat(((User) cached).identical(user), is(true));
//	}
//	
//	@Test
//	public void testGetUserFromCache() {
//		Cache cache = cacheManager.getCache(UserRepository.CACHE_NAME);
//		final Name name = Name.create("Updated title...");
//		
//		User user = UserRegistry.get(userId);
//		user.setTitle(name);
//		
//		cache.put(userId, user);
//		
//		User found = UserRegistry.get(userId);
//		logger.debug("found: " + found);
//		assertThat(found.getTitle(), is(name));
//	}
	
}
