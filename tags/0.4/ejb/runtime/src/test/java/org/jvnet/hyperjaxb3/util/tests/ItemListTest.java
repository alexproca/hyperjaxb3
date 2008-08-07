package org.jvnet.hyperjaxb3.util.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.jvnet.hyperjaxb3.item.DefaultItemList;

public class ItemListTest extends TestCase {

	public void testItemList() {

		final List<StringItem> items = new ArrayList<StringItem>();

		final List<String> strings = new DefaultItemList<String, StringItem>(
				items, StringItem.class);

		strings.add("a");
		strings.add("b");

		Assert.assertEquals("Wrong number of items.", 2, items.size());
		final Iterator<StringItem> iterator = items.iterator();
		Assert.assertEquals("Wrong value.", "a", iterator.next().getItem());
		Assert.assertEquals("Wrong value.", "b", iterator.next().getItem());
	}
}
