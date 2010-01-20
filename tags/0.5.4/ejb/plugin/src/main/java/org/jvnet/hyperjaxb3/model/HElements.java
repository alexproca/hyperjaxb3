package org.jvnet.hyperjaxb3.model;

import java.util.List;

import org.apache.commons.lang.Validate;

public class HElements {

	private final List<HElement> elements;

	public HElements(List<HElement> elements) {
		super();
		Validate.notEmpty(elements);
		this.elements = elements;
	}

	public List<HElement> getElements() {
		return this.elements;
	}

}
