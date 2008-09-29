package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public class OneToManyMapping extends AssociationMapping<OneToMany> {

	public OneToMany process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final OneToMany oneToMany = context.getCustomizing().getOneToMany(
				fieldOutline);

		createOneToMany$Name(context, fieldOutline, oneToMany);
		createOneToMany$TargetEntity(context, fieldOutline, oneToMany);
		createOneToMany$JoinTableOrJoinColumn(context, fieldOutline, oneToMany);
		return oneToMany;
	}

	public void createOneToMany$Name(Mapping context,
			FieldOutline fieldOutline, final OneToMany oneToMany) {
		oneToMany.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createOneToMany$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final OneToMany oneToMany) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof NType;

		final NType childClassInfo = (NType) type;

		oneToMany.setTargetEntity(childClassInfo.fullName());

	}

	public void createOneToMany$JoinTableOrJoinColumn(Mapping context,
			FieldOutline fieldOutline, OneToMany oneToMany) {

		if (!oneToMany.getJoinColumn().isEmpty()) {
			createJoinColumns(context, fieldOutline, oneToMany.getJoinColumn());
		} else if (oneToMany.getJoinTable() != null) {
			createJoinTable(context, fieldOutline, oneToMany.getJoinTable());
		} else {
			final JoinColumn joinColumn = new JoinColumn();
			oneToMany.getJoinColumn().add(joinColumn);
			createJoinColumns(context, fieldOutline, oneToMany.getJoinColumn());
		}

	}
}
