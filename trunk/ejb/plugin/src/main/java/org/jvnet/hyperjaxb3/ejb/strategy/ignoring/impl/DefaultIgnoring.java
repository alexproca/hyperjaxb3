package org.jvnet.hyperjaxb3.ejb.strategy.ignoring.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassInfoParent;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CClassInfoParent.Package;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.PackageOutline;

public class DefaultIgnoring implements Ignoring {

	protected Log logger = LogFactory.getLog(getClass());

	public boolean isPackageOutlineIgnored(PackageOutline packageOutline) {
		return false;
	}

	public boolean isClassOutlineIgnored(ClassOutline classOutline) {

		if (isPackageOutlineIgnored(classOutline._package())) {
			logger.debug("Class outline is ignored since package is ignored.");
			markAsAcknowledged(classOutline);
			return true;
		} else if (CustomizationUtils.containsCustomization(classOutline,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Class outline is ignored per customization.");
			markAsAcknowledged(classOutline);
			return true;
		} else if (classOutline.getSuperClass() != null
				&& isClassOutlineIgnored(classOutline.getSuperClass())) {
			logger
					.debug("Class outline is ignored since superclass outline is ignored.");
			markAsAcknowledged(classOutline);
			return true;
		} else {
			return false;
		}
	}

	public boolean isFieldOutlineIgnored(FieldOutline fieldOutline) {

		if (isClassOutlineIgnored(fieldOutline.parent())) {
			logger
					.debug("Field outline is ignored since its class outline is ignored.");
			return true;
		} else if (CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Field outline is ignored per customization.");
			return true;
		} else {

			boolean allIgnored = true;

			final Collection<? extends CTypeInfo> types = fieldOutline
					.getPropertyInfo().ref();

			for (final CTypeInfo type : types) {
				if (type instanceof CClassInfo) {
					final CClassInfo fieldClassInfo = (CClassInfo) type;
					final ClassOutline fieldClassOutline = fieldOutline
							.parent().parent().getClazz(fieldClassInfo);
					allIgnored = allIgnored
							&& isClassOutlineIgnored(fieldClassOutline);
				} else {
					allIgnored = false;
				}
			}

			if (allIgnored) {
				logger
						.debug("Field outline is ignored since all types are ignored.");
				markAsAcknowledged(fieldOutline);

			}
			return allIgnored;
		}
	}

	public void markAsAcknowledged(FieldOutline fieldOutline) {
		Customizations.markAsAcknowledged(fieldOutline.getPropertyInfo());
	}

	public void markAsAcknowledged(ClassOutline classOutline) {
		Customizations.markAsAcknowledged(classOutline.target);

		for (final FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
			Customizations.markAsAcknowledged(fieldOutline.getPropertyInfo());
		}
	}

	public boolean isPackageInfoIgnored(Package packageInfo) {
		return false;
	}

	public boolean isClassInfoIgnored(CClassInfo classInfo) {

		if (isPackageInfoIgnored(getPackageInfo(classInfo))) {
			logger.debug("Class info is ignored since package is ignored.");
			markAsAcknowledged(classInfo);
			return true;
		} else if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Class info is ignored per customization.");
			markAsAcknowledged(classInfo);
			return true;
		} else if (classInfo.getBaseClass() != null
				&& isClassInfoIgnored(classInfo.getBaseClass())) {
			logger
					.debug("Class info is ignored since base class info is ignored.");
			markAsAcknowledged(classInfo);
			return true;
		} else {
			return false;
		}
	}

	public boolean isPropertyInfoIgnored(CPropertyInfo propertyInfo) {
		if (propertyInfo.parent() instanceof CClassInfo
				&& isClassInfoIgnored((CClassInfo) propertyInfo.parent())) {
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
							&& isClassInfoIgnored(fieldClassInfo);
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
		} else {
			throw new AssertionError("Unexpexted class info parent [" + parent
					+ "].");
		}

	}

	public void markAsAcknowledged(CClassInfo classInfo) {
		Customizations.markAsAcknowledged(classInfo);
	}

	public void markAsAcknowledged(CPropertyInfo propertyInfo) {
		Customizations.markAsAcknowledged(propertyInfo);
	}

}