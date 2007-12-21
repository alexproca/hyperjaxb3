package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsDateTime extends AbstractXMLGregorianCalendarAdapter {

	public void setFields(Calendar source, XMLGregorianCalendar target) {
		setYear(source, target);
		setMonth(source, target);
		setDay(source, target);
		setHour(source, target);
		setMinute(source, target);
		setSecond(source, target);
		setMillisecond(source, target);
		setTimezone(source, target);
	}
}
