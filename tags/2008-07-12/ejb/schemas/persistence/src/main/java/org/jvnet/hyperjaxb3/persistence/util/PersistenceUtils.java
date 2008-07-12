package org.jvnet.hyperjaxb3.persistence.util;

import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.Persistence;
import com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.outline.ClassOutline;

public class PersistenceUtils {

	private PersistenceUtils() {
	}

	/**
	 * JAXB context to process Hibernate configuration.
	 */
	public static final JAXBContext CONTEXT;
	static {
		try {
			CONTEXT = JAXBContext.newInstance(
					PersistenceConstants.CONTEXT_PATH, Persistence.class
							.getClassLoader());
		} catch (JAXBException jaxbex) {

			throw new ExceptionInInitializerError(jaxbex);
		}
	}
	

	public static Marshaller createMarshaller() throws JAXBException {
		final Marshaller marshaller = CONTEXT.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
				PersistenceConstants.SCHEMA_LOCATION);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		return marshaller;
	}
}
