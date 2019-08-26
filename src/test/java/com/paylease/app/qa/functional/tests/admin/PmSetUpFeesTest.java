package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.FlexibleFeeStructure;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.PmSetUpFees;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PmSetUpFeesTest extends ScriptBase {

  public static final String REGION = "admin";
  public static final String FEATURE = "PmSetUpFees";

  @Test
  public void testFlexibleFeesLink() {
    Logger.info(
        "To verify that the Flexible fee structure link is visible and takes you to "
            + "the appropriate page after clicking."
    );

    TestSetupPage testPage = new TestSetupPage(REGION, FEATURE, "tc6147");
    testPage.open();

    final int pmId = Integer.parseInt(testPage.getString("pmId"));

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    PmSetUpFees pmSetUpFees = new PmSetUpFees(pmId);
    pmSetUpFees.open();

    Assert.assertTrue(pmSetUpFees.isFlexibleFeeStructureLinkVisible(),
        "Flexible fee structure link should be present.");

    pmSetUpFees.clickFlexibleFeeStructure();

    FlexibleFeeStructure flexibleFeeStructure = new FlexibleFeeStructure(pmId);

    Assert.assertTrue(flexibleFeeStructure.pageIsLoaded(),
        "Url for flexible fee structure has to be correct.");
  }
}
