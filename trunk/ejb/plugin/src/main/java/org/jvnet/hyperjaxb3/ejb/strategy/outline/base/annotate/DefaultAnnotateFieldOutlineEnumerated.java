package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineEnumerated implements
		AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				3);

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineBasic().process(outlineProcessor,
						fieldOutline, options));
		xannotations.add(createEnumerated(outlineProcessor, fieldOutline,
				options));

		return xannotations;

	}

	public XAnnotation createEnumerated(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return new XAnnotation(Enumerated.class, createEnumeratedValue(
				outlineProcessor, fieldOutline, options));
	}

	private EnumType defaultEnumeratedValue = EnumType.STRING;

	public EnumType getDefaultEnumeratedValue() {
		return defaultEnumeratedValue;
	}

	public void setDefaultEnumeratedValue(EnumType defaultEnumType) {
		this.defaultEnumeratedValue = defaultEnumType;
	}

	public EnumType getEnumeratedValue(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		final EnumType value = getDefaultEnumeratedValue();
		return value;
	}

	public XAnnotationField.XEnum createEnumeratedValue(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options) {
		final EnumType value = getEnumeratedValue(outlineProcessor,
				fieldOutline, options);

		if (value == null) {
			return null;
		} else {
			return new XAnnotationField.XEnum("value", value, EnumType.class);
		}
	}
}
