package org.jvnet.hyperjaxb3.ejb.strategy.model;

import com.sun.tools.xjc.model.Model;

public interface ModelProcessor<T, C> {

	public T process(C context, Model model);

}
