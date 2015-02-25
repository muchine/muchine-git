package com.lgu.abc.core.common.vo.time;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.period.Period;

public class PeriodTest {

	@Test
	public void testPeriodOverlapped() {
		Period period = new Period(new Time("20130710", "000000"), new Time("20130715", "000000"));
		
		Time start = new Time("20130620");
		Time end = new Time("20130630");
		
		assertFalse(period.overlapped(new Period(start, end)));
		
		start = new Time("20130720");
		end = new Time("20130722");
		
		assertFalse(period.overlapped(new Period(start, end)));
		
		start = new Time("20130620");
		end = new Time("20130712");
		
		assertTrue(period.overlapped(new Period(start, end)));
		
		start = new Time("20130712");
		end = new Time("20130730");
		
		assertTrue(period.overlapped(new Period(start, end)));
		
		start = new Time("20130620");
		end = new Time("20130720");
		
		assertTrue(period.overlapped(new Period(start, end)));
		
		start = new Time("20130712");
		end = new Time("20130713");
		
		assertTrue(period.overlapped(new Period(start, end)));
	}
	
}
