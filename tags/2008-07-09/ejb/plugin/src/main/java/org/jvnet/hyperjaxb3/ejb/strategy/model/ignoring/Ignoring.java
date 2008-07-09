package org.jvnet.hyperjaxb3.ejb.strategy.model.ignoring;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CPropertyInfo;

public interface Ignoring {

	public boolean isPackageInfoIgnored(ProcessModel context,
			CClassInfoParent.Package packageInfo);

	public boolean isClassInfoIgnored(ProcessModel context, CClassInfo classInfo);

	public boolean isPropertyInfoIgnored(ProcessModel context,
			CPropertyInfo propertyInfo);
}
