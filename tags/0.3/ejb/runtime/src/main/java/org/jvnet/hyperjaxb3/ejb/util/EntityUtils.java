package org.jvnet.hyperjaxb3.ejb.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

public class EntityUtils {

	private EntityUtils() {
	}

	public static Object getId(Object entity) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		final Collection<Field> atEmbeddedIdFields = getAnnotatedElements(
				entity.getClass().getFields(), EmbeddedId.class);
		final Collection<Method> atEmbeddedIdMethods = getAnnotatedElements(
				entity.getClass().getMethods(), EmbeddedId.class);
		final Collection<Field> atIdFields = getAnnotatedElements(entity
				.getClass().getFields(), Id.class);
		final Collection<Method> atIdMethods = getAnnotatedElements(entity
				.getClass().getMethods(), Id.class);

		if (atEmbeddedIdFields.size() > 0) {
			assert atEmbeddedIdFields.size() == 1 : "More than one field is annotated with @EmbeddedId.";
			final Field field = atEmbeddedIdFields.iterator().next();
			return get(entity, field);
		} else if (atEmbeddedIdMethods.size() > 0) {
			assert atEmbeddedIdMethods.size() == 1 : "More than one method is annotated with @EmbeddedId.";
			final Method method = atEmbeddedIdMethods.iterator().next();
			return get(entity, method);
		} else if (atIdFields.size() == 1) {
			return get(entity, atIdFields.iterator().next());
		} else if (atIdMethods.size() == 1) {
			return get(entity, atIdMethods.iterator().next());
		} else {
			throw new IllegalArgumentException("No id could be found.");
		}
	}

	private static <T extends AnnotatedElement> Collection<T> getAnnotatedElements(
			T[] elements, Class<? extends Annotation> annotationType) {
		final Collection<T> annotatedElements = new LinkedList<T>();
		for (final T element : elements) {
			if (element.isAnnotationPresent(annotationType)) {
				annotatedElements.add(element);
			}
		}
		return annotatedElements;
	}

	private static Object get(Object target, Field field)
			throws IllegalArgumentException, IllegalAccessException {
		return field.get(target);
	}

	private static Object get(Object target, Method method)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return method.invoke(target, (Object[]) null);
	}
}
