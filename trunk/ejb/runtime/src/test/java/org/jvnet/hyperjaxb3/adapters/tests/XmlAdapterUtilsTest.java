package org.jvnet.hyperjaxb3.adapters.tests;

import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.DurationAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.QNameAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.TimeStringAsCalendar;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDateTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;

public class XmlAdapterUtilsTest extends TestCase {

	public void testQNameXmlAdapter() throws Exception {

		final String alpha = "{urn:test}test";
		final QName omega = new QName("urn:test", "test");

		Assert.assertEquals("Conversion failed.", alpha, XmlAdapterUtils
				.unmarshall(QNameAsString.class, omega));
		Assert.assertEquals("Conversion failed.", omega, XmlAdapterUtils
				.marshall(QNameAsString.class, alpha));
	}

	public void testDuration() throws Exception {

		final String alpha = "P1Y2M3DT10H30M12.3S";
		final Duration omega = DatatypeFactory.newInstance().newDuration(alpha);

		Assert.assertEquals("Conversion failed.", alpha, XmlAdapterUtils
				.unmarshall(DurationAsString.class, omega));
		Assert.assertEquals("Conversion failed.", omega, XmlAdapterUtils
				.marshall(DurationAsString.class, alpha));
	}

	public void testXMLGregorianCalendarXmlAdapter() throws Exception {

		final XMLGregorianCalendar alpha = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar("2005-01-01T11:00:00.012+04:00");

		final XMLGregorianCalendar omega = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar("2005-01-01T09:00:00.012+02:00");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDateTime.class,
				XmlAdapterUtils.unmarshall(
						XMLGregorianCalendarAsDateTime.class, alpha));
		// Assert.assertEquals("Conversion failed.", alpha.normalize(),
		// omega.normalize());
		// Assert.assertEquals("Conversion failed.", alpha.normalize(),
		// beta.normalize());
		// Assert.assertEquals("Conversion failed.", beta.normalize(),
		// omega.normalize());
		Assert.assertEquals("Conversion failed.", XMLGregorianCalendarUtils
				.getTimeInMillis(alpha), XMLGregorianCalendarUtils
				.getTimeInMillis(beta));
		Assert.assertEquals("Conversion failed.", XMLGregorianCalendarUtils
				.getTimeInMillis(alpha), XMLGregorianCalendarUtils
				.getTimeInMillis(omega));
		Assert.assertEquals("Conversion failed.", XMLGregorianCalendarUtils
				.getTimeInMillis(beta), XMLGregorianCalendarUtils
				.getTimeInMillis(omega));
	}
	
	public void testXMLGregorianCalendarXmlAdapterInNegativeTimeZone() throws Exception {
		
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-2"));

		final XMLGregorianCalendar alpha = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar("2005-01-01T11:00:00.012+04:00");

		final XMLGregorianCalendar omega = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar("2005-01-01T09:00:00.012+02:00");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDateTime.class,
				XmlAdapterUtils.unmarshall(
						XMLGregorianCalendarAsDateTime.class, alpha));
		// Assert.assertEquals("Conversion failed.", alpha.normalize(),
		// omega.normalize());
		// Assert.assertEquals("Conversion failed.", alpha.normalize(),
		// beta.normalize());
		// Assert.assertEquals("Conversion failed.", beta.normalize(),
		// omega.normalize());
		Assert.assertEquals("Conversion failed.", XMLGregorianCalendarUtils
				.getTimeInMillis(alpha), XMLGregorianCalendarUtils
				.getTimeInMillis(beta));
		Assert.assertEquals("Conversion failed.", XMLGregorianCalendarUtils
				.getTimeInMillis(alpha), XMLGregorianCalendarUtils
				.getTimeInMillis(omega));
		Assert.assertEquals("Conversion failed.", XMLGregorianCalendarUtils
				.getTimeInMillis(beta), XMLGregorianCalendarUtils
				.getTimeInMillis(omega));
	}

	public void testXMLGregorianCalendarAsDate() throws Exception {

		final java.sql.Date alpha = java.sql.Date.valueOf("2005-01-01");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, alpha);

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsDate.class, beta);
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, gamma);
		Assert.assertEquals("Conversion failed.", beta, delta);
	}

	public void testXMLGregorianCalendarAsDateInNegativeTimezone()
			throws Exception {

		TimeZone.setDefault(TimeZone.getTimeZone("GMT-2"));

		final java.sql.Date alpha = java.sql.Date.valueOf("2005-01-01");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, alpha);

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsDate.class, beta);
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsDate.class, gamma);
		Assert.assertEquals("Conversion failed.", beta, delta);
	}

	public void testXMLGregorianCalendarAsTime() throws Exception {

		final java.sql.Time alpha = java.sql.Time.valueOf("10:12:14");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsTime.class, alpha);

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsTime.class, beta);
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsTime.class, gamma);
		Assert.assertEquals("Conversion failed.", beta, delta);
	}

	public void testXMLGregorianCalendarAsTimeInNegativeTimeZone()
			throws Exception {

		TimeZone.setDefault(TimeZone.getTimeZone("GMT-2"));

		final java.sql.Time alpha = java.sql.Time.valueOf("10:12:14");

		final XMLGregorianCalendar beta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsTime.class, alpha);

		final java.util.Date gamma = XmlAdapterUtils.unmarshall(
				XMLGregorianCalendarAsTime.class, beta);
		final XMLGregorianCalendar delta = XmlAdapterUtils.marshall(
				XMLGregorianCalendarAsTime.class, gamma);
		Assert.assertEquals("Conversion failed.", beta, delta);
	}

	public void testTimeStringAsCalendarXmlAdapter() throws Exception {

		checkTimeStringAsCalendarXmlAdapter("10:20:30");
		checkTimeStringAsCalendarXmlAdapter("10:20:30Z");
		checkTimeStringAsCalendarXmlAdapter("12:13:14+01:00");
		checkTimeStringAsCalendarXmlAdapter("12:13:14+02:00");
		checkTimeStringAsCalendarXmlAdapter("12:13:14-03:00");
	}

	private void checkTimeStringAsCalendarXmlAdapter(final String alpha) {
		final Calendar beta = XmlAdapterUtils.unmarshall(
				TimeStringAsCalendar.class, alpha);
		final String gamma = XmlAdapterUtils.marshall(
				TimeStringAsCalendar.class, beta);
		final Calendar delta = XmlAdapterUtils.unmarshall(
				TimeStringAsCalendar.class, gamma);
		final String epsilon = XmlAdapterUtils.marshall(
				TimeStringAsCalendar.class, delta);
		// Assert.assertEquals("Conversion failed.", alpha, gamma);
		Assert.assertEquals("Conversion failed.", beta, delta);
		Assert.assertEquals("Conversion failed.", gamma, epsilon);
	}
}
