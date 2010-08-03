package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.JoinTable;
import com.sun.java.xml.ns.persistence.orm.OrderColumn;
import com.sun.java.xml.ns.persistence.orm.PrimaryKeyJoinColumn;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;

public class DefaultAssociationMapping implements AssociationMapping {

	protected Log logger = LogFactory.getLog(getClass());

	@Override
	public Collection<FieldOutline> getSourceIdFieldsOutline(Mapping context,
			FieldOutline fieldOutline) {

		final ClassOutline classOutline = fieldOutline.parent();

		return getIdFieldsOutline(classOutline);
	}

	@Override
	public Collection<FieldOutline> getTargetIdFieldsOutline(Mapping context,
			FieldOutline fieldOutline) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);

		assert type != null;

		assert type instanceof CClass;

		if (type instanceof CClassInfo) {

			final ClassOutline targetClassOutline = fieldOutline.parent()
					.parent().getClazz((CClassInfo) type);

			return getIdFieldsOutline(targetClassOutline);
		} else {

			logger.error(MessageFormat
					.format("Field outline [{0}] references the type [{1}] which is not present in the XJC model "
							+ "(it is probably a class reference due to episodic compilation). "
							+ "Due to this reason Hyperjaxb3 cannot generate correct identifier column mapping. "
							+ "Please customize your association manually. See also issue HJIII-51.",
							propertyInfo.getName(true), type.getType()
									.fullName()));
			return Collections.emptyList();
		}
	}

	private Collection<FieldOutline> getIdFieldsOutline(
			final ClassOutline classOutline) {
		final Collection<FieldOutline> idFieldOutlines = new ArrayList<FieldOutline>();
		ClassOutline current = classOutline;

		while (current != null) {
			for (FieldOutline idFieldOutline : current.getDeclaredFields()) {
				final CPropertyInfo propertyInfo = idFieldOutline
						.getPropertyInfo();
				if ((CustomizationUtils.containsCustomization(propertyInfo,
						Customizations.ID_ELEMENT_NAME) || CustomizationUtils
						.containsCustomization(propertyInfo,
								Customizations.EMBEDDED_ID_ELEMENT_NAME))
						&& !CustomizationUtils.containsCustomization(
								propertyInfo,
								Customizations.IGNORED_ELEMENT_NAME)) {
					idFieldOutlines.add(idFieldOutline);
				}
			}
			current = current.getSuperClass();
		}

		return idFieldOutlines;
	}

	// * 1:1

	@Override
	public void createPrimaryKeyJoinColumns(Mapping context,
			FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines,
			List<PrimaryKeyJoinColumn> primaryKeyJoinColumns) {

		final Iterator<PrimaryKeyJoinColumn> joinColumnIterator = new ArrayList<PrimaryKeyJoinColumn>(
				primaryKeyJoinColumns).iterator();
		for (FieldOutline idFieldOutline : idFieldOutlines) {
			final PrimaryKeyJoinColumn joinColumn;
			if (joinColumnIterator.hasNext()) {
				joinColumn = joinColumnIterator.next();
			} else {
				joinColumn = new PrimaryKeyJoinColumn();
				primaryKeyJoinColumns.add(joinColumn);
			}
			createPrimaryKeyJoinColumn(context, fieldOutline, idFieldOutline,
					joinColumn);
		}

	}

	protected void createPrimaryKeyJoinColumn(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline,
			PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		createPrimaryKeyJoinColumn$Name(context, fieldOutline, idFieldOutline,
				primaryKeyJoinColumn);
	}

	protected void createPrimaryKeyJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline,
			PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		if (primaryKeyJoinColumn.getName() == null
				|| "##default".equals(primaryKeyJoinColumn.getName())) {
			primaryKeyJoinColumn.setName(context.getNaming()
					.getJoinColumn$Name(context, fieldOutline, idFieldOutline));
		}
	}

	// * M:1
	// * 1:M
	// * 1:1

	@Override
	public void createJoinColumns(Mapping context, FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines,
			List<JoinColumn> joinColumns) {
		final Iterator<JoinColumn> joinColumnIterator = new ArrayList<JoinColumn>(
				joinColumns).iterator();
		for (FieldOutline idFieldOutline : idFieldOutlines) {
			final JoinColumn joinColumn;
			if (joinColumnIterator.hasNext()) {
				joinColumn = joinColumnIterator.next();
			} else {
				joinColumn = new JoinColumn();
				joinColumns.add(joinColumn);
			}
			createJoinColumn(context, fieldOutline, idFieldOutline, joinColumn);

		}
	}

	protected void createJoinColumn(Mapping context, FieldOutline fieldOutline,
			FieldOutline idFieldOutline, JoinColumn joinColumn) {
		createJoinColumn$Name(context, fieldOutline, idFieldOutline, joinColumn);
	}

	protected void createJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline,
			JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming().getJoinColumn$Name(context,
					fieldOutline, idFieldOutline));
		}
	}

	// 1:1
	// M:1
	// 1:M
	// M:M
	@Override
	public void createJoinTable(Mapping context, FieldOutline fieldOutline,
			Collection<FieldOutline> sourceIdFieldOutlines,
			Collection<FieldOutline> targetIdFieldOutlines, JoinTable joinTable) {
		createJoinTable$Name(context, fieldOutline, joinTable);
		createJoinTable$JoinColumn(context, fieldOutline,
				sourceIdFieldOutlines, joinTable);
		createJoinTable$InverseJoinColumn(context, fieldOutline,
				targetIdFieldOutlines, joinTable);
	}

	protected void createJoinTable$Name(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {
		if (joinTable.getName() == null
				|| "##default".equals(joinTable.getName())) {
			joinTable.setName(context.getNaming().getJoinTable$Name(context,
					fieldOutline));
		}
	}

	protected void createJoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines, JoinTable joinTable) {

		final Iterator<JoinColumn> joinColumnIterator = new ArrayList<JoinColumn>(
				joinTable.getJoinColumn()).iterator();
		final Map<FieldOutline, JoinColumn> map = new IdentityHashMap<FieldOutline, JoinColumn>();
		for (FieldOutline idFieldOutline : idFieldOutlines) {
			final JoinColumn joinColumn;
			if (joinColumnIterator.hasNext()) {
				joinColumn = joinColumnIterator.next();
			} else {
				joinColumn = new JoinColumn();
				joinTable.getJoinColumn().add(joinColumn);
			}
			map.put(idFieldOutline, joinColumn);
			createJoinTable$JoinColumn(context, fieldOutline, idFieldOutline,
					joinColumn);
		}
	}

	protected void createJoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline,
			Collection<FieldOutline> idFieldOutlines, JoinTable joinTable) {

		final Iterator<JoinColumn> joinColumnIterator = new ArrayList<JoinColumn>(
				joinTable.getInverseJoinColumn()).iterator();
		final Map<FieldOutline, JoinColumn> map = new IdentityHashMap<FieldOutline, JoinColumn>();
		for (FieldOutline idFieldOutline : idFieldOutlines) {
			final JoinColumn joinColumn;
			if (joinColumnIterator.hasNext()) {
				joinColumn = joinColumnIterator.next();
			} else {
				joinColumn = new JoinColumn();
				joinTable.getInverseJoinColumn().add(joinColumn);
			}
			map.put(idFieldOutline, joinColumn);
			createJoinTable$InverseJoinColumn(context, fieldOutline,
					idFieldOutline, joinColumn);

		}
	}

	protected void createJoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline,
			JoinColumn joinColumn) {

		createJoinTable$JoinColumn$Name(context, fieldOutline, idFieldOutline,
				joinColumn);
		// createJoinTable$JoinColumn$ReferencedColumnName(context,
		// fieldOutline,
		// idFieldOutline, joinColumn);
	}

	protected void createJoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline,
			JoinColumn joinColumn) {

		createManyToOne$JoinTable$InverseJoinColumn$Name(context, fieldOutline,
				idFieldOutline, joinColumn);
	}

	protected void createJoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, FieldOutline idFieldOutline,
			JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getJoinTable$JoinColumn$Name(context, fieldOutline,
							idFieldOutline));
		}
	}

	protected void createJoinTable$JoinColumn$ReferencedColumnName(
			Mapping context, FieldOutline fieldOutline,
			FieldOutline idFieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setReferencedColumnName(context.getNaming()
					.getColumn$Name(context, idFieldOutline));
		}
	}

	public void createOrderColumn(Mapping context, FieldOutline fieldOutline,
			final OrderColumn orderColumn) {
		createOrderColumn$Name(context, fieldOutline, orderColumn);
	}

	protected void createOrderColumn$Name(Mapping context,
			FieldOutline fieldOutline, final OrderColumn orderColumn) {
		if (orderColumn.getName() == null
				|| "##default".equals(orderColumn.getName())) {
			orderColumn.setName(context.getNaming().getOrderColumn$Name(
					context, fieldOutline));
		}
	}

	protected void createManyToOne$JoinTable$InverseJoinColumn$Name(
			Mapping context, FieldOutline fieldOutline,
			FieldOutline idFieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getJoinTable$InverseJoinColumn$Name(context, fieldOutline,
							idFieldOutline));
		}
	}

	@Override
	public AssociationMapping createEmbeddedAssociationMapping(
			FieldOutline fieldOutline) {
		return new EmbeddedAssociationMappingWrapper(this, fieldOutline);
	}

}
