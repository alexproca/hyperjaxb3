package org.jvnet.hyperjaxb3.ejb.strategy.naming.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CClassRef;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultNaming implements Naming, InitializingBean {

	protected Log logger = LogFactory.getLog(Naming.class);

	private int maxIdentifierLength = 30;

	public int getMaxIdentifierLength() {
		return maxIdentifierLength;
	}

	public void setMaxIdentifierLength(int maxIdentifierLength) {
		this.maxIdentifierLength = maxIdentifierLength;
	}

	private Properties reservedNames;

	public Properties getReservedNames() {
		return reservedNames;
	}

	@Required
	public void setReservedNames(Properties reservedNames) {
		this.reservedNames = reservedNames;
	}

	private boolean updated = false;

	private Map<String, String> nameKeyMap = new TreeMap<String, String>();

	private Map<String, String> keyNameMap = new TreeMap<String, String>();

	public void afterPropertiesSet() throws Exception {

		final Set<Entry<Object, Object>> entries = getReservedNames()
				.entrySet();
		for (final Entry<Object, Object> entry : entries) {
			final Object entryKey = entry.getKey();
			if (entryKey != null) {
				final String key = entryKey.toString().toUpperCase();
				final Object entryValue = entry.getValue();
				final String value = entryValue == null
						|| "".equals(entryValue.toString().trim()) ? key + "_"
						: entryValue.toString();
				nameKeyMap.put(key, value);
				keyNameMap.put(value, key);
			}
		}
	}

	public String getName(final String draftName) {

		Validate.notNull(draftName, "Name must not be null.");
		final String name = draftName.replace('$', '_').toUpperCase();
		if (nameKeyMap.containsKey(name)) {
			return (String) nameKeyMap.get(name);
		} else if (name.length() >= getMaxIdentifierLength()) {
			for (int i = 0;; i++) {
				final String suffix = Integer.toString(i);
				final String prefix = name.substring(0,
						getMaxIdentifierLength() - suffix.length() - 1);
				final String identifier = prefix + "_" + suffix;
				if (!keyNameMap.containsKey(identifier)) {
					nameKeyMap.put(name, identifier);
					keyNameMap.put(identifier, name);
					updated = true;
					return identifier;
				}
			}
		} else if (getReservedNames().contains(name.toUpperCase())) {
			return name + "_";
		} else {
			return name;
		}

	}

	public String getColumnName(FieldOutline fieldOutline) {

		final String fieldName = fieldOutline.getPropertyInfo().getName(true);

		return getName(fieldName);
	}

	public String getOneToManyJoinTableName(FieldOutline fieldOutline) {
		final String targetEntityTableName = getTargetEntityTableName(fieldOutline);
		final String entityTableName = getEntityTableName(fieldOutline.parent());
		final String fieldColumnName = getColumnName(fieldOutline);
		return getName(entityTableName + "_" + fieldColumnName + "_"
				+ targetEntityTableName);
	}

	public String getEntityTableName(FieldOutline fieldOutline) {
		final String name = fieldOutline.parent().target.getSqueezedName();
		return getName(name);
	}

	public String getEntityTableName(ClassOutline classOutline) {
		return getEntityTableName(classOutline.target);
	}

	public String getEntityTableName(final CClass classInfo) {
		if (classInfo instanceof CClassInfo) {
			final String name = ((CClassInfo) classInfo).getSqueezedName();
			return getName(name);
		} else if (classInfo instanceof CClassRef) {
			final String fullName = ((CClassRef) classInfo).fullName();
			if (!fullName.contains(".")) {
				return getName(fullName);
			} else {
				return getName(fullName
						.substring(fullName.lastIndexOf(".") + 1));
			}
		} else {
			throw new AssertionError("Unexpected type.");
		}
	}

	private String getTargetEntityTableName(FieldOutline fieldOutline) {
		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		return getEntityTableName(childClassInfo);
	}

	public String getManyToOneJoinColumnName(FieldOutline fieldOutline) {
		final String entityTableName = getTargetEntityTableName(fieldOutline);
		final String fieldColumnName = getColumnName(fieldOutline);
		return getName(fieldColumnName + "_" + entityTableName + "_ID");
	}

	public String getOneToManyJoinColumnName(FieldOutline fieldOutline) {

		final String entityTableName = getEntityTableName(fieldOutline.parent());
		final String fieldColumnName = getColumnName(fieldOutline);

		return getName(fieldColumnName + "_" + entityTableName + "_ID");
	}

	public String getOneToManyJoinTableJoinColumnName(FieldOutline fieldOutline) {
		final String entityTableName = getEntityTableName(fieldOutline.parent());

		return getName("PARENT_" + entityTableName + "_ID");
	}

	public String getOneToManyJoinTableInverseJoinColumnName(
			FieldOutline fieldOutline) {
		return getName(

		"CHILD_" + getTargetEntityTableName(fieldOutline)

		+ "_ID");
	}

	public String getManyToOneJoinTableName(FieldOutline fieldOutline) {
		return getOneToManyJoinTableName(fieldOutline);
	}

	public String getManyToOneJoinTableInverseJoinColumnName(
			FieldOutline fieldOutline) {
		return getOneToManyJoinTableInverseJoinColumnName(fieldOutline);
	}

	public String getManyToOneJoinTableJoinColumnName(FieldOutline fieldOutline) {
		return getOneToManyJoinTableJoinColumnName(fieldOutline);
	}

}
