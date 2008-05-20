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
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineOneToMany implements
		AnnotateFieldOutline {

	// TODO #73 Target entity

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		return createOneToMany(outlineProcessor, fieldOutline, options);
		//
		// // if
		//
		// xannotations.add(createJoinTable(outlineProcessor, fieldOutline,
		// options));
		//
		// xannotations.add(new XAnnotation(OrderBy.class));
		//
		// return xannotations;
	}

	private boolean defaultJoinTable = true;

	public boolean isDefaultJoinTable() {
		return defaultJoinTable;
	}

	public void setDefaultJoinTable(boolean defaultJoinTable) {
		this.defaultJoinTable = defaultJoinTable;
	}

	public Collection<XAnnotation> createOneToMany(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				3);

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany = CustomizationUtils
				.containsCustomization(fieldOutline.getPropertyInfo(),
						Customizations.ONE_TO_MANY_ELEMENT_NAME) ? Customizations
				.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany> findCustomization(
						fieldOutline.getPropertyInfo(),
						Customizations.ONE_TO_MANY_ELEMENT_NAME)
				: new org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany();

		xannotations.add(createOneToMany(outlineProcessor, fieldOutline,
				options, coneToMany));

		final boolean joinTable;

		if (coneToMany.getJoinTable() != null) {
			joinTable = true;
		} else if (!coneToMany.getJoinColumn().isEmpty()) {
			joinTable = false;
		} else {
			final Model model = fieldOutline.parent().parent().getModel();
			final Persistence persistence = Customizations
					.<Persistence> findCustomization(model,
							Customizations.PERSISTENCE_ELEMENT_NAME);

			if (persistence == null || persistence.getDefaultOneToMany() == null) {
				joinTable = isDefaultJoinTable();
			} else if (persistence.getDefaultOneToMany().getJoinTable() != null) {
				joinTable = true;
			} else if (!persistence.getDefaultOneToMany().getJoinColumn().isEmpty()) {
				joinTable = false;
			} else {
				joinTable = isDefaultJoinTable();
			}
		}

		final Collection<XAnnotation> content;
		if (joinTable) {
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

		assert type instanceof CClassInfo;

		final CClassInfo childClassInfo = (CClassInfo) type;

		return new XAnnotationField.XClass("targetEntity", childClassInfo
				.fullName());

	}

	public XAnnotationField createMappedBy(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {
		return AnnotationUtils.create("mappedBy", oneToMany.getMappedBy());
	}

	private FetchType defaultFetch = null;

	public FetchType getDefaultFetch() {
		return defaultFetch;
	}

	public void setDefaultFetch(FetchType defaultFetch) {
		this.defaultFetch = defaultFetch;
	}

	public FetchType getFetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return getDefaultFetch();
	}

	public XEnum createFetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {
		if (oneToMany.getFetch() == null) {

			final FetchType fetch = getFetch(outlineProcessor, fieldOutline,
					options);

			if (fetch == null) {
				return null;
			} else {
				return new XAnnotationField.XEnum("fetch", fetch,
						FetchType.class);
			}
		} else {
			return new XAnnotationField.XEnum("fetch", FetchType
					.valueOf(oneToMany.getFetch()), FetchType.class);
		}
	}

	private CascadeType[] defaultCascade = { CascadeType.ALL };

	public CascadeType[] getDefaultCascade() {
		return defaultCascade;
	}

	public void setDefaultCascade(CascadeType[] defaultCascade) {
		this.defaultCascade = defaultCascade;
	}

	public CascadeType[] getCascade(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return getDefaultCascade();
	}

	public XAnnotationField.XEnumArray createCascade(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany oneToMany) {

		if (oneToMany.getCascade() == null) {
			final CascadeType[] cascade = getCascade(outlineProcessor,
					fieldOutline, options);
			if (cascade == null) {
				return null;
			} else {
				return new XAnnotationField.XEnumArray("cascade", cascade,
						CascadeType.class);
			}
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
			return new XAnnotationField.XEnumArray("cascade", cascade
					.toArray(new CascadeType[cascade.size()]),
					CascadeType.class);
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