package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.BillingAndUem;
import java.util.HashMap;
import java.util.List;

public class ExpenseManagement extends BillingAndExpenseManagement {

  /**
   * Represents a section of the PayLease Application.
   */
  protected static final String REGION = "pm";
  /**
   * Represents a feature of the PayLease Application.
   */
  protected static final String FEATURE = "expenseManagement";

  /**
   * This method saves saves all property ids in props[] and ref ids in refs[].
   */
  protected List<HashMap<String, Object>> openAppWithSetupData(
      BillingAndUem billingAndExpenseManagement, String logMessage) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    List<HashMap<String, Object>> properties = testSetupPage.getTable("properties");
    Logger.info(logMessage);
    billingAndExpenseManagement.open();

    return properties;
  }
}
