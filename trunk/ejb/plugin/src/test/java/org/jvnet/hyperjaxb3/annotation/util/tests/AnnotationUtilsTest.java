package org.jvnet.hyperjaxb3.annotation.util.tests;

import java.util.Collection;
import java.util.LinkedList;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

import junit.framework.TestCase;

public class AnnotationUtilsTest extends TestCase {

	public void testA() throws Exception {

		final Collection<XAnnotation> a = new LinkedList<XAnnotation>();
		a.add(new XAnnotation(Override.class));
		a.add(new XAnnotation(Override.class));
		XAnnotationField<XAnnotation[]> xa = AnnotationUtils.create("test",
				a.toArray(new XAnnotation[a.size()]), Override.class);
	}

	public void testB() throws Exception {

		final Collection<XAnnotation> a = new LinkedList<XAnnotation>();
		XAnnotationField<XAnnotation[]> xa = AnnotationUtils.create("test",
				a.toArray(new XAnnotation[a.size()]), Override.class);
	}

}