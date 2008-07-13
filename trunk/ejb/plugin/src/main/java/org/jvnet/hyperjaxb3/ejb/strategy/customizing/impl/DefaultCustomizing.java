package org.jvnet.hyperjaxb3.ejb.strategy.customizing.impl;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Entity;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultCustomizing implements Customizing {

	private Persistence defaultCustomizations;

	public Persistence getDefaultCustomizations() {
		return defaultCustomizations;
	}

	@Required
	public void setDefaultCustomizations(Persistence defaultCustomization) {
		this.defaultCustomizations = defaultCustomization;
	}

	public Persistence getModelCustomization(Model model) {
		final Persistence persistence = Customizations
				.<Persistence> findCustomization(model,
						Customizations.PERSISTENCE_ELEMENT_NAME);
		if (persistence == null) {
			return getDefaultCustomizations();
		}
		if (!persistence.isMerge()) {
			return persistence;
		} else {
			final Persistence defaultPersistence = getDefaultCustomizations();
			persistence.mergeFrom(persistence, defaultPersistence);
			if (persistence.getDefaultManyToOne().getJoinTable() != null) {
				persistence.getDefaultManyToOne().getJoinColumn().clear();
			}
			if (persistence.getDefaultOneToMany().getJoinTable() != null) {
				persistence.getDefaultOneToMany().getJoinColumn().clear();
			}
			return persistence;
		}
	}

	public Id getId(CClassInfo classInfo) {

		final Persistence persistence = getModelCustomization(classInfo);
		if (persistence.getDefaultId() == null) {
			throw new AssertionError("Default id element is not provided.");
		}
		final Id defaultId = (Id) persistence.getDefaultId().copyTo(new Id());
		final Id id;
		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.ID_ELEMENT_NAME)) {
			id = Customizations.<Id> findCustomization(classInfo,
					Customizations.ID_ELEMENT_NAME);

			if (id.isMerge()) {
				id.mergeFrom(id, defaultId);
			}
		} else {
			id = defaultId;
		}
		return id;
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
			id = Customizations.<Id> findCustomization(property,
					Customizations.ID_ELEMENT_NAME);

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
			version = Customizations.<Version> findCustomization(property,
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

	public Basic getBasic(CPropertyInfo property) {
		final Persistence persistence = getModelCustomization(property);
		if (persistence.getDefaultBasic() == null) {
			throw new AssertionError("Default basic element is not provided.");
		}
		final Basic defaultBasic = (Basic) persistence.getDefaultBasic()
				.copyTo(new Basic());
		final Basic basic;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.BASIC_ELEMENT_NAME)) {
			basic = Customizations.<Basic> findCustomization(property,
					Customizations.BASIC_ELEMENT_NAME);

			if (basic.isMerge()) {
				basic.mergeFrom(basic, defaultBasic);
			}
		} else {
			basic = defaultBasic;
		}
		return basic;
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
			coneToMany = Customizations.<OneToMany> findCustomization(property,
					Customizations.ONE_TO_MANY_ELEMENT_NAME);

			if (coneToMany.isMerge()) {
				coneToMany.mergeFrom(coneToMany, defaultOneToMany);
				if (coneToMany.getJoinTable() != null) {
					coneToMany.getJoinColumn().clear();
				}
			}
		} else {
			return defaultOneToMany;
		}
		return coneToMany;
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
			cmanyToOne = Customizations.<ManyToOne> findCustomization(property,
					Customizations.MANY_TO_ONE_ELEMENT_NAME);
			if (cmanyToOne.isMerge()) {
				cmanyToOne.mergeFrom(cmanyToOne, defaultManyToOne);
				if (cmanyToOne.getJoinTable() != null) {
					cmanyToOne.getJoinColumn().clear();
				}
			}
		} else {
			return defaultManyToOne;
		}
		return cmanyToOne;
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
			cOneToOne = Customizations.<OneToOne> findCustomization(property,
					Customizations.ONE_TO_ONE_ELEMENT_NAME);
			if (cOneToOne.isMerge()) {
				cOneToOne.mergeFrom(cOneToOne, defaultOneToOne);
				if (cOneToOne.getJoinTable() != null) {
					cOneToOne.getJoinColumn().clear();
				}
			}
		} else {
			return defaultOneToOne;
		}
		return cOneToOne;
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
			cManyToMany = Customizations.<ManyToMany> findCustomization(
					property, Customizations.MANY_TO_MANY_ELEMENT_NAME);
			if (cManyToMany.isMerge()) {
				cManyToMany.mergeFrom(cManyToMany, defaultManyToMany);
			}
		} else {
			return defaultManyToMany;
		}
		return cManyToMany;
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
			cEntity = Customizations.<Entity> findCustomization(classInfo,
					Customizations.ENTITY_ELEMENT_NAME);
			if (cEntity.isMerge()) {
				cEntity.mergeFrom(cEntity, defaultEntity);
			}
		} else {
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
		} else {
			final ToOne defaultToOne = (ToOne) persistence.getDefaultToOne()
					.copyTo(new ToOne());
			if (defaultToOne.getOneToOne() != null) {
				final OneToOne defaultToOne$OneToOne = defaultToOne
						.getOneToOne();

				final OneToOne defaultOneToOne = persistence
						.getDefaultOneToOne();

				if (defaultOneToOne == null) {
					throw new AssertionError(
							"Default One-to-one element is not provided.");
				}

				if (defaultToOne$OneToOne.isMerge()) {
					defaultToOne$OneToOne.mergeFrom(defaultToOne$OneToOne,
							defaultOneToOne);
					if (defaultToOne$OneToOne.getJoinTable() != null) {
						defaultToOne$OneToOne.getJoinColumn().clear();
					}
				}

				return defaultToOne$OneToOne;
			} else if (defaultToOne.getManyToOne() != null) {
				final ManyToOne defaultToOne$ManyToOne = defaultToOne
						.getManyToOne();

				final ManyToOne defaultManyToOne = persistence
						.getDefaultManyToOne();

				if (defaultManyToOne == null) {
					throw new AssertionError(
							"Default many-to-one element is not provided.");
				}

				if (defaultToOne$ManyToOne.isMerge()) {
					defaultToOne$ManyToOne.mergeFrom(defaultToOne$ManyToOne,
							defaultManyToOne);
					if (defaultToOne$ManyToOne.getJoinTable() != null) {
						defaultToOne$ManyToOne.getJoinColumn().clear();
					}
				}

				return defaultToOne$ManyToOne;
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
				final OneToMany defaultToMany$OneToMany = defaultToMany
						.getOneToMany();

				final OneToMany defaultOneToMany = persistence
						.getDefaultOneToMany();

				if (defaultOneToMany == null) {
					throw new AssertionError(
							"Default one-to-many element is not provided.");
				}

				if (defaultToMany$OneToMany.isMerge()) {
					defaultToMany$OneToMany.mergeFrom(defaultToMany$OneToMany,
							defaultOneToMany);
					if (defaultToMany$OneToMany.getJoinTable() != null) {
						defaultToMany$OneToMany.getJoinColumn().clear();
					}
				}

				return defaultToMany$OneToMany;
			} else if (defaultToMany.getManyToMany() != null) {
				final ManyToMany defaultToMany$ManyToMany = defaultToMany
						.getManyToMany();

				final ManyToMany defaultManyToMany = persistence
						.getDefaultManyToMany();

				if (defaultManyToMany == null) {
					throw new AssertionError(
							"Default many-to-many element is not provided.");
				}

				if (defaultToMany$ManyToMany.isMerge()) {
					defaultToMany$ManyToMany.mergeFrom(
							defaultToMany$ManyToMany, defaultManyToMany);
				}

				return defaultToMany$ManyToMany;
			} else {
				throw new AssertionError(
						"Either one-to-many or many-to-many elements must be provided in the default-to-many element.");
			}

		}
	}

}
