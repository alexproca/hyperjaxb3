package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineTemporal implements
		AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				3);

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineBasic().process(outlineProcessor,
						fieldOutline, options));

		final XAnnotation temporal = new XAnnotation(Temporal.class,
				createValue(outlineProcessor, fieldOutline, options));

		xannotations.add(temporal);
		return xannotations;
	}

	private TemporalType defaultValue = TemporalType.TIMESTAMP;

	public TemporalType getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(TemporalType defaultValue) {
		this.defaultValue = defaultValue;
	}

	public TemporalType getValue(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return getDefaultValue();
	}

	public XEnum createValue(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final TemporalType value = getValue(outlineProcessor, fieldOutline,
				options);

		if (value == null) {
			return null;
		} else {
			return new XAnnotationField.XEnum("value", value,
					TemporalType.class);
		}
	}
}
