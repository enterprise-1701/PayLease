package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmVapListPage;
import com.paylease.app.qa.framework.pages.pm.ViewAutopays.Action;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewVapTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "viewVap";

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

    PmVapListPage pmVapListPage = new PmVapListPage();
    pmVapListPage.open();

    String residentName = resFirstName + " " + resLastName;
    String accountNum = accountName + " #" + accountNumber.substring(accountNumber.length() - 4);

    Assert.assertTrue(
        pmVapListPage.hasRowMatchingData(
            status, startDate, endDate, createdDate, updatedDate, resRefId,
            residentName, property, debitDay, frequency, accountNum, null),
        "Row with matching data is present");
  }

  @Test
  public void exportFileDownloaded() {
    Logger.info("Export file is downloaded");

    PmVapListPage pmVapListPage = getBasicSetup("tc3");

    String downloadPath = setUpDownloadPath(this.getClass().getName(), "exportFileDownloaded");
    pmVapListPage.clickExport(downloadPath);

    Assert.assertTrue(true, "File download was successful");
  }

  @Test
  public void pmCanSkipActive() {
    Logger.info("PM is able to Skip a resident's autopay");

    PmVapListPage pmVapListPage = getBasicSetup("tc4");

    pmVapListPage.clickRowActionByRowNum(0, Action.SKIP);

    Assert.assertEquals(pmVapListPage.getSuccessMessage(), "AutoPay was successfully skipped",
        "Success message displayed");
  }

  @Test
  public void pmCanUndoSkip() {
    Logger.info("PM is able to cancel skip for a resident's autopay");

    PmVapListPage pmVapListPage = getBasicSetup("tc5");

    pmVapListPage.clickRowActionByRowNum(0, Action.CANCEL_SKIP);

    Assert.assertEquals(pmVapListPage.getSuccessMessage(), "Successfully canceled the AutoPay skip",
        "Success message displayed");
  }

  @Test
  public void pmMustConfirmCancel() {
    Logger.info("PM is asked to confirm their autopay cancellation request");

    PmVapListPage pmVapListPage = getBasicSetup("tc6");

    pmVapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    Assert.assertTrue(pmVapListPage.isConfirmCancelDialogDisplayed(),
        "Confirm Cancel dialog is displayed");
    Assert.assertEquals(pmVapListPage.getConfirmCancelMessage(),
        "Are you sure you would like to cancel this AutoPay? This action cannot be undone!",
        "Prompt message is as expected");
  }

  @Test
  public void pmCanDismissCancelWithNoChanges() {
    Logger.info("PM is able to stop their request to cancel an autopay");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("autopayId");
    final String origDbStatus = "Active";

    PmVapListPage pmVapListPage = new PmVapListPage();
    pmVapListPage.open();

    pmVapListPage.preparePageUnload();

    pmVapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    pmVapListPage.dismissAlert();
    Assert.assertFalse(pmVapListPage.isConfirmCancelDialogDisplayed(),
        "Confirm Cancel dialog is no longer displayed");
    Assert.assertTrue(
        !pmVapListPage.isPageChanged() || getAutopayStatus(transactionId).equals(origDbStatus),
        "Page has not been changed or status has not changed");
  }

  @Test
  public void pmCanConfirmCancel() {
    Logger.info("PM is able to cancel an autopay");

    PmVapListPage pmVapListPage = getBasicSetup("tc8");

    pmVapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    pmVapListPage.acceptAlert();
    Assert.assertEquals(pmVapListPage.getSuccessMessage(), "AutoPay was successfully canceled",
        "Success message should be displayed");
  }

  @Test
  public void pmCanNotEdit() {
    Logger.info("PM is not able to edit a variable autopay");

    PmVapListPage pmVapListPage = getBasicSetup("tc9");
    pmVapListPage.open();

    Assert.assertFalse(pmVapListPage.rowHasActionByRowNum(0, Action.EDIT),
        "Row should have no edit link");
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

    PmVapListPage pmVapListPage = new PmVapListPage();
    pmVapListPage.open();

    String residentName = resFirstName + " " + resLastName;
    String accountNum = accountName + " #" + accountNumber.substring(accountNumber.length() - 4);

    Assert.assertFalse(
        pmVapListPage.hasRowMatchingData(
            status, startDate, endDate, createdDate, updatedDate, resRefId,
            residentName, property, debitDay, frequency, accountNum, null),
        "Row with matching data is not present");
  }

  @Test
  public void pmCanCancelWhileSkipped() {
    Logger.info("An autopay can be cancelled while it's skipped");

    PmVapListPage pmVapListPage = getBasicSetup("tc11");

    pmVapListPage.clickRowActionByRowNum(0, Action.CANCEL);

    pmVapListPage.acceptAlert();
    Assert.assertEquals(pmVapListPage.getSuccessMessage(), "AutoPay was successfully canceled",
        "Success message displayed");
  }

  private PmVapListPage getBasicSetup(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    PmVapListPage pmVapListPage = new PmVapListPage();
    pmVapListPage.open();

    return pmVapListPage;
  }

  private String getAutopayStatus(String transactionId) {
    String sqlQuery = "SELECT status "
        + "FROM autopay_templates "
        + "WHERE autopay_id = " + transactionId;

    return DataBaseConnector.getDbValue(sqlQuery);
  }
}
