package org.jvnet.hyperjaxb3.ejb.strategy.processor;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.JAXBException;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.OutlineProcessor;
import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;
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

public class MappingFilePersistenceProcessor implements
		OutlineProcessor<EjbPlugin> {

	private Naming naming;

	public Naming getNaming() {
		return naming;
	}

	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	public Collection<ClassOutline> process(EjbPlugin plugin, Outline outline,
			Options options) throws Exception {

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

	private OutlineProcessor<EjbPlugin> outlineProcessor;

	public OutlineProcessor<EjbPlugin> getOutlineProcessor() {
		return outlineProcessor;
	}

	@Required
	public void setOutlineProcessor(OutlineProcessor<EjbPlugin> outlineProcessor) {
		this.outlineProcessor = outlineProcessor;
	}

	protected Persistence createPersistence(EjbPlugin plugin, Outline outline,
			Options options, final Collection<ClassOutline> includedClasses)
			throws JAXBException {

		final String persistenceUnitName = plugin.getPersistenceUnitName();
		final String generatedPersistenceUnitName = persistenceUnitName != null ? persistenceUnitName
				: getNaming().getPersistenceUnitName(outline);

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

		for (final ClassOutline classOutline : includedClasses) {
			final String className = OutlineUtils.getClassName(classOutline);
			persistenceUnit.getMappingFile().add(
					className.replace('.', '/') + ".orm.xml");
		}
		Collections.sort(persistenceUnit.getMappingFile());
		return persistence;
	}

}
