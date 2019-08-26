
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.Deposit;
import com.paylease.app.qa.api.tests.gapi.testcase.DepositTransaction;
import com.paylease.app.qa.api.tests.gapi.testcase.DepositsByDateRange;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class DepositsByDateRangeTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "depositsByDateRange";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void requestValidation() {
    Logger.info("DepositsByDateRange basic request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //DepositsByDateRange,171,3,,1/1/2015
    testCases.add(
        DepositsByDateRange.createValid(
            "Test Case 3 - empty start date",
            getExpectedResponse(gatewayErrors, "171"))
            .setSearchStartDate("")
    );

    //DepositsByDateRange,174,14,1/1/2015,
    testCases.add(
        DepositsByDateRange.createValid(
            "Test Case 14 - empty end date",
            getExpectedResponse(gatewayErrors, "174"))
            .setSearchEndDate("")
    );

    String[] invalidDates = {
        "/1/2015", "1a/1/2015", "111/1/2015", "1//2015", "1/1a/2015", "1/111/2015", "1/1/", "1/1/2",
        "1/1/15", "1/1/2015a",
    };

    for (String date : invalidDates) {
      //DepositsByDateRange,172,4,/1/2015,1/1/2015
      //DepositsByDateRange,172,5,1a/1/2015,1/1/2015
      //DepositsByDateRange,172,6,111/1/2015,1/1/2015
      //DepositsByDateRange,172,7,1//2015,1/1/2015
      //DepositsByDateRange,172,8,1/1a/2015,1/1/2015
      //DepositsByDateRange,172,9,1/111/2015,1/1/2015
      //DepositsByDateRange,172,10,1/1/,1/1/2015
      //DepositsByDateRange,172,11,1/1/2,1/1/2015
      //DepositsByDateRange,172,12,1/1/15,1/1/2015
      //DepositsByDateRange,172,13,1/1/2015a,1/1/2015
      testCases.add(
          DepositsByDateRange.createValid(
              "Test Case 4-13 - invalid start date",
              getExpectedResponse(gatewayErrors, "172"))
              .setSearchStartDate(date)
      );

      //DepositsByDateRange,175,15,1/1/2015,/1/2015
      //DepositsByDateRange,175,16,1/1/2015,1a/1/2015
      //DepositsByDateRange,175,17,1/1/2015,111/1/2015
      //DepositsByDateRange,175,18,1/1/2015,1//2015
      //DepositsByDateRange,175,19,1/1/2015,1/1a/2015
      //DepositsByDateRange,175,20,1/1/2015,1/111/2015
      //DepositsByDateRange,175,21,1/1/2015,1/1/
      //DepositsByDateRange,175,22,1/1/2015,1/1/2
      //DepositsByDateRange,175,23,1/1/2015,1/1/15
      //DepositsByDateRange,175,24,1/1/2015,1/1/2015a
      testCases.add(
          DepositsByDateRange.createValid(
              "Test Case 15-24 - invalid end date",
              getExpectedResponse(gatewayErrors, "175"))
              .setSearchEndDate(date)
      );
    }

    //DepositsByDateRange,241,25,01/26/2015,01/27/2011
    testCases.add(
        DepositsByDateRange.createValid(
            "Test Case 25 - end date before start date",
            getExpectedResponse(gatewayErrors, "241"))
            .setSearchStartDate("01/26/2015")
            .setSearchEndDate("01/27/2011")
    );

    //DepositsByDateRange,241,26,01/26/2011,01/27/2015
    //DepositsByDateRange,241,27,01/26/2011,01/27/2015
    testCases.add(
        DepositsByDateRange.createValid(
            "Test Case 26 - date range too big",
            getExpectedResponse(gatewayErrors, "241"))
            .setSearchStartDate("01/26/2011")
            .setSearchEndDate("01/27/2015")
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void canadaCcDeposit() {
    Logger.info("DepositsByDateRange - Paysafe CC deposit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc1");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String initiatedDate = testSetupPage.getString("initiatedDate");
    final String payoutDate = testSetupPage.getString("payoutDate");
    final String payeeId = testSetupPage.getString("payeeId");
    final String transId = testSetupPage.getString("transId");
    final String accountNum = testSetupPage.getString("accountNum");
    final String amount = testSetupPage.getString("amount");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    final String amountFormatted = moneyFormat.format(Float.valueOf(amount));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit deposit = new Deposit();
    deposit.setPayeeId(payeeId);
    deposit.setAccountNumber(accountNum.substring(accountNum.length() - 4));
    deposit.setDepositDate(depositDate);
    deposit.setAmount(amountFormatted);
    deposit.setCurrencyCode("CAD");
    DepositTransaction transaction = new DepositTransaction();
    transaction.setTransactionId(transId);
    transaction.setInitiatedDate(initiatedDate);
    transaction.setPayoutDate(payoutDate);
    transaction.setPaymentType("CC");
    transaction.setAmount(amountFormatted);
    transaction.setCurrencyCode("CAD");
    deposit.addTransaction(transaction);

    testCases.add(
        new DepositsByDateRange(
            "Check CC deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("CAD")
            .setTotalDeposits("1")
            .setTotalDepositAmount(amountFormatted)
            .setTotalCurrencyCode("CAD")
            .addDeposit(deposit)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void canadaCcSplitDeposit() {
    Logger.info("DepositsByDateRange - Paysafe CC split deposit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc3");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String initiatedDate = testSetupPage.getString("initiatedDate");
    final String payoutDateA = testSetupPage.getString("payoutDateA");
    final String payoutDateB = testSetupPage.getString("payoutDateB");
    final String payeeIdA = testSetupPage.getString("payeeIdA");
    final String payeeIdB = testSetupPage.getString("payeeIdB");
    final String transId = testSetupPage.getString("transId");
    final String accountNumA = testSetupPage.getString("accountNumA");
    final String accountNumB = testSetupPage.getString("accountNumB");
    final String amountA = testSetupPage.getString("amountA");
    final String amountB = testSetupPage.getString("amountB");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    final String amountFormattedA = moneyFormat.format(Float.valueOf(amountA));
    final String amountFormattedB = moneyFormat.format(Float.valueOf(amountB));
    final String totalAmountFormatted = moneyFormat
        .format(Float.valueOf(amountA) + Float.valueOf(amountB));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit depositA = new Deposit();
    depositA.setPayeeId(payeeIdA);
    depositA.setAccountNumber(accountNumA.substring(accountNumA.length() - 4));
    depositA.setDepositDate(depositDate);
    depositA.setAmount(amountFormattedA);
    depositA.setCurrencyCode("CAD");
    DepositTransaction transactionA = new DepositTransaction();
    transactionA.setTransactionId(transId);
    transactionA.setInitiatedDate(initiatedDate);
    transactionA.setPayoutDate(payoutDateA);
    transactionA.setPaymentType("CC");
    transactionA.setAmount(amountFormattedA);
    transactionA.setCurrencyCode("CAD");
    depositA.addTransaction(transactionA);

    Deposit depositB = new Deposit();
    depositB.setPayeeId(payeeIdB);
    depositB.setAccountNumber(accountNumB.substring(accountNumB.length() - 4));
    depositB.setDepositDate(depositDate);
    depositB.setAmount(amountFormattedB);
    depositB.setCurrencyCode("CAD");
    DepositTransaction transactionB = new DepositTransaction();
    transactionB.setTransactionId(transId);
    transactionB.setInitiatedDate(initiatedDate);
    transactionB.setPayoutDate(payoutDateB);
    transactionB.setPaymentType("CC");
    transactionB.setAmount(amountFormattedB);
    transactionB.setCurrencyCode("CAD");
    depositB.addTransaction(transactionB);

    testCases.add(
        new DepositsByDateRange(
            "Check CC deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("CAD")
            .setTotalDeposits("2")
            .setTotalDepositAmount(totalAmountFormatted)
            .setTotalCurrencyCode("CAD")
            .addDeposit(depositA)
            .addDeposit(depositB)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void canadaCcAndEftDeposit() {
    Logger.info("DepositsByDateRange - Paysafe CC AND EFT deposits same day");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc4");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String transIdEft = testSetupPage.getString("transIdEft");
    final String transIdCc = testSetupPage.getString("transIdCc");
    final String accountNumEft = testSetupPage.getString("accountNumEft");
    final String accountNumCc = testSetupPage.getString("accountNumCc");
    final String amountEft = testSetupPage.getString("amountEft");
    final String amountCc = testSetupPage.getString("amountCc");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    Float totalDepositAmountNumber = Float.valueOf(amountEft) + Float.valueOf(amountCc);
    String totalDepositAmount = moneyFormat.format(totalDepositAmountNumber);
    String amountEftFormatted = moneyFormat.format(Float.valueOf(amountEft));
    String amountCcFormatted = moneyFormat.format(Float.valueOf(amountCc));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit depositEft = new Deposit();
    depositEft.setAccountNumber(accountNumEft.substring(accountNumEft.length() - 4));
    DepositTransaction transactionEft = new DepositTransaction();
    transactionEft.setTransactionId(transIdEft);
    transactionEft.setPaymentType("ACH");
    transactionEft.setAmount(amountEftFormatted);
    depositEft.addTransaction(transactionEft);

    Deposit depositCc = new Deposit();
    depositCc.setAccountNumber(accountNumCc.substring(accountNumCc.length() - 4));
    DepositTransaction transactionCc = new DepositTransaction();
    transactionCc.setTransactionId(transIdCc);
    transactionCc.setPaymentType("CC");
    transactionCc.setAmount(amountCcFormatted);
    depositCc.addTransaction(transactionCc);

    testCases.add(
        new DepositsByDateRange(
            "Check CC and EFT deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("CAD")
            .setTotalDeposits("2")
            .setTotalDepositAmount(totalDepositAmount)
            .setTotalCurrencyCode("CAD")
            .addDeposit(depositEft)
            .addDeposit(depositCc)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void canadaEftDeposit() {
    Logger.info("DepositsByDateRange - Paysafe EFT deposit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc5");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String initiatedDate = testSetupPage.getString("initiatedDate");
    final String payoutDate = testSetupPage.getString("payoutDate");
    final String payeeId = testSetupPage.getString("payeeId");
    final String transId = testSetupPage.getString("transId");
    final String accountNum = testSetupPage.getString("accountNum");
    final String amount = testSetupPage.getString("amount");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    final String amountFormatted = moneyFormat.format(Float.valueOf(amount));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit deposit = new Deposit();
    deposit.setPayeeId(payeeId);
    deposit.setAccountNumber(accountNum.substring(accountNum.length() - 4));
    deposit.setDepositDate(depositDate);
    deposit.setAmount(amountFormatted);
    deposit.setCurrencyCode("CAD");
    DepositTransaction transaction = new DepositTransaction();
    transaction.setTransactionId(transId);
    transaction.setInitiatedDate(initiatedDate);
    transaction.setPayoutDate(payoutDate);
    transaction.setPaymentType("ACH");
    transaction.setAmount(amountFormatted);
    transaction.setCurrencyCode("CAD");
    deposit.addTransaction(transaction);

    testCases.add(
        new DepositsByDateRange(
            "Check EFT deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("CAD")
            .setTotalDeposits("1")
            .setTotalDepositAmount(amountFormatted)
            .setTotalCurrencyCode("CAD")
            .addDeposit(deposit)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void canadaEftSplitDeposit() {
    Logger.info("DepositsByDateRange - Paysafe EFT split deposit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc6");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String initiatedDate = testSetupPage.getString("initiatedDate");
    final String payoutDateA = testSetupPage.getString("payoutDateA");
    final String payoutDateB = testSetupPage.getString("payoutDateB");
    final String payeeIdA = testSetupPage.getString("payeeIdA");
    final String payeeIdB = testSetupPage.getString("payeeIdB");
    final String transId = testSetupPage.getString("transId");
    final String accountNumA = testSetupPage.getString("accountNumA");
    final String accountNumB = testSetupPage.getString("accountNumB");
    final String amountA = testSetupPage.getString("amountA");
    final String amountB = testSetupPage.getString("amountB");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    final String amountFormattedA = moneyFormat.format(Float.valueOf(amountA));
    final String amountFormattedB = moneyFormat.format(Float.valueOf(amountB));
    final String totalAmountFormatted = moneyFormat
        .format(Float.valueOf(amountA) + Float.valueOf(amountB));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit depositA = new Deposit();
    depositA.setPayeeId(payeeIdA);
    depositA.setAccountNumber(accountNumA.substring(accountNumA.length() - 4));
    depositA.setDepositDate(depositDate);
    depositA.setAmount(amountFormattedA);
    depositA.setCurrencyCode("CAD");
    DepositTransaction transactionA = new DepositTransaction();
    transactionA.setTransactionId(transId);
    transactionA.setInitiatedDate(initiatedDate);
    transactionA.setPayoutDate(payoutDateA);
    transactionA.setPaymentType("ACH");
    transactionA.setAmount(amountFormattedA);
    transactionA.setCurrencyCode("CAD");
    depositA.addTransaction(transactionA);

    Deposit depositB = new Deposit();
    depositB.setPayeeId(payeeIdB);
    depositB.setAccountNumber(accountNumB.substring(accountNumB.length() - 4));
    depositB.setDepositDate(depositDate);
    depositB.setAmount(amountFormattedB);
    depositB.setCurrencyCode("CAD");
    DepositTransaction transactionB = new DepositTransaction();
    transactionB.setTransactionId(transId);
    transactionB.setInitiatedDate(initiatedDate);
    transactionB.setPayoutDate(payoutDateB);
    transactionB.setPaymentType("ACH");
    transactionB.setAmount(amountFormattedB);
    transactionB.setCurrencyCode("CAD");
    depositB.addTransaction(transactionB);

    testCases.add(
        new DepositsByDateRange(
            "Check EFT deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("CAD")
            .setTotalDeposits("2")
            .setTotalDepositAmount(totalAmountFormatted)
            .setTotalCurrencyCode("CAD")
            .addDeposit(depositA)
            .addDeposit(depositB)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void usAchDeposit() {
    Logger.info("DepositsByDateRange - Profitstars ACH deposit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc7");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String initiatedDate = testSetupPage.getString("initiatedDate");
    final String payoutDate = testSetupPage.getString("payoutDate");
    final String payeeId = testSetupPage.getString("payeeId");
    final String transId = testSetupPage.getString("transId");
    final String accountNum = testSetupPage.getString("accountNum");
    final String amount = testSetupPage.getString("amount");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    final String amountFormatted = moneyFormat.format(Float.valueOf(amount));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit deposit = new Deposit();
    deposit.setPayeeId(payeeId);
    deposit.setAccountNumber(accountNum.substring(accountNum.length() - 4));
    deposit.setDepositDate(depositDate);
    deposit.setAmount(amountFormatted);
    deposit.setCurrencyCode("USD");
    DepositTransaction transaction = new DepositTransaction();
    transaction.setTransactionId(transId);
    transaction.setInitiatedDate(initiatedDate);
    transaction.setPayoutDate(payoutDate);
    transaction.setPaymentType("ACH");
    transaction.setAmount(amountFormatted);
    transaction.setCurrencyCode("USD");
    deposit.addTransaction(transaction);

    testCases.add(
        new DepositsByDateRange(
            "Check EFT deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("USD")
            .setTotalDeposits("1")
            .setTotalDepositAmount(amountFormatted)
            .setTotalCurrencyCode("USD")
            .addDeposit(deposit)
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "DepositsByDateRange"})
  public void usCcDeposit() {
    Logger.info("DepositsByDateRange - FNBO CC deposit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "content_tc8");
    testSetupPage.skipTestIfItsWeekend();
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String depositDate = testSetupPage.getString("depositDate");
    final String initiatedDate = testSetupPage.getString("initiatedDate");
    final String payoutDate = testSetupPage.getString("payoutDate");
    final String payeeId = testSetupPage.getString("payeeId");
    final String transId = testSetupPage.getString("transId");
    final String accountNum = testSetupPage.getString("accountNum");
    final String amount = testSetupPage.getString("amount");

    DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");
    final String amountFormatted = moneyFormat.format(Float.valueOf(amount));

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    Deposit deposit = new Deposit();
    deposit.setPayeeId(payeeId);
    deposit.setAccountNumber(accountNum.substring(accountNum.length() - 4));
    deposit.setDepositDate(depositDate);
    deposit.setAmount(amountFormatted);
    deposit.setCurrencyCode("USD");
    DepositTransaction transaction = new DepositTransaction();
    transaction.setTransactionId(transId);
    transaction.setInitiatedDate(initiatedDate);
    transaction.setPayoutDate(payoutDate);
    transaction.setPaymentType("CC");
    transaction.setAmount(amountFormatted);
    transaction.setCurrencyCode("USD");
    deposit.addTransaction(transaction);

    testCases.add(
        new DepositsByDateRange(
            "Check CC deposit details")
            .setSearchStartDate(depositDate)
            .setSearchEndDate(depositDate)
            .setCurrencyCode("USD")
            .setTotalDeposits("1")
            .setTotalDepositAmount(amountFormatted)
            .setTotalCurrencyCode("USD")
            .addDeposit(deposit)
    );

    executeTests(testCases);
  }
}