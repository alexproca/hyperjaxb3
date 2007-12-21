package org.jvnet.hyperjaxb3.xml.bind.annotation.adapters;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;

public abstract class AbstractXMLGregorianCalendarAdapter extends
		XmlAdapter<XMLGregorianCalendar, Date> {

	@Override
	public final Date unmarshal(XMLGregorianCalendar date) throws Exception {
		if (date == null) {
			return null;
		} else {
			// TODO Optimize
			final java.util.TimeZone timeZone = XMLGregorianCalendarUtils.TIMEZONE_UTC;
			return date.normalize().toGregorianCalendar(timeZone,
					Locale.getDefault(), null).getTime();
		}
	}

	@Override
	public final XMLGregorianCalendar marshal(Date date) throws Exception {

		if (date == null) {
			return null;
		} else {
			final Calendar source = createCalendar(date);
			final XMLGregorianCalendar target = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar();

			setFields(source, target);
			return target;
		}
	}

	public Calendar createCalendar(Date date) {
		final Calendar source = Calendar
				.getInstance(XMLGregorianCalendarUtils.TIMEZONE_UTC);
		source.setTime(date);
		// source.setTimeZone(new SimpleTimeZone(0, ""));
		return source;
	}

	public abstract void setFields(Calendar source, XMLGregorianCalendar target);

	public void setDay(Calendar source, XMLGregorianCalendar target) {
		target.setDay(source.get(Calendar.DAY_OF_MONTH));
	}

	public void setMonth(Calendar source, XMLGregorianCalendar target) {
		target.setMonth(source.get(Calendar.MONTH) + 1);
	}

	public void setYear(Calendar source, XMLGregorianCalendar target) {
		target.setYear(source.get(Calendar.YEAR));
	}

	public void setHour(Calendar source, XMLGregorianCalendar target) {
		target.setHour(source.get(Calendar.HOUR_OF_DAY));
	}

	public void setMinute(Calendar source, XMLGregorianCalendar target) {
		target.setMinute(source.get(Calendar.MINUTE));
	}

	public void setSecond(Calendar source, XMLGregorianCalendar target) {
		target.setSecond(source.get(Calendar.SECOND));
	}

	public void setMillisecond(Calendar source, XMLGregorianCalendar target) {
		final int millisecond = source.get(Calendar.MILLISECOND);
		if (millisecond != 0) {
			target.setMillisecond(millisecond);
		}
	}

	public void setTimezone(Calendar source, XMLGregorianCalendar target) {
		if (source.getTimeZone() != null) {
			int rawOffset = source.getTimeZone().getRawOffset();
			if (rawOffset != 0) {
				target.setTimezone(Math.round(rawOffset / (60 * 1000)));
			}
		}
	}
}
