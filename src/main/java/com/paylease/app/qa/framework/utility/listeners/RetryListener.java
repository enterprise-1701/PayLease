package com.paylease.app.qa.framework.utility.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

public class RetryListener implements IAnnotationTransformer {

  /**
   * Retry analyzer.
   *
   * @param annotation test
   * @param testClass test class
   * @param testConstructor test constructor
   * @param testMethod test method
   */
  @Override
  public void transform(ITestAnnotation annotation, Class testClass,
      Constructor testConstructor, Method testMethod) {

    IRetryAnalyzer retry = annotation.getRetryAnalyzer();
    if (retry == null) {
      annotation.setRetryAnalyzer(Retry.class);
    }
  }
}

