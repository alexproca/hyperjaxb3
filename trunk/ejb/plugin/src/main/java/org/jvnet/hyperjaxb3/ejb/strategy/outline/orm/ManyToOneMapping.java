package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class ManyToOneMapping extends AssociationMapping implements
		FieldOutlineMapping<ManyToOne> {
	
	public ManyToOne process(Mapping context, FieldOutline fieldOutline, Options options) {
		
		final ManyToOne manyToOne = context.getCustomizations().getManyToOne(fieldOutline);
		// TODO Auto-generated method stub
		return null;
	}

}
