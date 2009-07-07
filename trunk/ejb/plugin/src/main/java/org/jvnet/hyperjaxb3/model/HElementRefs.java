package org.jvnet.hyperjaxb3.model;

import java.util.List;

import org.apache.commons.lang.Validate;

public class HElementRefs {

	private final List<HElementRef> elementRefs;

	public HElementRefs(List<HElementRef> elementRefs) {
		super();
		Validate.notEmpty(elementRefs);
		this.elementRefs = elementRefs;
	}

	public List<HElementRef> getElementRefs() {
		return elementRefs;
	}
}
