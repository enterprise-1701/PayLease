package com.paylease.app.qa.testbase;

import static com.paylease.app.qa.framework.pages.admin.TransactionDetailPage.ADMIN_REFUND_MAX;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.admin.TransactionDetailPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.utility.database.client.dao.BankAccountDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchFileDao;
import com.paylease.app.qa.framework.utility.database.client.dao.BatchItemDao;
import com.paylease.app.qa.framework.utility.database.client.dao.DepositDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalBatchesDao;
import com.paylease.app.qa.framework.utility.database.client.dao.ExternalTransactionsDao;
import com.paylease.app.qa.framework.utility.database.client.dao.TransactionDao;
import com.paylease.app.qa.framework.utility.database.client.dto.BankAccount;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchFile;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import com.paylease.app.qa.framework.utility.database.client.dto.Deposit;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalBatchesDto;
import com.paylease.app.qa.framework.utility.database.client.dto.ExternalTransactionsDto;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import com.paylease.app.qa.framework.utility.sshtool.SshUtil;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.testng.Assert;

public class DbBase extends ScriptBase {

  protected int procId;
  protected String accountPersonName;
  protected String fromAcc;
  protected double amount;
  protected String fromRoutingNum;
  protected String toId;

  private Long fileId;
  private int batchId;

  public static final int DAYS_FROM_TODAY = -5;

  public enum FindBy {
    TRANSACTION_ID,
    PARENT_TRANSACTION_ID,
  }

  private static final String PAID_OUT = "Paid Out";
  private static final int REVERSAL_TYPE_OF_TRANSACTION = 5;
  private static final int REVERSAL_TYPE_OF_TRANSACTION_CC = 15;
  public static final String TRANSACTION_DATE = "2050-01-03 12:00:00";

  /**
   * Check DB transactions table.
   *
   * @param findBy findBy
   * @param connection connection
   * @param transactionId transactionId
   * @param expectedStatus expected status
   * @param expectedTypeOfTransaction expected type of transaction
   */
  public void checkTransactionsDbTable(FindBy findBy, Connection connection,
      String transactionId, String expectedStatus, int expectedTypeOfTransaction) {

    TransactionDao transactionDao = new TransactionDao();

    ArrayList<Transaction> transactionsList = null;

    if (findBy.equals(FindBy.TRANSACTION_ID)) {
      transactionsList = transactionDao.findById(connection, Long.parseLong(transactionId), 50);
    } else if (findBy.equals(FindBy.PARENT_TRANSACTION_ID)) {
      transactionsList = transactionDao
          .findByParentId(connection, Long.parseLong(transactionId), 50);
    }

    for (Transaction transaction : transactionsList) {

      String status = transaction.getStatus();
      int typeOfTransaction = transaction.getTypeOfTransaction();

      Logger.info("Transaction status is: " + status + " for transaction ID: "
          + transaction.getTransactionId());

      Assert.assertEquals(status, expectedStatus, "Status code does not match");

      Assert.assertEquals(typeOfTransaction, expectedTypeOfTransaction, "Expected type of "
          + "transaction does not match");
    }
  }

  /**
   * This function is to assert if no row was found in transactions table where the passed in
   * transaction id is the parent transaction id. In other words, this is to ensure that no
   * payout(child) transactions are created for this transaction id.
   *
   * @param connection connection
   * @param transactionId transaction ID
   */
  public void checkTransactionsDbTableForParent(Connection connection, String transactionId) {
    TransactionDao transactionDao = new TransactionDao();

    ArrayList<Transaction> transactionsList;

    transactionsList = transactionDao
        .findByParentId(connection, Long.parseLong(transactionId), 1);

    Assert.assertTrue(transactionsList.isEmpty(), "Expected no payout transactions to be created");
  }

