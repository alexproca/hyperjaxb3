package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.util.Collection;

import org.jvnet.hyperjaxb3.xjc.model.CTypeInfoUtils;

import com.sun.java.xml.ns.persistence.orm.ElementCollection;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.CClass;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.outline.FieldOutline;

public class ElementCollectionMapping implements
		FieldOutlineMapping<ElementCollection> {

	public ElementCollection process(Mapping context,
			FieldOutline fieldOutline, Options options) {

		final ElementCollection elementCollection = context.getCustomizing()
				.getElementCollection(fieldOutline);
		createElementCollection$Name(context, fieldOutline, elementCollection);
		// createOneToMany$OrderColumn(context, fieldOutline,
		// elementCollection);
		// createOneToMany$TargetEntity(context, fieldOutline,
		// elementCollection);
		// createOneToMany$JoinTableOrJoinColumn(context, fieldOutline,
		// elementCollection);
		return elementCollection;
	}

	public void createElementCollection$Name(Mapping context,
			FieldOutline fieldOutline, final ElementCollection target) {
		target.setName(context.getNaming().getPropertyName(context,
				fieldOutline));
	}

	// public void createOneToMany$OrderColumn(Mapping context,
	// FieldOutline fieldOutline, final OneToMany source) {
	// if (source.getOrderColumn() != null) {
	// context.getAssociationMapping().createOrderColumn(context,
	// fieldOutline, source.getOrderColumn());
	// }
	// }
	//
	// public void createOneToMany$TargetEntity(Mapping context,
	// FieldOutline fieldOutline, final OneToMany oneToMany) {
	//
	// final CPropertyInfo propertyInfo = fieldOutline.getPropertyInfo();
	//
	// final Collection<? extends CTypeInfo> types = propertyInfo.ref();
	//
	// final CTypeInfo type = CTypeInfoUtils.getCommonBaseTypeInfo(types);
	//
	// assert type != null;
	//
	// assert type instanceof CClass;
	//
	// final CClass childClassInfo = (CClass) type;
	//
	// oneToMany.setTargetEntity(context.getNaming().getEntityClass(
	// fieldOutline.parent().parent(), childClassInfo.getType()));
	//
	// }
	//
	// public void createOneToMany$JoinTableOrJoinColumn(Mapping context,
	// FieldOutline fieldOutline, OneToMany oneToMany) {
	//
	// if (!oneToMany.getJoinColumn().isEmpty()) {
	// final Collection<FieldOutline> idFieldsOutline = context
	// .getAssociationMapping().getSourceIdFieldsOutline(context,
	// fieldOutline);
	// if (idFieldsOutline.isEmpty()) {
	// oneToMany.getJoinColumn().clear();
	// }
	// context.getAssociationMapping().createJoinColumns(context,
	// fieldOutline, idFieldsOutline, oneToMany.getJoinColumn());
	// } else if (oneToMany.getJoinTable() != null) {
	// final Collection<FieldOutline> sourceIdFieldOutlines = context
	// .getAssociationMapping().getSourceIdFieldsOutline(context,
	// fieldOutline);
	// final Collection<FieldOutline> targetIdFieldOutlines = context
	// .getAssociationMapping().getTargetIdFieldsOutline(context,
	// fieldOutline);
	//
	// if (sourceIdFieldOutlines.isEmpty()) {
	// oneToMany.setJoinTable(null);
	// } else {
	// context.getAssociationMapping().createJoinTable(context,
	// fieldOutline, sourceIdFieldOutlines,
	// targetIdFieldOutlines, oneToMany.getJoinTable());
	// }
	// } else {
	// final Collection<FieldOutline> idFieldsOutline = context
	// .getAssociationMapping().getSourceIdFieldsOutline(context,
	// fieldOutline);
	// if (idFieldsOutline.isEmpty()) {
	// oneToMany.getJoinColumn().clear();
	// }
	// context.getAssociationMapping().createJoinColumns(context,
	// fieldOutline, idFieldsOutline, oneToMany.getJoinColumn());
	// }
	//
	// }
}
