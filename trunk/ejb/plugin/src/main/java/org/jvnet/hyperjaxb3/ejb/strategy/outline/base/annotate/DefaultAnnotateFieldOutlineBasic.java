package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XBoolean;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineBasic extends
		AbstractAnnotateSimpleFieldOutline implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic basic =

		outlineProcessor.getCustomizing().getBasic(fieldOutline);

		return create(outlineProcessor, fieldOutline, options, basic);
	}

	public Collection<XAnnotation> create(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic cbasic) {

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		xannotations.addAll(createBasic(outlineProcessor, fieldOutline,
				options, cbasic));
		xannotations.addAll(createColumn(outlineProcessor, fieldOutline,
				options, cbasic.getColumn()));
		xannotations.addAll(createLob(outlineProcessor, fieldOutline, options,
				cbasic.getLob()));
		xannotations.addAll(createTemporal(outlineProcessor, fieldOutline, options,
				cbasic.getTemporal()));
		xannotations.addAll(createEnumerated(outlineProcessor, fieldOutline, options,
				cbasic.getEnumerated()));
//		xannotations.addAll(outlineProcessor.getAnnotate()
//				.getAnnotateFieldOutlineColumn().process(outlineProcessor,
//						fieldOutline, options));
		return xannotations;
	}

	public Collection<XAnnotation> createLob(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			com.sun.java.xml.ns.persistence.orm.Lob lob) {
		if (lob != null) {
			return Collections.singletonList(new XAnnotation(Lob.class));
		} else {
			return Collections.emptyList();
		}
	}

	public Collection<XAnnotation> createBasic(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic cbasic) {
		final XAnnotation basic = new XAnnotation(Basic.class,
		//
				createBasic$Fetch(outlineProcessor, fieldOutline, options),
				//
				createBasic$Optional(outlineProcessor, fieldOutline, options));
		return Collections.singletonList(basic);
	}

	private Boolean defaultOptional = null;

	public Boolean isDefaultOptional() {
		return defaultOptional;
	}

	public void setDefaultOptional(Boolean defaultOptional) {
		this.defaultOptional = defaultOptional;
	}

	public Boolean getOptional(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return isDefaultOptional();
	}

	public XBoolean createBasic$Optional(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		final Boolean optional = getOptional(outlineProcessor, fieldOutline,
				options);
		if (optional == null) {
			return null;
		} else {
			return new XAnnotationField.XBoolean("optional", optional);
		}

	}

	private FetchType defaultFetch = null;

	public FetchType getDefaultFetch() {
		return defaultFetch;
	}

	public void setDefaultFetch(FetchType defaultFetch) {
		this.defaultFetch = defaultFetch;
	}

	public FetchType getFetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {
		return getDefaultFetch();
	}

	public XEnum createBasic$Fetch(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final FetchType fetch = getFetch(outlineProcessor, fieldOutline,
				options);

		if (fetch == null) {
			return null;
		} else {
			return new XAnnotationField.XEnum("fetch", fetch, FetchType.class);
		}
	}
}
