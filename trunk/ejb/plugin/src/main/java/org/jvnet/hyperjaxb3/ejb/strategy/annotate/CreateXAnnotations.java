package org.jvnet.hyperjaxb3.ejb.strategy.annotate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.GenerationType;
import javax.persistence.JoinColumns;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTables;
import javax.persistence.SqlResultSetMappings;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate.AbstractAnnotateSimpleFieldOutline.Transformer;

import com.sun.java.xml.ns.persistence.orm.AssociationOverride;
import com.sun.java.xml.ns.persistence.orm.AttributeOverride;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.CascadeType;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.ColumnResult;
import com.sun.java.xml.ns.persistence.orm.DiscriminatorColumn;
import com.sun.java.xml.ns.persistence.orm.Embeddable;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.EmptyType;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.EntityListener;
import com.sun.java.xml.ns.persistence.orm.EntityListeners;
import com.sun.java.xml.ns.persistence.orm.EntityResult;
import com.sun.java.xml.ns.persistence.orm.FieldResult;
import com.sun.java.xml.ns.persistence.orm.GeneratedValue;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.IdClass;
import com.sun.java.xml.ns.persistence.orm.Inheritance;
import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.JoinTable;
import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.MapKey;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.java.xml.ns.persistence.orm.NamedNativeQuery;
import com.sun.java.xml.ns.persistence.orm.NamedQuery;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.PrimaryKeyJoinColumn;
import com.sun.java.xml.ns.persistence.orm.QueryHint;
import com.sun.java.xml.ns.persistence.orm.SecondaryTable;
import com.sun.java.xml.ns.persistence.orm.SequenceGenerator;
import com.sun.java.xml.ns.persistence.orm.SqlResultSetMapping;
import com.sun.java.xml.ns.persistence.orm.Table;
import com.sun.java.xml.ns.persistence.orm.TableGenerator;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.UniqueConstraint;
import com.sun.java.xml.ns.persistence.orm.Version;

public class CreateXAnnotations {
	// ==================================================================
	// 8.1
	// ==================================================================

	// 8.1
	public XAnnotation createEntity(Entity cEntity) {
		return cEntity == null ? null :
		//
				new XAnnotation(javax.persistence.Entity.class,
				//
						AnnotationUtils.create("name", cEntity.getName())
				//
				);
	}

	// 8.2
	public XAnnotation createEntityListeners(EntityListeners cEntityListeners) {
		if (cEntityListeners == null
				|| cEntityListeners.getEntityListener().isEmpty()) {
			return null;
		} else {
			final List<String> classNames = new ArrayList<String>();
			for (EntityListener entityListener : cEntityListeners
					.getEntityListener()) {
				if (entityListener.getClazz() != null) {
					classNames.add(entityListener.getClazz());
				}
			}
			final String[] classNamesArray = classNames
					.toArray(new String[classNames.size()]);
			return new XAnnotation(javax.persistence.EntityListeners.class,
			//
					new XAnnotationField.XClassArray("value", classNamesArray)
			//
			);
		}
	}

	public XAnnotation createExcludeSuperclassListeners(
			EmptyType cExcludeSuperclassListeners) {
		return cExcludeSuperclassListeners == null ? null :
		//
				new XAnnotation(
						javax.persistence.ExcludeSuperclassListeners.class);
	}

	public XAnnotation createExcludeDefaultListeners(
			EmptyType cExcludeDefaultListeners) {
		return cExcludeDefaultListeners == null ? null :
		//
				new XAnnotation(javax.persistence.ExcludeDefaultListeners.class);
	}

	// public XAnnotation createEntityListeners(EntityListeners
	// cEntityListeners)

	// 8.3.1
	public XAnnotation createNamedQuery(NamedQuery cNamedQuery) {
		return cNamedQuery == null ? null :
		//
				new XAnnotation(
						javax.persistence.NamedQuery.class,
						//
						AnnotationUtils.create("query", cNamedQuery.getQuery()),
						//
						AnnotationUtils.create("hint",
								createQueryHint(cNamedQuery.getHint())),
						//
						AnnotationUtils.create("name", cNamedQuery.getName())
				//
				);

	}

	public XAnnotation createNamedQueries(Collection<NamedQuery> cNamedQueries) {
		return transform(NamedQueries.class, cNamedQueries,
				new Transformer<NamedQuery, XAnnotation>() {
					public XAnnotation transform(NamedQuery input) {
						return createNamedQuery(input);
					}
				});
	}

	public XAnnotation createQueryHint(QueryHint cQueryHint) {
		return cQueryHint == null ? null :
		//
				new XAnnotation(javax.persistence.QueryHint.class,
				//
						AnnotationUtils.create("name", cQueryHint.getName()),
						//
						AnnotationUtils.create("value", cQueryHint.getValue())
				//		
				);
	}

