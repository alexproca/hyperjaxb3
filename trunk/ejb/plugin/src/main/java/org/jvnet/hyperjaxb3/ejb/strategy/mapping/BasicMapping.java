package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import javax.persistence.EnumType;

import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class BasicMapping extends PropertyMapping implements
		FieldOutlineMapping<Basic> {

	public Basic process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Basic basic = context.getCustomizing().getBasic(fieldOutline);

		createBasic$Name(context, fieldOutline, basic);

		createBasic$Column(context, fieldOutline, basic);

		if (basic.getLob() == null && basic.getTemporal() == null
				&& basic.getEnumerated() == null) {
			if (isTemporal(fieldOutline)) {
				basic.setTemporal(getTemporalType(fieldOutline));
			} else if (isEnumerated(fieldOutline)) {
				basic.setEnumerated(getEnumerated(fieldOutline));
			} else if (isLob(fieldOutline)) {
				basic.setLob(getLob(fieldOutline));
			}

		}

		return basic;
	}

	public void createBasic$Name(Mapping context, FieldOutline fieldOutline,
			final Basic basic) {
		basic.setName(fieldOutline.getPropertyInfo().getName(true));
	}

	public void createBasic$Column(Mapping context, FieldOutline fieldOutline,
			final Basic basic) {
		if (basic.getColumn() == null) {
			basic.setColumn(new Column());
		}

		basic.setColumn(createColumn(context, fieldOutline, basic.getColumn()));
	}

	public boolean isLob(FieldOutline fieldOutline) {
		return false;
	}

	public Lob getLob(FieldOutline fieldOutline) {
		return new Lob();
	}

	public boolean isEnumerated(FieldOutline fieldOutline) {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return type instanceof CEnumLeafInfo;
	}

	public String getEnumerated(FieldOutline fieldOutline) {
		return EnumType.STRING.name();
	}

}
