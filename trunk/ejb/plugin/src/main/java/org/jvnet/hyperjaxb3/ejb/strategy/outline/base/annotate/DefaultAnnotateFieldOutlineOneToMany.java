package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderBy;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineOneToMany extends
		AbstractAnnotateFieldOutlineXToX {

	// TODO #73 Target entity

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		return createOneToMany(outlineProcessor, fieldOutline, options);
	}

	public Collection<XAnnotation> createOneToMany(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				3);

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany =

		outlineProcessor.getCustomizations().getOneToMany(fieldOutline);

		xannotations.add(createOneToMany(outlineProcessor, fieldOutline,
				options, coneToMany));

		final Collection<XAnnotation> content;
		if (coneToMany.getJoinTable() != null) {
			content = createJoinTable(outlineProcessor, fieldOutline, options,
					coneToMany);
		} else {
			content = createJoinColumns(outlineProcessor, fieldOutline,
					options, coneToMany);
		}

		xannotations.addAll(content);

		xannotations.add(new XAnnotation(OrderBy.class));

		return xannotations;
	}

	public XAnnotation createOneToMany(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {

		return new XAnnotation(javax.persistence.OneToMany.class,
		//
				createTargetEntity(outlineProcessor, fieldOutline, options),
				//
				createCascade(outlineProcessor, fieldOutline, options,
						oneToMany.getCascade()),
				//
				createFetch(outlineProcessor, fieldOutline, options, oneToMany
						.getFetch()),
				//
				createMappedBy(outlineProcessor, fieldOutline, options,
						oneToMany.getMappedBy()));

	}

	public Collection<XAnnotation> createJoinTable(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany) {

		final String joinTableName = (coneToMany.getJoinTable() != null && coneToMany
				.getJoinTable().getName() != null) ? coneToMany.getJoinTable()
				.getName() : outlineProcessor.getNaming()
				.getOneToManyJoinTableName(fieldOutline);

		final String joinColumnName = outlineProcessor.getNaming()
				.getOneToManyJoinTableJoinColumnName(fieldOutline);

		final String inverseJoinColumnName = outlineProcessor.getNaming()
				.getOneToManyJoinTableInverseJoinColumnName(fieldOutline);

		final XAnnotation joinColumn = new XAnnotation(JoinColumn.class,
				new XAnnotationField.XString("name", joinColumnName));

		final XAnnotation inverseJoinColumn = new XAnnotation(JoinColumn.class,
				new XAnnotationField.XString("name", inverseJoinColumnName));

		final XAnnotation joinTable = new XAnnotation(JoinTable.class,
				new XAnnotationField.XString("name", joinTableName),
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
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany) {

		final List<com.sun.java.xml.ns.persistence.orm.JoinColumn> joinColumns = new LinkedList<com.sun.java.xml.ns.persistence.orm.JoinColumn>();
		if (coneToMany.getJoinColumn().isEmpty()) {
			joinColumns
					.add(new com.sun.java.xml.ns.persistence.orm.JoinColumn());
		} else {
			joinColumns.addAll(coneToMany.getJoinColumn());
		}

		final Collection<XAnnotation> joinColumnAnnotations = new ArrayList<XAnnotation>(
				joinColumns.size());
		for (final com.sun.java.xml.ns.persistence.orm.JoinColumn joinColumn : joinColumns) {
			joinColumnAnnotations.add(createJoinColumn(outlineProcessor,
					fieldOutline, options, joinColumn));

		}
		return joinColumnAnnotations;
	}

	public XAnnotation createJoinColumn(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			com.sun.java.xml.ns.persistence.orm.JoinColumn column) {
		final String defaultColumnName = outlineProcessor.getNaming()
				.getOneToManyJoinColumnName(fieldOutline);
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