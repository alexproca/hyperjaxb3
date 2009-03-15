package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.List;

import com.sun.java.xml.ns.persistence.orm.JoinColumn;
import com.sun.java.xml.ns.persistence.orm.JoinTable;
import com.sun.java.xml.ns.persistence.orm.PrimaryKeyJoinColumn;
import com.sun.tools.xjc.outline.FieldOutline;

public abstract class AssociationMapping<T> implements FieldOutlineMapping<T> {

	public void createPrimaryKeyJoinColumns(Mapping context,
			FieldOutline fieldOutline,
			List<PrimaryKeyJoinColumn> primaryKeyJoinColumns) {
		for (PrimaryKeyJoinColumn primaryKeyJoinColumn : primaryKeyJoinColumns) {
			createPrimaryKeyJoinColumn(context, fieldOutline,
					primaryKeyJoinColumn);
		}
	}

	public void createPrimaryKeyJoinColumn(Mapping context,
			FieldOutline fieldOutline, PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		createPrimaryKeyJoinColumn$Name(context, fieldOutline,
				primaryKeyJoinColumn);
	}

	public void createPrimaryKeyJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, PrimaryKeyJoinColumn primaryKeyJoinColumn) {
		if (primaryKeyJoinColumn.getName() == null
				|| "##default".equals(primaryKeyJoinColumn.getName())) {
			primaryKeyJoinColumn.setName(context.getNaming()
					.getJoinColumn$Name(context, fieldOutline));
		}
	}

	public void createJoinColumns(Mapping context, FieldOutline fieldOutline,
			List<JoinColumn> joinColumns) {
		for (JoinColumn joinColumn : joinColumns) {
			createJoinColumn(context, fieldOutline, joinColumn);
		}
	}

	public void createJoinColumn(Mapping context, FieldOutline fieldOutline,
			JoinColumn joinColumn) {
		createJoinColumn$Name(context, fieldOutline, joinColumn);
	}

	public void createJoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming().getJoinColumn$Name(context,
					fieldOutline));
		}
	}

	public void createJoinTable(Mapping context, FieldOutline fieldOutline,
			JoinTable joinTable) {
		createJoinTable$Name(context, fieldOutline, joinTable);
		createJoinTable$JoinColumn(context, fieldOutline, joinTable);
		createJoinTable$InverseJoinColumn(context, fieldOutline, joinTable);
	}

	public void createJoinTable$Name(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {
		if (joinTable.getName() == null
				|| "##default".equals(joinTable.getName())) {
			joinTable.setName(context.getNaming().getJoinTable$Name(context,
					fieldOutline));
		}
	}

	public void createJoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {

		if (joinTable.getJoinColumn() == null
				|| joinTable.getJoinColumn().isEmpty()) {
			final JoinColumn joinColumn = new JoinColumn();
			joinTable.getJoinColumn().add(joinColumn);
			createJoinTable$JoinColumn(context, fieldOutline, joinColumn);
		} else {
			for (JoinColumn joinColumn : joinTable.getJoinColumn()) {
				createJoinTable$JoinColumn(context, fieldOutline, joinColumn);
			}
		}
	}

	public void createJoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinTable joinTable) {

		if (joinTable.getInverseJoinColumn() == null
				|| joinTable.getInverseJoinColumn().isEmpty()) {
			final JoinColumn joinColumn = new JoinColumn();
			joinTable.getInverseJoinColumn().add(joinColumn);
			createJoinTable$InverseJoinColumn(context, fieldOutline, joinColumn);
		} else {
			for (JoinColumn joinColumn : joinTable.getInverseJoinColumn()) {
				createJoinTable$InverseJoinColumn(context, fieldOutline,
						joinColumn);
			}
		}
	}

	public void createJoinTable$JoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {

		createJoinTable$JoinColumn$Name(context, fieldOutline, joinColumn);
	}

	public void createJoinTable$InverseJoinColumn(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {

		createManyToOne$JoinTable$InverseJoinColumn$Name(context, fieldOutline,
				joinColumn);
	}

	public void createJoinTable$JoinColumn$Name(Mapping context,
			FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn.setName(context.getNaming()
					.getJoinTable$JoinColumn$Name(context, fieldOutline));
		}
	}

	public void createManyToOne$JoinTable$InverseJoinColumn$Name(
			Mapping context, FieldOutline fieldOutline, JoinColumn joinColumn) {
		if (joinColumn.getName() == null
				|| "##default".equals(joinColumn.getName())) {
			joinColumn
					.setName(context.getNaming()
							.getJoinTable$InverseJoinColumn$Name(context,
									fieldOutline));
		}
	}

}
