package org.jvnet.hyperjaxb3.xjc.model;

import com.sun.tools.xjc.model.CAttributePropertyInfo;
import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.CValuePropertyInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.model.TypeUseFactory;

public class TypeUseUtils {

	public static TypeUse getTypeUse(CPropertyInfo propertyInfo) {
		if (propertyInfo instanceof CValuePropertyInfo) {
			return ((CValuePropertyInfo) propertyInfo).getTarget();
		} else if (propertyInfo instanceof CAttributePropertyInfo) {
			return ((CAttributePropertyInfo) propertyInfo).getTarget();
		} else {
			final CTypeInfo type = propertyInfo.ref().iterator().next();
			if (type instanceof CBuiltinLeafInfo) {
				if (propertyInfo.getAdapter() != null) {
					return TypeUseFactory.adapt((CBuiltinLeafInfo) type,
							propertyInfo.getAdapter());
				} else {
					return (CBuiltinLeafInfo) type;
				}
			} else if (type instanceof CElementInfo) {
				final CElementInfo elementInfo = (CElementInfo) type;
				return getTypeUse(elementInfo.getProperty());
			} else {
				throw new AssertionError("Unexpected type.");
			}
		}

	}

}
