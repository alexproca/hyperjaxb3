package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

public class HElementWrapper {

	private final QName name;

	private final boolean nillable;

	private final boolean required;

	public HElementWrapper(QName name, boolean nillable, boolean required) {
		super();
		this.name = name;
		this.nillable = nillable;
		this.required = required;
	}

	public QName getName() {
		return name;
	}

	public boolean isNillable() {
		return nillable;
	}

	public boolean isRequired() {
		return required;
	}

}
