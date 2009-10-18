package org.jvnet.hyperjaxb3.ejb.strategy.naming;

import org.jvnet.hyperjaxb3.ejb.strategy.mapping.Mapping;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface Naming {

	public String getEntityTable$Name(Mapping context, ClassOutline classOutline);

	public String getEntityTable$Name(Mapping context, FieldOutline fieldOutline);

	public String getColumn$Name(Mapping context, FieldOutline fieldOutline);

	public String getJoinTable$Name(Mapping context, FieldOutline fieldOutline);

	public String getJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline);

	public String getJoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline);

	public String getJoinTable$InverseJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline);

	public String getEmbedded$Column$Name(Mapping context, FieldOutline parent,
			FieldOutline child);

}
