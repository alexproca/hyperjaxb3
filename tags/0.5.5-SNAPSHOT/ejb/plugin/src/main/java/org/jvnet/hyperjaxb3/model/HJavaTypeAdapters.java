package org.jvnet.hyperjaxb3.model;

import java.util.List;

import org.apache.commons.lang.Validate;

public class HJavaTypeAdapters {

	public HJavaTypeAdapters(List<HJavaTypeAdapter> javaTypeAdapters) {
		super();
		Validate.noNullElements(javaTypeAdapters);
		this.javaTypeAdapters = javaTypeAdapters;
	}

	private final List<HJavaTypeAdapter> javaTypeAdapters;

	public List<HJavaTypeAdapter> getJavaTypeAdapters() {
		return javaTypeAdapters;
	}
}
