package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EnumType;
import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.xjc.model.CExternalLeafInfo;
import org.jvnet.hyperjaxb3.xsd.util.XMLSchemaConstrants;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeAnalyzer;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeVisitor;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Lob;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.xml.xsom.XSComponent;

public class BasicMapping extends PropertyMapping implements
		FieldOutlineMapping<Basic> {

	public Basic process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Basic basic = context.getCustomizations().getBasic(fieldOutline);

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

		// TODO Auto-generated method stub
		return null;
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

		return type instanceof CExternalLeafInfo;
	}

	public String getEnumerated(FieldOutline fieldOutline) {
		return EnumType.STRING.name();
	}

}
