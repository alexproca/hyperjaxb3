
package org.jvnet.hyperjaxb3.model;

import java.util.List;

import org.apache.commons.lang.Validate;

public class HSchemaTypes {

	private final List<HSchemaType> schemaTypes;

	public HSchemaTypes(List<HSchemaType> schemaTypes) {
		super();
		Validate.notEmpty(schemaTypes);
		this.schemaTypes = schemaTypes;
	}

	public List<HSchemaType> getSchemaTypes() {
		return schemaTypes;
	}
}
