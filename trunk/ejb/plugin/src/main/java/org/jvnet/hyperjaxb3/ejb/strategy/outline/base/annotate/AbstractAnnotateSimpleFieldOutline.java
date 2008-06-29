package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.namespace.QName;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XClass;
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
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.ColumnResult;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.EntityResult;
import com.sun.java.xml.ns.persistence.orm.FieldResult;
import com.sun.java.xml.ns.persistence.orm.NamedNativeQuery;
import com.sun.java.xml.ns.persistence.orm.NamedQuery;
import com.sun.java.xml.ns.persistence.orm.QueryHint;
import com.sun.java.xml.ns.persistence.orm.SqlResultSetMapping;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.xsom.XSComponent;

public class AbstractAnnotateSimpleFieldOutline {

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

	protected XAnnotation createColumn(Column column) {

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

	public Collection<XAnnotation> createEntityAnnotations(Entity centity) {
		return annotations(
		//
				createEntity(centity),
				//
				createNamedQuery(centity.getNamedQuery()),
				//
				createNamedNativeQuery(centity.getNamedNativeQuery()),
				//
				createSqlResultSetMapping(centity.getSqlResultSetMapping())
		//
		);
	}

	// 8.1
	public XAnnotation createEntity(Entity cEntity) {
		return new XAnnotation(javax.persistence.Entity.class, AnnotationUtils
				.create("name", cEntity.getName()));
	}

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
}
