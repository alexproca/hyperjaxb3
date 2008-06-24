package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineManyToOne extends
		AbstractAnnotateFieldOutlineXToX {

	// TODO #73 Target entity

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = createManyToOne(
				outlineProcessor, fieldOutline, options);
		return xannotations;
	}

	private Collection<XAnnotation> createManyToOne(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options) {
		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne cManyToOne =

		outlineProcessor.getCustomizations().getManyToOne(fieldOutline);

		xannotations.add(createManyToOne(outlineProcessor, fieldOutline,
				options, cManyToOne));

		final Collection<XAnnotation> content;
		if (cManyToOne.getJoinTable() != null) {
			final com.sun.java.xml.ns.persistence.orm.JoinTable cjoinTable = cManyToOne
					.getJoinTable();
			if (cjoinTable.getName() == null) {
				cjoinTable.setName(outlineProcessor.getNaming()
						.getManyToOneJoinTableName(outlineProcessor,
								fieldOutline, options));
			}

			content = createJoinTable(outlineProcessor, fieldOutline, options,
					cjoinTable);
		} else {
			content = createJoinColumns(outlineProcessor, fieldOutline,
					options, cManyToOne);
		}
		xannotations.addAll(content);

		return xannotations;
	}

	private XAnnotation createManyToOne(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne cManyToOne) {
		final XAnnotation manyToOne = new XAnnotation(ManyToOne.class,
		// 
				createTargetEntity(outlineProcessor, fieldOutline, options),
				//
				createFetch(outlineProcessor, fieldOutline, options, cManyToOne
						.getFetch()),
				//
				createCascade(outlineProcessor, fieldOutline, options,
						cManyToOne.getCascade()),
				//
				createOptional(outlineProcessor, fieldOutline, options,
						cManyToOne.isOptional()));
		return manyToOne;
	}

	public XAnnotationField createOptional(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options, Boolean optional) {

		return AnnotationUtils.create("optional", optional);

	}

	public XAnnotation createJoinColumn(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final String joinColumnName = outlineProcessor.getNaming()
				.getManyToOneJoinColumnName(outlineProcessor, fieldOutline,
						options);

		final XAnnotation joinColumn = new XAnnotation(JoinColumn.class,
				new XAnnotationField.XString("name", joinColumnName));

		return joinColumn;
	}

	public Collection<XAnnotation> createJoinTable(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			com.sun.java.xml.ns.persistence.orm.JoinTable cjoinTable) {

		final String joinTableName = (cjoinTable != null && cjoinTable
				.getName() != null) ? cjoinTable.getName() : outlineProcessor
				.getNaming().getManyToOneJoinTableName(outlineProcessor,
						fieldOutline, options);

		final String joinColumnName = outlineProcessor.getNaming()
				.getManyToOneJoinTableJoinColumnName(outlineProcessor,
						fieldOutline, options);

		final String inverseJoinColumnName = outlineProcessor.getNaming()
				.getManyToOneJoinTableInverseJoinColumnName(outlineProcessor,
						fieldOutline, options);

		final XAnnotation joinColumn = new XAnnotation(JoinColumn.class,
				new XAnnotationField.XString("name", joinColumnName));

		final XAnnotation inverseJoinColumn = new XAnnotation(JoinColumn.class,
				new XAnnotationField.XString("name", inverseJoinColumnName));

		final XAnnotation joinTable = new XAnnotation(JoinTable.class,
				//
				new XAnnotationField.XString("name", cjoinTable.getName()),
				new XAnnotationField.XString("catalog", cjoinTable.getCatalog()),
				new XAnnotationField.XString("schema", cjoinTable.getSchema()),
				
				new XAnnotationField.XAnnotationArray("joinColumns",
						new XAnnotation[] { joinColumn }, JoinColumn.class),
				new XAnnotationField.XAnnotationArray("inverseJoinColumns",
						new XAnnotation[] { inverseJoinColumn },
						JoinColumn.class));

		return Collections.singletonList(joinTable);
	}

	public Collection<XAnnotation> createJoinColumns(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne cmanyToOne) {

		final List<com.sun.java.xml.ns.persistence.orm.JoinColumn> joinColumns = new LinkedList<com.sun.java.xml.ns.persistence.orm.JoinColumn>();
		if (cmanyToOne.getJoinColumn().isEmpty()) {
			joinColumns
					.add(new com.sun.java.xml.ns.persistence.orm.JoinColumn());
		} else {
			joinColumns.addAll(cmanyToOne.getJoinColumn());
		}

		final Collection<XAnnotation> joinColumnAnnotations = new ArrayList<XAnnotation>(
				joinColumns.size());
		for (final com.sun.java.xml.ns.persistence.orm.JoinColumn joinColumn : joinColumns) {
			joinColumnAnnotations.add(createJoinColumn(outlineProcessor,
					fieldOutline, options, joinColumn));

		}
		return joinColumnAnnotations;
	}

	private XAnnotation createJoinColumn(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			com.sun.java.xml.ns.persistence.orm.JoinColumn column) {
		final String defaultColumnName = outlineProcessor.getNaming()
				.getManyToOneJoinColumnName(outlineProcessor, fieldOutline,
						options);
		final XAnnotation joinColumn = new XAnnotation(JoinColumn.class,
				AnnotationUtils.create("name", column.getName(),
						defaultColumnName),
				//
				AnnotationUtils.create("referencedColumnName", column
						.getReferencedColumnName()),
				//
				AnnotationUtils.create("unique", column.isUnique()),
				//
				AnnotationUtils.create("nullable", column.isNullable()),
				//
				AnnotationUtils.create("insertable", column.isInsertable()),
				//
				AnnotationUtils.create("updateable", column.isUpdatable()),
				//
				AnnotationUtils.create("columnDefinition", column
						.getColumnDefinition()),
				//
				AnnotationUtils.create("table", column.getTable()));
		return joinColumn;
	}
}
