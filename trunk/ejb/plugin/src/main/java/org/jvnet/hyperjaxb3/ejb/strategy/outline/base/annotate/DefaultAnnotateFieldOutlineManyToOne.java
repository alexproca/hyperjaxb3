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
import javax.persistence.ManyToOne;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XClass;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineManyToOne implements
		AnnotateFieldOutline {

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

		// final org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne
		// cManyToOne = CustomizationUtils
		// .containsCustomization(fieldOutline.getPropertyInfo(),
		// Customizations.MANY_TO_ONE_ELEMENT_NAME) ? Customizations
		// .<org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne>
		// findCustomization(
		// fieldOutline.getPropertyInfo(),
		// Customizations.MANY_TO_ONE_ELEMENT_NAME)
		// : new org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne();

		xannotations.add(createManyToOne(outlineProcessor, fieldOutline,
				options, cManyToOne));

		final Collection<XAnnotation> content;
		if (cManyToOne.getJoinTable() != null) {

			content = createJoinTable(outlineProcessor, fieldOutline, options,
					cManyToOne);
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
				createTargetEntity(outlineProcessor, fieldOutline, options,	cManyToOne),
				createFetch(outlineProcessor, fieldOutline, options, cManyToOne),
				createCascade(outlineProcessor, fieldOutline, options, cManyToOne),
				createOptional(outlineProcessor, fieldOutline, options,	cManyToOne));
		return manyToOne;
	}

	public XClass createTargetEntity(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne manyToOne) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof CClassInfo;

		final CClassInfo childClassInfo = (CClassInfo) type;

		return new XAnnotationField.XClass("targetEntity", childClassInfo
				.fullName());

	}

	public XAnnotationField createOptional(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne manyToOne) {

		return AnnotationUtils.create("optional", manyToOne.isOptional());

	}

	public XAnnotationField createFetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne manyToOne) {
		return manyToOne.getFetch() == null ? null : AnnotationUtils.create("fetch", FetchType.valueOf(manyToOne.getFetch()));
	}

	public XAnnotationField createCascade(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne manyToOne) {

		if (manyToOne.getCascade() == null) {
			return null;
		} else {
			final Collection<CascadeType> cascade = new HashSet<CascadeType>();

			if (manyToOne.getCascade().getCascadeAll() != null) {
				cascade.add(CascadeType.ALL);
			}
			if (manyToOne.getCascade().getCascadeMerge() != null) {
				cascade.add(CascadeType.MERGE);
			}
			if (manyToOne.getCascade().getCascadePersist() != null) {
				cascade.add(CascadeType.PERSIST);
			}
			if (manyToOne.getCascade().getCascadeRefresh() != null) {
				cascade.add(CascadeType.REFRESH);
			}
			if (manyToOne.getCascade().getCascadeRemove() != null) {
				cascade.add(CascadeType.REMOVE);
			}
			return AnnotationUtils.create("cascade", cascade
					.toArray(new CascadeType[cascade.size()]));
		}
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
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne cmanyToOne) {

		final String joinTableName = (cmanyToOne.getJoinTable() != null && cmanyToOne
				.getJoinTable().getName() != null) ? cmanyToOne.getJoinTable()
				.getName() : outlineProcessor.getNaming()
				.getManyToOneJoinTableName(outlineProcessor, fieldOutline,
						options);

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