	public Collection<XAnnotation> createQueryHint(
			Collection<QueryHint> cQueryHints) {
		return transform(cQueryHints,
				new Transformer<QueryHint, XAnnotation>() {
					public XAnnotation transform(QueryHint input) {
						return createQueryHint(input);
					}
				});
	}

	// 8.3.2
	public XAnnotation createNamedNativeQuery(NamedNativeQuery cNamedNativeQuery) {
		return cNamedNativeQuery == null ? null :
		//
				new XAnnotation(javax.persistence.NamedNativeQuery.class,
				//
						AnnotationUtils.create("name", cNamedNativeQuery
								.getName()),
						//
						AnnotationUtils.create("query", cNamedNativeQuery
								.getQuery()),
						//
						AnnotationUtils.create("hints",
								createQueryHint(cNamedNativeQuery.getHint())),
						//
						cNamedNativeQuery.getResultClass() == null ? null
								: new XAnnotationField.XClass("resultClass",
										cNamedNativeQuery.getResultClass()),
						//
						AnnotationUtils.create("resultSetMapping",
								cNamedNativeQuery.getResultSetMapping())
				//
				);
	}

	public XAnnotation createNamedNativeQuery(
			Collection<NamedNativeQuery> cNamedNativeQueries) {
		return transform(NamedNativeQueries.class, cNamedNativeQueries,
				new Transformer<NamedNativeQuery, XAnnotation>() {
					public XAnnotation transform(NamedNativeQuery input) {
						return createNamedNativeQuery(input);
					}
				});
	}

	public XAnnotation createSqlResultSetMapping(
			SqlResultSetMapping cSqlResultSetMapping) {
		return cSqlResultSetMapping == null ? null :
		//
				new XAnnotation(javax.persistence.SqlResultSetMapping.class,
				//
						AnnotationUtils.create("name", cSqlResultSetMapping
								.getName()),
						//
						AnnotationUtils.create("entityResult",
								createEntityResult(cSqlResultSetMapping
										.getEntityResult())),
						//
						AnnotationUtils.create("columnResult",
								createColumnResult(cSqlResultSetMapping
										.getColumnResult()))
				//
				);
	}

	public XAnnotation createSqlResultSetMapping(
			Collection<SqlResultSetMapping> cSqlResultSetMappings) {
		return transform(SqlResultSetMappings.class, cSqlResultSetMappings,
				new Transformer<SqlResultSetMapping, XAnnotation>() {
					public XAnnotation transform(SqlResultSetMapping input) {
						return createSqlResultSetMapping(input);
					}
				});
	}

	public XAnnotation createEntityResult(EntityResult cEntityResult) {
		return cEntityResult == null ? null :
		//
				new XAnnotation(javax.persistence.EntityResult.class,
				//
						new XAnnotationField.XClass("entityClass",
								cEntityResult.getEntityClass()),

						//
						AnnotationUtils.create("fields",
								createFieldResult(cEntityResult
										.getFieldResult())),
						//
						AnnotationUtils.create("discriminatorColumn",
								cEntityResult.getDiscriminatorColumn())
				//
				);
	}

	public Collection<XAnnotation> createEntityResult(
			List<EntityResult> cEntityResults) {
		return transform(cEntityResults,
				new Transformer<EntityResult, XAnnotation>() {
					public XAnnotation transform(EntityResult cEntityResult) {
						return createEntityResult(cEntityResult);
					}
				});
	}

	public XAnnotation createFieldResult(FieldResult cFieldResult) {
		return cFieldResult == null ? null :
		//
				new XAnnotation(javax.persistence.FieldResult.class,
				//
						AnnotationUtils.create("name", cFieldResult.getName()),
						//
						AnnotationUtils.create("column", cFieldResult
								.getColumn())
				//
				);
	}

	public Collection<XAnnotation> createFieldResult(
			List<FieldResult> cFieldResults) {
		return transform(cFieldResults,
				new Transformer<FieldResult, XAnnotation>() {
					public XAnnotation transform(FieldResult cFieldResult) {
						return createFieldResult(cFieldResult);
					}
				});
	}

	public XAnnotation createColumnResult(ColumnResult cColumnResult) {
		return cColumnResult == null ? null :
		//
				new XAnnotation(javax.persistence.ColumnResult.class,
				//
						AnnotationUtils.create("name", cColumnResult.getName())
				//
				);
	}

	public Collection<XAnnotation> createColumnResult(
			Collection<ColumnResult> cColumnResults) {
		return transform(cColumnResults,
				new Transformer<ColumnResult, XAnnotation>() {
					public XAnnotation transform(ColumnResult cColumnResult) {
						return createColumnResult(cColumnResult);
					}
				});
	}

	// ==================================================================
	// 9.1
	// ==================================================================

