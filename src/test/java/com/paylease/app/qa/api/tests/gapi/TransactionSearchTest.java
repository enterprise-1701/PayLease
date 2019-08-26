
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.TransactionSearch;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class TransactionSearchTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionSearch";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "TransactionSearch"})
  public void requestValidation() {
    Logger.info("TransactionSearch request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    //TransactionSearch,99,3,,5
    testCases.add(
        TransactionSearch.createValid(
            "Test Case 3 - empty transaction type",
            getExpectedResponse(gatewayErrors, "99"))
            .setTransactionType("")
    );

    //TransactionSearch,108,8,BADVALUE,5
    testCases.add(
        TransactionSearch.createValid(
            "Test Case 8 - invalid trans type",
            getExpectedResponse(gatewayErrors, "108"))
            .setTransactionType("BADVALUE")
    );

    String[] validTransTypes = {
        "ACH", "CreditCard",
    };

    String[] zeroNumItems = {
        "", "0",
    };

    for (String transType : validTransTypes) {
      for (String numItems : zeroNumItems) {
        //TransactionSearch,100,4,ACH,
        //TransactionSearch,100,5,ACH,0
        //TransactionSearch,100,6,CreditCard,
        //TransactionSearch,100,7,CreditCard,0
        testCases.add(
            TransactionSearch.createValid(
                "Test Case 4-7 - zero/empty number of items",
                getExpectedResponse(gatewayErrors, "100"))
                .setTransactionType(transType)
                .setNumberOfItems(numItems)
        );
      }

      String[] invalidNumItems = {
          "-1", "BADVALUE",
      };

      for (String numItems : invalidNumItems) {
        //TransactionSearch,145,9,ACH,-1
        //TransactionSearch,145,10,ACH,BADVALUE
        //TransactionSearch,145,11,CreditCard,-1
        //TransactionSearch,145,12,CreditCard,BADVALUE
        testCases.add(
            TransactionSearch.createValid(
                "Test Case 9-12 - invalid number of items",
                getExpectedResponse(gatewayErrors, "145"))
                .setTransactionType(transType)
                .setNumberOfItems(numItems)
        );
      }
    }

    executeTests(testCases);
  }
}