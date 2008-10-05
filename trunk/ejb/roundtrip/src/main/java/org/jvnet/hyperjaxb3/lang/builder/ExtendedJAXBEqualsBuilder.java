package org.jvnet.hyperjaxb3.lang.builder;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceConstants;
import org.custommonkey.xmlunit.DifferenceListener;
import org.jvnet.hyperjaxb3.xml.datatype.util.XMLGregorianCalendarUtils;
import org.w3c.dom.Node;

public class ExtendedJAXBEqualsBuilder extends
		org.jvnet.jaxb2_commons.lang.builder.ExtendedJAXBEqualsBuilder {

	@Override
	public EqualsBuilder append(Object lhs, Object rhs) {

		if (!isEquals()) {
			return this;
		}
		if (lhs == rhs) {
			return this;
		}
		if (lhs == null || rhs == null) {
			this.setEquals(false);
			return this;
		}
		final Class lhsClass = lhs.getClass();
		if (lhsClass.isArray()) {
			super.append(lhs, rhs);
		} else {
			if (lhs instanceof Node && rhs instanceof Node) {
				final Diff diff = new Diff(new DOMSource((Node) lhs),
						new DOMSource((Node) rhs)) {
					@Override
					public int differenceFound(Difference difference) {
						if (difference.getId() == DifferenceConstants.NAMESPACE_PREFIX_ID) {
							return DifferenceListener.RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
						} else {
							return super.differenceFound(difference);
						}
					}
				};
				this.setEquals(diff.identical());
			} else if (lhs instanceof XMLGregorianCalendar
					&& rhs instanceof XMLGregorianCalendar) {
				append(XMLGregorianCalendarUtils
						.getTimeInMillis((XMLGregorianCalendar) lhs),
						XMLGregorianCalendarUtils
								.getTimeInMillis((XMLGregorianCalendar) rhs));
			} else {
				super.append(lhs, rhs);
			}
		}
		return this;
	}

}
