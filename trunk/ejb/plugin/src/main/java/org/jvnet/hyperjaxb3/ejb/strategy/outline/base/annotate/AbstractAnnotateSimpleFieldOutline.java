package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumns;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTables;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.namespace.QName;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.xsd.util.XMLSchemaConstrants;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeVisitor;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
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
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.xsom.XSComponent;

public class AbstractAnnotateSimpleFieldOutline {

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

	public Collection<XAnnotation> createColumn(ProcessOutline context,
			FieldOutline fieldOutline, Options options, Column column) {

		if (column == null) {
			column = new Column();
		}

		if (column.getName() == null) {
			column.setName(context.getNaming().getColumnName(context,
					fieldOutline, options));
		}

		// If string
		if (column.getLength() == null) {
			final Long length = SimpleTypeAnalyzer.getLength(fieldOutline
					.getPropertyInfo().getSchemaComponent());

			if (length != null) {
				column.setLength(length.intValue());
			} else {
				final Long maxLength = SimpleTypeAnalyzer
						.getMaxLength(fieldOutline.getPropertyInfo()
								.getSchemaComponent());
				if (maxLength != null) {
					column.setLength(maxLength.intValue());
				} else {
					final Long minLength = SimpleTypeAnalyzer
							.getMinLength(fieldOutline.getPropertyInfo()
									.getSchemaComponent());
					if (minLength != null) {
						int intMinLength = minLength.intValue();
						if (intMinLength > 127) {
							column.setLength(intMinLength * 2);
						}
					}
				}
			}
		}

		// If decimal
		{
			if (column.getPrecision() == null) {
				final Long totalDigits = SimpleTypeAnalyzer
						.getTotalDigits(fieldOutline.getPropertyInfo()
								.getSchemaComponent());
				if (totalDigits != null) {
					column.setPrecision(totalDigits.intValue());
				}

			}

			if (column.getScale() == null) {
				final Long fractionDigits = SimpleTypeAnalyzer
						.getFractionDigits(fieldOutline.getPropertyInfo()
								.getSchemaComponent());
				if (fractionDigits != null) {
					column.setScale(fractionDigits.intValue());
				}
			}
		}

		return Collections.singletonList(createColumn(column));
	}

	public Collection<XAnnotation> createTemporal(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options, String temporal) {
		final XEnum value = createTemporal$Value(outlineProcessor,
				fieldOutline, options, temporal);
		if (value != null) {
			final XAnnotation ctemporal = new XAnnotation(Temporal.class, value);
			return Collections.singletonList(ctemporal);
		} else {
			return Collections.emptyList();
		}

	}

	public XEnum createTemporal$Value(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options, String temporal) {
		final TemporalType value;
		if (temporal != null) {
			value = TemporalType.valueOf(temporal);
		} else {
			final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

			final JType type = getter.type();

			if (JTypeUtils.isTemporalType(type)) {
				value = getTemporalType(outlineProcessor, fieldOutline, options);
			} else {
				value = null;
			}
		}

		return value == null ? null : new XAnnotationField.XEnum("value",
				value, TemporalType.class);
	}

