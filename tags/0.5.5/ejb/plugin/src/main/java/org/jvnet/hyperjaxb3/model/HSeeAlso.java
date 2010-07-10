
package org.jvnet.hyperjaxb3.model;

import java.util.List;

import org.apache.commons.lang.Validate;

public class HSeeAlso {

	public final List<LType> seeAlso;

	public HSeeAlso(List<LType> seeAlso) {
		super();
		Validate.notNull(seeAlso);
		this.seeAlso = seeAlso;
	}

	public List<LType> getSeeAlso() {
		return seeAlso;
	}
}
