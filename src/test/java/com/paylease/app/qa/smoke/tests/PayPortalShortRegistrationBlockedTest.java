package com.paylease.app.qa.smoke.tests;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.registration.PayPortalChooseUserPage;
import com.paylease.app.qa.framework.pages.registration.PayPortalShortAccountRegPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PayPortalShortRegistrationBlockedTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "registration";

  @Test(groups = {"smoke"})
  public void payPortalShortRegistrationBlockedTest() {
    Logger.info("Verify if Pay Portal short registration is blocked");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "pp_tc2");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String accountNumber = testSetupPage.getString("accountNumber");
    final String residentId = testSetupPage.getString("residentId");

    PayPortalShortAccountRegPage payPortalRegPage = new PayPortalShortAccountRegPage(pmId);

    payPortalRegPage.open();
    payPortalRegPage.enterAccountNumber(accountNumber);

    PayPortalChooseUserPage payPortalChooseUserPage = payPortalRegPage.clickOnProceedButton();

    payPortalChooseUserPage.fillAndSubmit(residentId);

    Assert.assertTrue(payPortalChooseUserPage.getErrorMessage()
            .contains("Account is blocked - contact your Property Management company."),
        "In case of a blocked user, error message must show");
  }

}
