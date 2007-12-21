package org.jvnet.hyperjaxb3.ejb.strategy.outline.base.ignoring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.IsClassOutlineIgnored;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultIsClassOutlineIgnored implements IsClassOutlineIgnored {

	protected Log logger = LogFactory.getLog(getClass());

	public Boolean process(ProcessOutline context, ClassOutline classOutline,
			Options options) {

		if (context.getIgnoring().isPackageOutlineIgnored(context,
				classOutline._package(), options)) {
			logger.debug("Class outline is ignored since package is ignored.");
			markAsAcknowledged(classOutline);
			return true;
		} else if (CustomizationUtils.containsCustomization(classOutline,
				Customizations.IGNORED_ELEMENT_NAME)) {
			logger.debug("Class outline is ignored per customization.");
			markAsAcknowledged(classOutline);
			return true;
		} else if (classOutline.getSuperClass() != null
				&& context.getIgnoring().isClassOutlineIgnored(context,
						classOutline.getSuperClass(), options)) {
			logger
					.debug("Class outline is ignored since superclass outline is ignored.");
			markAsAcknowledged(classOutline);
			return true;
		} else {
			return false;
		}
	}

	public void markAsAcknowledged(ClassOutline classOutline) {
		Customizations.markAsAcknowledged(classOutline.target);

		for (final FieldOutline fieldOutline : classOutline.getDeclaredFields()) {
			Customizations.markAsAcknowledged(fieldOutline.getPropertyInfo());
		}
	}

}
