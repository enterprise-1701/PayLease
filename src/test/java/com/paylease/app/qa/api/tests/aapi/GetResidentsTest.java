package com.paylease.app.qa.api.tests.aapi;

import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.api.tests.aapi.testcase.GetResidents;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Credentials;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;


public class GetResidentsTest extends BaseTest {

  private static final String REGION = "aapi";
  private static final String FEATURE = "GetResidents";

  private static final String STATUS_APPROVED = "Approved";
  private static final String STATUS_INACTIVE = "Inactive";
  private static final String STATUS_SUSPENDED = "Suspended";
  private static final String STATUS_PENDING = "Pending";
  private static final String STATUS_ALL = "All";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"aapi", "GetResidents"})
  public void getResidents() {
    Logger.info("Get Residents Test");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String userId = testSetupPage.getString("gatewayId");
    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String propertyReferenceId = testSetupPage.getString("propNumber");
    final String residentIdApproved = testSetupPage.getString("residentRefId_" + STATUS_APPROVED);
    final String secondaryResidentIdApproved = testSetupPage
        .getString("residentSecondaryRefId_Approved");
    final String residentIdInactive = testSetupPage.getString("residentRefId_" + STATUS_INACTIVE);
    final String secondaryResidentIdInactive = testSetupPage
        .getString("residentSecondaryRefId_Inactive");
    final String residentIdSuspended = testSetupPage.getString("residentRefId_" + STATUS_SUSPENDED);
    final String secondaryResidentIdSuspended = testSetupPage
        .getString("residentSecondaryRefId_Suspended");
    final String residentIdPending = testSetupPage.getString("residentRefId_" + STATUS_PENDING);
    final String secondaryResidentIdPending = testSetupPage
        .getString("residentSecondaryRefId_Pending");
    final List<HashMap<String, Object>> gatewayErrors = testSetupPage.getTable("gatewayErrors");

    HashMap<String, String> residentIds = new HashMap<>();
    residentIds.put(STATUS_APPROVED, residentIdApproved);
    residentIds.put(STATUS_INACTIVE, residentIdInactive);
    residentIds.put(STATUS_SUSPENDED, residentIdSuspended);
    residentIds.put(STATUS_PENDING, residentIdPending);
    residentIds.put(STATUS_ALL, residentIdApproved);
    residentIds.put("", residentIdApproved);

    HashMap<String, String> secondaryResIds = new HashMap<>();
    secondaryResIds.put(STATUS_APPROVED, secondaryResidentIdApproved);
    secondaryResIds.put(STATUS_INACTIVE, secondaryResidentIdInactive);
    secondaryResIds.put(STATUS_SUSPENDED, secondaryResidentIdSuspended);
    secondaryResIds.put(STATUS_PENDING, secondaryResidentIdPending);
    secondaryResIds.put(STATUS_ALL, secondaryResidentIdApproved);
    secondaryResIds.put("", secondaryResidentIdApproved);

    final String[] endpointValues = {
        "associa", "cinc", "collier", "fiserv", "onsite", "tops"
    };

    final String[] residentStatuses = {
        "Approved", "Inactive", "Suspended", "Pending", "All", "",
    };

    ArrayList<TestCaseCollection> testCases = new ArrayList<>();

    for (String endpointValue : endpointValues) {
      for (String residentStatus : residentStatuses) {

        //GetResidents,1,1,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,,,
        //GetResidents,1,2,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,,,
        //GetResidents,1,3,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,,,
        //GetResidents,1,4,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,,,
        //GetResidents,1,5,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,,,17343184
        //GetResidents,1,6,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,,,17343184

        //GetResidents,1,7,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Approved,,,
        //GetResidents,1,8,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Approved,,,
        //GetResidents,1,9,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Approved,,,
        //GetResidents,1,10,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Approved,,,
        //GetResidents,1,11,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Approved,,,17343184
        //GetResidents,1,12,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Approved,,,17343184

        //GetResidents,1,13,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Inactive,,,
        //GetResidents,1,14,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Inactive,,,
        //GetResidents,1,15,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Inactive,,,
        //GetResidents,1,16,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Inactive,,,
        //GetResidents,1,17,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Inactive,,,17343184
        //GetResidents,1,18,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Inactive,,,17343184

        //GetResidents,1,19,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Suspended,,,
        //GetResidents,1,20,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Suspended,,,
        //GetResidents,1,21,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Suspended,,,
        //GetResidents,1,22,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Suspended,,,
        //GetResidents,1,23,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Suspended,,,17343184
        //GetResidents,1,24,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Suspended,,,17343184

        //GetResidents,1,25,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Pending,,,
        //GetResidents,1,26,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Pending,,,
        //GetResidents,1,27,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Pending,,,
        //GetResidents,1,28,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Pending,,,
        //GetResidents,1,29,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Pending,,,17343184
        //GetResidents,1,30,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Pending,,,17343184

        //GetResidents,1,31,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,All,,,
        //GetResidents,1,32,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,All,,,
        //GetResidents,1,33,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,All,,,
        //GetResidents,1,34,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,All,,,
        //GetResidents,1,35,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,All,,,17343184
        //GetResidents,1,36,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,All,,,17343184

        //GetResidents,1,37,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,,,,
        //GetResidents,1,38,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,,,,
        //GetResidents,1,39,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,,,,
        //GetResidents,1,40,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,,,,

        //GetResidents,1,43,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Approved,,,
        //GetResidents,1,44,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Approved,,,
        //GetResidents,1,45,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Approved,,,
        //GetResidents,1,46,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Approved,,,

        //GetResidents,1,49,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Inactive,,,
        //GetResidents,1,50,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Inactive,,,
        //GetResidents,1,51,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Inactive,,,
        //GetResidents,1,52,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Inactive,,,

        //GetResidents,1,55,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Suspended,,,
        //GetResidents,1,56,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Suspended,,,
        //GetResidents,1,57,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Suspended,,,
        //GetResidents,1,58,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Suspended,,,

        //GetResidents,1,61,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Pending,,,
        //GetResidents,1,62,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Pending,,,
        //GetResidents,1,63,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Pending,,,
        //GetResidents,1,64,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Pending,,,

        //GetResidents,1,67,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,All,,,
        //GetResidents,1,68,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,All,,,
        //GetResidents,1,69,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,All,,,
        //GetResidents,1,70,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,All,,,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetResidents(
                    "Test case 1 to 40/43-46/49-52/55-58/61-64/67-70 - Empty residentId and "
                        + "secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "1"),
                    propertyReferenceId,
                    "",
                    "",
                    residentStatus)
            ));

        //GetResidents,1,73,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,GAPITesterCC,,
        //GetResidents,1,74,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,GAPITesterCC,,
        //GetResidents,1,75,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,GAPITesterCC,,
        //GetResidents,1,76,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,GAPITesterCC,,
        //GetResidents,1,77,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,GAPITesterCC,,17343184
        //GetResidents,1,78,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,GAPITesterCC,,17343184

        //GetResidents,1,79,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Approved,GAPITesterCC,,
        //GetResidents,1,80,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Approved,GAPITesterCC,,
        //GetResidents,1,81,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Approved,GAPITesterCC,,
        //GetResidents,1,82,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Approved,GAPITesterCC,,
        //GetResidents,1,83,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Approved,GAPITesterCC,,17343184
        //GetResidents,1,84,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Approved,GAPITesterCC,,17343184

        //GetResidents,1,85,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Inactive,GAPITesterCC,,
        //GetResidents,1,86,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Inactive,GAPITesterCC,,
        //GetResidents,1,87,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Inactive,GAPITesterCC,,
        //GetResidents,1,88,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Inactive,GAPITesterCC,,
        //GetResidents,1,89,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Inactive,GAPITesterCC,,17343184
        //GetResidents,1,90,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Inactive,GAPITesterCC,,17343184

        //GetResidents,1,91,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Suspended,GAPITesterCC,,
        //GetResidents,1,92,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Suspended,GAPITesterCC,,
        //GetResidents,1,93,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Suspended,GAPITesterCC,,
        //GetResidents,1,94,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Suspended,GAPITesterCC,,
        //GetResidents,1,95,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Suspended,GAPITesterCC,,17343184
        //GetResidents,1,96,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Suspended,GAPITesterCC,,17343184

        //GetResidents,1,97,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Pending,GAPITesterCC,,
        //GetResidents,1,98,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Pending,GAPITesterCC,,
        //GetResidents,1,99,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Pending,GAPITesterCC,,
        //GetResidents,1,100,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Pending,GAPITesterCC,,
        //GetResidents,1,101,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Pending,GAPITesterCC,,17343184
        //GetResidents,1,102,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Pending,GAPITesterCC,,17343184

        //GetResidents,1,103,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,All,GAPITesterCC,,
        //GetResidents,1,104,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,All,GAPITesterCC,,
        //GetResidents,1,105,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,All,GAPITesterCC,,
        //GetResidents,1,106,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,All,GAPITesterCC,,
        //GetResidents,1,107,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,All,GAPITesterCC,,17343184
        //GetResidents,1,108,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,All,GAPITesterCC,,17343184

        //GetResidents,1,109,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,,GAPITesterCC,,
        //GetResidents,1,110,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,,GAPITesterCC,,
        //GetResidents,1,111,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,,GAPITesterCC,,
        //GetResidents,1,112,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,,GAPITesterCC,,

        //GetResidents,1,115,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Approved,GAPITesterCC,,
        //GetResidents,1,116,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Approved,GAPITesterCC,,
        //GetResidents,1,117,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Approved,GAPITesterCC,,
        //GetResidents,1,118,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Approved,GAPITesterCC,,

        //GetResidents,1,121,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Inactive,GAPITesterCC,,
        //GetResidents,1,122,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Inactive,GAPITesterCC,,
        //GetResidents,1,123,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Inactive,GAPITesterCC,,
        //GetResidents,1,124,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Inactive,GAPITesterCC,,

        //GetResidents,1,127,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Suspended,GAPITesterCC,,
        //GetResidents,1,128,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Suspended,GAPITesterCC,,
        //GetResidents,1,129,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Suspended,GAPITesterCC,,
        //GetResidents,1,130,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Suspended,GAPITesterCC,,

        //GetResidents,1,133,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Pending,GAPITesterCC,,
        //GetResidents,1,134,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Pending,GAPITesterCC,,
        //GetResidents,1,135,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Pending,GAPITesterCC,,
        //GetResidents,1,136,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Pending,GAPITesterCC,,

        //GetResidents,1,139,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,All,GAPITesterCC,,
        //GetResidents,1,140,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,All,GAPITesterCC,,
        //GetResidents,1,141,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,All,GAPITesterCC,,
        //GetResidents,1,142,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,All,GAPITesterCC,,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetResidents(
                    "Test case 73 to 112/115-118/-121-124/133-136/139-142 - residentId and "
                        + "empty secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "1"),
                    propertyReferenceId,
                    residentIds.get(residentStatus),
                    "",
                    residentStatus)
            ));

        //GetResidents,1,145,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,,SecondRefID2,
        //GetResidents,1,146,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,,SecondRefID2,
        //GetResidents,1,147,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,,SecondRefID2,
        //GetResidents,1,148,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,,SecondRefID2,
        //GetResidents,1,149,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,,SecondRefID2,17343184
        //GetResidents,1,150,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,,SecondRefID2,17343184

        //GetResidents,1,151,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Approved,,SecondRefID2,
        //GetResidents,1,152,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Approved,,SecondRefID2,
        //GetResidents,1,153,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Approved,,SecondRefID2,
        //GetResidents,1,154,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Approved,,SecondRefID2,
        //GetResidents,1,155,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Approved,,SecondRefID2,17343184
        //GetResidents,1,156,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Approved,,SecondRefID2,17343184

        //GetResidents,1,157,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Inactive,,SecondRefID2,
        //GetResidents,1,158,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Inactive,,SecondRefID2,
        //GetResidents,1,159,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Inactive,,SecondRefID2,
        //GetResidents,1,160,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Inactive,,SecondRefID2,
        //GetResidents,1,161,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Inactive,,SecondRefID2,17343184
        //GetResidents,1,162,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Inactive,,SecondRefID2,17343184

        //GetResidents,1,163,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Suspended,,SecondRefID2,
        //GetResidents,1,164,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Suspended,,SecondRefID2,
        //GetResidents,1,165,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Suspended,,SecondRefID2,
        //GetResidents,1,166,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Suspended,,SecondRefID2,
        //GetResidents,1,167,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Suspended,,SecondRefID2,17343184
        //GetResidents,1,168,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Suspended,,SecondRefID2,17343184

        //GetResidents,1,169,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Pending,,SecondRefID2,
        //GetResidents,1,170,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Pending,,SecondRefID2,
        //GetResidents,1,171,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Pending,,SecondRefID2,
        //GetResidents,1,172,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Pending,,SecondRefID2,
        //GetResidents,1,173,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Pending,,SecondRefID2,17343184
        //GetResidents,1,174,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Pending,,SecondRefID2,17343184

        //GetResidents,1,175,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,All,,SecondRefID2,
        //GetResidents,1,176,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,All,,SecondRefID2,
        //GetResidents,1,177,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,All,,SecondRefID2,
        //GetResidents,1,178,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,All,,SecondRefID2,
        //GetResidents,1,179,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,All,,SecondRefID2,17343184
        //GetResidents,1,180,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,All,,SecondRefID2,17343184

        //GetResidents,1,181,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,,,SecondRefID2,
        //GetResidents,1,182,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,,,SecondRefID2,
        //GetResidents,1,183,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,,,SecondRefID2,
        //GetResidents,1,184,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,,,SecondRefID2,

        //GetResidents,1,187,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Approved,,SecondRefID2,
        //GetResidents,1,188,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Approved,,SecondRefID2,
        //GetResidents,1,189,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Approved,,SecondRefID2,
        //GetResidents,1,190,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Approved,,SecondRefID2,

        //GetResidents,1,193,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Inactive,,SecondRefID2,
        //GetResidents,1,194,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Inactive,,SecondRefID2,
        //GetResidents,1,195,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Inactive,,SecondRefID2,
        //GetResidents,1,196,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Inactive,,SecondRefID2,

        //GetResidents,1,199,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Suspended,,SecondRefID2,
        //GetResidents,1,200,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Suspended,,SecondRefID2,
        //GetResidents,1,201,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Suspended,,SecondRefID2,
        //GetResidents,1,202,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Suspended,,SecondRefID2,

        //GetResidents,1,205,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Pending,,SecondRefID2,
        //GetResidents,1,206,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Pending,,SecondRefID2,
        //GetResidents,1,207,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Pending,,SecondRefID2,
        //GetResidents,1,208,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Pending,,SecondRefID2,

        //GetResidents,1,211,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,All,,SecondRefID2,
        //GetResidents,1,212,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,All,,SecondRefID2,
        //GetResidents,1,213,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,All,,SecondRefID2,
        //GetResidents,1,214,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,All,,SecondRefID2,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetResidents(
                    "Test case 145 to 184/187-190/193-196/199-202/205-208/211-214 - empty "
                        + "residentId and secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "1"),
                    propertyReferenceId,
                    "",
                    secondaryResIds.get(residentStatus),
                    residentStatus)
            ));

        //GetResidents,1,217,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,218,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,219,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,220,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,221,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,GAPITesterCC,SecondRefID2,17343184
        //GetResidents,1,222,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,GAPITesterCC,SecondRefID2,17343184

        //GetResidents,1,223,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,224,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,225,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,226,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,227,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Approved,GAPITesterCC,SecondRefID2,17343184
        //GetResidents,1,228,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Approved,GAPITesterCC,SecondRefID2,17343184

        //GetResidents,1,229,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,230,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,231,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,232,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,233,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Inactive,GAPITesterCC,SecondRefID2,17343184
        //GetResidents,1,234,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Inactive,GAPITesterCC,SecondRefID2,17343184

        //GetResidents,1,235,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,236,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,237,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,238,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,239,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Suspended,GAPITesterCC,SecondRefID2,17343184
        //GetResidents,1,240,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Suspended,GAPITesterCC,SecondRefID2,17343184

        //GetResidents,1,241,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,242,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,243,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,244,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,245,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,Pending,GAPITesterCC,SecondRefID2,17343184
        //GetResidents,1,246,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,Pending,GAPITesterCC,SecondRefID2,17343184

        //GetResidents,1,247,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,248,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,249,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,250,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,251,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,All,GAPITesterCC,SecondRefID2,17343184
        //GetResidents,1,252,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,All,GAPITesterCC,SecondRefID2,17343184

        //GetResidents,1,253,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,254,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,255,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,,GAPITesterCC,SecondRefID2,
        //GetResidents,1,256,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,,GAPITesterCC,SecondRefID2,

        //GetResidents,1,259,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,260,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,261,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,1,262,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Approved,GAPITesterCC,SecondRefID2,

        //GetResidents,1,265,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,266,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,267,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,1,268,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Inactive,GAPITesterCC,SecondRefID2,

        //GetResidents,1,271,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,272,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,273,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,1,274,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Suspended,GAPITesterCC,SecondRefID2,

        //GetResidents,1,277,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,278,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,279,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,1,280,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,Pending,GAPITesterCC,SecondRefID2,

        //GetResidents,1,283,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,284,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,285,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,,All,GAPITesterCC,SecondRefID2,
        //GetResidents,1,286,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,,All,GAPITesterCC,SecondRefID2,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, pmId),
                "Test",
                endpointValue
            ).add(
                new GetResidents(
                    "Test case 217 to 256/259-262/265-268/271-274/277-280/283-286 - residentId "
                        + "and secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "1"),
                    propertyReferenceId,
                    residentIds.get(residentStatus),
                    secondaryResIds.get(residentStatus),
                    residentStatus)
            ));
      }

      //GetResidents,6,289,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,test123,17343184,,,,
      //GetResidents,6,290,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,test123,17343184,,,,
      //GetResidents,6,291,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,test123,17343184,,,,
      //GetResidents,6,292,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,test123,17343184,,,,
      //GetResidents,6,293,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,test123,17343184,,,,17343184
      //GetResidents,6,294,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,test123,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 289 to 294 - Invalid property id",
                  getExpectedResponse(gatewayErrors, "6"),
                  "test123",
                  "",
                  "",
                  "")
          ));

      //GetResidents,8,295,317343184,be297ejCEn,,Test,associa,123QAgapi,17343184,,,,
      //GetResidents,8,296,317343184,be297ejCEn,,Test,cinc,123QAgapi,17343184,,,,
      //GetResidents,8,297,317343184,be297ejCEn,,Test,collier,123QAgapi,17343184,,,,
      //GetResidents,8,298,317343184,be297ejCEn,,Test,fiserv,123QAgapi,17343184,,,,
      //GetResidents,8,299,317343184,be297ejCEn,,Test,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,8,300,317343184,be297ejCEn,,Test,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "", pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 295 to 300 - Empty api key",
                  getExpectedResponse(gatewayErrors, "8"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,9,301,317343184,be297ejCEn,badapikey,Test,associa,123QAgapi,17343184,,,,
      //GetResidents,9,302,317343184,be297ejCEn,badapikey,Test,cinc,123QAgapi,17343184,,,,
      //GetResidents,9,303,317343184,be297ejCEn,badapikey,Test,collier,123QAgapi,17343184,,,,
      //GetResidents,9,304,317343184,be297ejCEn,badapikey,Test,fiserv,123QAgapi,17343184,,,,
      //GetResidents,9,305,317343184,be297ejCEn,badapikey,Test,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,9,306,317343184,be297ejCEn,badapikey,Test,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, "badapikey", pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 301 to 306 - bad api key",
                  getExpectedResponse(gatewayErrors, "9"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,9,307,317343184,be297ejCEn, ,Test,associa,123QAgapi,17343184,,,,
      //GetResidents,9,308,317343184,be297ejCEn, ,Test,cinc,123QAgapi,17343184,,,,
      //GetResidents,9,309,317343184,be297ejCEn, ,Test,collier,123QAgapi,17343184,,,,
      //GetResidents,9,310,317343184,be297ejCEn, ,Test,fiserv,123QAgapi,17343184,,,,
      //GetResidents,9,311,317343184,be297ejCEn, ,Test,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,9,312,317343184,be297ejCEn, ,Test,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, " ", pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 307 to 312 - blank space as api key",
                  getExpectedResponse(gatewayErrors, "9"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,20,313,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,,associa,123QAgapi,17343184,,,,
      //GetResidents,20,314,317343184,be297ejCEn,hovee3uv0ooShoi1Such,,cinc,123QAgapi,17343184,,,,
      //GetResidents,20,315,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,,collier,123QAgapi,17343184,,,,
      //GetResidents,20,316,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,,fiserv,123QAgapi,17343184,,,,
      //GetResidents,20,317,317343184,be297ejCEn,qua1aiPhul2chohz5aer,,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,20,318,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 313 to 318 - empty mode",
                  getExpectedResponse(gatewayErrors, "20"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,21,319,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Production,associa,123QAgapi,17343184,,,,
      //GetResidents,21,320,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Production,cinc,123QAgapi,17343184,,,,
      //GetResidents,21,321,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Production,collier,123QAgapi,17343184,,,,
      //GetResidents,21,322,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Production,fiserv,123QAgapi,17343184,,,,
      //GetResidents,21,323,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Production,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,21,324,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Production,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Production",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 319 to 324 - production mode",
                  getExpectedResponse(gatewayErrors, "21"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,38,325,317343184,badpass,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,,,
      //GetResidents,38,326,317343184,badpass,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,,,
      //GetResidents,38,327,317343184,badpass,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,,,
      //GetResidents,38,328,317343184,badpass,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,,,
      //GetResidents,38,329,317343184,badpass,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,38,330,317343184,badpass,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "badpass", null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 325 to 330 - bad password",
                  getExpectedResponse(gatewayErrors, "38"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,38,331,317343184, ,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,,,
      //GetResidents,38,332,317343184, ,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,,,
      //GetResidents,38,333,317343184, ,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,,,
      //GetResidents,38,334,317343184, ,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,,,
      //GetResidents,38,335,317343184, ,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,38,336,317343184, ,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, " ", null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 331 to 336 - blank space as password",
                  getExpectedResponse(gatewayErrors, "38"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,45,337,317343184,,iedahy2ohcie7nieWoo1,Test,associa,123QAgapi,17343184,,,,
      //GetResidents,45,338,317343184,,hovee3uv0ooShoi1Such,Test,cinc,123QAgapi,17343184,,,,
      //GetResidents,45,339,317343184,,rohn3shaiquaesh1xoSu,Test,collier,123QAgapi,17343184,,,,
      //GetResidents,45,340,317343184,,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,123QAgapi,17343184,,,,
      //GetResidents,45,341,317343184,,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,17343184,,,,17343184
      //GetResidents,45,342,317343184,,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, "", null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 337 to 342 - empty password",
                  getExpectedResponse(gatewayErrors, "45"),
                  propertyReferenceId,
                  "",
                  "",
                  "")
          ));

      //GetResidents,58,343,317343184,be297ejCEn,iedahy2ohcie7nieWoo1,Test,associa,,17343184,,,,
      //GetResidents,58,344,317343184,be297ejCEn,hovee3uv0ooShoi1Such,Test,cinc,,17343184,,,,
      //GetResidents,58,345,317343184,be297ejCEn,rohn3shaiquaesh1xoSu,Test,collier,,17343184,,,,
      //GetResidents,58,346,317343184,be297ejCEn,YXqWB4MfTZPNwgb4f7Zh,Test,fiserv,,17343184,,,,
      //GetResidents,58,347,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,,17343184,,,,17343184
      //GetResidents,58,348,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,,17343184,,,,17343184
      testCases.add(
          new AapiTestCaseCollection(
              new Credentials(userId, username, password, null, pmId),
              "Test",
              endpointValue
          ).add(
              new GetResidents(
                  "Test case 343 to 348 - empty property id",
                  getExpectedResponse(gatewayErrors, "58"),
                  "",
                  "",
                  "",
                  "")
          ));
    }

    final String[] values = {
        "onsite", "tops"
    };

    for (String endpoint : values) {
      for (String residentStatus : residentStatuses) {

        //GetResidents,53,41,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,,,,
        //GetResidents,53,42,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,,,,

        //GetResidents,53,47,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Approved,,,
        //GetResidents,53,48,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Approved,,,

        //GetResidents,53,53,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Inactive,,,
        //GetResidents,53,54,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Inactive,,,

        //GetResidents,53,59,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Suspended,,,
        //GetResidents,53,60,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Suspended,,,

        //GetResidents,53,65,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Pending,,,
        //GetResidents,53,66,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Pending,,,

        //GetResidents,53,71,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,All,,,
        //GetResidents,53,72,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,All,,,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, ""),
                "Test",
                endpoint
            ).add(
                new GetResidents(
                    "Test case 41/42/47/48/53/54/59/60/65/66/71/72 - Empty residentId and "
                        + "secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "53"),
                    propertyReferenceId,
                    "",
                    "",
                    residentStatus)
            ));

        //GetResidents,53,113,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,,GAPITesterCC,,
        //GetResidents,53,114,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,,GAPITesterCC,,

        //GetResidents,53,119,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Approved,GAPITesterCC,,
        //GetResidents,53,120,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Approved,GAPITesterCC,,

        //GetResidents,53,125,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Inactive,GAPITesterCC,,
        //GetResidents,53,126,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Inactive,GAPITesterCC,,

        //GetResidents,53,131,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Suspended,GAPITesterCC,,
        //GetResidents,53,132,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Suspended,GAPITesterCC,,

        //GetResidents,53,137,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Pending,GAPITesterCC,,
        //GetResidents,53,138,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Pending,GAPITesterCC,,

        //GetResidents,53,143,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,All,GAPITesterCC,,
        //GetResidents,53,144,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,All,GAPITesterCC,,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, ""),
                "Test",
                endpoint
            ).add(
                new GetResidents(
                    "Test case 113/114/119/120/125/126/131/132/137/138/143/144 - residentId and "
                        + "empty secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "53"),
                    propertyReferenceId,
                    residentIds.get(residentStatus),
                    "",
                    residentStatus)
            ));

        //GetResidents,53,257,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,,GAPITesterCC,SecondRefID2,
        //GetResidents,53,258,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,,GAPITesterCC,SecondRefID2,

        //GetResidents,53,263,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Approved,GAPITesterCC,SecondRefID2,
        //GetResidents,53,264,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Approved,GAPITesterCC,SecondRefID2,

        //GetResidents,53,269,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Inactive,GAPITesterCC,SecondRefID2,
        //GetResidents,53,270,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Inactive,GAPITesterCC,SecondRefID2,

        //GetResidents,53,275,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Suspended,GAPITesterCC,SecondRefID2,
        //GetResidents,53,276,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Suspended,GAPITesterCC,SecondRefID2,

        //GetResidents,53,281,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Pending,GAPITesterCC,SecondRefID2,
        //GetResidents,53,282,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Pending,GAPITesterCC,SecondRefID2,

        //GetResidents,53,287,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,All,GAPITesterCC,SecondRefID2,
        //GetResidents,53,288,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,All,GAPITesterCC,SecondRefID2,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, ""),
                "Test",
                endpoint
            ).add(
                new GetResidents(
                    "Test case 257/258/263/264/269/270/275/276/281/282/287/288 - "
                        + "residentId and secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "53"),
                    propertyReferenceId,
                    residentIds.get(residentStatus),
                    secondaryResIds.get(residentStatus),
                    residentStatus)
            ));

        //GetResidents,53,185,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,,,SecondRefID2,
        //GetResidents,53,186,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,,,SecondRefID2,

        //GetResidents,53,191,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Approved,,SecondRefID2,
        //GetResidents,53,192,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Approved,,SecondRefID2,

        //GetResidents,53,197,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Inactive,,SecondRefID2,
        //GetResidents,53,198,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Inactive,,SecondRefID2,

        //GetResidents,53,203,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Suspended,,SecondRefID2,
        //GetResidents,53,204,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Suspended,,SecondRefID2,

        //GetResidents,53,209,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,Pending,,SecondRefID2,
        //GetResidents,53,210,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,Pending,,SecondRefID2,

        //GetResidents,53,215,317343184,be297ejCEn,qua1aiPhul2chohz5aer,Test,onsite,123QAgapi,,All,,SecondRefID2,
        //GetResidents,53,216,317343184,be297ejCEn,EinaGh1oe4eihu1gohci,Test,tops,123QAgapi,,All,,SecondRefID2,
        testCases.add(
            new AapiTestCaseCollection(
                new Credentials(userId, username, password, null, ""),
                "Test",
                endpoint
            ).add(
                new GetResidents(
                    "Test case 185/186/191/192/197/198/203/204/209/210/215/216 - empty "
                        + "residentId and secondaryResidentID with different statuses",
                    getExpectedResponse(gatewayErrors, "53"),
                    propertyReferenceId,
                    "",
                    secondaryResIds.get(residentStatus),
                    residentStatus)
            ));
      }
    }

    executeTests(testCases);
  }
}

