package com.paylease.app.qa.e2e.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.AdminHomePage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchPmResultsPage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchUsersResultsPage;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.FixedAutoPayTestDataProvider;
import org.testng.annotations.Test;

public class FixedAutoPayTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "fixedAutopay";

  //--------------------------------FIXED AUTOPAY TESTS---------------------------------------------

  @Test(dataProvider = "fapDataPm", dataProviderClass = FixedAutoPayTestDataProvider.class, groups =
      {"e2e"}, retryAnalyzer = Retry.class)
  public void fapPmAdmin(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, String paymentType, boolean expressPay) {
    Logger.info("PM fap, where test variation: " + testVariationNo + " with " + paymentType
        + ", entry point: " + entryPoint + ", " + "express pay: " + expressPay + ", frequency: "
        + frequency + ", indefinite: " + isIndefiniteChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupFapFlowPm");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String residentId = testSetupPage.getString("residentId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchPm(pmId);

    AdminSearchPmResultsPage adminSearchPmResultsPage = new AdminSearchPmResultsPage();
    adminSearchPmResultsPage.logAsPm(pmId);

    com.paylease.app.qa.e2e.tests.pm.FixedAutoPayTest fixedAutoPayTest =
        new com.paylease.app.qa.e2e.tests.pm.FixedAutoPayTest();

    fixedAutoPayTest
        .pmFapPaymentActions(residentId, entryPoint, frequency, isIndefiniteChecked, paymentType,
            expressPay);
  }

  @Test(dataProvider = "fapDataResident", dataProviderClass = FixedAutoPayTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void fapResidentAdmin(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, String paymentType, boolean expressPay) {
    Logger.info("Resident fap, where test variation: " + testVariationNo + " with " + paymentType
        + ", entry point: " + entryPoint + ", express pay: " + expressPay + ", frequency: "
        + frequency + ", indefinite: " + isIndefiniteChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupFapFlowResident");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchResident(residentId);

    AdminSearchUsersResultsPage adminSearchUsersResultsPage = new AdminSearchUsersResultsPage();
    adminSearchUsersResultsPage.logAsUser(residentId);

    com.paylease.app.qa.e2e.tests.resident.FixedAutoPayTest fixedAutopayTest =
        new com.paylease.app.qa.e2e.tests.resident.FixedAutoPayTest();

    fixedAutopayTest
        .residentFapPaymentActions(entryPoint, frequency, isIndefiniteChecked, paymentType,
            expressPay);
  }
}
