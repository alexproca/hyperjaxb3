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

public class SingleMarshallingField extends AbstractWrappingField {

	private final String contextPath;

	public SingleMarshallingField(ClassOutlineImpl context, CPropertyInfo prop,
			CPropertyInfo core, String contextPath) {
		super(context, prop, core);
		this.contextPath = contextPath;
	}

	@Override
	public JExpression wrapCondifiton(JExpression source) {
		return source.ne(JExpr._null());
	}

	@Override
	public JExpression unwrapCondifiton(JExpression source) {

		final JExpression isElement = codeModel.ref(JAXBContextUtils.class)
				.staticInvoke("isMarshallable").arg(contextPath).arg(source);
		return isElement;
	}

	protected JExpression wrap(final JExpression target) {

		return codeModel.ref(JAXBContextUtils.class).staticInvoke("unmarshal")
				.arg(contextPath).arg(target);
	}

	@Override
	protected JExpression unwrap(JExpression source) {
		return codeModel.ref(JAXBContextUtils.class).staticInvoke("marshal")
				.arg(contextPath).arg(source);
	}
}
