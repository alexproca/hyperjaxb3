package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

public class HAttribute {

	private final QName name;

	private final boolean required;

	private final HClass type;

	public HAttribute(QName name, HClass type) {
		this(name, false, type);
	}

	public HAttribute(QName name, boolean required, HClass type) {
		super();
		this.name = name;
		this.required = required;
		this.type = type;
	}

	public QName getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public HClass getType() {
		return type;
	}
}