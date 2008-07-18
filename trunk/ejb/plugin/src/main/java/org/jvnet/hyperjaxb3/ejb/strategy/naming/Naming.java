package org.jvnet.hyperjaxb3.ejb.strategy.naming;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface Naming {

	public String getEntityTable$Name(ClassOutline classOutline);

	public String getEntityTable$Name(FieldOutline fieldOutline);

	public String getColumn$Name(FieldOutline fieldOutline);

	public String getJoinTable$Name(FieldOutline fieldOutline);

	public String getJoinColumn$Name(FieldOutline fieldOutline);

	public String getJoinTable$JoinColumn$Name(FieldOutline fieldOutline);

	public String getJoinTable$InverseJoinColumn$Name(FieldOutline fieldOutline);

}
