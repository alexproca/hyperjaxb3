package org.jvnet.hyperjaxb3.model;

import java.util.List;

public interface HEnumClass {

	public HType getType();

	public HEnum getEnum();

	public HRootElement getRootElement();

	public List<HEnumMember> getEnumMembers();

}