  /**
   * Check batch items DB table.
   *
   * @param connection connection
   * @param transactionId transaction ID
   * @param expectedNumRows expected number of rows
   * @param expectedAmounts expected amounts
   */
  public void checkBatchItemsDbTable(Connection connection, String transactionId,
      int expectedNumRows, ArrayList<Double> expectedAmounts) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection,
        Long.parseLong(transactionId), 50);

    if (expectedNumRows > 0) {
      for (BatchItem batchItem : batchItemsList) {

        double amount = batchItem.getAmount();

        Logger.info("Amount is: " + amount);

        Assert.assertTrue(expectedAmounts.contains(amount), "Amount does not match");
      }
    } else {
      Assert.assertEquals(batchItemsList.size(), expectedNumRows, "Size does not match");
    }
  }

  /**
   * Check batch items DB table.
   *
   * @param connection connection
   * @param transactionId transaction ID
   */
  public List<Integer> getProcIdsFromBatchItemsTable(Connection connection, String transactionId) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection,
        Long.parseLong(transactionId), 50);
    List<Integer> procIds = new ArrayList<>();

    for (BatchItem batchItem : batchItemsList) {
      int procId = batchItem.getProcId();
      procIds.add(procId);
    }
    return procIds;
  }

  /**
   * Check batch items DB table.
   *
   * @param connection connection
   * @param transactionId transaction ID
   */
  public Integer getProcIdFromBatchItemsTable(Connection connection, String transactionId) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection,
        Long.parseLong(transactionId), 50);
    int procId = 0;

    for (BatchItem batchItem : batchItemsList) {
      procId = batchItem.getProcId();
    }

    return procId;
  }

  /**
   * /** Check batch items DB table.
   *
   * @param connection connection
   * @param procId transaction ID
   */
  public long getFileIdsFromBatchItemsTable(Connection connection, int procId) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findById(connection,
        procId, 50);
    long fileId = 0;

    for (BatchItem batchItem : batchItemsList) {
      fileId = batchItem.getFileId();
    }
    return fileId;
  }

  /**
   * Check batch items DB table.
   *
   * @param connection connection
   * @param procId transaction ID
   */
  public long getDepositIdFromBatchItemsTable(Connection connection, int procId) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findById(connection,
        procId, 50);
    long depositId = 0;

    for (BatchItem batchItem : batchItemsList) {
      depositId = batchItem.getDepositId();
    }
    return depositId;
  }

  /**
   * Check batch items DB table for file Id and ext ref ID.
   *
   * @param connection connection
   * @param transactionId transaction Id
   * @param isNewWorld if it is a new world transaction of not
   */
  public void checkBatchItemsDbTableForFileIdAndExtRefId(Connection connection,
      String transactionId, boolean isNewWorld) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection,
        Long.parseLong(transactionId), 50);

    Assert.assertFalse(batchItemsList.isEmpty(), "Batch Items is empty");
    for (BatchItem batchItem : batchItemsList) {

      long fileId = batchItem.getFileId();
      String extRefId = batchItem.getExtRefId();

      Logger.info("File ID for batch item " + batchItem.getProcId() + ": " + fileId);
      Logger.info("Ext Ref ID for batch item " + batchItem.getProcId() + ": " + extRefId);

      if (isNewWorld) {
        Assert.assertTrue(fileId < 0, "File ID is greater than 0");
        Assert.assertNull(extRefId, "Ext Ref Id is null");
      } else {
        Assert.assertTrue(fileId > 0, "File ID is less than 0");
        Assert.assertNotNull(extRefId, "Ext Ref Id is null");
      }
    }
  }

  //TODO: this will need to be updated when we have a customization to mark the PM as new world.
  // When the customization is enabled, we should check that there is an entry in this table.

  /**
   * Make transaction paid out.
   *
   * @param transactionId transactionId
   * @param connection connection
   * @return payoutTransactionsList
   */
  public ArrayList<Transaction> makeTransactionPaidOut(String transactionId,
      Connection connection) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    ArrayList<Transaction> payoutTransactionsList = null;

    TransactionDao transactionDao = new TransactionDao();
    PaymentBase paymentBase = new PaymentBase();

    try {
      paymentBase.processTransaction(transactionId);
      payoutTransactionsList = transactionDao
          .findByParentId(connection, Long.parseLong(transactionId), 50);
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return payoutTransactionsList;
  }

  /**
   * Update batch file sent date.
   * @param daysFromToday daysFromToday
   * @param payoutTransactionsList payoutTransactionsList
   * @param connection connection
   * @return batchFileSentDate
   */
  private String updateBatchFileSentDate(int daysFromToday,
      ArrayList<Transaction> payoutTransactionsList, Connection connection) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    BatchItemDao batchItemDao = new BatchItemDao();
    BatchFileDao batchFileDao = new BatchFileDao();

    UtilityManager um = new UtilityManager();
    Date backdate = um.addDays(new Date(), daysFromToday);
    String batchFileSentDate = UtilityManager.TIMESTAMP_FORMAT.format(backdate);

    try {

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
    } catch (Exception se) {
      se.getMessage();
      dataBaseConnector.closeConnection();
    }
    return batchFileSentDate;
  }

  /**
   * Moving this to the DbBase class so it can be used in both PaymentProcessingTest and
   * PaymentProcessingTestManagedDeposits classes.
   *
   * @param connection connection
   * @param transactionId transaction id
   * @param daysFromToday days from today
   */
  public void updateTransactionsDbTable(Connection connection, String transactionId,
      int daysFromToday) {

    TransactionDao transactionDao = new TransactionDao();

    ArrayList<Transaction> transactionsList = transactionDao
        .findById(connection, Long.parseLong(transactionId), 50);

    Transaction transaction = transactionsList.get(0);

    UtilityManager utilityManager = new UtilityManager();
    Date backdate = utilityManager.addDays(new Date(), daysFromToday);
    String newBackDate = UtilityManager.TIMESTAMP_FORMAT.format(backdate);

    transaction.setTransactionDate(newBackDate);

    int rowsAffected = transactionDao.update(connection, transaction);

    Logger.info("Number of rows affected are: " + rowsAffected);
    Logger.info("New transaction date is: " + transaction.getTransactionDate() + " for transaction "
        + "ID: " + transaction.getTransactionId());
  }


  /**
   * Trigger reversals and get expected refunds count.
   *
   * @param refundType refundType
   * @param transactionDetailPage transactionDetailPage
   * @param expectedRefundsCount expectedRefundsCount
   * @param processor processor
   * @return expectedRefundsCount
   */
  public int triggerReversalsAndGetExpectedRefundsCount(String refundType,
      TransactionDetailPage transactionDetailPage, int expectedRefundsCount, String processor) {
    boolean partial = true;
    switch (processor) {
      case "FNBO":
        if (refundType.equals(ADMIN_REFUND_MAX)) {
          partial = false;
        }
        transactionDetailPage.reverseTransBankFnbo(partial);
        break;

      case "FNBOCC":
        if (refundType.equals(ADMIN_REFUND_MAX)) {
          partial = false;
        }
        transactionDetailPage.reverseTransCc(partial);
        break;

      case "PROFITSTARS":
        if (refundType.equals(ADMIN_REFUND_MAX)) {
          partial = false;
        }
        transactionDetailPage.reverseTransBank(partial);
        break;
      default:
        break;
    }
    return expectedRefundsCount;
  }

  /**
   * Trigger refunds and get expected refunds count.
   *
   * @param refundType refundType
   * @param transactionDetailPage transactionDetailPage
   * @param expectedRefundsCount expectedRefundsCount
   * @param processor processor
   * @return expectedRefundsCount
   */
  public int triggerRefundsAndGetExpectedRefundsCount(String refundType,
      TransactionDetailPage transactionDetailPage, int expectedRefundsCount, String processor) {
    boolean partial = true;
    switch (processor) {
      case "FNBO":
        if (refundType.equals(ADMIN_REFUND_MAX)) {
          partial = false;
        }
        transactionDetailPage.refundTransBankFnbo(partial);
        break;

      case "FNBOCC":
        if (refundType.equals(ADMIN_REFUND_MAX)) {
          partial = false;
        }
        transactionDetailPage.refundTransCc(partial);
        break;

      case "PROFITSTARS":
        if (refundType.equals(ADMIN_REFUND_MAX)) {
          partial = false;
        }
        expectedRefundsCount++;
        transactionDetailPage.refundTransBank(partial);
        break;
      default:
        break;
    }
    return expectedRefundsCount;
  }

  /**
   * Get reversal transactions.
   *
   * @param connection connection
   * @param parentTransactionId parent transaction ID
   * @return reversalTransactionsList
   */
  public ArrayList<Transaction> getReversalTransactions(Connection connection,
      String parentTransactionId) {
    Logger.info("Parent Transaction ID: " + parentTransactionId);
    TransactionDao transactionDao = new TransactionDao();
    ArrayList<Transaction> transactionsList;

    transactionsList = transactionDao.findByParentId(connection,
        Long.parseLong(parentTransactionId), 10);

    ArrayList<Transaction> reversalTransactionsList = new ArrayList<>();
    for (Transaction transaction : transactionsList) {
      if (REVERSAL_TYPE_OF_TRANSACTION == transaction.getTypeOfTransaction()
          || REVERSAL_TYPE_OF_TRANSACTION_CC == transaction.getTypeOfTransaction()) {
        reversalTransactionsList.add(transaction);
      }
    }
    return reversalTransactionsList;
  }

  /**
   * Get transaction list for parent transaction ID.
   * @param transId transaction ID
   * @param connection connection
   * @return transactionIdList
   */
  public List<Long> getTransactionListForParentTransId(long transId, Connection connection) {
    TransactionDao transactionDao = new TransactionDao();
    Long transactionId = new Long(0);
    List<Long> transactionIdList = new ArrayList<>();
    ArrayList<Transaction> transactionsList = new ArrayList<>();
    transactionsList = transactionDao.findByParentId(connection, transId, 50);

    for (Transaction trans : transactionsList) {
      transactionId = trans.getTransactionId();
      transactionIdList.add(transactionId);
    }
    return transactionIdList;
  }

  public String makeTransactionRefundable(ArrayList<Transaction> payoutTransactionsList,
      Connection connection) {
    return updateBatchFileSentDate(-15, payoutTransactionsList, connection);
  }

  /**
   * Create refund details.
   *
   * @param refundsToPayoutsList refundsToPayoutsList
   * @param connection connection
   * @return refundDetailsList
   */
  public ArrayList<HashMap<String, String>> createRefundDetails(
      HashMap<Transaction, String> refundsToPayoutsList, Connection connection) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    BankAccountDao bankAccountDao = new BankAccountDao();
    ArrayList<HashMap<String, String>> refundDetailsList = new ArrayList<>();
    BankAccount bankAccount;

    for (Transaction payoutTransaction : refundsToPayoutsList.keySet()) {
      try {
        // Get bank account last 4 of payout transaction
        bankAccount = bankAccountDao
            .findById(connection, Long.valueOf(payoutTransaction.getPaymentTypeId()), 1).get(0);
        HashMap<String, String> refundDetailsMap = new HashMap<>();
        refundDetailsMap.put("account",
            bankAccount.getAccountNum().substring(bankAccount.getAccountNum().length() - 4));
        refundDetailsMap.put("paymentAmount", String.valueOf(payoutTransaction.getUnitAmount()));
        refundDetailsMap.put("refundAmount", refundsToPayoutsList.get(payoutTransaction));

        refundDetailsList.add(refundDetailsMap);
      } catch (Exception e) {
        Logger.error(e.getMessage());
        dataBaseConnector.closeConnection();
      }
    }
    return refundDetailsList;
  }

  /**
   * Verify transaction status.
   *
   * @param transactionId transactionId
   * @param payoutTransactionsList payoutTransactionsList
   * @param statusToMatch statusToMatch
   * @param batchFileSentDate batchFileSentDate
   * @param refundsToPayoutsList refundsToPayoutsList
   */
  public void verifyTransactionStatus(String transactionId,
      ArrayList<Transaction> payoutTransactionsList, String statusToMatch,
      String batchFileSentDate, HashMap<Transaction, String> refundsToPayoutsList) {
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);

    String expectedStatus;
    if (statusToMatch.equals(PAID_OUT)) {
      String formattedSentDate = "";

      try {
        formattedSentDate = UtilityManager.DATE_WITH_DASH_FORMAT
            .format(UtilityManager.TIMESTAMP_FORMAT.parse(batchFileSentDate));
      } catch (java.text.ParseException e) {
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

  /**
   * CheckBatchItems and TransactionsDbTable for New World transaction.
   *
   * @param dbBase dbBase
   * @param connection connection
   * @param transactionId transactionId
   * @param expectedStatus expectedStatus
   * @param expectedTypeOfTransaction expectedTypeOfTransaction
   */
  public void checkBatchItemsAndTransactionsDbTableForNewWorldChanges(DbBase dbBase, Connection
      connection, String transactionId, String expectedStatus, int expectedTypeOfTransaction) {

    dbBase.checkTransactionsDbTable(FindBy.TRANSACTION_ID, connection, transactionId,
        expectedStatus, expectedTypeOfTransaction);

    dbBase.checkBatchItemsDbTable(connection, transactionId, 0, null);
  }

  /**
   * Check if NACHA file was created.
   *
   * @param sshUtil sshUtil
   * @param fileId fileId
   * @param procId procId
   * @param total total
   * @param accountName accountName
   * @return true if NACHA file was created.
   */
  public boolean checkIfNachaFileWasCreated(SshUtil sshUtil, Long fileId, int procId, Double total,
                                            String accountName) {
    String concatenate = "cat fn" + fileId;
    String grep = " | grep " + procId;

    boolean isNachaFileCreated = false;

    // grep by proc id and validate amount and account person name for ACH transaction and PM
    // company for CC transaction
    String[] checkFile = new String[]{"cd web/batches/files/fn", concatenate + grep};

    String fileOutput = sshUtil.sshCommand(checkFile);

    ArrayList<String> outputs = new ArrayList<String>(Arrays.asList(fileOutput.split("\\s+")));

    String simplifiedTotal = String.valueOf(total).replace(".", "");

    Logger.info("Simplified total is: " + simplifiedTotal);

    if (outputs.size() > 0) {
      for (String output : outputs) {

        Logger.info("Output of the file is " + output);

        if (output.contains(simplifiedTotal) || output.contains(accountName)) {
          Assert.assertTrue(true, "Value doesn't match");
        }

        isNachaFileCreated = true;
      }
    }
    return isNachaFileCreated;
  }

  /**
   * Get amount from batch items table.
   *
   * @param procId procID
   * @param connection connection
   * @return amount
   */
  public double getAmountFromBatchItemsTable(int procId, Connection connection) {
    ArrayList<BatchItem> batchItemsList = new ArrayList<>();
    BatchItemDao batchItemDao = new BatchItemDao();
    double amount = 0;
    batchItemsList = batchItemDao.findById(connection, procId, 50);
    for (BatchItem batchItem : batchItemsList) {
      amount = batchItem.getAmount();
    }
    return amount;
  }

  /**
   * Get batch items
   * @param transactionIds list of transaction Ids
   * @param connection connection
   * @return batch items list
   */
  public List<BatchItem> getBatchItems(List<Long> transactionIds,
      Connection connection) {

    BatchItemDao batchItemDao = new BatchItemDao();
    ArrayList<BatchItem> batchItemsList = new ArrayList<>();
    try {
      for (long transaction : transactionIds) {
        batchItemsList.addAll(batchItemDao.findByTransId(connection, transaction, 50));
      }
    } catch (Exception e) {
      Logger.info("depositIds list is empty");
    }
    return batchItemsList;
  }

  protected Long getFileId(Connection connection, String transactionId) {

    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList = batchItemDao.findByTransId(connection,
        Long.parseLong(transactionId), 50);

    Assert.assertFalse(batchItemsList.isEmpty(), "Batch Items is empty");

    for (BatchItem batchItem : batchItemsList) {

      fileId = batchItem.getFileId();

      this.procId = batchItem.getProcId();
      this.accountPersonName = batchItem.getAccountPersonName();
      this.batchId = batchItem.getBatchId();
      this.amount = batchItem.getAmount();
      this.fromAcc = batchItem.getFromAcctNum();
      this.fromRoutingNum = batchItem.getFromRoutingNum();

      Logger.info("File ID is: " + fileId);
      Logger.info("Proc ID is: " + procId);
      Logger.info("Account person name is: " + accountPersonName);
      Logger.info("Batch ID is " + batchId);
      Logger.info("Account Number is " + fromAcc);
      Logger.info("Routing Number is " + fromRoutingNum);
    }

    return fileId;
  }


  public boolean checkDepositsTableForStatus(long depositId, Connection connection,
      String expectedStatus) {
    DepositDao depositDao = new DepositDao();
    ArrayList<Deposit> depositList = depositDao.findById(connection, depositId, 50);
    String status = " ";
    for (Deposit deposit : depositList) {
      status = deposit.getStatus();
    }
    return status.equals(expectedStatus);
  }

  public List<String> getExternalBatchIdfromExternalTransactionsTable(Connection connection,
      int transactionId) {
    ExternalTransactionsDao externalTransactionsDAO = new ExternalTransactionsDao();
    List<ExternalTransactionsDto> externalBatchIdListForTransId;
    List<String> externalBatchIdsListForTransactionId = new ArrayList<>();
    externalBatchIdListForTransId = externalTransactionsDAO
        .findByTransId(connection, transactionId, 50);
    for (ExternalTransactionsDto externalBatchId : externalBatchIdListForTransId) {
      String externalBatchIds = externalBatchId.getExternalBatchId();
      externalBatchIdsListForTransactionId.add(externalBatchIds);
    }
    return externalBatchIdsListForTransactionId;
  }

  public List<Long> getDepositIdList(Connection connection,
      List<Long> transactionIdsList) {

    return getDepositIdsFromBatchItemsTable(transactionIdsList, connection);
  }

  private List<Long> getDepositIdsFromBatchItemsTable(List<Long> transactionIds,
      Connection connection) {
    BatchItemDao batchItemDao = new BatchItemDao();

    ArrayList<BatchItem> batchItemsList;

    List<Long> depositIds = new ArrayList<>();
    try {
      for (Long transaction : transactionIds) {
        batchItemsList = batchItemDao.findByTransId(connection, transaction, 50);
        for (BatchItem batchItem : batchItemsList) {
          Long depositId = batchItem.getDepositId();
          if (depositId.intValue() != 0) {
            depositIds.add(depositId);
          }
        }
      }
    }catch (Exception e) {
      Logger.info("depositIds list is empty");
    }
    return depositIds;
  }

  public Deposit getDeposit(long id, Connection connection) {
    DepositDao depositDao = new DepositDao();
    ArrayList<Deposit> list = depositDao.findById(connection, id, 1);

    return list.get(0);
  }

  public ExternalBatchesDto getExternalBatch(long id, Connection connection) {
    ExternalBatchesDao externalBatchesDao = new ExternalBatchesDao();
    ArrayList<ExternalBatchesDto> list = externalBatchesDao.findByDepositId(connection, id, 1);

    return list.get(0);
  }

  public List<Long> getTransactionIdList(String processor, Connection connection,
      long transactionId, DbBase dbBase, List<Long> transactionIdsList,
      boolean missingProfitStarsLid) {
    if (processor.equals("FNBO")||missingProfitStarsLid) {
      transactionIdsList = dbBase
          .getTransactionListForParentTransId(transactionId, connection);
    } else {
      transactionIdsList.add(transactionId);
    }
    return transactionIdsList;
  }
}
