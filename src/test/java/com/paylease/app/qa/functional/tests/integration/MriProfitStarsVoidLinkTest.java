package com.paylease.app.qa.functional.tests.integration;

import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ManagedDepositPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ManagedDepositProcessingPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test that the void link is available for a MRI ProfitStars ACH transaction in PL Admin, the PM
 * UI, and the Res UI when appropriate based on Managed Deposit status.
 */
public class MriProfitStarsVoidLinkTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "MriProfitStarsVoidLink";

  private static final String MANAGED_DEPOSIT_STATUS_CLOSED = "CLOSED";
  private static final String MANAGED_DEPOSIT_STATUS_OPEN = "OPEN";

  /**
   * This test simulates closing the managed deposit internally and externally after
   * the transaction is created.
   * <p>
   * Verify that the void link is available in PL Admin when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed.
   * </p>
   */
  @Test
  public void testVoidLinkPlAdminBeforeAndAfterManagedDepositClosed() {
    Logger.info(
        "Verify VOID TRANSACTION link in PL Admin UI before and after Managed Deposit closed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    LoginPageAdmin adminLoginPage = new LoginPageAdmin();
    adminLoginPage.login();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
    transactionDetailPage.open();

    boolean hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertTrue(hasVoidLink, "Failed to find VOID TRANSACTION link when expected");

    Logger.info("Verified VOID TRANSACTION link available in PL Admin UI before Managed Deposit"
        + "closed for Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE, transactionId);

    transactionDetailPage.open();

    hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertFalse(hasVoidLink, "Found VOID TRANSACTION link when not expected");

    Logger.info("Verified VOID TRANSACTION link not available in PL Admin UI after Managed Deposit"
        + "closed for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing the managed deposit internally and externally after
   * the transaction is created.
   * <p>
   * Verify that the void link is available in the PM UI when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed.
   * </p>
   */
  @Test
  public void testVoidLinkPmUiBeforeAndAfterManagedDepositClosed() {
    Logger.info("Verify VOID link in PM UI before and after Managed Deposit closed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc02");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    boolean hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find VOID link when expected");

    Logger.info("Verified VOID link available in PM UI before Managed Deposit closed for"
        + "Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE, transactionId);

    pmPaymentHistoryPage.open(true);

    hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertFalse(hasVoidLink, "Found VOID link when not expected");

    Logger.info("Verified VOID link not available in PM UI after Managed Deposit closed for"
        + "Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing the managed deposit internally and externally after
   * the transaction is created.
   * <p>
   * Verify that the void link is available in the Resident UI when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed.
   * </p>
   */
  @Test
  public void testVoidLinkResidentUiBeforeAndAfterManagedDepositClosed() {
    Logger.info("Verify Void link in Resident UI before and after Managed Deposit closed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc03");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    ResPaymentHistoryPage residentPaymentHistoryPage = new ResPaymentHistoryPage();
    residentPaymentHistoryPage.open();

    boolean hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find Void link when expected");

    Logger.info(
        "Verified Void link available in Resident UI before Managed Deposit closed for Transaction "
            + "ID " + transactionId);

    Logger.info("Closing Managed Deposit for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE, transactionId);

    residentPaymentHistoryPage.open();

    hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertFalse(hasVoidLink, "Found Void link when not expected");

    Logger.info("Verified Void link not available in Resident UI after Managed Deposit closed for"
        + "Transaction ID " + transactionId);
  }

  /**
   * This test simulates creating the managed deposit after the transaction is created.
   * <p>
   * Verify that the void link is available in PL Admin before and after the related managed
   * deposit is created.
   * </p>
   */
  @Test
  public void testVoidLinkPlAdminBeforeAndAfterManagedDepositCreated() {
    Logger.info(
        "Verify VOID TRANSACTION link in PL Admin UI before and after Managed Deposit created");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc04");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    LoginPageAdmin adminLoginPage = new LoginPageAdmin();
    adminLoginPage.login();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
    transactionDetailPage.open();

    boolean hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertTrue(hasVoidLink, "Failed to find VOID TRANSACTION link when expected");

    Logger.info("Verified VOID TRANSACTION link available in PL Admin UI before Managed Deposit"
        + "created for Transaction ID " + transactionId);

    Logger.info("Creating Managed Deposit for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CREATE, transactionId);

    transactionDetailPage.open();

    hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertTrue(hasVoidLink, "Failed to find VOID TRANSACTION link when expected");

    Logger.info("Verified VOID TRANSACTION link still available in PL Admin UI after Managed"
        + "Deposit created for Transaction ID " + transactionId);
  }

  /**
   * This test simulates creating the managed deposit after the transaction is created.
   * <p>
   * Verify that the void link is available in the PM UI before and after the related managed
   * deposit is created.
   * </p>
   */
  @Test
  public void testVoidLinkPmUiBeforeAndAfterManagedDepositCreated() {
    Logger.info("Verify VOID link in PM UI before and after Managed Deposit created");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc05");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(false);

    boolean hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find VOID link when expected");

    Logger.info(
        "Verified VOID link available in PM UI before Managed Deposit created for Transaction ID "
            + transactionId);

    Logger.info("Creating Managed Deposit for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CREATE, transactionId);

    pmPaymentHistoryPage.open(false);

    hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find VOID link when expected");

    Logger.info(
        "Verified VOID link still available in PM UI after Managed Deposit created for Transaction"
        + "ID " + transactionId);
  }

  /**
   * This test simulates creating the managed deposit after the transaction is created.
   * </p>
   * verify that the void link is available in the Resident UI before and after the related managed
   * deposit is created.
   * </p>
   */
  @Test
  public void testVoidLinkResidentUiBeforeAndAfterManagedDepositCreated() {
    Logger.info("Verify Void link in Resident UI before and after Managed Deposit created");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc06");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    ResPaymentHistoryPage residentPaymentHistoryPage = new ResPaymentHistoryPage();
    residentPaymentHistoryPage.open();

    boolean hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find Void link when expected");

    Logger.info("Verified Void link available in Resident UI before Managed Deposit created for"
        + "Transaction ID " + transactionId);

    Logger.info("Creating Managed Deposit for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CREATE, transactionId);

    residentPaymentHistoryPage.open();

    hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find Void link when expected");

    Logger.info("Verified Void link still available in Resident UI after Managed Deposit created "
        + "for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit internally only after a transaction is created.
   * <p>
   * Verify that the void link is available in PL Admin when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed
   * internally only.
   * </p>
   */
  @Test
  public void testVoidLinkPlAdminBeforeAndAfterManagedDepositClosedInternally() {
    Logger.info("Verify VOID TRANSACTION link in PL Admin UI before and after Managed Deposit "
        + "closed internally only");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc07");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    LoginPageAdmin adminLoginPage = new LoginPageAdmin();
    adminLoginPage.login();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
    transactionDetailPage.open();

    boolean hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertTrue(hasVoidLink, "Failed to find VOID TRANSACTION link when expected");

    Logger.info("Verified VOID TRANSACTION link available in PL Admin UI before Managed Deposit "
        + "closed internally for Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit internally for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE_INTERNALLY, transactionId);

    transactionDetailPage.open();

    hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertFalse(hasVoidLink, "Found VOID TRANSACTION link when not expected");

    Logger.info("Verified VOID TRANSACTION link not available in PL Admin UI after Managed Deposit"
        + "closed internally for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit internally only after a transaction is created.
   * <p>
   * Verify that the void link is available in the PM UI when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed
   * internally only.
   * </p>
   */
  @Test
  public void testVoidLinkPmUiBeforeAndAfterManagedDepositClosedInternally() {
    Logger
        .info("Verify VOID link in PM UI before and after Managed Deposit closed internally only");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc08");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(false);

    boolean hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find VOID link when expected");

    Logger.info("Verified VOID link available in PM UI before Managed Deposit closed internally "
        + "for Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit internally for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE_INTERNALLY, transactionId);

    pmPaymentHistoryPage.open(false);

    hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertFalse(hasVoidLink, "Found VOID link when not expected");

    Logger.info("Verified VOID link not available in PM UI after Managed Deposit closed internally"
        + "for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit internally only after a transaction is created.
   * <p>
   * Verify that the void link is available in the Resident UI when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed
   * internally only.
   * </p>
   */
  @Test
  public void testVoidLinkResidentUiBeforeAndAfterManagedDepositClosedInternally() {
    Logger.info(
        "Verify Void link in Resident UI before and after Managed Deposit closed internally only");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc09");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    ResPaymentHistoryPage residentPaymentHistoryPage = new ResPaymentHistoryPage();
    residentPaymentHistoryPage.open();

    boolean hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find Void link when expected");

    Logger.info("Verified Void link available in Resident UI before Managed Deposit closed"
        + "internally for Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit internally for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE_INTERNALLY, transactionId);

    residentPaymentHistoryPage.open();

    hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertFalse(hasVoidLink, "Found Void link when not expected");

    Logger.info("Verified Void link not available in Resident UI after Managed Deposit closed"
        + "internally for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit externally only after a transaction is created.
   * <p>
   * Verify that the void link is available in PL Admin when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed
   * externally only.
   * </p>
   */
  @Test
  public void testVoidLinkPlAdminBeforeAndAfterManagedDepositClosedExternally() {
    Logger.info("Verify VOID TRANSACTION link in PL Admin UI before and after Managed Deposit "
        + "closed externally only");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    LoginPageAdmin adminLoginPage = new LoginPageAdmin();
    adminLoginPage.login();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
    transactionDetailPage.open();

    boolean hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertTrue(hasVoidLink, "Failed to find VOID TRANSACTION link when expected");

    Logger.info(
        "Verified VOID TRANSACTION link available in PL Admin UI before Managed Deposit closed"
            + "externally for Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit externally for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE_EXTERNALLY, transactionId);

    transactionDetailPage.open();

    hasVoidLink = transactionDetailPage.hasVoidLink();
    Assert.assertFalse(hasVoidLink, "Found VOID TRANSACTION link when not expected");

    Logger.info(
        "Verified VOID TRANSACTION link not available in PL Admin UI after Managed Deposit closed"
            + "externally for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit externally only after a transaction is created.
   * <p>
   * Verify that the void link is available in the PM UI when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed
   * externally only.
   * </p>
   */
  @Test
  public void testVoidLinkPmUiBeforeAndAfterManagedDepositClosedExternally() {
    Logger
        .info("Verify VOID link in PM UI before and after Managed Deposit closed externally only");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(false);

    boolean hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find VOID link when expected");

    Logger.info(
        "Verified VOID link available in PM UI before Managed Deposit closed externally for"
            + "Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit externally for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE_EXTERNALLY, transactionId);

    pmPaymentHistoryPage.open(false);

    hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertFalse(hasVoidLink, "Found VOID link when not expected");

    Logger.info(
        "Verified VOID link not available in PM UI after Managed Deposit closed externally for"
            + "Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit externally only after a transaction is created.
   * <p>
   * Verify that the void link is available in the Resident UI when the related managed deposit
   * is still open, and that it is not available when the related managed deposit is closed
   * externally only.
   * </p>
   */
  @Test
  public void testVoidLinkResidentUiBeforeAndAfterManagedDepositClosedExternally() {
    Logger.info(
        "Verify Void link in Resident UI before and after Managed Deposit closed externally only");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    ResPaymentHistoryPage residentPaymentHistoryPage = new ResPaymentHistoryPage();
    residentPaymentHistoryPage.open();

    boolean hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find Void link when expected");

    Logger.info("Verified Void link available in Resident UI before Managed Deposit closed"
        + "externally for Transaction ID " + transactionId);

    Logger.info("Closing Managed Deposit externally for Transaction ID: " + transactionId);
    processManagedDeposit(ManagedDepositPage.CLOSE_EXTERNALLY, transactionId);

    residentPaymentHistoryPage.open();

    hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertFalse(hasVoidLink, "Found Void link when not expected");

    Logger.info(
        "Verified Void link not available in Resident UI after Managed Deposit closed externally"
            + "for Transaction ID " + transactionId);
  }

  /**
   * This test simulates closing a managed deposit after the payment history page is loaded.
   * <p>
   * Verify that the void link is available in the PM UI when the related managed deposit
   * is still open, and that it fails when the related managed deposit is closed by a
   * separate window.
   * </p>
   */
  @Test
  public void testVoidLinkPmUiManagedDepositClosedAfterPageLoad() {
    Logger.info("Verify VOID link in PM UI when Managed Deposit closed after page load");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc13");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    boolean hasVoidLink = pmPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find VOID link when expected");

    Logger.info(
        "Verified VOID link available in PM UI before Managed Deposit closed for Transaction ID "
            + transactionId);

    // process the managed deposit in a separate window
    Logger.info("Closing Managed Deposit for Transaction ID: " + transactionId);
    openNewWindow();
    try {
      processManagedDeposit(ManagedDepositPage.CLOSE, transactionId);
    } finally {
      restorePreviousWindow();
    }

    // click the void link and accept the alert
    pmPaymentHistoryPage.clickVoidLink(transactionId);
    DriverManager.getDriver().switchTo().alert().accept();

    boolean hasVoidErrorMessage = pmPaymentHistoryPage.isVoidErrorDisplayed();
    Assert.assertTrue(hasVoidErrorMessage, "Failed to find void error message when expected");

    Logger.info("Verified VOID link fails in PM UI after Managed Deposit closed for Transaction ID "
        + transactionId);
  }

  /**
   * This test simulates closing a managed deposit after the payment history page is loaded.
   * <p>
   * Verify that the void link is available in the Resident UI when the related managed deposit
   * is still open, and that it fails when the related managed deposit is closed by a
   * separate window.
   * </p>
   */
  @Test
  public void testVoidLinkResidentUiManagedDepositClosedAfterPageLoad() {
    Logger.info("Verify Void link in Resident UI when Managed Deposit closed after page load");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc14");
    testSetupPage.open();
    String transactionId = testSetupPage.getString("transactionId", "Payment ID");

    ResPaymentHistoryPage residentPaymentHistoryPage = new ResPaymentHistoryPage();
    residentPaymentHistoryPage.open();

    boolean hasVoidLink = residentPaymentHistoryPage.hasVoidLink(transactionId);
    Assert.assertTrue(hasVoidLink, "Failed to find Void link when expected");

    Logger.info("Verified Void link available in Resident UI before Managed Deposit closed for "
        + "Transaction ID " + transactionId);

    // process the managed deposit in a separate window
    Logger.info("Closing Managed Deposit for Transaction ID: " + transactionId);
    openNewWindow();
    try {
      processManagedDeposit(ManagedDepositPage.CLOSE, transactionId);
    } finally {
      restorePreviousWindow();
    }

    // click the void link and accept the alert
    residentPaymentHistoryPage.clickVoidLink(transactionId);
    DriverManager.getDriver().switchTo().alert().accept();

    boolean hasVoidErrorMessage = residentPaymentHistoryPage.isVoidErrorDisplayed();
    Assert.assertTrue(hasVoidErrorMessage, "Failed to find void error message when expected");

    Logger.info("Verified Void link fails in Resident UI after Managed Deposit closed for "
        + "Transaction ID " + transactionId);
  }

  //----------------------------------METHODS----------------------------------

  /**
   * Perform the given action for the managed deposit related to the given
   * transaction id, and then verify that the managed deposit is in the
   * expected state internally and externally.
   *
   * @param action valid values: close, close_externally, close_internally, create
   * @param transactionId test transaction id
   */
  private void processManagedDeposit(String action, String transactionId) {
    ManagedDepositPage managedDepositPage = new ManagedDepositPage();
    managedDepositPage.open();

    ManagedDepositProcessingPage resultPage =
        managedDepositPage.processManagedDepositForTransactionId(action, transactionId);

    String expectedInternalStatus = "";
    switch (action) {
      case ManagedDepositPage.CREATE:
      case ManagedDepositPage.CLOSE_EXTERNALLY:
        expectedInternalStatus = MANAGED_DEPOSIT_STATUS_OPEN;
        break;
      case ManagedDepositPage.CLOSE:
      case ManagedDepositPage.CLOSE_INTERNALLY:
        expectedInternalStatus = MANAGED_DEPOSIT_STATUS_CLOSED;
        break;
    }

    String expectedExternalStatus = "";
    switch (action) {
      case ManagedDepositPage.CREATE:
      case ManagedDepositPage.CLOSE_INTERNALLY:
        expectedExternalStatus = MANAGED_DEPOSIT_STATUS_OPEN;
        break;
      case ManagedDepositPage.CLOSE:
      case ManagedDepositPage.CLOSE_EXTERNALLY:
        expectedExternalStatus = MANAGED_DEPOSIT_STATUS_CLOSED;
        break;
    }

    Assert.assertEquals(resultPage.getInternalStatus(), expectedInternalStatus,
        "Unexpected Internal Managed Deposit Status: " + resultPage.getInternalStatus());
    Assert.assertEquals(resultPage.getExternalStatus(), expectedExternalStatus,
        "Unexpected External Managed Deposit Status: " + resultPage.getExternalStatus());
  }
}
