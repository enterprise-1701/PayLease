package com.paylease.app.qa.api.tests;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseApiTest extends ScriptBase {

  protected abstract int getMaxValidPerRequest();

  protected boolean isCollectionValid(TestCaseCollection testCaseCollection, Response response) {

    boolean testResult = true;
    for (ApiTestCase testCase : testCaseCollection.getTestCases()) {
      boolean result = testCase.test(response);
      if (!result) {
        testResult = false;
      }
    }

    return testResult;
  }

  protected boolean executeTests(TestCaseCollection testCaseCollection) {
    int testCasesProcessed = 0;
    boolean result = true;
    ArrayList<ApiTestCase> testCases = testCaseCollection.getTestCases();

    while (testCasesProcessed < testCases.size()) {
      try {
        Request apiRequest = testCaseCollection.getRequest();
        TestCaseCollection testCasesThisRequest = new TestCaseCollection(testCaseCollection);

        for (int testCaseCount = 0; testCaseCount < getMaxValidPerRequest(); testCaseCount++) {
          ApiTestCase testCase = testCases.get(testCasesProcessed);
          testCase.setIndex(testCaseCount + 1);
          testCasesThisRequest.addTestCase(testCase);
          testCase.addTransaction(apiRequest);

          if (++testCasesProcessed >= testCases.size()) {
            break;
          }
        }

        Logger.trace(
            "API request includes " + testCasesThisRequest.getTestCases().size() + " transactions");
        Response response = apiRequest.sendRequest();
        result = isCollectionValid(testCasesThisRequest, response) && result;

      } catch (Exception e) {
        Logger.error(e.toString());
        return false;
      }
    }
    return result;
  }

  protected boolean executeTests(ArrayList<TestCaseCollection> testCaseCollections) {
    boolean result = true;
    for (TestCaseCollection testCaseCollection : testCaseCollections) {
      result = executeTests(testCaseCollection) && result;
    }
    return result;
  }

  protected ExpectedResponse getExpectedResponse(
      List<HashMap<String, Object>> messageTable, String code
  ) {
    return ExpectedResponse.createFromSetupTable(messageTable, code);
  }
}
