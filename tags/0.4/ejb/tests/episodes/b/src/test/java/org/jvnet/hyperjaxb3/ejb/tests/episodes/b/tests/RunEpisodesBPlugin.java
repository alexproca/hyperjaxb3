package org.jvnet.hyperjaxb3.ejb.tests.episodes.b.tests;

import org.jvnet.hyperjaxb3.maven2.Hyperjaxb3Mojo;
import org.jvnet.hyperjaxb3.maven2.ejb.test.RunEjbHyperjaxb3Mojo;

public class RunEpisodesBPlugin extends RunEjbHyperjaxb3Mojo {

	@Override
	protected void configureHyperjaxb3Mojo(Hyperjaxb3Mojo mojo) {
		super.configureHyperjaxb3Mojo(mojo);

//		final org.jvnet.jaxb2.maven2.Artifact episode = new org.jvnet.jaxb2.maven2.Artifact();
//
//		episode.setGroupId("org.jvnet.hyperjaxb3");
//		episode.setArtifactId("hyperjaxb3-ejb-tests-episodes-a");
//		episode.setVersion("0.4-SNAPSHOT");
//		mojo.setEpisodes(new org.jvnet.jaxb2.maven2.Artifact[] { episode });
	}
}
