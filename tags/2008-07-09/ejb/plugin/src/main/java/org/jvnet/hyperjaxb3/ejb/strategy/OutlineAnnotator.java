package org.jvnet.hyperjaxb3.ejb.strategy;

import java.util.Collection;

import org.jvnet.jaxb2_commons.strategy.ClassOutlineProcessor;
import org.jvnet.jaxb2_commons.strategy.FieldOutlineProcessor;
import org.jvnet.jaxb2_commons.strategy.OutlineProcessor;

import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

/**
 * Outline annotator. Annotates classes and returns the collection of the
 * annotated classes.
 */
public interface OutlineAnnotator extends
		OutlineProcessor<Collection<ClassOutline>, OutlineAnnotator> {

	public ClassOutlineProcessor<Boolean, OutlineAnnotator> getClassAnnotator();

	public ClassOutlineProcessor getFieldsProcessor();

	public FieldOutlineProcessor getFieldProcessor();

	public ClassOutlineProcessor getEntityAnnotator();

	public ClassOutlineProcessor<FieldOutline, OutlineAnnotator> getIdFieldOutlineCreator();

	public FieldOutlineProcessor getIdFieldAnnotator();

	public FieldOutlineProcessor getSingleFieldProcessor();

	public FieldOutlineProcessor getHomoSingleFieldProcessor();

	public FieldOutlineProcessor getSimpleSingleFieldProcessor();

	public FieldOutlineProcessor getComplexSingleFieldProcessor();

	public FieldOutlineProcessor getCollectionFieldProcessor();

	public FieldOutlineProcessor getHomoCollectionFieldProcessor();

	public FieldOutlineProcessor getComplexCollectionFieldProcessor();

	public FieldOutlineProcessor<Collection<? extends CTypeInfo>, OutlineAnnotator> getTypeStrategy();

	public FieldOutlineProcessor getBasicAnnotator();

	public FieldOutlineProcessor getTransientAnnotator();

	public FieldOutlineProcessor getManyToOneAnnotator();

	public FieldOutlineProcessor getOneToManyAnnotator();
}
