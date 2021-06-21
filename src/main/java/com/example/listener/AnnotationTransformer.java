package com.example.listener;

import com.example.base.ExcelManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {

  private static final Logger LOGGER = LogManager.getLogger(AnnotationTransformer.class);

  @Override
  public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    List<Map<String, String>> excelRow;
    try {
      excelRow = ExcelManager.getControllerRowsList();
      for (Map<String, String> stringStringMap : excelRow) {
        if (testMethod.getName().equalsIgnoreCase(stringStringMap.get("TestMethodName"))) {
          if (stringStringMap.get("Execute").equalsIgnoreCase("No")) {
            annotation.setEnabled(false);
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw new RuntimeException(e);
    }
  }
}
