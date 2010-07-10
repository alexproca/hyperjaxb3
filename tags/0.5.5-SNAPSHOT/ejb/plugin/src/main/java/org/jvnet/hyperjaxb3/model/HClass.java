package org.jvnet.hyperjaxb3.model;

import java.util.List;

public interface HClass {

	public HClass getSuperClass();

	public HPackage getPackage();

	public HClass getEnclosingClass();

	public String getSimpleName();

	public String getFullName();

	public String getLocalName();

	public String getFullCanonicalName();

	public String getLocalCanonicalName();

	public List<HProperty> getProperties();

	public HType getType();

	public HRootElement getRootElement();

	public HTransient getTransient();

}
