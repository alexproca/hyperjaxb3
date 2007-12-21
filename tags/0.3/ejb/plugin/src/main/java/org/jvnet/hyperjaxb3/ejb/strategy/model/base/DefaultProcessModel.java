package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.model.AdaptTypeUse;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreateDefaultIdPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.GetTypes;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessPropertyInfos;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.Model;

public class DefaultProcessModel implements ProcessModel {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CClassInfo> process(ProcessModel context, Model model) {

		logger.debug("Processing model [...].");

		final CClassInfo[] classInfos = model.beans().values().toArray(
				new CClassInfo[0]);
		final Collection<CClassInfo> includedClasses = new HashSet<CClassInfo>();

		for (final CClassInfo classInfo : classInfos) {
			final Collection<CClassInfo> targetClassInfos = getProcessClassInfo()
					.process(context, classInfo);
			if (targetClassInfos != null) {
				for (final CClassInfo targetClassInfo : targetClassInfos) {
					includedClasses.add(targetClassInfo);
				}
			}
		}
		
		return includedClasses;
	}

	private ProcessClassInfo processClassInfo;

	public ProcessClassInfo getProcessClassInfo() {
		return processClassInfo;
	}

	@Required
	public void setProcessClassInfo(ProcessClassInfo processClassInfo) {
		this.processClassInfo = processClassInfo;
	}

	private ProcessPropertyInfos processPropertyInfos;

	public ProcessPropertyInfos getProcessPropertyInfos() {
		return processPropertyInfos;
	}

	@Required
	public void setProcessPropertyInfos(
			ProcessPropertyInfos processPropertyInfos) {
		this.processPropertyInfos = processPropertyInfos;
	}

	private CreateDefaultIdPropertyInfos createDefaultIdPropertyInfos;

	public CreateDefaultIdPropertyInfos getCreateDefaultIdPropertyInfos() {
		return createDefaultIdPropertyInfos;
	}

	@Required
	public void setCreateDefaultIdPropertyInfos(
			CreateDefaultIdPropertyInfos createDefaultIdPropertyInfos) {
		this.createDefaultIdPropertyInfos = createDefaultIdPropertyInfos;
	}

	private CreatePropertyInfos wrapComplexHeteroCollection;

	public CreatePropertyInfos getWrapComplexHeteroCollection() {
		return this.wrapComplexHeteroCollection;
	}

	public void setWrapComplexHeteroCollection(
			CreatePropertyInfos wrapHeteroCollection) {
		this.wrapComplexHeteroCollection = wrapHeteroCollection;
	}

	private GetTypes getTypes;

	public GetTypes getGetTypes() {
		return getTypes;
	}

	@Required
	public void setGetTypes(GetTypes getTypes) {
		this.getTypes = getTypes;
	}

	private CreatePropertyInfos wrapSingleBuiltinAttribute;

	public CreatePropertyInfos getWrapSingleBuiltinAttribute() {
		return wrapSingleBuiltinAttribute;
	}

	@Required
	public void setWrapSingleBuiltinAttribute(
			CreatePropertyInfos wrapSingleBuiltinAttribute) {
		this.wrapSingleBuiltinAttribute = wrapSingleBuiltinAttribute;
	}
	
	private CreatePropertyInfos wrapCollectionBuiltinAttribute;

	public CreatePropertyInfos getWrapCollectionBuiltinAttribute() {
		return wrapCollectionBuiltinAttribute;
	}

	@Required
	public void setWrapCollectionBuiltinAttribute(
			CreatePropertyInfos wrapCollectionBuiltinAttribute) {
		this.wrapCollectionBuiltinAttribute = wrapCollectionBuiltinAttribute;
	}
	
	private CreatePropertyInfos wrapCollectionEnumAttribute;

	public CreatePropertyInfos getWrapCollectionEnumAttribute() {
		return wrapCollectionEnumAttribute;
	}

	@Required
	public void setWrapCollectionEnumAttribute(
			CreatePropertyInfos wrapCollectionEnumAttribute) {
		this.wrapCollectionEnumAttribute = wrapCollectionEnumAttribute;
	}
	
	

	private CreatePropertyInfos wrapSingleBuiltinValue;

	public CreatePropertyInfos getWrapSingleBuiltinValue() {
		return wrapSingleBuiltinValue;
	}

	@Required
	public void setWrapSingleBuiltinValue(
			CreatePropertyInfos wrapSingleBuiltinValue) {
		this.wrapSingleBuiltinValue = wrapSingleBuiltinValue;
	}
	
	private CreatePropertyInfos wrapCollectionBuiltinValue;

	public CreatePropertyInfos getWrapCollectionBuiltinValue() {
		return wrapCollectionBuiltinValue;
	}

	@Required
	public void setWrapCollectionBuiltinValue(
			CreatePropertyInfos wrapCollectionBuiltinValue) {
		this.wrapCollectionBuiltinValue = wrapCollectionBuiltinValue;
	}
	
	private CreatePropertyInfos wrapCollectionEnumValue;

	public CreatePropertyInfos getWrapCollectionEnumValue() {
		return wrapCollectionEnumValue;
	}

	@Required
	public void setWrapCollectionEnumValue(
			CreatePropertyInfos wrapCollectionEnumValue) {
		this.wrapCollectionEnumValue = wrapCollectionEnumValue;
	}

	private CreatePropertyInfos wrapSingleBuiltinElement;

	public CreatePropertyInfos getWrapSingleBuiltinElement() {
		return wrapSingleBuiltinElement;
	}

	@Required
	public void setWrapSingleBuiltinElement(
			CreatePropertyInfos wrapSingleBuiltinElement) {
		this.wrapSingleBuiltinElement = wrapSingleBuiltinElement;
	}
	
