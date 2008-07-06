package org.jvnet.hyperjaxb3.ejb.strategy.customizations.base;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Entity;
import org.jvnet.hyperjaxb3.ejb.strategy.customizations.ModelCustomizations;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultModelCustomizations implements ModelCustomizations {

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
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id defaultId = new Id();
		persistence.getDefaultId().copyTo(defaultId);
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id;
		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.ID_ELEMENT_NAME)) {
			id = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id> findCustomization(
							classInfo, Customizations.ID_ELEMENT_NAME);

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
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id defaultId = new Id();
		persistence.getDefaultId().copyTo(defaultId);
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id id;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.ID_ELEMENT_NAME)) {
			id = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id> findCustomization(
							property, Customizations.ID_ELEMENT_NAME);

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
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version defaultVersion = new Version();
		persistence.getDefaultVersion().copyTo(defaultVersion);
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version version;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.VERSION_ELEMENT_NAME)) {
			version = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version> findCustomization(
							property, Customizations.VERSION_ELEMENT_NAME);

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
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic defaultBasic = new Basic();
		persistence.getDefaultBasic().copyTo(defaultBasic);
		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic basic;
		if (CustomizationUtils.containsCustomization(property,
				Customizations.BASIC_ELEMENT_NAME)) {
			basic = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic> findCustomization(
							property, Customizations.BASIC_ELEMENT_NAME);

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

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany;
		final Persistence persistence = getModelCustomization(property);
		final OneToMany defaultOneToMany = persistence.getDefaultOneToMany();
		if (defaultOneToMany == null) {
			// TODO
			throw new AssertionError(
					"Default one-to-many element is not provided.");
		}
		if (CustomizationUtils.containsCustomization(property,
				Customizations.ONE_TO_MANY_ELEMENT_NAME)) {
			coneToMany = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany> findCustomization(
							property, Customizations.ONE_TO_MANY_ELEMENT_NAME);

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
		final ManyToOne defaultManyToOne = persistence.getDefaultManyToOne();
		if (defaultManyToOne == null) {
			// TODO
			throw new AssertionError(
					"Default many-to-one element is not provided.");
		}

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne cmanyToOne;

		if (CustomizationUtils.containsCustomization(property,
				Customizations.MANY_TO_ONE_ELEMENT_NAME)) {
			cmanyToOne = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne> findCustomization(
							property, Customizations.MANY_TO_ONE_ELEMENT_NAME);
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
	
	public Entity getEntity(ClassOutline classOutline) {
		return getEntity(classOutline.target);
	}

	public Entity getEntity(CClassInfo classInfo) {
		
		final Persistence persistence = getModelCustomization(classInfo);
		final Entity defaultEntity = persistence.getDefaultEntity();
		if (defaultEntity == null) {
			// TODO
			throw new AssertionError(
					"Default entity element is not provided.");
		}

		final Entity cEntity;

		if (CustomizationUtils.containsCustomization(classInfo,
				Customizations.ENTITY_ELEMENT_NAME)) {
			cEntity = Customizations
					.<Entity> findCustomization(
							classInfo, Customizations.ENTITY_ELEMENT_NAME);
			if (cEntity.isMerge()) {
				cEntity.mergeFrom(cEntity, defaultEntity);
			}
		} else {
			return defaultEntity;
		}
		return cEntity;
	}
}
