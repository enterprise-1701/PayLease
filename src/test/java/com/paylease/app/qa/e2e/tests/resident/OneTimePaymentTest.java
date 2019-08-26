package com.paylease.app.qa.e2e.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResidentLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.PaymentBase;
import com.paylease.app.qa.testbase.dataproviders.OneTimePaymentDataProvider;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OneTimePaymentTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "otp";

  private String paymentAmount;
  private String feeAmount;

  //--------------------------------ONE TIME PAYMENT FLOW TESTS-------------------------------------

  @Test(dataProvider = "otpDataResident", dataProviderClass = OneTimePaymentDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void otpResident(String testVariationNo, String testCase, String paymentType,
      boolean useMakePayment, boolean expressPay) {
    Logger.info("Resident , where test variation and test case: " + testVariationNo + "," + testCase
        + " with " + paymentType + " where resident list page being used is "
        + useMakePayment + " and using express pay is " + expressPay);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String residentEmail = testSetupPage.getString("residentEmail");

    residentOtPaymentActions(residentEmail, paymentType, useMakePayment, expressPay, null);
  }
  //--------------------------------TEST METHOD-----------------------------------------------------

  /**
   * Perform Resident otp actions.
   *
   * @param paymentType payment type
   * @param useMakePayment boolean use make payment or not
   * @param expressPay express pay
   */
  public String residentOtPaymentActions(String residentEmail, String paymentType,
      boolean useMakePayment, boolean expressPay, ArrayList<String> paymentFieldList) {
    if (null != residentEmail) {
      ResLoginPage resLoginPage = new ResLoginPage();

      resLoginPage.open();
      resLoginPage.login(residentEmail, null);
    }

    PaymentBase paymentBase = new PaymentBase();

    if (useMakePayment) {
      ResidentMenuItems residentMenuItems = new ResidentMenuItems();

      residentMenuItems.clickMakePaymentTab();
      if (null == paymentFieldList) {
        paymentBase.fillAndSubmitPaymentAmount(false);
      } else {
        paymentBase.fillAndSubmitPaymentAmount(false, paymentFieldList);
      }
    } else {
      if (null == paymentFieldList) {
        paymentBase.fillAndSubmitPaymentAmount(true);
      } else {
        paymentBase.fillAndSubmitPaymentAmount(true, paymentFieldList);
      }
    }

    paymentBase.selectPaymentMethod(paymentType, expressPay);
    paymentBase.reviewAndSubmit();

    ReceiptPage receiptPage = new ReceiptPage();

    this.paymentAmount = receiptPage.getLeasePaymentAmount();
    this.feeAmount = receiptPage.getProcessingFeeAmount();

    Assert.assertTrue(receiptPage.pageIsLoaded(), "Should be on Receipt page");

    Assert.assertEquals((receiptPage.getTransactionStatus()), "Processing",
        "The transaction did not process successfully");

    String transId = receiptPage.getTransactionId();

    ResidentLogoutBar residentLogoutBar = new ResidentLogoutBar();

    residentLogoutBar.clickLogoutButton();
    return transId;
  }

  public String getPaymentAmount() {
    return paymentAmount;
  }

  public String getFeeAmount() {
    return feeAmount;
  }

}
