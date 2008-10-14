package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;
import org.jvnet.jaxb2_commons.strategy.OutlineProcessor;
import org.jvnet.jaxb2_commons.util.OutlineUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.fmt.JTextFile;
import com.sun.java.xml.ns.persistence.Persistence;
import com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class ClassPersistenceProcessor implements
		OutlineProcessor<Void, EjbPlugin> {

	public Void process(EjbPlugin plugin, Outline outline, Options options)
			throws Exception {

		Collection<ClassOutline> includedClasses = getOutlineProcessor()
				.process(plugin, outline, options);

		final Persistence persistence = createPersistence(plugin, outline,
				options, includedClasses);

		final JCodeModel codeModel = outline.getCodeModel();

		final JPackage defaultPackage = codeModel._package("");

		final JTextFile persistenceXmlFile = new JTextFile(
				"META-INF/persistence.xml");

		defaultPackage.addResourceFile(persistenceXmlFile);

		final Writer writer = new StringWriter();
		PersistenceUtils.createMarshaller().marshal(persistence, writer);
		persistenceXmlFile.setContents(writer.toString());

		// TODO HACK!!! REMOVE ME!!!
		new File(plugin.getTargetDir(), "META-INF").mkdir();

		// TODO Auto-generated method stub
		return null;
	}

	private OutlineProcessor<Collection<ClassOutline>, EjbPlugin> outlineProcessor;

	public OutlineProcessor<Collection<ClassOutline>, EjbPlugin> getOutlineProcessor() {
		return outlineProcessor;
	}

	@Required
	public void setOutlineProcessor(
			OutlineProcessor<Collection<ClassOutline>, EjbPlugin> outlineProcessor) {
		this.outlineProcessor = outlineProcessor;
	}

	protected Persistence createPersistence(EjbPlugin plugin, Outline outline,
			Options options, final Collection<ClassOutline> includedClasses)
			throws JAXBException {

		final String persistenceUnitName = plugin.getPersistenceUnitName();
		final String generatedPersistenceUnitName = persistenceUnitName != null ? persistenceUnitName
				: OutlineUtils.getContextPath(outline);

		final Persistence persistence;
		final PersistenceUnit persistenceUnit;

		final File persistenceXml = plugin.getPersistenceXml();

		if (persistenceXml != null) {
			try {

				persistence = (Persistence) PersistenceUtils.CONTEXT
						.createUnmarshaller().unmarshal(persistenceXml);

				PersistenceUnit foundPersistenceUnit = null;

				for (final PersistenceUnit unit : persistence
						.getPersistenceUnit()) {
					if (persistenceUnitName != null
							&& persistenceUnitName.equals(unit.getName())) {
						foundPersistenceUnit = unit;
					} else if ("##generated".equals(unit.getName())) {
						foundPersistenceUnit = unit;
						foundPersistenceUnit
								.setName(generatedPersistenceUnitName);
					}
				}
				if (foundPersistenceUnit != null) {
					persistenceUnit = foundPersistenceUnit;
				} else {
					persistenceUnit = new PersistenceUnit();
					persistence.getPersistenceUnit().add(persistenceUnit);
					persistenceUnit.setName(generatedPersistenceUnitName);
				}

			} catch (Exception ex) {
				throw new JAXBException("Persistence XML file ["
						+ persistenceXml + "] could not be parsed.", ex);
			}

		} else {
			persistence = new Persistence();
			persistence.setVersion("1.0");
			persistenceUnit = new PersistenceUnit();
			persistence.getPersistenceUnit().add(persistenceUnit);
			persistenceUnit.setName(generatedPersistenceUnitName);
		}

		// final S

		for (final ClassOutline classOutline : includedClasses) {
			persistenceUnit.getClazz().add(
					OutlineUtils.getClassName(classOutline));
		}
		Collections.sort(persistenceUnit.getClazz());
		return persistence;
	}

}
