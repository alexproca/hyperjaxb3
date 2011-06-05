package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class EmbeddedIdMapping extends PropertyMapping implements
		FieldOutlineMapping<EmbeddedId> {

	public EmbeddedId process(Mapping context, FieldOutline fieldOutline,
			Options options) throws Exception {

		final EmbeddedId embeddedId = context.getCustomizing().getEmbeddedId(
				fieldOutline);

		createEmbeddedId$Name(context, fieldOutline, embeddedId);
		createAttributeOverride(context, fieldOutline,
				embeddedId.getAttributeOverride());

		return embeddedId;
	}

	public void createEmbeddedId$Name(Mapping context,
			FieldOutline fieldOutline, final EmbeddedId embeddedId) {
		embeddedId.setName(context.getNaming().getPropertyName(context, fieldOutline));
	}
}
