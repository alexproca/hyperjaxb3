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
import org.jvnet.annox.model.XAnnotationField.XClass;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineOneToMany implements
		AnnotateFieldOutline {

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

		return new XAnnotation(
				javax.persistence.OneToMany.class,
				//
				createTargetEntity(outlineProcessor, fieldOutline, options),
				//
				createCascade(outlineProcessor, fieldOutline, options,
						oneToMany),
				//
				createFetch(outlineProcessor, fieldOutline, options, oneToMany),
				//
				createMappedBy(outlineProcessor, fieldOutline, options,
						oneToMany));

	}

	public XClass createTargetEntity(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof NType;

		final NType childClassInfo = (NType) type;

		return new XAnnotationField.XClass("targetEntity", childClassInfo
				.fullName());

	}

	public XAnnotationField createMappedBy(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {
		return AnnotationUtils.create("mappedBy", oneToMany.getMappedBy());
	}

	public XAnnotationField createFetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {
		return oneToMany.getFetch() == null ? null :  AnnotationUtils.create("fetch", FetchType.valueOf(oneToMany.getFetch()));
	}


	public XAnnotationField createCascade(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {

		if (oneToMany.getCascade() == null) {
			return null;
		} else {
			final Collection<CascadeType> cascade = new HashSet<CascadeType>();

			if (oneToMany.getCascade().getCascadeAll() != null) {
				cascade.add(CascadeType.ALL);
			}
			if (oneToMany.getCascade().getCascadeMerge() != null) {
				cascade.add(CascadeType.MERGE);
			}
			if (oneToMany.getCascade().getCascadePersist() != null) {
				cascade.add(CascadeType.PERSIST);
			}
			if (oneToMany.getCascade().getCascadeRefresh() != null) {
				cascade.add(CascadeType.REFRESH);
			}
			if (oneToMany.getCascade().getCascadeRemove() != null) {
				cascade.add(CascadeType.REMOVE);
			}
			return AnnotationUtils.create("cascade", cascade
					.toArray(new CascadeType[cascade.size()]));
		}
	}

	public Collection<XAnnotation> createJoinTable(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany) {

		final String joinTableName = (coneToMany.getJoinTable() != null && coneToMany
				.getJoinTable().getName() != null) ? coneToMany.getJoinTable()
				.getName() : outlineProcessor.getNaming()
				.getOneToManyJoinTableName(outlineProcessor, fieldOutline,
						options);

		final String joinColumnName = outlineProcessor.getNaming()
				.getOneToManyJoinTableJoinColumnName(outlineProcessor,
						fieldOutline, options);

		final String inverseJoinColumnName = outlineProcessor.getNaming()
				.getOneToManyJoinTableInverseJoinColumnName(outlineProcessor,
						fieldOutline, options);

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

	private XAnnotation createJoinColumn(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			com.sun.java.xml.ns.persistence.orm.JoinColumn column) {
		final String defaultColumnName = outlineProcessor.getNaming()
				.getOneToManyJoinColumnName(outlineProcessor, fieldOutline,
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