
package org.jvnet.hyperjaxb3.model;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;

public class HElement {

	private final QName name;

	private final boolean nillable;

	private final boolean required;

	private final LType type;

	private final String defaultValue;

	public HElement(QName name, LType type, String defaultValue) {
		this(name, false, false, type, defaultValue);
	}

	public HElement(QName name, boolean nillable, boolean required,
			LType type, String defaultValue) {
		super();
		Validate.notNull(name);
		Validate.notNull(type);
		this.name = name;
		this.nillable = nillable;
		this.required = required;
		this.type = type;
		this.defaultValue = defaultValue;
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

	public LType getType() {
		return type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
}