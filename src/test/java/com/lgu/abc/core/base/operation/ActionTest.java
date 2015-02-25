package com.lgu.abc.core.base.operation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ActionTest {

	@Test
	public void testActions() {
		assertThat(Action.CREATE.isCreating(), is(true));
		assertThat(Action.CREATE.isUpdating(), is(false));
		assertThat(Action.CREATE.isDeleting(), is(false));
		assertThat(Action.CREATE.isFinding(), is(false));
		
		assertThat(Action.UPDATE.isCreating(), is(false));
		assertThat(Action.UPDATE.isUpdating(), is(true));
		assertThat(Action.UPDATE.isDeleting(), is(false));
		assertThat(Action.UPDATE.isFinding(), is(false));
	}
	
}
