package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmInvoices;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InvoicesTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "Invoices";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    Logger.info("The PM should see status as listeners");

    verifyStatusMessage("tc1", "RETRY");
  }

  @Test
  public void oakwood_tc1() {
    Logger.info("The PM should see status as listeners - Oakwood");

    verifyStatusMessage("oakwood_tc1", "RETRY");
  }

  @Test
  public void tc2() {
    Logger.info("The PM should see status as processing");

    verifyStatusMessage("tc2", "PROCESSED");
  }

  @Test
  public void oakwood_tc2() {
    Logger.info("The PM should see status as processing - Oakwood");

    verifyStatusMessage("oakwood_tc2", "PROCESSED");
  }

  @Test
  public void tc3() {
    Logger.info("The PM should see status as created");

    verifyStatusMessage("tc3", "CREATED");
  }

  @Test
  public void oakwood_tc3() {
    Logger.info("The PM should see status as created - Oakwood");

    verifyStatusMessage("oakwood_tc3", "CREATED");
  }

  @Test
  public void tc4() {
    Logger.info("The PM should see invoice has been marked for reprocessing in pop up");

    verifyStatusPopUpMessage("tc4",
        "Invoice has been marked for re-processing.");
  }

  @Test
  public void oakwood_tc4() {
    Logger.info("The PM should see invoice has been marked for reprocessing in pop up - Oakwood");

    verifyStatusPopUpMessage("oakwood_tc4",
        "Invoice has been marked for re-processing.");
  }

  @Test
  public void tc5() {
    Logger.info("The PM should see invoice has been marked for reprocessing in pop up");

    verifyStatusPopUpMessage("tc5", "Invoice processed successfully.");
  }

  @Test
  public void oakwood_tc5() {
    Logger.info("The PM should see invoice has been marked for reprocessing in pop up");

    verifyStatusPopUpMessage("oakwood_tc5",
        "Invoice processed successfully.");
  }

  @Test
  public void tc6() {
    Logger.info("The PM should see invoice has been marked for processing in pop up");

    verifyStatusPopUpMessage("tc6", "Invoice has not yet been processed.");
  }

  @Test
  public void oakwood_tc6() {
    Logger.info("The PM should see invoice has been marked for processing in pop up - Oakwood");

    verifyStatusPopUpMessage("oakwood_tc6",
        "Invoice has not yet been processed.");
  }

  @Test
  public void tc7() {
    Logger.info("The PM should see the Pay now link under Pay Invoice - Retry");

    verifyPayNowLinkExists("tc7", true);
  }

  @Test
  public void oakwood_tc7() {
    Logger.info("The PM should see the Pay now link under Pay Invoice - Retry - Oakwood");

    verifyPayNowLinkExists("oakwood_tc7", true);
  }

  @Test
  public void tc8() {
    Logger.info("The PM should not see the Pay now link under Pay Invoice - Processing");

    verifyPayNowLinkExists("tc8", false);
  }

  @Test
  public void oakwood_tc8() {
    Logger.info("The PM should not see the Pay now link under Pay Invoice - Processing - Oakwood");

    verifyPayNowLinkExists("oakwood_tc8", false);
  }

  @Test
  public void tc9() {
    Logger.info("The PM should see the Pay now link under Pay Invoice - Created");

    verifyPayNowLinkExists("tc9", true);
  }

  @Test
  public void oakwood_tc9() {
    Logger.info("The PM should see the Pay now link under Pay Invoice - Created - Oakwood");

    verifyPayNowLinkExists("oakwood_tc9", true);
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private PmInvoices testPrep(String testCase) {

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String invoiceId = testSetupPage.getString("invoiceId");

    PmInvoices pmInvoices = new PmInvoices();

    pmInvoices.open();
    pmInvoices.searchByInvoiceId(invoiceId);

    return new PmInvoices();
  }

  private void verifyStatusMessage(String testCase, String status) {
    PmInvoices pmInvoices = testPrep(testCase);

    Assert.assertEquals(pmInvoices.getStatusText(), status, "Status should match");
  }

  private void verifyStatusPopUpMessage(String testCase, String popUpMessage) {
    PmInvoices pmInvoices = testPrep(testCase);

    pmInvoices.mouseHoverOnStatus();

    Assert.assertEquals(pmInvoices.getStatusQtipText(),
        popUpMessage, "Status qTip text should match");
  }

  private void verifyPayNowLinkExists(String testCase, boolean linkPresent) {
    PmInvoices pmInvoices = testPrep(testCase);

    if (linkPresent) {
      Assert.assertTrue(pmInvoices.isPayNowLinkPresent(), "Pay now link should be present");
    } else {
      Assert.assertFalse(pmInvoices.isPayNowLinkPresent(), "Pay now link should not be present");
    }
  }
}
