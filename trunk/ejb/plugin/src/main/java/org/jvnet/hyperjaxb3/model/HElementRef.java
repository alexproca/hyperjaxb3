
package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;

public class HElementRef {

	private final QName name;

	private final LType type;

	public HElementRef(QName name, LType type) {
		super();
		Validate.notNull(name);
		Validate.notNull(type);
		this.name = name;
		this.type = type;
	}

	public QName getName() {
		return name;
	}

	public LType getType() {
		return type;
	}
}
