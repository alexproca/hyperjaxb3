package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

public class XMLGregorianCalendarAsGYear extends AbstractXMLGregorianCalendarAdapter {

	public void setFields(Calendar source, XMLGregorianCalendar target) {
		setYear(source, target);
		setTimezone(source, target);
	}
}
