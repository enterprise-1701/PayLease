package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.PmSettingsPage;
import com.paylease.app.qa.framework.pages.automatedhelper.CustomizationPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PmSettingsTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "PmSettings";

  private static final String BLOCK_PAYMENTS_CUSTOMIZATION = "BLOCK_PAYMENTS";

  @DataProvider(parallel = true)
  private Object[][] provideCustomizationValuesToChange() {
    return new Object[][]{
        {"tc6050", true},
        {"tc6056", false},
    };
  }

  @Test(dataProvider = "provideCustomizationValuesToChange", retryAnalyzer = Retry.class)
  public void changeBlockPaymentsInAdminSettings(String testCaseId, boolean customizationValue)
      throws Exception {
    Logger.info(
        testCaseId + " - Validate changing the block payment setting in admin changes the customization value."
    );

    final String pmId = loginPm(testCaseId);

    PmSettingsPage pmSettingsPage = new PmSettingsPage(pmId);
    pmSettingsPage.open();

    pmSettingsPage.updateStopAcceptingPayment(customizationValue);
    pmSettingsPage.clickUpdate();

    CustomizationPage customizationPage = new CustomizationPage(pmId, BLOCK_PAYMENTS_CUSTOMIZATION);
    customizationPage.open();

    Assert.assertEquals(customizationPage.isCustomizationEnabled(), customizationValue,
        "Stop accepting payments customization should have been changed.");
  }

  @DataProvider(parallel = true)
  private Object[][] provideCustomizationValuesToRead() {
    return new Object[][]{
        {"tc6051", true},
        {"tc6057", false},
    };
  }

  @Test(dataProvider = "provideCustomizationValuesToRead", retryAnalyzer = Retry.class)
  public void adminSettingsDisplayCorrectSetting(String testCaseId, boolean customizationValue) {
    Logger.info(
        testCaseId + " - Validate admin setting shows the correct value of the customization.");

    final String pmId = loginPm(testCaseId);

    PmSettingsPage pmSettingsPage = new PmSettingsPage(pmId);
    pmSettingsPage.open();

    Assert.assertEquals(pmSettingsPage.isStopAcceptingPaymentTurnedOn(), customizationValue,
        "Stop accepting payments customization should have been read correctly.");
  }

  private String loginPm(String testCaseId) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    return pmId;
  }
}
