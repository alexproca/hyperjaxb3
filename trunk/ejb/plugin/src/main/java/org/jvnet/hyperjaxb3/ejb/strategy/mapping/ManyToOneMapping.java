package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.JoinTable;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.nav.NType;
import com.sun.tools.xjc.outline.FieldOutline;

public class ManyToOneMapping extends AssociationMapping implements
		FieldOutlineMapping<ManyToOne> {

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
		manyToOne.setName(fieldOutline.getPropertyInfo().getName(true));
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
			for (JoinColumn joinColumn : manyToOne.getJoinColumn()) {
				createManyToOne$JoinColumn(context, fieldOutline, joinColumn);
			}
		} else if (manyToOne.getJoinTable() != null) {
			final JoinTable joinTable = manyToOne.getJoinTable();
			createManyToOne$JoinTable(context, fieldOutline, joinTable);
		} else {
			final JoinColumn joinColumn = new JoinColumn();
			manyToOne.getJoinColumn().add(joinColumn);
			createManyToOne$JoinColumn(context, fieldOutline, joinColumn);
		}

	}

	public void createManyToOne$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		createManyToOne$JoinColumn$Name(context, fieldOutline, joinColumn);
	}

	public void createManyToOne$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming().getManyToOneJoinColumnName(
					fieldOutline));
		}
	}

	public void createManyToOne$JoinTable(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {
		createManyToOne$JoinTable$Name(context, fieldOutline, joinTable);
		createManyToOne$JoinTable$JoinColumn(context, fieldOutline, joinTable);
		createManyToOne$JoinTable$InverseJoinColumn(context, fieldOutline,
				joinTable);
	}

	public void createManyToOne$JoinTable$Name(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {
		if (joinTable.getName() == null
				|| "##default".equals(joinTable.getName())) {
			joinTable.setName(context.getNaming().getManyToOneJoinTableName(
					fieldOutline));
		}
	}

	public void createManyToOne$JoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {

		if (joinTable.getJoinColumn() == null
				|| joinTable.getJoinColumn().isEmpty()) {
			final JoinColumn joinColumn = new JoinColumn();
			joinTable.getJoinColumn().add(joinColumn);
			createManyToOne$JoinTable$JoinColumn(context, fieldOutline,
					joinColumn);
		} else {
			for (JoinColumn joinColumn : joinTable.getJoinColumn()) {
				createManyToOne$JoinTable$JoinColumn(context, fieldOutline,
						joinColumn);
			}
		}
	}

	public void createManyToOne$JoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {

		if (joinTable.getInverseJoinColumn() == null
				|| joinTable.getInverseJoinColumn().isEmpty()) {
			final JoinColumn joinColumn = new JoinColumn();
			joinTable.getInverseJoinColumn().add(joinColumn);
			createManyToOne$JoinTable$InverseJoinColumn(context, fieldOutline,
					joinColumn);
		} else {
			for (JoinColumn joinColumn : joinTable.getInverseJoinColumn()) {
				createManyToOne$JoinTable$InverseJoinColumn(context,
						fieldOutline, joinColumn);
			}
		}
	}

	public void createManyToOne$JoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {

		createManyToOne$JoinTable$JoinColumn$Name(context, fieldOutline,
				joinColumn);
	}

	public void createManyToOne$JoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {

		createManyToOne$JoinTable$InverseJoinColumn$Name(context, fieldOutline,
				joinColumn);
	}

	public void createManyToOne$JoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getManyToOneJoinTableJoinColumnName(fieldOutline));
		}
	}

	public void createManyToOne$JoinTable$InverseJoinColumn$Name(
			Mapping context, FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getManyToOneJoinTableInverseJoinColumnName(fieldOutline));
		}
	}
}
