package com.lgu.abc.core.base.repository.vo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.base.repository.vo.Rearrangement.Direction;

public class RearrangementTest {

	@Test
	public void testRearrange6to3() {
		int from = 6;
		int to = 3;
		
		Rearrangement rearrangement= new Rearrangement(from, to);
		assertThat(rearrangement.getPosition().getFrom(), is(3));
		assertThat(rearrangement.getPosition().getTo(), is(5));
		assertThat(rearrangement.getDirection(), is(Direction.BACKWARD));
	}
	
	@Test
	public void testRearrange2to7() {
		int from = 2;
		int to = 7;
		
		Rearrangement rearrangement= new Rearrangement(from, to);
		assertThat(rearrangement.getPosition().getFrom(), is(3));
		assertThat(rearrangement.getPosition().getTo(), is(7));
		assertThat(rearrangement.getDirection(), is(Direction.FORWARD));
	}
	
}
