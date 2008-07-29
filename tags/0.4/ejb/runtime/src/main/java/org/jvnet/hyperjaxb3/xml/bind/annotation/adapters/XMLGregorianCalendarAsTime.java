package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.sql.Time;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;

public class XMLGregorianCalendarAsTime extends
		AbstractXMLGregorianCalendarAdapter {

	public Date createDate(XMLGregorianCalendar calendar) {
		final java.util.TimeZone timeZone = XMLGregorianCalendarUtils.TIMEZONE_UTC;
		final java.sql.Time time = new java.sql.Time(calendar.normalize()
				.toGregorianCalendar(timeZone, Locale.UK, null)
				.getTimeInMillis());
		time.setTime(time.getTime() + 60000 * time.getTimezoneOffset());
		return time;
	}

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setHour(date.getHours());
		calendar.setMinute(date.getMinutes());
		calendar.setSecond(date.getSeconds());
		System.out.println("1>" + date.getTime());
		System.out.println("2>" + (date.getTime() % 1000));
		System.out.println("3>" + ((int) (date.getTime() % 1000)));
		calendar.setMillisecond((int) (date.getTime() % 1000));
	}
}
