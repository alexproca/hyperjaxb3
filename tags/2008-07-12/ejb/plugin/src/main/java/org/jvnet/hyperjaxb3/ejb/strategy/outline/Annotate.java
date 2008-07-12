package org.jvnet.hyperjaxb3.ejb.strategy.outline;

public interface Annotate {

	public AnnotateClassOutline getAnnotateClassOutlineEntity();

	public AnnotateClassOutline getAnnotateClassOutlineTable();

	public AnnotateFieldOutline getAnnotateFieldOutlineColumn();
	
	public AnnotateFieldOutline getAnnotateFieldOutlineId();

	public AnnotateFieldOutline getAnnotateFieldOutlineVersion();

	public AnnotateFieldOutline getAnnotateFieldOutlineBasic();

	public AnnotateFieldOutline getAnnotateFieldOutlineTransient();

	public AnnotateFieldOutline getAnnotateFieldOutlineTemporal();

	public AnnotateFieldOutline getAnnotateFieldOutlineEnumerated();

	public AnnotateFieldOutline getAnnotateFieldOutlineManyToOne();

	public AnnotateFieldOutline getAnnotateFieldOutlineOneToMany();

}
