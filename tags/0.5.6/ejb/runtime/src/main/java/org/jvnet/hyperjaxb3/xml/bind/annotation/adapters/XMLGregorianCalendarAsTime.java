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
		final java.sql.Time time = new java.sql.Time(calendar.normalize()
				.toGregorianCalendar(/* timeZone, Locale.UK, null */)
				.getTimeInMillis());
		return time;
	}

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setHour(date.getHours());
		calendar.setMinute(date.getMinutes());
		calendar.setSecond(date.getSeconds());
		calendar.setMillisecond((int) (date.getTime() % 1000));
	}
}
