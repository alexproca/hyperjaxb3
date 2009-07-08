
package org.jvnet.hyperjaxb3.model;

import org.apache.commons.lang.Validate;

public class HEnum {

	private final LType type;

	public HEnum(LType type) {
		super();
		Validate.notNull(type);
		this.type = type;
	}

	public LType getType() {
		return type;
	}
}
