package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

public class HAttribute {

	private final QName name;

	private final boolean required;

	public HAttribute(QName name) {
		this(name, false);
	}

	public HAttribute(QName name, boolean required) {
		super();
		this.name = name;
		this.required = required;
	}

	public QName getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}
}