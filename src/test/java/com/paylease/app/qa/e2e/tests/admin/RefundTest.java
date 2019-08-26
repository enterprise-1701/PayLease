package com.paylease.app.qa.e2e.tests.admin;

import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_OVER_MAX;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_PARTIAL;
import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.REFUND_ENTITY_RESIDENT;
import static com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.NEW_BANK;

import com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest;
import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.automatedhelper.ProcessTransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BankAccountDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dao.UserDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BankAccount;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.database.client.dto.User;
import com.paylease.app.qa.testbase.dataproviders.RefundTestDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.paylease.app.qa.framework.UtilityManager;


public class RefundTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "refundReversal";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(dataProvider = "refundTransDataAdmin", dataProviderClass = RefundTestDataProvider.class,
      groups = {"e2e"})
  public void refundTransaction(String testVariationNo, String testCaseSetup,
      boolean isMultiPaymentFields,
      boolean pmHasDiffBankAccts,
      String refundType, String paymentType, String refundEntity, boolean isPaidOut) {
    Logger.info(
        "Admin, where test variation no and testcase: " + testVariationNo + "," + testCaseSetup
            + ". Verify refund transaction when "
            + " and isMultiPaymentFields: " + isMultiPaymentFields
            + " and pmHasDiffBankAccts: " + pmHasDiffBankAccts
            + " and refundType: " + refundType
            + " and paymentType: " + paymentType
            + " and refundEntity: " + refundEntity
            + " and testCaseSetup: " + testCaseSetup
            + " and isPaidOut:" + isPaidOut);

    UtilityManager utilityManager = new UtilityManager();
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

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
    String pmId = testSetupPage.getString("pmId");

    //Create the transaction
    Transaction transaction = createTransaction(connection, residentEmail, paymentFieldList);
    String transactionId = String.valueOf(transaction.getTransactionId());

    if (isPaidOut) {
      makeTransactionPaidOut(connection, transactionId);
    }

    Login login = new Login();
    login.logInAdmin();

    TransactionDetailPage transactionDetailPage = new TransactionDetailPage(transactionId);
    transactionDetailPage.open();

    String refundAmount = "";
    Double totalRefundAmount = 0.00;

    switch (refundType) {
      case ADMIN_REFUND_PARTIAL:
        refundAmount = "1.00";
        break;
      case ADMIN_REFUND_OVER_MAX:
        refundAmount = String
            .format("%.2f", Double.parseDouble(transaction.getUnitAmount()) + 100.00);
        break;
      default:
        refundAmount = transaction.getUnitAmount();
        break;
    }

    ArrayList<String> bankAccountIds = getPmBankAccountId(connection, pmId);

    int expectedRefundsCount = 0;

    if (refundEntity.equals(REFUND_ENTITY_RESIDENT)) {
      transactionDetailPage.clickReverseRefund();
      transactionDetailPage.fillRefundDetailsForResident(refundAmount);
      transactionDetailPage.clickOkReverseRefund();
      totalRefundAmount = totalRefundAmount + Double.parseDouble(refundAmount);
      if (refundType.equals(ADMIN_REFUND_OVER_MAX)) {
        Assert.assertEquals(transactionDetailPage.getRefundReversalError(), "Invalid amount");
        transactionDetailPage.clickCancelReverseRefund();
      } else {
        expectedRefundsCount++;
      }
    } else {
      for (String bankAccountId : bankAccountIds) {
        transactionDetailPage.clickReverseRefund();
        transactionDetailPage.fillRefundDetailsForPm(bankAccountId, refundAmount);
        transactionDetailPage.clickOkReverseRefund();
        if (refundType.equals(ADMIN_REFUND_OVER_MAX)) {
          Assert.assertEquals(transactionDetailPage.getRefundReversalError(), "Invalid amount");
          transactionDetailPage.clickCancelReverseRefund();
        } else {
          expectedRefundsCount++;
          Logger.info("PM Refund: Refunded " + refundAmount + " to " + bankAccountId);
        }
        totalRefundAmount = totalRefundAmount + Double.parseDouble(refundAmount);
      }
    }

    ArrayList<Transaction> refundTransactions = getRefundTransactions(connection, transactionId);

    //assert refund appears in database
    Assert.assertEquals(refundTransactions.size(), expectedRefundsCount,
        "Expecting " + expectedRefundsCount + " refunds but found " + refundTransactions.size());

    //Assert the refunds we got from the database appear in the Admin UI
    for (Transaction refundTransaction : refundTransactions) {
      Assert.assertTrue(transactionDetailPage
          .isRefundTransactionPresent(String.valueOf(refundTransaction.getTransactionId())));
    }

    UserDao userDao = new UserDao();
    User adminUser;

    adminUser = userDao.findByUsername(connection, AppConstant.QA_ADMIN_USER);

    //Verify log entries
    for (Transaction refundTransaction : refundTransactions) {
      Assert.assertTrue(transactionDetailPage
          .isAdminRefundLogged(transactionId, String.valueOf(refundTransaction.getTransactionId()),
              adminUser.getFirstName(), adminUser.getLastName(), adminUser.getUser()));
    }

    login.logOutAdmin();

    //Log in to PM UI and assert we also see the transactions as refunded

    login.logInUser(pmEmail, UserType.PM);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    String expectedMessage = "";

    if (isPaidOut) {
      expectedMessage = "Transaction has already been refunded.";
    } else {
      expectedMessage = "Transaction has already been refunded."
          + "\nTransaction must be paid out and must be paid out for at least 6 business days and at most 90 business days.";
    }

    //Verify refund link enabled/disabled and ineligible message, if disabled.
    if (ADMIN_REFUND_OVER_MAX == refundType) {
      for (String paymentField : paymentFieldList) {
        if (!paymentField.equals("")) {
          if (isPaidOut) {
            //refund link should be enabled
            Assert.assertTrue(pmPaymentHistoryPage
                .isRefundEligible(transactionId, paymentField.toLowerCase().replace(" ", "_")));
          } else {
            //refund link should be disabled
            String ineligibleMessage = pmPaymentHistoryPage
                .getRefundLinkIneligibleMessage(transactionId, paymentField);
            Assert.assertEquals(ineligibleMessage,
                "Transaction must be paid out and must be paid out for at least 6 business days and at most 90 business days.");
          }
        }
      }
    } else {
      //refund link should be disabled, verify message
      for (String paymentField : paymentFieldList) {
        if (!paymentField.equals("")) {
          String ineligibleMessage = pmPaymentHistoryPage
              .getRefundLinkIneligibleMessage(transactionId, paymentField);
          Assert.assertEquals(ineligibleMessage, expectedMessage);
        }
      }
    }

    //Verify transaction status
    if (ADMIN_REFUND_OVER_MAX == refundType) {
      for (String paymentField : paymentFieldList) {
        if (!paymentField.equals("")) {
          if (isPaidOut) {
            Assert.assertTrue(pmPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, paymentField.toLowerCase())
                .contains("Paid Out"));
          } else {
            Assert.assertEquals(pmPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, paymentField.toLowerCase()), "Processing");
          }
        }
      }
    } else {
      for (String paymentField : paymentFieldList) {
        if (!paymentField.equals("")) {
          if (isPaidOut) {
            if (REFUND_ENTITY_RESIDENT == refundEntity) {
              //Assert status is Refunded and shows refunded amount
              Assert.assertEquals(pmPaymentHistoryPage
                      .getStatusByIdAndBillType(transactionId, paymentField.toLowerCase()),
                  "Refunded (" + utilityManager
                      .formatToDollarAmount(Double.parseDouble(refundAmount)) + ")");
            } else {
              Assert.assertTrue(pmPaymentHistoryPage
                  .getStatusByIdAndBillType(transactionId, paymentField.toLowerCase())
                  .contains("Paid Out"));
            }
          } else {
            Assert.assertEquals(pmPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, paymentField.toLowerCase()), "Processing");
          }
        }
      }
    }

    //Verify transaction status in RES ui
    login.logInUser(residentEmail, UserType.RESIDENT);
    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();
    resPaymentHistoryPage.open();

    //Verify transaction status

    String billTypeRes;

    if (isMultiPaymentFields) {
      billTypeRes = "Total Amount";
    } else {
      billTypeRes = "Lease Payment";
    }

    if (ADMIN_REFUND_OVER_MAX == refundType) {
      if (isPaidOut) {
        Assert.assertTrue(resPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase())
                .contains("Paid Out"),
            "Expecting status to show 'Paid Out' but found: " + resPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()));
      } else {
        Assert.assertEquals(resPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()), "Processing",
            "Expecting status 'Processing' but found: " + resPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()));
      }
    } else {
      if (REFUND_ENTITY_RESIDENT == refundEntity) {
        //Assert status is Refunded and shows refunded amount
        Assert.assertEquals(resPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()),
            "Refunded (" + utilityManager.formatToDollarAmount(Double.parseDouble(refundAmount))
                + ")",
            "Expecting status Refunded (" + utilityManager.formatToDollarAmount(totalRefundAmount)
                + ") but found " + resPaymentHistoryPage
                .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()));
      } else {
        if (isPaidOut) {
          Assert.assertTrue(resPaymentHistoryPage
                  .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase())
                  .contains("Paid Out"),
              "Expecting status to show 'Paid Out' but found: " + resPaymentHistoryPage
                  .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()));
        } else {
          Assert.assertTrue(resPaymentHistoryPage
                  .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase())
                  .equals("Processing"),
              "Expecting status 'Processing' but found: " + resPaymentHistoryPage
                  .getStatusByIdAndBillType(transactionId, billTypeRes.toLowerCase()));
        }
      }
    }

    dataBaseConnector.closeConnection();
  }

  //------------------------------------TEST METHODS-----------------------------------------------

  private Transaction createTransaction(Connection conn, String residentEmail,
      ArrayList<String> paymentFieldList) {

    com.paylease.app.qa.e2e.tests.resident.OneTimePaymentTest oneTimePaymentTest = new OneTimePaymentTest();
    String transactionId = oneTimePaymentTest
        .residentOtPaymentActions(residentEmail, NEW_BANK, false, false, paymentFieldList);

    TransactionDao transactionDao = new TransactionDao();
    Transaction transaction = new Transaction();

    try {

      ArrayList<Transaction> transactionsList = transactionDao
          .findById(conn, Long.parseLong(transactionId), 1);
      transaction = transactionsList.get(0);
    } catch (Exception se) {
      se.getMessage();
    }
    return transaction;
  }

  private ArrayList<Transaction> makeTransactionPaidOut(Connection conn, String transactionId) {
    ArrayList<Transaction> payoutTransactionsList = null;

    TransactionDao transactionDao = new TransactionDao();

    try {
      processTransaction(transactionId);

      payoutTransactionsList = transactionDao
          .findByParentId(conn, Long.parseLong(transactionId), 50);
    } catch (Exception se) {
      se.getMessage();
    }
    return payoutTransactionsList;
  }

  private void processTransaction(String transId) {
    ProcessTransactionPage processTransactionPage = new ProcessTransactionPage();
    processTransactionPage.open();
    processTransactionPage.processTransaction(transId);
  }

  private ArrayList<Transaction> getRefundTransactions(Connection conn,
      String parentTransactionId) {
    Logger.info("Parent Transaction ID: " + parentTransactionId);
    TransactionDao transactionDao = new TransactionDao();
    ArrayList<Transaction> transactionsList;

    transactionsList = transactionDao.findByParentId(conn, Long.parseLong(parentTransactionId), 10);

    ArrayList<Transaction> refundTransactionsList = new ArrayList<>();
    for (Transaction transaction : transactionsList) {
      if (8 == transaction.getTypeOfTransaction()) {
        refundTransactionsList.add(transaction);
      }
    }
    return refundTransactionsList;
  }

  private ArrayList<String> getPmBankAccountId(Connection conn, String pmId) {
    BankAccountDao bankAccountDao = new BankAccountDao();

    ArrayList<BankAccount> pmBankAccounts = bankAccountDao.findByUserId(conn, pmId, 2);

    ArrayList<String> bankAccountIds = new ArrayList<>();
    for (BankAccount bankAccount : pmBankAccounts) {
      bankAccountIds.add(bankAccount.getAccountId());
    }
    return bankAccountIds;
  }
}