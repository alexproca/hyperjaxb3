package org.jvnet.hyperjaxb3.maven2.tests;

import java.io.File;

import org.apache.maven.project.MavenProject;
import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;
import org.jvnet.jaxb2.maven2.XJC2Mojo;
import org.jvnet.jaxb2.maven2.test.RunXJC2Mojo;

public class Hyperjaxb3MojoTest extends RunXJC2Mojo {

	@Override
	protected XJC2Mojo createMojo() {
		return new Hyperjaxb3Mojo();
	}

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		super.configureMojo(mojo);
		configureHyperjaxb3Mojo((Hyperjaxb3Mojo) mojo);

	}

	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		mojo.setProject(new MavenProject());
		mojo.setDebug(true);
	}

	@Override
	public File getSchemaDirectory() {
		return new File(getBaseDir(), "src/test/resources");
	}
}
