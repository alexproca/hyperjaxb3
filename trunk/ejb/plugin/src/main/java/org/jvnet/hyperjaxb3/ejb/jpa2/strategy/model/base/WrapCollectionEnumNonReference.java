package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.model.base;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.base.CreateNoPropertyInfos;

import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;

public class WrapCollectionEnumNonReference implements CreatePropertyInfos {

	public Collection<CPropertyInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {

		// Single
		assert propertyInfo.isCollection();
		// Builtin
		assert propertyInfo.ref().size() == 1;
		assert propertyInfo.ref().iterator().next() instanceof CEnumLeafInfo;

		return getCreatePropertyInfos(context, propertyInfo).process(context,
				propertyInfo);
	}

	public CreatePropertyInfos getCreatePropertyInfos(ProcessModel context,
			CPropertyInfo propertyInfo) {
		// final Basic basic = context.getCustomizing().getBasic(propertyInfo);
		//
		// if (basic != null && basic.getEnumeratedValue() != null) {
		// return new AdaptEnumNonReferenceAsEnumValue();
		// } else {
		return CreateNoPropertyInfos.INSTANCE;
		// }
	}

}
