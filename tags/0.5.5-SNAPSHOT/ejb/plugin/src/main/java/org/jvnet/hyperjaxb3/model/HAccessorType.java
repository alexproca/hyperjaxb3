package org.jvnet.hyperjaxb3.model;

import javax.xml.bind.annotation.XmlAccessType;

import org.apache.commons.lang.Validate;

public class HAccessorType {

	private final XmlAccessType accessType;

	public HAccessorType() {
		this(XmlAccessType.PUBLIC_MEMBER);
	}

	public HAccessorType(XmlAccessType accessType) {
		super();
		Validate.notNull(accessType);
		this.accessType = accessType;
	}

	public XmlAccessType getAccessType() {
		return accessType;
	}

}
