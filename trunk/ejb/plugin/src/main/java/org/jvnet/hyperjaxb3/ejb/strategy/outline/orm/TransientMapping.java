package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class TransientMapping implements FieldOutlineMapping<Transient> {

	@Override
	public Transient process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		// TODO
		final Transient _transient = new Transient();
		// context.getCustomizations().getTransient(fieldOutline);

		createTransient(context, fieldOutline, _transient);
		return _transient;
	}

	public void createTransient(Mapping context, FieldOutline fieldOutline,
			Transient _transient) {
		createTransient$Name(context, fieldOutline, _transient);
	}

	public void createTransient$Name(Mapping context,
			FieldOutline fieldOutline, final Transient _transient) {
		_transient.setName(fieldOutline.getPropertyInfo().getName(true));
	}

}
