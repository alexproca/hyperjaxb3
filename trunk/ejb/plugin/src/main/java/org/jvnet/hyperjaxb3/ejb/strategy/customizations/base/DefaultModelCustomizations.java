package org.jvnet.hyperjaxb3.ejb.strategy.customizations.base;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.strategy.customizations.ModelCustomizations;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
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
		return persistence != null ? persistence : getDefaultCustomizations();
	}

	public OneToMany getOneToMany(CPropertyInfo property) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany coneToMany;

		final CClassInfo classInfo = (CClassInfo) property.parent();
		final Persistence persistence = getModelCustomization(classInfo.model);
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

	public OneToMany getOneToMany(FieldOutline property) {
		return getOneToMany(property.getPropertyInfo());
	}

	public ManyToOne getManyToOne(CPropertyInfo property) {

		final CClassInfo classInfo = (CClassInfo) property.parent();
		final Persistence persistence = getModelCustomization(classInfo.model);
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
}
