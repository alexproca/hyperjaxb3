package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import org.jvnet.hyperjaxb3.ejb.strategy.outline.Annotate;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateClassOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.springframework.beans.factory.annotation.Required;

public class DefaultAnnotate implements Annotate {

	private AnnotateClassOutline annotateClassOutlineEntity;

	public AnnotateClassOutline getAnnotateClassOutlineEntity() {
		return annotateClassOutlineEntity;
	}

	@Required
	public void setAnnotateClassOutlineEntity(
			AnnotateClassOutline annotateClassOutline) {
		this.annotateClassOutlineEntity = annotateClassOutline;
	}
	
	private AnnotateClassOutline annotateClassOutlineTable;

	public AnnotateClassOutline getAnnotateClassOutlineTable() {
		return annotateClassOutlineTable;
	}

	@Required
	public void setAnnotateClassOutlineTable(
			AnnotateClassOutline annotateClassOutline) {
		this.annotateClassOutlineTable = annotateClassOutline;
	}
	
	
	private AnnotateFieldOutline annotateFieldOutlineTransient;

	public AnnotateFieldOutline getAnnotateFieldOutlineTransient() {
		return annotateFieldOutlineTransient;
	}

	@Required
	public void setAnnotateFieldOutlineTransient(
			AnnotateFieldOutline annotateFieldOutlineTransient) {
		this.annotateFieldOutlineTransient = annotateFieldOutlineTransient;
	}
	
	
	public AnnotateFieldOutline annotateFieldOutlineColumn;

	public AnnotateFieldOutline getAnnotateFieldOutlineColumn() {
		return annotateFieldOutlineColumn;
	}

	@Required
	public void setAnnotateFieldOutlineColumn(
			AnnotateFieldOutline annotateFieldOutlineColumn) {
		this.annotateFieldOutlineColumn = annotateFieldOutlineColumn;
	}
	

	public AnnotateFieldOutline annotateFieldOutlineId;

	public AnnotateFieldOutline getAnnotateFieldOutlineId() {
		return annotateFieldOutlineId;
	}

	@Required
	public void setAnnotateFieldOutlineId(
			AnnotateFieldOutline annotateFieldOutlineId) {
		this.annotateFieldOutlineId = annotateFieldOutlineId;
	}

	public AnnotateFieldOutline annotateFieldOutlineVersion;

	public AnnotateFieldOutline getAnnotateFieldOutlineVersion() {
		return annotateFieldOutlineVersion;
	}

	@Required
	public void setAnnotateFieldOutlineVersion(
			AnnotateFieldOutline annotateFieldOutlineVersion) {
		this.annotateFieldOutlineVersion = annotateFieldOutlineVersion;
	}
	

	private AnnotateFieldOutline annotateFieldOutlineBasic;

	public AnnotateFieldOutline getAnnotateFieldOutlineBasic() {
		return annotateFieldOutlineBasic;
	}

	@Required
	public void setAnnotateFieldOutlineBasic(
			AnnotateFieldOutline annotateFieldOutlineBasic) {
		this.annotateFieldOutlineBasic = annotateFieldOutlineBasic;
	}

	private AnnotateFieldOutline annotateFieldOutlineTemporal;

	public AnnotateFieldOutline getAnnotateFieldOutlineTemporal() {
		return annotateFieldOutlineTemporal;
	}

	@Required
	public void setAnnotateFieldOutlineTemporal(
			AnnotateFieldOutline annotateFieldOutlineTemporal) {
		this.annotateFieldOutlineTemporal = annotateFieldOutlineTemporal;
	}

	private AnnotateFieldOutline annotateFieldOutlineEnumerated;

	public AnnotateFieldOutline getAnnotateFieldOutlineEnumerated() {
		return annotateFieldOutlineEnumerated;
	}

	@Required
	public void setAnnotateFieldOutlineEnumerated(
			AnnotateFieldOutline annotateFieldOutlineEnumerated) {
		this.annotateFieldOutlineEnumerated = annotateFieldOutlineEnumerated;
	}

	private AnnotateFieldOutline annotateFieldOutlineManyToOne;

	public AnnotateFieldOutline getAnnotateFieldOutlineManyToOne() {
		return annotateFieldOutlineManyToOne;
	}

	@Required
	public void setAnnotateFieldOutlineManyToOne(
			AnnotateFieldOutline annotateFieldOutlineManyToOne) {
		this.annotateFieldOutlineManyToOne = annotateFieldOutlineManyToOne;
	}

	private AnnotateFieldOutline annotateFieldOutlineOneToMany;

	public AnnotateFieldOutline getAnnotateFieldOutlineOneToMany() {
		return annotateFieldOutlineOneToMany;
	}

	@Required
	public void setAnnotateFieldOutlineOneToMany(
			AnnotateFieldOutline annotateFieldOutlineOneToMany) {
		this.annotateFieldOutlineOneToMany = annotateFieldOutlineOneToMany;
	}
	
}
