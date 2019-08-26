package com.paylease.app.qa.e2e.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.AdminHomePage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchPmResultsPage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchUsersResultsPage;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.VariableAutoPayTestDataProvider;
import org.testng.annotations.Test;

public class VariableAutoPayTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "variableAutopay";

  //--------------------------------VARIABLE AUTOPAY TESTS------------------------------------------

  @Test(dataProvider = "vapDataPm", dataProviderClass = VariableAutoPayTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void vapAdminPm(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, boolean isMaxLimitChecked, String paymentType,
      boolean expressPay) {
    Logger.info("PM vap, where test variation: " + testVariationNo + " with " + paymentType
        + ", entry point: " + entryPoint + ", express pay: " + expressPay + ", frequency: "
        + frequency + ", indefinite: " + isIndefiniteChecked + " and Max Limit: "
        + isMaxLimitChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupVapFlowPm");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String residentId = testSetupPage.getString("residentId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchPm(pmId);

    AdminSearchPmResultsPage adminSearchPmResultsPage = new AdminSearchPmResultsPage();
    adminSearchPmResultsPage.logAsPm(pmId);

    com.paylease.app.qa.e2e.tests.pm.VariableAutoPayTest variableAutoPayTest =
        new com.paylease.app.qa.e2e.tests.pm.VariableAutoPayTest();

    variableAutoPayTest
        .pmVapPaymentActions(residentId, entryPoint, frequency, isIndefiniteChecked,
            isMaxLimitChecked, paymentType, expressPay);
  }

  @Test(dataProvider = "vapDataResident", dataProviderClass = VariableAutoPayTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void vapAdminResident(String testVariationNo, String entryPoint, String frequency,
      boolean isIndefiniteChecked, boolean isMaxLimitChecked, String paymentType,
      boolean expressPay) {
    Logger.info("Resident vap, where test variation: " + testVariationNo + " with "
        +  paymentType + ", entry point: " + entryPoint + ", express pay: " + expressPay
        + ", frequency: " + frequency + ", indefinite: " + isIndefiniteChecked + " and Max Limit: "
        + isMaxLimitChecked);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "testSetupVapFlowResident");
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchResident(residentId);

    AdminSearchUsersResultsPage adminSearchUsersResultsPage = new AdminSearchUsersResultsPage();
    adminSearchUsersResultsPage.logAsUser(residentId);

    com.paylease.app.qa.e2e.tests.resident.VariableAutoPayTest variableAutoPayTest =
        new com.paylease.app.qa.e2e.tests.resident.VariableAutoPayTest();

    variableAutoPayTest
        .residentVapPaymentActions(entryPoint, frequency, isIndefiniteChecked, isMaxLimitChecked,
            paymentType, expressPay);
  }
}
