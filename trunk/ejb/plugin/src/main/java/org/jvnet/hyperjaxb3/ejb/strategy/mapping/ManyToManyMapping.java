package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.java.xml.ns.persistence.orm.JoinTable;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class ManyToManyMapping extends AssociationMapping<ManyToMany> {

	public ManyToMany process(Mapping context, FieldOutline fieldOutline,
			Options options) {

		final ManyToMany manyToMany = context.getCustomizing().getManyToMany(
				fieldOutline);

		createManyToMany$Name(context, fieldOutline, manyToMany);
		createManyToMany$TargetEntity(context, fieldOutline, manyToMany);
		createManyToMany$JoinTable(context, fieldOutline, manyToMany);
		return manyToMany;
	}

	public void createManyToMany$Name(Mapping context,
			FieldOutline fieldOutline, final ManyToMany manyToMany) {
		manyToMany.setName(OutlineUtils.getPropertyName(fieldOutline));
	}

	public void createManyToMany$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final ManyToMany manyToMany) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);

		assert type != null;

		assert type instanceof CClass;

		final CClass childClassInfo = (CClass) type;

		manyToMany.setTargetEntity(context.getNaming().getEntityClass(
				fieldOutline.parent().parent(), childClassInfo.getType()));

	}

	public void createManyToMany$JoinTable(Mapping context,
			FieldOutline fieldOutline, ManyToMany manyToMany) {

		if (manyToMany.getJoinTable() != null) {
			createJoinTable(context, fieldOutline, manyToMany.getJoinTable());
		} else {
			final JoinTable joinTable = new JoinTable();
			manyToMany.setJoinTable(joinTable);
			createJoinTable(context, fieldOutline, manyToMany.getJoinTable());
		}

	}
}
