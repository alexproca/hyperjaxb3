package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.xsd.util.XMLSchemaConstrants;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.hyperjaxb3.xsom.TypeUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.AssociationOverride;
import com.sun.java.xml.ns.persistence.orm.AttributeOverride;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;
import com.sun.xml.xsom.XSComponent;

public class PropertyMapping {

	public Column createColumn(Mapping context, FieldOutline fieldOutline,
			Column column) {

		if (column == null) {
			column = new Column();
		}

		if (column.getName() == null || "##default".equals(column.getName())) {
			column.setName(createColumn$Name(context, fieldOutline));
		}

		// If string
		if (column.getLength() == null) {
			column.setLength(createColumn$Length(fieldOutline));
		}

		if (column.getPrecision() == null) {
			column.setPrecision(createColumn$Precision(fieldOutline));
		}

		if (column.getScale() == null) {
			column.setScale(createColumn$Scale(fieldOutline));
		}

		return column;
	}

	public String createColumn$Name(Mapping context, FieldOutline fieldOutline) {
		return context.getNaming().getColumn$Name(context, fieldOutline);
	}

	public Integer createColumn$Scale(FieldOutline fieldOutline) {
		final Integer scale;
		final Long fractionDigits = SimpleTypeAnalyzer
				.getFractionDigits(fieldOutline.getPropertyInfo()
						.getSchemaComponent());
		if (fractionDigits != null) {
			scale = fractionDigits.intValue();
		} else {
			scale = null;
		}
		return scale;
	}

	public Integer createColumn$Precision(FieldOutline fieldOutline) {
		final Integer precision;
		final Long totalDigits = SimpleTypeAnalyzer.getTotalDigits(fieldOutline
				.getPropertyInfo().getSchemaComponent());
		if (totalDigits != null) {
			precision = totalDigits.intValue();
		} else {
			precision = null;
		}
		return precision;
	}

	public Integer createColumn$Length(FieldOutline fieldOutline) {
		final Integer finalLength;
		final Long length = SimpleTypeAnalyzer.getLength(fieldOutline
				.getPropertyInfo().getSchemaComponent());

		if (length != null) {
			finalLength = length.intValue();
		} else {
			final Long maxLength = SimpleTypeAnalyzer.getMaxLength(fieldOutline
					.getPropertyInfo().getSchemaComponent());
			if (maxLength != null) {
				finalLength = maxLength.intValue();
			} else {
				final Long minLength = SimpleTypeAnalyzer
						.getMinLength(fieldOutline.getPropertyInfo()
								.getSchemaComponent());
				if (minLength != null) {
					int intMinLength = minLength.intValue();
					if (intMinLength > 127) {
						finalLength = intMinLength * 2;
					} else {
						finalLength = null;
					}
				} else {
					finalLength = null;
				}
			}
		}
		return finalLength;
	}

