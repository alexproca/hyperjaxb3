package org.jvnet.hyperjaxb3.ejb.strategy.customizing;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Entity;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.GeneratedId;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Version;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

/**
 * Customizations interface.
 */
public interface Customizing {

	public GeneratedId getGeneratedId(CClassInfo classInfo);

	public Id getId(CPropertyInfo property);

	public Id getId(FieldOutline property);

	public Basic getBasic(CPropertyInfo property);

	public Basic getBasic(FieldOutline property);

	public Version getVersion(CPropertyInfo property);

	public Version getVersion(FieldOutline property);

	public Object getToOne(CPropertyInfo property);

	public Object getToOne(FieldOutline property);

	public Object getToMany(CPropertyInfo property);

	public Object getToMany(FieldOutline property);

	/**
	 * Returns the one-to-many customization for the given property. Must not
	 * return null.
	 * 
	 * @param context
	 *            processing context.
	 * @param property
	 *            property to retrieve customization for.
	 * @return One-to-many customization for the given property, never null.
	 */
	public OneToMany getOneToMany(CPropertyInfo property);

	public OneToMany getOneToMany(FieldOutline property);

	public ManyToOne getManyToOne(CPropertyInfo property);

	public ManyToOne getManyToOne(FieldOutline property);
	
	public OneToOne getOneToOne(CPropertyInfo property);

	public OneToOne getOneToOne(FieldOutline property);

	public ManyToMany getManyToMany(CPropertyInfo property);

	public ManyToMany getManyToMany(FieldOutline property);
	

	// New generation

	public Entity getEntity(ClassOutline classOutline);
}
