package org.jvnet.hyperjaxb3.xjc.generator.bean.field;

import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CElement;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CReferencePropertyInfo;

public class SingleMarshallingField extends WrappingField {

	public SingleMarshallingField(ClassOutlineImpl context, CPropertyInfo prop,
			CPropertyInfo core) {
		super(context, prop, core);
	}

	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return source.ne(JExpr._null());
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {

		final String contextPath = OutlineUtils
				.getContextPath(outline.parent());

		final JExpression isElement = codeModel.ref(JAXBContextUtils.class)
				.staticInvoke("isMarshallable").arg(contextPath).arg(source);
		return isElement;
	}

	protected JExpression wrap(final JExpression target) {

		final String contextPath = OutlineUtils
				.getContextPath(outline.parent());

		return codeModel.ref(JAXBContextUtils.class).staticInvoke("marshall")
				.arg(contextPath).arg(target);
	}

	@Override
	protected JExpression unwrap(JExpression source) {
		final String contextPath = OutlineUtils
				.getContextPath(outline.parent());

		return codeModel.ref(JAXBContextUtils.class).staticInvoke("unmarshall")
				.arg(contextPath).arg(source);
	}
}
