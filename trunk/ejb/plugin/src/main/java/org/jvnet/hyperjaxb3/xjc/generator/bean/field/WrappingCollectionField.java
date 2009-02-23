package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import javax.xml.bind.annotation.XmlTransient;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CPropertyInfo;

public class WrappingCollectionField extends AbstractWrapCollectionField {

	public WrappingCollectionField(ClassOutlineImpl context,
			CPropertyInfo wrappedProperty, CPropertyInfo wrappingProperty) {
		super(context, wrappingProperty, wrappedProperty, wrappingProperty);
	}

	protected JFieldRef createField() {

		final JFieldVar field = outline.implClass.field(JMod.PROTECTED
				+ JMod.TRANSIENT,

		propertyListType, property.getName(false));
//		field.annotate(XmlTransient.class);
		return JExpr._this().ref(field);
	}

}
