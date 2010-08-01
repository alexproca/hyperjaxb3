package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class OneToManyMapping extends AssociationMapping<OneToMany> {

	public OneToMany process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final OneToMany oneToMany = context.getCustomizing().getOneToMany(
				fieldOutline);
		createOneToMany$Name(context, fieldOutline, oneToMany);
		createOneToMany$OrderColumn(context, fieldOutline, oneToMany);
		createOneToMany$TargetEntity(context, fieldOutline, oneToMany);
		createOneToMany$JoinTableOrJoinColumn(context, fieldOutline, oneToMany);
		return oneToMany;
	}

	public void createOneToMany$Name(Mapping context,
			FieldOutline fieldOutline, final OneToMany oneToMany) {
		oneToMany.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createOneToMany$OrderColumn(Mapping context,
			FieldOutline fieldOutline, final OneToMany source) {
		if (source.getOrderColumn() != null) {
			createOrderColumn(context, fieldOutline, source.getOrderColumn());
		}
	}

	public void createOneToMany$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final OneToMany oneToMany) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);

		assert type != null;

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		oneToMany.setTargetEntity(context.getNaming().getEntityClass(
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createOneToMany$JoinTableOrJoinColumn(Mapping context,
			FieldOutline fieldOutline, OneToMany oneToMany) {

		if (!oneToMany.getJoinColumn().isEmpty()) {
			final Collection<FieldOutline> idFieldsOutline = getSourceIdFieldsOutline(
					context, fieldOutline);
			createJoinColumns(context, fieldOutline, idFieldsOutline,
					oneToMany.getJoinColumn());
		} else if (oneToMany.getJoinTable() != null) {
			final Collection<FieldOutline> sourceIdFieldOutlines = getSourceIdFieldsOutline(
					context, fieldOutline);
			final Collection<FieldOutline> targetIdFieldOutlines = getTargetIdFieldsOutline(
					context, fieldOutline);

			createJoinTable(context, fieldOutline, sourceIdFieldOutlines,
					targetIdFieldOutlines, oneToMany.getJoinTable());
		} else {
			final Collection<FieldOutline> idFieldsOutline = getSourceIdFieldsOutline(
					context, fieldOutline);
			// final JoinColumn joinColumn = new JoinColumn();
			// oneToMany.getJoinColumn().add(joinColumn);
			createJoinColumns(context, fieldOutline, idFieldsOutline,
					oneToMany.getJoinColumn());
		}

	}
}
