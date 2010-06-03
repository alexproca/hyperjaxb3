package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;

@MappedSuperclass
public abstract class PrimitiveItem<T, V> implements Equals, HashCode, Item<V> {

	private T value;

	@Basic
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator,
			Object object, EqualsStrategy strategy) {
		if (!(object instanceof PrimitiveItem)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final PrimitiveItem that = ((PrimitiveItem) object);
		{
			Object lhsValue;
			lhsValue = this.getValue();
			Object rhsValue;
			rhsValue = that.getValue();
			if (!strategy.equals(LocatorUtils.field(thisLocator, "value",
					lhsValue), LocatorUtils.field(thatLocator, "value",
					rhsValue), lhsValue, rhsValue)) {
				return false;
			}
		}
		return true;
	}

	public boolean equals(Object object) {
		final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
		return equals(null, null, object, strategy);
	}

	@Override
	public int hashCode(ObjectLocator locator, HashCodeStrategy hashCodeStrategy) {

		final T theValue;
		theValue = this.getValue();
		return hashCodeStrategy.hashCode(LocatorUtils.field(locator, "value",
				theValue), 0, theValue);
	}

	public int hashCode() {
		return hashCode(null, JAXBHashCodeStrategy.INSTANCE);
	}

}
