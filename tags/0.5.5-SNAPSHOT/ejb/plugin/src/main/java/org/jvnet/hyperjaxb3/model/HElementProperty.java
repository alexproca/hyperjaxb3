package org.jvnet.hyperjaxb3.model;

public interface HElementProperty {

	public HElement getElement();

	public HID getID();

	public HIDREF getIDREF();

	public HList getList();

	public HSchemaType getSchemaType();

	public HAttachmentRef getAttachmentRef();

	public HMimeType getMimeType();

	public HInlineBinaryData getInlineBinaryData();

	public HJavaTypeAdapter getJavaTypeAdapter();

	public HElementWrapper getElementWrapper();

}
