
package org.jvnet.hyperjaxb3.samples.pows.bindings;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.1-02/02/2007 09:55 AM(vivekp)-FCS
 * Generated source version: 2.1
 * 
 */
@WebService(name = "OrderServicePortType", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface OrderServicePortType {


    /**
     * 
     * @param updateRequest
     * @return
     *     returns org.jvnet.hyperjaxb3.samples.pows.bindings.UpdateResponseType
     */
    @WebMethod(action = "updateOrder")
    @WebResult(name = "updateResponse", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "updateResponse")
    public UpdateResponseType update(
        @WebParam(name = "updateRequest", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "updateRequest")
        UpdateRequestType updateRequest);

    /**
     * 
     * @param queryRequest
     * @return
     *     returns org.jvnet.hyperjaxb3.samples.pows.bindings.QueryResponseType
     */
    @WebMethod(action = "queryOrder")
    @WebResult(name = "queryResponse", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "queryResponse")
    public QueryResponseType query(
        @WebParam(name = "queryRequest", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "queryRequest")
        QueryRequestType queryRequest);

}
