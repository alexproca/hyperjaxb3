package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.mapping;

import com.sun.java.xml.ns.persistence.orm.EntityMappings;

public class MarshalMappings extends
		org.jvnet.hyperjaxb3.ejb.strategy.mapping.MarshalMappings {

	@Override
	protected EntityMappings createEntityMappings() {
		final EntityMappings entityMappings = new EntityMappings();
		entityMappings.setVersion("2.0");
		return entityMappings;
	}

}
