package com.lgu.abc.core.base.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class BaseEntityTest {
	
	private BaseEntity entity;
	
	@Before
	public void setup() {
		entity = new TestBaseEntity();
		entity.setId("1");
//		entity.setCorpSeq(1L);
	}

	@Test
	public void testIdentical() {
		// Given
		BaseEntity compared = new TestBaseEntity();
		compared.setId("1");
		
		// When
		boolean expected = entity.identical(compared);
		
		// Then
		assertThat(expected, is(true));
	}
	
	@Test
	public void testIdenticalWithNullEntity() {
		// Given
		
		// When
		boolean expected = entity.identical(null);		
		
		// Then
		assertThat(expected, is(false));
	}
	
	@Test
	public void testIdenticalWithNullID() {
		// Given
		entity.setId(null);
		
		BaseEntity compared = new TestBaseEntity();
		compared.setId("1");
		
		// When
		boolean expected = entity.identical(null);
		
		// Then
		assertThat(expected, is(false));
	}
	
	@Test
	public void testIdenticalWithDifferentClass() {
		// Given
		BaseEntity compared = new TestBaseEntity2();
		compared.setId("1");
		
		// When
		boolean expected = entity.identical(compared);
		
		// Then
		assertThat(expected, is(false));
	}
	
//	@Test
//	public void testHasSameCorporation() {
//		// Given
//		BaseEntity compared = new TestBaseEntity();
//		compared.setCorpSeq(1L);
//		
//		// When
//		boolean expected = entity.hasSameCorporation(compared);
//		
//		// Then
//		assertThat(expected, is(true));
//	}
	
//	@Test
//	public void testHasSameCorporationWithNullEntity() {
//		// Given
//		
//		// When
//		boolean expected = entity.hasSameCorporation(null);
//		
//		// Then
//		assertThat(expected, is(false));
//	}
	
//	@Test
//	public void testHasSameCorporationWithNullID() {
//		// Given
//		entity.setCorpSeq(null);
//		
//		BaseEntity compared = new TestBaseEntity();
//		compared.setCorpSeq(1L);
//		
//		// When
//		boolean expected = entity.hasSameCorporation(compared);
//		
//		// Then
//		assertThat(expected, is(false));
//	}
//	
//	@Test
//	public void testHasSameCorporationWithDifferentClass() {
//		// Given
//		BaseEntity compared = new TestBaseEntity2();
//		compared.setCorpSeq(1L);
//		
//		// When
//		boolean expected = entity.hasSameCorporation(compared);
//		
//		// Then
//		assertThat(expected, is(true));
//	}
	
	private static class TestBaseEntity extends BaseEntity {}
	
	private static class TestBaseEntity2 extends BaseEntity {}
}
