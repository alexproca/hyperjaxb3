package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.jaxb2_commons.util.ClassUtils;
import org.jvnet.jaxb2_commons.util.CodeModelUtils;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.xml.sax.Locator;

import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.IdClass;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizations;
import com.sun.tools.xjc.model.CElementPropertyInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CPropertyVisitor;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.CElementPropertyInfo.CollectionMode;
import com.sun.xml.xsom.XSComponent;

public class CreateIdClass implements ProcessClassInfo {

	public Collection<CClassInfo> process(ProcessModel context,
			CClassInfo classInfo) {

		final XSComponent component = classInfo.getSchemaComponent();

		final Collection<CPropertyInfo> propertyInfos = context
				.getGetIdPropertyInfos().process(context, classInfo);

		if (propertyInfos.size() > 1) {

			context.getGetIdPropertyInfos().process(context, classInfo);

			final CClassInfo idClassInfo = new CClassInfo(classInfo.model,
					classInfo, classInfo.shortName + "Id", null, null, null,

					component, new CCustomizations());

			if (!classInfo.model.serializable) {
				org.jvnet.jaxb2_commons.plugin.inheritance.Customizations
						._implements(idClassInfo, Serializable.class.getName());
			}

			Customizations.markIgnored(idClassInfo);
			// Customizations.markGenerated(idClassInfo);

			for (CPropertyInfo propertyInfo : propertyInfos) {
				idClassInfo.addProperty(propertyInfo
						.accept(new CPropertyVisitor<CPropertyInfo>() {

							public CPropertyInfo onAttribute(
									CAttributePropertyInfo propertyInfo) {
								return new CAttributePropertyInfo(propertyInfo
										.getName(true), propertyInfo
										.getSchemaComponent(),
										new CCustomizations(), null,
										propertyInfo.getXmlName(), propertyInfo
												.getTarget(), propertyInfo
												.getSchemaType(), false);
							}

							public CPropertyInfo onElement(
									CElementPropertyInfo propertyInfo) {

								return new CElementPropertyInfo(

										propertyInfo.getName(true),
										propertyInfo.isCollection() ? CollectionMode.REPEATED_ELEMENT
												: CollectionMode.NOT_REPEATED,
										propertyInfo.id(), propertyInfo
												.getExpectedMimeType(),
										propertyInfo.getSchemaComponent(),
										new CCustomizations(), (Locator) null,
										false);
							}

							public CPropertyInfo onReference(
									CReferencePropertyInfo propertyInfo) {
								return new CReferencePropertyInfo(propertyInfo
										.getName(true), propertyInfo
										.isCollection(), false, propertyInfo
										.isMixed(), propertyInfo
										.getSchemaComponent(),
										new CCustomizations(), null,
										propertyInfo.isDummy(), propertyInfo
												.isContent(), propertyInfo
												.isMixedExtendedCust());
							}

							public CPropertyInfo onValue(
									CValuePropertyInfo propertyInfo) {
								return new CValuePropertyInfo(propertyInfo
										.getName(true), propertyInfo
										.getSchemaComponent(),
										new CCustomizations(), null,
										propertyInfo.getTarget(), propertyInfo
												.getSchemaType());
							}
						}));
			}

			final IdClass idClass = new IdClass();

			idClass.setClazz(ClassUtils.getPackagedClassName(idClassInfo));

			final Object customization = context.getCustomizing()
					.getEntityOrMappedSuperclassOrEmbeddable(classInfo);

			if (customization instanceof Entity) {
				((Entity) customization).setIdClass(idClass);
			} else if (customization instanceof MappedSuperclass) {
				((MappedSuperclass) customization).setIdClass(idClass);
			}

			return Collections.singleton(idClassInfo);
		} else {
			return Collections.emptyList();
		}
	}

}