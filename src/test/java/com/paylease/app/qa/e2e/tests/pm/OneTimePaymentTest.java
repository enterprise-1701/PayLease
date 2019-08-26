package com.paylease.app.qa.e2e.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.OneTimePaymentDataProvider;
import java.util.ArrayList;
import java.util.Collections;
import org.testng.annotations.Test;

public class OneTimePaymentTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "otp";

  //--------------------------------ONE TIME PAYMENT FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpDataPm", dataProviderClass = OneTimePaymentDataProvider.class, groups =
      {"e2e"}, retryAnalyzer = Retry.class)
  public void otpPm(String testVariationNo, String testCase, String paymentType,
      boolean useResidentList,
      boolean expressPay) {
    Logger.info("PM otp, where test variation: " + testVariationNo + " with " + paymentType
        + " where resident list page being used is " + useResidentList
        + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");
    final String residentId = testSetupPage.getString("residentId");

    ArrayList<String> paymentFieldList = new ArrayList<>();
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField3Label"));
    Collections.sort(paymentFieldList);

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PaymentBase paymentBase = new PaymentBase();

    paymentBase.pmOtPaymentActions(paymentType, useResidentList, expressPay, residentId,
        paymentFieldList);
  }
}
