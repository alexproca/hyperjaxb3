package org.jvnet.hyperjaxb3.ejb.strategy.model.base.ignoring;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ignoring.Ignoring;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CClassInfoParent.Package;

public class DefaultIgnoring implements Ignoring {
	
	protected Log logger = LogFactory.getLog(getClass());

	public boolean isPackageInfoIgnored(ProcessModel context,
			Package packageInfo) {
		return false;
	}

	public boolean isClassInfoIgnored(ProcessModel context, CClassInfo classInfo) {

		if (context.getIgnoring().isPackageInfoIgnored(context,
				getPackageInfo(classInfo))) {
			logger.debug("Class info is ignored since package is ignored.");
			markAsAcknowledged(classInfo);
			return true;
		} else if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Class info is ignored per customization.");
			markAsAcknowledged(classInfo);
			return true;
		} else if (classInfo.getBaseClass() != null
				&& context.getIgnoring().isClassInfoIgnored(context,
						classInfo.getBaseClass())) {
			logger
					.debug("Class info is ignored since base class info is ignored.");
			markAsAcknowledged(classInfo);
			return true;
		} else {
			return false;
		}
	}

	public boolean isPropertyInfoIgnored(ProcessModel context,
			CPropertyInfo propertyInfo) {
		if (propertyInfo.parent() instanceof CClassInfo &&
				context.getIgnoring().isClassInfoIgnored(context,
				(CClassInfo) propertyInfo.parent())) {
			logger
					.debug("Property info is ignored since its class info is ignored.");
			return true;
		} else if (CustomizationUtils.containsCustomization(propertyInfo,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Property info is ignored per customization.");
			return true;
		} else {

			boolean allIgnored = true;

			final Collection<? extends CTypeInfo> types = propertyInfo.ref();

			for (final CTypeInfo type : types) {
				if (type instanceof CClassInfo) {
					final CClassInfo fieldClassInfo = (CClassInfo) type;
					allIgnored = allIgnored
							&& context.getIgnoring().isClassInfoIgnored(
									context, fieldClassInfo);
				} else {
					allIgnored = false;
				}
			}

			if (allIgnored) {
				logger
						.debug("Property info is ignored since all types are ignored.");
				markAsAcknowledged(propertyInfo);

			}
			return allIgnored;
		}
	}

	private CClassInfoParent.Package getPackageInfo(CClassInfoParent parent) {
		if (parent instanceof CClassInfoParent.Package) {
			return (Package) parent;
		} else if (parent instanceof CClassInfo) {
			return getPackageInfo(((CClassInfo) parent).parent());
		} else if (parent instanceof CElementInfo) {
			return getPackageInfo(((CElementInfo) parent).parent);
		}
		else
		{
			throw new AssertionError("Unexpexted class info parent [" + parent + "].");
		}

	}
	
	public void markAsAcknowledged(CClassInfo classInfo) {
		Customizations.markAsAcknowledged(classInfo);
	}
	
	public void markAsAcknowledged(CPropertyInfo propertyInfo) {
		Customizations.markAsAcknowledged(propertyInfo);
	}
}