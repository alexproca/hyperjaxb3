package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;

public class HSchemaType {

	private final QName name;

	private final HClass type;

	public HSchemaType(QName name, HClass type) {
		super();
		Validate.notNull(name);
		Validate.notNull(type);
		this.name = name;
		this.type = type;
	}

	public QName getName() {
		return name;
	}

	public HClass getType() {
		return type;
	}

}
