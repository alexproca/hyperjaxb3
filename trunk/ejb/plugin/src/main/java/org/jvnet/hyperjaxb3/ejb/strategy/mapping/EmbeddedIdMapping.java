package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.AttributeOverride;
import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Column;
import com.sun.java.xml.ns.persistence.orm.EmbeddableAttributes;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class EmbeddedIdMapping extends PropertyMapping implements
		FieldOutlineMapping<EmbeddedId> {

	public EmbeddedId process(Mapping context, FieldOutline fieldOutline,
			Options options) throws Exception {

		final EmbeddedId embeddedId = context.getCustomizing().getEmbeddedId(
				fieldOutline);

		createEmbeddedId$Name(context, fieldOutline, embeddedId);

		createEmbeddedId$AttributeOverride(context, fieldOutline, embeddedId);

		return embeddedId;
	}

	public void createEmbeddedId$Name(Mapping context, FieldOutline fieldOutline,
			final EmbeddedId embeddedId) {
		embeddedId.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createEmbeddedId$AttributeOverride(Mapping context,
			FieldOutline fieldOutline, final EmbeddedId embeddedId)
			throws Exception {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo typeInfo = types.iterator().next();

		assert typeInfo instanceof CClassInfo;

		final CClassInfo classInfo = (CClassInfo) typeInfo;

		final Outline outline = fieldOutline.parent().parent();

		final ClassOutline classOutline = outline.getClazz(classInfo);

		final Options options = outline.getModel().options;

		final Map<String, AttributeOverride> attributeOverrides = new HashMap<String, AttributeOverride>();
		for (final AttributeOverride attributeOverride : embeddedId
				.getAttributeOverride()) {
			attributeOverrides.put(attributeOverride.getName(),
					attributeOverride);
		}

		final EmbeddableAttributes embeddableAttributes = context
				.getEmbeddableAttributesMapping().process(context,
						classOutline, options);

		for (final Basic basic : embeddableAttributes.getBasic()) {
			final String name = basic.getName();
			final AttributeOverride attributeOverride;
			if (!attributeOverrides.containsKey(name)) {
				attributeOverride = new AttributeOverride();
				attributeOverride.setName(name);
				attributeOverrides.put(name, attributeOverride);
				embeddedId.getAttributeOverride().add(attributeOverride);
			} else {
				attributeOverride = attributeOverrides.get(name);
			}

			if (attributeOverride.getColumn() == null) {
				attributeOverride.setColumn(new Column());
			}
		}

		final FieldOutline[] fieldOutlines = classOutline.getDeclaredFields();
		for (final FieldOutline childFieldOutline : fieldOutlines) {
			final AttributeOverride attributeOverride = attributeOverrides
					.get(OutlineUtils.getPropertyName(childFieldOutline));

			if (attributeOverride != null) {
				final Column column = attributeOverride.getColumn();
				if (column.getName() == null
						|| "##default".equals(column.getName())) {
					column.setName(context.getNaming().getEmbedded$Column$Name(
							context, fieldOutline, childFieldOutline));
				}
			}
		}
	}
}
