package org.jvnet.hyperjaxb3.model;

import org.w3c.dom.Element;
import org.xml.sax.Locator;

public interface HCustomization {

	public Element getElement();

	public Locator getLocator();

	public boolean isAcknowledged();

	public void markAcknowledged();

}
