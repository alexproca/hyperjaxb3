package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class AttributesMapping implements ClassOutlineMapping<Attributes> {

	protected Log logger = LogFactory.getLog(getClass());

	public Attributes process(Mapping context, ClassOutline classOutline,
			Options options) {

		final Attributes attributes = new Attributes();

		final FieldOutline[] fieldOutlines = classOutline.getDeclaredFields();
		for (final FieldOutline fieldOutline : fieldOutlines) {

			final Object attributeMapping = getAttributeMapping(context,
					fieldOutline, options).process(context, fieldOutline,
					options);

			if (attributeMapping instanceof Id) {
				if (attributes.getEmbeddedId() == null) {
					attributes.getId().add((Id) attributeMapping);
				} else {
					logger
							.error("Could not add an id element to the attributes of the class ["
									+

									fieldOutline.parent().target.getName()
									+ "] because they already contain an embedded-id element.");
				}
			} else if (attributeMapping instanceof EmbeddedId) {
				if (!attributes.getId().isEmpty()) {
					logger
							.error("Could not add an embedded-id element to the attributes of the class ["
									+

									fieldOutline.parent().target.getName()
									+ "] because they already contain an id element.");
				} else if (attributes.getEmbeddedId() != null) {
					logger
							.error("Could not add an embedded-id element to the attributes of the class ["
									+

									fieldOutline.parent().target.getName()
									+ "] because they already contain an embedded-id element.");
				} else {
					attributes.setEmbeddedId((EmbeddedId) attributeMapping);
				}
			} else if (attributeMapping instanceof Basic) {
				attributes.getBasic().add((Basic) attributeMapping);
			} else if (attributeMapping instanceof Version) {
				attributes.getVersion().add((Version) attributeMapping);
			} else if (attributeMapping instanceof ManyToOne) {
				attributes.getManyToOne().add((ManyToOne) attributeMapping);
			} else if (attributeMapping instanceof OneToMany) {
				attributes.getOneToMany().add((OneToMany) attributeMapping);
			} else if (attributeMapping instanceof OneToOne) {
				attributes.getOneToOne().add((OneToOne) attributeMapping);
			} else if (attributeMapping instanceof ManyToMany) {
				attributes.getManyToMany().add((ManyToMany) attributeMapping);
			} else if (attributeMapping instanceof Embedded) {
				attributes.getEmbedded().add((Embedded) attributeMapping);
			} else if (attributeMapping instanceof Transient) {
				attributes.getTransient().add((Transient) attributeMapping);
			}
		}
		return attributes;
	}

	public FieldOutlineMapping<?> getAttributeMapping(Mapping context,
			FieldOutline fieldOutline, Options options) {
		if (context.getIgnoring().isFieldOutlineIgnored(null, fieldOutline,
				options)) {
			return context.getTransientMapping();
		} else if (isFieldOutlineId(fieldOutline)) {
			return context.getIdMapping();
		} else if (isFieldOutlineVersion(fieldOutline)) {
			return context.getVersionMapping();
		} else {

			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			if (!propertyInfo.isCollection()) {
				logger.trace("Field outline  [" + propertyInfo.getName(true)
						+ "] is a single field.");

				final Collection<? extends CTypeInfo> types = propertyInfo
						.ref();

				if (types.size() == 1) {
					logger.trace("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous single field.");

					if (isFieldOutlineBasic(fieldOutline)) {
						return context.getBasicMapping();
					} else

					if (isFieldOutlineComplex(fieldOutline)) {
						logger.trace("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a complex field.");
						return context.getToOneMapping();
					}
				} else {
					logger.warn("Field outline  [" + propertyInfo.getName(true)
							+ "] is a heterogeneous single field.");
				}
			} else {
				logger.trace("Field outline [" + propertyInfo.getName(true)
						+ "] is a collection field.");

				final Collection<? extends CTypeInfo> types = propertyInfo
						.ref();

				if (types.size() == 1) {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous collection field.");
					if (isFieldOutlineComplex(fieldOutline)) {
						logger
								.debug("Field outline  ["
										+ propertyInfo.getName(true)
										+ "] is a complex homogeneous collection field.");
						return context.getToManyMapping();
					}
				} else {
					logger.warn("Field outline  [" + propertyInfo.getName(true)
							+ "] is a heterogenous collection field.");
				}

			}

			logger.error("Field outline  [" +

			((CClassInfo) propertyInfo.parent()).getName() + "."
					+ propertyInfo.getName(true)
					+ "] could not be annotated. It will be made transient.");
			return context.getTransientMapping();
		}
	}

	public boolean isFieldOutlineId(FieldOutline fieldOutline) {
		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.ID_ELEMENT_NAME);
	}

	public boolean isFieldOutlineVersion(FieldOutline fieldOutline) {

		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.VERSION_ELEMENT_NAME);
	}

	public boolean isFieldOutlineBasic(FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

		final JType type = getter.type();

		return JTypeUtils.isBasicType(type);
	}

	public boolean isFieldOutlineComplex(FieldOutline fieldOutline) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return type instanceof CClass;
	}
}
