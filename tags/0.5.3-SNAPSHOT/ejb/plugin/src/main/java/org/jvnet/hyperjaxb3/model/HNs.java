package org.jvnet.hyperjaxb3.model;

import org.apache.commons.lang.Validate;

public class HNs {

	private final String prefix;

	private final String namespaceURI;

	public HNs(String prefix, String namespaceURI) {
		super();
		Validate.notNull(prefix);
		Validate.notNull(namespaceURI);
		this.prefix = prefix;
		this.namespaceURI = namespaceURI;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}
}
