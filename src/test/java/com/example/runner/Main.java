package com.example.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * The main class which serves as entry point for execution via jar
 */
public class Main {

  public static void main(String[] args) {
    try {
      FileUtils.cleanDirectory(new File("./target"));
    } catch (Exception e) {
      if (e.getMessage().contains("Unable to delete file: .\\target\\Log.log")) {
        throw new RuntimeException(
            "Failed to clean target directory. It appears that something is open from target. "
                + "Alternately you may manually delete target directory");
      }
    }

    XmlSuite xmlSuite = new XmlSuite();
    xmlSuite.setName("My Test Suite");

    XmlTest xmlTest = new XmlTest(xmlSuite);
    xmlTest.setName("My Tests");

    XmlPackage xmlPackage = new XmlPackage();
    xmlPackage.setName("com.example.tests.*");
    xmlTest.setXmlPackages(Collections.singletonList(xmlPackage));

    TestNG testNG = new TestNG();
    testNG.setXmlSuites(Collections.singletonList(xmlSuite));
    testNG.setOutputDirectory("./target/test-output/");

    List<Class<? extends ITestNGListener>> listeners = new ArrayList<>();
    listeners.add(com.example.listener.TestListener.class);
    listeners.add(com.example.listener.AnnotationTransformer.class);
    listeners.add(com.example.listener.SuiteListener.class);
    listeners.add(com.example.listener.ExecutionListener.class);
    testNG.setListenerClasses(listeners);

    testNG.run();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      //
    }
  }

}
