package com.paylease.app.qa.framework.utility.listeners;

import com.paylease.app.qa.framework.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

public class Retry implements IRetryAnalyzer {

  private final Map<Integer, Integer> retryCount = new HashMap<>();

  @Override
  public boolean retry(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
      final String testName = getName(result);
      final int count = getRetryCount(result);
      final int maxRetriesAllowed = 2;
      if (count < maxRetriesAllowed) {
        retryCount.put(getId(result), count + 1);
        Logger.info(
            "Retrying test (attempt " + (count + 1) + "/" + maxRetriesAllowed + "): " + testName);
        return true;
      } else {
        Logger.error("Failing test after " + count + " retries: " + testName);
      }
    }

    return false;
  }

  private int getRetryCount(ITestResult result) {
    final int testId = getId(result);
    return retryCount.getOrDefault(testId, 0);
  }

  private String getName(ITestResult result) {
    final List<String> parameters = new ArrayList<>();
    if (result.getParameters() != null) {
      for (Object parameter : result.getParameters()) {
        if (parameter instanceof TestResult && ((TestResult) parameter).getStatus() < 0) {
          // TestResult.toString() will explode with status < 0, can't use the toString() method
          parameters.add(parameter.getClass().getName() + "@" + parameter.hashCode());
        } else {
          parameters.add(parameter == null ? "null" : parameter.toString());
        }
      }
    }

    return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod()
        .getMethodName() + "("
        + StringUtils.join(parameters, ",") + ")";
  }

  private int getId(ITestResult result) {
    final HashCodeBuilder builder = new HashCodeBuilder();
    builder.append(result.getTestClass().getRealClass());
    builder.append(result.getMethod().getMethodName());
    builder.append(result.getParameters());
    return builder.toHashCode();
  }
}