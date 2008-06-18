package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.ejb.Constants;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeRef;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.xml.bind.v2.model.core.PropertyKind;
import com.sun.xml.xsom.XSComponent;

public abstract class AbstractAdaptPropertyInfo implements
		CreatePropertyInfos {

	public abstract String getPropertyName(ProcessModel context,
			CPropertyInfo propertyInfo);

	public QName getPropertyQName(ProcessModel context,
			CPropertyInfo propertyInfo) {
		final String propertyName = getPropertyName(context, propertyInfo);
		return new QName(Constants.NAMESPACE, propertyName);
	}

	public abstract TypeUse getPropertyType(ProcessModel context,
			CPropertyInfo propertyInfo);

	public abstract PropertyKind getPropertyKind(ProcessModel context,
			CPropertyInfo propertyInfo);

	public final CPropertyInfo createPropertyInfo(ProcessModel context,
			CPropertyInfo propertyInfo) {

		final String propertyName = getPropertyName(context, propertyInfo);
		final XSComponent source = getSchemaComponent(context, propertyInfo);

		final QName propertyQName = getPropertyQName(context, propertyInfo);

		final TypeUse propertyTypeInfo = getPropertyType(context, propertyInfo);

		final PropertyKind propertyKind = getPropertyKind(context, propertyInfo);

		final CPropertyInfo newPropertyInfo;
		final CCustomizations customizations = createCustomizations(context,
				propertyInfo);

		if (PropertyKind.ELEMENT.equals(propertyKind)) {
			newPropertyInfo = createElementPropertyInfo(propertyName,
					source,
					propertyTypeInfo, propertyQName, customizations);
		} else if (PropertyKind.ATTRIBUTE.equals(propertyKind)) {
			newPropertyInfo = createAttributePropertyInfo(propertyName,
					source,
					propertyTypeInfo, propertyQName, customizations);

		} else {
			throw new AssertionError("Unexpected property kind ["
					+ propertyKind + "].");
		}

		Customizations.markGenerated(newPropertyInfo);

		return newPropertyInfo;
	}

	public XSComponent getSchemaComponent(ProcessModel context, CPropertyInfo propertyInfo) {
		return propertyInfo.getSchemaComponent();
	}

	public CCustomizations createCustomizations(ProcessModel context,
			CPropertyInfo propertyInfo) {
		final CCustomizations customizations = CustomizationUtils
				.getCustomizations(propertyInfo);

		final CCustomizations newCustomizations = new CCustomizations();
		if (customizations != null) {
			for (final CPluginCustomization customization : customizations) {
				if (Customizations.NAMESPACE_URI.equals(customization.element
						.getNamespaceURI())) {
					newCustomizations.add(customization);
				}
			}
		}
		return newCustomizations;
	}

	public CPropertyInfo createAttributePropertyInfo(String propertyName,
			XSComponent source,
			TypeUse propertyType, QName propertyQName,
			CCustomizations customizations) {
		final CAttributePropertyInfo propertyInfo = new CAttributePropertyInfo(

		propertyName, source,

		customizations, null, propertyQName, propertyType, propertyType
				.getInfo().getTypeName(), false);
		return propertyInfo;
	}

	public CPropertyInfo createElementPropertyInfo(String propertyName,
			XSComponent source,
			TypeUse propertyType, QName propertyQName,
			CCustomizations customizations) {

		final CNonElement propertyTypeInfo = propertyType.getInfo();

		final CElementPropertyInfo propertyInfo = new CElementPropertyInfo(
				propertyName, CollectionMode.NOT_REPEATED, propertyTypeInfo
						.idUse(), propertyTypeInfo.getExpectedMimeType(), source,
				customizations, null, true);

		final CTypeRef typeRef = new CTypeRef(propertyTypeInfo, propertyQName,
				propertyTypeInfo.getTypeName(), false, null);

		propertyInfo.setAdapter(propertyType.getAdapterUse());

		propertyInfo.getTypes().add(typeRef);
		return propertyInfo;
	}

}
