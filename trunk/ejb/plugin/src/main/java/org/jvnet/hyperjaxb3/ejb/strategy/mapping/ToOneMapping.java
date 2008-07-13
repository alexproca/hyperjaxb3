package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class ToOneMapping implements FieldOutlineMapping<Object> {

	public Object process(Mapping context, FieldOutline fieldOutline,
			Options options) throws Exception {

		final Object toOne = context.getCustomizing().getToOne(fieldOutline);

		if (toOne instanceof ManyToOne) {
			return context.getManyToOneMapping().process(context, fieldOutline,
					options);
		} else if (toOne instanceof OneToOne) {
			return context.getOneToOneMapping().process(context, fieldOutline,
					options);
		} else {
			throw new AssertionError(
					"Either many-to-one or one-to-one mappings are expected.");
		}
	}

}
