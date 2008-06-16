package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeStringAsCalendar extends XmlAdapter<String, Calendar> {

	@Override
	public String marshal(Calendar time) throws Exception {
		if (time == null) {
			return null;
		} else {
			return DatatypeConverter.printTime(time);
		}
	}

	@Override
	public Calendar unmarshal(String time) throws Exception {
		if (time == null) {
			return null;
		} else {
			return DatatypeConverter.parseTime(time);
		}
	}
}
