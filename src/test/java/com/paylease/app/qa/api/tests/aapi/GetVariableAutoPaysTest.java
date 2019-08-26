package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetVariableAutoPays;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class GetVariableAutoPaysTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetVariableAutoPays";

  private static final String[] endpointValues = {
      "associa", "cinc", "collier", "fiserv", "onsite", "tops"
  };

  private static final String[] statuses = {
      "ACTIVE", "CANCELLED", "EXPIRED", ""
  };


  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc872() {
    Logger.info("Get status 1 for GetVariableAutoPays with all data");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetVariableAutoPays(
                  "Happy path.",
                  getExpectedResponse(gatewayErrors, "1"),
                  residentReferenceId,
                  status
              )
          ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc873() {
    Logger.info("Get status 5 for GetVariableAutoPays with no existing variable autopays for any residents");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc873");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "No existing auto-pays for PM",
                    getExpectedResponse(gatewayErrors, "5"),
                    "",
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc874() {
    Logger.info("Get status 1 for GetVariableAutoPays no reference ID");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "No reference ID",
                    getExpectedResponse(gatewayErrors, "1"),
                    "",
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc875() {
    Logger.info("Get status 6 for GetVariableAutoPays with bad reference id");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Bad reference id.",
                    getExpectedResponse(gatewayErrors, "6"),
                    "badrefid",
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc876() {
    Logger.info("Get status 8 for GetVariableAutoPays with empty api key");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, "", pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Empty API Key.",
                    getExpectedResponse(gatewayErrors, "8"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc877() {
    Logger.info("Get status 9 for GetVariableAutoPays with bad api key");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, "badAPIkey", pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Bad API Key.",
                    getExpectedResponse(gatewayErrors, "9"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc878() {
    Logger.info("Get status 9 for GetVariableAutoPays with a space for the api key");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, " ", pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "A space for the API Key.",
                    getExpectedResponse(gatewayErrors, "9"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc879() {
    Logger.info("Get status 20 for GetVariableAutoPays with an empty mode");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Empty Mode",
                    getExpectedResponse(gatewayErrors, "20"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc880() {
    Logger.info("Get status 21 for GetVariableAutoPays with mode == Production");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Production",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Production Mode",
                    getExpectedResponse(gatewayErrors, "21"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc881() {
    Logger.info("Get status 38 for GetVariableAutoPays with blank space for password");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, " ", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Blank Space For Password",
                    getExpectedResponse(gatewayErrors, "38"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc882() {
    Logger.info("Get status 38 for GetVariableAutoPays with Bad password and bad user id.");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials("5125", username, "badpass", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Bad password and user ID",
                    getExpectedResponse(gatewayErrors, "38"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc883() {
    Logger.info("Get status 38 for GetVariableAutoPays with Bad password.");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, "badpass", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Bad Password",
                    getExpectedResponse(gatewayErrors, "38"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc884() {
    Logger.info("Get status 45 for GetVariableAutoPays with an empty password.");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, "", null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Empty Password",
                    getExpectedResponse(gatewayErrors, "45"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc885() {
    Logger.info("Get status 39 for GetVariableAutoPays with a bad status.");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc872");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "Bad Status",
                    getExpectedResponse(gatewayErrors, "39"),
                    residentReferenceId,
                    "badstatus"
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc886() {
    Logger.info("Get status 5 for GetVariableAutoPays searching for non-existing status.");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc886");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "PM does not have residents with Expired Status",
                    getExpectedResponse(gatewayErrors, "5"),
                    residentReferenceId,
                    "EXPIRED"
                )
            ));
      }
    }

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "GetVariableAutoPays"})
  public void tc887() {
    Logger.info("Get status 5 for GetVariableAutoPays with no existing variable autopays for any residents");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc873");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentReferenceId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");


    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String status : statuses) {
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetVariableAutoPays(
                    "No existing auto-pays for PM",
                    getExpectedResponse(gatewayErrors, "5"),
                    residentReferenceId,
                    status
                )
            ));
      }
    }

    executeTests(testCases);
  }
}
