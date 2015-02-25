package com.lgu.abc.core.common.file.policy;

import java.util.Date;

import com.lgu.abc.core.common.vo.time.Time;

public class FileExpirationPolicy {

	private static final int NORMAL_FILE_EXPIRATION = 1;
	
	private static final int LARGE_FILE_EXPIRATION = 7;
	
	private static final Date PERMANENT_FILE_EXPIRATION = new Time("22000101").toDate();
	
	public static final Date expiredAt(boolean temp, boolean large) {
		if (temp) return large ? dateAfter(LARGE_FILE_EXPIRATION) : dateAfter(NORMAL_FILE_EXPIRATION);
		return PERMANENT_FILE_EXPIRATION;
	}
	
	private static Date dateAfter(int days) {
		return new Time().plusDays(days).toDate();
	}
	
}
