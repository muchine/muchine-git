package com.lgu.abc.core.prototype.org;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.lgu.abc.core.base.domain.action.ShareableEntity;
import com.lgu.abc.core.common.infra.share.domain.AccessLevel;
import com.lgu.abc.core.common.infra.share.domain.Accessor;
import com.lgu.abc.core.common.infra.share.domain.Privilege;
import com.lgu.abc.core.prototype.org.Party;

public class AccessLevelTester {

	private final Party party;
	
	private final ShareableEntity entity;
	
	private Accessor accessor; 
	
	private Privilege priv;
	
	public AccessLevelTester(Party party, ShareableEntity entity) {
		this.party = party;
		this.entity = entity;
		
		priv = new Privilege(AccessLevel.NONE);
		accessor = new Accessor(party, priv);
	}
	
	public void testReadWithAccessLevel(boolean notExist, boolean none, boolean read, boolean write, boolean manage) {
		assertThat(party.canRead(entity), is(notExist));
		
		entity.addAccessor(accessor);
		
		priv.setLevel(AccessLevel.NONE);
		assertThat(party.canRead(entity), is(none));

		priv.setLevel(AccessLevel.READ);
		assertThat(party.canRead(entity), is(read));
		
		priv.setLevel(AccessLevel.WRITE);
		assertThat(party.canRead(entity), is(write));
		
		priv.setLevel(AccessLevel.MANAGE);
		assertThat(party.canRead(entity), is(manage));
	}
	
	public void testWriteWithAccessLevel(boolean notExist, boolean none, boolean read, boolean write, boolean manage) {
		assertThat(party.canWrite(entity), is(notExist));
		
		entity.addAccessor(accessor);

		priv.setLevel(AccessLevel.NONE);
		assertThat(party.canWrite(entity), is(none));

		priv.setLevel(AccessLevel.READ);
		assertThat(party.canWrite(entity), is(read));
		
		priv.setLevel(AccessLevel.WRITE);
		assertThat(party.canWrite(entity), is(write));
		
		priv.setLevel(AccessLevel.MANAGE);
		assertThat(party.canWrite(entity), is(manage));
	}
	
	public void testManageWithAccessLevel(boolean notExist, boolean none, boolean read, boolean write, boolean manage) {
		assertThat(party.canManage(entity), is(notExist));

		entity.addAccessor(accessor);
		
		priv.setLevel(AccessLevel.NONE);
		assertThat(party.canManage(entity), is(none));

		priv.setLevel(AccessLevel.READ);
		assertThat(party.canManage(entity), is(read));
		
		priv.setLevel(AccessLevel.WRITE);
		assertThat(party.canManage(entity), is(write));
		
		priv.setLevel(AccessLevel.MANAGE);
		assertThat(party.canManage(entity), is(manage));
	}
	
	public void testAllWithAccessLevel(boolean[] read, boolean[] write, boolean[] manage) {
		assertAccessLevelList(0, read, write, manage);

		entity.addAccessor(accessor);
		
		priv.setLevel(AccessLevel.NONE);
		assertAccessLevelList(1, read, write, manage);

		priv.setLevel(AccessLevel.READ);
		assertAccessLevelList(2, read, write, manage);
		
		priv.setLevel(AccessLevel.WRITE);
		assertAccessLevelList(3, read, write, manage);
		
		priv.setLevel(AccessLevel.MANAGE);
		assertAccessLevelList(4, read, write, manage);
	}
	
	public void assertAccessLevel(boolean read, boolean write, boolean manage) {
		assertThat(party.canRead(entity), is(read));
		assertThat(party.canWrite(entity), is(write));
		assertThat(party.canManage(entity), is(manage));
	}
	
	private void assertAccessLevelList(int step, boolean[] read, boolean[] write, boolean[] manage) {
		assertAccessLevel(read[step], write[step], manage[step]);
	}
}
