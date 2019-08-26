package com.paylease.app.qa.api.tests.gapi;

import com.github.javafaker.Faker;
import com.paylease.app.qa.api.tests.BaseApiTest;
import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.api.tests.TestCaseCollection;
import com.paylease.app.qa.framework.pages.automatedhelper.GapiResponsePage;
import com.paylease.app.qa.framework.pages.automatedhelper.GapiValidationPage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;

public abstract class BaseTest extends BaseApiTest {
  private static final int MAX_VALID_PER_REQUEST = 100;

  protected int getMaxValidPerRequest() {
    return MAX_VALID_PER_REQUEST;
  }

  protected boolean executeTests(TestCaseCollection testCaseCollection) {
    boolean result = super.executeTests(testCaseCollection);
    Assert.assertTrue(result, "Not all test cases were successful");
    return true;
  }

  @Override
  protected ExpectedResponse getExpectedResponse(List<HashMap<String, Object>> messageTable, String code) {
    ExpectedResponse expectedResponse = super.getExpectedResponse(messageTable, code);
    if (code.equals("194")) {
      expectedResponse.appendMessage(" 3-Day Transaction Limit of $100,000.00.");
    }
    return expectedResponse;
  }

  protected String validateGapiResponse(String gatewayPayerId) {
    GapiResponsePage gapiResponsePage = new GapiResponsePage();
    gapiResponsePage.open();

    GapiValidationPage gapiValidationPage = gapiResponsePage.validateGatewayPayerRecord(gatewayPayerId);

    String userId = gapiValidationPage.getUserId();

    return userId;
  }

  public String setAmount(){
    Faker faker = new Faker();
    int amount = faker.number().numberBetween(10, 100);
    String amountString = Integer.toString(amount);
    return amountString;
  }

  public String setFeeAmount(){
    Faker faker = new Faker();
    int amount = faker.number().numberBetween(1, 10);
    String feeAmount = Integer.toString(amount);
    return feeAmount;
  }

  public String totalAmount(String [] amounts){
    int sum = 0;
    for (String amount : amounts){
      int amount1 = Integer.parseInt(amount);

      sum = sum + amount1;
    }
    String totalAmount = Integer.toString(sum);
    return totalAmount;
  }
}
