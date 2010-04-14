package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.fmt.JTextFile;
import com.sun.java.xml.ns.persistence.Persistence;

public class PersistenceMarshaller {

	public void marshallPersistence(File directory, JCodeModel codeModel,
			Persistence persistence) throws Exception {

		final JPackage defaultPackage = codeModel._package("");

		final JTextFile persistenceXmlFile = new JTextFile(
				"META-INF/persistence.xml");

		defaultPackage.addResourceFile(persistenceXmlFile);

		final Writer writer = new StringWriter();
		PersistenceUtils.createMarshaller().marshal(persistence, writer);
		persistenceXmlFile.setContents(writer.toString());

		// TODO HACK!!! REMOVE ME!!!
		new File(directory, "META-INF").mkdir();
	}
}