	private CreatePropertyInfos wrapSingleHeteroElement;

	public CreatePropertyInfos getWrapSingleHeteroElement() {
		return wrapSingleHeteroElement;
	}

	@Required
	public void setWrapSingleHeteroElement(
			CreatePropertyInfos wrapSingleHeteroElement) {
		this.wrapSingleHeteroElement = wrapSingleHeteroElement;
	}
	

	private CreatePropertyInfos wrapCollectionBuiltinElement;

	public CreatePropertyInfos getWrapCollectionBuiltinElement() {
		return wrapCollectionBuiltinElement;
	}

	@Required
	public void setWrapCollectionBuiltinElement(
			CreatePropertyInfos wrapCollectionBuiltinElement) {
		this.wrapCollectionBuiltinElement = wrapCollectionBuiltinElement;
	}

	private CreatePropertyInfos wrapCollectionEnumElement;

	public CreatePropertyInfos getWrapCollectionEnumElement() {
		return wrapCollectionEnumElement;
	}

	@Required
	public void setWrapCollectionEnumElement(
			CreatePropertyInfos wrapCollectionEnumElement) {
		this.wrapCollectionEnumElement = wrapCollectionEnumElement;
	}
	
	private CreatePropertyInfos wrapCollectionHeteroElement;

	public CreatePropertyInfos getWrapCollectionHeteroElement() {
		return wrapCollectionHeteroElement;
	}

	@Required
	public void setWrapCollectionHeteroElement(
			CreatePropertyInfos wrapCollectionHeteroElement) {
		this.wrapCollectionHeteroElement = wrapCollectionHeteroElement;
	}
	

	// Reference

	private CreatePropertyInfos wrapSingleBuiltinElementReference;

	public CreatePropertyInfos getWrapSingleBuiltinElementReference() {
		return wrapSingleBuiltinElementReference;
	}

	@Required
	public void setWrapSingleBuiltinElementReference(
			CreatePropertyInfos wrapSingleBuiltinElementReference) {
		this.wrapSingleBuiltinElementReference = wrapSingleBuiltinElementReference;
	}

	private CreatePropertyInfos wrapSingleEnumElementReference;

	public CreatePropertyInfos getWrapSingleEnumElementReference() {
		return wrapSingleEnumElementReference;
	}

	@Required
	public void setWrapSingleEnumElementReference(
			CreatePropertyInfos wrapSingleEnumElementReference) {
		this.wrapSingleEnumElementReference = wrapSingleEnumElementReference;
	}

	private CreatePropertyInfos wrapSingleClassElementReference;

	public CreatePropertyInfos getWrapSingleClassElementReference() {
		return wrapSingleClassElementReference;
	}

	@Required
	public void setWrapSingleClassElementReference(
			CreatePropertyInfos wrapSingleClassElementReference) {
		this.wrapSingleClassElementReference = wrapSingleClassElementReference;
	}

	private CreatePropertyInfos wrapSingleSubstitutedElementReference;

	public CreatePropertyInfos getWrapSingleSubstitutedElementReference() {
		return wrapSingleSubstitutedElementReference;
	}

	@Required
	public void setWrapSingleSubstitutedElementReference(
			CreatePropertyInfos wrapSingleSubstitutedElementReference) {
		this.wrapSingleSubstitutedElementReference = wrapSingleSubstitutedElementReference;
	}

	private CreatePropertyInfos wrapSingleHeteroReference;

	public CreatePropertyInfos getWrapSingleHeteroReference() {
		return wrapSingleHeteroReference;
	}

	@Required
	public void setWrapSingleHeteroReference(
			CreatePropertyInfos wrapSingleHeteroReference) {
		this.wrapSingleHeteroReference = wrapSingleHeteroReference;
	}
	
	private CreatePropertyInfos wrapSingleClassReference;

	public CreatePropertyInfos getWrapSingleClassReference() {
		return wrapSingleClassReference;
	}

	@Required
	public void setWrapSingleClassReference(
			CreatePropertyInfos wrapSingleClassReference) {
		this.wrapSingleClassReference = wrapSingleClassReference;
	}

	private CreatePropertyInfos wrapSingleWildcardReference;

	public CreatePropertyInfos getWrapSingleWildcardReference() {
		return wrapSingleWildcardReference;
	}

	@Required
	public void setWrapSingleWildcardReference(
			CreatePropertyInfos wrapSingleWildcardReference) {
		this.wrapSingleWildcardReference = wrapSingleWildcardReference;
	}

	private CreatePropertyInfos wrapCollectionHeteroReference;

	public CreatePropertyInfos getWrapCollectionHeteroReference() {
		return wrapCollectionHeteroReference;
	}

	public void setWrapCollectionHeteroReference(
			CreatePropertyInfos wrapCollectionHeteroReference) {
		this.wrapCollectionHeteroReference = wrapCollectionHeteroReference;
	}
	
	private CreatePropertyInfos wrapCollectionWildcardReference;

	public CreatePropertyInfos getWrapCollectionWildcardReference() {
		return wrapCollectionWildcardReference;
	}

	public void setWrapCollectionWildcardReference(
			CreatePropertyInfos wrapCollectionWildcardReference) {
		this.wrapCollectionWildcardReference = wrapCollectionWildcardReference;
	}
	

	private AdaptTypeUse adaptBuiltinTypeUse;

	public AdaptTypeUse getAdaptBuiltinTypeUse() {
		return adaptBuiltinTypeUse;
	}

	@Required
	public void setAdaptBuiltinTypeUse(AdaptTypeUse adaptBuiltinTypeUse) {
		this.adaptBuiltinTypeUse = adaptBuiltinTypeUse;
	}
}
