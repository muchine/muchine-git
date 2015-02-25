package com.lgu.abc.core.common.vo.time;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.lgu.abc.core.common.vo.time.Time;

public class TimeTest {
	private static final String date = "20130701";
	private static final String time = "100500";
	private static final String datetime = date + time;
	
	private static Date DATE;
	
	@Getter
	private Time fixture = new Time(date, time);
	
	@Before
	public void setup() throws Exception {
		DATE = new SimpleDateFormat("yyyyMMddHHmmss").parse(date + time);
	}

	@Test
	public void testTimeConstructorWithDateString() {
		Time t = new Time(date);
		assertThat(t.toString(), is(date + "000000"));
	}
	
	@Test
	public void testTimeConstructorWithDateAndTimeString() {
		Time t = new Time(date, time);
		assertThat(t.toString(), is(datetime));
	}
	
	@Test
	public void testTimeConstructorWithTimeClass() {
		Time t = new Time(date, time);
		Time n = new Time(t);
		
		assertThat(n.toString(), is(datetime));
	}
	
	@Test
	public void testTimeConstructorWithDateClass() {
		Time t = new Time(DATE);
		assertThat(t.toString(), is(datetime));
	}
	
	@Test
	public void testTimeConstructorWithDateTimeClass() {
		DateTime dt = new Time(DATE).toDateTIme();
		Time t = new Time(dt);
		
		assertThat(t.toString(), is(datetime));
	}
	
	@Test
	public void testTimeConstructorWithCalendarClass() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DATE);
		
		Time t = new Time(calendar);
		assertThat(t.toString(), is(datetime));
	}
	
	@Test
	public void testGetYear() {
		assertThat(fixture.year(), is("2013"));
	}
	
	@Test
	public void testGetMonth() {
		assertThat(fixture.month(), is("07"));
	}
	
	@Test
	public void testGetDay() {
		assertThat(fixture.day(), is("01"));
	}
	
	@Test
	public void testBefore() {
		Time t = new Time("20140101", "100000");
		assertThat(fixture.before(t), is(true));
	}
	
	@Test
	public void testAfter() {
		Time t = new Time("20130101", "100000");
		assertThat(fixture.after(t), is(true));
	}
	
	@Test
	public void testPlusDays() {
		Time t = fixture.plusDays(2);
		assertThat(t.toString(), is("20130703" + time));
	}
	
	@Test
	public void testPlusMinutes() {
		Time t = fixture.plusMinutes(10);
		assertThat(t.toString(), is(date + "101500"));
	}
	
	@Test
	public void testPlusSeconds() {
		Time t = fixture.plusSeconds(10);
		assertThat(t.toString(), is(date + "100510"));
	}
	
	@Test
	public void testMinusDays() {
		Time t = fixture.minusDays(2);
		assertThat(t.toString(), is("20130629" + time));
	}
	
	@Test
	public void testMinusMinutes() {
		Time t = fixture.minusMinutes(10);
		assertThat(t.toString(), is(date + "095500"));
	}
	
	@Test
	public void testMinusSeconds() {
		Time t = fixture.minusSeconds(10);
		assertThat(t.toString(), is(date + "100450"));
	}
	
	@Test
	public void testToDate() {
		assertThat(fixture.toDate(), is(DATE));
	}
	
	@Test
	public void testToDateTime() {
		// TODO
	}
	
	@Test
	public void testCompareTo() {
		Time t = fixture.plusDays(2);
		assertTrue(t.compareTo(fixture) > 0 );
	}
	
	@Test
	public void testEqual() {
		Time a = new Time("20130819", "100000");
		Time b = new Time("20130819", "100000");
		
		assertThat(b, is(a));
	}
	
	@Test
	public void testHashcode() {
		Time a = new Time("20130819", "100000");
		Time b = new Time("20130819", "100000");
		
		assertThat(b.hashCode(), is(a.hashCode()));
	}
	
}
