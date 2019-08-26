package com.paylease.app.qa.e2e.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.AdminHomePage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchPmResultsPage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchUsersResultsPage;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.OneTimePaymentDataProvider;
import java.util.ArrayList;
import java.util.Collections;
import org.testng.annotations.Test;

public class OneTimePaymentTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "otp";

  //--------------------------------ONE TIME PAYMENT FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpDataAdminPm", dataProviderClass =
      OneTimePaymentDataProvider.class, groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void otpAdminPm(String testVariationNo, String testCase, String paymentType,
      boolean useResidentList, boolean expressPay) {
    Logger.info("PM otp, where test variation: " + testVariationNo + " with " + paymentType
        + " where resident list page being used is " + useResidentList
        + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String residentId = testSetupPage.getString("residentId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchPm(pmId);

    AdminSearchPmResultsPage adminSearchPmResultsPage = new AdminSearchPmResultsPage();
    adminSearchPmResultsPage.logAsPm(pmId);

    PaymentBase paymentBase = new PaymentBase();

    paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay, residentId,
        paymentFieldList);
  }

  @Test(dataProvider = "otpDataAdminRes", dataProviderClass = OneTimePaymentDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void otpAdminResident(String testVariationNo, String testCase, String paymentType,
      boolean useMakePayment, boolean expressPay) {
    Logger.info("Resident , where test variation: " + testVariationNo + " with " + paymentType
        + " where resident list page being used is "
        + useMakePayment + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String residentId = testSetupPage.getString("residentId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchResident(residentId);

    AdminSearchUsersResultsPage adminSearchUsersResultsPage = new AdminSearchUsersResultsPage();
    adminSearchUsersResultsPage.logAsUser(residentId);

    com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest oneTimePaymentTest =
        new com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest();

    oneTimePaymentTest
        .residentOtPaymentActions(null, paymentType, useMakePayment, expressPay, null);
  }
}

