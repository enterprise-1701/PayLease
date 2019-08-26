package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.SubAccountsPrivileges;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SubAccountsPrivilegesTest extends ScriptBase {

  public static final String REGION = "pm";
  public static final String FEATURE = "subAccountPrivileges";

  @Test
  public void viewCheckImagesOptionPresent() {
    Logger.info("Validate view check images option is present.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4214");
    testSetupPage.open();

    final String pmUsername = testSetupPage.getString("pmUsername");
    final String subAccId = testSetupPage.getString("subAccId");
    final String custCode = testSetupPage.getString("custCode");
    final String custLabel = testSetupPage.getString("custLabel");

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmUsername);

    SubAccountsPrivileges subAccountsPrivileges = new SubAccountsPrivileges(subAccId);
    subAccountsPrivileges.open();
    subAccountsPrivileges.clickPaymentsDrawerHeader();

    String expectedCode = subAccountsPrivileges.getCustCheckboxNameAttr("payments", custLabel);
    Assert.assertEquals(custCode, expectedCode, "View check images option should be present.");
  }

  @Test
  public void viewCheckImagesOptionNotPresent() {
    Logger.info("Validate view check images option is not present.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4215");
    testSetupPage.open();

    final String pmUsername = testSetupPage.getString("pmUsername");
    final String subAccId = testSetupPage.getString("subAccId");
    final String custCode = testSetupPage.getString("custCode");

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmUsername);

    SubAccountsPrivileges subAccountsPrivileges = new SubAccountsPrivileges(subAccId);
    subAccountsPrivileges.open();
    subAccountsPrivileges.clickPaymentsDrawerHeader();

    Assert.assertFalse(subAccountsPrivileges.isCustomizationAvailable(custCode),
        "View check images option should not be present.");
  }
}
