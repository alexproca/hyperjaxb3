package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import java.util.Collection;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface ProcessFieldOutlines extends
		EjbClassOutlineProcessor<Collection<FieldOutline>> {

	public Collection<FieldOutline> process(ProcessOutline context,
			ClassOutline classOutline, Options options);

}
