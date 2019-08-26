package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.RdcTransactionCallback;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;

public class RdcTransactionCallbackTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "rdcTransactionCallback";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "RdcTransactionCallback"})
  public void rdcTransactionCallbackTest() {
    Logger.info("RdcTransactionCallback Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String gatewayId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String bankAccountId = testSetupPage.getString("bankAccountId");
    final String propId = testSetupPage.getString("propertyId");
    final String propId2 = testSetupPage.getString("propertyId2");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    //RdcTransactionCallback,1,1,317343184,be297ejCEn,API_USER_0000267267,IeteoCiojao2Jahshahr,Test,wfs,1,17343184,477,P2127252,113.36,1589635972,11000028,RAND,974588,19447099,2522425,Harry,Dresden,113.36,267376382670,2670,51000017,RAND,P2127252,1742.36,1589635972,11000028,RAND,1028009,19447098,2522425,Happy,Tester,1742.36,1.8085E+11,4844,51000017,RAND,17343184,477,P2127252,1313.13,1589635972,11000028,RAND,1028009,20043603,2522425,Sad,Tester,1313.13,1.6299E+11,9483,51000017,RAND
    //RdcTransactionCallback,1,3,317343184,be297ejCEn,API_USER_0000267267,IeteoCiojao2Jahshahr,Test,wfs,1,17343184,477,P2127252,113.36,1589635972,11000028,RAND,974588,19447099,2522425,Harry,Dresden,113.36,267376382670,2670,51000017,RAND,P2127252,1742.36,1589635972,11000028,RAND,1028009,19447098,2522425,Happy,Tester,1742.36,180850114844,4844,51000017,RAND,17343184,477,P2127252,1313.13,1589635972,11000028,RAND,1028009,20043603,2522425,Sad,Tester,1313.13,162989529483,9483,51000017,RAND
    RdcTransactionCallback testCase = new RdcTransactionCallback(
        "Test case 1/3 - Single Batch, Single Check",
        getExpectedResponse(gatewayErrors, "1"));

    String batchSequence = testCase
        .createBatch(pmId, "477", "P" + bankAccountId, "113.36", "1589635972", "11000028");
    testCase
        .addCheckToBatch(batchSequence, propId, "19447099", "2522425", "Harry", "Dresden", "113.36",
            "267376382670", "2670", "51000017");
    testCases.add(
        new RdcTestCaseCollection(
            new Credentials(gatewayId, username, password),
            "Test",
            "wfs"
        ).add(testCase));

    //RdcTransactionCallback,1,2,317343184,be297ejCEn,API_USER_0000267267,IeteoCiojao2Jahshahr,Test,wfs,3,
    //    17343184,477,
    //      P2127252,113.36,1589635972,11000028,RAND,
    //        974588,19447099,2522425,Harry,Dresden,113.36,267376382670,2670,51000017,RAND,
    //      P2127252,1742.36,1589635972,11000028,RAND,
    //        1028009,19447098,2522425,Happy,Tester,1742.36,1.8085E+11,4844,51000017,RAND,
    //    17343184,477,
    //      P2127252,1313.13,1589635972,11000028,RAND,
    //        1028009,20043603,2522425,Sad,Tester,1313.13,1.6299E+11,9483,51000017,RAND
    testCase = new RdcTransactionCallback(
        "Test case 2 - Multiple Batches, Multiple Checks",
        getExpectedResponse(gatewayErrors, "1"));

    String batchSequence1 = testCase
        .createBatch(pmId, "477", "P" + bankAccountId, "113.36", "1589635972", "11000028");
    testCase.addCheckToBatch(batchSequence1, propId, "19447099", "2522425", "Harry", "Dresden",
        "113.36", "267376382670", "2670", "51000017");
    testCase.addCheckToBatch(batchSequence1, propId2, "19447098", "2522425", "Harry", "Dresden",
        "1742.36", "1.8085E+11", "4844", "51000017");
    String batchSequence2 = testCase
        .createBatch(pmId, "477", "P" + bankAccountId, "1313.13", "1589635972", "11000028");
    testCase
        .addCheckToBatch(batchSequence2, propId2, "20043603", "2522425", "Sad", "Tester", "1313.13",
            "6299E+11", "9483", "51000017");

    testCases.add(
        new RdcTestCaseCollection(
            new Credentials(gatewayId, username, password),
            "Test",
            "wfs"
        ).add(testCase));

    executeTests(testCases);
  }
}
