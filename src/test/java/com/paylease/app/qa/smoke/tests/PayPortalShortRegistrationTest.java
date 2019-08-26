package com.paylease.app.qa.smoke.tests;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.registration.PayPortalChooseUserPage;
import com.paylease.app.qa.framework.pages.registration.PayPortalShortAccountRegPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PayPortalShortRegistrationTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "registration";

  @Test(groups = {"smoke"})
  public void payPortalShortRegistrationTest() {
    Logger.info("Verify Pay Portal short registration");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "pp_tc1");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String accountNumber = testSetupPage.getString("accountNumber");
    final String residentId = testSetupPage.getString("residentId");
    final String residentName = testSetupPage.getString("residentName");

    PayPortalShortAccountRegPage payPortalRegPage = new PayPortalShortAccountRegPage(
        pmId);

    payPortalRegPage.open();
    payPortalRegPage.enterAccountNumber(accountNumber);

    PayPortalChooseUserPage payPortalChooseUserPage = payPortalRegPage.clickOnProceedButton();

    payPortalChooseUserPage.fillAndSubmit(residentId);

    ResHomePage resHomePage = new ResHomePage();

    Assert.assertTrue(resHomePage.getWelcomeMessage().equals("Welcome: " + residentName),
        "Resident not registered");
  }

}
