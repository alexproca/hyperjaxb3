package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import java.util.Collection;

import org.jvnet.annox.model.XAnnotation;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public interface AnnotateFieldOutline extends
		EjbFieldOutlineProcessor<Collection<XAnnotation>> {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options);

}
