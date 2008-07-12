package org.jvnet.hyperjaxb3.samples.pows.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author <a href="mailto:farrukh@wellfleetsoftware.com">Farrukh S. Najmi</a>
 */
public class JPAPersistenceManager {
    private static final Log log = LogFactory.getLog(JPAPersistenceManager.class);
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    /** Creates a new instance of JPAPersistenceManager */
    public JPAPersistenceManager() {
        setUp();
        em = createEntityManager();        
    }
    
        
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }
    
    private void setUp() {
        final EntityManagerFactory _emf = getEntityManagerFactory();
        if (_emf == null || !_emf.isOpen()) {
            setEntityManagerFactory(createEntityManagerFactory());
        }
    }
    
    private String getPersistenceUnitName() {
        
        final String name = "org.jvnet.hyperjaxb3.samples.pows.bindings";
        return name;       
    }
    
    private Map getEntityManagerProperties() {
        return null;
    }
    
    private EntityManagerFactory createEntityManagerFactory() {
        
        try {
            final Enumeration<URL> resources = getClass().getClassLoader().getResources(
                    "META-INF/persistence.xml");
            
            ArrayList urls = Collections.list(resources);
            log.debug("Detected " + urls.size() + " META-INF/persistence.xml files in classloader");
            
            Iterator iter = urls.iterator();
            while (iter.hasNext()) {
                final URL url = (URL) iter.next();
                log.debug("Detected [" + url + "].");
            }
            
        } catch (IOException ignored) {
            
        }
        
        final Map properties = getEntityManagerFactoryProperties();
        
        if (properties == null) {
            return Persistence.createEntityManagerFactory(getPersistenceUnitName());
        } else {
            return Persistence.createEntityManagerFactory(getPersistenceUnitName(), properties);
        }
    }
    
    private Map getEntityManagerFactoryProperties() {
        
        Properties properties = new Properties();
        
        try {
            String emPropsName = getEntityManagerFactoryPropertiesResourceName();
            log.debug("Loading entity manager factory properties from files named: " + emPropsName);
            
            final Enumeration<URL> resources = getClass().getClassLoader().getResources(
                    emPropsName);
                        
            ArrayList urls = Collections.list(resources);
            log.debug("Detected " + urls.size() + " files with name " + emPropsName + " in classloader");
            
            log.debug("Loading entity manager factory properties.");

            Iterator iter = urls.iterator();                
            while (iter.hasNext()) {
                final URL resource = (URL) iter.next();
                log.debug("Loading entity manager factory properties from [" + resource + "].");

                if (resource != null) {
                    InputStream is = null;
                    try {
                        is = resource.openStream();
                        properties.load(is);
                    } catch (IOException ex) {
                        log.error(ex, ex);
                        properties = null;
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException ex) {
                                // Ignore
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            log.error(ex, ex);
            properties = null;
        }
        log.debug("Loaded following entity manager factory properties: " + properties);
        return properties;
    }
    
    private String getEntityManagerFactoryPropertiesResourceName() {
        return "META-INF/persistence.properties";
    }
    
    private EntityManager createEntityManager() {
        final Map properties = getEntityManagerProperties();
        final EntityManagerFactory emf = getEntityManagerFactory();
        if (properties == null) {
            return emf.createEntityManager();
        } else {
            return emf.createEntityManager(properties);
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }
    

}
