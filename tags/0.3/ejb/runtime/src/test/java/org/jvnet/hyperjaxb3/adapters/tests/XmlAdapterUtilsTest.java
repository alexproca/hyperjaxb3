package org.jvnet.hyperjaxb3.adapters.tests;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.DurationAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.QNameAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDateTime;
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
				XMLGregorianCalendarAsDateTime.class, XmlAdapterUtils.unmarshall(
						XMLGregorianCalendarAsDateTime.class, alpha));
//		Assert.assertEquals("Conversion failed.", alpha.normalize(), omega.normalize());
//		Assert.assertEquals("Conversion failed.", alpha.normalize(), beta.normalize());
//		Assert.assertEquals("Conversion failed.", beta.normalize(), omega.normalize());
		Assert.assertEquals("Conversion failed.",
				XMLGregorianCalendarUtils.getTimeInMillis(alpha),
				XMLGregorianCalendarUtils.getTimeInMillis(beta));
		Assert.assertEquals("Conversion failed.",
				XMLGregorianCalendarUtils.getTimeInMillis(alpha),
				XMLGregorianCalendarUtils.getTimeInMillis(omega));
		Assert.assertEquals("Conversion failed.",
				XMLGregorianCalendarUtils.getTimeInMillis(beta),
				XMLGregorianCalendarUtils.getTimeInMillis(omega));
	}
}
