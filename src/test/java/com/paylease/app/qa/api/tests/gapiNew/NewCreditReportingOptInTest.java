package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.CreditReportingOptInTransaction.CREDIT_REPORTING_OPT_IN_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.CreditReportingOptInTransaction;
import java.util.ArrayList;
import java.util.List;

public class NewCreditReportingOptInTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "creditReportingOptIn";

  private PayLeaseGatewayRequest buildRequest(Credentials credentials, String creditReportingId,
      String firstName, String lastName, String streetAddress, String city, String state,
      String zip, String monthToMonthCreditReporting, String leaseEndDate, String ssn,
      String birthDate) {

    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    CreditReportingOptInTransaction creditReportingOptInTransaction = new CreditReportingOptInTransaction();
    creditReportingOptInTransaction.setTransactionAction(CREDIT_REPORTING_OPT_IN_TRANSACTION);
    if (!creditReportingId.equals("")) {
      creditReportingOptInTransaction.setCreditReportingId(creditReportingId);
    }
    creditReportingOptInTransaction.setFirstName(firstName);
    creditReportingOptInTransaction.setLastName(lastName);
    creditReportingOptInTransaction.setStreetAddress(streetAddress);
    creditReportingOptInTransaction.setCity(city);
    creditReportingOptInTransaction.setState(state);
    creditReportingOptInTransaction.setZip(zip);

    if (!monthToMonthCreditReporting.equals("")) {
      creditReportingOptInTransaction.setMonthToMonthCreditReporting(monthToMonthCreditReporting);
    }

    if (!leaseEndDate.equals("")) {
      creditReportingOptInTransaction.setLeaseEndDate(leaseEndDate);
    }

    creditReportingOptInTransaction.setSsn(ssn);
    creditReportingOptInTransaction.setBirthDate(birthDate);

    transactionList.add(creditReportingOptInTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
