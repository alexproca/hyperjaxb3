package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Version;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class VersionMapping extends PropertyMapping implements
		FieldOutlineMapping<Version> {

	public Version process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Version version = context.getCustomizing().getVersion(
				fieldOutline);

		createVersion$Name(context, fieldOutline, version);
		createVersion$Column(context, fieldOutline, version);
		createVersion$Temporal(context, fieldOutline, version);
		return version;
	}

	public void createVersion$Name(Mapping context, FieldOutline fieldOutline,
			final Version version) {
		version.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createVersion$Column(Mapping context,
			FieldOutline fieldOutline, final Version version) {
		if (version.getColumn() == null) {
			version.setColumn(new Column());
		}

		version.setColumn(createColumn(context, fieldOutline, version
				.getColumn()));
	}

	public void createVersion$Temporal(Mapping context,
			FieldOutline fieldOutline, Version version) {
		if (version.getTemporal() == null && isTemporal(fieldOutline)) {
			version.setTemporal(getTemporalType(fieldOutline));
		}
	}

}
