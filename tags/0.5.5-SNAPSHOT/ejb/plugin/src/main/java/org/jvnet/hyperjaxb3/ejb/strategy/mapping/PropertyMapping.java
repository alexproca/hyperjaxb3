package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.xsd.util.XMLSchemaConstrants;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.hyperjaxb3.xsom.TypeUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.tools.xjc.outline.FieldOutline;
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

}
