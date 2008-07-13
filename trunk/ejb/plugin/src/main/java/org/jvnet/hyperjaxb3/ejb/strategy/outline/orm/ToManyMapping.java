package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class ToManyMapping implements FieldOutlineMapping<Object> {

	public Object process(Mapping context, FieldOutline fieldOutline,
			Options options) throws Exception {

		final Object ToMany = context.getCustomizations().getToMany(
				fieldOutline);

		if (ToMany instanceof ManyToMany) {
			return context.getManyToManyMapping().process(context,
					fieldOutline, options);
		} else if (ToMany instanceof OneToMany) {
			return context.getOneToManyMapping().process(context, fieldOutline,
					options);
		} else {
			throw new AssertionError(
					"Either one-to-many or many-to-many mappings are expected.");
		}
	}

}
