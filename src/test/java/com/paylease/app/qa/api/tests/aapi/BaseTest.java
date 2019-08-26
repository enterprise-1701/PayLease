package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.BaseApiTest;
import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.framework.UtilityManager;
import java.util.ArrayList;
import java.util.Date;
import org.testng.Assert;

public abstract class BaseTest extends BaseApiTest {
  private static final int MAX_VALID_PER_REQUEST = 1;

  protected static final String DATE_FORMAT = "MM/dd/yyyy";

  protected boolean executeTests(ArrayList<TestCaseCollection> testCaseCollections) {
    boolean result = super.executeTests(testCaseCollections);
    Assert.assertTrue(result, "Not all test cases were successful");
    return true;
  }

  protected int getMaxValidPerRequest() {
    return MAX_VALID_PER_REQUEST;
  }

  protected String addDays(Date date, int daysToAdd) {

    UtilityManager utilityManager = new UtilityManager();

    Date newDate = utilityManager.addDays(date, daysToAdd);

    return utilityManager.dateToString(newDate, DATE_FORMAT);
  }
}
