package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.SingleMarshallingField;

import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.outline.FieldOutline;

public class AdaptWildcardNonReference extends AbstractAdaptBuiltinPropertyInfo {

	
	public AdaptWildcardNonReference(TypeUse propertyType) {
		super(propertyType);
	}
	
	@Override
	public String getPropertyName(ProcessModel context,
			CPropertyInfo propertyInfo) {
		// TODO Allow for customization
		return propertyInfo.getName(true) + "Object";
	}

	@Override
	protected FieldOutline generateField(CPropertyInfo core,
			ClassOutlineImpl classOutline, CPropertyInfo propertyInfo) {
		final SingleMarshallingField fieldOutline = new SingleMarshallingField(classOutline, propertyInfo, core);
		fieldOutline.generateAccessors();
		return fieldOutline;
	}
}
