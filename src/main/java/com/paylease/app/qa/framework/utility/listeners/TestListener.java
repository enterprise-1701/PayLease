package com.paylease.app.qa.framework.utility.listeners;

import java.util.Set;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

  @Override
  public void onStart(ITestContext iTestContext) {

  }

  @Override
  public void onFinish(ITestContext context) {
    Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();

    for (ITestResult temp : skippedTests) {
      ITestNGMethod method = temp.getMethod();
      //If the method is not a @Test annotated method, then lets remove that from the skipped lists
      if (!method.isTest()) {
        skippedTests.remove(temp);
        continue;
      }
      if (context.getSkippedTests().getResults(method).size() > 1) {
        skippedTests.remove(temp);
      } else {
        if (context.getPassedTests().getResults(method).size() > 0) {
          skippedTests.remove(temp);
        } else {
          if (context.getFailedTests().getResults(method).size() > 0) {
            skippedTests.remove(temp);
          }
        }
      }
    }
  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {

  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {

  }

  public void onTestStart(ITestResult iTestResult) {

  }

  public void onTestSkipped(ITestResult iTestResult) {

  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

  }
}
