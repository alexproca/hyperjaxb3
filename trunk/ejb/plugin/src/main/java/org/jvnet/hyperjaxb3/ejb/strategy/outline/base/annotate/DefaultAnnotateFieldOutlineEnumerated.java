package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineEnumerated extends
		AbstractAnnotateSimpleFieldOutline implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				3);

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineBasic().process(outlineProcessor,
						fieldOutline, options));
		xannotations.addAll(createEnumerated(outlineProcessor, fieldOutline,
				options, null));
		return xannotations;

	}
}
