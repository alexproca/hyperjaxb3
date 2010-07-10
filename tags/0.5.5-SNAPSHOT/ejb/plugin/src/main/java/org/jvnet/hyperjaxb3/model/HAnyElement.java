package org.jvnet.hyperjaxb3.model;

import javax.xml.bind.annotation.DomHandler;
import javax.xml.bind.annotation.W3CDomHandler;

import org.apache.commons.lang.Validate;

public class HAnyElement {

	private final boolean lax;

	private final Class<? extends DomHandler> domHandler;

	public HAnyElement() {
		this(false, W3CDomHandler.class);
	}

	public HAnyElement(boolean lax, Class<? extends DomHandler> domHandler) {
		super();
		this.lax = lax;
		Validate.notNull(domHandler);
		this.domHandler = domHandler;
	}

	public boolean isLax() {
		return lax;
	}

	public Class<? extends DomHandler> getDomHandler() {
		return domHandler;
	}

}
