package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.jaxb2_commons.strategy.OutlineProcessor;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class AnnotationsProcessor implements
		OutlineProcessor<Collection<ClassOutline>, EjbPlugin> {

	public Collection<ClassOutline> process(EjbPlugin context, Outline outline,
			Options options) throws Exception {

		getProcessModel().process(context, outline, options);

		return getAnnotateOutline().process(context, outline, options);
	}

	private OutlineProcessor<Collection<CClassInfo>, EjbPlugin> processModel;

	public OutlineProcessor<Collection<CClassInfo>, EjbPlugin> getProcessModel() {
		return processModel;
	}

	@Required
	public void setProcessModel(
			OutlineProcessor<Collection<CClassInfo>, EjbPlugin> outlineProcessor) {
		this.processModel = outlineProcessor;
	}

	private OutlineProcessor<Collection<ClassOutline>, EjbPlugin> annotateOutline;

	public OutlineProcessor<Collection<ClassOutline>, EjbPlugin> getAnnotateOutline() {
		return annotateOutline;
	}

	public void setAnnotateOutline(
			OutlineProcessor<Collection<ClassOutline>, EjbPlugin> annotateOutline) {
		this.annotateOutline = annotateOutline;
	}

}
