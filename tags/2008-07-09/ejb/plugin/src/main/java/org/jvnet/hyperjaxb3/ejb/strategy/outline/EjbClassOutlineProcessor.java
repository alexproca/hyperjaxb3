package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import org.jvnet.jaxb2_commons.strategy.ClassOutlineProcessor;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

/**
 * EJB class outline processor. Processes the class outline.
 * 
 * @param <T>
 *            Type of the processing result.
 */
public interface EjbClassOutlineProcessor<T> extends
		ClassOutlineProcessor<T, ProcessOutline> {
	
	public T process(ProcessOutline outlineProcessor, ClassOutline classOutline, Options options);

}
