package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;

public class XMLGregorianCalendarAsDate extends
		AbstractXMLGregorianCalendarAdapter {

	@Override
	public Date createDate(XMLGregorianCalendar calendar) {
		final java.util.TimeZone timeZone = XMLGregorianCalendarUtils.TIMEZONE_UTC;
		return new java.sql.Date(calendar.normalize().toGregorianCalendar(
				timeZone, Locale.UK, null).getTimeInMillis());
	}

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setYear(date.getYear() + 1900);
		calendar.setMonth(date.getMonth() + 1);
		calendar.setDay(date.getDate());
	}
}
