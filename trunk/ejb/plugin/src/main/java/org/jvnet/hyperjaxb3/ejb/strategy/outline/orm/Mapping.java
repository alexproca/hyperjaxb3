package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import java.rmi.UnexpectedException;

import org.jvnet.hyperjaxb3.ejb.strategy.customizations.ModelCustomizations;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;

public abstract class Mapping {

	private ClassOutlineMapping<Entity> entityMapping = new EntityMapping();

	public ClassOutlineMapping<Entity> getEntityMapping() {
		return entityMapping;
	}

	public void setEntityMapping(ClassOutlineMapping<Entity> entityMapping) {
		this.entityMapping = entityMapping;
	}

	private ClassOutlineMapping<Attributes> attributesMapping = new AttributesMapping();

	public ClassOutlineMapping<Attributes> getAttributesMapping() {
		return attributesMapping;
	}

	public void setAttributesMapping(
			ClassOutlineMapping<Attributes> attributesMapping) {
		this.attributesMapping = attributesMapping;
	}

	private FieldOutlineMapping<Id> idMapping = new IdMapping();

	public FieldOutlineMapping<Id> getIdMapping() {
		return idMapping;
	}

	public void setIdMapping(FieldOutlineMapping<Id> idMapping) {
		this.idMapping = idMapping;
	}

	private FieldOutlineMapping<Basic> basicMapping;

	public FieldOutlineMapping<Basic> getBasicMapping() {
		return basicMapping;
	}

	public void setBasicMapping(FieldOutlineMapping<Basic> basicMapping) {
		this.basicMapping = basicMapping;
	}

	private FieldOutlineMapping<Version> versionMapping = new VersionMapping();

	public FieldOutlineMapping<Version> getVersionMapping() {
		return versionMapping;
	}

	public void setVersionMapping(FieldOutlineMapping<Version> versionMapping) {
		this.versionMapping = versionMapping;
	}

	private FieldOutlineMapping<EmbeddedId> embeddedIdMaping;// = new
																// EmbeddedIdMapping();

	public FieldOutlineMapping<EmbeddedId> getEmbeddedIdMapping() {
		throw new UnsupportedOperationException();
//		return embeddedIdMaping;
	}

	public void setEmbeddedIdMaping(
			FieldOutlineMapping<EmbeddedId> embeddedIdMaping) {
		this.embeddedIdMaping = embeddedIdMaping;
	}

	private FieldOutlineMapping<Embedded> embeddedMapping;

	public FieldOutlineMapping<Embedded> getEmbeddedMapping() {
		throw new UnsupportedOperationException();
//		return embeddedMapping;
	}

	public void setEmbeddedMapping(FieldOutlineMapping<Embedded> embeddedMapping) {
		this.embeddedMapping = embeddedMapping;
	}

	private FieldOutlineMapping<?> toOneMapping = new ToOneMapping();

	public FieldOutlineMapping<?> getToOneMapping() {
		return toOneMapping;
	}

	public void setToOneMapping(FieldOutlineMapping<?> toOneMapping) {
		this.toOneMapping = toOneMapping;
	}

	private FieldOutlineMapping<ManyToOne> manyToOneMapping = new ManyToOneMapping();

	public FieldOutlineMapping<ManyToOne> getManyToOneMapping() {
		return manyToOneMapping;
	}

	public void setManyToOneMapping(
			FieldOutlineMapping<ManyToOne> manyToOneMapping) {
		this.manyToOneMapping = manyToOneMapping;
	}

	private FieldOutlineMapping<OneToOne> oneToOneMapping;// = new
															// OneToOneMapping();

	public FieldOutlineMapping<OneToOne> getOneToOneMapping() {
		throw new UnsupportedOperationException();
//		return oneToOneMapping;
	}

	public void setOneToOneMapping(FieldOutlineMapping<OneToOne> oneToOneMapping) {
		this.oneToOneMapping = oneToOneMapping;
	}

	private FieldOutlineMapping<?> toManyMapping = new ToManyMapping();

	public FieldOutlineMapping<?> getToManyMapping() {
		return toManyMapping;
	}

	public void setToManyMapping(FieldOutlineMapping<?> toManyMapping) {
		this.toManyMapping = toManyMapping;
	}

	private FieldOutlineMapping<OneToMany> oneToManyMapping = new OneToManyMapping();

	public FieldOutlineMapping<OneToMany> getOneToManyMapping() {
		return oneToManyMapping;
	}

	public void setOneToManyMapping(
			FieldOutlineMapping<OneToMany> oneToManyMapping) {
		this.oneToManyMapping = oneToManyMapping;
	}

	private FieldOutlineMapping<ManyToMany> manyToManyMapping;

	public FieldOutlineMapping<ManyToMany> getManyToManyMapping() {
		throw new UnsupportedOperationException();
//		return manyToManyMapping;
	}

	public void setManyToManyMapping(
			FieldOutlineMapping<ManyToMany> manyToManyMapping) {
		this.manyToManyMapping = manyToManyMapping;
	}

	private FieldOutlineMapping<Transient> transientMapping = new TransientMapping();

	public FieldOutlineMapping<Transient> getTransientMapping() {
		return transientMapping;
	}

	public void setTransientMapping(
			FieldOutlineMapping<Transient> transientMapping) {
		this.transientMapping = transientMapping;
	}

	private ModelCustomizations modelCustomizations;

	public ModelCustomizations getCustomizations() {
		return modelCustomizations;
	}

	public void setModelCustomizations(ModelCustomizations modelCustomizations) {
		this.modelCustomizations = modelCustomizations;
	}

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	private Ignoring ignoring;

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

}
