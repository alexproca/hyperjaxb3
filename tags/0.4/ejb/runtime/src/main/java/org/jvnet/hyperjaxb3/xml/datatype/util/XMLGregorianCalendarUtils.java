package org.jvnet.hyperjaxb3.xml.datatype.util;

import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarUtils {

	public static TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

	public static XMLGregorianCalendar normalize(XMLGregorianCalendar calendar) {
		final XMLGregorianCalendar normalized = calendar.normalize();
		normalized.setTimezone(0);
		return normalized;
	}

	public static long getTimeInMillis(XMLGregorianCalendar calendar) {
		// TODO optimize
		return normalize(calendar).toGregorianCalendar(TIMEZONE_UTC, Locale.UK,
				null).getTimeInMillis();
	}
}