	public boolean isTemporal(FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);
		final JType type = getter.type();
		return JTypeUtils.isTemporalType(type);
	}

	public String getTemporalType(FieldOutline fieldOutline) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);
		final JType type = getter.type();
		final JCodeModel codeModel = type.owner();
		// Detect SQL types
		if (codeModel.ref(java.sql.Time.class).equals(type)) {
			return "TIME";
		} else if (codeModel.ref(java.sql.Date.class).equals(type)) {
			return "DATE";
		} else if (codeModel.ref(java.sql.Timestamp.class).equals(type)) {
			return "TIMESTAMP";
		} else if (codeModel.ref(java.util.Calendar.class).equals(type)) {
			return "TIMESTAMP";
		} else {
			final List<QName> typeNames;
			final XSComponent schemaComponent = fieldOutline.getPropertyInfo()
					.getSchemaComponent();
			if (schemaComponent != null) {
				typeNames = TypeUtils.getTypeNames(schemaComponent);
			} else {
				typeNames = Collections.emptyList();
			}
			if (typeNames.contains(XMLSchemaConstrants.DATE_QNAME)
					||
					//
					typeNames.contains(XMLSchemaConstrants.G_YEAR_MONTH_QNAME)
					||
					//
					typeNames.contains(XMLSchemaConstrants.G_YEAR_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_MONTH_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_MONTH_DAY_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_DAY_QNAME)) {
				return "DATE";
			} else if (typeNames.contains(XMLSchemaConstrants.TIME_QNAME)) {
				return "TIME";
			} else if (typeNames.contains(XMLSchemaConstrants.DATE_TIME_QNAME)) {
				return "TIMESTAMP";
			} else {
				return "TIMESTAMP";
			}
		}
	}

	public void createAttributeOverride(Mapping context,
			FieldOutline fieldOutline,
			final List<AttributeOverride> attributeOverrides) throws Exception {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo typeInfo = types.iterator().next();

		assert typeInfo instanceof CClassInfo;

		final CClassInfo classInfo = (CClassInfo) typeInfo;

		final Outline outline = fieldOutline.parent().parent();

		final ClassOutline classOutline = outline.getClazz(classInfo);

		final Options options = outline.getModel().options;

		final Map<String, AttributeOverride> attributeOverridesMap = new HashMap<String, AttributeOverride>();
		for (final AttributeOverride attributeOverride : attributeOverrides) {
			attributeOverridesMap.put(attributeOverride.getName(),
					attributeOverride);
		}
		Mapping embeddedMapping = context.createEmbeddedMapping(fieldOutline);

		final EmbeddableAttributes embeddableAttributes = embeddedMapping
				.getEmbeddableAttributesMapping().process(embeddedMapping,
						classOutline, options);

		for (final Basic basic : embeddableAttributes.getBasic()) {
			final String name = basic.getName();
			final AttributeOverride attributeOverride;
			if (!attributeOverridesMap.containsKey(name)) {
				attributeOverride = new AttributeOverride();
				attributeOverride.setName(name);
				attributeOverride.setColumn(basic.getColumn());
				attributeOverridesMap.put(name, attributeOverride);
				attributeOverrides.add(attributeOverride);
			} else {
				attributeOverride = attributeOverridesMap.get(name);
			}

			// TODO Check that column is not null
			if (attributeOverride.getColumn() == null) {
				attributeOverride.setColumn(new Column());
			}
		}
		for (final Embedded embedded : embeddableAttributes.getEmbedded()) {
			final String parentName = embedded.getName();

			for (AttributeOverride embeddedAttributeOverride : embedded
					.getAttributeOverride()) {
				final String childName = embeddedAttributeOverride.getName();
				final String name = parentName + "." + childName;

				final AttributeOverride attributeOverride;
				if (!attributeOverridesMap.containsKey(name)) {
					attributeOverride = new AttributeOverride();
					attributeOverride.setName(name);
					attributeOverride.setColumn(embeddedAttributeOverride
							.getColumn());
					attributeOverridesMap.put(name, attributeOverride);
					attributeOverrides.add(attributeOverride);
				} else {
					attributeOverride = attributeOverridesMap.get(name);
				}

				// TODO Check that column is not null
				if (attributeOverride.getColumn() == null) {
					attributeOverride.setColumn(new Column());
				}
			}
		}
	}

	public void createAssociationOverride(Mapping context,
			FieldOutline fieldOutline,
			final List<AssociationOverride> associationOverrides)
			throws Exception {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo typeInfo = types.iterator().next();

		assert typeInfo instanceof CClassInfo;

		final CClassInfo classInfo = (CClassInfo) typeInfo;

		final Outline outline = fieldOutline.parent().parent();

		final ClassOutline classOutline = outline.getClazz(classInfo);

		final Options options = outline.getModel().options;

		final Map<String, AssociationOverride> associationOverridesMap = new HashMap<String, AssociationOverride>();
		for (final AssociationOverride associationOverride : associationOverrides) {
			associationOverridesMap.put(associationOverride.getName(),
					associationOverride);
		}
		Mapping embeddedMapping = context.createEmbeddedMapping(fieldOutline);

		final EmbeddableAttributes embeddableAttributes = embeddedMapping
				.getEmbeddableAttributesMapping().process(embeddedMapping,
						classOutline, options);

		for (final ManyToOne source : embeddableAttributes.getManyToOne()) {
			final String name = source.getName();
			final AssociationOverride associationOverride;
			if (!associationOverridesMap.containsKey(name)) {
				associationOverride = new AssociationOverride();
				associationOverride.setName(name);
				associationOverride.setJoinTable(source.getJoinTable());
				associationOverride.getJoinColumn().addAll(
						source.getJoinColumn());
				associationOverridesMap.put(name, associationOverride);
				associationOverrides.add(associationOverride);
			} else {
				associationOverride = associationOverridesMap.get(name);
			}
		}
		for (final OneToMany source : embeddableAttributes.getOneToMany()) {
			final String name = source.getName();
			final AssociationOverride associationOverride;
			if (!associationOverridesMap.containsKey(name)) {
				associationOverride = new AssociationOverride();
				associationOverride.setName(name);
				associationOverride.setJoinTable(source.getJoinTable());
				associationOverride.getJoinColumn().addAll(
						source.getJoinColumn());
				associationOverridesMap.put(name, associationOverride);
				associationOverrides.add(associationOverride);
			} else {
				associationOverride = associationOverridesMap.get(name);
			}
		}
		for (final ManyToMany source : embeddableAttributes.getManyToMany()) {
			final String name = source.getName();
			final AssociationOverride associationOverride;
			if (!associationOverridesMap.containsKey(name)) {
				associationOverride = new AssociationOverride();
				associationOverride.setName(name);
				associationOverride.setJoinTable(source.getJoinTable());
				associationOverridesMap.put(name, associationOverride);
				associationOverrides.add(associationOverride);
			} else {
				associationOverride = associationOverridesMap.get(name);
			}
		}
		for (final OneToOne source : embeddableAttributes.getOneToOne()) {
			final String name = source.getName();
			final AssociationOverride associationOverride;
			if (!associationOverridesMap.containsKey(name)) {
				associationOverride = new AssociationOverride();
				associationOverride.setName(name);
				associationOverride.setJoinTable(source.getJoinTable());
				associationOverride.getJoinColumn().addAll(
						source.getJoinColumn());
				associationOverridesMap.put(name, associationOverride);
				associationOverrides.add(associationOverride);
			} else {
				associationOverride = associationOverridesMap.get(name);
			}
		}
		// TODO Element collection

		for (final Embedded embedded : embeddableAttributes.getEmbedded()) {
			final String parentName = embedded.getName();

			for (AssociationOverride embeddedAssociationOverride : embedded
					.getAssociationOverride()) {
				final String childName = embeddedAssociationOverride.getName();
				final String name = parentName + "." + childName;

				final AssociationOverride associationOverride;
				if (!associationOverridesMap.containsKey(name)) {
					associationOverride = new AssociationOverride();
					associationOverride.setName(name);
					associationOverride
							.setJoinTable(embeddedAssociationOverride
									.getJoinTable());
					associationOverride.getJoinColumn().addAll(
							embeddedAssociationOverride.getJoinColumn());
					associationOverridesMap.put(name, associationOverride);
					associationOverrides.add(associationOverride);
				} else {
					associationOverride = associationOverridesMap.get(name);
				}
			}
		}
	}
}
