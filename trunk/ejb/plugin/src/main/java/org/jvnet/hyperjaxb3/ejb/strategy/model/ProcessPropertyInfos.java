package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import com.sun.tools.xjc.model.CPropertyInfo;

public interface ProcessPropertyInfos extends
		EjbClassInfoProcessor<Collection<CPropertyInfo>>,
		EjbPropertyInfoProcessor<Collection<CPropertyInfo>> {

}
