package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.model.CExternalLeafInfo;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.ElementAsString;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.xml.bind.v2.model.core.WildcardMode;

public class WrapSingleWildcardReference implements CreatePropertyInfos {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {
		assert propertyInfo instanceof CReferencePropertyInfo;
		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) propertyInfo;
		assert referencePropertyInfo.ref().size() == 1;
		assert referencePropertyInfo.getWildcard() != null;
		assert referencePropertyInfo.getElements().isEmpty();

		final WildcardMode wildcard = referencePropertyInfo.getWildcard();

		assert wildcard.equals(WildcardMode.SKIP)
				|| wildcard.equals(WildcardMode.STRICT);

		final CreatePropertyInfos createPropertyInfos;
		if (wildcard.equals(WildcardMode.SKIP)) {
			createPropertyInfos = new AdaptSingleBuiltinNonReference(

					new CExternalLeafInfo(String.class, "string",
							ElementAsString.class));
		} else {
			createPropertyInfos = new AdaptWildcardNonReference(
					CBuiltinLeafInfo.STRING);
		}

		final Collection<CPropertyInfo> newPropertyInfos = createPropertyInfos
				.process(context, propertyInfo);

		for (CPropertyInfo newPropertyInfo : newPropertyInfos) {
			final Basic basic = new Basic();
			basic.setLob(new Lob());

			CustomizationUtils.addCustomization(newPropertyInfo, Customizations
					.getContext(), Customizations.BASIC_ELEMENT_NAME, basic);

			Customizations.markGenerated(newPropertyInfo);
		}
		Customizations.markIgnored(propertyInfo);
		return newPropertyInfos;
	}
}
