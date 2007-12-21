package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public interface ProcessClassOutline extends
		EjbClassOutlineProcessor<ClassOutline> {

	public ClassOutline process(ProcessOutline context,
			ClassOutline classOutline, Options options);

}
