package org.jvnet.hyperjaxb3.ejb.tests.equalsbuilder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;

public class HelloWorldEqualsBuilder extends JAXBEqualsBuilder {

	@Override
	public EqualsBuilder append(Object lhs, Object rhs) {
		System.out.println("Hello world!");
		return super.append(lhs, rhs);
	}

}
