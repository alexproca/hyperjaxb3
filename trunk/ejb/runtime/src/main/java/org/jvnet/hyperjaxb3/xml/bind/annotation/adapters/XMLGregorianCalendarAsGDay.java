package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGDay extends XMLGregorianCalendarAsDate {

	@Override
	public void createCalendar(Date date, XMLGregorianCalendar calendar) {
		calendar.setDay(date.getDate());
	}
	
}
