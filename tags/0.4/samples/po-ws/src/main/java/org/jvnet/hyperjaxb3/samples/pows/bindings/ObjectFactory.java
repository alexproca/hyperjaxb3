
package org.jvnet.hyperjaxb3.samples.pows.bindings;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jvnet.hyperjaxb3.samples.pows.bindings package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateResponse_QNAME = new QName("urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", "updateResponse");
    private final static QName _QueryRequest_QNAME = new QName("urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", "queryRequest");
    private final static QName _UpdateRequest_QNAME = new QName("urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", "updateRequest");
    private final static QName _QueryResponse_QNAME = new QName("urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", "queryResponse");
    private final static QName _Comment_QNAME = new QName("urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd", "comment");
    private final static QName _PurchaseOrder_QNAME = new QName("urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd", "purchaseOrder");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jvnet.hyperjaxb3.samples.pows.bindings
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateRequestType }
     * 
     */
    public UpdateRequestType createUpdateRequestType() {
        return new UpdateRequestType();
    }

    /**
     * Create an instance of {@link UpdateResponseType }
     * 
     */
    public UpdateResponseType createUpdateResponseType() {
        return new UpdateResponseType();
    }

    /**
     * Create an instance of {@link QueryResponseType }
     * 
     */
    public QueryResponseType createQueryResponseType() {
        return new QueryResponseType();
    }

    /**
     * Create an instance of {@link Items.Item }
     * 
     */
    public Items.Item createItemsItem() {
        return new Items.Item();
    }

    /**
     * Create an instance of {@link USAddress }
     * 
     */
    public USAddress createUSAddress() {
        return new USAddress();
    }

    /**
     * Create an instance of {@link QueryRequestType }
     * 
     */
    public QueryRequestType createQueryRequestType() {
        return new QueryRequestType();
    }

    /**
     * Create an instance of {@link PurchaseOrderType }
     * 
     */
    public PurchaseOrderType createPurchaseOrderType() {
        return new PurchaseOrderType();
    }

    /**
     * Create an instance of {@link Items }
     * 
     */
    public Items createItems() {
        return new Items();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", name = "updateResponse")
    public JAXBElement<UpdateResponseType> createUpdateResponse(UpdateResponseType value) {
        return new JAXBElement<UpdateResponseType>(_UpdateResponse_QNAME, UpdateResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", name = "queryRequest")
    public JAXBElement<QueryRequestType> createQueryRequest(QueryRequestType value) {
        return new JAXBElement<QueryRequestType>(_QueryRequest_QNAME, QueryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", name = "updateRequest")
    public JAXBElement<UpdateRequestType> createUpdateRequest(UpdateRequestType value) {
        return new JAXBElement<UpdateRequestType>(_UpdateRequest_QNAME, UpdateRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", name = "queryResponse")
    public JAXBElement<QueryResponseType> createQueryResponse(QueryResponseType value) {
        return new JAXBElement<QueryResponseType>(_QueryResponse_QNAME, QueryResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd", name = "comment")
    public JAXBElement<String> createComment(String value) {
        return new JAXBElement<String>(_Comment_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseOrderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:purchaseorder.xsd", name = "purchaseOrder")
    public JAXBElement<PurchaseOrderType> createPurchaseOrder(PurchaseOrderType value) {
        return new JAXBElement<PurchaseOrderType>(_PurchaseOrder_QNAME, PurchaseOrderType.class, null, value);
    }

}
