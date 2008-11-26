package org.jvnet.hyperjaxb3.ejb.strategy.mapping;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.persistence.util.PersistenceUtils;
import org.jvnet.jaxb2_commons.strategy.OutlineProcessor;
import org.jvnet.jaxb2_commons.util.CodeModelUtils;
import org.jvnet.jaxb2_commons.util.OutlineUtils;

import com.sun.codemodel.fmt.JTextFile;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.EntityMappings;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

public class MarshalMappings implements
		OutlineProcessor<Collection<ClassOutline>, EjbPlugin> {

	protected Log logger = LogFactory.getLog(getClass());

	public Collection<ClassOutline> process(EjbPlugin context, Outline outline,
			Options options) throws Exception {
		logger.debug("Processing outline with context path ["
				+ OutlineUtils.getContextPath(outline) + "].");

		final Collection<? extends ClassOutline> classes = outline.getClasses();
		final Collection<ClassOutline> processedClassOutlines = new ArrayList<ClassOutline>(
				classes.size());

		for (final ClassOutline classOutline : classes) {
			if (!getIgnoring().isClassOutlineIgnored(classOutline)) {
				final ClassOutline processedClassOutline = process(this,
						classOutline, options);
				if (processedClassOutline != null) {
					processedClassOutlines.add(processedClassOutline);
				}
			}
		}
		return processedClassOutlines;
	}

	public ClassOutline process(MarshalMappings context,
			ClassOutline classOutline, Options options) throws Exception {
		logger.debug("Processing class outline ["
				+ OutlineUtils.getClassName(classOutline) + "].");

		final String className = CodeModelUtils
				.getLocalClassName(classOutline.ref);

		final JTextFile classOrmXmlFile = new JTextFile(className + ".orm.xml");

		classOutline._package()._package().addResourceFile(classOrmXmlFile);

		final EntityMappings entityMappings = new EntityMappings();
		entityMappings.setVersion("1.0");

		final Object draftEntityOrSuperclass = context.getMapping()
				.getEntityOrMappedSuperclassMapping().process(
						context.getMapping(), classOutline, options);
		if (draftEntityOrSuperclass instanceof Entity) {
			final Entity draftEntity = (Entity) draftEntityOrSuperclass;

			final Entity entity = new Entity();
			entity.mergeFrom(draftEntity, entity);
			entityMappings.getEntity().add(entity);
		} else if (draftEntityOrSuperclass instanceof MappedSuperclass) {
			final MappedSuperclass draftMappedSuperclass = (MappedSuperclass) draftEntityOrSuperclass;

			final MappedSuperclass entity = new MappedSuperclass();
			entity.mergeFrom(draftMappedSuperclass, entity);
			entityMappings.getMappedSuperclass().add(entity);
		} else {
			throw new AssertionError(
					"Either one-to-many or many-to-many mappings are expected.");
		}

		final Writer writer = new StringWriter();
		PersistenceUtils.createMarshaller().marshal(entityMappings, writer);
		classOrmXmlFile.setContents(writer.toString());
		return classOutline;
	}

	private Ignoring ignoring;

	public Ignoring getIgnoring() {
		return ignoring;
	}

	public void setIgnoring(Ignoring ignoring) {
		this.ignoring = ignoring;
	}

	private Mapping mapping;

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

}
