package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryReceiptPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReceiptTest extends ScriptBase {

  private static final String REGION = "Resident";
  private static final String FEATURE = "Receipt";

  @DataProvider(parallel = true)
  private Object[][] customizationProvider() {
    return new Object[][]{
        //testCaseId
        {"tc4208"},
        {"tc4209"}
    };
  }

  @Test(dataProvider = "customizationProvider", retryAnalyzer = Retry.class)
  public void validateCheckImagesNotShownWhenCustOn(String testCaseId) {
    Logger.info(
        testCaseId + " - To validate the transaction receipt does not show the check images for the resident."
    );

    ResPaymentHistoryReceiptPage receiptPage = setupAndNavigateTo(testCaseId, false);

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(), "Front check image is not present");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(), "Back check image is not present");
  }

  @Test
  public void validateCheckImagesNotShownInMobileView() {
    Logger.info(
        "To validate the transaction receipt does not show the check images when in mobile view.");

    ResPaymentHistoryReceiptPage receiptPage = setupAndNavigateTo("tc4210", true);

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(), "Front check image is not present");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(), "Back check image is not present");
  }

  @Test
  public void validateCheckImagesNotShownWhenCustOnForAch() {
    Logger.info(
        "To validate the transaction receipt does not show the check images when customization is on for ACH transaction.");

    ResPaymentHistoryReceiptPage receiptPage = setupAndNavigateTo("tc4212", false);

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(), "Front check image is not present");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(), "Back check image is not present");
  }

  private ResPaymentHistoryReceiptPage setupAndNavigateTo(String testCaseId, boolean isMobile) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String resUsername = testSetupPage.getString("resUsername");

    ResLoginPage resLoginPage = new ResLoginPage();
    resLoginPage.open();
    resLoginPage.login(resUsername, null);

    ResPaymentHistoryReceiptPage receiptPage = new ResPaymentHistoryReceiptPage(transId);
    if (isMobile) {
      receiptPage.openMobile();
    } else {
      receiptPage.open();
    }

    return receiptPage;
  }
}