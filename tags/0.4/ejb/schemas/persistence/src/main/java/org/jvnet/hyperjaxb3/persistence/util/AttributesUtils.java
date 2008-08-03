package org.jvnet.hyperjaxb3.persistence.util;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.Transient;
import com.sun.java.xml.ns.persistence.orm.Version;

public class AttributesUtils {

	private AttributesUtils() {

	}

	public static Object getAttribute(Attributes attributes, String name) {
		if (attributes == null || name == null) {
			return null;
		} else {
			for (final Id attribute : attributes.getId()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}

			{
				final EmbeddedId attribute = attributes.getEmbeddedId();
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}

			for (final Basic attribute : attributes.getBasic()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}

			for (final Version attribute : attributes.getVersion()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			for (final ManyToOne attribute : attributes.getManyToOne()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			for (final OneToMany attribute : attributes.getOneToMany()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			for (final OneToOne attribute : attributes.getOneToOne()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			for (final ManyToMany attribute : attributes.getManyToMany()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			for (final Embedded attribute : attributes.getEmbedded()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			for (final Transient attribute : attributes.getTransient()) {
				if (attribute != null && name.equals(attribute.getName())) {
					return attribute;
				}
			}
			return null;
		}
	}
}
