package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import org.jvnet.hyperjaxb3.ejb.strategy.customizations.ModelCustomizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.naming.Naming;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;

public interface Mapping {

	public ClassOutlineMapping<Entity> getEntityMapping();

	public ClassOutlineMapping<Attributes> getAttributesMapping();
	
	public FieldOutlineMapping<Id> getIdMapping();
	public FieldOutlineMapping<Basic> getBasicMapping();
	public FieldOutlineMapping<Version> getVersionMapping();

//	public FieldOutlineMapping<EmbeddedId> getEmbeddedIdMapping();
//	public FieldOutlineMapping<Embedded> getEmbeddedMapping();

	public FieldOutlineMapping<ManyToOne> getManyToOneMapping();
	public FieldOutlineMapping<OneToMany> getOneToManyMapping();
	
//	public FieldOutlineMapping<OneToOne> getOneToOneMapping();
//	public FieldOutlineMapping<ManyToMany> getManyToManyMapping();
	
	public FieldOutlineMapping<Transient> getTransientMapping();
	
	public ModelCustomizations getCustomizations();
	
	public Naming getNaming();
}
