package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.ignoring;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ignoring.IsFieldOutlineIgnored;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultIsFieldOutlineIgnored implements IsFieldOutlineIgnored {

	protected Log logger = LogFactory.getLog(getClass());

	public Boolean process(ProcessOutline context, FieldOutline fieldOutline,
			Options options) {

		if (context.getIgnoring().isClassOutlineIgnored(context,
				fieldOutline.parent(), options)) {
			logger
					.debug("Field outline is ignored since its class outline is ignored.");
			return true;
		} else if (CustomizationUtils.containsCustomization(fieldOutline,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Field outline is ignored per customization.");
			return true;
		} else {

			boolean allIgnored = true;

			final Collection<? extends CTypeInfo> types = fieldOutline
					.getPropertyInfo().ref();

			for (final CTypeInfo type : types) {
				if (type instanceof CClassInfo) {
					final CClassInfo fieldClassInfo = (CClassInfo) type;
					final ClassOutline fieldClassOutline = fieldOutline
							.parent().parent().getClazz(fieldClassInfo);
					allIgnored = allIgnored
							&& context.getIgnoring().isClassOutlineIgnored(
									context, fieldClassOutline, options);
				} else {
					allIgnored = false;
				}
			}

			if (allIgnored) {
				logger
						.debug("Field outline is ignored since all types are ignored.");
				markAsAcknowledged(fieldOutline);

			}
			return allIgnored;
		}
	}

	public void markAsAcknowledged(FieldOutline fieldOutline) {
		Customizations.markAsAcknowledged(fieldOutline.getPropertyInfo());
	}
}
