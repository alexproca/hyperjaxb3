package org.jvnet.hyperjaxb3.model;

import java.util.List;

import org.apache.commons.lang.Validate;

public class HSeeAlso {

	public final List<HClass> seeAlso;

	public HSeeAlso(List<HClass> seeAlso) {
		super();
		Validate.notNull(seeAlso);
		this.seeAlso = seeAlso;
	}

	public List<HClass> getSeeAlso() {
		return seeAlso;
	}
}
