package com.paylease.app.qa.api.tests.gapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newGapi.CcPaymentTransaction.CC_PAYMENT_TRANSACTION;

import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.Deposit;
import com.paylease.app.qa.framework.newApi.PayLeaseGatewayRequest;
import com.paylease.app.qa.framework.newApi.SplitDeposit;
import com.paylease.app.qa.framework.newApi.Transaction;
import com.paylease.app.qa.framework.newApi.Transactions;
import com.paylease.app.qa.framework.newApi.newGapi.CcPaymentTransaction;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

public class NewCcPaymentTest extends ScriptBase {

  private static final String REGION = "gapi";
  private static final String FEATURE = "ccPayment";

  private PayLeaseGatewayRequest buildResponse(Credentials credentials, String paymentReferenceId,
      String payerSecondaryRefId, String paymentTraceId, String payeeId, String payerReferenceId,
      String creditReportingId, String payerFirstName, String payerLastName,
      String creditCardAction, String creditCardType, String creditCardNumber,
      String creditCardExpMonth, String creditCardExpYear, String creditCardCvv2,
      String billingFirstName, String billingLastName, String billingStreetAddress,
      String billingCity, String billingState, String billingCountry, String billingZip,
      boolean splitDeposit, String feeAmount, String message, String incurFee, String saveAccount) {
    PayLeaseGatewayRequest payLeaseGatewayRequest = new PayLeaseGatewayRequest();
    payLeaseGatewayRequest.setCredentials(credentials);
    payLeaseGatewayRequest.setMode(TEST_MODE);

    Transactions transactions = new Transactions();
    List<Transaction> transactionList = new ArrayList<>();

    CcPaymentTransaction ccPaymentTransaction = new CcPaymentTransaction();
    ccPaymentTransaction.setTransactionAction(CC_PAYMENT_TRANSACTION);
    ccPaymentTransaction.setPaymentReferenceId(paymentReferenceId);

    if (!payerSecondaryRefId.equals("")) {
      ccPaymentTransaction.setPayerSecondaryRefId(payerSecondaryRefId);
    }

    ccPaymentTransaction.setPaymentTraceId(paymentTraceId);
    ccPaymentTransaction.setPayerReferenceId(payerReferenceId);

    if (!creditReportingId.equals("")) {
      ccPaymentTransaction.setCreditReportingId(creditReportingId);
    }

    ccPaymentTransaction.setPayerFirstName(payerFirstName);
    ccPaymentTransaction.setPayerLastName(payerLastName);

    if (!creditCardAction.equals("")) {
      ccPaymentTransaction.setCreditCardAction(creditCardAction);
    }

    ccPaymentTransaction.setCreditCardType(creditCardType);
    ccPaymentTransaction.setCreditCardNumber(creditCardNumber);
    ccPaymentTransaction.setCreditCardExpMonth(creditCardExpMonth);
    ccPaymentTransaction.setCreditCardExpYear(creditCardExpYear);

    if (!creditCardCvv2.equals("")) {
      ccPaymentTransaction.setCreditCardCvv2(creditCardCvv2);
    }

    ccPaymentTransaction.setBillingFirstName(billingFirstName);
    ccPaymentTransaction.setBillingLastName(billingLastName);
    ccPaymentTransaction.setBillingStreetAddress(billingStreetAddress);
    ccPaymentTransaction.setBillingCity(billingCity);
    ccPaymentTransaction.setBillingState(billingState);
    ccPaymentTransaction.setBillingCountry(billingCountry);
    ccPaymentTransaction.setBillingZip(billingZip);

    double feeAmountVal = 0.0;
    if (!feeAmount.equals("")) {
      feeAmountVal = Double.parseDouble(feeAmount);
      ccPaymentTransaction.setFeeAmount(feeAmountVal);
    }

    if (splitDeposit) {
      DecimalFormat decim = new DecimalFormat("0.00");

      SplitDeposit splitDeposit1 = new SplitDeposit();
      Deposit deposit1 = new Deposit();
      deposit1.setPayeeId(payeeId);
      double amount1 = Double.parseDouble(
          "2" + RandomStringUtils.random(1, false, true) + "." + RandomStringUtils
              .random(2, false, true));
      deposit1.setAmount(amount1);

      Deposit deposit2 = new Deposit();
      deposit2.setPayeeId(payeeId);
      double amount2 = Double.parseDouble(
          "2" + RandomStringUtils.random(1, false, true) + "." + RandomStringUtils
              .random(2, false, true));
      deposit2.setAmount(amount2);

      List<Deposit> depositList = new ArrayList<>();
      depositList.add(deposit1);
      depositList.add(deposit2);
      splitDeposit1.setDepositList(depositList);

      ccPaymentTransaction.setSplitDeposit(splitDeposit1);
      ccPaymentTransaction
          .setTotalAmount(decim.format(feeAmountVal + deposit1.getAmount() + deposit2.getAmount()));
    } else {
      String totalAmount = "2" + RandomStringUtils.random(2, false, true) + "." + RandomStringUtils
          .random(2, false, true);
      ccPaymentTransaction.setTotalAmount(totalAmount);
    }

    if (!message.equals("")) {
      ccPaymentTransaction.setMessage(message);
    }

    if (!incurFee.equals("")) {
      ccPaymentTransaction.setIncurFee(incurFee);
    }

    ccPaymentTransaction.setSaveAccount(saveAccount);

    transactionList.add(ccPaymentTransaction);
    transactions.setTransactions(transactionList);
    payLeaseGatewayRequest.setTransactions(transactions);

    return payLeaseGatewayRequest;
  }

}
