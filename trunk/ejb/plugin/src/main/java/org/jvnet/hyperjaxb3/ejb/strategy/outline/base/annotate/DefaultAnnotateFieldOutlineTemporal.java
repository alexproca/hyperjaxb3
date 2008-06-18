package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.namespace.QName;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.xsd.util.XMLSchemaConstrants;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeVisitor;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.xsom.XSComponent;

public class DefaultAnnotateFieldOutlineTemporal implements
		AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				3);

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineBasic().process(outlineProcessor,
						fieldOutline, options));

		final XAnnotation temporal = new XAnnotation(Temporal.class,
				createValue(outlineProcessor, fieldOutline, options));

		xannotations.add(temporal);
		return xannotations;
	}

	private TemporalType defaultValue = TemporalType.TIMESTAMP;

	public TemporalType getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(TemporalType defaultValue) {
		this.defaultValue = defaultValue;
	}

	public TemporalType getValue(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);
		final JType type = getter.type();
		final JCodeModel codeModel = type.owner();
		// Detect SQL types
		if (codeModel.ref(java.sql.Time.class).equals(type)) {
			return TemporalType.TIME;
		} else if (codeModel.ref(java.sql.Date.class).equals(type)) {
			return TemporalType.DATE;
		} else if (codeModel.ref(java.sql.Timestamp.class).equals(type)) {
			return TemporalType.TIMESTAMP;
		} else if (codeModel.ref(java.util.Calendar.class).equals(type)) {
			return TemporalType.TIMESTAMP;
		} else {
			final List<QName> typeNames;
			final XSComponent schemaComponent = fieldOutline.getPropertyInfo()
					.getSchemaComponent();
			if (schemaComponent != null) {

				final SimpleTypeVisitor visitor = new SimpleTypeVisitor();
				schemaComponent.visit(visitor);
				typeNames = visitor.getTypeNames();
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
				return TemporalType.DATE;
			} else if (typeNames.contains(XMLSchemaConstrants.TIME_QNAME)) {
				return TemporalType.TIME;
			} else if (typeNames.contains(XMLSchemaConstrants.DATE_TIME_QNAME)) {
				return TemporalType.TIMESTAMP;
			} else {
				return TemporalType.TIMESTAMP;
			}
		}
	}

	public XEnum createValue(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final TemporalType value = getValue(outlineProcessor, fieldOutline,
				options);

		if (value == null) {
			return null;
		} else {
			return new XAnnotationField.XEnum("value", value,
					TemporalType.class);
		}
	}
}
