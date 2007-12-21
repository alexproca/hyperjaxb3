package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import org.jvnet.jaxb2_commons.strategy.PackageOutlineProcessor;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.PackageOutline;

/**
 * EJB package outline processor. Processes the package outline.
 * 
 * @param <T>
 *            Type of the processing result.
 */
public interface EjbPackageOutlineProcessor<T> extends
		PackageOutlineProcessor<T, ProcessOutline> {

	public T process(ProcessOutline context, PackageOutline packageOutline,
			Options options);

}
