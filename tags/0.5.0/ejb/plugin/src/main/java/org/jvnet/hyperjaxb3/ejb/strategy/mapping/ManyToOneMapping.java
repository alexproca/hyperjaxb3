package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public class ManyToOneMapping extends AssociationMapping<ManyToOne> {

	public ManyToOne process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final ManyToOne manyToOne = context.getCustomizing().getManyToOne(
				fieldOutline);

		createManyToOne$Name(context, fieldOutline, manyToOne);
		createManyToOne$TargetEntity(context, fieldOutline, manyToOne);
		createManyToOne$JoinTableOrJoinColumn(context, fieldOutline, manyToOne);
		return manyToOne;
	}

	public void createManyToOne$Name(Mapping context,
			FieldOutline fieldOutline, final ManyToOne manyToOne) {
		manyToOne.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createManyToOne$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final ManyToOne manyToOne) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof NType;

		final NType childClassInfo = (NType) type;

		manyToOne.setTargetEntity(childClassInfo.fullName());

	}

	public void createManyToOne$JoinTableOrJoinColumn(Mapping context,
			FieldOutline fieldOutline, ManyToOne manyToOne) {

		if (manyToOne.getJoinColumn() != null
				&& !manyToOne.getJoinColumn().isEmpty()) {
			createJoinColumns(context, fieldOutline, manyToOne.getJoinColumn());
		} else if (manyToOne.getJoinTable() != null) {
			createJoinTable(context, fieldOutline, manyToOne.getJoinTable());
		} else {
			final JoinColumn joinColumn = new JoinColumn();
			manyToOne.getJoinColumn().add(joinColumn);
			createJoinColumns(context, fieldOutline, manyToOne.getJoinColumn());
		}

	}

}