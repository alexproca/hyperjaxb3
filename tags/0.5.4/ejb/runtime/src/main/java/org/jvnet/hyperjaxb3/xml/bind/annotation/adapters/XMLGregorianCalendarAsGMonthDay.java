package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGMonthDay extends XMLGregorianCalendarAsDate {

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setMonth(date.getMonth() + 1);
		calendar.setDay(date.getDate());
	}
	
}
