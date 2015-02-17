package com.comparethemarket.platform.performance.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	public static String getTimestamp() {
		return FORMATTER.format(new Date());
	}

	private DateUtils() {
		super();
	}
}
