package org.jvnet.hyperjaxb3.ejb.tests.po;

import org.apache.maven.model.Dependency;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;
import org.jvnet.jaxb2.maven2.AbstractXJC2Mojo;

public class RunPoPlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureMojo(AbstractXJC2Mojo mojo) {
		final Dependency dependency = new Dependency();
		dependency.setGroupId("org.jvnet.hyperjaxb3");
		dependency
				.setArtifactId("hyperjaxb3-ejb-tests-custom-naming-extension");
		dependency.setVersion("0.5.3-SNAPSHOT");
		mojo.setPlugins(new Dependency[] { dependency });
		super.configureMojo(mojo);
	}

}
