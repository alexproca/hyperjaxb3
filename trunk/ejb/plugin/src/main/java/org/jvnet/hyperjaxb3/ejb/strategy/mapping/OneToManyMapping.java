package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.JoinTable;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public class OneToManyMapping extends AssociationMapping implements
		FieldOutlineMapping<OneToMany> {

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
			FieldOutline fieldOutline, final OneToMany OneToMany) {
		OneToMany.setName(fieldOutline.getPropertyInfo().getName(true));
	}

	public void createOneToMany$TargetEntity(Mapping context,
			FieldOutline fieldOutline, final OneToMany OneToMany) {

		final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();

		final Collection<? extends CTypeInfo> types = propertyInfo.ref();

		assert types.size() == 1;

		final CTypeInfo type = types.iterator().next();

		assert type instanceof NType;

		final NType childClassInfo = (NType) type;

		OneToMany.setTargetEntity(childClassInfo.fullName());

	}

	public void createOneToMany$JoinTableOrJoinColumn(Mapping context,
			FieldOutline fieldOutline, OneToMany OneToMany) {

		if (OneToMany.getJoinColumn() != null
				&& !OneToMany.getJoinColumn().isEmpty()) {
			for (JoinColumn joinColumn : OneToMany.getJoinColumn()) {
				createOneToMany$JoinColumn(context, fieldOutline, joinColumn);
			}
		} else if (OneToMany.getJoinTable() != null) {
			final JoinTable joinTable = OneToMany.getJoinTable();
			createOneToMany$JoinTable(context, fieldOutline, joinTable);
		} else {
			final JoinColumn joinColumn = new JoinColumn();
			OneToMany.getJoinColumn().add(joinColumn);
			createOneToMany$JoinColumn(context, fieldOutline, joinColumn);
		}

	}

	public void createOneToMany$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		createOneToMany$JoinColumn$Name(context, fieldOutline, joinColumn);
	}

	public void createOneToMany$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming().getOneToManyJoinColumnName(
					fieldOutline));
		}
	}

	public void createOneToMany$JoinTable(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {
		createOneToMany$JoinTable$Name(context, fieldOutline, joinTable);
		createOneToMany$JoinTable$JoinColumn(context, fieldOutline, joinTable);
		createOneToMany$JoinTable$InverseJoinColumn(context, fieldOutline,
				joinTable);
	}

	public void createOneToMany$JoinTable$Name(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {
		if (joinTable.getName() == null
				|| "##default".equals(joinTable.getName())) {
			joinTable.setName(context.getNaming().getOneToManyJoinTableName(
					fieldOutline));
		}
	}

	public void createOneToMany$JoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {

		if (joinTable.getJoinColumn() == null
				|| joinTable.getJoinColumn().isEmpty()) {
			final JoinColumn joinColumn = new JoinColumn();
			joinTable.getJoinColumn().add(joinColumn);
			createOneToMany$JoinTable$JoinColumn(context, fieldOutline,
					joinColumn);
		} else {
			for (JoinColumn joinColumn : joinTable.getJoinColumn()) {
				createOneToMany$JoinTable$JoinColumn(context, fieldOutline,
						joinColumn);
			}
		}
	}

	public void createOneToMany$JoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {

		if (joinTable.getInverseJoinColumn() == null
				|| joinTable.getInverseJoinColumn().isEmpty()) {
			final JoinColumn joinColumn = new JoinColumn();
			joinTable.getInverseJoinColumn().add(joinColumn);
			createOneToMany$JoinTable$InverseJoinColumn(context, fieldOutline,
					joinColumn);
		} else {
			for (JoinColumn joinColumn : joinTable.getInverseJoinColumn()) {
				createOneToMany$JoinTable$InverseJoinColumn(context,
						fieldOutline, joinColumn);
			}
		}
	}

	public void createOneToMany$JoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {

		createOneToMany$JoinTable$JoinColumn$Name(context, fieldOutline,
				joinColumn);
	}

	public void createOneToMany$JoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {

		createOneToMany$JoinTable$InverseJoinColumn$Name(context, fieldOutline,
				joinColumn);
	}

	public void createOneToMany$JoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getOneToManyJoinTableJoinColumnName(fieldOutline));
		}
	}

	public void createOneToMany$JoinTable$InverseJoinColumn$Name(
			Mapping context, FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getOneToManyJoinTableInverseJoinColumnName(fieldOutline));
		}
	}
}
