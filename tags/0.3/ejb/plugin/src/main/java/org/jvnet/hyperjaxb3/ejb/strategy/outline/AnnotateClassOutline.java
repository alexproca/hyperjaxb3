package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import java.util.Collection;

import org.jvnet.annox.model.XAnnotation;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

/**
 * Annotates the class outline and returns an array of annotations.
 */
public interface AnnotateClassOutline extends
		EjbClassOutlineProcessor<Collection<XAnnotation>> {
	
	public Collection<XAnnotation> process(ProcessOutline outlineProcessor, ClassOutline classOutline, Options options);

}
