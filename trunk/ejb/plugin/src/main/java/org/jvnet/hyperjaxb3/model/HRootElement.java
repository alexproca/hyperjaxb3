package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;

public class HRootElement {

	private final QName name;

	public HRootElement(QName name) {
		super();
		Validate.notNull(name);
		this.name = name;
	}

	public QName getName() {
		return name;
	}

}
