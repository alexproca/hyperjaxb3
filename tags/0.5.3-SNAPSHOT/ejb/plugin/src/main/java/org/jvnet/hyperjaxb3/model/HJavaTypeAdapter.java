
package org.jvnet.hyperjaxb3.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.Validate;

public class HJavaTypeAdapter {

	private final Class<? extends XmlAdapter> adapter;

	private final LType type;

	public HJavaTypeAdapter(Class<? extends XmlAdapter> adapter, LType type) {
		super();
		Validate.notNull(adapter);
		Validate.notNull(type);
		this.adapter = adapter;
		this.type = type;
	}

	public Class<? extends XmlAdapter> getAdapter() {
		return adapter;
	}

	public LType getType() {
		return type;
	}
}
