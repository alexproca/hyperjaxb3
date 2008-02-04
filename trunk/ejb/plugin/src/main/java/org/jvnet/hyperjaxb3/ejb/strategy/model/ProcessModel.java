package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.strategy.model.base.DefaultProcessPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ignoring.Ignoring;

import com.sun.tools.xjc.model.CClassInfo;

public interface ProcessModel extends EjbModelProcessor<Collection<CClassInfo>> {

	public ProcessClassInfo getProcessClassInfo();

	public ProcessPropertyInfos getProcessPropertyInfos();

	public CreateDefaultIdPropertyInfos getCreateDefaultIdPropertyInfos();

	public GetTypes getGetTypes();

	// Value

	public CreatePropertyInfos getWrapSingleBuiltinValue();

	public CreatePropertyInfos getWrapCollectionBuiltinValue();

	public CreatePropertyInfos getWrapCollectionEnumValue();

	// Attribute

	public CreatePropertyInfos getWrapSingleBuiltinAttribute();

	public CreatePropertyInfos getWrapCollectionBuiltinAttribute();

	public CreatePropertyInfos getWrapCollectionEnumAttribute();

	// Element

	public CreatePropertyInfos getWrapSingleBuiltinElement();

	public CreatePropertyInfos getWrapSingleHeteroElement();

	public CreatePropertyInfos getWrapCollectionBuiltinElement();

	public CreatePropertyInfos getWrapCollectionEnumElement();

	public CreatePropertyInfos getWrapCollectionHeteroElement();

	// Reference

	public CreatePropertyInfos getWrapSingleBuiltinElementReference();

	public CreatePropertyInfos getWrapSingleEnumElementReference();

	public CreatePropertyInfos getWrapSingleClassElementReference();

	public CreatePropertyInfos getWrapSingleSubstitutedElementReference();
	
	public CreatePropertyInfos getWrapSingleClassReference();

	public CreatePropertyInfos getWrapSingleWildcardReference();

	public CreatePropertyInfos getWrapSingleHeteroReference();

	public CreatePropertyInfos getWrapCollectionHeteroReference();

	public CreatePropertyInfos getWrapCollectionWildcardReference();

	public AdaptTypeUse getAdaptBuiltinTypeUse();


	public Ignoring getIgnoring();
}
