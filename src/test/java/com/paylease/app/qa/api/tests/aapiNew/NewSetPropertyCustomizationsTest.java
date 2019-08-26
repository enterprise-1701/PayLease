package com.paylease.app.qa.api.tests.aapiNew;

import static com.paylease.app.qa.framework.newApi.PayLeaseAdminRequest.TEST_MODE;
import static com.paylease.app.qa.framework.newApi.newAapi.SetPropertyCustomizations.ACTION_NAME;
import static com.paylease.app.qa.framework.utility.database.client.dto.CustomizationsPropertyDTO.CASH_PAY;
import static com.paylease.app.qa.framework.utility.database.client.dto.CustomizationsPropertyDTO.FIXED_AMOUNT;
import static com.paylease.app.qa.framework.utility.database.client.dto.CustomizationsPropertyDTO.PAYMENT_METHOD_ACH;
import static com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider.CUSTOMIZATION_NOT_RECOGNIZED;
import static com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider.DISALLOW_PRE_PAYMENTS;
import static com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider.ENABLE_ACH;
import static com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider.ENABLE_CASH_PAY;
import static com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider.ENABLE_CC;
import static com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider.LOCK_BALANCES;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Action;
import com.paylease.app.qa.framework.newApi.CodeMessage;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.Customization;
import com.paylease.app.qa.framework.newApi.Customizations;
import com.paylease.app.qa.framework.newApi.PayLeaseAdminRequest;
import com.paylease.app.qa.framework.newApi.Properties;
import com.paylease.app.qa.framework.newApi.Property;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newAapi.SetPropertyCustomizations;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.database.client.dao.CustomizationsPropertyDAO;
import com.paylease.app.qa.framework.utility.database.client.dao.PropertiesDAO;
import com.paylease.app.qa.framework.utility.database.client.dto.CustomizationsPropertyDTO;
import com.paylease.app.qa.framework.utility.database.client.dto.PropertiesDTO;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.SetPropertyCustomizationsDataProvider;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class NewSetPropertyCustomizationsTest extends ScriptBase {

  private static final String REGION = "aapi";
  private static final String FEATURE = "SetPropertyCustomizations";

  private static final String[] CUSTOMIZATION_PROP = new String[]{ENABLE_ACH, ENABLE_CASH_PAY,
      LOCK_BALANCES, DISALLOW_PRE_PAYMENTS};
  private static final String[] PROPERTIES = new String[]{ENABLE_ACH, ENABLE_CC};

  @Test(dataProvider = "setPropertyCustomizationsDataProvider", dataProviderClass = SetPropertyCustomizationsDataProvider.class, retryAnalyzer = Retry.class)
  public void setPropertyCustomizationsTest(String testVariationNum, String testCase,
      Customizations prop1Customizations,
      Customizations prop2Customizations, Customizations expectedCustomizations1,
      Customizations expectedCustomizations2, String expectedSuccess, CodeMessage[] errorsArray,
      String expectedProperty1Success, String expectedProperty2Success) {
    Logger.info("Verify that the SetPropertyCustomizations AAPI action works as expected "
        + testVariationNum);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String apiKey = testSetupPage.getString("apiKey");
    final String prop1Num = testSetupPage.getString("propNum1");
    final String prop2Num = testSetupPage.getString("propNum2");
    final String prop1Id = testSetupPage.getString("propId1");
    final String prop2Id = testSetupPage.getString("propId2");

    Credentials credentials = new Credentials();
    credentials.setUsername(username);
    credentials.setPassword(password);
    credentials.setApiKey(apiKey);

    SetPropertyCustomizations setPropertyCustomizations = new SetPropertyCustomizations();
    setPropertyCustomizations.setPmId(pmId);
    setPropertyCustomizations.setType(ACTION_NAME);
    List<Property> propertyList = new ArrayList<>();

    Property property1 = new Property();
    property1.setPropertyId(prop1Num);
    property1.setCustomizations(prop1Customizations);
    propertyList.add(property1);

    if (prop2Customizations != null) {
      Property property2 = new Property();
      property2.setCustomizations(prop2Customizations);
      property2.setPropertyId(prop2Num);
      propertyList.add(property2);
    }

    Properties properties = new Properties();
    properties.setPropertyList(propertyList);

    setPropertyCustomizations.setProperties(properties);

    PayLeaseAdminRequest payLeaseAdminRequest = buildRequestObject(credentials,
        setPropertyCustomizations);

    StringEntity payLeaseAdminRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseAdminRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    String url = PayLeaseAdminRequest.createUrl(getEndpoint(testCase));
    HttpResponse httpResponse = XmlHelper
        .sendAapiRequest(restClientUtil, payLeaseAdminRequestEntity, url);
    Response response = new Response();

    errorMessagePropCust(errorsArray, prop1Num);

    response
        .validateSetPropertyCustomizationAapiResponse(restClientUtil, httpResponse, expectedSuccess,
            errorsArray, expectedCustomizations1, expectedCustomizations2, expectedProperty1Success,
            expectedProperty2Success, testVariationNum);

    verifyDBChanges(prop1Id, expectedCustomizations1, testVariationNum);
    verifyDBChanges(prop2Id, expectedCustomizations2, testVariationNum);

  }

  private PayLeaseAdminRequest buildRequestObject(Credentials credentials, Action action) {
    PayLeaseAdminRequest payLeaseAdminRequest = new PayLeaseAdminRequest();
    payLeaseAdminRequest.setCredentials(credentials);
    payLeaseAdminRequest.setMode(TEST_MODE);
    payLeaseAdminRequest.setAction(action);

    return payLeaseAdminRequest;
  }

  private void verifyDBChanges(String propId, Customizations expectedCustomizations,
      String testVariationNum) {
    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();
    SoftAssert softAssert = new SoftAssert();
    if (expectedCustomizations != null) {
      List<Customization> customizations = expectedCustomizations.getCustomizationList();
      for (Customization customization : customizations) {

        if (customization != null) {
          String customizationName = customization.getName();

          if (Arrays.asList(CUSTOMIZATION_PROP).contains(customization.getName())) {
            Logger
                .info("Verify that the customizations are set in Customization_property db table");

            CustomizationsPropertyDAO customizationsPropertyDAO = new CustomizationsPropertyDAO();

            ArrayList<CustomizationsPropertyDTO> propertyCustomizations = customizationsPropertyDAO
                .findByPropIdAndCustomizationId(connection, Integer.valueOf(propId),
                    getCustomizationId(customizationName), 1);

            String expectedValue =
                !customization.getSuccess().equals("NO") ? customization.getValue().toUpperCase()
                    : null;
            String actualValue = propertyCustomizations.size() == 0 ? null
                : propertyCustomizations.get(0).getOverrideValue();
            softAssert.assertEquals(actualValue,
                expectedValue, testVariationNum + "Value in customization_property is not correct");
          }
          if (Arrays.asList(PROPERTIES).contains(customizationName)) {

            PropertiesDAO propertiesDAO = new PropertiesDAO();

            ArrayList<PropertiesDTO> properties = propertiesDAO
                .findByPropId(connection, Integer.valueOf(propId), 1);
            if (customizationName.equals("EnableCC")) {
              softAssert.assertEquals(properties.get(0).getDisableCc(),
                  getPropertyCustVal(customization.getValue()),
                  testVariationNum + " EnableCC has the wrong value");
            } else {
              softAssert.assertEquals(properties.get(0).getDisableAch(),
                  getPropertyCustVal(customization.getValue()),
                  testVariationNum + " EnableAch has the wrong value");
            }
          }
        }
      }
    }
    softAssert.assertAll();
  }

  private int getCustomizationId(String customizationName) {
    switch (customizationName) {
      case "EnableACH":
        return PAYMENT_METHOD_ACH;
      case "EnableCashPay":
        return CASH_PAY;
      case "LockBalances":
        return FIXED_AMOUNT;
      case "DisallowPrePayments":
        return CustomizationsPropertyDTO.DISALLOW_PRE_PAYMENTS;
      default:
        return 999;
    }
  }

  private int getPropertyCustVal(String customizationValue) {
    switch (customizationValue) {
      case "YES":
        return 0;
      case "NO":
        return 1;
      default:
        return 2;
    }
  }

  private void errorMessagePropCust(CodeMessage[] errorArray, String propNum) {
    for (int i = 0; i < errorArray.length; i++) {
      if (errorArray[i].getMessage().equals(CUSTOMIZATION_NOT_RECOGNIZED)) {
        String errorMessage = errorArray[i].getMessage();
        errorMessage = errorMessage.replace("{{property}}", propNum);
        errorArray[i].setMessage(errorMessage);
      }
    }
  }

  private String getEndpoint(String testCase) {
    String[] mriEndpoint = new String[]{"tc6796", "tc6807", "tc6810", "tc6814"};
    String[] resmanEndpoint = new String[]{"tc6822", "tc6827", "tc6830", "tc6834"};
    if (Arrays.asList(mriEndpoint).contains(testCase)) {
      return "mri";
    }
    if (Arrays.asList(resmanEndpoint).contains(testCase)) {
      return "resman";
    }
    return null;
  }

}
