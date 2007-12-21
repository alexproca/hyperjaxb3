package org.jvnet.hyperjaxb3.ejb.strategy.model;

import java.util.Collection;

import com.sun.tools.xjc.model.CPropertyInfo;

public interface CreateDefaultIdPropertyInfos extends
		EjbClassInfoProcessor<Collection<CPropertyInfo>> {

	public boolean isTransient();

	public void setTransient(boolean transientField);

}
