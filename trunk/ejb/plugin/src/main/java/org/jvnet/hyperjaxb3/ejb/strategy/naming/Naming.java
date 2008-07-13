package org.jvnet.hyperjaxb3.ejb.strategy.naming;

import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface Naming {

	public String getEntityTableName(ClassOutline classOutline);

	public String getEntityTableName(FieldOutline fieldOutline);

	public String getOneToManyJoinTableName(FieldOutline fieldOutline);

	public String getColumnName(FieldOutline fieldOutline);

	public String getOneToManyJoinColumnName(FieldOutline fieldOutline);

	public String getOneToManyJoinTableJoinColumnName(FieldOutline fieldOutline);

	public String getOneToManyJoinTableInverseJoinColumnName(
			FieldOutline fieldOutline);

	public String getManyToOneJoinColumnName(FieldOutline fieldOutline);

	public String getManyToOneJoinTableName(FieldOutline fieldOutline);

	public String getManyToOneJoinTableJoinColumnName(FieldOutline fieldOutline);

	public String getManyToOneJoinTableInverseJoinColumnName(
			FieldOutline fieldOutline);
}
