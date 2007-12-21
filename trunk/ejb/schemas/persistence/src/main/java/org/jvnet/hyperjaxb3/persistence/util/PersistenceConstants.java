package org.jvnet.hyperjaxb3.persistence.util;

import com.sun.java.xml.ns.persistence.Persistence;
import com.sun.java.xml.ns.persistence.orm.Entity;

public class PersistenceConstants {

	private PersistenceConstants() {
	}

	public static final String CONTEXT_PATH = Persistence.class.getPackage()
			.getName()
			+ ":" + Entity.class.getPackage().getName();

	public static final String SCHEMA_LOCATION = "http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd";

}
