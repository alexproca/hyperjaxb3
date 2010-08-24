package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.model.base;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.AbstractWrapBuiltin;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.CreateNoPropertyInfos;

import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.TypeUse;

public class WrapCollectionBuiltinNonReference extends AbstractWrapBuiltin {

	protected Log logger = LogFactory.getLog(getClass());

	public CBuiltinLeafInfo getTypeUse(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return (CBuiltinLeafInfo) propertyInfo.ref().iterator().next();
	}

	@Override
	public CreatePropertyInfos getCreatePropertyInfos(ProcessModel context,
			CPropertyInfo propertyInfo) {

		final CBuiltinLeafInfo originalTypeUse = getTypeUse(context,
				propertyInfo);

		final TypeUse adaptingTypeUse = context.getAdaptBuiltinTypeUse()
				.process(context, propertyInfo);

		if (adaptingTypeUse == originalTypeUse
				|| adaptingTypeUse.getAdapterUse() == null) {
			logger.debug("No adaptation required.");
			return CreateNoPropertyInfos.INSTANCE;

		} else {
			logger.error("In progress (HJIII-63).");
			return CreateNoPropertyInfos.INSTANCE;
		}
	}

	protected Collection<CPropertyInfo> wrapAnyType(ProcessModel context,
			CPropertyInfo propertyInfo) {
		logger.error("In progress (HJIII-63).");
		return CreateNoPropertyInfos.INSTANCE.process(context, propertyInfo);
	}

}
