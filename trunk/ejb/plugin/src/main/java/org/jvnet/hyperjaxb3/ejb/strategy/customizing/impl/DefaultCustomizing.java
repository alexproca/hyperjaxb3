package org.jvnet.hyperjaxb3.ejb.strategy.customizing.impl;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Embeddable;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Embedded;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.EmbeddedId;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Entity;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.GeneratedClass;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.GeneratedId;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.GeneratedProperty;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.MappedSuperclass;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.SingleProperty;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.hyperjaxb3.xsom.TypeUtils;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CCustomizable;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.xsom.XSComponent;

public class DefaultCustomizing implements Customizing {

	private final static Log logger = LogFactory.getLog(Customizations.class);

	private final Map<CPluginCustomization, Object> customizationsMap = new IdentityHashMap<CPluginCustomization, Object>();

	@SuppressWarnings("unchecked")
	public <T> T findCustomization(Model model, QName name) {
		final CPluginCustomization customization = CustomizationUtils
				.findCustomization(model, name);
		return (T) unmarshalCustomization(customization);
	}

	@SuppressWarnings("unchecked")
	public <T> T findCustomization(CClassInfo classInfo, QName name) {
		final CPluginCustomization customization = CustomizationUtils
				.findCustomization(classInfo, name);
		return (T) unmarshalCustomization(customization);
	}

	@SuppressWarnings("unchecked")
	public <T> T findCustomization(CPropertyInfo propertyInfo, QName name) {
		final CPluginCustomization customization = CustomizationUtils
				.findCustomization(propertyInfo, name);
		return (T) unmarshalCustomization(customization);
	}

	public void addCustomization(CCustomizable customizable, QName name,
			Object customization) {
		final CPluginCustomization pluginCustomization = CustomizationUtils
				.addCustomization(customizable, Customizations.getContext(),
						name, customization);
		this.customizationsMap.put(pluginCustomization, customization);

	}

	private Object unmarshalCustomization(
			final CPluginCustomization customization) throws AssertionError {
		if (customization == null) {
			return null;
		} else {
			final Object value = this.customizationsMap.get(customization);

			if (value != null)

			{
				return value;
			} else {
				final Object newValue = CustomizationUtils.unmarshall(
						Customizations.getContext(), customization);
				this.customizationsMap.put(customization, newValue);
				return newValue;
			}
		}
	}

	private Persistence defaultCustomizations;

	public Persistence getDefaultCustomizations() {
		return defaultCustomizations;
	}

	@Required
	public void setDefaultCustomizations(Persistence defaultCustomization) {
		this.defaultCustomizations = defaultCustomization;
	}

	public Persistence getModelCustomization(Model model) {
		final Persistence cPersistence = findCustomization(model,
				Customizations.PERSISTENCE_ELEMENT_NAME);
		if (cPersistence == null) {
			return getDefaultCustomizations();
		}
		if (!cPersistence.isMerge()) {
			return cPersistence;
		} else {
			final Persistence defaultPersistence = getDefaultCustomizations();
			if (cPersistence.getDefaultManyToOne() != null) {
				if (cPersistence.getDefaultManyToOne().getJoinTable() != null) {
					defaultPersistence.getDefaultManyToOne().getJoinColumn()
							.clear();
				} else if (!cPersistence.getDefaultManyToOne().getJoinColumn()
						.isEmpty()) {
					defaultPersistence.getDefaultManyToOne().setJoinTable(null);
				}
			}
			if (cPersistence.getDefaultOneToOne() != null) {
				if (cPersistence.getDefaultOneToOne().getJoinTable() != null) {
					defaultPersistence.getDefaultOneToOne().getJoinColumn()
							.clear();
				} else if (!cPersistence.getDefaultOneToOne().getJoinColumn()
						.isEmpty()) {
					defaultPersistence.getDefaultOneToOne().setJoinTable(null);
				}
			}
			if (cPersistence.getDefaultOneToMany() != null) {
				if (cPersistence.getDefaultOneToMany().getJoinTable() != null) {
					defaultPersistence.getDefaultOneToMany().getJoinColumn()
							.clear();
				} else if (!cPersistence.getDefaultOneToMany().getJoinColumn()
						.isEmpty()) {
					defaultPersistence.getDefaultOneToMany().setJoinTable(null);
				}
			}
			cPersistence.getDefaultSingleProperty().addAll(
					defaultPersistence.getDefaultSingleProperty());
			cPersistence.mergeFrom(cPersistence, defaultPersistence);
			return cPersistence;
		}
	}

