package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineId extends
		AbstractAnnotateSimpleFieldOutline implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id =
		
		outlineProcessor.getCustomizing().getId(fieldOutline);
		
		assert id != null;
		
		return createId(outlineProcessor, fieldOutline, options, id);
	}

	protected Collection<XAnnotation> createId(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id) {
		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		xannotations.add(new XAnnotation(Id.class));
		xannotations.addAll(createColumn(outlineProcessor, fieldOutline,
				options, id.getColumn()));
		xannotations.add(createGeneratedValue(id.getGeneratedValue()));

		xannotations.addAll(createTemporal(outlineProcessor, fieldOutline,
					options, id.getTemporal()));
		xannotations.addAll(createSequenceGenerator(outlineProcessor,
				fieldOutline, options, id));
		xannotations.addAll(createTableGenerator(outlineProcessor,
				fieldOutline, options, id));
		return xannotations;
	}

	public XAnnotation createGeneratedValue(
			com.sun.java.xml.ns.persistence.orm.GeneratedValue generatedValue) {

		if (generatedValue == null) {
			return null;
		} else {
			final XAnnotationField.XString generator = createGeneratedValue$Generator(generatedValue
					.getGenerator());
			final XAnnotationField.XEnum strategy = createGeneratedValue$Strategy(generatedValue
					.getStrategy());

			return new XAnnotation(GeneratedValue.class, generator, strategy);

		}
	}

	public XAnnotationField.XEnum createGeneratedValue$Strategy(String strategy) {
		return strategy == null ? null : new XAnnotationField.XEnum("strategy",
				GenerationType.valueOf(strategy), GenerationType.class);
	}

	public XAnnotationField.XString createGeneratedValue$Generator(String generator) {
		return generator == null ? null : new XAnnotationField.XString(
				"generator", generator);
	}

	public Collection<XAnnotation> createSequenceGenerator(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id) {

		if (id.getSequenceGenerator() == null) {
			return Collections.emptyList();
		} else {
			final com.sun.java.xml.ns.persistence.orm.SequenceGenerator cSequenceGenerator = id
					.getSequenceGenerator();
			final XAnnotation sequenceGenerator = new XAnnotation(
					SequenceGenerator.class,
					//
					AnnotationUtils
							.create("name", cSequenceGenerator.getName()),
					//
					AnnotationUtils.create("sequenceName", cSequenceGenerator
							.getSequenceName()),
					//
					AnnotationUtils.create("initialValue", cSequenceGenerator
							.getInitialValue()),
					//
					AnnotationUtils.create("allocationSize", cSequenceGenerator
							.getAllocationSize()));
			return Collections.singletonList(sequenceGenerator);
		}
	}

	public Collection<XAnnotation> createTableGenerator(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id) {

		if (id.getTableGenerator() == null) {
			return Collections.emptyList();
		} else {
			final com.sun.java.xml.ns.persistence.orm.TableGenerator cTableGenerator = id
					.getTableGenerator();

			final List<XAnnotation> uniqueConstraints = new LinkedList<XAnnotation>();
			for (final com.sun.java.xml.ns.persistence.orm.UniqueConstraint cUniqueConstraint : cTableGenerator
					.getUniqueConstraint()) {
				final List<String> columnNames = cUniqueConstraint
						.getColumnName();
				final XAnnotation uniqueConstraint = new XAnnotation(
						UniqueConstraint.class, AnnotationUtils.create(
								"columnNames",
								columnNames.toArray(new String[columnNames
										.size()])));
				uniqueConstraints.add(uniqueConstraint);

			}

			final XAnnotation tableGenerator = new XAnnotation(
					TableGenerator.class,
					//
					AnnotationUtils.create("name", cTableGenerator.getName()),
					//
					AnnotationUtils.create("table", cTableGenerator.getTable()),
					//
					AnnotationUtils.create("catalog", cTableGenerator
							.getCatalog()),
					//
					AnnotationUtils.create("schema", cTableGenerator
							.getSchema()),
					//
					AnnotationUtils.create("pkColumnName", cTableGenerator
							.getPkColumnName()),
					//
					AnnotationUtils.create("valueColumnName", cTableGenerator
							.getValueColumnName()),
					//
					AnnotationUtils.create("pkColumnValue", cTableGenerator
							.getPkColumnValue()),
					//
					AnnotationUtils.create("initialValue", cTableGenerator
							.getInitialValue()),
					//
					AnnotationUtils.create("allocationSize", cTableGenerator
							.getAllocationSize()),
					//
					new XAnnotationField.XAnnotationArray("uniqueConstraints",
							uniqueConstraints
									.toArray(new XAnnotation[uniqueConstraints
											.size()]), UniqueConstraint.class));

			return Collections.singletonList(tableGenerator);
		}
	}
}
