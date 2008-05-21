package org.jvnet.hyperjaxb3.ejb.strategy.outline.base;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.customizations.ModelCustomizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.Annotate;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessClassOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessFieldOutlines;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.naming.Naming;
import org.jvnet.jaxb2_commons.util.OutlineUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class DefaultProcessOutline implements ProcessOutline {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<ClassOutline> process(ProcessOutline context,
			Outline outline, Options options) {
		logger.debug("Processing outline with context path ["
				+ OutlineUtils.getContextPath(outline) + "].");

		final Collection<? extends ClassOutline> classes = outline.getClasses();
		final Collection<ClassOutline> processedClassOutlines = new ArrayList<ClassOutline>(
				classes.size());

		for (final ClassOutline classOutline : classes) {
			if (!context.getIgnoring().isClassOutlineIgnored(context,
					classOutline, options)) {
				final ClassOutline processedClassOutline = getProcessClassOutline()
						.process(context, classOutline, options);
				if (processedClassOutline != null) {
					processedClassOutlines.add(processedClassOutline);
				}
			}
		}
		return processedClassOutlines;
	}

	private ProcessClassOutline processClassOutline;

	public ProcessClassOutline getProcessClassOutline() {
		return processClassOutline;
	}

	@Required
	public void setProcessClassOutline(ProcessClassOutline processClassOutline) {
		this.processClassOutline = processClassOutline;
	}

	private ProcessFieldOutline processFieldOutline;

	public ProcessFieldOutline getProcessFieldOutline() {
		return processFieldOutline;
	}

	@Required
	public void setProcessFieldOutline(ProcessFieldOutline processFieldOutline) {
		this.processFieldOutline = processFieldOutline;
	}

	private ProcessFieldOutlines processFieldOutlines;

	public ProcessFieldOutlines getProcessFieldOutlines() {
		return processFieldOutlines;
	}

	@Required
	public void setProcessFieldOutlines(
			ProcessFieldOutlines processFieldOutlines) {
		this.processFieldOutlines = processFieldOutlines;
	}

	private Ignoring ignoring;

	public Ignoring getIgnoring() {
		return ignoring;
	}

	@Required
	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	private Annotate annotate;

	public Annotate getAnnotate() {
		return annotate;
	}

	@Required
	public void setAnnotate(Annotate annotate) {
		this.annotate = annotate;
	}

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	@Required
	public void setNaming(Naming naming) {
		this.naming = naming;
	}
	
	private ModelCustomizations customizations;
	
	public ModelCustomizations getCustomizations() {
		return customizations;
	}
	
	public void setCustomizations(ModelCustomizations customizations) {
		this.customizations = customizations;
	}
}
