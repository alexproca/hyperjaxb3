package org.jvnet.hyperjaxb3.ejb.strategy.outline;

import java.util.Collection;

import org.jvnet.hyperjaxb3.ejb.plugin.EjbPlugin;
import org.jvnet.hyperjaxb3.ejb.strategy.customizing.Customizing;
import org.jvnet.hyperjaxb3.ejb.strategy.ignoring.Ignoring;
import org.jvnet.hyperjaxb3.ejb.strategy.naming.Naming;
import org.jvnet.jaxb2_commons.strategy.OutlineProcessor;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

/**
 * EJB outline processor. Processes the outline and returns the collection of
 * processed class outlines.
 * 
 * @param <C>
 *            Type of the context.
 */
public interface ProcessOutline extends
		OutlineProcessor<Collection<ClassOutline>, EjbPlugin> {

	public Collection<ClassOutline> process(EjbPlugin context,
			Outline outline, Options options);

	/**
	 * Principal processor for the class outlines.
	 * 
	 * @return Principal class outline processor.
	 */
	public ProcessClassOutline getProcessClassOutline();

	public ProcessFieldOutlines getProcessFieldOutlines();

	public ProcessFieldOutline getProcessFieldOutline();

	public Ignoring getIgnoring();

	public Annotate getAnnotate();

	public Naming getNaming();
	
	public Customizing getCustomizing();

}
