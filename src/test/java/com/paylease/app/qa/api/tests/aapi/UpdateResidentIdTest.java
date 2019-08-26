package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.UpdateResidentId;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class UpdateResidentIdTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "updateResidentId";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "UpdateResidentID"})
  public void updateResidentIdWithPermission() {
    Logger.info("UpdateResidentID tests - PM HAS update permission");

    TestSetupPage testSetupPage = getNewTestSetup();
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //UpdateResidentID,1,1,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,cpay_rand_id_0,flubber4,TRUE
    //UpdateResidentID,1,3,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,cpay_rand_id_1,dubber4,TRUE
    //UpdateResidentID,1,10,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,flubber4,unused000,TRUE
    //UpdateResidentID,1,11,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,dubber4,unused555,TRUE
    String userId = testSetupPage.getString("gatewayId");
    String username = testSetupPage.getString("username");
    String password = testSetupPage.getString("password");
    String pmId = testSetupPage.getString("pmId");
    String residentId = testSetupPage.getString("residentReferenceId");

    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new UpdateResidentId(
                "Test case 1/3/10/11 - basic update",
                getExpectedResponse(gatewayErrors, "1"))
                .setCurrentResidentId(residentId)
                .setNewResidentId("flubber4")
                .setUpdateSuccess("TRUE")
            ));

    //UpdateResidentID,1,2,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,cpay_rand_id_0,flubber4,FALSE
    //UpdateResidentID,1,4,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,cpay_rand_id_1,dubber4,FALSE
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new UpdateResidentId(
                "Test case 2/4 - update using unknown current id",
                getExpectedResponse(gatewayErrors, "1"))
                .setCurrentResidentId("unknown")
                .setNewResidentId("flubber4")
                .setUpdateSuccess("FALSE")
        ));

    //UpdateResidentID,1,5,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,dubber4,abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz,FALSE
    testSetupPage = getNewTestSetup();
    userId = testSetupPage.getString("gatewayId");
    username = testSetupPage.getString("username");
    password = testSetupPage.getString("password");
    pmId = testSetupPage.getString("pmId");
    residentId = testSetupPage.getString("residentReferenceId");

    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new UpdateResidentId(
                "Test case 5 - update to invalid resident ID",
                getExpectedResponse(gatewayErrors, "1"))
                .setCurrentResidentId(residentId)
                .setNewResidentId("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")
                .setUpdateSuccess("FALSE")
        ));

    //UpdateResidentID,1,6,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,dubber4,,FALSE
    testSetupPage = getNewTestSetup();
    userId = testSetupPage.getString("gatewayId");
    username = testSetupPage.getString("username");
    password = testSetupPage.getString("password");
    pmId = testSetupPage.getString("pmId");
    residentId = testSetupPage.getString("residentReferenceId");
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new UpdateResidentId(
                "Test case 6 - update to empty resident ID",
                getExpectedResponse(gatewayErrors, "1"))
                .setCurrentResidentId(residentId)
                .setNewResidentId("")
                .setUpdateSuccess("FALSE")
        ));

    //UpdateResidentID,1,7,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,,dubber4,FALSE
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new UpdateResidentId(
                "Test case 7 - update from empty resident ID",
                getExpectedResponse(gatewayErrors, "1"))
                .setCurrentResidentId("")
                .setNewResidentId("dubber4")
                .setUpdateSuccess("FALSE")
        ));

    //UpdateResidentID,57,8,324751344,Y3vgNMyPJE,IeteoCiojao2Jahshahr,Test,oakwood,FALSE
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "oakwood"
        ).add(
            new UpdateResidentId(
                "Test case 8 - update with no resident information",
                getExpectedResponse(gatewayErrors, "57"))
                .setUpdateSuccess("FALSE")
        ));

    executeTests(testCases);
  }

  @Test(groups = {"aapi", "UpdateResidentID"})
  public void updateResidentIdNoPermission() {
    Logger.info("UpdateResidentID tests - PM does NOT have update permission");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String residentId = testSetupPage.getString("residentReferenceId");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //UpdateResidentID,37,9,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,apiQAtestRes2,FALSE
    testCases.add(
        new AapiTestCaseCollection(
            new Credentials(userId, username, password, null, pmId),
            "Test",
            "cinc"
        ).add(
            new UpdateResidentId(
                "Test case 9 - update without permission",
                getExpectedResponse(gatewayErrors, "37"))
                .setCurrentResidentId(residentId)
                .setUpdateSuccess("FALSE")
        ));

    executeTests(testCases);
  }

  private TestSetupPage getNewTestSetup() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    return testSetupPage;
  }
}
