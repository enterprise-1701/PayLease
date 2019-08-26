package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import org.testng.Assert;

public class CreditCardDetails extends ScriptBase {

  public void assertDisclosureMessage(
      String region, String feature, String testCase, String schedule, int step
  ) {
    TestSetupPage testSetupPage = new TestSetupPage(region, feature, testCase);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, schedule, transId);

    paymentFlow.openStep(step);

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

    Assert.assertFalse(
        cardAccountDetailsPage.getDisclosureMessage().isEmpty(), "Missing disclosure message"
    );

    cardAccountDetailsPage.clickDisclosureReadMoreButton();

    Assert.assertTrue(
        cardAccountDetailsPage.isSecondDisclosureMessageVisible(),
        "Second disclosure message not displayed"
    );
  }
}
