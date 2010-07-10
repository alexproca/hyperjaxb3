package org.jvnet.hyperjaxb3.ejb.strategy.annotate;

import org.jvnet.annox.model.XAnnotationVisitor;
import org.jvnet.annox.model.XAnnotationField.XString;
import org.jvnet.annox.model.XAnnotationField.XStringArray;
import org.jvnet.hyperjaxb3.xsd.util.StringUtils;

import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JCodeModel;

public class Annotator extends
		org.jvnet.jaxb2_commons.plugin.annotate.Annotator {

	@Override
	protected XAnnotationVisitor<JAnnotationUse> createAnnotationFieldVisitor(
			JCodeModel codeModel, JAnnotationUse annotationUse) {
		return new AnnotatingFieldVisitor(codeModel, annotationUse) {

			@Override
			public JAnnotationUse visitStringField(XString field) {
				return use.param(field.getName(), StringUtils
						.normalizeString(field.getValue()));
			}

			@Override
			public JAnnotationUse visitStringArrayField(XStringArray field) {
				final JAnnotationArrayMember array = use.paramArray(field
						.getName());
				for (final String value : field.getValue()) {
					array.param(StringUtils.normalizeString(value));
				}
				return use;
			}

		};
	}
}
