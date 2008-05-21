package org.jvnet.hyperjaxb3.ejb.strategy.customizations.base;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Persistence;
import org.jvnet.hyperjaxb3.ejb.strategy.customizations.ModelCustomizations;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultModelCustomizations implements ModelCustomizations {

	public Persistence getDefaultCustomization() {
		return null;
	}

	public Persistence getModelCustomization(Model model) {
		final Persistence persistence = Customizations
				.<Persistence> findCustomization(model,
						Customizations.PERSISTENCE_ELEMENT_NAME);
		return persistence != null ? persistence : getDefaultCustomization();
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

}
