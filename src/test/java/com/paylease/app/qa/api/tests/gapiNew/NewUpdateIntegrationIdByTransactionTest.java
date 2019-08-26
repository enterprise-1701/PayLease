package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.UpdateIntegrationIdByTransactionTransaction.UPDATE_INTEGRATION_ID_BY_TRANSACTION_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.UpdateIntegrationIdByTransactionTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewUpdateIntegrationIdByTransactionTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "updateIntegrationIdByTransaction";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String transactionId,
      String integrationId, String secondaryIntegrationId) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    UpdateIntegrationIdByTransactionTransaction updateIntegrationIdByTransactionTransaction = new UpdateIntegrationIdByTransactionTransaction();
    updateIntegrationIdByTransactionTransaction
        .setTransactionAction(UPDATE_INTEGRATION_ID_BY_TRANSACTION_TRANSACTION);
    updateIntegrationIdByTransactionTransaction.setTransactionId(transactionId);
    updateIntegrationIdByTransactionTransaction.setIntegrationId(integrationId);

    if (!secondaryIntegrationId.equals("")) {
      updateIntegrationIdByTransactionTransaction.setSecondaryIntegrationId(secondaryIntegrationId);
    }

    transactionList.add(updateIntegrationIdByTransactionTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
