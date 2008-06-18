package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGYearMonth extends
		XMLGregorianCalendarAsDate {

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setYear(date.getYear() + 1900);
		calendar.setMonth(date.getMonth() + 1);
	}
}
