package org.jvnet.hyperjaxb3.ejb.strategy.model.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.schemas.customizations.Customizations;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.impl.DefaultIgnoring;
import org.jvnet.hyperjaxb3.ejb.strategy.model.AdaptTypeUse;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreateDefaultIdPropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.CreatePropertyInfos;
import org.jvnet.hyperjaxb3.ejb.strategy.model.GetTypes;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessClassInfo;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessModel;
import org.jvnet.hyperjaxb3.ejb.strategy.model.ProcessPropertyInfos;
import org.jvnet.jaxb2_commons.util.CustomizationUtils;
import org.springframework.beans.factory.annotation.Required;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMod;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.generator.bean.BeanGenerator;
import com.sun.tools.xjc.generator.bean.ClassOutlineImpl;
import com.sun.tools.xjc.model.CClassInfo;
import com.sun.tools.xjc.model.CPropertyInfo;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class DefaultProcessModel implements ProcessModel {

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

	public Collection<CClassInfo> process(EjbPlugin context, Outline outline,
			Options options) {

		final Model model = outline.getModel();

		CustomizationUtils.findCustomization(model,
				Customizations.PERSISTENCE_ELEMENT_NAME);

		logger.debug("Processing model [...].");

		final CClassInfo[] classInfos = model.beans().values().toArray(
				new CClassInfo[0]);
		final Collection<CClassInfo> includedClasses = new HashSet<CClassInfo>();

		for (final CClassInfo classInfo : classInfos) {
			if (!getIgnoring().isClassInfoIgnored(classInfo)) {
				final Collection<CClassInfo> targetClassInfos = getProcessClassInfo()
						.process(this, classInfo);
				if (targetClassInfos != null) {
					for (final CClassInfo targetClassInfo : targetClassInfos) {
						includedClasses.add(targetClassInfo);
					}
				}
			}
		}

		for (final CClassInfo classInfo : includedClasses) {
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

		return includedClasses;
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

	private ProcessClassInfo processClassInfo;

	public ProcessClassInfo getProcessClassInfo() {
		return processClassInfo;
	}

	@Required
	public void setProcessClassInfo(ProcessClassInfo processClassInfo) {
		this.processClassInfo = processClassInfo;
	}

	private ProcessPropertyInfos processPropertyInfos;

	public ProcessPropertyInfos getProcessPropertyInfos() {
		return processPropertyInfos;
	}

	@Required
	public void setProcessPropertyInfos(
			ProcessPropertyInfos processPropertyInfos) {
		this.processPropertyInfos = processPropertyInfos;
	}

	private CreateDefaultIdPropertyInfos createDefaultIdPropertyInfos;

	public CreateDefaultIdPropertyInfos getCreateDefaultIdPropertyInfos() {
		return createDefaultIdPropertyInfos;
	}

	@Required
	public void setCreateDefaultIdPropertyInfos(
			CreateDefaultIdPropertyInfos createDefaultIdPropertyInfos) {
		this.createDefaultIdPropertyInfos = createDefaultIdPropertyInfos;
	}

	private CreatePropertyInfos wrapComplexHeteroCollection;

	public CreatePropertyInfos getWrapComplexHeteroCollection() {
		return this.wrapComplexHeteroCollection;
	}

	public void setWrapComplexHeteroCollection(
			CreatePropertyInfos wrapHeteroCollection) {
		this.wrapComplexHeteroCollection = wrapHeteroCollection;
	}

	private GetTypes getTypes;

	public GetTypes getGetTypes() {
		return getTypes;
	}

	@Required
	public void setGetTypes(GetTypes getTypes) {
		this.getTypes = getTypes;
	}

	private CreatePropertyInfos wrapSingleBuiltinAttribute;

	public CreatePropertyInfos getWrapSingleBuiltinAttribute() {
		return wrapSingleBuiltinAttribute;
	}

	@Required
	public void setWrapSingleBuiltinAttribute(
			CreatePropertyInfos wrapSingleBuiltinAttribute) {
		this.wrapSingleBuiltinAttribute = wrapSingleBuiltinAttribute;
	}

	private CreatePropertyInfos wrapCollectionBuiltinAttribute;

	public CreatePropertyInfos getWrapCollectionBuiltinAttribute() {
		return wrapCollectionBuiltinAttribute;
	}

	@Required
	public void setWrapCollectionBuiltinAttribute(
			CreatePropertyInfos wrapCollectionBuiltinAttribute) {
		this.wrapCollectionBuiltinAttribute = wrapCollectionBuiltinAttribute;
	}

	private CreatePropertyInfos wrapCollectionEnumAttribute;

	public CreatePropertyInfos getWrapCollectionEnumAttribute() {
		return wrapCollectionEnumAttribute;
	}

	@Required
	public void setWrapCollectionEnumAttribute(
			CreatePropertyInfos wrapCollectionEnumAttribute) {
		this.wrapCollectionEnumAttribute = wrapCollectionEnumAttribute;
	}

	private CreatePropertyInfos wrapSingleBuiltinValue;

	public CreatePropertyInfos getWrapSingleBuiltinValue() {
		return wrapSingleBuiltinValue;
	}

	@Required
	public void setWrapSingleBuiltinValue(
			CreatePropertyInfos wrapSingleBuiltinValue) {
		this.wrapSingleBuiltinValue = wrapSingleBuiltinValue;
	}

	private CreatePropertyInfos wrapCollectionBuiltinValue;

	public CreatePropertyInfos getWrapCollectionBuiltinValue() {
		return wrapCollectionBuiltinValue;
	}

	@Required
	public void setWrapCollectionBuiltinValue(
			CreatePropertyInfos wrapCollectionBuiltinValue) {
		this.wrapCollectionBuiltinValue = wrapCollectionBuiltinValue;
	}

	private CreatePropertyInfos wrapCollectionEnumValue;

	public CreatePropertyInfos getWrapCollectionEnumValue() {
		return wrapCollectionEnumValue;
	}

	@Required
	public void setWrapCollectionEnumValue(
			CreatePropertyInfos wrapCollectionEnumValue) {
		this.wrapCollectionEnumValue = wrapCollectionEnumValue;
	}

	private CreatePropertyInfos wrapSingleBuiltinElement;

	public CreatePropertyInfos getWrapSingleBuiltinElement() {
		return wrapSingleBuiltinElement;
	}

	@Required
	public void setWrapSingleBuiltinElement(
			CreatePropertyInfos wrapSingleBuiltinElement) {
		this.wrapSingleBuiltinElement = wrapSingleBuiltinElement;
	}

	private CreatePropertyInfos wrapSingleHeteroElement;

	public CreatePropertyInfos getWrapSingleHeteroElement() {
		return wrapSingleHeteroElement;
	}

	@Required
	public void setWrapSingleHeteroElement(
			CreatePropertyInfos wrapSingleHeteroElement) {
		this.wrapSingleHeteroElement = wrapSingleHeteroElement;
	}

	private CreatePropertyInfos wrapCollectionBuiltinElement;

	public CreatePropertyInfos getWrapCollectionBuiltinElement() {
		return wrapCollectionBuiltinElement;
	}

	@Required
	public void setWrapCollectionBuiltinElement(
			CreatePropertyInfos wrapCollectionBuiltinElement) {
		this.wrapCollectionBuiltinElement = wrapCollectionBuiltinElement;
	}

	private CreatePropertyInfos wrapCollectionEnumElement;

	public CreatePropertyInfos getWrapCollectionEnumElement() {
		return wrapCollectionEnumElement;
	}

	@Required
	public void setWrapCollectionEnumElement(
			CreatePropertyInfos wrapCollectionEnumElement) {
		this.wrapCollectionEnumElement = wrapCollectionEnumElement;
	}

	private CreatePropertyInfos wrapCollectionHeteroElement;

	public CreatePropertyInfos getWrapCollectionHeteroElement() {
		return wrapCollectionHeteroElement;
	}

	@Required
	public void setWrapCollectionHeteroElement(
			CreatePropertyInfos wrapCollectionHeteroElement) {
		this.wrapCollectionHeteroElement = wrapCollectionHeteroElement;
	}

	// Reference

	private CreatePropertyInfos wrapSingleBuiltinElementReference;

	public CreatePropertyInfos getWrapSingleBuiltinElementReference() {
		return wrapSingleBuiltinElementReference;
	}

	@Required
	public void setWrapSingleBuiltinElementReference(
			CreatePropertyInfos wrapSingleBuiltinElementReference) {
		this.wrapSingleBuiltinElementReference = wrapSingleBuiltinElementReference;
	}

	private CreatePropertyInfos wrapSingleEnumElementReference;

	public CreatePropertyInfos getWrapSingleEnumElementReference() {
		return wrapSingleEnumElementReference;
	}

	@Required
	public void setWrapSingleEnumElementReference(
			CreatePropertyInfos wrapSingleEnumElementReference) {
		this.wrapSingleEnumElementReference = wrapSingleEnumElementReference;
	}

	private CreatePropertyInfos wrapSingleClassElementReference;

	public CreatePropertyInfos getWrapSingleClassElementReference() {
		return wrapSingleClassElementReference;
	}

	@Required
	public void setWrapSingleClassElementReference(
			CreatePropertyInfos wrapSingleClassElementReference) {
		this.wrapSingleClassElementReference = wrapSingleClassElementReference;
	}

	private CreatePropertyInfos wrapSingleSubstitutedElementReference;

	public CreatePropertyInfos getWrapSingleSubstitutedElementReference() {
		return wrapSingleSubstitutedElementReference;
	}

	@Required
	public void setWrapSingleSubstitutedElementReference(
			CreatePropertyInfos wrapSingleSubstitutedElementReference) {
		this.wrapSingleSubstitutedElementReference = wrapSingleSubstitutedElementReference;
	}

	private CreatePropertyInfos wrapSingleHeteroReference;

	public CreatePropertyInfos getWrapSingleHeteroReference() {
		return wrapSingleHeteroReference;
	}

	@Required
	public void setWrapSingleHeteroReference(
			CreatePropertyInfos wrapSingleHeteroReference) {
		this.wrapSingleHeteroReference = wrapSingleHeteroReference;
	}

	private CreatePropertyInfos wrapSingleClassReference;

	public CreatePropertyInfos getWrapSingleClassReference() {
		return wrapSingleClassReference;
	}

	@Required
	public void setWrapSingleClassReference(
			CreatePropertyInfos wrapSingleClassReference) {
		this.wrapSingleClassReference = wrapSingleClassReference;
	}

	private CreatePropertyInfos wrapSingleWildcardReference;

	public CreatePropertyInfos getWrapSingleWildcardReference() {
		return wrapSingleWildcardReference;
	}

	@Required
	public void setWrapSingleWildcardReference(
			CreatePropertyInfos wrapSingleWildcardReference) {
		this.wrapSingleWildcardReference = wrapSingleWildcardReference;
	}

	private CreatePropertyInfos wrapCollectionHeteroReference;

	public CreatePropertyInfos getWrapCollectionHeteroReference() {
		return wrapCollectionHeteroReference;
	}

	public void setWrapCollectionHeteroReference(
			CreatePropertyInfos wrapCollectionHeteroReference) {
		this.wrapCollectionHeteroReference = wrapCollectionHeteroReference;
	}

	private CreatePropertyInfos wrapCollectionWildcardReference;

	public CreatePropertyInfos getWrapCollectionWildcardReference() {
		return wrapCollectionWildcardReference;
	}

	public void setWrapCollectionWildcardReference(
			CreatePropertyInfos wrapCollectionWildcardReference) {
		this.wrapCollectionWildcardReference = wrapCollectionWildcardReference;
	}

	private AdaptTypeUse adaptBuiltinTypeUse;

	public AdaptTypeUse getAdaptBuiltinTypeUse() {
		return adaptBuiltinTypeUse;
	}

	@Required
	public void setAdaptBuiltinTypeUse(AdaptTypeUse adaptBuiltinTypeUse) {
		this.adaptBuiltinTypeUse = adaptBuiltinTypeUse;
	}

	private Ignoring ignoring = new DefaultIgnoring();

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	private Customizing customizing;

	public Customizing getCustomizing() {
		return customizing;
	}

	@Required
	public void setCustomizing(Customizing customizations) {
		this.customizing = customizations;
	}
}
