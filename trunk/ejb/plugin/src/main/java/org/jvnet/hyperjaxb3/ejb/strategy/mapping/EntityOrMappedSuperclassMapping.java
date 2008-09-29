package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class EntityOrMappedSuperclassMapping implements
		ClassOutlineMapping<Object> {

	@Override
	public Object process(Mapping context, ClassOutline classOutline,
			Options options) throws Exception {
		final Object EntityOrMappedSuperclass = context.getCustomizing()
				.getEntityOrMappedSuperclass(classOutline);

		if (EntityOrMappedSuperclass instanceof Entity) {
			return context.getEntityMapping().process(context, classOutline,
					options);
		} else if (EntityOrMappedSuperclass instanceof MappedSuperclass) {
			return context.getMappedSuperclassMapping().process(context,
					classOutline, options);
		} else {
			throw new AssertionError(
					"Either one-to-many or many-to-many mappings are expected.");
		}
	}

}
