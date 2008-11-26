package org.jvnet.hyperjaxb3.annotation.util;

import java.lang.reflect.Array;
import java.util.Collection;

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

	public static <T> XAnnotationField<T[]> create(final String name,
			final T[] value) {
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

	public static <T> XAnnotationField<T[]> create(final String name,
			final Collection<T> value) {
		if (value == null) {
			return null;
		} else {
			if (value.isEmpty()) {
				throw new IllegalArgumentException(
						"Could not create an annotation field from an empty array.");
			}
			final Class<T> theClass = (Class<T>) value.iterator().next().getClass();
			for (T item : value) {
				if (!theClass.isAssignableFrom(item.getClass())) {
					throw new IllegalArgumentException(
							"Could not create an annotation field from the heterogeneous collection.");
				}
			}
			final T[] arrayValue = (T[]) Array.newInstance(theClass, value.size());
			value.toArray(arrayValue);
			try {
				return XAnnotationFieldParser.GENERIC.construct(name, arrayValue,
						arrayValue.getClass());
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
	
	public static <T> XAnnotationField<T[]> create(final String name,
			final T[] value, final T[] defaultValue) {
		if (value != null) {
			return create(name, value);
		} else {
			return create(name, defaultValue);
		}
	}
	
	public static <T> XAnnotationField<T[]> create(final String name,
			final Collection<T> value, final Collection<T> defaultValue) {
		if (value != null) {
			return create(name, value);
		} else {
			return create(name, defaultValue);
		}
	}
	
	
}
