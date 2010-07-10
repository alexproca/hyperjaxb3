package org.jvnet.hyperjaxb3.model;

import java.util.List;

public interface HPackage extends HClassInfoContainer {

	public HSchema getSchema();

	public HAccessorType getAccessorType();

	public HAccessorOrder getAccessorOrder();

	public HSchemaTypes getSchemaTypes();

	public HJavaTypeAdapters getJavaTypeAdapters();

	public List<HClass> getClasses();

	public List<HEnumClass> getEnumClasses();

}
