package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
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

		return Collections.singletonList(createTable(table));
	}

	protected XAnnotation createTable(Table table) {

		assert table.getName() != null;

		final XAnnotationField.XString name = new XAnnotationField.XString(
				"name", table.getName());
		final XAnnotationField.XString catalog = table.getCatalog() == null ? null
				: new XAnnotationField.XString("catalog", table.getName());
		final XAnnotationField.XString schema = table.getSchema() == null ? null
				: new XAnnotationField.XString("schema", table.getName());

		return new XAnnotation(javax.persistence.Table.class, name, catalog,
				schema);
	}
}
