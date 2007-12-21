package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsTime extends AbstractXMLGregorianCalendarAdapter {

	public void setFields(Calendar source, XMLGregorianCalendar target) {
		setHour(source, target);
		setMinute(source, target);
		setSecond(source, target);
		setMillisecond(source, target);
		setTimezone(source, target);
	}
}
