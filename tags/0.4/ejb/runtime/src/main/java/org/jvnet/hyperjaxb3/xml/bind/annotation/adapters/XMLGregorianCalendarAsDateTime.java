package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;

public class XMLGregorianCalendarAsDateTime extends
		AbstractXMLGregorianCalendarAdapter {

	public Date createDate(XMLGregorianCalendar calendar) {
		final java.util.TimeZone timeZone = XMLGregorianCalendarUtils.TIMEZONE_UTC;
		final java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.normalize().toGregorianCalendar(
				timeZone, Locale.UK, null).getTimeInMillis());
		timestamp.setTime(timestamp.getTime() + 60000 * timestamp.getTimezoneOffset());
		return timestamp;
	}

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setYear(date.getYear() + 1900);
		calendar.setMonth(date.getMonth() + 1);
		calendar.setDay(date.getDate());
		calendar.setHour(date.getHours());
		calendar.setMinute(date.getMinutes());
		calendar.setSecond(date.getSeconds());
		calendar.setMillisecond((int) (date.getTime() % 1000));
	}
}
