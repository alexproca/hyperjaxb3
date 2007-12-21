package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.strategy.model.AdaptTypeUse;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.xjc.model.CExternalLeafInfo;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.DurationAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.QNameAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDateTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsGDay;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsGMonth;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsGMonthDay;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsGYear;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsGYearMonth;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsTime;
import org.jvnet.hyperjaxb3.xsom.SimpleTypeVisitor;

import com.sun.tools.xjc.model.CBuiltinLeafInfo;
import com.sun.tools.xjc.model.CElementInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.CTypeInfo;
import com.sun.tools.xjc.model.TypeUse;
import com.sun.tools.xjc.model.TypeUseFactory;
import com.sun.xml.bind.v2.WellKnownNamespace;
import com.sun.xml.xsom.XSComponent;

public class AdaptBuiltinTypeUse implements AdaptTypeUse {

	protected Log logger = LogFactory.getLog(getClass());

	public TypeUse process(ProcessModel context, CPropertyInfo propertyInfo) {
		final CBuiltinLeafInfo type = getType(context, propertyInfo);
		final XSComponent schemaComponent = propertyInfo.getSchemaComponent();

		if (schemaComponent != null) {
			final SimpleTypeVisitor simpleTypeVisitor = new SimpleTypeVisitor();
			schemaComponent.visit(simpleTypeVisitor);
			final QName typeName = simpleTypeVisitor.getTypeName();
			final TypeUse createPropertyInfos = adapters.get(new PropertyType(
					type, typeName));
			if (createPropertyInfos != null) {
				return createPropertyInfos;
			} else {
				return adapters.get(new PropertyType(type));
			}

		} else {
			return adapters.get(new PropertyType(type));
		}
	}

	public CBuiltinLeafInfo getType(ProcessModel context,
			CPropertyInfo propertyInfo) {
		final CTypeInfo type = propertyInfo.ref().iterator().next();

		if (type instanceof CBuiltinLeafInfo) {
			return (CBuiltinLeafInfo) type;
		} else if (type instanceof CElementInfo) {
			final CElementInfo elementInfo = (CElementInfo) type;
			return (CBuiltinLeafInfo) elementInfo.getContentType();
		} else {
			throw new AssertionError("Unexpected type.");
		}
	}

	private Map<PropertyType, TypeUse> adapters = new HashMap<PropertyType, TypeUse>();
	{

		adapters.put(new PropertyType(CBuiltinLeafInfo.BASE64_BYTE_ARRAY),
				CBuiltinLeafInfo.BASE64_BYTE_ARRAY);
		adapters.put(new PropertyType(CBuiltinLeafInfo.BIG_DECIMAL),
				CBuiltinLeafInfo.BIG_DECIMAL);
		adapters.put(new PropertyType(CBuiltinLeafInfo.BIG_INTEGER),
				CBuiltinLeafInfo.BIG_INTEGER);
		adapters.put(new PropertyType(CBuiltinLeafInfo.BOOLEAN),
				CBuiltinLeafInfo.BOOLEAN);
		adapters.put(new PropertyType(CBuiltinLeafInfo.BYTE),
				CBuiltinLeafInfo.BYTE);
		adapters.put(new PropertyType(CBuiltinLeafInfo.DOUBLE),
				CBuiltinLeafInfo.DOUBLE);
		adapters.put(new PropertyType(CBuiltinLeafInfo.FLOAT),
				CBuiltinLeafInfo.FLOAT);
		adapters.put(new PropertyType(CBuiltinLeafInfo.INT),
				CBuiltinLeafInfo.INT);
		adapters.put(new PropertyType(CBuiltinLeafInfo.LONG),
				CBuiltinLeafInfo.LONG);
		adapters.put(new PropertyType(CBuiltinLeafInfo.SHORT),
				CBuiltinLeafInfo.SHORT);
		adapters.put(new PropertyType(CBuiltinLeafInfo.STRING),
				CBuiltinLeafInfo.STRING);

		adapters.put(

		new PropertyType(CBuiltinLeafInfo.QNAME),

		TypeUseFactory.adapt(CBuiltinLeafInfo.STRING, QNameAsString.class,
				false));

		adapters.put(new PropertyType(CBuiltinLeafInfo.DURATION),
				TypeUseFactory.adapt(CBuiltinLeafInfo.STRING,
						DurationAsString.class, false));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR),

		new CExternalLeafInfo(Date.class, "dateTime",
				XMLGregorianCalendarAsDateTime.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "dateTime")),

		new CExternalLeafInfo(Date.class, "dateTime",
				XMLGregorianCalendarAsDateTime.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "date")), new CExternalLeafInfo(
				Date.class, "date", XMLGregorianCalendarAsDate.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "time")), new CExternalLeafInfo(
				Date.class, "time", XMLGregorianCalendarAsTime.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "gYearMonth")),
				new CExternalLeafInfo(Date.class, "gYearMonth",
						XMLGregorianCalendarAsGYearMonth.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "gYear")),
				new CExternalLeafInfo(Date.class, "gYear",
						XMLGregorianCalendarAsGYear.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "gMonthDay")),
				new CExternalLeafInfo(Date.class, "gMonthDay",
						XMLGregorianCalendarAsGMonthDay.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "gDay")), new CExternalLeafInfo(
				Date.class, "gDay", XMLGregorianCalendarAsGDay.class));

		adapters.put(new PropertyType(CBuiltinLeafInfo.CALENDAR, new QName(
				WellKnownNamespace.XML_SCHEMA, "gMonth")),
				new CExternalLeafInfo(Date.class, "gMonth",
						XMLGregorianCalendarAsGMonth.class));
	}

	private class PropertyType {
		private TypeUse typeUse;

		private QName typeName;

		public PropertyType(TypeUse typeUse) {
			super();
			this.typeUse = typeUse;
			this.typeName = null;
		}

		public PropertyType(TypeUse typeUse, QName typeName) {
			super();
			this.typeUse = typeUse;
			this.typeName = typeName;
		}

		public PropertyType(TypeUse typeUse, String typeName) {
			super();
			this.typeUse = typeUse;
			this.typeName = new QName(WellKnownNamespace.XML_SCHEMA, typeName);
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result
					+ ((typeName == null) ? 0 : typeName.hashCode());
			result = PRIME * result
					+ ((typeUse == null) ? 0 : typeUse.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final PropertyType other = (PropertyType) obj;
			if (typeName == null) {
				if (other.typeName != null)
					return false;
			} else if (!typeName.equals(other.typeName))
				return false;
			if (typeUse == null) {
				if (other.typeUse != null)
					return false;
			} else if (!typeUse.equals(other.typeUse))
				return false;
			return true;
		}
	}
}