	public GeneratedId getGeneratedId(CClassInfo classInfo) {

		final Persistence persistence = getModelCustomization(classInfo);
		if (persistence.getDefaultGeneratedId() == null) {
			throw new AssertionError("Default id element is not provided.");
		}
		final GeneratedId defaultId = (GeneratedId) persistence
				.getDefaultGeneratedId().copyTo(new GeneratedId());
		final GeneratedId id;
		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.GENERATED_ID_ELEMENT_NAME)) {
			id = findCustomization(classInfo,
					Customizations.GENERATED_ID_ELEMENT_NAME);

			if (id.isMerge()) {
				id.mergeFrom(id, defaultId);
			}
		} else {
			id = defaultId;
		}
		return id;
	}

	public GeneratedClass getGeneratedClass(CPropertyInfo propertyInfo) {
		final GeneratedClass generatedClass;
		if (CustomizationUtils.containsCustomization(propertyInfo,
				Customizations.GENERATED_CLASS_ELEMENT_NAME)) {
			generatedClass = findCustomization(propertyInfo,
					Customizations.GENERATED_CLASS_ELEMENT_NAME);
		} else {
			generatedClass = null;
		}
		return generatedClass;
	}

	public GeneratedProperty getGeneratedProperty(CPropertyInfo propertyInfo) {
		final GeneratedProperty generatedProperty;
		if (CustomizationUtils.containsCustomization(propertyInfo,
				Customizations.GENERATED_PROPERTY_ELEMENT_NAME)) {
			generatedProperty = findCustomization(propertyInfo,
					Customizations.GENERATED_PROPERTY_ELEMENT_NAME);
		} else {
			generatedProperty = null;
		}
		return generatedProperty;
	}

	public Id getId(CPropertyInfo property) {
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultId() == null) {
			throw new AssertionError("Default id element is not provided.");
		}
		final Id defaultId = (Id) persistence.getDefaultId().copyTo(new Id());
		final Id id;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.ID_ELEMENT_NAME)) {
			id = findCustomization(property, Customizations.ID_ELEMENT_NAME);

			if (id.isMerge()) {
				id.mergeFrom(id, defaultId);
			}
		} else {
			id = defaultId;
		}
		return id;
	}

	public Id getId(FieldOutline property) {
		return getId(property.getPropertyInfo());
	}

	public EmbeddedId getEmbeddedId(CPropertyInfo property) {
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultEmbeddedId() == null) {
			throw new AssertionError(
					"Default embedded id element is not provided.");
		}
		final EmbeddedId defaultId = (EmbeddedId) persistence
				.getDefaultEmbeddedId().copyTo(new EmbeddedId());
		final EmbeddedId id;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.EMBEDDED_ID_ELEMENT_NAME)) {
			id = findCustomization(property,
					Customizations.EMBEDDED_ID_ELEMENT_NAME);
			if (id.isMerge()) {
				id.mergeFrom(id, defaultId);
			}
		} else {
			id = defaultId;
		}
		return id;
	}

	public EmbeddedId getEmbeddedId(FieldOutline property) {
		return getEmbeddedId(property.getPropertyInfo());
	}

	public Version getVersion(CPropertyInfo property) {
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultVersion() == null) {
			throw new AssertionError("Default version element is not provided.");
		}
		final Version defaultVersion = (Version) persistence
				.getDefaultVersion().copyTo(new Version());
		final Version version;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.VERSION_ELEMENT_NAME)) {
			version = findCustomization(property,
					Customizations.VERSION_ELEMENT_NAME);

			if (version.isMerge()) {
				version.mergeFrom(version, defaultVersion);
			}
		} else {
			version = defaultVersion;
		}
		return version;
	}

	public Version getVersion(FieldOutline property) {
		return getVersion(property.getPropertyInfo());
	}

	public Basic getDefaultBasic(CPropertyInfo property) throws AssertionError {
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultBasic() == null) {
			throw new AssertionError("Default basic element is not provided.");
		}

		final Basic defaultBasic;
		final XSComponent schemaComponent = property.getSchemaComponent();
		if (schemaComponent == null) {
			defaultBasic = (Basic) persistence.getDefaultBasic().copyTo(
					new Basic());
		} else {
			final List<QName> typeNames = TypeUtils
					.getTypeNames(schemaComponent);
			Basic basic = null;
			for (Iterator<QName> typeNameIterator = typeNames.iterator(); typeNameIterator
					.hasNext()
					&& basic == null;) {
				final QName typeName = typeNameIterator.next();
				final SingleProperty singleProperty = getDefaultSingleProperty(
						persistence, typeName);
				if (singleProperty != null) {
					if (singleProperty.getBasic() != null) {
						basic = singleProperty.getBasic();
					} else {
						logger
								.warn("Default single property for type ["
										+ typeName
										+ "] does not define the expected basic mapping.");
					}
				}
			}
			if (basic == null) {
				defaultBasic = (Basic) persistence.getDefaultBasic().copyTo(
						new Basic());
			} else {
				defaultBasic = (Basic) basic.copyTo(new Basic());
				if (defaultBasic.isMerge()) {
					defaultBasic.mergeFrom(defaultBasic, (Basic) persistence
							.getDefaultBasic().copyTo(new Basic()));
				}
			}
		}

		if (defaultBasic.getColumn() != null) {

			final Integer length = createColumn$Length(property);

			if (length != null) {
				defaultBasic.getColumn().setLength(length);
			}

			final Integer precision = createColumn$Precision(property);
			if (precision != null) {
				defaultBasic.getColumn().setPrecision(precision);
			}

			final Integer scale = createColumn$Scale(property);
			if (scale != null && scale.intValue() != 0) {
				defaultBasic.getColumn().setScale(scale);
			}

		}
		return defaultBasic;
	}

	public SingleProperty getDefaultSingleProperty(Persistence persistence,
			QName typeName) {
		Validate.notNull(persistence);
		Validate.notNull(typeName);
		for (final SingleProperty singleProperty : persistence
				.getDefaultSingleProperty()) {
			if (typeName.equals(singleProperty.getType())) {
				return singleProperty;
			}
		}
		return null;
	}

	public Basic getBasic(CPropertyInfo property) {
		final Basic defaultBasic = getDefaultBasic(property);
		final Basic basic;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.BASIC_ELEMENT_NAME)) {
			basic = findCustomization(property,
					Customizations.BASIC_ELEMENT_NAME);
			if (basic.isMerge()) {
				basic.mergeFrom(basic, defaultBasic);
			}
		} else {
			basic = defaultBasic;
		}
		return basic;
	}

	public Integer createColumn$Scale(CPropertyInfo property) {
		final Integer scale;
		final Long fractionDigits = SimpleTypeAnalyzer
				.getFractionDigits(property.getSchemaComponent());
		if (fractionDigits != null) {
			scale = fractionDigits.intValue();
		} else {
			scale = null;
		}
		return scale;
	}

	public Integer createColumn$Precision(CPropertyInfo property) {
		final Integer precision;
		final Long totalDigits = SimpleTypeAnalyzer.getTotalDigits(property
				.getSchemaComponent());
		if (totalDigits != null) {
			precision = totalDigits.intValue();
		} else {
			precision = null;
		}
		return precision;
	}

	public Integer createColumn$Length(CPropertyInfo property) {
		final Integer finalLength;
		final Long length = SimpleTypeAnalyzer.getLength(property
				.getSchemaComponent());

		if (length != null) {
			finalLength = length.intValue();
		} else {
			final Long maxLength = SimpleTypeAnalyzer.getMaxLength(property
					.getSchemaComponent());
			if (maxLength != null) {
				finalLength = maxLength.intValue();
			} else {
				final Long minLength = SimpleTypeAnalyzer.getMinLength(property
						.getSchemaComponent());
				if (minLength != null) {
					int intMinLength = minLength.intValue();
					if (intMinLength > 127) {
						finalLength = intMinLength * 2;
					} else {
						finalLength = null;
					}
				} else {
					finalLength = null;
				}
			}
		}
		return finalLength;
	}

	public Basic getBasic(FieldOutline fieldOutline) {
		return getBasic(fieldOutline.getPropertyInfo());
	}

	public OneToMany getOneToMany(CPropertyInfo property) {

		final OneToMany coneToMany;
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultOneToMany() == null) {
			throw new AssertionError(
					"Default one-to-many element is not provided.");
		}
		final OneToMany defaultOneToMany = (OneToMany) persistence
				.getDefaultOneToMany().copyTo(new OneToMany());
		if (CustomizationUtils.containsCustomization(property,
				Customizations.ONE_TO_MANY_ELEMENT_NAME)) {
			coneToMany = findCustomization(property,
					Customizations.ONE_TO_MANY_ELEMENT_NAME);

			merge(coneToMany, defaultOneToMany);
		} else {
			return defaultOneToMany;
		}
		return coneToMany;
	}

	private void merge(final OneToMany cOneToMany,
			final OneToMany defaultOneToMany) {
		if (cOneToMany.isMerge()) {
			if (cOneToMany.getJoinTable() != null) {
				defaultOneToMany.getJoinColumn().clear();
			} else if (!cOneToMany.getJoinColumn().isEmpty()) {
				defaultOneToMany.setJoinTable(null);
			}
			cOneToMany.mergeFrom(cOneToMany, defaultOneToMany);
		}
	}

	private void merge(final Embedded cEmbedded, final Embedded defaultEmbedded) {
		if (cEmbedded.isMerge()) {
			cEmbedded.mergeFrom(cEmbedded, defaultEmbedded);
		}
	}

	public Persistence getModelCustomization(CPropertyInfo property) {
		final CClassInfo classInfo = (CClassInfo) property.parent();
		return getModelCustomization(classInfo);
	}

	public Persistence getModelCustomization(final CClassInfo classInfo) {
		final Persistence persistence = getModelCustomization(classInfo.model);
		return persistence;
	}

	public OneToMany getOneToMany(FieldOutline property) {
		return getOneToMany(property.getPropertyInfo());
	}

	public ManyToOne getManyToOne(CPropertyInfo property) {

		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultManyToOne() == null) {
			// TODO
			throw new AssertionError(
					"Default many-to-one element is not provided.");
		}
		final ManyToOne defaultManyToOne = (ManyToOne) persistence
				.getDefaultManyToOne().copyTo(new ManyToOne());

		final ManyToOne cmanyToOne;

		if (CustomizationUtils.containsCustomization(property,
				Customizations.MANY_TO_ONE_ELEMENT_NAME)) {
			cmanyToOne = findCustomization(property,
					Customizations.MANY_TO_ONE_ELEMENT_NAME);
			merge(cmanyToOne, defaultManyToOne);
		} else {
			return defaultManyToOne;
		}
		return cmanyToOne;
	}

	private void merge(final ManyToOne cManyToOne,
			final ManyToOne defaultManyToOne) {
		if (cManyToOne.isMerge()) {
			if (cManyToOne.getJoinTable() != null) {
				defaultManyToOne.getJoinColumn().clear();
			} else if (!cManyToOne.getJoinColumn().isEmpty()) {
				defaultManyToOne.setJoinTable(null);
			}
			cManyToOne.mergeFrom(cManyToOne, defaultManyToOne);
		}
	}

	public ManyToOne getManyToOne(FieldOutline property) {
		return getManyToOne(property.getPropertyInfo());
	}

	public OneToOne getOneToOne(CPropertyInfo property) {

		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultOneToOne() == null) {
			// TODO
			throw new AssertionError(
					"Default one-to-one element is not provided.");
		}
		final OneToOne defaultOneToOne = (OneToOne) persistence
				.getDefaultOneToOne().copyTo(new OneToOne());

		final OneToOne cOneToOne;

		if (CustomizationUtils.containsCustomization(property,
				Customizations.ONE_TO_ONE_ELEMENT_NAME)) {
			cOneToOne = findCustomization(property,
					Customizations.ONE_TO_ONE_ELEMENT_NAME);
			merge(cOneToOne, defaultOneToOne);
		} else {
			return defaultOneToOne;
		}
		return cOneToOne;
	}

	private void merge(final OneToOne cOneToOne, final OneToOne defaultOneToOne) {
		if (cOneToOne.isMerge()) {
			if (cOneToOne.getJoinTable() != null) {
				defaultOneToOne.getJoinColumn().clear();
			} else if (!cOneToOne.getJoinColumn().isEmpty()) {
				defaultOneToOne.setJoinTable(null);
			}
			cOneToOne.mergeFrom(cOneToOne, defaultOneToOne);
		}
	}

	public OneToOne getOneToOne(FieldOutline property) {
		return getOneToOne(property.getPropertyInfo());
	}

	public ManyToMany getManyToMany(CPropertyInfo property) {

		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultManyToMany() == null) {
			// TODO
			throw new AssertionError(
					"Default many-to-many element is not provided.");
		}
		final ManyToMany defaultManyToMany = (ManyToMany) persistence
				.getDefaultManyToMany().copyTo(new ManyToMany());

		final ManyToMany cManyToMany;

		if (CustomizationUtils.containsCustomization(property,
				Customizations.MANY_TO_MANY_ELEMENT_NAME)) {
			cManyToMany = findCustomization(property,
					Customizations.MANY_TO_MANY_ELEMENT_NAME);
			merge(cManyToMany, defaultManyToMany);
		} else {
			return defaultManyToMany;
		}
		return cManyToMany;
	}

	private void merge(final ManyToMany cManyToMany,
			final ManyToMany defaultManyToMany) {
		if (cManyToMany.isMerge()) {
			cManyToMany.mergeFrom(cManyToMany, defaultManyToMany);
		}
	}

	public ManyToMany getManyToMany(FieldOutline property) {
		return getManyToMany(property.getPropertyInfo());
	}

	public Entity getEntity(ClassOutline classOutline) {
		return getEntity(classOutline.target);
	}

	public Entity getEntity(CClassInfo classInfo) {

		final Persistence persistence = getModelCustomization(classInfo);
		if (persistence.getDefaultEntity() == null) {
			// TODO
			throw new AssertionError("Default entity element is not provided.");
		}
		final Entity defaultEntity = (Entity) persistence.getDefaultEntity()
				.copyTo(new Entity());

		final Entity cEntity;

		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.ENTITY_ELEMENT_NAME)) {
			cEntity = findCustomization(classInfo,
					Customizations.ENTITY_ELEMENT_NAME);
			if (cEntity.isMerge()) {
				cEntity.mergeFrom(cEntity, defaultEntity);
			}
		} else {
			addCustomization(classInfo, Customizations.ENTITY_ELEMENT_NAME,
					defaultEntity);
			return defaultEntity;
		}
		return cEntity;
	}

	public Object getToMany(FieldOutline property) {
		return getToMany(property.getPropertyInfo());
	}

	public Object getToOne(FieldOutline property) {
		return getToOne(property.getPropertyInfo());
	}

	public Object getToOne(CPropertyInfo property) {

		final Persistence persistence = getModelCustomization(property);
		if (CustomizationUtils.containsCustomization(property,
				Customizations.MANY_TO_ONE_ELEMENT_NAME)) {
			return getManyToOne(property);
		} else if (CustomizationUtils.containsCustomization(property,
				Customizations.ONE_TO_ONE_ELEMENT_NAME)) {
			return getOneToOne(property);
		}
		if (CustomizationUtils.containsCustomization(property,
				Customizations.EMBEDDED_ELEMENT_NAME)) {
			return getEmbedded(property);
		} else {
			final ToOne defaultToOne = (ToOne) persistence.getDefaultToOne()
					.copyTo(new ToOne());
			if (defaultToOne.getOneToOne() != null) {
				final OneToOne cOneToOne = defaultToOne.getOneToOne();

				final OneToOne defaultOneToOne = persistence
						.getDefaultOneToOne();

				if (defaultOneToOne == null) {
					throw new AssertionError(
							"Default One-to-one element is not provided.");
				}

				merge(cOneToOne, defaultOneToOne);

				return cOneToOne;
			} else if (defaultToOne.getManyToOne() != null) {
				final ManyToOne cManyToOne = defaultToOne.getManyToOne();

				final ManyToOne defaultManyToOne = persistence
						.getDefaultManyToOne();

				if (defaultManyToOne == null) {
					throw new AssertionError(
							"Default many-to-one element is not provided.");
				}

				merge(cManyToOne, defaultManyToOne);

				return cManyToOne;
			} else if (defaultToOne.getEmbedded() != null) {
				final Embedded cEmbedded = defaultToOne.getEmbedded();

				final Embedded defaultEmbedded = persistence
						.getDefaultEmbedded();

				if (defaultEmbedded == null) {
					throw new AssertionError(
							"Default embedded element is not provided.");
				}

				merge(cEmbedded, defaultEmbedded);

				return cEmbedded;
			} else {
				throw new AssertionError(
						"Either one-to-one or many-to-one elements must be provided in the default-to-one element.");
			}

		}
	}

	public Object getToMany(CPropertyInfo property) {

		final Persistence persistence = getModelCustomization(property);
		if (CustomizationUtils.containsCustomization(property,
				Customizations.MANY_TO_MANY_ELEMENT_NAME)) {
			return getManyToMany(property);
		} else if (CustomizationUtils.containsCustomization(property,
				Customizations.ONE_TO_MANY_ELEMENT_NAME)) {
			return getOneToMany(property);
		} else {
			final ToMany defaultToMany = (ToMany) persistence
					.getDefaultToMany().copyTo(new ToMany());
			if (defaultToMany.getOneToMany() != null) {
				final OneToMany cOneToMany = defaultToMany.getOneToMany();

				final OneToMany defaultOneToMany = persistence
						.getDefaultOneToMany();

				if (defaultOneToMany == null) {
					throw new AssertionError(
							"Default one-to-many element is not provided.");
				}

				merge(cOneToMany, defaultOneToMany);
				return cOneToMany;
			} else if (defaultToMany.getManyToMany() != null) {
				final ManyToMany cManyToMany = defaultToMany.getManyToMany();

				final ManyToMany defaultManyToMany = persistence
						.getDefaultManyToMany();

				if (defaultManyToMany == null) {
					throw new AssertionError(
							"Default many-to-many element is not provided.");
				}

				merge(cManyToMany, defaultManyToMany);

				return cManyToMany;
			} else {
				throw new AssertionError(
						"Either one-to-many or many-to-many elements must be provided in the default-to-many element.");
			}

		}
	}

	public MappedSuperclass getMappedSuperclass(ClassOutline classOutline) {
		return getMappedSuperclass(classOutline.target);
	}

	public MappedSuperclass getMappedSuperclass(CClassInfo classInfo) {

		final Persistence persistence = getModelCustomization(classInfo);
		if (persistence.getDefaultMappedSuperclass() == null) {
			// TODO
			throw new AssertionError(
					"Default mapped superclass element is not provided.");
		}
		final MappedSuperclass defaultMappedSuperclass = (MappedSuperclass) persistence
				.getDefaultMappedSuperclass().copyTo(new MappedSuperclass());

		final MappedSuperclass cMappedSuperclass;

		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME)) {
			cMappedSuperclass = findCustomization(classInfo,
					Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME);
			if (cMappedSuperclass.isMerge()) {
				cMappedSuperclass.mergeFrom(cMappedSuperclass,
						defaultMappedSuperclass);
			}
		} else {
			return defaultMappedSuperclass;
		}
		return cMappedSuperclass;
	}

	public Object getEntityOrMappedSuperclassOrEmbeddable(
			ClassOutline classOutline) {
		return getEntityOrMappedSuperclassOrEmbeddable(classOutline.target);
	}

	public Object getEntityOrMappedSuperclassOrEmbeddable(CClassInfo classInfo) {

		// final Persistence persistence = getModelCustomization(classInfo);
		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.ENTITY_ELEMENT_NAME)) {
			return getEntity(classInfo);
		} else if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.MAPPED_SUPERCLASS_ELEMENT_NAME)) {
			return getMappedSuperclass(classInfo);
		} else if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.EMBEDDABLE_ELEMENT_NAME)) {
			return getEmbeddable(classInfo);
		} else {
			// Default is entity
			return getEntity(classInfo);
		}
	}

	public Embeddable getEmbeddable(CClassInfo classInfo) {
		final Persistence persistence = getModelCustomization(classInfo);
		if (persistence.getDefaultEmbeddable() == null) {
			// TODO
			throw new AssertionError(
					"Default embeddable element is not provided.");
		}
		final Embeddable defaultEmbeddable = (Embeddable) persistence
				.getDefaultEmbeddable().copyTo(new Embeddable());

		final Embeddable cEmbeddable;

		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.EMBEDDABLE_ELEMENT_NAME)) {
			cEmbeddable = findCustomization(classInfo,
					Customizations.EMBEDDABLE_ELEMENT_NAME);
			if (cEmbeddable.isMerge()) {
				cEmbeddable.mergeFrom(cEmbeddable, defaultEmbeddable);
			}
		} else {
			return defaultEmbeddable;
		}
		return cEmbeddable;
	}

	public Embeddable getEmbeddable(ClassOutline classOutline) {
		return getEmbeddable(classOutline.target);
	}

	public Embedded getEmbedded(CPropertyInfo property) {
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultEmbedded() == null) {
			throw new AssertionError(
					"Default embedded element is not provided.");
		}
		final Embedded defaultEmbedded = (Embedded) persistence
				.getDefaultEmbedded().copyTo(new Embedded());
		final Embedded embedded;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.EMBEDDED_ELEMENT_NAME)) {
			embedded = findCustomization(property,
					Customizations.EMBEDDED_ELEMENT_NAME);

			if (embedded.isMerge()) {
				embedded.mergeFrom(embedded, defaultEmbedded);
			}
		} else {
			embedded = defaultEmbedded;
		}
		return embedded;
	}

	public Embedded getEmbedded(FieldOutline property) {
		return getEmbedded(property.getPropertyInfo());
	}

}
