package org.jvnet.hyperjaxb3.model;

import org.apache.commons.lang.Validate;

public class HEnum {

	private final MSimpleTypeClass type;

	public HEnum(MSimpleTypeClass type) {
		super();
		Validate.notNull(type);
		this.type = type;
	}

	public MSimpleTypeClass getType() {
		return type;
	}
}
