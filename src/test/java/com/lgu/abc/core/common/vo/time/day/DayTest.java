package com.lgu.abc.core.common.vo.time.day;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.period.Period;

public class DayTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void testJanuaryAsList() {
		testMonth("201201", 31);
	}
	
	@Test
	public void testLeapMonthAsList() {
		testMonth("201202", 29);
	}
	
	@Test
	public void testFebraruAsList() {
		testMonth("201102", 28);
	}
	
	@Test
	public void testAprilAsList() {
		testMonth("201204", 30);
	}
	
	private void testMonth(String month, int count) {
		// When
		List<Day> days = Day.asList(month);
		
		// Then
		for (Day day : days) {
			logger.debug("day: " + day);
		}
		
		assertThat(days.size(), is(count));
	}
	
	@Test
	public void testPeriodAsListWithOneDay() {
		// Given
		Time start = new Time("20140827", "100000");
		Time end = new Time("20140827", "110000");
		
		// When
		List<Day> days = Day.asList(new Period(start, end));
		
		// Then
		assertThat(days.size(), is(1));
		assertThat(days.get(0), is(new Day(start)));
	}
	
	@Test
	public void testPeriodAsListWithSuccessiveDays() {
		testPeriodAsListWithSuccessiveDays("100000", "210000");
	}
	
	@Test
	public void testPeriodAsListWithSuccessiveDaysAndDifferentTime() {
		testPeriodAsListWithSuccessiveDays("200000", "110000");
	}
	
	private void testPeriodAsListWithSuccessiveDays(String startTime, String endTime) {
		// Given
		Time start = new Time("20140827", startTime);
		Time end = new Time("20140828", endTime);
		
		// When
		List<Day> days = Day.asList(new Period(start, end));
		
		// Then
		for (Day day : days) {
			logger.debug("day: " + day);
		}
		
		assertThat(days.get(0), is(new Day(start)));
		assertThat(days.get(1), is(new Day(end)));
		assertThat(days.size(), is(2));
	}
	
	@Test
	public void testPeriodAsListWithMultipleDays() {
		testPeriodAsListWithMultipleDays("100000", "210000");
	}
	
	@Test
	public void testPeriodAsListWithMultipleDaysAndDifferentTime() {
		testPeriodAsListWithMultipleDays("210000", "100000");
	}
	
	private  void testPeriodAsListWithMultipleDays(String startTime, String endTime) {
		// Given
		Time start = new Time("20140827", startTime);
		Time end = new Time("20140910", endTime);
		
		// When
		List<Day> days = Day.asList(new Period(start, end));
		
		// Then
		for (Day day : days) {
			logger.debug("day: " + day);
		}
		
		assertThat(days.size(), is(15));
		assertThat(days.get(0), is(new Day(start)));
		assertThat(days.get(days.size() - 1), is(new Day(end)));
	}
	
}
