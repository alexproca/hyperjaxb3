package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.List;

import com.sun.java.xml.ns.persistence.orm.AttributeOverride;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.outline.FieldOutline;

public interface AttributeMapping {

	public Column createColumn(Mapping context, FieldOutline fieldOutline,
			Column column);

	public boolean isTemporal(FieldOutline fieldOutline);

	public String createTemporalType(FieldOutline fieldOutline);

	public boolean isLob(FieldOutline fieldOutline);

	public Lob createLob(FieldOutline fieldOutline);

	public boolean isEnumerated(FieldOutline fieldOutline);

	public String createEnumerated(FieldOutline fieldOutline);

	public void createAttributeOverride(Mapping context,
			FieldOutline fieldOutline,
			final List<AttributeOverride> attributeOverrides);

}