	public TemporalType getTemporalType(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);
		final JType type = getter.type();
		final JCodeModel codeModel = type.owner();
		// Detect SQL types
		if (codeModel.ref(java.sql.Time.class).equals(type)) {
			return TemporalType.TIME;
		} else if (codeModel.ref(java.sql.Date.class).equals(type)) {
			return TemporalType.DATE;
		} else if (codeModel.ref(java.sql.Timestamp.class).equals(type)) {
			return TemporalType.TIMESTAMP;
		} else if (codeModel.ref(java.util.Calendar.class).equals(type)) {
			return TemporalType.TIMESTAMP;
		} else {
			final List<QName> typeNames;
			final XSComponent schemaComponent = fieldOutline.getPropertyInfo()
					.getSchemaComponent();
			if (schemaComponent != null) {

				final SimpleTypeVisitor visitor = new SimpleTypeVisitor();
				schemaComponent.visit(visitor);
				typeNames = visitor.getTypeNames();
			} else {
				typeNames = Collections.emptyList();
			}
			if (typeNames.contains(XMLSchemaConstrants.DATE_QNAME)
					||
					//
					typeNames.contains(XMLSchemaConstrants.G_YEAR_MONTH_QNAME)
					||
					//
					typeNames.contains(XMLSchemaConstrants.G_YEAR_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_MONTH_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_MONTH_DAY_QNAME) ||
					//
					typeNames.contains(XMLSchemaConstrants.G_DAY_QNAME)) {
				return TemporalType.DATE;
			} else if (typeNames.contains(XMLSchemaConstrants.TIME_QNAME)) {
				return TemporalType.TIME;
			} else if (typeNames.contains(XMLSchemaConstrants.DATE_TIME_QNAME)) {
				return TemporalType.TIMESTAMP;
			} else {
				return TemporalType.TIMESTAMP;
			}
		}
	}

	public Collection<XAnnotation> createEnumerated(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options, String enumerated) {
		final XAnnotationField.XEnum value = createEnumerated$Value(
				outlineProcessor, fieldOutline, options, enumerated);
		if (value == null) {
			return Collections.emptyList();
		} else {
			return Collections.singletonList(new XAnnotation(Enumerated.class,
					value));
		}
	}

	public XAnnotationField.XEnum createEnumerated$Value(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options, String enumerated) {
		final EnumType value;
		if (enumerated != null) {
			value = EnumType.valueOf(enumerated);
		} else {
			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

			final Collection<? extends CTypeInfo> types = propertyInfo.ref();

			assert types.size() == 1;

			final CTypeInfo type = types.iterator().next();

			if (type instanceof CEnumLeafInfo) {
				// TODO analyze string vs ordinal
				value = EnumType.STRING;
			} else {
				value = null;
			}
		}
		return value == null ? null : new XAnnotationField.XEnum("value",
				value, EnumType.class);
	}

	// ==================================================================
	// 8.1
	// ==================================================================

	// 8.1
	public XAnnotation createEntity(Entity cEntity) {
		return new XAnnotation(javax.persistence.Entity.class,
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
		if (cExcludeSuperclassListeners == null) {
			return null;
		} else {
			return new XAnnotation(
					javax.persistence.ExcludeSuperclassListeners.class);
		}
	}

	public XAnnotation createDefaultSuperclassListeners(
			EmptyType cExcludeDefaultListeners) {
		if (cExcludeDefaultListeners == null) {
			return null;
		} else {
			return new XAnnotation(
					javax.persistence.ExcludeDefaultListeners.class);
		}
	}

	// public XAnnotation createEntityListeners(EntityListeners
	// cEntityListeners)

	// 8.3.1
	public XAnnotation createNamedQuery(Collection<NamedQuery> cNamedQueries) {
		return transform(NamedQueries.class, cNamedQueries,
				new Transformer<NamedQuery, XAnnotation>() {
					public XAnnotation transform(NamedQuery input) {
						return createNamedQuery(input);
					}
				});
	}

	public XAnnotation createNamedQuery(NamedQuery cNamedQuery) {
		return new XAnnotation(javax.persistence.NamedQuery.class,
				AnnotationUtils.create("query", cNamedQuery.getQuery()),
				AnnotationUtils.create("hint", createQueryHint(cNamedQuery
						.getHint())),

				AnnotationUtils.create("name", cNamedQuery.getName()));

	}

	public Collection<XAnnotation> createQueryHint(
			Collection<QueryHint> queryHints) {
		return transform(queryHints, new Transformer<QueryHint, XAnnotation>() {
			public XAnnotation transform(QueryHint input) {
				return createQueryHint(input);
			}
		});
	}

	public XAnnotation createQueryHint(QueryHint queryHint) {
		return new XAnnotation(javax.persistence.QueryHint.class,
				AnnotationUtils.create("name", queryHint.getName()),
				AnnotationUtils.create("value", queryHint.getValue()));
	}

	// 8.3.2
	public XAnnotation createNamedNativeQuery(
			Collection<NamedNativeQuery> cNamedNativeQueries) {
		return transform(NamedNativeQueries.class, cNamedNativeQueries,
				new Transformer<NamedNativeQuery, XAnnotation>() {
					public XAnnotation transform(NamedNativeQuery input) {
						return createNamedNativeQuery(input);
					}
				});
	}

	public XAnnotation createNamedNativeQuery(NamedNativeQuery cNamedNativeQuery) {
		return new XAnnotation(javax.persistence.NamedNativeQuery.class,
		//
				AnnotationUtils.create("name", cNamedNativeQuery.getName()),
				//
				AnnotationUtils.create("query", cNamedNativeQuery.getQuery()),
				//
				AnnotationUtils.create("hints",
						createQueryHint(cNamedNativeQuery.getHint())),
				//
				cNamedNativeQuery.getResultClass() == null ? null
						: new XAnnotationField.XClass("resultClass",
								cNamedNativeQuery.getResultClass()),
				//
				AnnotationUtils.create("resultSetMapping", cNamedNativeQuery
						.getResultSetMapping()));
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

	public XAnnotation createSqlResultSetMapping(
			SqlResultSetMapping cSqlResultSetMapping) {
		return new XAnnotation(javax.persistence.SqlResultSetMapping.class,
		//
				AnnotationUtils.create("name", cSqlResultSetMapping.getName()),
				//
				AnnotationUtils.create("entityResult",
						createEntityResult(cSqlResultSetMapping
								.getEntityResult())),
				//
				AnnotationUtils.create("columnResult",
						createColumnResult(cSqlResultSetMapping
								.getColumnResult())));
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

	public XAnnotation createEntityResult(EntityResult cEntityResult) {
		return new XAnnotation(javax.persistence.EntityResult.class,
		//
				new XAnnotationField.XClass("entityClass", cEntityResult
						.getEntityClass()),

				//
				AnnotationUtils.create("fields",
						createFieldResult(cEntityResult.getFieldResult())),
				//
				AnnotationUtils.create("discriminatorColumn", cEntityResult
						.getDiscriminatorColumn()));
	}

	public Collection<XAnnotation> createFieldResult(
			List<FieldResult> fieldResult) {
		return transform(fieldResult,
				new Transformer<FieldResult, XAnnotation>() {
					public XAnnotation transform(FieldResult cFieldResult) {
						return createFieldResult(cFieldResult);
					}
				});
	}

	public XAnnotation createFieldResult(FieldResult cFieldResult) {
		return new XAnnotation(javax.persistence.FieldResult.class,
		//
				AnnotationUtils.create("name", cFieldResult.getName()),
				//
				AnnotationUtils.create("column", cFieldResult.getColumn()));
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

	public XAnnotation createColumnResult(ColumnResult columnResult) {
		return new XAnnotation(javax.persistence.ColumnResult.class,
				AnnotationUtils.create("name", columnResult.getName()));
	}

	// ==================================================================
	// 9.1
	// ==================================================================

	// 9.1.1
	public XAnnotation createTable(Table cTable) {
		return new XAnnotation(javax.persistence.Table.class,
		//
				AnnotationUtils.create("name", cTable.getName()),
				//
				AnnotationUtils.create("catalog", cTable.getCatalog()),
				//
				AnnotationUtils.create("schema", cTable.getSchema()),
				//
				AnnotationUtils.create("uniqueConstraints",
						createUniqueConstraint(cTable.getUniqueConstraint()))
		//
		);

	}

	// 9.1.2
	public XAnnotation createSecondaryTable(SecondaryTable cSecondaryTable) {
		return new XAnnotation(
				javax.persistence.SecondaryTable.class,
				//
				AnnotationUtils.create("name", cSecondaryTable.getName()),
				//
				AnnotationUtils.create("catalog", cSecondaryTable.getCatalog()),
				//
				AnnotationUtils.create("schema", cSecondaryTable.getSchema()),
				//
				AnnotationUtils.create("pkJoinColumns",
						createPrimaryKeyJoinColumn(cSecondaryTable
								.getPrimaryKeyJoinColumn())),
				//
				AnnotationUtils.create("uniqueConstraints",
						createUniqueConstraint(cSecondaryTable
								.getUniqueConstraint()))

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
		return new XAnnotation(javax.persistence.UniqueConstraint.class,
		//
				AnnotationUtils.create("columnNames", cUniqueConstraint
						.getColumnName())
		//
		);
	}

	public Collection<XAnnotation> createUniqueConstraint(
			List<UniqueConstraint> cUniqueConstraint) {
		return transform(cUniqueConstraint,
				new Transformer<UniqueConstraint, XAnnotation>() {
					public XAnnotation transform(UniqueConstraint input) {
						return createUniqueConstraint(input);
					}
				});
	}

	// 9.1.5
	public XAnnotation createColumn(Column column) {

		return new XAnnotation(javax.persistence.Column.class,
		//
				AnnotationUtils.create("name", column.getName()),
				//
				AnnotationUtils.create("unique", column.isUnique()),
				//
				AnnotationUtils.create("nullable", column.isNullable()),
				//
				AnnotationUtils.create("insertable", column.isInsertable()),
				//
				AnnotationUtils.create("updatable", column.isUpdatable()),
				//
				AnnotationUtils.create("columnDefinition", column
						.getColumnDefinition()),
				//
				AnnotationUtils.create("table", column.getTable()),
				//
				AnnotationUtils.create("length", column.getLength()),
				//
				AnnotationUtils.create("precision", column.getPrecision()),
				//
				AnnotationUtils.create("scale", column.getScale()));
	}

	// 9.1.6
	public XAnnotation createJoinColumn(JoinColumn joinColumn) {
		return new XAnnotation(
				javax.persistence.JoinColumn.class,
				//
				AnnotationUtils.create("name", joinColumn.getName()),
				//
				AnnotationUtils.create("referencedColumnName", joinColumn
						.getReferencedColumnName()),
				//
				AnnotationUtils.create("unique", joinColumn.isUnique()),
				//
				AnnotationUtils.create("nullable", joinColumn.isNullable()),
				//
				AnnotationUtils.create("insertable", joinColumn.isInsertable()),
				//
				AnnotationUtils.create("updatable", joinColumn.isUpdatable()),
				//
				AnnotationUtils.create("columnDefinition", joinColumn
						.getColumnDefinition()),
				//
				AnnotationUtils.create("table", joinColumn.getTable())
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

	// 9.1.8
	public XAnnotation createId(Id id) {
		return new XAnnotation(javax.persistence.Id.class);
	}

	// 9.1.9
	public XAnnotation createGeneratedValue(GeneratedValue generatedValue) {

		if (generatedValue == null) {
			return null;
		} else {
			return new XAnnotation(javax.persistence.GeneratedValue.class,
			//
					AnnotationUtils.create("generator", generatedValue
							.getGenerator()),
					//
					createGeneratedValue$Strategy(generatedValue.getStrategy())
			//
			);

		}
	}

	public XAnnotationField.XEnum createGeneratedValue$Strategy(String strategy) {
		return strategy == null ? null : new XAnnotationField.XEnum("strategy",
				GenerationType.valueOf(strategy), GenerationType.class);
	}

	// 9.1.10
	public XAnnotation createAttributeOverride(
			AttributeOverride cAttributeOverride) {
		return new XAnnotation(javax.persistence.AttributeOverride.class,
		//
				AnnotationUtils.create("name", cAttributeOverride.getName()),
				//
				AnnotationUtils.create("column",
						createColumn(cAttributeOverride.getColumn()))
		//
		);
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
			AssociationOverride associationOverride) {
		return new XAnnotation(javax.persistence.AssociationOverride.class,
		//
				AnnotationUtils.create("name", associationOverride.getName()),
				//
				AnnotationUtils.create("joinColumns",
						createJoinColumn(associationOverride.getJoinColumn())));
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
		return new XAnnotation(javax.persistence.EmbeddedId.class);
	}

	// 9.1.15
	public XAnnotation createIdClass(IdClass cIdClass) {
		return new XAnnotation(javax.persistence.IdClass.class,
		//
				cIdClass.getClazz() == null ? null
						: new XAnnotationField.XClass("value", cIdClass
								.getClazz())
		//
		);
	}

	// 9.1.16
	public XAnnotation createTransient(Transient cTransient) {
		return new XAnnotation(javax.persistence.Transient.class);
	}

	// 9.1.17
	public XAnnotation createVersion(Version cVersion) {
		return new XAnnotation(javax.persistence.Version.class);
	}

	// 9.1.18
	public XAnnotation createBasic(Basic cBasic) {
		return new XAnnotation(javax.persistence.Basic.class,
		//
				AnnotationUtils
						.create("fetch", getFetchType(cBasic.getFetch())),
				//
				AnnotationUtils.create("optional", cBasic.isOptional())
		//
		);
	}

	// 9.1.19
	public XAnnotation createLob(Lob cLob) {
		return new XAnnotation(javax.persistence.Lob.class);
	}

	// 9.1.20
	public XAnnotation createTemporal(String temporal) {
		return temporal == null ? null : new XAnnotation(
				javax.persistence.Temporal.class,
				//
				new XAnnotationField.XEnum("value",
						javax.persistence.TemporalType.valueOf(temporal),
						javax.persistence.TemporalType.class));
	}

	// 9.1.21
	public XAnnotation createEnumerated(String enumerated) {
		return enumerated == null ? null
				: new XAnnotation(javax.persistence.Enumerated.class,
				//
						new XAnnotationField.XEnum("value",
								javax.persistence.EnumType.valueOf(enumerated),
								javax.persistence.EnumType.class));
	}

	// 9.1.22
	public XAnnotation createManyToOne(ManyToOne cManyToOne) {
		return new XAnnotation(javax.persistence.ManyToOne.class,
		//
				cManyToOne.getTargetEntity() == null ? null
						: new XAnnotationField.XClass("targetEntity",
								cManyToOne.getTargetEntity()),
				//
				AnnotationUtils.create("cascaseType", getCascadeType(cManyToOne
						.getCascade())),
				//
				AnnotationUtils.create("fetch", getFetchType(cManyToOne
						.getFetch())),
				//
				AnnotationUtils.create("optional", cManyToOne.isOptional())

		//
		);
	}

	// 9.1.23
	public XAnnotation createOneToOne(OneToOne cOneToOne) {
		return new XAnnotation(javax.persistence.OneToOne.class,
		//
				cOneToOne.getTargetEntity() == null ? null
						: new XAnnotationField.XClass("targetEntity", cOneToOne
								.getTargetEntity()),
				//
				AnnotationUtils.create("cascaseType", getCascadeType(cOneToOne
						.getCascade())),
				//
				AnnotationUtils.create("fetch", getFetchType(cOneToOne
						.getFetch())),
				//
				AnnotationUtils.create("optional", cOneToOne.isOptional()),
				//
				AnnotationUtils.create("mappedBy", cOneToOne.getMappedBy())
		//
		);
	}

	// 9.1.24
	public XAnnotation createOneToMany(OneToMany cOneToMany) {
		return new XAnnotation(javax.persistence.OneToMany.class,
		//
				cOneToMany.getTargetEntity() == null ? null
						: new XAnnotationField.XClass("targetEntity",
								cOneToMany.getTargetEntity()),
				//
				AnnotationUtils.create("cascaseType", getCascadeType(cOneToMany
						.getCascade())),
				//
				AnnotationUtils.create("fetch", getFetchType(cOneToMany
						.getFetch())),
				//
				AnnotationUtils.create("mappedBy", cOneToMany.getMappedBy())
		//
		);
	}

	// 9.1.25
	public XAnnotation createJoinTable(JoinTable cJoinTable) {
		return new XAnnotation(javax.persistence.JoinTable.class,

		//
				AnnotationUtils.create("name", cJoinTable.getName()),
				//
				AnnotationUtils.create("catalog", cJoinTable.getCatalog()),
				//
				AnnotationUtils.create("schema", cJoinTable.getSchema()),

				//
				AnnotationUtils.create("joinColumns",
						createJoinColumn(cJoinTable.getJoinColumn())),
				//
				AnnotationUtils.create("inverseJoinColumns",
						createJoinColumn(cJoinTable.getInverseJoinColumn())),
				//
				AnnotationUtils
						.create("uniqueConstraints",
								createUniqueConstraint(cJoinTable
										.getUniqueConstraint()))
		//

		);
	}

	// 9.1.26
	public XAnnotation createManyToMany(ManyToMany cManyToMany) {
		return new XAnnotation(javax.persistence.ManyToMany.class,
		//
				cManyToMany.getTargetEntity() == null ? null
						: new XAnnotationField.XClass("targetEntity",
								cManyToMany.getTargetEntity()),
				//
				AnnotationUtils.create("cascaseType",
						getCascadeType(cManyToMany.getCascade())),
				//
				AnnotationUtils.create("fetch", getFetchType(cManyToMany
						.getFetch())),
				//
				AnnotationUtils.create("mappedBy", cManyToMany.getMappedBy())
		//
		);
	}

	// 9.1.27
	public XAnnotation createMapKey(MapKey cMapKey) {
		return new XAnnotation(javax.persistence.MapKey.class,
		//
				AnnotationUtils.create("name", cMapKey.getName())
		//
		);
	}

	// 9.1.28
	public XAnnotation createOrderBy(String orderBy) {
		if (orderBy == null) {
			return null;
		} else {
			return new XAnnotation(javax.persistence.OrderBy.class,
					AnnotationUtils.create("value", orderBy));
		}
	}

	// 9.1.29
	public XAnnotation createInheritance(Inheritance cInheritance) {
		if (cInheritance == null) {
			return null;
		} else {
			return new XAnnotation(javax.persistence.Inheritance.class,
			//
					AnnotationUtils.create("strategy",
							getInheritanceType(cInheritance.getStrategy()))
			//
			);
		}
	}

	// 9.1.30
	public XAnnotation createDiscriminatorColumn(
			DiscriminatorColumn cDiscriminatorColumn) {
		return new XAnnotation(javax.persistence.DiscriminatorColumn.class,
		//
				AnnotationUtils.create("name", cDiscriminatorColumn.getName()),
				//
				AnnotationUtils.create("discriminatorType",
						getDiscriminatorType(cDiscriminatorColumn
								.getDiscriminatorType())),
				//
				AnnotationUtils.create("columnDefinition", cDiscriminatorColumn
						.getColumnDefinition()),
				//
				AnnotationUtils.create("length", cDiscriminatorColumn
						.getLength())
		//
		);
	}

	// 9.1.31
	public XAnnotation createDiscriminatorValue(String discriminatorValue) {
		return discriminatorValue == null ? null : new XAnnotation(
				javax.persistence.DiscriminatorValue.class, AnnotationUtils
						.create("value", discriminatorValue));
	}

	// 9.1.32
	public XAnnotation createPrimaryKeyJoinColumn(PrimaryKeyJoinColumn input) {
		return new XAnnotation(javax.persistence.PrimaryKeyJoinColumn.class,
		//
				AnnotationUtils.create("name", input.getName()),
				//
				AnnotationUtils.create("referencedColumnName", input
						.getReferencedColumnName()),
				//
				AnnotationUtils.create("columnDefinition", input
						.getColumnDefinition())
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
		return new XAnnotation(javax.persistence.Embeddable.class);
	}

	// 9.1.35
	public XAnnotation createEmbedded(Embedded cEmbedded) {
		return new XAnnotation(javax.persistence.Embedded.class);
	}

	// 9.1.36
	public XAnnotation createMappedSuperclass(
			MappedSuperclass cMappedSupperclass) {
		return new XAnnotation(javax.persistence.MappedSuperclass.class);
	}

	// 9.1.37
	public XAnnotation createSequenceGenerator(
			SequenceGenerator cSequenceGenerator) {

		return new XAnnotation(javax.persistence.SequenceGenerator.class,
		//
				AnnotationUtils.create("name", cSequenceGenerator.getName()),
				//
				AnnotationUtils.create("sequenceName", cSequenceGenerator
						.getSequenceName()),
				//
				AnnotationUtils.create("initialValue", cSequenceGenerator
						.getInitialValue()),
				//
				AnnotationUtils.create("allocationSize", cSequenceGenerator
						.getAllocationSize()));
	}

	// 9.1.38
	public XAnnotation createTableGenerator(TableGenerator cTableGenerator) {

		return new XAnnotation(
				javax.persistence.TableGenerator.class,
				//
				AnnotationUtils.create("name", cTableGenerator.getName()),
				//
				AnnotationUtils.create("table", cTableGenerator.getTable()),
				//
				AnnotationUtils.create("catalog", cTableGenerator.getCatalog()),
				//
				AnnotationUtils.create("schema", cTableGenerator.getSchema()),
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
				AnnotationUtils.create("uniqueConstraints",
						createUniqueConstraint(cTableGenerator
								.getUniqueConstraint()))
		//
		);
	}

	// 10.1.3
	public Collection<XAnnotation> createEntityAnnotations(Entity centity) {
		return annotations(
		//
				createEntity(centity),
				//
				createTable(centity.getTable()),
				//
				createSecondaryTables(centity.getSecondaryTable()),
				//
				createPrimaryKeyJoinColumns(centity.getPrimaryKeyJoinColumn()),
				//
				createIdClass(centity.getIdClass()),
				//
				createInheritance(centity.getInheritance()),
				//
				createDiscriminatorValue(centity.getDiscriminatorValue()),
				//
				createDiscriminatorColumn(centity.getDiscriminatorColumn()),
				//
				createSequenceGenerator(centity.getSequenceGenerator()),
				//
				createTableGenerator(centity.getTableGenerator()),
				//
				createNamedQuery(centity.getNamedQuery()),
				//
				createNamedNativeQuery(centity.getNamedNativeQuery()),
				//
				createSqlResultSetMapping(centity.getSqlResultSetMapping()),
				//
				createExcludeSuperclassListeners(centity
						.getExcludeDefaultListeners()),
				//
				createExcludeSuperclassListeners(centity
						.getExcludeSuperclassListeners()),
				//
				createEntityListeners(centity.getEntityListeners()),
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
				createAttributeOverrides(centity.getAttributeOverride()),
				//
				createAssociationOverrides(centity.getAssociationOverride())
		// "attributes"

		//
		);
	}

	// 10.1.3.22
	public Collection<XAnnotation> createIdAnnotations(Id id) {
		return annotations(
		//
				createId(id),
				//
				createColumn(id.getColumn()),
				//
				createGeneratedValue(id.getGeneratedValue()),
				//
				createTemporal(id.getTemporal()),
				//
				createTableGenerator(id.getTableGenerator()),
				//
				createSequenceGenerator(id.getSequenceGenerator())

		//
		);
	}

	// 10.1.4.10
	public Collection<XAnnotation> createBasicAnnotations(Basic cBasic) {
		return annotations(
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

	// 10.1.4.11
	public Collection<XAnnotation> createVersionAnnotations(Version cVersion) {
		return annotations(
		//
				createVersion(cVersion),
				//
				createColumn(cVersion.getColumn()),
				//
				createTemporal(cVersion.getTemporal()));
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
			return Arrays.asList(annotations);
		}
	}

}
