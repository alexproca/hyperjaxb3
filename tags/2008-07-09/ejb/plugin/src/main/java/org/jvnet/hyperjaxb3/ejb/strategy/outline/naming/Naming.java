package org.jvnet.hyperjaxb3.ejb.strategy.outline.naming;

import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public interface Naming {

	public String getEntityTableName(ProcessOutline context,
			ClassOutline classOutline, Options options);
	
	public String getEntityTableName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);

	public String getOneToManyJoinTableName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);

	public String getColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);

	public String getOneToManyJoinColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);
	
	public String getOneToManyJoinTableJoinColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);

	public String getOneToManyJoinTableInverseJoinColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);
	
	public String getManyToOneJoinColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);
	
	public String getManyToOneJoinTableName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);
	
	public String getManyToOneJoinTableJoinColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);

	public String getManyToOneJoinTableInverseJoinColumnName(ProcessOutline context,
			FieldOutline fieldOutline, Options options);
}
