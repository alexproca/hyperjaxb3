package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Version;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineVersion extends
		AbstractAnnotateSimpleFieldOutline implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version cversion = outlineProcessor
				.getCustomizations().getVersion(fieldOutline);

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		xannotations.add(new XAnnotation(Version.class));

		xannotations.addAll(createColumn(outlineProcessor, fieldOutline,
				options, cversion.getColumn()));
		xannotations.addAll(createTemporal(outlineProcessor, fieldOutline,
				options, cversion.getTemporal()));

		return xannotations;
	}
}
