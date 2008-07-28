package org.jvnet.hyperjaxb3.ejb.test.tests;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;

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

	@SuppressWarnings("unchecked")
	public void equals(Object object, EqualsBuilder equalsBuilder) {
		if (object == null) {
			equalsBuilder.appendSuper(false);
			return;
		}
		if (!getClass().isAssignableFrom(object.getClass())) {
			equalsBuilder.appendSuper(false);
			return;
		}
		if (this == object) {
			return;
		}
		final PrimitiveItem<T, V> rhs = (PrimitiveItem<T, V>) object;
		{
			T lhsValue;
			lhsValue = this.getValue();
			T rhsValue;
			rhsValue = rhs.getValue();
			equalsBuilder.append(lhsValue, rhsValue);
		}
	}

	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!getClass().isAssignableFrom(object.getClass())) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
		equals(object, equalsBuilder);
		return equalsBuilder.isEquals();
	}

	public void hashCode(HashCodeBuilder hashCodeBuilder) {
		final T theValue;
		theValue = this.getValue();
		hashCodeBuilder.append(theValue);
	}

	public int hashCode() {
		final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
		hashCode(hashCodeBuilder);
		return hashCodeBuilder.toHashCode();
	}

}
