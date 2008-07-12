package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.ignoring;

import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.IsPackageOutlineIgnored;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.PackageOutline;

public class DefaultIsPackageOutlineIgnored implements IsPackageOutlineIgnored {

	// TODO #13 Allow package outlines to be ignored

	public Boolean process(ProcessOutline context,
			PackageOutline packageOutline, Options options) {
		return false;
	}

}
