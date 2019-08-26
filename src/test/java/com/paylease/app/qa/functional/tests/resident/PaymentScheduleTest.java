package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentScheduleTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "paymentSchedule";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    Logger.info("Verify credit reporting optin container is not present");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    PaymentFlow paymentFlow = new PaymentFlow(
        PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_FIXED_AUTO, null);
    paymentFlow.openStep(PaymentFlow.STEP_SCHEDULE);

    CreditReporting creditReporting = new CreditReporting();

    Assert.assertFalse(creditReporting.isOptinContainerPresent(),
        "Optin container should not be present");
  }
}
