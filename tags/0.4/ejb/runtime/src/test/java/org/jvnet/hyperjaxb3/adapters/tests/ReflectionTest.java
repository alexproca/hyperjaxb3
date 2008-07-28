package org.jvnet.hyperjaxb3.adapters.tests;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsGDay;

public class ReflectionTest extends TestCase {

	protected Log logger = LogFactory.getLog(getClass());

	public void testType() throws Exception {
//
//		final Class x = XMLGregorianCalendarAsGDayAdapter.class;
//		
//		assert XmlAdapter.class.isAssignableFrom(x);
//
//		
//		Method method = x.getMethod("marshal", new Class[]{Object.class});
//		
//
//		Type t = method.getGenericReturnType();
//		
//		Class xc = (Class) x.getGenericSuperclass();
//		
//		TypeVariable[] typeParameters = xc.getTypeParameters();
//		
//		Method xmethod = xc.getMethod("marshal", new Class[]{Object.class});
//		
//		Type genericReturnType = xmethod.getGenericReturnType();
//		
//		logger.debug(t);
	}
}
