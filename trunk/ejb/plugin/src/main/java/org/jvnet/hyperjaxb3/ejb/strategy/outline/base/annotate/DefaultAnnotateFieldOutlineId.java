package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineId implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id = Customizations
				.findCustomization(fieldOutline.getPropertyInfo(),
						Customizations.ID_ELEMENT_NAME);

		assert id != null;

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		xannotations.add(createId());
		xannotations.add(createGeneratedValue(id.getGeneratedValue()));

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineColumn().process(outlineProcessor,
						fieldOutline, options));
		return xannotations;
	}

	public XAnnotation createId() {
		return new XAnnotation(Id.class);
	}

	public XAnnotation createGeneratedValue(
			com.sun.java.xml.ns.persistence.orm.GeneratedValue generatedValue) {

		if (generatedValue == null) {
			return null;
		} else {

			final XAnnotationField.XString generator = createGenerator(generatedValue
					.getGenerator());
			final XAnnotationField.XEnum strategy = createStrategy(generatedValue
					.getStrategy());

			return new XAnnotation(GeneratedValue.class, generator, strategy);

		}
	}

	public XAnnotationField.XEnum createStrategy(String strategy) {
		return strategy == null ? null : new XAnnotationField.XEnum("strategy",
				GenerationType.valueOf(strategy), GenerationType.class);
	}

	public XAnnotationField.XString createGenerator(String generator) {
		return generator == null ? null : new XAnnotationField.XString(
				"generator", generator);
	}

}
