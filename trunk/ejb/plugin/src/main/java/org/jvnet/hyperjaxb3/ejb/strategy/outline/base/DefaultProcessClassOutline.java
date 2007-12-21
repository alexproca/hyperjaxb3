package org.jvnet.hyperjaxb3.ejb.strategy.outline.base;

import java.util.Collection;

import javax.persistence.Transient;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessClassOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.plugin.annotate.Annotator;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class DefaultProcessClassOutline implements ProcessClassOutline {

	protected Log logger = LogFactory.getLog(getClass());

	public ClassOutline process(ProcessOutline context,
			ClassOutline classOutline, Options options) {
		logger.debug("Processing class outline ["
				+ OutlineUtils.getClassName(classOutline) + "].");

		context.getProcessFieldOutlines().process(context, classOutline,
				options);

		final Collection<XAnnotation> xannotations = context.getAnnotate()
				.getAnnotateClassOutlineEntity().process(context, classOutline,
						options);

		logger.debug("Annotating the class ["
				+ OutlineUtils.getClassName(classOutline) + "]:\n"
				+ ArrayUtils.toString(xannotations));

		getAnnotator().annotate(classOutline.ref.owner(), classOutline.ref,
				xannotations);

		if (classOutline.target.declaresAttributeWildcard()) {
			logger
					.debug("The class ["
							+ OutlineUtils.getClassName(classOutline)
							+ "] declares an attribute wildcard which will be made transient.");
			String FIELD_NAME = "otherAttributes";
			String METHOD_SEED = classOutline.parent().getModel()
					.getNameConverter().toClassName(FIELD_NAME);

			final JMethod getOtherAttributesMethod = classOutline.ref
					.getMethod("get" + METHOD_SEED, new JType[0]);

			if (getOtherAttributesMethod == null) {
				logger
						.error("Could not find the attribute wildcard method in the class ["
								+ OutlineUtils.getClassName(classOutline)
								+ "].");
			} else {
				getOtherAttributesMethod.annotate(Transient.class);
			}
		}
		return classOutline;
	}

	private Annotator annotator = Annotator.INSTANCE;

	public Annotator getAnnotator() {
		return annotator;
	}

	public void setAnnotator(Annotator annotator) {
		this.annotator = annotator;
	}

}
