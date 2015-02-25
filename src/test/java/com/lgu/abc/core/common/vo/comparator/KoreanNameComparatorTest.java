package com.lgu.abc.core.common.vo.comparator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.vo.Name;

public class KoreanNameComparatorTest {

	private final AbstractNameComparator comparator = KoreanNameComparator.instance();
	
	private final Name name1 = Name.create("test", "test", "test", "test");
	private final Name name2 = Name.create("abc", "abc", "abc", "abc");
	
	@Test
	public void testBothOfNullName() {
		assertThat(comparator.compare(null, null), is(0));
		assertThat(comparator.compare(Name.create(null), Name.create(null)), is(0));
	}
	
	@Test
	public void testFirstNullName() {
		assertThat(comparator.compare(null, name1), is(1));
		assertThat(comparator.compare(Name.create(null), name1), is(1));
	}
	
	@Test
	public void testSecondNullName() {
		assertThat(comparator.compare(name1, null), is(-1));
		assertThat(comparator.compare(name1, Name.create(null)), is(-1));
	}
	
	@Test
	public void testName() {
		assertThat(comparator.compare(name1, name2) > 0, is(true));
		assertThat(comparator.compare(name2, name1) < 0, is(true));
	}
	
}
