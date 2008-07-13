package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;
import org.jvnet.annox.util.ClassUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreateDefaultIdPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.TransientSingleField;
import org.jvnet.hyperjaxb3.xjc.model.CExternalLeafInfo;

import com.sun.tools.xjc.generator.bean.field.GenericFieldRenderer;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;

public class DefaultCreateDefaultIdPropertyInfos implements
		CreateDefaultIdPropertyInfos {

	private boolean transientField;

	public boolean isTransient() {
		return transientField;
	}

	public void setTransient(boolean transientField) {
		this.transientField = transientField;
	}

	public Collection<CPropertyInfo> process(ProcessModel context,
			CClassInfo classInfo) {

		final CPropertyInfo propertyInfo = createPropertyInfo(context,
				classInfo);

		return Collections.singletonList(propertyInfo);

	}

	protected CPropertyInfo createPropertyInfo(ProcessModel context,
			CClassInfo classInfo) {
		final String propertyName = getPropertyName(context, classInfo);
		final QName attributeName = getAttributeName(context, classInfo);
		final CNonElement propertyTypeInfo = getPropertyTypeInfo(context,
				classInfo);
		final CCustomizations customizations = new CCustomizations();
		final CPluginCustomization id = createIdCustomization(context,
				classInfo);
		customizations.add(id);
		//		
		// CPluginCustomization generated = CustomizationUtils
		// .createCustomization(org.jvnet.jaxb2_commons.plugin.Customizations.GENERATED_ELEMENT_NAME);
		// generated.markAsAcknowledged();
		// customizations.add(generated);

		final CPropertyInfo propertyInfo = new CAttributePropertyInfo(
				propertyName, null, customizations, null, attributeName,
				propertyTypeInfo, propertyTypeInfo.getTypeName(), false);

		if (isTransient()) {
			propertyInfo.realization = new GenericFieldRenderer(
					TransientSingleField.class);
		}

		Customizations.markGenerated(propertyInfo);

		// final CPropertyInfo propertyInfo = new CElementPropertyInfo(
		// propertyName,
		// CollectionMode.NOT_REPEATED,
		// ID.NONE,
		// null,
		//				
		// null, new CCustomizations(), null, attributeName,
		// propertyTypeInfo, false);
		/*
		 * final CElementPropertyInfo propertyInfo = new CElementPropertyInfo(
		 * propertyName, propertyTypeInfo.isCollection() ?
		 * CollectionMode.REPEATED_VALUE : CollectionMode.NOT_REPEATED,
		 * propertyTypeInfo.idUse(), propertyTypeInfo .getExpectedMimeType(),
		 * null, new CCustomizations(), null, true);
		 * 
		 * 
		 * final CTypeRef typeRef = new
		 * CTypeRef((CBuiltinLeafInfo)propertyTypeInfo, attributeName, false,
		 * null);
		 * 
		 * propertyInfo.getTypes().add(typeRef);
		 */
		return propertyInfo;
	}

	public String getPropertyName(ProcessModel context, CClassInfo classInfo) {
		final Id id = context.getCustomizations().getId(classInfo);
		final String name = id.getName();
		Validate.notEmpty(name, "The hj:/@name attribute must not be empty.");
		return name;
	}

	public QName getAttributeName(ProcessModel context, CClassInfo classInfo) {
		final Id id = context.getCustomizations().getId(classInfo);
		final QName attributeName = id.getAttributeName();
		return attributeName != null ? attributeName : new QName(
				getPropertyName(context, classInfo));
	}

	public CNonElement getPropertyTypeInfo(ProcessModel context,
			CClassInfo classInfo) {
		final Id id = context.getCustomizations().getId(classInfo);
		final String javaType = id.getJavaType();
		Validate.notEmpty(javaType,
				"The hj:/@javaType attribute must not be empty.");
		final QName schemaType = id.getSchemaType();
		Validate.notNull(schemaType,
				"The hj:/@schemaType attribute must not be null.");
		try {
			final Class theClass = ClassUtils.forName(javaType);
			return new CExternalLeafInfo(theClass, schemaType, null);
		} catch (ClassNotFoundException cnfex) {
			throw new IllegalArgumentException(
					"Class name ["
							+ javaType
							+ "] provided in the hj:id/@javaType attribute could not be resolved.",
					cnfex);
		}
	}

	public CPluginCustomization createIdCustomization(ProcessModel context,
			CClassInfo classInfo) {
		final Id id = context.getCustomizations().getId(classInfo);

		final JAXBElement<Id> idElement = Customizations
				.getCustomizationsObjectFactory().createId(id);

		return Customizations.createCustomization(idElement);

	}

}