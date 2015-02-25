package com.lgu.abc.core.common.vo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.lgu.abc.core.common.vo.Size.Unit;

public class SizeTest {

	@Test
	public void testConstructFromBytes() {
		Size size = new Size(1024);
		assertThat(size.bytes(), is(1024L));
		assertThat(size.asKB(), is(1L));
		assertThat(size.asMB(), is(0L));
	}
	
	@Test
	public void testConstructFromUnitKB() {
		Size size = new Size(1, Unit.KB);
		assertThat(size.bytes(), is(1024L));
		assertThat(size.asKB(), is(1L));
		assertThat(size.asMB(), is(0L));
	}
	
	@Test
	public void testConstructFromUnitMB() {
		Size size = new Size(1, Unit.MB);
		assertThat(size.bytes(), is(1024 * 1024L));
		assertThat(size.asKB(), is(1024L));
		assertThat(size.asMB(), is(1L));
		assertThat(size.asGB(), is(0L));
	}
	
	@Test
	public void testConstructFromUnitGB() {
		Size size = new Size(1, Unit.GB);
		assertThat(size.bytes(), is(1024 * 1024 * 1024L));
		assertThat(size.asKB(), is(1024 * 1024L));
		assertThat(size.asMB(), is(1024L));
		assertThat(size.asGB(), is(1L));
		assertThat(size.asTB(), is(0L));
	}
	
	@Test
	public void testConstructFromUnitTB() {
		Size size = new Size(1, Unit.TB);
		assertThat(size.bytes(), is(1024 * 1024 * 1024 * 1024L));
		assertThat(size.asKB(), is(1024 * 1024 * 1024L));
		assertThat(size.asMB(), is(1024 * 1024L));
		assertThat(size.asGB(), is(1024L));
		assertThat(size.asTB(), is(1L));
	}
	
	@Test
	public void testAdd() {
		Size added = new Size(1, Unit.MB).add(new Size(2, Unit.MB));
		assertThat(added.asMB(), is(3L));
	}
	
	@Test
	public void testSumWithVariableArguments() {
		Size sum = Size.sum(new Size(1, Unit.MB), new Size(2, Unit.MB), new Size(3, Unit.MB));
		assertThat(sum.asMB(), is(6L));	
	}
	
	@Test
	public void testSumWithSizeArray() {
		Size[] sizes = new Size[] {new Size(1, Unit.MB), new Size(2, Unit.MB), new Size(3, Unit.MB)};
		Size sum = Size.sum(sizes);
		assertThat(sum.asMB(), is(6L));	
	}
	
	@Test
	public void testSubstract() {
		Size substracted = new Size(3, Unit.MB).subtract(new Size(1, Unit.MB));
		assertThat(substracted.asMB(), is(2L));
	}
	
	@Test
	public void testText() {
		assertThat(new Size(Size.TB + 200000000000L).text(), is("1.18 TB"));
		assertThat(new Size(Size.GB + 200000000L).text(), is("1.19 GB"));
		assertThat(new Size(Size.MB + 200000L).text(), is("1.19 MB"));
		assertThat(new Size(Size.KB + 200L).text(), is("1.20 KB"));
		assertThat(new Size(200L).text(), is("0.20 KB"));
	}
	
}
