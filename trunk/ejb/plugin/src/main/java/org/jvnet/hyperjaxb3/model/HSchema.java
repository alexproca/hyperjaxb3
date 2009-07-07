package org.jvnet.hyperjaxb3.model;

import java.util.List;

import javax.xml.bind.annotation.XmlNsForm;

public class HSchema {

	private final List<HNs> xmlns;

	private final String namespace;

	private final String location;

	private final XmlNsForm elementFormDefault;

	private final XmlNsForm attributeFormDefault;

	public HSchema(List<HNs> xmlns, String namespace, String location,
			XmlNsForm elementFormDefault, XmlNsForm attributeFormDefault) {
		super();
		this.xmlns = xmlns;
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
