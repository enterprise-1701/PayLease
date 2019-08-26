package com.paylease.app.qa.functional.tests.backend;

import com.paylease.app.qa.api.tests.aapi.testcase.GetTenants;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.aapi.RdcRequest;
import com.paylease.app.qa.framework.api.aapi.response.GetTenantsResponse;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AapiGetTenantsTest extends ScriptBase {

  private static final String REGION = "backend";
  private static final String FEATURE = "aapiGetTenants";

  @Test(dataProvider = "provideStatus", retryAnalyzer = Retry.class)
  public void statusImported(String testCaseId, String status) throws Exception {
    Logger.info("In the GetTenants API response we see translated status '" + status
        + "' attached to the resident's name.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String locationId = testSetupPage.getString("locationId");
    final String residentId = testSetupPage.getString("residentId");

    GetTenantsResponse tenantsResponse = getTenantsResponse(gatewayId, username, locationId);

    Assert.assertEquals(tenantsResponse.getTenantId(1, 1), residentId,
        "Resident should be included in response");

    Assert.assertTrue(tenantsResponse.getTenantFirstName(1, 1).contains(" - " + status),
        "Status is appended to first name");
  }

  @Test
  public void statusTruncated() throws Exception {
    Logger.info(
        "In the GetTenants API response we see truncated status attached to the resident's name.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4151");
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String locationId = testSetupPage.getString("locationId");
    final String residentId = testSetupPage.getString("residentId");
    final String originalStatus = testSetupPage.getString("originalStatus");

    String expectedStatus = originalStatus.substring(0, 10);

    GetTenantsResponse tenantsResponse = getTenantsResponse(gatewayId, username, locationId);

    Assert.assertEquals(tenantsResponse.getTenantId(1, 1), residentId,
        "Resident should be included in response");

    Assert.assertTrue(tenantsResponse.getTenantFirstName(1, 1).contains(" - " + expectedStatus),
        "Status is appended to first name");
  }

  @Test(dataProvider = "provideStatusEmpty", retryAnalyzer = Retry.class)
  public void statusIsEmpty(String testCaseId) throws Exception {
    Logger.info(
        "In the GetTenants API response we see nothing attached to the resident's first name.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String locationId = testSetupPage.getString("locationId");
    final String residentId = testSetupPage.getString("residentId");
    final String residentFirstName = testSetupPage.getString("residentFirstName");

    GetTenantsResponse tenantsResponse = getTenantsResponse(gatewayId, username, locationId);

    Assert.assertEquals(tenantsResponse.getTenantId(1, 1), residentId,
        "Resident should be included in response");

    Assert.assertEquals(tenantsResponse.getTenantFirstName(1, 1), residentFirstName,
        "Status is not appended to first name");
  }

  @Test(dataProvider = "provideBalance", retryAnalyzer = Retry.class)
  public void balanceImportedFromCorrectField(String testCaseId, boolean useRentAmount) throws Exception {
    Logger.info(testCaseId
        + " - In the GetTenants API response we see balance populated from correct fields");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String locationId = testSetupPage.getString("locationId");
    final String residentId = testSetupPage.getString("residentId");
    final String rentAmount = testSetupPage.getString("rentAmount");
    final String lateAmount = testSetupPage.getString("lateAmount");
    final String expectedBalance = useRentAmount ? rentAmount : lateAmount;

    GetTenantsResponse tenantsResponse = getTenantsResponse(gatewayId, username, locationId);

    Assert.assertEquals(tenantsResponse.getTenantId(1, 1), residentId,
        "Resident should be included in response");

    Assert.assertTrue(tenantsResponse.getTenantFirstName(1, 1).contains(" - " + expectedBalance),
        "Balance amount is appended to first name");
  }

  private GetTenantsResponse getTenantsResponse(
      String gatewayId, String username, String locationId
  ) throws Exception {
    RdcRequest request = new RdcRequest(
        new Credentials(gatewayId, username, null), "Test", "wfs"
    );

    GetTenants testCase = new GetTenants("", null, locationId);
    testCase.addTransaction(request);

    Response response = request.sendRequest();

    return new GetTenantsResponse(response.getResponse());
  }

  @DataProvider(name = "provideStatus", parallel = true)
  private Object[][] provideStatus() {
    return new Object[][]{
        //testCaseId, status
        {"tc4121", "App-Pen"},
        {"tc4122", "App-PenT"},
        {"tc4123", "App-Apprv"},
        {"tc4124", "App-Den"},
        {"tc4125", "App-Cncl"},
        {"tc4126", "Res-Cur"},
        {"tc4134", "Res-PenR"},
        {"tc4135", "Res-Evic"},
        {"tc4136", "Res-Form"},
        {"tc4137", "Res-Evic"},
        {"tc4131", "Pros"},
        {"tc4132", "Non Res"},
        {"tc4133", "WOIT"},
    };
  }

  @DataProvider(parallel = true)
  private Object[][] provideStatusEmpty() {
    return new Object[][]{
        //testCaseId
        {"tc4211"},
    };
  }

  @DataProvider(parallel = true)
  private Object[][] provideBalance() {
    return new Object[][]{
        //testCaseId, Use rent amount
        {"tc4165", true},
        {"tc4166", false},
    };
  }
}
