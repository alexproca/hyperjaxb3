package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.springframework.beans.factory.annotation.Required;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;

public class Mapping {

	private ClassOutlineMapping<Object> entityOrMappedSuperclassMapping = new EntityOrMappedSuperclassMapping();

	public ClassOutlineMapping<Object> getEntityOrMappedSuperclassMapping() {
		return entityOrMappedSuperclassMapping;
	}

	public void setEntityOrMappedSuperclassMapping(
			ClassOutlineMapping<Object> entityOrMappedSuperclassMapping) {
		this.entityOrMappedSuperclassMapping = entityOrMappedSuperclassMapping;
	}

	private ClassOutlineMapping<Entity> entityMapping = new EntityMapping();

	public ClassOutlineMapping<Entity> getEntityMapping() {
		return entityMapping;
	}

	public void setEntityMapping(ClassOutlineMapping<Entity> entityMapping) {
		this.entityMapping = entityMapping;
	}

	private ClassOutlineMapping<MappedSuperclass> mappedSuperclassMapping = new MappedSuperclassMapping();

	public ClassOutlineMapping<MappedSuperclass> getMappedSuperclassMapping() {
		return mappedSuperclassMapping;
	}

	public void setMappedSuperclassMapping(
			ClassOutlineMapping<MappedSuperclass> mappedSuperclassMapping) {
		this.mappedSuperclassMapping = mappedSuperclassMapping;
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

	private FieldOutlineMapping<Basic> basicMapping = new BasicMapping();

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

	/*
	private FieldOutlineMapping<EmbeddedId> embeddedIdMaping;// = new

	// EmbeddedIdMapping();

	public FieldOutlineMapping<EmbeddedId> getEmbeddedIdMapping() {
		throw new UnsupportedOperationException();
		// return embeddedIdMaping;
	}

	public void setEmbeddedIdMaping(
			FieldOutlineMapping<EmbeddedId> embeddedIdMaping) {
		this.embeddedIdMaping = embeddedIdMaping;
	}
*/
	/*private FieldOutlineMapping<Embedded> embeddedMapping;// = new

	// EmbeddedMapping();

	public FieldOutlineMapping<Embedded> getEmbeddedMapping() {
		throw new UnsupportedOperationException();
		// return embeddedMapping;
	}

	public void setEmbeddedMapping(FieldOutlineMapping<Embedded> embeddedMapping) {
		this.embeddedMapping = embeddedMapping;
	}*/

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

	private FieldOutlineMapping<OneToOne> oneToOneMapping = new OneToOneMapping();

	public FieldOutlineMapping<OneToOne> getOneToOneMapping() {
		return oneToOneMapping;
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

	private FieldOutlineMapping<ManyToMany> manyToManyMapping = new ManyToManyMapping();

	public FieldOutlineMapping<ManyToMany> getManyToManyMapping() {
		return manyToManyMapping;
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

	private Customizing customizing;

	public Customizing getCustomizing() {
		return customizing;
	}

	@Required
	public void setCustomizing(Customizing modelCustomizations) {
		this.customizing = modelCustomizations;
	}

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	@Required
	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	private Ignoring ignoring;

	public Ignoring getIgnoring() {
		return ignoring;
	}

	@Required
	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

}