	// 9.1.1
	public XAnnotation createTable(Table cTable) {
		return cTable == null ? null :
		//
				new XAnnotation(javax.persistence.Table.class,
				//
						AnnotationUtils.create("name", cTable.getName()),
						//
						AnnotationUtils.create("catalog", cTable.getCatalog()),
						//
						AnnotationUtils.create("schema", cTable.getSchema()),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cTable
										.getUniqueConstraint()))
				//
				);

	}

	// 9.1.2
	public XAnnotation createSecondaryTable(SecondaryTable cSecondaryTable) {
		return cSecondaryTable == null ? null :
		//
				new XAnnotation(javax.persistence.SecondaryTable.class,
				//
						AnnotationUtils.create("name", cSecondaryTable
								.getName()),
						//
						AnnotationUtils.create("catalog", cSecondaryTable
								.getCatalog()),
						//
						AnnotationUtils.create("schema", cSecondaryTable
								.getSchema()),
						//
						AnnotationUtils.create("pkJoinColumns",
								createPrimaryKeyJoinColumn(cSecondaryTable
										.getPrimaryKeyJoinColumn())),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cSecondaryTable
										.getUniqueConstraint()))
				//
				);
	}

	// 9.1.3
	public XAnnotation createSecondaryTables(
			List<SecondaryTable> cSecondaryTables) {
		return transform(SecondaryTables.class, cSecondaryTables,
				new Transformer<SecondaryTable, XAnnotation>() {
					public XAnnotation transform(SecondaryTable input) {
						return createSecondaryTable(input);
					}
				});
	}

	// 9.1.4
	public XAnnotation createUniqueConstraint(UniqueConstraint cUniqueConstraint) {
		return cUniqueConstraint == null ? null :
		//
				new XAnnotation(javax.persistence.UniqueConstraint.class,
				//
						AnnotationUtils.create("columnNames", cUniqueConstraint
								.getColumnName())
				//
				);
	}

	public Collection<XAnnotation> createUniqueConstraint(
			List<UniqueConstraint> cUniqueConstraints) {
		return transform(cUniqueConstraints,
				new Transformer<UniqueConstraint, XAnnotation>() {
					public XAnnotation transform(UniqueConstraint input) {
						return createUniqueConstraint(input);
					}
				});
	}

	// 9.1.5
	public XAnnotation createColumn(Column cColumn) {

		return cColumn == null ? null :
		//
				new XAnnotation(javax.persistence.Column.class,
				//
						AnnotationUtils.create("name", cColumn.getName()),
						//
						AnnotationUtils.create("unique", cColumn.isUnique()),
						//
						AnnotationUtils
								.create("nullable", cColumn.isNullable()),
						//
						AnnotationUtils.create("insertable", cColumn
								.isInsertable()),
						//
						AnnotationUtils.create("updatable", cColumn
								.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition", cColumn
								.getColumnDefinition()),
						//
						AnnotationUtils.create("table", cColumn.getTable()),
						//
						AnnotationUtils.create("length", cColumn.getLength()),
						//
						AnnotationUtils.create("precision", cColumn
								.getPrecision()),
						//
						AnnotationUtils.create("scale", cColumn.getScale()));
	}

	// 9.1.6
	public XAnnotation createJoinColumn(JoinColumn cJoinColumn) {
		return cJoinColumn == null ? null :
		//
				new XAnnotation(javax.persistence.JoinColumn.class,
				//
						AnnotationUtils.create("name", cJoinColumn.getName()),
						//
						AnnotationUtils.create("referencedColumnName",
								cJoinColumn.getReferencedColumnName()),
						//
						AnnotationUtils
								.create("unique", cJoinColumn.isUnique()),
						//
						AnnotationUtils.create("nullable", cJoinColumn
								.isNullable()),
						//
						AnnotationUtils.create("insertable", cJoinColumn
								.isInsertable()),
						//
						AnnotationUtils.create("updatable", cJoinColumn
								.isUpdatable()),
						//
						AnnotationUtils.create("columnDefinition", cJoinColumn
								.getColumnDefinition()),
						//
						AnnotationUtils.create("table", cJoinColumn.getTable())
				//
				);
	}

	public Collection<XAnnotation> createJoinColumn(
			List<JoinColumn> cJoinColumns) {
		return transform(cJoinColumns,
				new Transformer<JoinColumn, XAnnotation>() {
					public XAnnotation transform(JoinColumn input) {
						return createJoinColumn(input);
					}
				});
	}

	// 9.1.7
	public XAnnotation createJoinColumns(List<JoinColumn> cJoinColumns) {
		return transform(JoinColumns.class, cJoinColumns,
				new Transformer<JoinColumn, XAnnotation>() {
					public XAnnotation transform(JoinColumn input) {
						return createJoinColumn(input);
					}
				});
	}
	
	
	public Collection<XAnnotation> createAttributeAnnotations(Object attribute) {

		if (attribute == null) {
			return null;
		} else if (attribute instanceof Id) {
			return createIdAnnotations((Id) attribute);
		} else if (attribute instanceof EmbeddedId) {
			return createEmbeddedIdAnnotations((EmbeddedId) attribute);
		} else if (attribute instanceof Basic) {
			return createBasicAnnotations((Basic) attribute);

		} else if (attribute instanceof Version) {
			return createVersionAnnotations((Version) attribute);

		} else if (attribute instanceof ManyToOne) {
			return createManyToOneAnnotations((ManyToOne) attribute);

		} else if (attribute instanceof OneToMany) {
			return createOneToManyAnnotations((OneToMany) attribute);
		} else if (attribute instanceof OneToOne) {
			return createOneToOneAnnotations((OneToOne) attribute);
		} else if (attribute instanceof ManyToMany) {
			return createManyToManyAnnotations((ManyToMany) attribute);
		} else if (attribute instanceof Embedded) {
			return createEmbeddedAnnotations((Embedded) attribute);
		} else if (attribute instanceof Transient) {
			return createTransientAnnotations((Transient) attribute);
		} else {
			return null;
		}
	}

	// 9.1.8
	public XAnnotation createId(Id cId) {
		return cId == null ? null :
		//
				new XAnnotation(javax.persistence.Id.class);
	}

	// 9.1.9
	public XAnnotation createGeneratedValue(GeneratedValue cGeneratedValue) {

		return cGeneratedValue == null ? null :
		//
				new XAnnotation(javax.persistence.GeneratedValue.class,
				//
						AnnotationUtils.create("generator", cGeneratedValue
								.getGenerator()),
						//
						createGeneratedValue$Strategy(cGeneratedValue
								.getStrategy())
				//
				);
	}

	public XAnnotationField.XEnum createGeneratedValue$Strategy(String strategy) {
		return strategy == null ? null :
		//
				new XAnnotationField.XEnum("strategy", GenerationType
						.valueOf(strategy), GenerationType.class);
	}

	// 9.1.10
	public XAnnotation createAttributeOverride(
			AttributeOverride cAttributeOverride) {
		return cAttributeOverride == null ? null :
		//
				new XAnnotation(javax.persistence.AttributeOverride.class,
				//
						AnnotationUtils.create("name", cAttributeOverride
								.getName()),
						//
						AnnotationUtils.create("column",
								createColumn(cAttributeOverride.getColumn()))
				//
				);
	}

	public Collection<XAnnotation> createAttributeOverride(
			List<AttributeOverride> cAttributeOverrides) {
		return transform(cAttributeOverrides,
				new Transformer<AttributeOverride, XAnnotation>() {
					public XAnnotation transform(AttributeOverride input) {
						return createAttributeOverride(input);
					}
				});
	}

	// 9.1.11
	public XAnnotation createAttributeOverrides(
			List<AttributeOverride> cAttributeOverrides) {
		return transform(javax.persistence.AttributeOverrides.class,
				cAttributeOverrides,
				new Transformer<AttributeOverride, XAnnotation>() {
					public XAnnotation transform(AttributeOverride input) {
						return createAttributeOverride(input);
					}
				});
	}

	// 9.1.12
	public XAnnotation createAssociationOverride(
			AssociationOverride cAssociationOverride) {
		return cAssociationOverride == null ? null :
		//
				new XAnnotation(javax.persistence.AssociationOverride.class,
				//
						AnnotationUtils.create("name", cAssociationOverride
								.getName()),
						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(cAssociationOverride
										.getJoinColumn()))
				//
				);
	}

	// 9.1.13
	public XAnnotation createAssociationOverrides(
			List<AssociationOverride> cAssociationOverrides) {
		return transform(javax.persistence.AssociationOverride.class,
				cAssociationOverrides,
				new Transformer<AssociationOverride, XAnnotation>() {
					public XAnnotation transform(AssociationOverride input) {
						return createAssociationOverride(input);
					}
				});
	}

	// 9.1.14
	public XAnnotation createEmbeddedId(EmbeddedId cEmbeddedId) {
		return cEmbeddedId == null ? null :
		//
				new XAnnotation(javax.persistence.EmbeddedId.class);
	}

	// 9.1.15
	public XAnnotation createIdClass(IdClass cIdClass) {
		return cIdClass == null ? null :
		//
				new XAnnotation(javax.persistence.IdClass.class,
				//
						cIdClass.getClazz() == null ? null
								: new XAnnotationField.XClass("value", cIdClass
										.getClazz())
				//
				);
	}

	// 9.1.16
	public XAnnotation createTransient(Transient cTransient) {
		return cTransient == null ? null :
		//
				new XAnnotation(javax.persistence.Transient.class);
	}

	// 9.1.17
	public XAnnotation createVersion(Version cVersion) {
		return cVersion == null ? null :
		//
				new XAnnotation(javax.persistence.Version.class);
	}

	// 9.1.18
	public XAnnotation createBasic(Basic cBasic) {
		return cBasic == null ? null :
		//
				new XAnnotation(javax.persistence.Basic.class,
				//
						AnnotationUtils.create("fetch", getFetchType(cBasic
								.getFetch())),
						//
						AnnotationUtils.create("optional", cBasic.isOptional())
				//
				);
	}

	// 9.1.19
	public XAnnotation createLob(Lob cLob) {
		return cLob == null ? null :
		//
				new XAnnotation(javax.persistence.Lob.class);
	}

	// 9.1.20
	public XAnnotation createTemporal(String cTemporal) {
		return cTemporal == null ? null :
		//
				new XAnnotation(javax.persistence.Temporal.class,
				//
						new XAnnotationField.XEnum("value",
								javax.persistence.TemporalType
										.valueOf(cTemporal),
								javax.persistence.TemporalType.class));
	}

	// 9.1.21
	public XAnnotation createEnumerated(String cEnumerated) {
		return cEnumerated == null ? null :
		//
				new XAnnotation(
						javax.persistence.Enumerated.class,
						//
						new XAnnotationField.XEnum(
								"value",
								javax.persistence.EnumType.valueOf(cEnumerated),
								javax.persistence.EnumType.class));
	}

	// 9.1.22
	public XAnnotation createManyToOne(ManyToOne cManyToOne) {
		return cManyToOne == null ? null :
		//
				new XAnnotation(javax.persistence.ManyToOne.class,
				//
						cManyToOne.getTargetEntity() == null ? null
								: new XAnnotationField.XClass("targetEntity",
										cManyToOne.getTargetEntity()),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cManyToOne.getCascade())),
						//
						AnnotationUtils.create("fetch", getFetchType(cManyToOne
								.getFetch())),
						//
						AnnotationUtils.create("optional", cManyToOne
								.isOptional())

				//
				);
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
						AnnotationUtils.create("fetch", getFetchType(cOneToOne
								.getFetch())),
						//
						AnnotationUtils.create("optional", cOneToOne
								.isOptional()),
						//
						AnnotationUtils.create("mappedBy", cOneToOne
								.getMappedBy())
				//
				);
	}

	// 9.1.24
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
						AnnotationUtils.create("fetch", getFetchType(cOneToMany
								.getFetch())),
						//
						AnnotationUtils.create("mappedBy", cOneToMany
								.getMappedBy())
				//
				);
	}

	// 9.1.25
	public XAnnotation createJoinTable(JoinTable cJoinTable) {
		return cJoinTable == null ? null :
		//
				new XAnnotation(javax.persistence.JoinTable.class,

				//
						AnnotationUtils.create("name", cJoinTable.getName()),
						//
						AnnotationUtils.create("catalog", cJoinTable
								.getCatalog()),
						//
						AnnotationUtils
								.create("schema", cJoinTable.getSchema()),

						//
						AnnotationUtils.create("joinColumns",
								createJoinColumn(cJoinTable.getJoinColumn())),
						//
						AnnotationUtils.create("inverseJoinColumns",
								createJoinColumn(cJoinTable
										.getInverseJoinColumn())),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cJoinTable
										.getUniqueConstraint()))
				//
				);
	}

	// 9.1.26
	public XAnnotation createManyToMany(ManyToMany cManyToMany) {
		return cManyToMany == null ? null :
		//
				new XAnnotation(javax.persistence.ManyToMany.class,
				//
						cManyToMany.getTargetEntity() == null ? null
								: new XAnnotationField.XClass("targetEntity",
										cManyToMany.getTargetEntity()),
						//
						AnnotationUtils.create("cascade",
								getCascadeType(cManyToMany.getCascade())),
						//
						AnnotationUtils.create("fetch",
								getFetchType(cManyToMany.getFetch())),
						//
						AnnotationUtils.create("mappedBy", cManyToMany
								.getMappedBy())
				//
				);
	}

	// 9.1.27
	public XAnnotation createMapKey(MapKey cMapKey) {
		return cMapKey == null ? null :
		//
				new XAnnotation(javax.persistence.MapKey.class,
				//
						AnnotationUtils.create("name", cMapKey.getName())
				//
				);
	}

	// 9.1.28
	public XAnnotation createOrderBy(String orderBy) {
		return orderBy == null ? null :
		//
				new XAnnotation(javax.persistence.OrderBy.class,
						AnnotationUtils.create("value", orderBy));
	}

	// 9.1.29
	public XAnnotation createInheritance(Inheritance cInheritance) {
		return cInheritance == null ? null :
		//
				new XAnnotation(javax.persistence.Inheritance.class,
				//
						AnnotationUtils.create("strategy",
								getInheritanceType(cInheritance.getStrategy()))
				//
				);
	}

	// 9.1.30
	public XAnnotation createDiscriminatorColumn(
			DiscriminatorColumn cDiscriminatorColumn) {
		return cDiscriminatorColumn == null ? null :
		//
				new XAnnotation(javax.persistence.DiscriminatorColumn.class,
				//
						AnnotationUtils.create("name", cDiscriminatorColumn
								.getName()),
						//
						AnnotationUtils.create("discriminatorType",
								getDiscriminatorType(cDiscriminatorColumn
										.getDiscriminatorType())),
						//
						AnnotationUtils.create("columnDefinition",
								cDiscriminatorColumn.getColumnDefinition()),
						//
						AnnotationUtils.create("length", cDiscriminatorColumn
								.getLength())
				//
				);
	}

	// 9.1.31
	public XAnnotation createDiscriminatorValue(String cDiscriminatorValue) {
		return cDiscriminatorValue == null ? null :
		//
				new XAnnotation(javax.persistence.DiscriminatorValue.class,
						AnnotationUtils.create("value", cDiscriminatorValue));
	}

	// 9.1.32
	public XAnnotation createPrimaryKeyJoinColumn(
			PrimaryKeyJoinColumn cPrimaryKeyJoinColumn) {
		return cPrimaryKeyJoinColumn == null ? null :
		//
				new XAnnotation(javax.persistence.PrimaryKeyJoinColumn.class,
				//
						AnnotationUtils.create("name", cPrimaryKeyJoinColumn
								.getName()),
						//
						AnnotationUtils
								.create("referencedColumnName",
										cPrimaryKeyJoinColumn
												.getReferencedColumnName()),
						//
						AnnotationUtils.create("columnDefinition",
								cPrimaryKeyJoinColumn.getColumnDefinition())
				//
				);
	}

	public Collection<XAnnotation> createPrimaryKeyJoinColumn(
			List<PrimaryKeyJoinColumn> cPrimaryKeyJoinColumn) {
		return transform(cPrimaryKeyJoinColumn,
				new Transformer<PrimaryKeyJoinColumn, XAnnotation>() {
					public XAnnotation transform(PrimaryKeyJoinColumn input) {
						return createPrimaryKeyJoinColumn(input);
					}

				});
	}

	// 9.1.33
	public XAnnotation createPrimaryKeyJoinColumns(
			List<PrimaryKeyJoinColumn> cPrimaryKeyJoinColumn) {
		return transform(PrimaryKeyJoinColumns.class, cPrimaryKeyJoinColumn,
				new Transformer<PrimaryKeyJoinColumn, XAnnotation>() {
					public XAnnotation transform(PrimaryKeyJoinColumn input) {
						return createPrimaryKeyJoinColumn(input);
					}

				});
	}

	// 9.1.34
	public XAnnotation createEmbeddable(Embeddable cEmbeddable) {
		return cEmbeddable == null ? null :
		//
				new XAnnotation(javax.persistence.Embeddable.class);
	}

	// 9.1.35
	public XAnnotation createEmbedded(Embedded cEmbedded) {
		return cEmbedded == null ? null :
		//
				new XAnnotation(javax.persistence.Embedded.class);
	}

	// 9.1.36
	public XAnnotation createMappedSuperclass(MappedSuperclass cMappedSuperclass) {
		return cMappedSuperclass == null ? null :
		//
				new XAnnotation(javax.persistence.MappedSuperclass.class);
	}

	// 9.1.37
	public XAnnotation createSequenceGenerator(
			SequenceGenerator cSequenceGenerator) {

		return cSequenceGenerator == null ? null :
		//
				new XAnnotation(javax.persistence.SequenceGenerator.class,
				//
						AnnotationUtils.create("name", cSequenceGenerator
								.getName()),
						//
						AnnotationUtils.create("sequenceName",
								cSequenceGenerator.getSequenceName()),
						//
						AnnotationUtils.create("initialValue",
								cSequenceGenerator.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize",
								cSequenceGenerator.getAllocationSize()));
	}

	// 9.1.38
	public XAnnotation createTableGenerator(TableGenerator cTableGenerator) {

		return cTableGenerator == null ? null :
		//
				new XAnnotation(javax.persistence.TableGenerator.class,
				//
						AnnotationUtils.create("name", cTableGenerator
								.getName()),
						//
						AnnotationUtils.create("table", cTableGenerator
								.getTable()),
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
						AnnotationUtils.create("valueColumnName",
								cTableGenerator.getValueColumnName()),
						//
						AnnotationUtils.create("pkColumnValue", cTableGenerator
								.getPkColumnValue()),
						//
						AnnotationUtils.create("initialValue", cTableGenerator
								.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize",
								cTableGenerator.getAllocationSize()),
						//
						AnnotationUtils.create("uniqueConstraints",
								createUniqueConstraint(cTableGenerator
										.getUniqueConstraint()))
				//
				);
	}

	// ==================================================================
	// 10.1
	// ==================================================================

	// 10.1.3
	public Collection<XAnnotation> createEntityAnnotations(Entity cEntity) {
		return cEntity == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createEntity(cEntity),
						//
						createTable(cEntity.getTable()),
						//
						createSecondaryTables(cEntity.getSecondaryTable()),
						//
						createPrimaryKeyJoinColumns(cEntity
								.getPrimaryKeyJoinColumn()),
						//
						createIdClass(cEntity.getIdClass()),
						//
						createInheritance(cEntity.getInheritance()),
						//
						createDiscriminatorValue(cEntity
								.getDiscriminatorValue()),
						//
						createDiscriminatorColumn(cEntity
								.getDiscriminatorColumn()),
						//
						createSequenceGenerator(cEntity.getSequenceGenerator()),
						//
						createTableGenerator(cEntity.getTableGenerator()),
						//
						createNamedQueries(cEntity.getNamedQuery()),
						//
						createNamedNativeQuery(cEntity.getNamedNativeQuery()),
						//
						createSqlResultSetMapping(cEntity
								.getSqlResultSetMapping()),
						//
						createExcludeDefaultListeners(cEntity
								.getExcludeDefaultListeners()),
						//
						createExcludeSuperclassListeners(cEntity
								.getExcludeSuperclassListeners()),
						//
						createEntityListeners(cEntity.getEntityListeners()),
						//
						// "prePersist",
						//
						// "postPersist",
						//
						// "preRemove",
						//
						// "postRemove",
						//
						// "preUpdate",
						//
						// "postUpdate",
						//
						// "postLoad",
						//
						createAttributeOverrides(cEntity.getAttributeOverride()),
						//
						createAssociationOverrides(cEntity
								.getAssociationOverride())
				// "attributes"

				//
				);
	}

	// 10.1.3.22
	public Collection<XAnnotation> createIdAnnotations(Id cId) {
		return cId == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createId(cId),
						//
						createColumn(cId.getColumn()),
						//
						createGeneratedValue(cId.getGeneratedValue()),
						//
						createTemporal(cId.getTemporal()),
						//
						createTableGenerator(cId.getTableGenerator()),
						//
						createSequenceGenerator(cId.getSequenceGenerator())

				//
				);
	}

	// 10.1.3.23
	public Collection<XAnnotation> createEmbeddedIdAnnotations(
			EmbeddedId cEmbeddedId) {
		return cEmbeddedId == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createEmbeddedId(cEmbeddedId),
						//
						createAttributeOverride(cEmbeddedId
								.getAttributeOverride())
				//
				);
	}

	// 10.1.3.24
	public Collection<XAnnotation> createBasicAnnotations(Basic cBasic) {
		return cBasic == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createBasic(cBasic),
						//
						createColumn(cBasic.getColumn()),
						//
						createLob(cBasic.getLob()),
						//
						createTemporal(cBasic.getTemporal()),
						//
						createEnumerated(cBasic.getEnumerated())
				//
				);
	}

	// 10.1.3.25
	public Collection<XAnnotation> createVersionAnnotations(Version cVersion) {
		return cVersion == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createVersion(cVersion),
						//
						createColumn(cVersion.getColumn()),
						//
						createTemporal(cVersion.getTemporal())
				//
				);
	}

	// 10.1.3.26
	public Collection<XAnnotation> createManyToOneAnnotations(
			ManyToOne cManyToOne) {
		return cManyToOne == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createManyToOne(cManyToOne),
						//
						createJoinColumn(cManyToOne.getJoinColumn()),
						//
						createJoinTable(cManyToOne.getJoinTable())
				//
				);
	}

	// 10.1.3.27
	public Collection<XAnnotation> createOneToManyAnnotations(
			OneToMany cOneToMany) {
		return cOneToMany == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createOneToMany(cOneToMany),
						//
						createOrderBy(cOneToMany.getOrderBy()),
						//
						createMapKey(cOneToMany.getMapKey()),
						//
						createJoinColumn(cOneToMany.getJoinColumn()),
						//
						createJoinTable(cOneToMany.getJoinTable())
				//
				);
	}

	// 10.1.3.28
	public Collection<XAnnotation> createOneToOneAnnotations(OneToOne cOneToOne) {
		return cOneToOne == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createOneToOne(cOneToOne),
						//
						createPrimaryKeyJoinColumn(cOneToOne
								.getPrimaryKeyJoinColumn()),
						//
						createJoinColumn(cOneToOne.getJoinColumn()),
						//
						createJoinTable(cOneToOne.getJoinTable())
				//
				);
	}

	// 10.1.3.29
	public Collection<XAnnotation> createManyToManyAnnotations(
			ManyToMany cManyToMany) {
		return cManyToMany == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createManyToMany(cManyToMany),
						//
						createOrderBy(cManyToMany.getOrderBy()),
						//
						createMapKey(cManyToMany.getMapKey()),
						//
						createJoinTable(cManyToMany.getJoinTable())
				// 
				);
	}

	// 10.1.3.30
	public Collection<XAnnotation> createEmbeddedAnnotations(Embedded cEmbedded) {
		return cEmbedded == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createEmbedded(cEmbedded),
						//
						createAttributeOverride(cEmbedded
								.getAttributeOverride())
				//		
				);
	}

	// 10.1.3.31
	public Collection<XAnnotation> createTransientAnnotations(
			Transient cTransient) {
		return cTransient == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
				createTransient(cTransient)
				//
				);

	}

	// 10.1.4
	public Collection<XAnnotation> createMappedSuperclassAnnotations(
			MappedSuperclass cMappedSuperclass) {
		return cMappedSuperclass == null ? Collections
				.<XAnnotation> emptyList() :
		//
				annotations(
				//
						createMappedSuperclass(cMappedSuperclass),
						//
						createIdClass(cMappedSuperclass.getIdClass()),
						//
						createExcludeDefaultListeners(cMappedSuperclass
								.getExcludeDefaultListeners()),
						//
						createExcludeSuperclassListeners(cMappedSuperclass
								.getExcludeSuperclassListeners()),
						//
						createEntityListeners(cMappedSuperclass
								.getEntityListeners())
				//
				// "prePersist",
				//
				// "postPersist",
				//
				// "preRemove",
				//
				// "postRemove",
				//
				// "preUpdate",
				//
				// "postUpdate",
				//
				// "postLoad",
				//

				);

	}

	// 10.1.4
	public Collection<XAnnotation> createEmbeddableAnnotations(
			Embeddable cEmbeddable) {
		return cEmbeddable == null ? Collections.<XAnnotation> emptyList() :
		//
				annotations(
				//
				createEmbeddable(cEmbeddable)
				//
				);
	}

	public interface Transformer<I, O> {
		public O transform(I input);
	}

	public static <T> XAnnotation transform(
			Class<? extends Annotation> collectionClass,
			Collection<T> collection, Transformer<T, XAnnotation> transformer) {
		if (collection == null || collection.isEmpty()) {
			return null;
		} else if (collection.size() == 1) {
			return transformer.transform(collection.iterator().next());
		} else {

			return new XAnnotation(collectionClass, AnnotationUtils.create(
					"value", transform(collection, transformer)));
		}
	}

	public static <T> Collection<XAnnotation> transform(
			Collection<T> collection, Transformer<T, XAnnotation> transformer) {
		if (collection == null || collection.isEmpty()) {
			return null;
		} else {
			final Collection<XAnnotation> annotations = new ArrayList<XAnnotation>(
					collection.size());
			for (T item : collection) {
				annotations.add(transformer.transform(item));
			}
			return annotations;
		}
	}

	public static Collection<XAnnotation> annotations(
			XAnnotation... annotations) {
		if (annotations == null) {
			return null;
		} else if (annotations.length == 0) {
			return Collections.emptyList();
		} else {
			final List<XAnnotation> xannotations = new ArrayList<XAnnotation>(
					annotations.length);
			for (XAnnotation annotation : annotations) {
				if (annotation != null) {
					xannotations.add(annotation);
				}
			}
			return xannotations;
		}
	}

	@SuppressWarnings("unchecked")
	public static Collection<XAnnotation> annotations(Object... annotations) {
		if (annotations == null) {
			return null;
		} else if (annotations.length == 0) {
			return Collections.emptyList();
		} else {
			final List<XAnnotation> xannotations = new ArrayList<XAnnotation>(
					annotations.length);
			for (Object annotation : annotations) {
				if (annotation != null) {
					if (annotation instanceof XAnnotation) {
						final XAnnotation xannotation = (XAnnotation) annotation;
						xannotations.add(xannotation);
					} else if (annotation instanceof Collection) {
						final Collection<XAnnotation> xannotation = (Collection<XAnnotation>) annotation;
						xannotations.addAll(xannotation);
					} else {
						throw new IllegalArgumentException(
								"Expecting either annotations or collections of annotations.");
					}
				}
			}
			return xannotations;
		}
	}

	public javax.persistence.FetchType getFetchType(String fetch) {
		return fetch == null ? null : javax.persistence.FetchType
				.valueOf(fetch);
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
			return cascades;
		}
	}

	public javax.persistence.DiscriminatorType getDiscriminatorType(
			String discriminatorType) {
		return discriminatorType == null ? null
				: javax.persistence.DiscriminatorType
						.valueOf(discriminatorType);
	}
	
	public javax.persistence.InheritanceType getInheritanceType(String strategy) {
		return strategy == null ? null : javax.persistence.InheritanceType
				.valueOf(strategy);
	}
	
}
