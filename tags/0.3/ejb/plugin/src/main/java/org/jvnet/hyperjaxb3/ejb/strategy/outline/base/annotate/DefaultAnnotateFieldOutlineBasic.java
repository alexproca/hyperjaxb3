package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.annotate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.annox.model.XAnnotationField;
import org.jvnet.annox.model.XAnnotationField.XBoolean;
import org.jvnet.annox.model.XAnnotationField.XEnum;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAnnotateFieldOutlineBasic implements AnnotateFieldOutline {

	public Collection<XAnnotation> process(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options) {

		final Collection<XAnnotation> xannotations = createBasic(
				outlineProcessor, fieldOutline, options);

		return xannotations;
	}

	private Collection<XAnnotation> createBasic(
			ProcessOutline outlineProcessor, FieldOutline fieldOutline,
			Options options) {

		final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic cbasic = CustomizationUtils
				.containsCustomization(fieldOutline.getPropertyInfo(),
						Customizations.BASIC_ELEMENT_NAME) ? Customizations
				.<org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic> findCustomization(
						fieldOutline.getPropertyInfo(),
						Customizations.BASIC_ELEMENT_NAME)
				: new org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic();

		final Collection<XAnnotation> xannotations = new ArrayList<XAnnotation>(
				2);

		final XAnnotation basic = createBasic(outlineProcessor, fieldOutline,
				options, cbasic);

		xannotations.add(basic);

		if (cbasic.getLob() != null) {
			xannotations.add(createLob(outlineProcessor, fieldOutline, options,
					cbasic));
		}

		xannotations.addAll(outlineProcessor.getAnnotate()
				.getAnnotateFieldOutlineColumn().process(outlineProcessor,
						fieldOutline, options));
		return xannotations;
	}

	public XAnnotation createLob(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic cbasic) {
		return new XAnnotation(Lob.class);
	}

	private XAnnotation createBasic(ProcessOutline outlineProcessor,
			FieldOutline fieldOutline, Options options,
			final org.jvnet.hyperjaxb3.ejb.schemas.customizations.Basic cbasic) {
		if (cbasic == null) {
		}

		final XAnnotation basic = new XAnnotation(Basic.class, createFetch(
				outlineProcessor, fieldOutline, options), createOptional(
				outlineProcessor, fieldOutline, options));
		return basic;
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

	public XBoolean createOptional(ProcessOutline outlineProcessor,
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

	public XEnum createFetch(ProcessOutline outlineProcessor,
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
