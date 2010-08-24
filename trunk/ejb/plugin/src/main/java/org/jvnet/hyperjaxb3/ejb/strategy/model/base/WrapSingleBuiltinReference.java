package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.TypeUse;

public class WrapSingleBuiltinReference extends AbstractWrapBuiltin {

	protected Log logger = LogFactory.getLog(getClass());

	public CBuiltinLeafInfo getTypeUse(ProcessModel context,
			CPropertyInfo propertyInfo) {

		final CElementInfo elementInfo = ((CElementInfo) propertyInfo.ref()
				.iterator().next());

		final CBuiltinLeafInfo type = (CBuiltinLeafInfo) elementInfo
				.getContentType();

		return type;
	}

	@Override
	public CreatePropertyInfos getCreatePropertyInfos(ProcessModel context,
			CPropertyInfo propertyInfo) {

		final TypeUse adaptingTypeUse = context.getAdaptBuiltinTypeUse()
				.process(context, propertyInfo);

		return new AdaptBuiltinReference(adaptingTypeUse);
	}
	
	protected Collection<CPropertyInfo> wrapAnyType(ProcessModel context, CPropertyInfo propertyInfo) {
		return new AdaptWildcardReference(CBuiltinLeafInfo.STRING).process(context,
				propertyInfo);
	}
	
}
