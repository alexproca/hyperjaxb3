package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XClass;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public abstract class AbstractAnnotateFieldOutlineXToX implements
		AnnotateFieldOutline {

	public XAnnotationField createMappedBy(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options, String mappedBy) {
		return AnnotationUtils.create("mappedBy", mappedBy);
	}

	public XAnnotationField createFetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options, String fetch) {
		return fetch == null ? null : AnnotationUtils.create("fetch", FetchType
				.valueOf(fetch));
	}

	public XAnnotationField createCascade(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			com.sun.java.xml.ns.persistence.orm.CascadeType cascade) {

		if (cascade == null) {
			return null;
		} else {
			final Collection<CascadeType> cascades = new HashSet<CascadeType>();

			if (cascade.getCascadeAll() != null) {
				cascades.add(CascadeType.ALL);
			}
			if (cascade.getCascadeMerge() != null) {
				cascades.add(CascadeType.MERGE);
			}
			if (cascade.getCascadePersist() != null) {
				cascades.add(CascadeType.PERSIST);
			}
			if (cascade.getCascadeRefresh() != null) {
				cascades.add(CascadeType.REFRESH);
			}
			if (cascade.getCascadeRemove() != null) {
				cascades.add(CascadeType.REMOVE);
			}
			return AnnotationUtils.create("cascade", cascades
					.toArray(new CascadeType[cascades.size()]));
		}
	}

	public XClass createTargetEntity(ProcessOutline outlineProcessor, FieldOutline fieldOutline, Options options) {
	
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
	
		final Collection<? extends CTypeInfo> types = propertyInfo.ref();
	
		assert types.size() == 1;
	
		final CTypeInfo type = types.iterator().next();
	
		assert type instanceof NType;
	
		final NType childClassInfo = (NType) type;
	
		return new XAnnotationField.XClass("targetEntity", childClassInfo
				.fullName());
	
	}

}
