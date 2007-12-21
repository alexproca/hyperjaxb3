package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateClassOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class DefaultAnnotateClassOutlineEntity implements AnnotateClassOutline {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		final XAnnotation entity = createEntity(outlineProcessor, classOutline,
				options);

		xannotations.add(entity);

		xannotations.addAll(

		outlineProcessor.getAnnotate().getAnnotateClassOutlineTable().process(
				outlineProcessor, classOutline, options));

		if (classOutline.getSuperClass() == null) {
			final XAnnotation inheritance = createInheritance(outlineProcessor,
					classOutline, options);
			xannotations.add(inheritance);
		}
		return xannotations;
	}

	public XAnnotation createEntity(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options) {
		final XAnnotation entity = new XAnnotation(Entity.class, createName(
				outlineProcessor, classOutline, options));
		return entity;
	}

	public XAnnotation createInheritance(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options) {
		final XAnnotation entity = new XAnnotation(Inheritance.class,
				createInheritanceStrategy(outlineProcessor, classOutline,
						options));
		return entity;
	}

	public XAnnotationField.XString createName(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options) {
		final String name = getEntityName(outlineProcessor, classOutline,
				options);
		if (name == null) {
			return null;
		} else {
			return new XAnnotationField.XString("name", name);
		}
	}

	/**
	 * Returns name of the entity. <code>null</code> for default.
	 * 
	 * @return Name of the entity.
	 */
	public String getEntityName(ProcessOutline outlineProcessor,
			ClassOutline classOutline, Options options) {
		return OutlineUtils.getClassName(classOutline);
	}

	public XAnnotationField.XEnum createInheritanceStrategy(
			ProcessOutline outlineProcessor, ClassOutline classOutline,
			Options options) {
		final InheritanceType inheritanceStrategy = getInheritanceStrategy(
				outlineProcessor, classOutline, options);
		if (inheritanceStrategy == null) {
			return null;
		} else {
			return new XAnnotationField.XEnum("strategy", inheritanceStrategy,
					InheritanceType.class);
		}
	}

	public InheritanceType getInheritanceStrategy(
			ProcessOutline outlineProcessor, ClassOutline classOutline,
			Options options) {
		return getDefaultInheritanceStrategy();
	}

	private InheritanceType defaultInheritanceStrategy = InheritanceType.JOINED;

	public InheritanceType getDefaultInheritanceStrategy() {
		return defaultInheritanceStrategy;
	}

	public void setDefaultInheritanceStrategy(
			InheritanceType defaultInheritanceStrategy) {
		this.defaultInheritanceStrategy = defaultInheritanceStrategy;
	}

}
