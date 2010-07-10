package org.jvnet.hyperjaxb3.model;

import java.util.List;

public interface HClassInfo extends HCustomizable, HClassInfoContainer {

	public HModel getModel();

	public HClassInfoContainer getContainer();

	public List<HProperty> getProperties();

}
