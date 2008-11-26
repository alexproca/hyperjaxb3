package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class IdMapping extends PropertyMapping implements
		FieldOutlineMapping<Id> {

	public Id process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Id id = context.getCustomizing().getId(fieldOutline);

		createId$Name(context, fieldOutline, id);
		createId$Column(context, fieldOutline, id);
		createId$Temporal(context, fieldOutline, id);
		return id;
	}

	public void createId$Name(Mapping context, FieldOutline fieldOutline,
			final Id id) {
		id.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createId$Column(Mapping context, FieldOutline fieldOutline,
			final Id id) {
		if (id.getColumn() == null) {
			id.setColumn(new Column());
		}

		id.setColumn(createColumn(context, fieldOutline, id.getColumn()));
	}

	public void createId$Temporal(Mapping context, FieldOutline fieldOutline,
			Id id) {
		if (id.getTemporal() == null && isTemporal(fieldOutline)) {
			id.setTemporal(getTemporalType(fieldOutline));
		}
	}

}
