package org.jvnet.hyperjaxb3.model;

import java.util.List;

import javax.xml.namespace.QName;

public class HType {

	private final QName name;

	private final List<String> propOrder;

	private final HClass factoryClass;

	private final String factoryMethod;

	public HType(QName name, List<String> propOrder, HClass factoryClass,
			String factoryMethod) {
		super();
		this.name = name;
		this.propOrder = propOrder;
		this.factoryClass = factoryClass;
		this.factoryMethod = factoryMethod;
	}

	public QName getName() {
		return name;
	}

	public List<String> getPropOrder() {
		return propOrder;
	}

	public HClass getFactoryClass() {
		return factoryClass;
	}

	public String getFactoryMethod() {
		return factoryMethod;
	}
}