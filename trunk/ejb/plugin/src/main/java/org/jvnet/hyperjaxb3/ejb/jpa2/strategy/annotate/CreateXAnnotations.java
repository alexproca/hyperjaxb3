package org.jvnet.hyperjaxb3.ejb.jpa2.strategy.annotate;

import java.util.Collection;

import javax.persistence.AccessType;

import org.jvnet.annox.model.XAnnotation;
import org.jvnet.hyperjaxb3.annotation.util.AnnotationUtils;

import com.sun.java.xml.ns.persistence.orm.Basic;
import com.sun.java.xml.ns.persistence.orm.Embeddable;
import com.sun.java.xml.ns.persistence.orm.Embedded;
import com.sun.java.xml.ns.persistence.orm.EmbeddedId;
import com.sun.java.xml.ns.persistence.orm.Entity;
import com.sun.java.xml.ns.persistence.orm.Id;
import com.sun.java.xml.ns.persistence.orm.ManyToMany;
import com.sun.java.xml.ns.persistence.orm.ManyToOne;
import com.sun.java.xml.ns.persistence.orm.MappedSuperclass;
import com.sun.java.xml.ns.persistence.orm.OneToMany;
import com.sun.java.xml.ns.persistence.orm.OneToOne;
import com.sun.java.xml.ns.persistence.orm.SequenceGenerator;
import com.sun.java.xml.ns.persistence.orm.UniqueConstraint;
import com.sun.java.xml.ns.persistence.orm.Version;

public class CreateXAnnotations extends
		org.jvnet.hyperjaxb3.ejb.strategy.annotate.CreateXAnnotations {

	public XAnnotation createCacheable(Boolean cacheable) {
		return cacheable == null ? null :
		//
				new XAnnotation(javax.persistence.Cacheable.class,
				//
						AnnotationUtils.create("value", cacheable)
				//
				);
	}

	public XAnnotation createAccess(String access) {
		return access == null ? null : new XAnnotation(
				javax.persistence.Access.class, AnnotationUtils.create("value",
						AccessType.valueOf(access)));

	}

	@Override
	public Collection<XAnnotation> createEntityAnnotations(Entity source) {

		final Collection<XAnnotation> annotations = super
				.createEntityAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createCacheable(source.isCacheable()));
	}

	@Override
	public Collection<XAnnotation> createBasicAnnotations(Basic source) {
		final Collection<XAnnotation> annotations = super
				.createBasicAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createEmbeddableAnnotations(Embeddable source) {
		final Collection<XAnnotation> annotations = super
				.createEmbeddableAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createEmbeddedAnnotations(Embedded source) {
		final Collection<XAnnotation> annotations = super
				.createEmbeddedAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createEmbeddedIdAnnotations(EmbeddedId source) {
		Collection<XAnnotation> annotations = super
				.createEmbeddedIdAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createIdAnnotations(Id source) {
		final Collection<XAnnotation> annotations = super
				.createIdAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createManyToManyAnnotations(ManyToMany source) {
		final Collection<XAnnotation> annotations = super
				.createManyToManyAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createManyToOneAnnotations(ManyToOne source) {
		final Collection<XAnnotation> annotations = super
				.createManyToOneAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createMappedSuperclassAnnotations(
			MappedSuperclass source) {
		Collection<XAnnotation> annotations = super
				.createMappedSuperclassAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createOneToManyAnnotations(OneToMany source) {
		final Collection<XAnnotation> annotations = super
				.createOneToManyAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createOneToOneAnnotations(OneToOne source) {
		final Collection<XAnnotation> annotations = super
				.createOneToOneAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public Collection<XAnnotation> createVersionAnnotations(Version source) {
		final Collection<XAnnotation> annotations = super
				.createVersionAnnotations(source);
		return source == null ? annotations : annotations(annotations,
				createAccess(source.getAccess()));
	}

	@Override
	public XAnnotation createUniqueConstraint(UniqueConstraint source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.UniqueConstraint.class,
						//
						AnnotationUtils.create("name", source.getName()),
						AnnotationUtils.create("columnNames", source
								.getColumnName())
				//
				);
	}

	@Override
	public XAnnotation createSequenceGenerator(SequenceGenerator source) {
		return source == null ? null :
		//
				new XAnnotation(javax.persistence.SequenceGenerator.class,
				//
						AnnotationUtils.create("name", source.getName()),
						//
						AnnotationUtils.create("sequenceName", source
								.getSequenceName()),
						//
						AnnotationUtils.create("catalog", source
								.getCatalog()),
						//
						AnnotationUtils.create("schema", source
								.getSchema()),
						//
						AnnotationUtils.create("initialValue", source
								.getInitialValue()),
						//
						AnnotationUtils.create("allocationSize", source
								.getAllocationSize()));
	}
}
