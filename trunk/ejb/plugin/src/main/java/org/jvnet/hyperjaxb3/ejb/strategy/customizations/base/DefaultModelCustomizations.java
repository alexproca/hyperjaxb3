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

		if (CustomizationUtils.containsCustomization(property,
				Customizations.ONE_TO_MANY_ELEMENT_NAME)) {
			coneToMany = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany> findCustomization(
							property, Customizations.ONE_TO_MANY_ELEMENT_NAME);

		} else {
			final CClassInfo classInfo = (CClassInfo) property.parent();
			final Persistence persistence = getModelCustomization(classInfo.model);
			if (persistence != null
					&& persistence.getDefaultOneToMany() != null) {
				coneToMany = persistence.getDefaultOneToMany();
			} else {
				// Default;
				coneToMany = new org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany();
				coneToMany.getJoinColumn().add(new JoinColumn());
			}

		}
		return coneToMany;
	}

	public OneToMany getOneToMany(FieldOutline property) {
		return getOneToMany(property.getPropertyInfo());
	}

	public ManyToOne getManyToOne(CPropertyInfo property) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne cmanyToOne;

		if (CustomizationUtils.containsCustomization(property,
				Customizations.MANY_TO_ONE_ELEMENT_NAME)) {
			cmanyToOne = Customizations
					.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne> findCustomization(
							property, Customizations.MANY_TO_ONE_ELEMENT_NAME);

		} else {
			final CClassInfo classInfo = (CClassInfo) property.parent();
			final Persistence persistence = getModelCustomization(classInfo.model);
			if (persistence != null
					&& persistence.getDefaultManyToOne() != null) {
				cmanyToOne = persistence.getDefaultManyToOne();
			} else {
				// Default;
				cmanyToOne = new org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne();
				cmanyToOne.getJoinColumn().add(new JoinColumn());
			}

		}
		return cmanyToOne;
	}

	public ManyToOne getManyToOne(FieldOutline property) {
		return getManyToOne(property.getPropertyInfo());
	}
}
