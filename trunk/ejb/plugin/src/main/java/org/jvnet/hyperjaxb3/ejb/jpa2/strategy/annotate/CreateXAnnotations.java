package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.annotate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.persistence.AccessType;
import javax.persistence.LockModeType;
import javax.persistence.MapKeyJoinColumns;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

import com.sun.java.xml.ns.persistence.orm.AssociationOverride;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.CascadeType;
import com.sun.java.xml.ns.persistence.orm.CollectionTable;
import com.sun.java.xml.ns.persistence.orm.ElementCollection;
import com.sun.java.xml.ns.persistence.orm.Embeddable;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.MapKeyClass;
import com.sun.java.xml.ns.persistence.orm.MapKeyColumn;
import com.sun.java.xml.ns.persistence.orm.MapKeyJoinColumn;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.java.xml.ns.persistence.orm.NamedQuery;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.OrderColumn;
import com.sun.java.xml.ns.persistence.orm.SequenceGenerator;
import com.sun.java.xml.ns.persistence.orm.UniqueConstraint;
import com.sun.java.xml.ns.persistence.orm.Version;

public class CreateXAnnotations extends
		org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations {

	// 9.1.8
	public XAnnotation createId(Boolean source) {
		return source == null ? null : createId(source.booleanValue());
	}

	public XAnnotation createId(boolean source) {
		return !source ? null : new XAnnotation(javax.persistence.Id.class);
	}

	public XAnnotation createMapsId(String source) {
		return source == null ? null : new XAnnotation(
				javax.persistence.MapsId.class, AnnotationUtils.create("value",
						source));

	}

	public XAnnotation createAccess(String access) {
		return access == null ? null : new XAnnotation(
				javax.persistence.Access.class, AnnotationUtils.create("value",
						AccessType.valueOf(access)));

	}

	public XAnnotation createCacheable(Boolean cacheable) {
		return cacheable == null ? null :
		//
				new XAnnotation(javax.persistence.Cacheable.class,
				//
						AnnotationUtils.create("value", cacheable)
				//
				);
	}

	@Override
	public Collection<XAnnotation> createEntityAnnotations(Entity source) {

		final Collection<XAnnotation> annotations = super
				.createEntityAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createCacheable(source.isCacheable()));
	}

	@Override
	public Collection<XAnnotation> createBasicAnnotations(Basic source) {
		final Collection<XAnnotation> annotations = super
				.createBasicAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createEmbeddableAnnotations(Embeddable source) {
		final Collection<XAnnotation> annotations = super
				.createEmbeddableAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createEmbeddedAnnotations(Embedded source) {
		final Collection<XAnnotation> annotations = super
				.createEmbeddedAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createEmbeddedIdAnnotations(EmbeddedId source) {
		Collection<XAnnotation> annotations = super
				.createEmbeddedIdAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createIdAnnotations(Id source) {
		final Collection<XAnnotation> annotations = super
				.createIdAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createManyToManyAnnotations(ManyToMany source) {
		final Collection<XAnnotation> annotations = super
				.createManyToManyAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createOrderColumn(source.getOrderColumn()));
	}

	@Override
	public Collection<XAnnotation> createManyToOneAnnotations(ManyToOne source) {
		final Collection<XAnnotation> annotations = super
				.createManyToOneAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createMapsId(source.getMapsId()), createId(source.isId()));
	}

	@Override
	public Collection<XAnnotation> createMappedSuperclassAnnotations(
			MappedSuperclass source) {
		Collection<XAnnotation> annotations = super
				.createMappedSuperclassAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public XAnnotation createOneToMany(OneToMany cOneToMany) {
		return cOneToMany == null ? null :
		//
				new XAnnotation(javax.persistence.OneToMany.class,
				//
						cOneToMany.getTargetEntity() == null ? null
								: new XAnnotationField.XClass("targetEntity",
										cOneToMany.getTargetEntity()),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cOneToMany.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cOneToMany.getFetch())),
						//
						AnnotationUtils.create("mappedBy",
								cOneToMany.getMappedBy()),
						//
						AnnotationUtils.create("orphanRemoval",
								cOneToMany.isOrphanRemoval())
				//
				);
	}

	@Override
	public Collection<XAnnotation> createOneToManyAnnotations(OneToMany source) {
		final Collection<XAnnotation> annotations = super
				.createOneToManyAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createOrderColumn(source.getOrderColumn()));
	}

	// 9.1.23
	public XAnnotation createOneToOne(OneToOne cOneToOne) {
		return cOneToOne == null ? null :
		//
				new XAnnotation(javax.persistence.OneToOne.class,
				//
						cOneToOne.getTargetEntity() == null ? null
								: new XAnnotationField.XClass("targetEntity",
										cOneToOne.getTargetEntity()),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cOneToOne.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cOneToOne.getFetch())),
						//
						AnnotationUtils.create("optional",
								cOneToOne.isOptional()),
						//
						AnnotationUtils.create("mappedBy",
								cOneToOne.getMappedBy()),
						//
						AnnotationUtils.create("orphanRemoval",
								cOneToOne.isOrphanRemoval())
				//
				);
	}

	@Override
	public Collection<XAnnotation> createOneToOneAnnotations(OneToOne source) {
		final Collection<XAnnotation> annotations = super
				.createOneToOneAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()),
				createMapsId(source.getMapsId()), createId(source.isId()));
	}

	@Override
	public Collection<XAnnotation> createVersionAnnotations(Version source) {
		final Collection<XAnnotation> annotations = super
				.createVersionAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public XAnnotation createUniqueConstraint(UniqueConstraint source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.UniqueConstraint.class,
						//
						AnnotationUtils.create("name", source.getName()),
						AnnotationUtils.create("columnNames",
								source.getColumnName())
				//
				);
	}

	@Override
	public XAnnotation createSequenceGenerator(SequenceGenerator source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.SequenceGenerator.class,
				//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("sequenceName",
								source.getSequenceName()),
						//
						AnnotationUtils.create("catalog", source.getCatalog()),
						//
						AnnotationUtils.create("schema", source.getSchema()),
						//
						AnnotationUtils.create("initialValue",
								source.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize",
								source.getAllocationSize()));
	}

	// 8.3.1
	public XAnnotation createNamedQuery(NamedQuery source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.NamedQuery.class,
						//
						AnnotationUtils.create("query", source.getQuery()),
						//
						AnnotationUtils.create("hint",
								createQueryHint(source.getHint())),
						//
						AnnotationUtils.create("name", source.getName()),
						AnnotationUtils.create("lockMode",
								createLockMode(source.getLockMode()))

				//
				);

	}

	public LockModeType createLockMode(String lockMode) {
		return lockMode == null ? null : LockModeType.valueOf(lockMode);
	}

	public Collection<javax.persistence.CascadeType> getCascadeType(
			CascadeType cascade) {

		if (cascade == null) {
			return null;
		} else {
			final Collection<javax.persistence.CascadeType> cascades = new HashSet<javax.persistence.CascadeType>();

			if (cascade.getCascadeAll() != null) {
				cascades.add(javax.persistence.CascadeType.ALL);
			}
			if (cascade.getCascadeMerge() != null) {
				cascades.add(javax.persistence.CascadeType.MERGE);
			}
			if (cascade.getCascadePersist() != null) {
				cascades.add(javax.persistence.CascadeType.PERSIST);
			}
			if (cascade.getCascadeRefresh() != null) {
				cascades.add(javax.persistence.CascadeType.REFRESH);
			}
			if (cascade.getCascadeRemove() != null) {
				cascades.add(javax.persistence.CascadeType.REMOVE);
			}
			if (cascade.getCascadeDetach() != null) {
				cascades.add(javax.persistence.CascadeType.DETACH);
			}
			return cascades;
		}
	}

	public XAnnotation createOrderColumn(OrderColumn source) {
		return source == null ? null
				:
				//
				new XAnnotation(
						javax.persistence.OrderColumn.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("nullable", source.isNullable()),
						//
						AnnotationUtils.create("insertable",
								source.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								source.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								source.getColumnDefinition())
				//
				);

	}

	public XAnnotation createCollectionTable(CollectionTable source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.CollectionTable.class,
				//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("catalog", source.getCatalog()),
						//
						AnnotationUtils.create("schema", source.getSchema()),
						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(source.getJoinColumn())),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(source
										.getUniqueConstraint()))
				//
				);

	}

	public XAnnotation createMapKeyJoinColumn(MapKeyJoinColumn source) {
		return source == null ? null
				:
				//
				new XAnnotation(
						javax.persistence.MapKeyJoinColumn.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("referencedColumnName",
								source.getReferencedColumnName()),
						//
						AnnotationUtils.create("unique", source.isUnique()),
						//
						AnnotationUtils.create("nullable", source.isNullable()),
						//
						AnnotationUtils.create("insertable",
								source.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								source.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								source.getColumnDefinition()),
						//
						AnnotationUtils.create("table", source.getTable())
				//
				);
	}

	public XAnnotation createMapKeyJoinColumns(
			Collection<MapKeyJoinColumn> source) {
		return transform(MapKeyJoinColumns.class, source,
				new Transformer<MapKeyJoinColumn, XAnnotation>() {
					public XAnnotation transform(MapKeyJoinColumn input) {
						return createMapKeyJoinColumn(input);
					}
				});
	}

	public XAnnotation createMapKeyColumn(MapKeyColumn source) {
		return source == null ? null
				:
				//
				new XAnnotation(
						javax.persistence.MapKeyColumn.class,
						//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("unique", source.isUnique()),
						//
						AnnotationUtils.create("nullable", source.isNullable()),
						//
						AnnotationUtils.create("insertable",
								source.isInsertable()),
						//
						AnnotationUtils.create("updatable",
								source.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition",
								source.getColumnDefinition()),
						//
						AnnotationUtils.create("table", source.getTable()),
						//
						AnnotationUtils.create("length", source.getLength()),
						//
						AnnotationUtils.create("precision",
								source.getPrecision()),
						//
						AnnotationUtils.create("scale", source.getScale())

				//
				);
	}

	public XAnnotation createMapKeyClass(MapKeyClass source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.MapKeyClass.class,
				//
						source.getClazz() == null ? null
								: new XAnnotationField.XClass("value",
										source.getClazz())
				//
				);
	}

	public XAnnotation createElementCollection(ElementCollection source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.ElementCollection.class,
				//
						source.getTargetClass() == null ? null
								: new XAnnotationField.XClass("value",
										source.getTargetClass()),

						AnnotationUtils.create("fetch",
								getFetchType(source.getFetch()))

				//
				);
	}

	public Collection<XAnnotation> createElementCollectionAnnotations(
			ElementCollection source) {
		return source == null ? Collections.<XAnnotation> emptyList()
				:
				//
				annotations(
				//
						createElementCollection(source),
						//
						createOrderBy(source.getOrderBy()),
						//
						createOrderColumn(source.getOrderColumn()),

						//
						createMapKey(source.getMapKey()),
						//
						createMapKeyClass(source.getMapKeyClass()),
						//
						createMapKeyTemporal(source.getMapKeyTemporal()),
						//
						createMapKeyEnumerated(source.getMapKeyEnumerated()),
						//
						createAttributeOverrides(source
								.getMapKeyAttributeOverride()),
						//
						createMapKeyColumn(source.getMapKeyColumn()),
						//
						createMapKeyJoinColumns(source.getMapKeyJoinColumn()),
						//
						createColumn(source.getColumn()),
						//
						createTemporal(source.getTemporal()),
						//
						createEnumerated(source.getEnumerated()),
						//
						createLob(source.getLob()),
						//
						createAttributeOverrides(source.getAttributeOverride()),
						//
						createAssociationOverrides(source
								.getAssociationOverride()),
						//
						createCollectionTable(source.getCollectionTable()),
						//
						createAccess(source.getAccess())
				//
				);
	}

	public XAnnotation createMapKeyTemporal(String source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.MapKeyTemporal.class,
				//
						new XAnnotationField.XEnum("value",
								javax.persistence.TemporalType.valueOf(source),
								javax.persistence.TemporalType.class));
	}

	public XAnnotation createMapKeyEnumerated(String source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.MapKeyEnumerated.class,
				//
						new XAnnotationField.XEnum("value",
								javax.persistence.EnumType.valueOf(source),
								javax.persistence.EnumType.class));
	}

	// 9.1.12
	public XAnnotation createAssociationOverride(AssociationOverride source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.AssociationOverride.class,
				//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(source.getJoinColumn())),
						//
						AnnotationUtils.create("joinTable",
								createJoinTable(source.getJoinTable()))

				//
				);
	}

}
