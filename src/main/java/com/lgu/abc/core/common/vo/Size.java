package com.lgu.abc.core.common.vo;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Value bean for file/disk size
 * 
 * @author sejoon
 *
 */
public final class Size implements Comparable<Size> {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final long MAX_VALUE = Long.MAX_VALUE;
	
	private static final DecimalFormat formatter = new DecimalFormat("##0.00");
	
	public static final long KB = 1024;
	public static final long MB = KB * 1024;
	public static final long GB = MB * 1024 ;
	public static final long TB = GB * 1024;
	
	private final long bytes;
	
	public enum Unit {
		Byte, KB, MB, GB, TB
	}
	
	public Size(long bytes) {
		validate(bytes);
		this.bytes = bytes;
	}
	
	public Size(int size, Unit unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit must not be null.");
		
		// Convert size value to canonical format i.e Bytes
		long bytes = toBytes(size, unit);

		validate(bytes);
		this.bytes = bytes;
	}
	
	private void validate(long bytes) {
		if (bytes < 0 || bytes > MAX_VALUE)
			throw new IllegalArgumentException("Size value is out of valid range. size: " + bytes);		
	}
		
	private long toBytes(int size, Unit unit) {
		long bytes = size;
		
		switch(unit) {
			case Byte	: return bytes;
			case KB		: return bytes * KB;
			case MB		: return bytes * MB;
			case GB		: return bytes * GB;
			case TB		: return bytes * TB;
		}
		
		throw new IllegalArgumentException("Invalid Unit: " + unit);
	}
	
	/*
	 * Getters
	 */
	@JsonValue
	public long bytes() {
		return this.bytes;
	}
		
	/*
	 * Operation methods
	 */
	public Size add(Size size) {
		long sum = this.bytes + size.bytes;
		
		if (sum > MAX_VALUE)
			throw new IllegalStateException("Size value cannot exceed max value. value: " + sum);
		
		return new Size(sum);
	}
	
	public static Size sum(Size... sizes) {
		long sum = 0;
		
		for (Size size : sizes) {
			sum += size.bytes;
		}
		
		if (sum > MAX_VALUE)
			throw new IllegalStateException("Size value cannot exceed max value. value: " + sum);
		
		return new Size(sum);
	}
		
	public Size subtract(Size size) {
		long sub = this.bytes - size.bytes;
		
		// Size can't be negative number
		if (sub < 0) return new Size(0);
		
		return new Size(sub);
	}
	
	public Size multiply(int multiple) {
		return new Size(this.bytes * multiple);
	}
	
	/*
	 * Conversion methods
	 */
	public long asKB() {
		return bytes / KB;
	}
	
	public Long asMB() {
		return bytes / MB;
	}
	
	public Long asGB() {
		return bytes / GB;
	}
	
	public Long asTB() {
		return bytes / TB; 
	}
	
	@Override
	public int compareTo(Size o) {
		if (o == null) throw new NullPointerException();

		return new Long(this.bytes).compareTo(o.bytes);
	}

	@Override
	public String toString() {
		return this.asMB() + " (MB)";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Size)) return false;
		Size size = (Size) obj;

		return size.bytes == this.bytes;
	}
	
	public String text() {
		if (bytes >= TB) return formatter.format(bytes / new Float(TB)) + " TB";
		if (bytes >= GB) return formatter.format(bytes / new Float(GB)) + " GB";
		if (bytes >= MB) return formatter.format(bytes / new Float(MB)) + " MB";
		
		return formatter.format(bytes / new Float(KB)) + " KB";
	}
	
	public static void main(String[] args) {
		Size a = new Size(100, Unit.MB);
		Size b = new Size(22, Unit.MB);
				
		System.out.println(Long.MAX_VALUE);
		System.out.println(a.subtract(b));
	}

}
