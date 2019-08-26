package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.RdcTransactionCallbackTransaction;
import java.util.ArrayList;
import java.util.HashMap;

public class RdcTransactionCallback extends BasicTestCase {

  private static final String BATCH_KEY_USER_ID = "userId";
  private static final String BATCH_KEY_LOCATION_ID = "locationId";
  private static final String BATCH_KEY_DEPOSIT_TID = "depositTid";
  private static final String BATCH_KEY_DEPOSIT_AMOUNT = "depositAmount";
  private static final String BATCH_KEY_DEPOSIT_ACCOUNT = "depositAccount";
  private static final String BATCH_KEY_DEPOSIT_ROUTING = "depositRouting";

  private static final String CHECK_KEY_SEQUENCE_NUMBER = "sequenceNumber";
  private static final String CHECK_KEY_PROPERTY_ID = "propertyId";
  private static final String CHECK_KEY_TENANT_ID = "tenantId";
  private static final String CHECK_KEY_RESIDENT_ID = "residentId";
  private static final String CHECK_KEY_FIRST_NAME = "firstName";
  private static final String CHECK_KEY_LAST_NAME = "lastName";
  private static final String CHECK_KEY_AMOUNT = "amount";
  private static final String CHECK_KEY_ACCOUNT = "account";
  private static final String CHECK_KEY_SERIAL = "serial";
  private static final String CHECK_KEY_ROUTING = "routing";

  private HashMap<String, HashMap<String, String>> batches;
  private HashMap<String, ArrayList<HashMap<String, String>>> checks;

  /**
   * Create an RdcTransactionCallback test case.
   *
   * @param summary Summary of this test case
   * @param expectedResponse Expected API response
   */
  public RdcTransactionCallback(String summary, ExpectedResponse expectedResponse) {
    super(summary, expectedResponse);

    this.batches = new HashMap<>();
    this.checks = new HashMap<>();
  }

  /**
   * Create a batch for this test case.
   *
   * @param userId PMID for the batch
   * @param locationId LocationID for the batch
   * @param depositTid BankAccount for Batch Deposit (with a "P" on the front)
   * @param depositAmount Amount of the deposit
   * @param depositAccount Account to deposit to
   * @param depositRouting Routing number to deposit to
   * @return Batch sequence number - for use in adding checks to batch
   */
  public String createBatch(String userId, String locationId, String depositTid,
      String depositAmount, String depositAccount, String depositRouting) {
    DataHelper dataHelper = new DataHelper();

    HashMap<String, String> batch = new HashMap<>();
    batch.put(BATCH_KEY_USER_ID, userId);
    batch.put(BATCH_KEY_LOCATION_ID, locationId);
    batch.put(BATCH_KEY_DEPOSIT_TID, depositTid);
    batch.put(BATCH_KEY_DEPOSIT_AMOUNT, depositAmount);
    batch.put(BATCH_KEY_DEPOSIT_ACCOUNT, depositAccount);
    batch.put(BATCH_KEY_DEPOSIT_ROUTING, depositRouting);

    String sequenceNumber = dataHelper.getSequenceNumber();
    batches.put(sequenceNumber, batch);
    checks.put(sequenceNumber, new ArrayList<>());
    return sequenceNumber;
  }

  /**
   * Add a check to a batch.
   *
   * @param batchSequence Batch Sequence number for matching to a batch
   * @param propertyId Property ID of the resident submitting the check
   * @param tenantId External User ID of the resident
   * @param residentId PayLease resident user Id
   * @param firstName Resident First Name
   * @param lastName Resident Last Name
   * @param amount Amount of the check
   * @param account Account number of the check
   * @param serial Serial number on the check
   * @param routing Routing number of the check
   */
  public void addCheckToBatch(String batchSequence, String propertyId, String tenantId,
      String residentId, String firstName, String lastName, String amount, String account,
      String serial, String routing) {
    DataHelper dataHelper = new DataHelper();

    HashMap<String, String> check = new HashMap<>();
    String sequenceNumber = dataHelper.getSequenceNumber();
    check.put(CHECK_KEY_SEQUENCE_NUMBER, sequenceNumber);
    check.put(CHECK_KEY_PROPERTY_ID, propertyId);
    check.put(CHECK_KEY_TENANT_ID, tenantId);
    check.put(CHECK_KEY_RESIDENT_ID, residentId);
    check.put(CHECK_KEY_FIRST_NAME, firstName);
    check.put(CHECK_KEY_LAST_NAME, lastName);
    check.put(CHECK_KEY_AMOUNT, amount);
    check.put(CHECK_KEY_ACCOUNT, account);
    check.put(CHECK_KEY_SERIAL, serial);
    check.put(CHECK_KEY_ROUTING, routing);

    checks.get(batchSequence).add(check);
  }

  @Override
  public void addTransaction(Request apiRequest) {
    ParameterCollection parameters = getTransactionParameterCollection();

    Parameter output = new Parameter();

    for (String batchSequence : batches.keySet()) {
      HashMap<String, String> batch = batches.get(batchSequence);
      Parameter batchParam = new Parameter();
      batchParam
          .addParameter(RdcTransactionCallbackTransaction.USER_ID, batch.get(BATCH_KEY_USER_ID));
      batchParam.addParameter(RdcTransactionCallbackTransaction.LOCATION_ID,
          batch.get(BATCH_KEY_LOCATION_ID));
      batchParam.addParameter(RdcTransactionCallbackTransaction.DEPOSIT_TID,
          batch.get(BATCH_KEY_DEPOSIT_TID));
      batchParam.addParameter(RdcTransactionCallbackTransaction.DEPOSIT_AMOUNT,
          batch.get(BATCH_KEY_DEPOSIT_AMOUNT));
      batchParam.addParameter(RdcTransactionCallbackTransaction.DEPOSIT_ACCOUNT,
          batch.get(BATCH_KEY_DEPOSIT_ACCOUNT));
      batchParam.addParameter(RdcTransactionCallbackTransaction.DEPOSIT_ABA,
          batch.get(BATCH_KEY_DEPOSIT_ROUTING));
      batchParam.addParameter(RdcTransactionCallbackTransaction.SEQUENCE_NUMBER, batchSequence);

      for (HashMap<String, String> check : checks.get(batchSequence)) {
        Parameter checkParam = new Parameter();
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_PROPERTY_ID,
            check.get(CHECK_KEY_PROPERTY_ID));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_TENANT_ID,
            check.get(CHECK_KEY_TENANT_ID));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_RESIDENT_ID,
            check.get(CHECK_KEY_RESIDENT_ID));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_FIRST_NAME,
            check.get(CHECK_KEY_FIRST_NAME));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_LAST_NAME,
            check.get(CHECK_KEY_LAST_NAME));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_AMOUNT,
            check.get(CHECK_KEY_AMOUNT));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_ACCOUNT,
            check.get(CHECK_KEY_ACCOUNT));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_SERIAL,
            check.get(CHECK_KEY_SERIAL));
        checkParam.addParameter(RdcTransactionCallbackTransaction.CHECK_ABA,
            check.get(CHECK_KEY_ROUTING));
        checkParam.addParameter(RdcTransactionCallbackTransaction.SEQUENCE_NUMBER,
            check.get(CHECK_KEY_SEQUENCE_NUMBER));

        batchParam.addParameter(RdcTransactionCallbackTransaction.CHECK, checkParam);
      }

      output.addParameter(RdcTransactionCallbackTransaction.BATCH, batchParam);
    }
    parameters.put(RdcTransactionCallbackTransaction.OUTPUT, output);

    AapiTransaction transaction = new RdcTransactionCallbackTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }
}
