
package org.jvnet.hyperjaxb3.samples.pows.bindings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Items complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Items">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="quantity">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;maxExclusive value="100"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="USPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                   &lt;element ref="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}comment" minOccurs="0"/>
 *                   &lt;element name="shipDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="partNum" use="required" type="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}SKU" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Items", namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd", propOrder = {
    "item"
})
public class Items {

    protected List<Items.Item> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Items.Item }
     * 
     * 
     */
    public List<Items.Item> getItem() {
        if (item == null) {
            item = new ArrayList<Items.Item>();
        }
        return this.item;
    }

    public boolean isSetItem() {
        return ((this.item!= null)&&(!this.item.isEmpty()));
    }

    public void unsetItem() {
        this.item = null;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="productName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="quantity">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;maxExclusive value="100"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="USPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *         &lt;element ref="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}comment" minOccurs="0"/>
     *         &lt;element name="shipDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="partNum" use="required" type="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}SKU" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "productName",
        "quantity",
        "usPrice",
        "comment",
        "shipDate"
    })
    public static class Item {

        @XmlElement(required = true)
        protected String productName;
        protected int quantity;
        @XmlElement(name = "USPrice", required = true)
        protected BigDecimal usPrice;
        @XmlElement(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd")
        protected String comment;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar shipDate;
        @XmlAttribute(required = true)
        protected String partNum;

        /**
         * Gets the value of the productName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProductName() {
            return productName;
        }

        /**
         * Sets the value of the productName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductName(String value) {
            this.productName = value;
        }

        public boolean isSetProductName() {
            return (this.productName!= null);
        }

        /**
         * Gets the value of the quantity property.
         * 
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         */
        public void setQuantity(int value) {
            this.quantity = value;
        }

        public boolean isSetQuantity() {
            return true;
        }

        /**
         * Gets the value of the usPrice property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getUSPrice() {
            return usPrice;
        }

        /**
         * Sets the value of the usPrice property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setUSPrice(BigDecimal value) {
            this.usPrice = value;
        }

        public boolean isSetUSPrice() {
            return (this.usPrice!= null);
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
         * Gets the value of the shipDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getShipDate() {
            return shipDate;
        }

        /**
         * Sets the value of the shipDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setShipDate(XMLGregorianCalendar value) {
            this.shipDate = value;
        }

        public boolean isSetShipDate() {
            return (this.shipDate!= null);
        }

        /**
         * Gets the value of the partNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPartNum() {
            return partNum;
        }

        /**
         * Sets the value of the partNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPartNum(String value) {
            this.partNum = value;
        }

        public boolean isSetPartNum() {
            return (this.partNum!= null);
        }

    }

}