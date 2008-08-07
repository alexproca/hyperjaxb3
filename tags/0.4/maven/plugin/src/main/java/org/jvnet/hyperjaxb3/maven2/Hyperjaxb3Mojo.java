/*
 * Copyright [2006] java.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 		http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package org.jvnet.hyperjaxb3.maven2;

import java.io.File;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.NullAppender;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.jfrog.maven.annomojo.annotations.MojoGoal;
import org.jfrog.maven.annomojo.annotations.MojoParameter;
import org.jfrog.maven.annomojo.annotations.MojoPhase;
import org.jvnet.jaxb2.maven2.XJC2Mojo;

import com.sun.tools.xjc.Options;

@MojoGoal("generate")
@MojoPhase("generate-sources")
public class Hyperjaxb3Mojo extends XJC2Mojo {

	/**
	 * Target directory for the generated mappings. If left empty, mappings are
	 * generated together with sources.
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.target}")
	public File target;

	/**
	 * Name of the roundtrip test case. If omitted, no roundtrip test case is
	 * generated.
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.roundtripTestClassName}")
	public String roundtripTestClassName;

	/**
	 * Patterns for files to be included as resources.
	 * 
	 */
	@MojoParameter
	public String[] resourceIncludes = new String[] { "**/*.hbm.xml",
			"**/*.orm.xml", "**/*.cfg.xml", "META-INF/persistence.xml" };

	/**
	 * Persistence variant. Switches between various persistence
	 * implementations. Possible values are "hibernate" and "ejb-hibernate".
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.variant}", defaultValue = "ejb")
	public String variant = "ejb";

	/**
	 * 
	 * Persistence unit name (EJB3 specific).
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.persistenceUnitName}")
	public String persistenceUnitName;

	/**
	 * 
	 * Persistence unit name (EJB3 specific).
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.persistenceXml}")
	public File persistenceXml;

	/**
	 * 
	 * Whether the <code>hashCode()</code> method should be generated.
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.generateHashCode}", defaultValue = "true")
	public boolean generateHashCode = true;

	/**
	 * 
	 * Whether the <code>equals(...)</code> methods should be generated.
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.generateEquals}", defaultValue = "true")
	public boolean generateEquals = true;

	/**
	 * 
	 * Whether the generated id property must be transient.
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.generateTransientId}", defaultValue = "false")
	public boolean generateTransientId = false;

	/**
	 * Generation result. Possible values are "annotations", "mappingFiles".
	 * 
	 */
	@MojoParameter(expression = "${maven.hj3.result}", defaultValue = "annotations")
	public String result = "annotations";

	/**
	 * Sets up the verbose and debug mode depending on mvn logging level, and
	 * sets up hyperjaxb logging.
	 */
	protected void setupLogging() {
		super.setupLogging();

		final Logger rootLogger = LogManager.getRootLogger();
		rootLogger.addAppender(new NullAppender());
		final Logger logger = LogManager.getLogger("org.jvnet.hyperjaxb3");

		final Log log = getLog();
		logger.addAppender(new Appender(getLog(), new PatternLayout(
				"%m%n        %c%n")));

		if (this.getDebug()) {
			log.debug("Logger level set to [debug].");
			logger.setLevel(Level.DEBUG);
		} else if (this.getVerbose())
			logger.setLevel(Level.INFO);
		else if (log.isWarnEnabled())
			logger.setLevel(Level.WARN);
		else
			logger.setLevel(Level.ERROR);
	}

	/**
	 * Logs options defined directly as mojo parameters.
	 */
	protected void logUserSettings(StringBuffer sb) {
		sb.append("\n\ttarget: " + target);
		sb.append("\n\troundtripTest: " + roundtripTestClassName);
		sb.append("\n\tresourceIncludes: "
				+ recursiveToString(resourceIncludes));
		sb.append("\n\tvariant: " + variant);
		super.logUserSettings(sb);
	}

	/**
	 * Ensure the any default settings are met and throws exceptions when
	 * settings are invalid and also stores the schemas and the bindings files
	 * into member vars for calculating timestamps, later.
	 * 
	 * Exception will cause build to fail.
	 * 
	 * @throws MojoExecutionException
	 */
	protected void setupCmdLineArgs(Options xjcOpts)
			throws MojoExecutionException {
		if ("ejb".equals(variant)) {
			getArgs().add("-Xhyperjaxb3-ejb");

			if (result != null) {
				getArgs().add("-Xhyperjaxb3-ejb-result=" + result);
			}

			if (roundtripTestClassName != null) {
				getArgs().add(
						"-Xhyperjaxb3-ejb-roundtripTestClassName="
								+ roundtripTestClassName);
			}
			if (persistenceUnitName != null) {
				getArgs().add(
						"-Xhyperjaxb3-ejb-persistenceUnitName="
								+ persistenceUnitName);
			}
			if (persistenceXml != null) {
				getArgs().add(
						"-Xhyperjaxb3-ejb-persistenceXml="
								+ persistenceXml.getAbsolutePath());
			}

			if (generateTransientId) {
				getArgs().add("-Xhyperjaxb3-ejb-generateTransientId=true");
			}

		}

		if (generateEquals) {
			getArgs().add("-Xequals");
		}
		if (generateHashCode) {
			getArgs().add("-XhashCode");
		}

		super.setupCmdLineArgs(xjcOpts);
	}

	/**
	 * Updates XJC's compilePath ans resources and update hyperjaxb2's
	 * resources, that is, *.hbm.xml files and hibernate.config.xml file.
	 * 
	 * @param xjcOpts
	 * @throws MojoExecutionException
	 */
	protected void updateMavenPaths() {
		super.updateMavenPaths();

		final Resource resource = new Resource();
		resource.setDirectory(getGenerateDirectory().getPath());
		for (String resourceInclude : resourceIncludes) {
			resource.addInclude(resourceInclude);
		}
		getProject().addResource(resource);

		if (this.roundtripTestClassName != null) {
			getProject().addTestCompileSourceRoot(
					getGenerateDirectory().getPath());
		}
	}

	public static class Appender extends AppenderSkeleton {
		private final Log log;

		private final Layout layout;

		public Appender(final Log log, final Layout layout) {
			super();
			this.log = log;
			this.layout = layout;
		}

		@Override
		public boolean requiresLayout() {
			return true;
		}

		@Override
		protected void append(LoggingEvent event) {

			if (event.getLevel().equals(Level.TRACE)) {
				log.debug(layout.format(event));
			} else if (event.getLevel().equals(Level.DEBUG)) {
				log.debug(layout.format(event));
			} else if (event.getLevel().equals(Level.INFO)) {
				log.info(layout.format(event));
			} else if (event.getLevel().equals(Level.WARN)) {
				log.warn(layout.format(event));
			} else if (event.getLevel().equals(Level.ERROR)) {
				log.error(layout.format(event));
			} else if (event.getLevel().equals(Level.FATAL)) {
				log.error(layout.format(event));
			}
		}

		@Override
		public void close() {
		}
	}
}