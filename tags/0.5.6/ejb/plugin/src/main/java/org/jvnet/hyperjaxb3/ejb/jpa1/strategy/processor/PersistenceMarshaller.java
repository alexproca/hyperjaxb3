package org.jvnet.hyperjaxb3.ejb.jpa1.strategy.processor;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jvnet.hyperjaxb3.persistence.jpa1.JPA1Utils;

public class PersistenceMarshaller extends
		org.jvnet.hyperjaxb3.ejb.strategy.processor.PersistenceMarshaller {

	@Override
	protected Marshaller getMarshaller() throws JAXBException {
		return JPA1Utils.createMarshaller();
	}

}
