package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Version;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineVersion implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		xannotations
				.add(createVersion(outlineProcessor, fieldOutline, options));

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineColumn().process(outlineProcessor,
						fieldOutline, options));

		return xannotations;
	}

	public XAnnotation createVersion(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return new XAnnotation(Version.class);
	}
}
