package org.jvnet.hyperjaxb3.model;

import org.apache.commons.lang.Validate;

public class HMimeType {

	private final String mimeType;

	public HMimeType(String mimeType) {
		super();
		Validate.notNull(mimeType);
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}
}
