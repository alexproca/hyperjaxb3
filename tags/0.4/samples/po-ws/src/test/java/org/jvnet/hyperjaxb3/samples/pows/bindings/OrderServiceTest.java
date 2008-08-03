/*
 * OrderServiceTest.java
 *
 * Created on December 1, 2006, 12:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jvnet.hyperjaxb3.samples.pows.bindings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPElement;
import junit.framework.TestCase;
import org.jvnet.hyperjaxb3.samples.pows.common.BindingUtility;

/**
 * Tests the OrderService by publishing an order and then querying it via
 * OrderingService web service endpoint operations.
 *
 * @author <a href="mailto:farrukh@wellfleetsoftware.com">Farrukh S. Najmi</a>
 */
public class OrderServiceTest extends TestCase {
    
    static OrderService service = new OrderService();
    static OrderServicePortType port = service.getOrderServicePort();
    
    public OrderServiceTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    /**
     * Test of update method of class OrderServicePortTypeImpl
     * by updating/inserting a BusinessEntity.
     */    
    public void testUpdate() throws Exception {
        System.out.println("testUpdate");
        
        doUpdate("/samples/updateRequest.xml");
    }
    
    /**
     * Test of update method, of class ca.on.gov.mgs.gsdc.bd.bni.server.OrderServicePortTypeImpl.
     */    
    public void testQuery() throws Exception {
        System.out.println("testQuery");
        
        doQuery("/samples/queryRequest.xml");        
    }
        
    /**
     * Test of update method of class ca.on.gov.mgs.gsdc.bd.bni.server.OrderServicePortTypeImpl.
     * 
     */    
    private void doUpdate(String requestFilePath) throws Exception {
        
        InputStream is = getResourceAsInputStream(requestFilePath);
        
        Unmarshaller unmarshaller = BindingUtility.getJAXBContext().createUnmarshaller();
        JAXBElement elem = (JAXBElement) unmarshaller.unmarshal(is);
        UpdateRequestType updateRequest = (UpdateRequestType) elem.getValue();
                
        long start = System.currentTimeMillis();
        UpdateResponseType resp = port.update(updateRequest);        
        long end = System.currentTimeMillis();
        System.out.println("It takes: " + (end - start) + "ms to complete update request in file: " + requestFilePath);
        
        assertTrue("Update failed.", resp.getStatus().equals(BigInteger.ZERO));
         
    }
    
    /**
     * Test of query method, of class ca.on.gov.mgs.gsdc.bd.bni.server.QueryPortTypeImpl.
     */    
    private void doQuery(String requestFilePath) throws Exception {
        
        InputStream is = getResourceAsInputStream(requestFilePath);
        
        Unmarshaller unmarshaller = BindingUtility.getJAXBContext().createUnmarshaller();
        JAXBElement elem = (JAXBElement) unmarshaller.unmarshal(is);
        QueryRequestType queryRequest = (QueryRequestType) elem.getValue();
        
        long start = System.currentTimeMillis();
        QueryResponseType resp = port.query(queryRequest);
        long end = System.currentTimeMillis();
        System.out.println("It takes: " + (end - start) + "ms to complete query request in file: " + requestFilePath);
        
        //Make sure that there is at least one matched object        
        List<PurchaseOrderType> pos = resp.getPurchaseOrder();
        assertTrue("Failed to find results matching the query.", pos.size() > 0 );
         
    }    
    
    private InputStream getResourceAsInputStream(String resource) throws IOException {
        FileInputStream is = null;
        
        URL resourceURL = getClass().getResource(resource);
        is = new FileInputStream(resourceURL.getFile());
        
        return is;
    }
    
}
