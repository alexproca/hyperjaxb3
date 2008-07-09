
package org.jvnet.hyperjaxb3.samples.pows.bindings;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for queryResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="queryResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd}purchaseOrder" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryResponseType", propOrder = {
    "purchaseOrder"
})
public class QueryResponseType {

    @XmlElement(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd")
    protected List<PurchaseOrderType> purchaseOrder;

    /**
     * Gets the value of the purchaseOrder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the purchaseOrder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPurchaseOrder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PurchaseOrderType }
     * 
     * 
     */
    public List<PurchaseOrderType> getPurchaseOrder() {
        if (purchaseOrder == null) {
            purchaseOrder = new ArrayList<PurchaseOrderType>();
        }
        return this.purchaseOrder;
    }

    public boolean isSetPurchaseOrder() {
        return ((this.purchaseOrder!= null)&&(!this.purchaseOrder.isEmpty()));
    }

    public void unsetPurchaseOrder() {
        this.purchaseOrder = null;
    }

}
