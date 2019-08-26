package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAddCardAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ManageCardAccountsTest extends ScriptBase {

  // maps to setup page in php
  private static final String REGION = "resident";
  private static final String FEATURE = "manageCardAccounts";

  @Test
  public void assertDisclosure() {
    Logger.info("Verify we see disclosure messages for new payment method - card");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc15");
    testSetupPage.open();

    ResidentAddCardAccountPage addCardAccountPage = new ResidentAddCardAccountPage();
    addCardAccountPage.open();

    Assert.assertFalse(
        addCardAccountPage.getDisclosureMessage().isEmpty(), "Missing disclosure message"
    );

    addCardAccountPage.clickDisclosureReadMoreButton();

    Assert.assertTrue(
        addCardAccountPage.isSecondDisclosureMessageVisible(),
        "Second disclosure message not displayed"
    );
  }
}
