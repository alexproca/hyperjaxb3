package org.jvnet.hyperjaxb3.ejb.strategy.outline.base;

import java.util.Collection;
import java.util.LinkedList;

import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessFieldOutlines;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultProcessFieldOutlines implements ProcessFieldOutlines {

	public Collection<FieldOutline> process(ProcessOutline context,
			ClassOutline classOutline, Options options) {

		final Collection<FieldOutline> processedFieldOutlines = new LinkedList<FieldOutline>();
		final FieldOutline[] fieldOutlines = classOutline.getDeclaredFields();
		for (final FieldOutline fieldOutline : fieldOutlines) {
			final FieldOutline processedFieldOutline = context
					.getProcessFieldOutline().process(context, fieldOutline,
							options);
			if (processedFieldOutline != null) {
				processedFieldOutlines.add(processedFieldOutline);
			}
		}
		return processedFieldOutlines;
	}
}
