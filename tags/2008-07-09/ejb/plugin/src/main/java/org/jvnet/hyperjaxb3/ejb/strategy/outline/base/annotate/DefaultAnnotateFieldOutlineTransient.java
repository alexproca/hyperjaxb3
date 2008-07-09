package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Transient;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineTransient implements
		AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return Collections.singletonList( createTransient(outlineProcessor,
				fieldOutline, options) );
	}

	public XAnnotation createTransient(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return new XAnnotation(Transient.class);
	}

}
