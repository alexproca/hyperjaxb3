package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class OneToOneMapping extends AssociationMapping<OneToOne> {

	public OneToOne process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final OneToOne oneToOne = context.getCustomizing().getOneToOne(
				fieldOutline);
		createOneToOne$Name(context, fieldOutline, oneToOne);
		createOneToOne$TargetEntity(context, fieldOutline, oneToOne);
		createOneToOne$JoinTableOrJoinColumnOrPrimaryKeyJoinColumn(context,
				fieldOutline, oneToOne);
		return oneToOne;
	}

	public void createOneToOne$Name(Mapping context, FieldOutline fieldOutline,
			final OneToOne oneToOne) {
		oneToOne.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createOneToOne$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final OneToOne oneToOne) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		oneToOne.setTargetEntity(context.getNaming().getEntityClass(
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createOneToOne$JoinTableOrJoinColumnOrPrimaryKeyJoinColumn(
			Mapping context, FieldOutline fieldOutline, OneToOne oneToOne) {
		if (!oneToOne.getPrimaryKeyJoinColumn().isEmpty()) {
			createPrimaryKeyJoinColumns(context, fieldOutline, oneToOne
					.getPrimaryKeyJoinColumn());
		} else if (!oneToOne.getJoinColumn().isEmpty()) {
			createJoinColumns(context, fieldOutline, oneToOne.getJoinColumn());
		} else if (oneToOne.getJoinTable() != null) {
			createJoinTable(context, fieldOutline, oneToOne.getJoinTable());
		} else {
			final JoinColumn joinColumn = new JoinColumn();
			oneToOne.getJoinColumn().add(joinColumn);
			createJoinColumns(context, fieldOutline, oneToOne.getJoinColumn());
		}

	}

}
