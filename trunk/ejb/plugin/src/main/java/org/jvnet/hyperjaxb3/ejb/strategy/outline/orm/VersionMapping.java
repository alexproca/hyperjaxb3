package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.Version;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.FieldOutline;

public class VersionMapping extends AttributeMapping implements
		FieldOutlineMapping<Version> {

	public Version process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final Version version = context.getCustomizations().getVersion(fieldOutline);

		createVersion$Name(context, fieldOutline, version);
		createVersion$Column(context, fieldOutline, version);
		createVersion$Temporal(context, fieldOutline, version);
		return version;
	}

	public void createVersion$Name(Mapping context, FieldOutline fieldOutline,
			final Version version) {
		version.setName(fieldOutline.getPropertyInfo().getName(true));
	}

	public void createVersion$Column(Mapping context, FieldOutline fieldOutline,
			final Version version) {
		if (version.getColumn() == null) {
			version.setColumn(new Column());
		}

		version.setColumn(createColumn(context, fieldOutline, version.getColumn()));
	}

	public void createVersion$Temporal(Mapping context, FieldOutline fieldOutline,
			Version version) {
		if (version.getTemporal() == null && isTemporal(fieldOutline)) {
			version.setTemporal(getTemporalType(fieldOutline));
		}
	}

}
