package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleWrappingReferenceField;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.outline.FieldOutline;

public class AdaptBuiltinReference extends AbstractAdaptBuiltinPropertyInfo {

	public AdaptBuiltinReference(TypeUse type,
			Class<? extends XmlAdapter> adapterClass) {
		super(type, adapterClass);
	}

	public AdaptBuiltinReference(TypeUse propertyType) {
		super(propertyType);
	}

	protected FieldOutline generateField(final CPropertyInfo core,
			ClassOutlineImpl classOutline, CPropertyInfo propertyInfo) {
		assert core instanceof CReferencePropertyInfo;
		SingleWrappingReferenceField fieldOutline = new SingleWrappingReferenceField(
				classOutline, propertyInfo, (CReferencePropertyInfo) core);
		fieldOutline.generateAccessors();
		return fieldOutline;
	}

}
