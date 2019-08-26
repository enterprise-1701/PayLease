
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.TransactionVoid;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class TransactionVoidTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionVoid";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "TransactionVoid"})
  public void requestInitiated() {
    Logger.info("TransactionVoid request for initiated transaction");

    if (isProcessLocked("1")) {
      return;
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //TransactionVoid,14,1,60844001
    testCases.add(
        new TransactionVoid(
            transactionId,
            "Test Case 1 - basic valid initiated",
            getExpectedResponse(gatewayErrors, "14"))
    );

    //TransactionVoid,97,2,
    testCases.add(
        new TransactionVoid(
            "",
            "Test Case 2 - empty transaction id",
            getExpectedResponse(gatewayErrors, "97"))
    );

    String[] invalidTransIds = {
        "29332973a", "29332973!", "2a", "2!",
    };

    for (String transId : invalidTransIds) {
      //TransactionVoid,143,4,29332973a
      //TransactionVoid,143,5,29332973!
      //TransactionVoid,143,6,2a
      //TransactionVoid,143,7,2!
      testCases.add(
          new TransactionVoid(
              transId,
              "Test Case 4-7 - invalid transaction id",
              getExpectedResponse(gatewayErrors, "143"))
      );
    }

    String[] incorrectTransIds = {
        "2", String.valueOf(transactionId + 1),
    };

    for (String transId : incorrectTransIds) {
      //TransactionVoid,154,8,2
      //TransactionVoid,154,9,293329733
      testCases.add(
          new TransactionVoid(
              transId,
              "Test Case 8-9 - incorrect transaction id",
              getExpectedResponse(gatewayErrors, "154"))
      );
    }

    executeTests(testCases);
  }


  @Test(groups = {"gapi", "TransactionVoid"})
  public void requestCompleted() {
    Logger.info("TransactionVoid request for completed transaction");

    if (isProcessLocked("1")) {
      return;
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //TransactionVoid,166,3,60843997
    testCases.add(
        new TransactionVoid(
            transactionId,
            "Test Case 3 - trans already completed",
            getExpectedResponse(gatewayErrors, "166"))
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "TransactionVoid"})
  public void requestCancelled() {
    Logger.info("TransactionVoid request for cancelled transaction");

    if (isProcessLocked("1")) {
      return;
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //TransactionVoid,176,10,60844002
    testCases.add(
        new TransactionVoid(
            transactionId,
            "Test Case 10 - trans already cancelled",
            getExpectedResponse(gatewayErrors, "176"))
    );

    executeTests(testCases);
  }

  @Test(groups = {"gapi", "TransactionVoid"})
  public void paysafeEftTransactionVoidTest() {
    createAndSendRequest("TransactionVoid request for Paysafe EFT transaction with status 1", "tc4",
        "TransactionVoid request for Paysafe EFT transaction with status 1", "14");
  }

  @Test(groups = {"gapi", "TransactionVoid"})
  public void paysafeEftProcessedTransactionVoidTest() {
    createAndSendRequest("TransactionVoid request for Paysafe EFT transaction with status 2", "tc5",
        "TransactionVoid request for Paysafe EFT transaction with status 2", "166");
  }

  @Test(groups = {"gapi", "TransactionVoid"})
  public void paysafeEftNsfTransactionVoidTest() {
    createAndSendRequest("TransactionVoid request for Paysafe EFT NSF transaction", "tc6",
        "TransactionVoid request for Paysafe EFT NSF transaction", "166");
  }

  @Test(groups = {"gapi", "TransactionVoid"})
  public void paysafeEftVoidedTransactionVoidTest() {
    createAndSendRequest("TransactionVoid request for Paysafe EFT voided transaction", "tc7",
        "TransactionVoid request for Paysafe EFT voided transaction", "176");
  }

  @Test(groups = {"gapi", "TransactionVoid"})
  public void paysafeCcTransactionVoidTest() {
    createAndSendRequest("TransactionVoid request for Paysafe CC transaction", "tc8",
        "TransactionVoid request for Paysafe CC transaction", "167");
  }

  /**
   * Create and send a gapi request.
   */
  public void createAndSendRequest(String loggerInfo, String setUpPage, String summary,
      String expectedResponse) {
    Logger.info(loggerInfo);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, setUpPage);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String transactionId = testSetupPage.getString("transactionId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");
    final String processLockId = testSetupPage.getString("processLockId");

    if (isProcessLocked(processLockId)) {
      return;
    }

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    testCases.add(
        new TransactionVoid(
            transactionId,
            summary,
            getExpectedResponse(gatewayErrors, expectedResponse))
    );
    executeTests(testCases);
  }

  //------------------------------------Test Method-------------------------------------------------

  private boolean isProcessLocked(String processLockId) {
    String sqlQuery = "SELECT is_locked FROM process_lock WHERE process_lock_id =" + processLockId;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    boolean isLocked = true;
    int attempts = 0;

    while (isLocked) {
      attempts++;
      ResultSet query = dataBaseConnector.executeSqlQuery(sqlQuery);

      try {
        if (query.first()) {
          isLocked = query.getString("is_locked").equals("1");
          if (isLocked) {
            Thread.sleep(500); // see if we can wait for the process to finish
            if (attempts > 30) {
              break; // but don't wait forever
            }
          }
        } else {
          isLocked = false;
          break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

    dataBaseConnector.closeConnection();

    if (isLocked) {
      Logger.warning("Transaction processing Locked - TransactionVoid attempt prevented");
    }
    return isLocked;
  }
}