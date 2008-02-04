package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.ignoring;

import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.IsClassOutlineIgnored;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.IsFieldOutlineIgnored;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.IsPackageOutlineIgnored;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.PackageOutline;

public class DefaultIgnoring implements Ignoring {

	private IsPackageOutlineIgnored isPackageOutlineIgnored;

	public IsPackageOutlineIgnored getIsPackageOutlineIgnored() {
		return isPackageOutlineIgnored;
	}

	@Required
	public void setIsPackageOutlineIgnored(
			IsPackageOutlineIgnored isPackageOutlineIgnored) {
		this.isPackageOutlineIgnored = isPackageOutlineIgnored;
	}

	private IsClassOutlineIgnored isClassOutlineIgnored;

	public IsClassOutlineIgnored getIsClassOutlineIgnored() {
		return isClassOutlineIgnored;
	}

	@Required
	public void setIsClassOutlineIgnored(
			IsClassOutlineIgnored isClassOutlineIgnored) {
		this.isClassOutlineIgnored = isClassOutlineIgnored;
	}

	private IsFieldOutlineIgnored isFieldOutlineIgnored;

	public IsFieldOutlineIgnored getIsFieldOutlineIgnored() {
		return isFieldOutlineIgnored;
	}

	public void setIsFieldOutlineIgnored(
			IsFieldOutlineIgnored isFieldOutlineIgnored) {
		this.isFieldOutlineIgnored = isFieldOutlineIgnored;
	}

	public boolean isPackageOutlineIgnored(ProcessOutline context,
			PackageOutline packageOutline, Options options) {
		return getIsPackageOutlineIgnored().process(context, packageOutline,
				options);
	}

	public boolean isClassOutlineIgnored(ProcessOutline context,
			ClassOutline classOutline, Options options) {
		return getIsClassOutlineIgnored().process(context, classOutline,
				options);
	}

	public boolean isFieldOutlineIgnored(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {
		return getIsFieldOutlineIgnored().process(context, fieldOutline,
				options);
	}
}
