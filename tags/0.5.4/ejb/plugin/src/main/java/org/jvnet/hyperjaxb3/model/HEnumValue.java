package org.jvnet.hyperjaxb3.model;

import org.apache.commons.lang.Validate;

public class HEnumValue {

	private final String value;

	public HEnumValue(String value) {
		super();
		Validate.notNull(value);
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
