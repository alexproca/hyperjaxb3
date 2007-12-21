package org.jvnet.hyperjaxb3.ejb.strategy.outline.base;

import java.util.Collection;

import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.codemodel.util.JTypeUtils;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.AnnotateFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessFieldOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.plugin.annotate.Annotator;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.FieldAccessorUtils;

import com.sun.codemodel.JMethod;
import com.sun.codemodel.JType;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CEnumLeafInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultProcessFieldOutline implements ProcessFieldOutline {

	protected Log logger = LogFactory.getLog(getClass());

	public FieldOutline process(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {
		logger.debug("Processing field ["
				+ fieldOutline.getPropertyInfo().getName(true) + "].");

		final JMethod issetter = FieldAccessorUtils.issetter(fieldOutline);
		if (issetter != null) {
			logger.debug("Annotating [" + issetter.name()
					+ "] with @javax.persistence.Transient.");
			issetter.annotate(Transient.class);
		}

		final AnnotateFieldOutline annotateFieldOutline = getAnnotateFieldOutline(
				context, fieldOutline, options);

		final Collection<XAnnotation> xannotations = annotateFieldOutline
				.process(context, fieldOutline, options);

		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

		getAnnotator().annotate(fieldOutline.parent().ref.owner(), getter,
				xannotations);

		return fieldOutline;
	}

	private Annotator annotator = Annotator.INSTANCE;

	public Annotator getAnnotator() {
		return annotator;
	}

	public void setAnnotator(Annotator annotator) {
		this.annotator = annotator;
	}

	public boolean isFieldOutlineId(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {
		return Customizations.findCustomization(fieldOutline.getPropertyInfo(),
				Customizations.ID_ELEMENT_NAME) != null;
	}

	public boolean isFieldOutlineVersion(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {

		return CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.VERSION_ELEMENT_NAME);
	}

	public AnnotateFieldOutline getAnnotateFieldOutline(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {
		if (context.getIgnoring().isFieldOutlineIgnored(context, fieldOutline,
				options)) {
			return context.getAnnotate().getAnnotateFieldOutlineTransient();
		} else if (isFieldOutlineId(context, fieldOutline, options)) {
			return context.getAnnotate().getAnnotateFieldOutlineId();
		} else if (isFieldOutlineVersion(context, fieldOutline, options)) {
			return context.getAnnotate().getAnnotateFieldOutlineVersion();
		} else {

			final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
			if (!propertyInfo.isCollection()) {
				logger.debug("Field outline  [" + propertyInfo.getName(true)
						+ "] is a single field.");

				final Collection<? extends CTypeInfo> types = propertyInfo
						.ref();

				if (types.size() == 1) {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous single field.");

					if (isFieldOutlineTemporal(context, fieldOutline, options)) {
						logger.debug("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a basic field.");
						return context.getAnnotate()
								.getAnnotateFieldOutlineTemporal();
					} else if (isFieldOutlineEnum(context, fieldOutline,
							options)) {
						logger.debug("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a enum  field.");
						return context.getAnnotate()
								.getAnnotateFieldOutlineEnumerated();
					} else if (isFieldOutlineBasic(context, fieldOutline,
							options)) {
						logger.debug("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a basic field.");
						return context.getAnnotate()
								.getAnnotateFieldOutlineBasic();
					} else if (isFieldOutlineComplex(context, fieldOutline,
							options)) {
						logger.debug("Field outline  ["
								+ propertyInfo.getName(true)
								+ "] is a complex field.");
						return context.getAnnotate()
								.getAnnotateFieldOutlineManyToOne();
					}
				} else {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a heterogeneous single field.");

				}

				// final Collection<? extends CTypeInfo> types = propertyInfo
				// .ref();
				// if (types.size() == 1) {
				// logger.warn("Field outline [" + propertyInfo.getName(true)
				// + "] is a homogeneous field.");
				//
				// final CTypeInfo type = types.iterator().next();
				//					
				// JType type3 =
				// type.getType().toType(fieldOutline.parent().parent(),
				// Aspect.EXPOSED);
				//					
				// JClass class1 = type3.owner().BYTE.array();
				// boolean b = type3.equals(class1);
				// b = b;
				//					
				// } else {
				// logger
				// .warn("Field outline ["
				// + propertyInfo.getName(true)
				// + "] is a heterogeneous field. It will be made transient.");
				// return context.getAnnotateFieldOutlineTransient();
				// }

			} else {
				logger.debug("Field outline F [" + propertyInfo.getName(true)
						+ "] is a collection field.");

				final Collection<? extends CTypeInfo> types = propertyInfo
						.ref();

				if (types.size() == 1) {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a homogeneous collection field.");
					if (isFieldOutlineComplex(context, fieldOutline, options)) {
						logger
								.debug("Field outline  ["
										+ propertyInfo.getName(true)
										+ "] is a complex homogeneous collection field.");
						return context.getAnnotate()
								.getAnnotateFieldOutlineOneToMany();
					}
				} else {
					logger.debug("Field outline  ["
							+ propertyInfo.getName(true)
							+ "] is a heterogenous collection field.");
				}

			}

			logger.error("Field outline  [" +

			((CClassInfo) propertyInfo.parent()).getName() + "."
					+ propertyInfo.getName(true)
					+ "] could not be annotated. It will be made transient.");
			return context.getAnnotate().getAnnotateFieldOutlineTransient();
		}
	}

	public boolean isFieldOutlineBasic(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

		final JType type = getter.type();

		return JTypeUtils.isBasicType(type);
	}

	public boolean isFieldOutlineTemporal(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {
		final JMethod getter = FieldAccessorUtils.getter(fieldOutline);

		final JType type = getter.type();

		return JTypeUtils.isTemporalType(type);
	}

	public boolean isFieldOutlineEnum(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return type instanceof CEnumLeafInfo;
	}

	public boolean isFieldOutlineComplex(ProcessOutline context,
			FieldOutline fieldOutline, Options options) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		return type instanceof CClassInfo;
	}
}
