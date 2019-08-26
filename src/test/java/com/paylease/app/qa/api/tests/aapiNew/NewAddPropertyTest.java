package com.paylease.app.qa.api.tests.aapiNew;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.newApi.Action;
import com.paylease.app.qa.framework.newApi.Credentials;
import com.paylease.app.qa.framework.newApi.PayLeaseAdminRequest;
import com.paylease.app.qa.framework.newApi.Payee;
import com.paylease.app.qa.framework.newApi.PaymentField;
import com.paylease.app.qa.framework.newApi.PaymentFields;
import com.paylease.app.qa.framework.newApi.Property;
import com.paylease.app.qa.framework.newApi.Response;
import com.paylease.app.qa.framework.newApi.XmlHelper;
import com.paylease.app.qa.framework.newApi.newAapi.AddPropertyAction;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.testbase.dataproviders.AddPropertyDataProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.Test;


public class NewAddPropertyTest extends ScriptBase {

  private static final String REGION = "aapi";
  private static final String FEATURE = "AddProperty";

  @Test(dataProvider = "addPropertyDataProvider", dataProviderClass =
      AddPropertyDataProvider.class, retryAnalyzer = Retry.class)
  public void addPropertyXml(String testVariationNum, String testPartnerVariationNum,
      Property testProperty, PaymentField testPaymentField1, PaymentField testPaymentField2,
      String expectedCode, String expectedMessage, Payee expectedPayee1, Payee expectedPayee2) {
    Logger.info(testVariationNum
        + " Verify that adding a new property is successful");
    String endPoint = determineEndPoint(testPartnerVariationNum);

    if (endPoint == null) {
      return;
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testPartnerVariationNum);
    testSetupPage.open();

    final String username = testSetupPage.getString("username");
    final String password = testSetupPage.getString("password");
    final String pmId = testSetupPage.getString("pmId");
    final String apiKey = testSetupPage.getString("apiKey");

    Credentials credentials = new Credentials();
    credentials.setUsername(username);
    credentials.setPassword(password);
    credentials.setApiKey(apiKey);

    AddPropertyAction addPropertyAction = new AddPropertyAction();
    addPropertyAction.setPmId(pmId);
    addPropertyAction.setType(AddPropertyAction.ACTION_NAME);
    List<PaymentField> paymentFieldsList = new ArrayList<PaymentField>();
    PaymentFields paymentFields = new PaymentFields();
    if (testPaymentField1 != null) {
      paymentFieldsList.add(testPaymentField1);
    }
    if (testPaymentField2 != null) {
      paymentFieldsList.add(testPaymentField2);
    }
    if (!paymentFieldsList.isEmpty()) {
      paymentFields.setPaymentFieldList(paymentFieldsList);
      testProperty.setPaymentFields(paymentFields);
    }
    addPropertyAction.setProperty(testProperty);

    PayLeaseAdminRequest payLeaseAdminRequest = buildRequestObject(credentials, addPropertyAction);

    StringEntity payLeaseAdminRequestEntity = XmlHelper
        .marshalRequestObjectToXmlString(payLeaseAdminRequest);

    RestClientUtil restClientUtil = new RestClientUtil();
    String url = PayLeaseAdminRequest.createUrl(endPoint);
    HttpResponse httpResponse = XmlHelper
        .sendAapiRequest(restClientUtil, payLeaseAdminRequestEntity, url);

    Response response = new Response();

    response.validateAddPropertyAapiResponse(restClientUtil, httpResponse, expectedCode,
        expectedMessage, expectedPayee1, expectedPayee2);
  }

  //------------------------------------------Test Methods------------------------------------------

  private PayLeaseAdminRequest buildRequestObject(Credentials credentials, Action action) {
    PayLeaseAdminRequest payLeaseAdminRequest = new PayLeaseAdminRequest();
    payLeaseAdminRequest.setCredentials(credentials);
    payLeaseAdminRequest.setMode(PayLeaseAdminRequest.TEST_MODE);
    payLeaseAdminRequest.setAction(action);

    return payLeaseAdminRequest;
  }

  private String determineEndPoint(String testVariation) {
    switch (testVariation) {
      case "tc6526":
        return "mri";
      case "tc6527":
        return "resman";
      default:
        return null;
    }
  }
}
