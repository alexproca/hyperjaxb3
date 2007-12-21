package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import org.jvnet.jaxb2_commons.strategy.FieldOutlineProcessor;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public interface EjbFieldOutlineProcessor<T> extends
		FieldOutlineProcessor<T, ProcessOutline> {

	public T process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options);

}
