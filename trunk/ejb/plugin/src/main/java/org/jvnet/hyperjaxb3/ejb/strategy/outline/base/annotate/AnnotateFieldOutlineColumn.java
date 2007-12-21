package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.Collection;
import java.util.Collections;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class AnnotateFieldOutlineColumn implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Column column = CustomizationUtils.containsCustomization(
				propertyInfo, Customizations.COLUMN_ELEMENT_NAME) ? (Column) Customizations
				.getCustomization(propertyInfo,
						Customizations.COLUMN_ELEMENT_NAME)
				: new Column();

		if (column.getName() == null) {
			column.setName(context.getNaming().getColumnName(context,
					fieldOutline, options));
		}

		// If string
		if (column.getLength() == null) {
			final Long length = SimpleTypeAnalyzer.getLength(fieldOutline
					.getPropertyInfo().getSchemaComponent());

			if (length != null) {
				column.setLength(length.intValue());
			} else {
				final Long maxLength = SimpleTypeAnalyzer
						.getMaxLength(fieldOutline.getPropertyInfo()
								.getSchemaComponent());
				if (maxLength != null) {
					column.setLength(maxLength.intValue());
				} else {
					final Long minLength = SimpleTypeAnalyzer
							.getMinLength(fieldOutline.getPropertyInfo()
									.getSchemaComponent());
					if (minLength != null) {
						int intMinLength = minLength.intValue();
						if (intMinLength > 127) {
							column.setLength(intMinLength * 2);
						}
					}
				}
			}
		}
		
		// If decimal
		{
		if (column.getPrecision() == null)
		{
			final Long totalDigits = SimpleTypeAnalyzer.getTotalDigits(fieldOutline
					.getPropertyInfo().getSchemaComponent());
			if (totalDigits != null)
			{
				column.setPrecision(totalDigits.intValue());
			}
			
		}

		if (column.getScale() == null)
		{
			final Long fractionDigits = SimpleTypeAnalyzer.getFractionDigits(fieldOutline
					.getPropertyInfo().getSchemaComponent());
			if (fractionDigits != null)
			{
				column.setScale(fractionDigits.intValue());
			}
		}
		}
		

		return Collections.singletonList(createColumn(column));
	}

	protected XAnnotation createColumn(Column column) {

		return new XAnnotation(javax.persistence.Column.class,
		//
				AnnotationUtils.create("name", column.getName()),
				//
				AnnotationUtils.create("unique", column.isUnique()),
				//
				AnnotationUtils.create("nullable", column.isNullable()),
				//
				AnnotationUtils.create("insertable", column.isInsertable()),
				//
				AnnotationUtils.create("updatable", column.isUpdatable()),
				//
				AnnotationUtils.create("columnDefinition", column
						.getColumnDefinition()),
				//
				AnnotationUtils.create("table", column.getTable()),
				//
				AnnotationUtils.create("length", column.getLength()),
				//
				AnnotationUtils.create("precision", column.getPrecision()),
				//
				AnnotationUtils.create("scale", column.getScale()));
	}
}
