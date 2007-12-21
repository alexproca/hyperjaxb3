package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGMonthDay extends AbstractXMLGregorianCalendarAdapter {

	public void setFields(Calendar source, XMLGregorianCalendar target) {
		setMonth(source, target);
		setDay(source, target);
		setTimezone(source, target);
	}
}
