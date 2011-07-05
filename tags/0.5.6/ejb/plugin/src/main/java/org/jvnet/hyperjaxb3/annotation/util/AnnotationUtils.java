package org.jvnet.hyperjaxb3.annotation.util;

import java.lang.annotation.Annotation;
import java.util.Collection;

import org.jvnet.annox.model.XAnnotation;
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

	public static <T extends Annotation> XAnnotationField<XAnnotation[]> create(
			final String name, final XAnnotation[] value,
			Class<T> annotationClass) {
		if (value == null) {
			return null;
		} else {
			return new XAnnotationField.XAnnotationArray(name, value,
					annotationClass);
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

}
