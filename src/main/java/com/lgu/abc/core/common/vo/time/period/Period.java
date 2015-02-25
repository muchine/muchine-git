package com.lgu.abc.core.common.vo.time.period;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang.Validate;

import com.lgu.abc.core.common.vo.time.Time;
import com.lgu.abc.core.common.vo.time.exception.InvalidPeriodException;

@ToString(includeFieldNames=true) @EqualsAndHashCode
public final class Period {
	
	public static final Period MAX = new Period(new Time("19700101"), new Time("21001231"));

	private final Time start;
	private final Time end;
	
	public Period(Time start, Time end) {
		validate(start, end);
		
		this.start = start;
		this.end = end;
	}
	
	public Period(Time start, long seconds) {
		Validate.notNull(start, "start time is null.");
		
		this.start = start;
		
		long startMillis = this.start.toDate().getTime();
		if (Long.MAX_VALUE - startMillis < seconds * 1000)
			throw new IllegalArgumentException("Seconds are too big.");
		
		this.end = new Time(new Date(startMillis + seconds * 1000));
	}
	
	public static void validate(Time start, Time end) throws InvalidPeriodException {
		Validate.notNull(start, "start time is null.");
		Validate.notNull(end, "end time is null.");
				
		if (start.compareTo(end) > 0)
			throw new InvalidPeriodException();
	}
	
	public int days() {
		long diff = end.toDate().getTime() - start.toDate().getTime();
		long days = diff / (24 * 60 * 60 * 1000);
		
		if (days < Integer.MIN_VALUE || days > Integer.MAX_VALUE)
			throw new IllegalStateException(days + " cannot be casted to integer.");
		
		return (int) days;
	}
	
	public long seconds() {
		long diff = end.toDate().getTime() - start.toDate().getTime();
		return diff / 1000;
	}
	
	/*
	 * Comparator methods
	 */
	public boolean before(Date date) {
		return end.toDate().compareTo(date) < 0;
	}
	
	public boolean contains(Date date) {
		return start.toDate().compareTo(date) <= 0 && end.toDate().compareTo(date) >= 0;
	}
	
	public boolean after(Date date) {
		return start.toDate().compareTo(date) > 0;
	}
	
	public boolean overlapped(Period period) {
		return !this.before(period.start.toDate()) && !this.after(period.end.toDate());
	}
	
	public boolean contains(Period period) {
		return !this.start.after(period.start) && !this.end.before(period.end);
	}
	
	public Time start() {
		return start;
	}
	
	public Time end() {
		return end;
	}
	
	public Period plusDays(int days) {
		return new Period(start.plusDays(days), end.plusDays(days));
	}
	
	public Period plusMinutes(int minutes) {
		return new Period(start.plusMinutes(minutes), end.plusMinutes(minutes));
	}
	
	public Period plusSeconds(int seconds) {
		return new Period(start.plusSeconds(seconds), end.plusSeconds(seconds));
	}
	
	public Period minusDays(int days) {
		return new Period(start.minusDays(days), end.minusDays(days));
	}
	
	public Period minusMinutes(int minutes) {
		return new Period(start.minusMinutes(minutes), end.minusMinutes(minutes));
	}
	
	public Period minusSeconds(int seconds) {
		return new Period(start.minusSeconds(seconds), end.minusSeconds(seconds));
	}
	
	public static void main(String[] args) {
		Period period = new Period(new Time("20130624", "140000"), new Time("20130625", "140000"));
		System.out.println(period.after(new Date()));
		System.out.println(period.days());
		
		System.out.println("equals: " + period.equals(new Period(new Time("20130624", "140000"), new Time("20130625", "140000"))));
		
		System.out.println(new Time("20130625", "140000").toDate().before(new Date()));
		
		System.out.println(new Period(new Time(), 300));
	}
}
