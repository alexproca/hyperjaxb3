/*
 * OrderServicePortTypeImpl.java
 *
 * Created on November 30, 2006, 12:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jvnet.hyperjaxb3.samples.pows.server;

import java.math.BigInteger;
import java.util.Iterator;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.samples.pows.bindings.PurchaseOrderType;
import org.jvnet.hyperjaxb3.samples.pows.bindings.OrderServicePortType;
import org.jvnet.hyperjaxb3.samples.pows.bindings.QueryRequestType;
import org.jvnet.hyperjaxb3.samples.pows.bindings.QueryResponseType;
import org.jvnet.hyperjaxb3.samples.pows.bindings.UpdateRequestType;
import org.jvnet.hyperjaxb3.samples.pows.bindings.UpdateResponseType;
import org.jvnet.hyperjaxb3.samples.pows.common.BindingUtility;

import javax.persistence.Query;

import java.util.List;
import javax.persistence.EntityTransaction;


/**
 * Implementation of OrderServicePortType.
 *
 * @author <a href="mailto:farrukh@wellfleetsoftware.com">Farrukh S. Najmi</a>
 */
@WebService(portName = "OrderServicePortType", serviceName="OrderService", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service.wsdl",
        endpointInterface="org.jvnet.hyperjaxb3.samples.pows.bindings.OrderServicePortType")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@javax.xml.ws.soap.MTOM
public class OrderServicePortTypeImpl implements OrderServicePortType {
    
    private static final Log log = LogFactory.getLog(OrderServicePortTypeImpl.class.getName());
    
    //Following should ideally be wired by spring but keeping it simple for now.
    static JPAPersistenceManager pm = new JPAPersistenceManager();
            
    /**
     * Creates a new instance of OrderServicePortTypeImpl
     */
    public OrderServicePortTypeImpl() {
        try {
            BindingUtility.getJAXBContext();
        } catch (JAXBException ex) {
            log.error(ex, ex);
        }
    }

    @WebMethod(action = "updateOrder")
    @WebResult(name = "updateResponse", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "updateResponse")
    public UpdateResponseType update(@WebParam(name = "updateRequest", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "updateRequest") UpdateRequestType updateRequest) {
        log.trace("update entered");
                
        List<PurchaseOrderType> pos = updateRequest.getPurchaseOrder();

        EntityTransaction txn = pm.getEntityManager().getTransaction();
        txn.begin();
        UpdateResponseType resp = BindingUtility.fac.createUpdateResponseType();
        
        try {        
            for (Iterator<PurchaseOrderType> i = pos.iterator(); i.hasNext(); ) {
                PurchaseOrderType po = i.next();
                pm.getEntityManager().persist(po);
            }

            resp.setStatus(BigInteger.ZERO);
            txn.commit();
        } catch (Exception e) {
            log.error(e, e);
            txn.rollback();
            resp.setStatus(BigInteger.ONE);
        }
        
        return resp;
    }

    @WebMethod(action = "queryOrder")
    @WebResult(name = "queryResponse", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "queryResponse")
    public QueryResponseType query(@WebParam(name = "queryRequest", targetNamespace = "urn:org:javanet:hyperjaxb3:samples:po-ws:po-service-protocol.xsd", partName = "queryRequest") QueryRequestType queryRequest) {
        log.trace("query entered");
        
        EntityTransaction txn = pm.getEntityManager().getTransaction();
        txn.begin();
        QueryResponseType resp = BindingUtility.fac.createQueryResponseType();       
                
        try {        
            String queryStr = queryRequest.getQueryString();
            Query query = pm.getEntityManager().createQuery(queryStr);
            List pos = query.getResultList();            
            resp.setPurchaseOrder(pos);
            txn.commit();
        } catch (Exception e) {
            log.error(e, e);
            txn.rollback();
        }
        return resp;
    }

}
