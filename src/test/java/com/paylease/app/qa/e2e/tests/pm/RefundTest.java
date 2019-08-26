package com.paylease.app.qa.e2e.tests.pm;

import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_FULL;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_MAX;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_OVER_LIMIT;
import static com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage.REFUND_TYPE_PARTIAL_PARTIAL;
import static com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider.REFUND_INELIGIBLE_LESS_THAN_MIN_DAYS;
import static com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider.REFUND_INELIGIBLE_NOT_PAID_OUT;
import static com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider.REFUND_INELIGIBLE_OVER_REFUND_LIMIT;
import static com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider.REFUND_INELIGIBLE_PAST_REFUND_WINDOW;
import static com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider.REFUND_INELIGIBLE_REFUNDED;

import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ProcessTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BankAccountDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchFileDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BankAccount;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchFile;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.testng.Assert;
import org.testng.annotations.Test;


public class RefundTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "refundReversal";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(dataProvider = "refundLinkData", dataProviderClass = RefundTestDataProvider.class,
      groups = {"e2e"}, retryAnalyzer = Retry.class)
  public void verifyRefundLink(String testVariationNo, String testCaseSetup,
      boolean isMultiPaymentFields, boolean pmHasDiffBankAccts, boolean isRefundEnabled,
      boolean isRefundEligible) {
    Logger.info("Pm, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
        + ". Assert that the refund link is displayed = " + isRefundEnabled
        + " and transaction is refund eligible = " + isRefundEligible
        + " and isMultiPaymentFields = " + isMultiPaymentFields
        + " and pmHasDiffBankAccts = " + pmHasDiffBankAccts);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseSetup);
    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    String residentEmail = testSetupPage.getString("residentEmail");
    String pmEmail = testSetupPage.getString("pmEmail");

    Transaction transaction = createTransaction(residentEmail, paymentFieldList);
    String transactionId = String.valueOf(transaction.getTransactionId());

    ArrayList<Transaction> payoutTransactionsList = makeTransactionPaidOut(transactionId);

    if (isRefundEligible) {
      makeTransactionRefundable(payoutTransactionsList);
    } else {
      updateBatchFileSentDate(-3, payoutTransactionsList);
    }

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();

    pmLoginPage.login(pmEmail);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    for (Transaction payoutTransaction : payoutTransactionsList) {
      String paymentFieldName = payoutTransaction.getDescription().replaceAll("[^A-Za-z_]", "");
      if (isRefundEnabled) {
        Assert.assertTrue(pmPaymentHistoryPage.hasRefundLink(transactionId,
            paymentFieldName));
        if (isRefundEligible) {
          Assert.assertTrue(pmPaymentHistoryPage.isRefundEligible(transactionId, paymentFieldName));
        } else {
          Assert
              .assertFalse(pmPaymentHistoryPage.isRefundEligible(transactionId, paymentFieldName));
        }
      } else {
        Assert.assertFalse(pmPaymentHistoryPage.hasRefundLink(transactionId, paymentFieldName));
      }
    }

    PmLogoutBar logoutBar = new PmLogoutBar();
    logoutBar.clickLogoutButton();
  }

  @Test(dataProvider = "refundIneligiblelData", dataProviderClass = RefundTestDataProvider.class,
      groups = {"e2e"})
  public void verifyRefundIneligibleMessage(String testVariationNo, String testCaseSetup,
      boolean isMultiPaymentFields, boolean pmHasDiffBankAccts, String ineligibleReason) {
    Logger.info("Pm, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
        + ". Verify the correct messages displays when the user hovers over the refund link for a"
        + " transaction that is not eligible for refund."
        + " and isMultiPaymentFields = " + isMultiPaymentFields
        + " and pmHasDiffBankAccts = " + pmHasDiffBankAccts
        + " and ineligibleReason = " + ineligibleReason
        + " and testCaseSetup = " + testCaseSetup);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseSetup);
    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    //Get test data strings
    String residentEmail = testSetupPage.getString("residentEmail");
    String pmEmail = testSetupPage.getString("pmEmail");
    float refundLimit = Float.parseFloat(testSetupPage.getString("refundLimit"));

    //Create the transaction
    Transaction transaction = createTransaction(residentEmail, paymentFieldList);
    String transactionId = String.valueOf(transaction.getTransactionId());

    String expectedMessage = "";

    //If reason is 'not paid out', open transaction history page and do the assertions
    if (ineligibleReason.equals(REFUND_INELIGIBLE_NOT_PAID_OUT)) {
      expectedMessage = "Transaction must be paid out and must be paid out for at least 6 business"
          + " days and at most 90 business days.";
    } else {
      //If reason is 'over refund limit', update the transaction amounts to be over the refund
      // limit before paying out
      if (ineligibleReason.equals(REFUND_INELIGIBLE_OVER_REFUND_LIMIT)) {
        updateTransactionAmountOverLimit(transaction, paymentFieldList, refundLimit);
        expectedMessage = "Transaction amount is over the maximum limit.";
      }
      // Make the transaction paid out
      ArrayList<Transaction> payoutTransactionsList;

      payoutTransactionsList = makeTransactionPaidOut(transactionId);

      switch (ineligibleReason) {
        case REFUND_INELIGIBLE_PAST_REFUND_WINDOW:
          //If reason is 'past refund window' then update the batch file sent date
          updateBatchFileSentDate(-150, payoutTransactionsList);
          expectedMessage = "Transaction must be paid out and must be paid out for at least 6"
              + " business days and at most 90 business days.";
          break;
        case REFUND_INELIGIBLE_LESS_THAN_MIN_DAYS:
          //If reason is 'less than min days' then update the batch file sent date
          updateBatchFileSentDate(-3, payoutTransactionsList);
          expectedMessage = "Transaction must be paid out and must be paid out for at least 6"
              + " business days and at most 90 business days.";
          break;
        case REFUND_INELIGIBLE_REFUNDED:
          // If reason is 'cancelled' or 'refunded', then make the transaction refundable
          makeTransactionRefundable(payoutTransactionsList);

          LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
          loginPageAdmin.login();

          TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
          transactionDetailPage.open();

          // Refund the transaction
          transactionDetailPage.clickReverseRefund();
          transactionDetailPage
              .fillRefundDetailsForResident(String.valueOf(transaction.getUnitAmount()));
          transactionDetailPage.clickOkReverseRefund();
          expectedMessage = "Transaction has already been refunded.";

          break;
      }
    }

    //Login as the PM to verify results on PM UI
    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    pmPaymentHistoryPage.open(true);

    // If reason is 'past refund window' then enter transaction date as report start date to see
    // the transaction
    if (ineligibleReason.equals(REFUND_INELIGIBLE_PAST_REFUND_WINDOW)) {
      String startDate = null;
      try {
        Date payoutDate = UtilityManager.TIMESTAMP_FORMAT.parse(transaction.getTransactionDate());
        startDate = UtilityManager.US_FORMAT.format(payoutDate);
      } catch (ParseException e) {
        Logger.error(e.getMessage());
      }
      pmPaymentHistoryPage.enterStartDateAndSubmit(startDate);
    }

    for (String paymentField : paymentFieldList) {
      if (!paymentField.equals("")) {
        String ineligibleMessage = pmPaymentHistoryPage
            .getRefundLinkIneligibleMessage(transactionId, paymentField);
        Assert.assertEquals(ineligibleMessage, expectedMessage);
      }
    }

    PmLogoutBar logoutBar = new PmLogoutBar();
    logoutBar.clickLogoutButton();
  }

  @Test(dataProvider = "refundTransDataPm", dataProviderClass = RefundTestDataProvider.class,
      groups = {"e2e"})
  public void refundTransaction(String testVariationNo, String testCaseSetup,
      boolean isMultiPaymentFields,
      boolean pmHasDiffBankAccts,
      String refundType, boolean cancelInitiateRefund, boolean cancelContinueRefund) {
    Logger.info("Pm, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
        + ". Verify refund transaction when "
        + " and isMultiPaymentFields: " + isMultiPaymentFields
        + " and pmHasDiffBankAccts: " + pmHasDiffBankAccts
        + " and refundType: " + refundType
        + " and cancelInitiateRefund: " + cancelInitiateRefund
        + " and cancelContinueRefund: " + cancelContinueRefund
        + " and testCaseSetup: " + testCaseSetup);

    TestSetupPage testSetupPage;

    testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseSetup);
    testSetupPage.open();

    ArrayList<String> paymentFieldList = new ArrayList<>();

    //Assemble the list of payment fields
    paymentFieldList.add(testSetupPage.getString("paymentField1Label"));
    paymentFieldList.add(testSetupPage.getString("paymentField2Label"));

    //Get test data strings
    String residentEmail = testSetupPage.getString("residentEmail");
    String pmEmail = testSetupPage.getString("pmEmail");

    //Create the transaction
    Transaction transaction = createTransaction(residentEmail, paymentFieldList);
    String transactionId = String.valueOf(transaction.getTransactionId());

    //Make the transaction paid out
    ArrayList<Transaction> payoutTransactionsList = makeTransactionPaidOut(transactionId);

    //Make the transaction refundable
    String batchFileSentDate = makeTransactionRefundable(payoutTransactionsList);

    //Login as the pm and click the refund link on the transaction history page
    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    pmPaymentHistoryPage.clickRefundLink(transactionId);

    //If 'cancelInitiateRefund' = true, click 'Cancel' on initiate refund and verify that
    // transaction was not refunded.
    if (cancelInitiateRefund) {

      pmPaymentHistoryPage.clickCancelInitiateRefund();

      verifyTransactionStatus(transactionId, payoutTransactionsList, "Paid Out", batchFileSentDate,
          null);
    } else {

      //If 'cancelInitiateRefund' = false, select the refund type
      //Prepare data for refund, will use for filling out inputs (in the case of partial refunds)
      // and for assertions at the end of the test
      HashMap<Transaction, String> refundsToPayoutsList = new HashMap<>();
      ArrayList<HashMap<String, String>> refundsDetailsList;
      ArrayList<HashMap<String, String>> refundConfirmationDetailsList;

      if (refundType.equals(REFUND_TYPE_FULL)) {
        pmPaymentHistoryPage.clickFullRefundOption();

        for (Transaction payoutTransaction : payoutTransactionsList) {
          refundsToPayoutsList
              .put(payoutTransaction, String.valueOf(payoutTransaction.getUnitAmount()));
        }
        //assemble list of refunds to bank accounts and payment fields
        refundsDetailsList = createRefundDetails(refundsToPayoutsList);
      } else {
        pmPaymentHistoryPage.clickPartialRefundOption();
        //If partial refund, amount will depend on whether it is partial, max or over the limit
        switch (refundType) {
          case REFUND_TYPE_PARTIAL_PARTIAL:
            for (Transaction payoutTransaction : payoutTransactionsList) {
              refundsToPayoutsList.put(payoutTransaction, "1.00");
            }
            break;
          case REFUND_TYPE_PARTIAL_MAX:
            for (Transaction payoutTransaction : payoutTransactionsList) {
              refundsToPayoutsList
                  .put(payoutTransaction, String.valueOf(payoutTransaction.getUnitAmount()));
            }
            break;
          case REFUND_TYPE_PARTIAL_OVER_LIMIT:
            for (Transaction payoutTransaction : payoutTransactionsList) {
              String overLimitAmount = String
                  .format("%2f", Float.parseFloat(payoutTransaction.getUnitAmount()) + 1.00);
              refundsToPayoutsList.put(payoutTransaction, overLimitAmount);
            }
            break;
        }
        //assemble list of refunds to bank accounts and payment fields
        refundsDetailsList = createRefundDetails(refundsToPayoutsList);
        //enter the amounts on the page (not needed for full refund)
        pmPaymentHistoryPage.enterPartialRefundAmount(refundsDetailsList);
      }

      //Click 'Initiate'
      pmPaymentHistoryPage.clickInitiateRefund();

      if (refundType.equals(REFUND_TYPE_PARTIAL_OVER_LIMIT)) {
        //If refund amount is over the payment amount, expect error message and refund did not
        // proceed
        ArrayList<String> errorMessages = pmPaymentHistoryPage.getpartialRefundInputErrorMessages();
        Assert.assertEquals(errorMessages.size(), payoutTransactionsList.size());
        for (HashMap map : refundsDetailsList) {
          Assert.assertTrue(errorMessages
              .contains("The amount should not be more than " + map.get("paymentAmount")));
        }
        //Cancel the refund after verifying the error message
        pmPaymentHistoryPage.clickCancelInitiateRefund();
      } else {
        //Get the refund confirmation details and assert that the number of payouts = number of
        // refunds confirmed
        refundConfirmationDetailsList = pmPaymentHistoryPage.getRefundConfirmationDetails();
        Assert.assertEquals(refundConfirmationDetailsList.size(), payoutTransactionsList.size());

        //Assert that the confirmed refunds match the expected refunds
        for (HashMap<String, String> refundDetails : refundsDetailsList) {
          Assert.assertTrue(refundConfirmationDetailsList.contains(refundDetails));
        }

        if (cancelContinueRefund) {
          //If cancelContinueRefund = true then click 'Cancel'
          pmPaymentHistoryPage.clickCancelContinueRefund();
          verifyTransactionStatus(transactionId, payoutTransactionsList, "Paid Out",
              batchFileSentDate, refundsToPayoutsList);
        } else {
          //If cancelContinueRefund = false then click 'Continue'
          pmPaymentHistoryPage.clickContinueRefund();
          verifyTransactionStatus(transactionId, payoutTransactionsList, "Refunded",
              batchFileSentDate, refundsToPayoutsList);
        }
      }
    }

    PmLogoutBar pmLogoutBar = new PmLogoutBar();
    pmLogoutBar.clickLogoutButton();
  }
  //------------------------------------TEST METHODS-----------------------------------------------

  private Transaction createTransaction(String residentEmail,
      ArrayList<String> paymentFieldList) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();
    String transactionId = oneTimePaymentTest
        .residentOtPaymentActions(residentEmail, NEW_BANK, false, false, paymentFieldList);

    TransactionDao transactionDao = new TransactionDao();
    Transaction transaction = new Transaction();

    try {
      dataBaseConnector.createConnection();
      Connection connection = dataBaseConnector.getConnection();

      ArrayList<Transaction> transactionsList = transactionDao
          .findById(connection, Long.parseLong(transactionId), 1);
      transaction = transactionsList.get(0);
      dataBaseConnector.closeConnection();
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return transaction;
  }

  private ArrayList<Transaction> makeTransactionPaidOut(String transactionId) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    ArrayList<Transaction> payoutTransactionsList = null;

    TransactionDao transactionDao = new TransactionDao();

    try {
      dataBaseConnector.createConnection();
      Connection connection = dataBaseConnector.getConnection();

      processTransaction(transactionId);

      payoutTransactionsList = transactionDao
          .findByParentId(connection, Long.parseLong(transactionId), 50);
      dataBaseConnector.closeConnection();
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return payoutTransactionsList;
  }

  private String updateBatchFileSentDate(int daysFromToday,
      ArrayList<Transaction> payoutTransactionsList) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    BatchItemDao batchItemDao = new BatchItemDao();
    BatchFileDao batchFileDao = new BatchFileDao();

    UtilityManager um = new UtilityManager();
    Date backdate = um.addDays(new Date(), daysFromToday);
    String batchFileSentDate = UtilityManager.TIMESTAMP_FORMAT.format(backdate);

    try {
      dataBaseConnector.createConnection();
      Connection connection = dataBaseConnector.getConnection();

      for (Transaction payoutTransaction : payoutTransactionsList) {
        ArrayList<BatchItem> batchItemList = batchItemDao
            .findByTransId(connection, payoutTransaction.getTransactionId(), 50);
        for (BatchItem batchItem : batchItemList) {
          ArrayList<BatchFile> batchFileList = batchFileDao
              .findById(connection, batchItem.getFileId(), 50);
          for (BatchFile batchFile : batchFileList) {
            batchFile.setSentDate(batchFileSentDate);
            int numOfRowsAffected = batchFileDao.update(connection, batchFile);
            Logger.debug("Batch File UPDATE - Rows Affected: " + numOfRowsAffected);
            Logger.debug("Batch File sent date updated to:" + batchFileSentDate);
          }
        }
      }
      dataBaseConnector.closeConnection();
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return batchFileSentDate;
  }

  private ArrayList<HashMap<String, String>> createRefundDetails(
      HashMap<Transaction, String> refundsToPayoutsList) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    BankAccountDao bankAccountDao = new BankAccountDao();
    ArrayList<HashMap<String, String>> refundDetailsList = new ArrayList<>();
    BankAccount bankAccount;

    for (Transaction payoutTransaction : refundsToPayoutsList.keySet()) {
      try {
        dataBaseConnector.createConnection();
        Connection connection = dataBaseConnector.getConnection();
        // Get bank account last 4 of payout transaction
        bankAccount = bankAccountDao
            .findById(connection, Long.valueOf(payoutTransaction.getPaymentTypeId()), 1).get(0);
        HashMap<String, String> refundDetailsMap = new HashMap<>();
        refundDetailsMap.put("account",
            bankAccount.getAccountNum().substring(bankAccount.getAccountNum().length() - 4));
        refundDetailsMap.put("paymentAmount", String.valueOf(payoutTransaction.getUnitAmount()));
        refundDetailsMap.put("refundAmount", refundsToPayoutsList.get(payoutTransaction));

        refundDetailsList.add(refundDetailsMap);
        dataBaseConnector.closeConnection();
      } catch (Exception e) {
        Logger.error(e.getMessage());
        dataBaseConnector.closeConnection();
      }
    }
    return refundDetailsList;
  }

  private String makeTransactionRefundable(ArrayList<Transaction> payoutTransactionsList) {
    return updateBatchFileSentDate(-15, payoutTransactionsList);
  }

  private void verifyTransactionStatus(String transactionId,
      ArrayList<Transaction> payoutTransactionsList, String statusToMatch,
      String batchFileSentDate, HashMap<Transaction, String> refundsToPayoutsList) {
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    String expectedStatus;
    if (statusToMatch.equals("Paid Out")) {
      String formattedSentDate = "";

      try {
        formattedSentDate = UtilityManager.DATE_WITH_DASH_FORMAT
            .format(UtilityManager.TIMESTAMP_FORMAT.parse(batchFileSentDate));
      } catch (ParseException e) {
        Logger.error(e.getMessage());
      }
      expectedStatus = statusToMatch.concat("\n" + formattedSentDate);
    } else {
      double totalRefundAmount = 0.00;
      for (Transaction payoutTransaction : payoutTransactionsList) {
        totalRefundAmount =
            totalRefundAmount + Double.parseDouble(refundsToPayoutsList.get(payoutTransaction));
      }
      expectedStatus = statusToMatch
          .concat(" (" + currencyFormatter.format(totalRefundAmount) + ")");
    }

    for (Transaction payoutTransaction : payoutTransactionsList) {
      String billType = payoutTransaction.getDescription().replaceAll("[^A-Za-z_]", "")
          .replace("_", " ").toLowerCase();
      PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
      String actualStatus = pmPaymentHistoryPage.getStatusByIdAndBillType(transactionId, billType);

      Assert.assertEquals(actualStatus, expectedStatus);
    }
  }

  private void processTransaction(String transId) {
    ProcessTransactionPage processTransactionPage = new ProcessTransactionPage();
    processTransactionPage.open();
    processTransactionPage.processTransaction(transId);
  }

  private void updateTransactionAmountOverLimit(Transaction transaction,
      ArrayList<String> paymentFields, float refundLimit) {

    float newAmount = 0;

    String description = "";

    for (String paymentField : paymentFields) {
      if (!paymentField.equals("")) {
        float overLimitAmount = refundLimit + 1;
        String fieldDescription = "[" + paymentField.toLowerCase().replace(" ", "_") + "]{" + String
            .format("%.2f", overLimitAmount) + "}";

        //add pipe if more than one paymentfield
        if (!description.equals("")) {
          fieldDescription = "|".concat(fieldDescription);
        }

        description = description
            .concat(fieldDescription);

        newAmount = newAmount + overLimitAmount;
      }
    }
    float newTotalAmount = Float.parseFloat(transaction.getTotalAmount()) - Float
        .parseFloat(transaction.getUnitAmount()) + newAmount;

    transaction.setDescription(description);
    transaction.setTotalAmount(String.format("%2f", newTotalAmount));
    transaction.setUnitAmount(String.format("%2f", newAmount));

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection conn = dataBaseConnector.getConnection();
    TransactionDao transactionDao = new TransactionDao();

    int numRowsAffected = transactionDao.update(conn, transaction);
    Logger.debug("Transaction update - rows affected " + numRowsAffected);

    dataBaseConnector.closeConnection();
  }
}
