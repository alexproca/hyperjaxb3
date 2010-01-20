package org.jvnet.hyperjaxb3.model;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlNsForm;

import org.apache.commons.lang.Validate;

public class HSchema {

	private final List<HNs> xmlns;

	private final String namespace;

	private final String location;

	private final XmlNsForm elementFormDefault;

	private final XmlNsForm attributeFormDefault;

	public HSchema(List<HNs> xmlns, String namespace, String location,
			XmlNsForm elementFormDefault, XmlNsForm attributeFormDefault) {
		super();
		Validate.notNull(elementFormDefault);
		Validate.notNull(attributeFormDefault);
		this.xmlns = xmlns == null ? Collections.<HNs> emptyList() : xmlns;
		this.namespace = namespace;
		this.location = location;
		this.elementFormDefault = elementFormDefault;
		this.attributeFormDefault = attributeFormDefault;
	}

	public List<HNs> getXmlns() {
		return xmlns;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getLocation() {
		return location;
	}

	public XmlNsForm getElementFormDefault() {
		return elementFormDefault;
	}

	public XmlNsForm getAttributeFormDefault() {
		return attributeFormDefault;
	}
}
