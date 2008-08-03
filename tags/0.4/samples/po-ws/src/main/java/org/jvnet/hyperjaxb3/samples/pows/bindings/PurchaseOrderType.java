
package org.jvnet.hyperjaxb3.samples.pows.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PurchaseOrderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseOrderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shipTo" type="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}USAddress"/>
 *         &lt;element name="billTo" type="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}USAddress"/>
 *         &lt;element ref="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}comment" minOccurs="0"/>
 *         &lt;element name="items" type="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}Items"/>
 *       &lt;/sequence>
 *       &lt;attribute name="orderDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseOrderType", namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd", propOrder = {
    "shipTo",
    "billTo",
    "comment",
    "items"
})
public class PurchaseOrderType {

    @XmlElement(required = true)
    protected USAddress shipTo;
    @XmlElement(required = true)
    protected USAddress billTo;
    @XmlElement(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd")
    protected String comment;
    @XmlElement(required = true)
    protected Items items;
    @XmlAttribute
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar orderDate;

    /**
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link USAddress }
     *     
     */
    public USAddress getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link USAddress }
     *     
     */
    public void setShipTo(USAddress value) {
        this.shipTo = value;
    }

    public boolean isSetShipTo() {
        return (this.shipTo!= null);
    }

    /**
     * Gets the value of the billTo property.
     * 
     * @return
     *     possible object is
     *     {@link USAddress }
     *     
     */
    public USAddress getBillTo() {
        return billTo;
    }

    /**
     * Sets the value of the billTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link USAddress }
     *     
     */
    public void setBillTo(USAddress value) {
        this.billTo = value;
    }

    public boolean isSetBillTo() {
        return (this.billTo!= null);
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    public boolean isSetComment() {
        return (this.comment!= null);
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public Items getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setItems(Items value) {
        this.items = value;
    }

    public boolean isSetItems() {
        return (this.items!= null);
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

    public boolean isSetOrderDate() {
        return (this.orderDate!= null);
    }

}
