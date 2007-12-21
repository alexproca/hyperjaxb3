package org.jvnet.hyperjaxb3.ejb.plugin;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.outline.ProcessOutline;
import org.jvnet.hyperjaxb3.ejb.test.RoundtripTest;
import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;
import org.jvnet.hyperjaxb3.xjc.generator.bean.field.UntypedListFieldRenderer;
import org.jvnet.jaxb2_commons.plugin.spring.AbstractSpringConfigurablePlugin;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.jvnet.jaxb2_commons.util.GeneratorContextUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.fmt.JTextFile;
import com.sun.java.xml.ns.persistence.Persistence;
import com.sun.java.xml.ns.persistence.Persistence.PersistenceUnit;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.generator.bean.BeanGenerator;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.generator.bean.field.FieldRenderer;
import com.sun.tools.xjc.generator.bean.field.FieldRendererFactory;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPluginCustomization;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

/**
 * Hyperjaxb3 EJB plugin.
 * 
 */
public class EjbPlugin extends AbstractSpringConfigurablePlugin {

	protected Log logger = LogFactory.getLog(getClass());

	private final Method generateFieldDecl;
	{
		try {
			generateFieldDecl = BeanGenerator.class.getDeclaredMethod(
					"generateFieldDecl", new Class[] { ClassOutlineImpl.class,
							CPropertyInfo.class });
			generateFieldDecl.setAccessible(true);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);

		}
	}

	public String getOptionName() {
		return "Xhyperjaxb3-ejb";
	}

	public String getUsage() {
		return "  -Xhyperjaxb3-ejb: Hyperjaxb2 EJB plugin";
	}

	private String roundtripTestClassName;

	public String getRoundtripTestClassName() {
		return roundtripTestClassName;
	}

	public void setRoundtripTestClassName(String rt) {
		this.roundtripTestClassName = rt;
	}

	private String persistenceUnitName;

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	private File targetDir;

	public File getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(File targetDir) {
		this.targetDir = targetDir;
	}

	private File persistenceXml;

	public File getPersistenceXml() {
		return persistenceXml;
	}

	public void setPersistenceXml(File persistenceXml) {
		this.persistenceXml = persistenceXml;
	}

	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[] { "classpath*:"
				+ getClass().getPackage().getName().replace('.', '/')
				+ "/applicationContext.xml" };
	}

	private boolean generateTransientId = false;

	public boolean isGenerateTransientId() {
		return generateTransientId;
	}

	public void setGenerateTransientId(boolean generateTransientId) {
		this.generateTransientId = generateTransientId;
	}

	// @Override
	// public List<String> getCustomizationURIs() {
	// return Collections.singletonList(Constants.NAMESPACE_URI);
	// }
	//
	// @Override
	// public boolean isCustomizationTagName(String nsUri, String localName) {
	// return Constants.NAMESPACE_URI.equals(nsUri);
	// }
	//
	@Override
	public boolean run(Outline outline, Options options,
			ErrorHandler errorHandler) {
		
		getProcessModel().getCreateDefaultIdPropertyInfos().setTransient(isGenerateTransientId());

		try {
			processModel(outline);

			processOutline(outline, options);

			for (final CClassInfo classInfo : outline.getModel().beans()
					.values()) {
				checkCustomizations(classInfo);
				for (final CPropertyInfo propertyInfo : classInfo
						.getProperties()) {
					checkCustomizations(classInfo, propertyInfo);
				}
			}

		} catch (Exception ex) {
			try {
				logger.error("Outline processing error.", ex);
				errorHandler.error(new SAXParseException(
						"Outline processing error.", null, ex));
			} catch (SAXException ignored) {
				// Exception is ignored
			}
			return false;
		}
		return true;
	}

	public void processOutline(Outline outline, Options options)
			throws IOException, JAXBException {
		final ProcessOutline outlineProcessor = getOutlineProcessor();

		final Collection<ClassOutline> includedClasses = outlineProcessor
				.process(outlineProcessor, outline, options);

		final Persistence persistence = createPersistence(outline,
				includedClasses);

		/*
		 * final File metaInf = new File(options.targetDir, "META-INF");
		 * 
		 * metaInf.mkdirs();
		 * 
		 * final File persistenceXml = new File(metaInf, "persistence.xml");
		 * 
		 * Writer writer = null;
		 * 
		 * try { writer = new FileWriter(persistenceXml);
		 * PersistenceUtils.createMarshaller().marshal(persistence, writer); }
		 * finally { if (writer != null) try { writer.close(); } catch
		 * (IOException ignored) { } }
		 */

		final JCodeModel codeModel = outline.getCodeModel();

		final JPackage defaultPackage = codeModel._package("");

		final JTextFile persistenceXmlFile = new JTextFile(
				"META-INF/persistence.xml");

		defaultPackage.addResourceFile(persistenceXmlFile);

		final Writer writer = new StringWriter();
		PersistenceUtils.createMarshaller().marshal(persistence, writer);
		persistenceXmlFile.setContents(writer.toString());

		if (getRoundtripTestClassName() != null) {
			GeneratorContextUtils.generateContextPathAwareClass(outline,
					getRoundtripTestClassName(), RoundtripTest.class);
		}

		// TODO HACK!!! REMOVE ME!!!
		new File(getTargetDir(), "META-INF").mkdir();

	}

	protected Persistence createPersistence(Outline outline,
			final Collection<ClassOutline> includedClasses)
			throws JAXBException {
		final String generatedPersistenceUnitName = getPersistenceUnitName() != null ? getPersistenceUnitName()
				: OutlineUtils.getContextPath(outline);

		final Persistence persistence;
		final PersistenceUnit persistenceUnit;

		final File persistenceXml = getPersistenceXml();

		if (persistenceXml != null) {
			try {

				persistence = (Persistence) PersistenceUtils.CONTEXT
						.createUnmarshaller().unmarshal(persistenceXml);

				PersistenceUnit foundPersistenceUnit = null;

				for (final PersistenceUnit unit : persistence
						.getPersistenceUnit()) {
					if (getPersistenceUnitName() != null
							&& getPersistenceUnitName().equals(unit.getName())) {
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
			persistenceUnit.getClazz().add(
					OutlineUtils.getClassName(classOutline));
		}
		return persistence;
	}

	public void processModel(Outline outline) {
		Collection<CClassInfo> classes = getProcessModel().process(
				getProcessModel(), outline.getModel());

		for (final CClassInfo classInfo : classes) {
			final ClassOutline classOutline = outline.getClazz(classInfo);
			if (Customizations.isGenerated(classInfo)) {
				generateClassBody(outline, (ClassOutlineImpl) classOutline);
			}

			for (final CPropertyInfo propertyInfo : classInfo.getProperties()) {
				if (outline.getField(propertyInfo) == null) {
					generateFieldDecl(outline, (ClassOutlineImpl) classOutline,
							propertyInfo);
				}
			}
		}
	}

	private void checkCustomizations(CClassInfo classInfo,
			CPropertyInfo customizable) {

		for (CPluginCustomization pluginCustomization : CustomizationUtils
				.getCustomizations(customizable)) {
			if (!pluginCustomization.isAcknowledged()
					&& Customizations.NAMESPACE_URI
							.equals(pluginCustomization.element
									.getNamespaceURI())) {
				logger.error("Unacknowledged customization [" +

				getName(pluginCustomization.element) + "] in the property ["
						+ classInfo.getName() + "."
						+ customizable.getName(true) + "].");

				// pluginCustomization.markAsAcknowledged();
			}
		}

	}

	private void checkCustomizations(CClassInfo customizable) {

		for (final CPluginCustomization pluginCustomization : CustomizationUtils
				.getCustomizations(customizable)) {
			final Element element = pluginCustomization.element;

			if (!pluginCustomization.isAcknowledged()
			// && Customizations.NAMESPACE_URI.equals(element
			// .getNamespaceURI())
			) {
				logger.error("Unacknowledged customization [" +

				getName(element) + "] in the class [" + customizable.getName()
						+ "].");
			}
		}

	}

	private QName getName(Element element) {
		return new QName(element.getNamespaceURI(), element.getLocalName(),
				element.getPrefix() == null ? XMLConstants.DEFAULT_NS_PREFIX
						: element.getPrefix());
	}

	// private OutlineAnnotator outlineAnnotator = new
	// DefaultOutlineAnnotator();
	//
	// public OutlineAnnotator getOutlineAnnotator() {
	// return outlineAnnotator;
	// }
	//
	// public void setOutlineAnnotator(OutlineAnnotator annotationStrategy) {
	// this.outlineAnnotator = annotationStrategy;
	// }
	//

	@Override
	public void postProcessModel(Model model, ErrorHandler errorHandler) {
		// super.postProcessModel(model, errorHandler);
		// model.strategy = ImplStructureStrategy.BEAN_ONLY;
		// getProcessModel().process(getProcessModel(), model);
		// getProcessModel().process(getProcessModel(), model);

	}

	@Override
	public void onActivated(Options options) throws BadCommandLineException {
		super.onActivated(options);
		if (getTargetDir() == null) {
			setTargetDir(options.targetDir);
		}
		final FieldRendererFactory fieldRendererFactory = new FieldRendererFactory() {

			public FieldRenderer getList(JClass coreList) {
				return new UntypedListFieldRenderer(coreList);
			}
		};
		options.setFieldRendererFactory(fieldRendererFactory, this);
	}

	private ProcessOutline outlineProcessor;

	public ProcessOutline getOutlineProcessor() {
		return outlineProcessor;
	}

	public void setOutlineProcessor(ProcessOutline outlineProcessor) {
		this.outlineProcessor = outlineProcessor;
	}

	private ProcessModel processModel;

	public ProcessModel getProcessModel() {
		return processModel;
	}

	public void setProcessModel(ProcessModel processModel) {
		logger.debug("Setting process model.");
		this.processModel = processModel;
	}

	@Override
	public List<String> getCustomizationURIs() {
		final List<String> customizationURIs = new LinkedList<String>();
		customizationURIs.addAll(super.getCustomizationURIs());
		customizationURIs.addAll(Customizations.NAMESPACES);
		return customizationURIs;
	}

	@Override
	public boolean isCustomizationTagName(String namespace, String localPart) {
		return super.isCustomizationTagName(namespace, localPart)
				|| Customizations.NAMESPACES.contains(namespace);
	}

	private void generateClassBody(Outline outline, ClassOutlineImpl cc) {

		final JCodeModel codeModel = outline.getCodeModel();
		final Model model = outline.getModel();
		CClassInfo target = cc.target;

		// if serialization support is turned on, generate
		// [RESULT]
		// class ... implements Serializable {
		// private static final long serialVersionUID = <id>;
		// ....
		// }
		if (model.serializable) {
			cc.implClass._implements(Serializable.class);
			if (model.serialVersionUID != null) {
				cc.implClass.field(JMod.PRIVATE | JMod.STATIC | JMod.FINAL,
						codeModel.LONG, "serialVersionUID", JExpr
								.lit(model.serialVersionUID));
			}
		}

		// used to simplify the generated annotations
		// String mostUsedNamespaceURI =
		// cc._package().getMostUsedNamespaceURI();

		// [RESULT]
		// @XmlType(name="foo", targetNamespace="bar://baz")
		// XmlTypeWriter xtw = cc.implClass.annotate2(XmlTypeWriter.class);
		// writeTypeName(cc.target.getTypeName(), xtw, mostUsedNamespaceURI);

		// if(model.options.target.isLaterThan(SpecVersion.V2_1)) {
		// // @XmlSeeAlso
		// Iterator<CClassInfo> subclasses = cc.target.listSubclasses();
		// if(subclasses.hasNext()) {
		// XmlSeeAlsoWriter saw =
		// cc.implClass.annotate2(XmlSeeAlsoWriter.class);
		// while (subclasses.hasNext()) {
		// CClassInfo s = subclasses.next();
		// saw.value(outline.getClazz(s).implRef);
		// }
		// }
		// }

		// if(target.isElement()) {
		// String namespaceURI = target.getElementName().getNamespaceURI();
		// String localPart = target.getElementName().getLocalPart();
		//
		// // [RESULT]
		// // @XmlRootElement(name="foo", targetNamespace="bar://baz")
		// XmlRootElementWriter xrew =
		// cc.implClass.annotate2(XmlRootElementWriter.class);
		// xrew.name(localPart);
		// if(!namespaceURI.equals(mostUsedNamespaceURI)) // only generate if
		// necessary
		// xrew.namespace(namespaceURI);
		// }

		// if(target.isOrdered()) {
		// for(CPropertyInfo p : target.getProperties() ) {
		// if( ! (p instanceof CAttributePropertyInfo )) {
		// xtw.propOrder(p.getName(false));
		// }
		// }
		// } else {
		// // produce empty array
		// xtw.getAnnotationUse().paramArray("propOrder");
		// }

		for (CPropertyInfo prop : target.getProperties()) {
			generateFieldDecl(outline, cc, prop);
		}

		assert !target.declaresAttributeWildcard();
		// if( target.declaresAttributeWildcard() ) {
		// generateAttributeWildcard(cc);
		// }

		// generate some class level javadoc
		// cc.ref.javadoc().append(target.javadoc);

		// cc._package().objectFactoryGenerator().populate(cc);
	}

	private FieldOutline generateFieldDecl(Outline outline,
			ClassOutlineImpl cc, CPropertyInfo prop) {

		try {
			return (FieldOutline) generateFieldDecl.invoke(outline,
					new Object[] { cc, prop });
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
