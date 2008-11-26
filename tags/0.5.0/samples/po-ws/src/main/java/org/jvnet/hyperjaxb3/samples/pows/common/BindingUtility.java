/*
 * ====================================================================
 *
 * This code is subject to the freebxml License, Version 1.1
 *
 * Copyright (c) 2001 - 2003 freebxml.org.  All rights reserved.
 *
 * $Header: /cvs/hyperjaxb3/samples/po-ws/src/main/java/org/jvnet/hyperjaxb3/samples/pows/common/BindingUtility.java,v 1.1 2007/05/31 01:23:31 najmi Exp $
 * ====================================================================
 */
package org.jvnet.hyperjaxb3.samples.pows.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * Utility class providing convenience methods for generated bindings.
 *
 * @author <a href="mailto:farrukh@wellfleetsoftware.com">Farrukh S. Najmi</a>
 */
public abstract class BindingUtility {
    private static final Log log = LogFactory.getLog(BindingUtility.class);
    public static JAXBContext jaxbContext;
    public static org.jvnet.hyperjaxb3.samples.pows.bindings.ObjectFactory fac;
    
    public static JAXBContext getJAXBContext() throws JAXBException {
        if (jaxbContext == null) {
            jaxbContext = JAXBContext.newInstance(
                    "org.jvnet.hyperjaxb3.samples.pows.bindings",
                    BindingUtility.class.getClassLoader());

            fac = new org.jvnet.hyperjaxb3.samples.pows.bindings.ObjectFactory();
        }

        return jaxbContext;
    }

    public static Unmarshaller getUnmarshaller() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        //unmarshaller.setValidating(true);
        unmarshaller.setEventHandler(new ValidationEventHandler() {
                public boolean handleEvent(ValidationEvent event) {
                    boolean keepOn = false;

                    return keepOn;
                }
            });

        return unmarshaller;
    }


}
