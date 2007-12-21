package org.jvnet.hyperjaxb3.annotation.util;

import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.parser.XAnnotationFieldParser;

public class AnnotationUtils {

	@SuppressWarnings("unchecked")
	public static <T> XAnnotationField<T> create(final String name,
			final T value) {
		if (value == null) {
			return null;
		} else {

			try {
				return XAnnotationFieldParser.GENERIC.construct(name, value,
						value.getClass());
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	
	public static <T> XAnnotationField<T> create(final String name,
			final T value, final T defaultValue) {
		if (value != null) {
			return create(name, value);
		} else {
			return create(name, defaultValue);
		}
	}
	
}
