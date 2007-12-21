package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public interface ProcessFieldOutline extends
		EjbFieldOutlineProcessor<FieldOutline> {

	public FieldOutline process(ProcessOutline context,
			FieldOutline fieldOutline, Options options);

}
