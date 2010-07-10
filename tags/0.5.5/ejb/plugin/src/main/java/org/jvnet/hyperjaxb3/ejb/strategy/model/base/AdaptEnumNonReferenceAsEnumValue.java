package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleEnumValueWrappingField;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.outline.FieldOutline;

public class AdaptEnumNonReferenceAsEnumValue extends
		AbstractAdaptEnumPropertyInfo {

	public AdaptEnumNonReferenceAsEnumValue() {
		super();
	}

	@Override
	public TypeUse getPropertyType(ProcessModel context,
			CPropertyInfo propertyInfo) {
		return ((CEnumLeafInfo) propertyInfo.ref().iterator().next()).base;
	}

	@Override
	protected FieldOutline generateField(CPropertyInfo core,
			ClassOutlineImpl classOutline, CPropertyInfo propertyInfo) {
		final SingleEnumValueWrappingField fieldOutline = new SingleEnumValueWrappingField(
				classOutline, propertyInfo, core);
		fieldOutline.generateAccessors();
		return fieldOutline;

	}

}
