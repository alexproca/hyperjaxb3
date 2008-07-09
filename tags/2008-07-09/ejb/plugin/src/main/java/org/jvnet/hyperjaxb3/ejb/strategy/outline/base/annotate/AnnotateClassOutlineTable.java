package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateClassOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.java.xml.ns.persistence.orm.Table;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class AnnotateClassOutlineTable implements AnnotateClassOutline {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options) {

		final Table table = CustomizationUtils.containsCustomization(
				classOutline.target, Customizations.TABLE_ELEMENT_NAME) ? (Table) Customizations
				.getCustomization(classOutline.target,
						Customizations.TABLE_ELEMENT_NAME)
				: new Table();

		if (table.getName() == null) {
			table.setName(outlineProcessor.getNaming().getEntityTableName(
					outlineProcessor, classOutline, options));
		}

		return createTable(outlineProcessor, classOutline, options, table);
	}

	public Collection<XAnnotation> createTable(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options, Table table) {

		assert table.getName() != null;

		final XAnnotation xTable = new XAnnotation(
				javax.persistence.Table.class,
				//
				AnnotationUtils.create("name", table.getName()),
				//
				AnnotationUtils.create("catalog", table.getCatalog()),
				//
				AnnotationUtils.create("schema", table.getSchema()),
				//
				createTable$UniqueConstraints(table.getUniqueConstraint()));
		return Collections.singletonList(xTable);
	}

	public XAnnotationField.XAnnotationArray createTable$UniqueConstraints(
			List<com.sun.java.xml.ns.persistence.orm.UniqueConstraint> cUniqueConstraints) {
		if (cUniqueConstraints == null || cUniqueConstraints.isEmpty()) {
			return null;
		} else {
			final List<XAnnotation> uniqueConstraints = new LinkedList<XAnnotation>();
			for (final com.sun.java.xml.ns.persistence.orm.UniqueConstraint cUniqueConstraint : cUniqueConstraints) {
				uniqueConstraints
						.add(createUniqueConstraint(cUniqueConstraint));
			}

			final XAnnotationField.XAnnotationArray xUniqueConstraints = new XAnnotationField.XAnnotationArray(
					"uniqueConstraints",
					uniqueConstraints.toArray(new XAnnotation[uniqueConstraints
							.size()]), UniqueConstraint.class);
			return xUniqueConstraints;
		}
	}

	public XAnnotation createUniqueConstraint(
			final com.sun.java.xml.ns.persistence.orm.UniqueConstraint cUniqueConstraint) {
		final List<String> columnNames = cUniqueConstraint.getColumnName();
		final XAnnotation uniqueConstraint = new XAnnotation(
				UniqueConstraint.class, AnnotationUtils.create("columnNames",
						columnNames.toArray(new String[columnNames.size()])));
		return uniqueConstraint;
	}
}
