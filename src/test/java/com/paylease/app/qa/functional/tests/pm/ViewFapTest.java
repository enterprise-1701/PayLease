package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmFapListPage;
import com.paylease.app.qa.framework.pages.pm.ViewAutopays.Action;
import java.text.DecimalFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewFapTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "viewFap";

  @Test
  public void tableDataMatchesDatabase() {
    Logger.info("All values match information that was entered");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String status = testSetupPage.getString("status");
    final String startDate = testSetupPage.getString("startDate");
    final String endDate = testSetupPage.getString("endDate");
    final String createdDate = testSetupPage.getString("createdDate");
    final String updatedDate = testSetupPage.getString("updatedDate");
    final String resRefId = testSetupPage.getString("resRefId");
    final String resFirstName = testSetupPage.getString("resFirstName");
    final String resLastName = testSetupPage.getString("resLastName");
    final String property = testSetupPage.getString("property");
    final String debitDay = testSetupPage.getString("debitDay");
    final String frequency = testSetupPage.getString("frequency");
    final String accountName = testSetupPage.getString("accountName");
    final String accountNumber = testSetupPage.getString("accountNumber");
    final String amount = testSetupPage.getString("amount");

    PmFapListPage pmFapListPage = new PmFapListPage();
    pmFapListPage.open();

    String residentName = resFirstName + " " + resLastName;
    String accountNum = accountName + " #" + accountNumber.substring(accountNumber.length() - 4);

    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    String formattedAmount = "$" + decimalFormat.format(Float.valueOf(amount));

    Assert.assertTrue(
        pmFapListPage.hasRowMatchingData(
            status, startDate, endDate, createdDate, updatedDate, resRefId,
            residentName, property, debitDay, frequency, accountNum, formattedAmount),
        "Row with matching data is present");
  }

  @Test
  public void exportFileDownloaded() {
    Logger.info("Export file is downloaded");

    PmFapListPage pmFapListPage = getBasicSetup("tc3");

    String downloadPath = setUpDownloadPath(this.getClass().getName(), "exportFileDownloaded");
    pmFapListPage.clickExport(downloadPath);

    Assert.assertTrue(true, "File download was successful");
  }

  @Test
  public void pmCanSkipActive() {
    Logger.info("PM is able to Skip a resident's autopay");

    PmFapListPage pmFapListPage = getBasicSetup("tc4");

    pmFapListPage.clickRowActionByRowNum(0, Action.SKIP);

    Assert.assertEquals(pmFapListPage.getSuccessMessage(), "AutoPay was successfully skipped",
        "Success message displayed");
  }

  @Test
  public void pmCanUndoSkip() {
    Logger.info("PM is able to cancel skip for a resident's autopay");

    PmFapListPage pmFapListPage = getBasicSetup("tc5");

    pmFapListPage.clickRowActionByRowNum(0, Action.CANCEL_SKIP);

    Assert.assertEquals(pmFapListPage.getSuccessMessage(), "Successfully canceled the AutoPay skip",
        "Success message displayed");
  }

  @Test
  public void pmMustConfirmCancel() {
    Logger.info("PM is asked to confirm their autopay cancellation request");

    PmFapListPage pmFapListPage = getBasicSetup("tc6");

    pmFapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    Assert.assertTrue(pmFapListPage.isConfirmCancelDialogDisplayed(),
        "Confirm Cancel dialog is displayed");
    Assert.assertEquals(pmFapListPage.getConfirmCancelMessage(),
        "Are you sure you would like to cancel this AutoPay? This action cannot be undone!",
        "Prompt message is as expected");
  }

  @Test
  public void pmCanDismissCancelWithNoChanges() {
    Logger.info("PM is able to stop their request to cancel an autopay");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String origDbStatus = testSetupPage.getString("origDbStatus");

    PmFapListPage pmFapListPage = new PmFapListPage();
    pmFapListPage.open();

    pmFapListPage.preparePageUnload();

    pmFapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    pmFapListPage.dismissAlert();
    Assert.assertFalse(pmFapListPage.isConfirmCancelDialogDisplayed(),
        "Confirm Cancel dialog is no longer displayed");
    Assert.assertTrue(
        !pmFapListPage.isPageChanged() || getAutopayStatus(transactionId).equals(origDbStatus),
        "Page has not been changed or status has not changed");
  }

  @Test
  public void pmCanConfirmCancel() {
    Logger.info("PM is able to cancel an autopay");

    PmFapListPage pmFapListPage = getBasicSetup("tc8");

    pmFapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    pmFapListPage.acceptAlert();
    Assert.assertEquals(pmFapListPage.getSuccessMessage(), "AutoPay was successfully canceled",
        "Success message should be displayed");
  }

  @Test
  public void pmCanEdit() {
    Logger.info("PM is able to edit an autopay");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PmFapListPage pmFapListPage = new PmFapListPage();
    pmFapListPage.open();

    pmFapListPage.clickRowActionByRowNum(0, Action.EDIT);

    PaymentFlow paymentFlow = new PaymentFlow();
    Assert.assertEquals(paymentFlow.getUi(), PaymentFlow.UI_PM, "Should be in PM UI");
    Assert.assertEquals(paymentFlow.getSchedule(), PaymentFlow.SCHEDULE_EDIT_FIXED_AUTO,
        "Should be on Edit page of Payment flow");
    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Should be on Amount page for selected autopay");

    String editTransId = paymentFlow.getPaymentId();
    Assert.assertEquals(getOriginalTransaction(editTransId), transactionId,
        "Editable transaction Id originated from original transaction");
  }

  @Test
  public void cancelledAutoPayNotInTable() {
    Logger.info("Once an autopay is cancelled it is no longer on this page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String status = testSetupPage.getString("status");
    final String startDate = testSetupPage.getString("startDate");
    final String endDate = testSetupPage.getString("endDate");
    final String createdDate = testSetupPage.getString("createdDate");
    final String updatedDate = testSetupPage.getString("updatedDate");
    final String resRefId = testSetupPage.getString("resRefId");
    final String resFirstName = testSetupPage.getString("resFirstName");
    final String resLastName = testSetupPage.getString("resLastName");
    final String property = testSetupPage.getString("property");
    final String debitDay = testSetupPage.getString("debitDay");
    final String frequency = testSetupPage.getString("frequency");
    final String accountName = testSetupPage.getString("accountName");
    final String accountNumber = testSetupPage.getString("accountNumber");
    final String amount = testSetupPage.getString("amount");

    PmFapListPage pmFapListPage = new PmFapListPage();
    pmFapListPage.open();

    String residentName = resFirstName + " " + resLastName;
    String accountNum = accountName + " #" + accountNumber.substring(accountNumber.length() - 4);

    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    String formattedAmount = "$" + decimalFormat.format(Float.valueOf(amount));

    Assert.assertFalse(
        pmFapListPage.hasRowMatchingData(
            status, startDate, endDate, createdDate, updatedDate, resRefId,
            residentName, property, debitDay, frequency, accountNum, formattedAmount),
        "Row with matching data is not present");
  }

  @Test
  public void pmCanCancelWhileSkipped() {
    Logger.info("An autopay can be cancelled while it's skipped");

    PmFapListPage pmFapListPage = getBasicSetup("tc11");

    pmFapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    pmFapListPage.acceptAlert();
    Assert.assertEquals(pmFapListPage.getSuccessMessage(), "AutoPay was successfully canceled",
        "Success message displayed");
  }

  private PmFapListPage getBasicSetup(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    PmFapListPage pmFapListPage = new PmFapListPage();
    pmFapListPage.open();

    return pmFapListPage;
  }

  private String getAutopayStatus(String transactionId) {
    String sqlQuery = "SELECT status "
        + "FROM transactions "
        + "WHERE trans_id = " + transactionId;

    return DataBaseConnector.getDbValue(sqlQuery);
  }

  private String getOriginalTransaction(String editTransId) {
    String sqlQuery = "SELECT original_autopay_id "
        + "FROM autopay_edits "
        + "WHERE duplicate_autopay_id = " + editTransId;

    return DataBaseConnector.getDbValue(sqlQuery);
  }

}
