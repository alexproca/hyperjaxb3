package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.strategy.model.GetTypes;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;

import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;

public class DefaultGetTypes implements GetTypes {

	public Collection<? extends CTypeInfo> process(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return propertyInfo.ref();
	}

}
