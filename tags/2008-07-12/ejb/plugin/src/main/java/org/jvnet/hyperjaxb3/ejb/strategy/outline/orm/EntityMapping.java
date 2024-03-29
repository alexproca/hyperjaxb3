package org.jvnet.hyperjaxb3.ejb.strategy.outline.orm;

import javax.persistence.InheritanceType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.Attributes;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Inheritance;
import com.sun.java.xml.ns.persistence.orm.Table;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;

public class EntityMapping implements ClassOutlineMapping<Entity> {

	private static Log logger = LogFactory.getLog(EntityMapping.class);

	public Entity process(Mapping context, ClassOutline classOutline,
			Options options) {
		final Entity entity = context.getCustomizations().getEntity(classOutline);
		createEntity(context, classOutline, entity);
		return entity;
	}

	public void createEntity(Mapping context, ClassOutline classOutline,
			final Entity entity) {
		createEntity$Name(context, classOutline, entity);
		createEntity$Class(context, classOutline, entity);

		createEntity$Inheritance(context, classOutline, entity);

		createEntity$Table(context, classOutline, entity);

		createEntity$Attributes(context, classOutline, entity);
	}

	public void createEntity$Name(Mapping context, ClassOutline classOutline,
			final Entity entity) {
		if (entity.getName() == null || "##default".equals(entity.getName())) {
			entity.setName(OutlineUtils.getClassName(classOutline));
		}
	}

	public void createEntity$Class(Mapping context, ClassOutline classOutline,
			final Entity entity) {
		if (entity.getClazz() == null || "##default".equals(entity.getClazz())) {
			entity.setClazz(OutlineUtils.getClassName(classOutline));
		}
	}

	public void createEntity$Inheritance(Mapping context,
			ClassOutline classOutline, final Entity entity) {
		final InheritanceType inheritanceStrategy = getInheritanceStrategy(
				context, classOutline);

		if (isEntityClassHierarchyRoot(context, classOutline)) {
			if (entity.getInheritance() == null
					|| entity.getInheritance().getStrategy() == null) {
				entity.setInheritance(new Inheritance());
				entity.getInheritance().setStrategy(inheritanceStrategy.name());
			}
		} else {
			if (entity.getInheritance() != null
					&& entity.getInheritance().getStrategy() != null) {
				entity.setInheritance(null);
			}
		}
	}

	private void createEntity$Table(Mapping context, ClassOutline classOutline,
			Entity entity) {
		final InheritanceType inheritanceStrategy = getInheritanceStrategy(
				context, classOutline);
		switch (inheritanceStrategy) {
		case JOINED:
			if (entity.getTable() == null) {
				entity.setTable(new Table());
			}
			createTable(context, classOutline, entity.getTable());
			break;
		case SINGLE_TABLE:
			if (isEntityClassHierarchyRoot(context, classOutline)) {
				if (entity.getTable() == null) {
					entity.setTable(new Table());
				}
				createTable(context, classOutline, entity.getTable());
			} else {
				if (entity.getTable() != null) {
					entity.setTable(null);
				}
			}
			break;
		case TABLE_PER_CLASS:
			if (entity.getTable() == null) {
				entity.setTable(new Table());
			}
			createTable(context, classOutline, entity.getTable());
			break;
		default:
			throw new IllegalArgumentException("Unknown inheritance strategy.");
		}
	}

	public void createTable(Mapping context, ClassOutline classOutline,
			final Table table) {
		if (table.getName() == null || "##default".equals(table.getName())) {
			// TODO
			table.setName(context.getNaming().getEntityTableName(null,
					classOutline, null));
		}
	}

	public void createEntity$Attributes(Mapping context,
			ClassOutline classOutline, final Entity entity) {
		final Attributes attributes = context.getAttributesMapping().process(
				context, classOutline, null);
		entity.setAttributes(attributes);
	}

	public javax.persistence.InheritanceType getInheritanceStrategy(
			Mapping context, ClassOutline classOutline) {
		if (isEntityClassHierarchyRoot(context, classOutline)) {
			// TODO
			final Entity entity = null; // getEntity();
			if (entity.getInheritance() != null
					&& entity.getInheritance().getStrategy() != null) {
				return InheritanceType.valueOf(entity.getInheritance()
						.getStrategy());
			} else {
				return javax.persistence.InheritanceType.JOINED;
			}
		} else {
			final ClassOutline superClassOutline = getSuperClassOutline(
					context, classOutline);
			return getInheritanceStrategy(context, superClassOutline);
		}
	}

	public ClassOutline getSuperClassOutline(Mapping context,
			ClassOutline classOutline) {
		return classOutline.getSuperClass();
	}

	public boolean isEntityClassHierarchyRoot(Mapping context,
			ClassOutline classOutline) {
		return getSuperClassOutline(context, classOutline) == null;
	}

}
