package org.jvnet.hyperjaxb2.verylong.test;

import java.io.File;
import java.util.List;

import org.jvnet.jaxbcommons.addon.tests.AbstractAddOnTest;

public class RunAddOn extends AbstractAddOnTest {

  public List getAddonOptions() {
    final List options = super.getAddonOptions();
    options.add("-Xequals");
    options.add("-XhashCode");
    options.add("-Xhyperjaxb2");
    final File hj2NamesFile = new File(getBaseDir() + "/src/main/resources/hyperjaxb2.names.properties");
    options.add("-Xhyperjaxb2-principalStrategy.namingStrategy.namesFile="
        + hj2NamesFile.getAbsolutePath());
    return options;
  }
}
