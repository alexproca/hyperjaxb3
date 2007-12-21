package org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring;

import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.PackageOutline;

public interface Ignoring {

	public boolean isPackageOutlineIgnored(ProcessOutline context,
			PackageOutline packageOutline, Options options);

	public boolean isClassOutlineIgnored(ProcessOutline context,
			ClassOutline classOutline, Options options);

	public boolean isFieldOutlineIgnored(ProcessOutline context,
			FieldOutline fieldOutline, Options options);
}
