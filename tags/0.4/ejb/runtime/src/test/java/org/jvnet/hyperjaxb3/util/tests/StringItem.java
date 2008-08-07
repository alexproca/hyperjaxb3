package org.jvnet.hyperjaxb3.util.tests;

import org.jvnet.hyperjaxb3.item.Item;

public class StringItem implements Item<String> {

	private String value;

	public void setItem(String value) {
		this.value = value;
	}

	public String getItem() {
		return this.value;
	}

}
