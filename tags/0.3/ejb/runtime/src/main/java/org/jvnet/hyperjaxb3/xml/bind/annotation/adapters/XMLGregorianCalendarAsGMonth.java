package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGMonth extends AbstractXMLGregorianCalendarAdapter {

	public void setFields(Calendar source, XMLGregorianCalendar target) {
		setMonth(source, target);
		setTimezone(source, target);
	}
}
