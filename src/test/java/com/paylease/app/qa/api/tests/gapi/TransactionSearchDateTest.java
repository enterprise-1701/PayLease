
package com.paylease.app.qa.api.tests.gapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.gapi.testcase.TransactionSearchDate;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class TransactionSearchDateTest extends BaseTest {

  private static final String REGION = "gapi";
  private static final String FEATURE = "transactionSearchDate";

  //-----------------------------------TESTS----------------------------------------------

  @Test(groups = {"gapi", "TransactionSearchDate"})
  public void requestValidation() {
    Logger.info("TransactionSearchDate request validation");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    TestCaseCollection testCases = new GapiTestCaseCollection(
        new Credentials(gatewayId, username, password)
    );

    String[] invalidNumItems = {
        "23 a", "2 3", "2.3", ".3",
    };

    for (String numItems : invalidNumItems) {
      //TransactionSearchDate,145,4,04/29/2014,04/29/2014,23 a
      //TransactionSearchDate,145,5,04/29/2014,04/29/2014,2 3
      //TransactionSearchDate,145,6,04/29/2014,04/29/2014,2.3
      //TransactionSearchDate,145,7,04/29/2014,04/29/2014,.3
      testCases.add(
          TransactionSearchDate.createValid(
              "Test Case 4-7 - invalid number of items",
              getExpectedResponse(gatewayErrors, "145"))
              .setNumberOfItems(numItems)
      );
    }

    //TransactionSearchDate,169,8,04/29/2014,04/29/2014,51
    testCases.add(
        TransactionSearchDate.createValid(
            "Test Case 8 - number of items too large",
            getExpectedResponse(gatewayErrors, "169"))
            .setNumberOfItems("51")
    );

    String[] invalidDates = {
        "02//2014", "/10/2014", "02/10/", "02/10/14", "02/31/2014", "02a/10/2014", "02/10a/2014",
        "02/10/2014a", "02!/10/2014", "02/10!/2014", "02/10/2014!", "40/15/2014", "4/40/2014",
        "4/150/2014", "4/015/2014", "4/15/20014",
    };

    for (String startDate : invalidDates) {
      //TransactionSearchDate,172,9,02//2014,04/15/2014,2
      //TransactionSearchDate,172,10,/10/2014,04/15/2014,2
      //TransactionSearchDate,172,11,02/10/,04/15/2014,1
      //TransactionSearchDate,172,12,02/10/14,04/15/2014,2
      //TransactionSearchDate,172,13,02/31/2014,04/15/2014,2
      //TransactionSearchDate,172,14,02a/10/2014,04/15/2014,2
      //TransactionSearchDate,172,15,02/10a/2014,04/15/2014,2
      //TransactionSearchDate,172,16,02/10/2014a,04/15/2014,2
      //TransactionSearchDate,172,17,02!/10/2014,04/15/2014,2
      //TransactionSearchDate,172,18,02/10!/2014,04/15/2014,2
      //TransactionSearchDate,172,19,02/10/2014!,04/15/2014,2
      //TransactionSearchDate,172,20,40/15/2014,04/15/2014,2
      //TransactionSearchDate,172,21,4/40/2014,04/04/2014,2
      //TransactionSearchDate,172,22,4/150/2014,4/15/2014,2
      //TransactionSearchDate,172,23,4/015/2014,4/15/2014,2
      //TransactionSearchDate,172,24,4/15/20014,4/15/2014,2
      testCases.add(
          TransactionSearchDate.createValid(
              "Test Case 9-24 - invalid start date",
              getExpectedResponse(gatewayErrors, "172"))
              .setSearchStartDate(startDate)
      );
    }

    for (String endDate : invalidDates) {
      //TransactionSearchDate,175,25,04/15/2014,02//2014,2
      //TransactionSearchDate,175,26,04/15/2014,/10/2014,2
      //TransactionSearchDate,175,27,04/15/2014,02/10/,2
      //TransactionSearchDate,175,28,04/15/2014,02/10/14,2
      //TransactionSearchDate,175,29,04/15/2014,02/31/2014,2
      //TransactionSearchDate,175,30,04/15/2014,02a/10/2014,2
      //TransactionSearchDate,175,31,04/15/2014,02/10a/2014,2
      //TransactionSearchDate,175,32,04/15/2014,02/10/2014a,2
      //TransactionSearchDate,175,33,04/15/2014,02!/10/2014,2
      //TransactionSearchDate,175,34,04/15/2014,02/10!/2014,2
      //TransactionSearchDate,175,35,04/15/2014,02/10/2014!,2
      //TransactionSearchDate,175,36,4/04/2014,40/04/2014,2
      //TransactionSearchDate,175,37,4/04/2014,04/40/2014,2
      //TransactionSearchDate,175,38,4/04/2014,4/150/2014,2
      //TransactionSearchDate,175,39,4/04/2014,4/015/2014,2
      //TransactionSearchDate,175,40,4/04/2014,4/04/20140,2
      testCases.add(
          TransactionSearchDate.createValid(
              "Test Case 25-40 - invalid end date",
              getExpectedResponse(gatewayErrors, "175"))
              .setSearchEndDate(endDate)
      );
    }

    String[] invalidTimes = {
        "0:00:00", "00:0:00", "00:00:0", "000:00:00", "00:000:00", "00:00:000", "00:00:", "00::00",
        ":00:00", "00:00-00", "00-00:00", "00-00-00", "00:00 00", "00 00:00", "00 00 00",
        "99:99:99", "99:59:59", "23:99:59", "23:59:99",
    };

    for (String startTime : invalidTimes) {
      //TransactionSearchDate,228,41,01/01/2013 0:00:00,01/02/2013,2
      //TransactionSearchDate,228,42,01/01/2013 00:0:00,01/02/2013,2
      //TransactionSearchDate,228,43,01/01/2013 00:00:0,01/02/2013,2
      //TransactionSearchDate,228,44,01/01/2013 000:00:00,01/02/2013,2
      //TransactionSearchDate,228,45,01/01/2013 00:000:00,01/02/2013,2
      //TransactionSearchDate,228,46,01/01/2013 00:00:000,01/02/2013,2
      //TransactionSearchDate,228,47,01/01/2013 00:00:,01/02/2013,2
      //TransactionSearchDate,228,48,01/01/2013 00::00,01/02/2013,2
      //TransactionSearchDate,228,49,01/01/2013 :00:00,01/02/2013,2
      //TransactionSearchDate,228,50,01/01/2013 00:00-00,01/02/2013,2
      //TransactionSearchDate,228,51,01/01/2013 00-00:00,01/02/2013,2
      //TransactionSearchDate,228,52,01/01/2013 00-00-00,01/02/2013,2
      //TransactionSearchDate,228,53,01/01/2013 00:00 00,01/02/2013,2
      //TransactionSearchDate,228,54,01/01/2013 00 00:00,01/02/2013,2
      //TransactionSearchDate,228,55,01/01/2013 00 00 00,01/02/2013,2
      //TransactionSearchDate,228,56,01/01/2013 00 00 00,01/02/2013,2
      //TransactionSearchDate,228,57,01/01/2013 99:99:99,01/02/2013,2
      //TransactionSearchDate,228,58,01/01/2013 99:59:59,01/02/2013,2
      //TransactionSearchDate,228,59,01/01/2013 23:99:59,01/02/2013,2
      //TransactionSearchDate,228,60,01/01/2013 23:59:99,01/02/2013,2
      testCases.add(
          TransactionSearchDate.createValid(
              "Test Case 41-60 - invalid start time",
              getExpectedResponse(gatewayErrors, "228"))
              .setSearchStartTime(startTime)
      );
    }

    for (String endTime : invalidTimes) {
      //TransactionSearchDate,229,61,01/02/2013,01/01/2013 0:00:00,2
      //TransactionSearchDate,229,62,01/02/2013,01/01/2013 00:0:00,2
      //TransactionSearchDate,229,63,01/02/2013,01/01/2013 00:00:0,2
      //TransactionSearchDate,229,64,01/02/2013,01/01/2013 000:00:00,2
      //TransactionSearchDate,229,65,01/02/2013,01/01/2013 00:000:00,2
      //TransactionSearchDate,229,66,01/02/2013,01/01/2013 00:00:000,2
      //TransactionSearchDate,229,67,01/02/2013,01/01/2013 00:00:,2
      //TransactionSearchDate,229,68,01/02/2013,01/01/2013 00::00,2
      //TransactionSearchDate,229,69,01/02/2013,01/01/2013 :00:00,2
      //TransactionSearchDate,229,70,01/02/2013,01/01/2013 00:00-00,2
      //TransactionSearchDate,229,71,01/02/2013,01/01/2013 00-00:00,2
      //TransactionSearchDate,229,72,01/02/2013,01/01/2013 00-00-00,2
      //TransactionSearchDate,229,73,01/02/2013,01/01/2013 00:00 00,2
      //TransactionSearchDate,229,74,01/02/2013,01/01/2013 00 00:00,2
      //TransactionSearchDate,229,75,01/02/2013,01/01/2013 00 00 00,2
      //TransactionSearchDate,229,76,01/02/2013,01/01/2013 00 00 00,2
      //TransactionSearchDate,229,77,01/02/2013,01/01/2013 99:99:99,2
      //TransactionSearchDate,229,78,01/02/2013,01/01/2013 99:59:59,2
      //TransactionSearchDate,229,79,01/02/2013,01/01/2013 23:99:59,2
      //TransactionSearchDate,229,80,01/02/2013,01/01/2013 23:59:99,2
      testCases.add(
          TransactionSearchDate.createValid(
              "Test Case 61-80 - invalid end time",
              getExpectedResponse(gatewayErrors, "229"))
              .setSearchEndTime(endTime)
      );
    }

    executeTests(testCases);
  }
}