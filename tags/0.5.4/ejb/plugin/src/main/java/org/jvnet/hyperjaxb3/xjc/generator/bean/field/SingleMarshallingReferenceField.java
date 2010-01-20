package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.jvnet.hyperjaxb3.codemodel.util.JExprUtils;
import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CNonElement;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;
import com.sun.tools.xjc.outline.Aspect;

public class SingleMarshallingReferenceField extends AbstractWrappingField {

	public SingleMarshallingReferenceField(ClassOutlineImpl context,
			CPropertyInfo prop, CPropertyInfo core) {
		super(context, prop, core);
	}

	@Override
	protected JExpression wrap(JExpression target) {

		final CReferencePropertyInfo referencePropertyInfo = (CReferencePropertyInfo) core;

		final Collection<CElement> elements = referencePropertyInfo
				.getElements();

		final CElement element = elements.iterator().next();

		final CElementInfo elementInfo = (CElementInfo) element.getType();

		final CNonElement type = elementInfo.getProperty().ref().iterator()
				.next();

		final JClass declaredType = (JClass) type.toType(
				outline.parent(), Aspect.EXPOSED);

		final JClass scope = getScope(elementInfo.getScope());

		final QName name = elementInfo.getElementName();

		final String contextPath = OutlineUtils
				.getContextPath(outline.parent());

		return codeModel.ref(JAXBContextUtils.class).staticInvoke(
				"marshallJAXBElement").arg(contextPath).arg(
				JExprUtils.newQName(codeModel, name)).arg(scope.dotclass())
				.arg(target);
	}

	@Override
	protected JExpression unwrap(JExpression source) {

		final String contextPath = OutlineUtils
				.getContextPath(outline.parent());

		return codeModel.ref(JAXBContextUtils.class).staticInvoke(
				"unmarshallJAXBElement").arg(contextPath).arg(source);
	}

}
