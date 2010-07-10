package org.jvnet.hyperjaxb3.model;

import javax.xml.bind.annotation.XmlAccessOrder;

import org.apache.commons.lang.Validate;

public class HAccessorOrder {

	private final XmlAccessOrder accessOrder;

	public HAccessorOrder() {
		this(XmlAccessOrder.UNDEFINED);
	}

	public HAccessorOrder(XmlAccessOrder accessOrder) {
		super();
		Validate.notNull(accessOrder);
		this.accessOrder = accessOrder;
	}

	public XmlAccessOrder getAccessOrder() {
		return accessOrder;
	}

}
