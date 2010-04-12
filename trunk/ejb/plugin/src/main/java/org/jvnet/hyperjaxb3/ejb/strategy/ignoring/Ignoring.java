package org.jvnet.hyperjaxb3.ejb.strategy.ignoring;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.tools.xjc.outline.PackageOutline;

public interface Ignoring {

	public boolean isPackageOutlineIgnored(Outline outline, PackageOutline packageOutline);

	public boolean isClassOutlineIgnored(ClassOutline classOutline);

	public boolean isFieldOutlineIgnored(FieldOutline fieldOutline);

	public boolean isPackageInfoIgnored(Model model, CClassInfoParent.Package packageInfo);

	public boolean isClassInfoIgnored(CClassInfo classInfo);

	public boolean isPropertyInfoIgnored(CPropertyInfo propertyInfo);

}
