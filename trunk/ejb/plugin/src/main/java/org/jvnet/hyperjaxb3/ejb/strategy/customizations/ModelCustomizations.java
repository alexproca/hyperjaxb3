package org.jvnet.hyperjaxb3.ejb.strategy.customizations;

import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Id;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.ManyToOne;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.OneToMany;

import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.outline.FieldOutline;

/**
 * Customizations interface.
 */
public interface ModelCustomizations {

	public Id getId(CClassInfo classInfo);
	public Id getId(CPropertyInfo property);
	public Id getId(FieldOutline property);
	
	public Basic getBasic(CPropertyInfo property);
	public Basic getBasic(FieldOutline property);
	

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
	
}
