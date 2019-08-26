package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.PaymentAmount;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.resident.SsoErrorPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.resident.ResAutoPayListPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResidentAddCardAccountPage;
import com.paylease.app.qa.framework.pages.resident.SsoBankAccountFormPage;
import com.paylease.app.qa.framework.pages.resident.SsoBankAccountLoginPage;
import com.paylease.app.qa.framework.pages.resident.SsoCreditCardFormPage;
import com.paylease.app.qa.framework.pages.resident.SsoCreditCardLoginPage;
import com.paylease.app.qa.framework.pages.resident.SsoPaymentMethodLoginPage;
import java.text.DecimalFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SsoTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "sso";

  private enum SsoPaymentMethodType {
    BANK, CREDIT
  }

  private enum SsoPaymentMethodTokenType {
    NO_TOKEN, INVALID_TOKEN, VALID_TOKEN
  }

  private static final String PAYMENT_DISABLED_ERROR_MESSAGE = "Error encountered: Payments have "
      + "been disabled by your property manager.\n"
      + "Please contact support.";

  private static final String REQUEST_VALIDATION_ERROR_MESSAGE = "Error encountered: Request "
      + "Validation Failed.\n"
      + "Please contact support.";

  private static final String PAYMENT_NOT_ACCEPTED_ERROR_MESSAGE =
      "Payments are not being accepted at this time. Please contact your property management "
          + "company or HOA for further information.";

  private static final String SSO_LINK_EXPIRED_ERROR_MESSAGE = "Error encountered: SSO Link is "
      + "Expired.\n"
      + "Please contact support.";

  //-----------------------------------SSO Tests----------------------------------------------------

  @Test
  public void onePaymentAmount() {
    Logger.info("To validate that the same payment field and amount value is populated as defined "
        + "in the SSO string");

    testOnePaymentAmount("tc1", false);
  }

  @Test
  public void multiplePaymentAmount() {
    Logger.info("To validate that the same payment fields and amount values are populated as "
        + "defined in the SSO string");

    testMultiplePaymentAmount("tc2");
  }

  @Test
  public void multiplePaymentAmountYavo() {
    Logger.info("To validate that the same payment fields and amount values are populated as "
        + "defined in the SSO string - YAVO");

    testMultiplePaymentAmount("yavo_tc2");
  }

  @Test
  public void multipleInvalidPaymentAmount() {
    Logger.info("To validate that all invalid payment amounts are populated under the same field");

    testMultipleInvalidPaymentAmount("tc3");
  }

  @Test
  public void multipleInvalidPaymentAmountYavo() {
    Logger.info(
        "To validate that all invalid payment amounts are populated under the same field - YAVO");

    testMultipleInvalidPaymentAmount("yavo_tc3");
  }

  @Test
  public void multipleValidInvalidPaymentAmount() {
    Logger.info("To validate that invalid payment amounts are populated under the same field but "
        + "valid amounts are populated under their respective fields");

    testMultipleValidInvalidPaymentAmount("tc4");
  }

  @Test
  public void multipleValidInvalidPaymentAmountYavo() {
    Logger.info("To validate that invalid payment amounts are populated under the same field but "
        + "valid amounts are populated under their respective fields - YAVO");

    testMultipleValidInvalidPaymentAmount("yavo_tc4");
  }

  @Test
  public void lockedPaymentAmount() {
    Logger.info("To validate that the locked payment amount value is not editable");

    testOnePaymentAmount("tc5", true);
  }

  @Test
  public void fixedAutoPayDisabled() {
    Logger.info("To validate that when fixed autopay is disabled via SSO, resident is unable to "
        + "schedule fixed autopay");

    testPrep("tc6");

    ResAutoPayListPage resAutoPayListPage = new ResAutoPayListPage();

    Assert.assertFalse(resAutoPayListPage.isCreateNewAutoPayButtonPresent(),
        "Create AutoPay button should not be present");
  }

  @Test
  public void badHmac() {
    Logger.info("To validate that the resident is unable to login with a SSO string consisting of "
        + "bad hmac");

    testErrorMessage("tc7", REQUEST_VALIDATION_ERROR_MESSAGE);
  }

  @Test
  public void blockedResident() {
    Logger.info("To validate that the resident is blocked from logging in and presented with a "
        + "message that payments have been disabled");

    testErrorMessage("tc8", PAYMENT_DISABLED_ERROR_MESSAGE);
  }

  @Test
  public void blockedResidentYavo() {
    Logger.info("To validate that the resident is blocked from logging in and presented with a "
        + "message that payments have been disabled - YAVO");

    testErrorMessage("yavo_tc8", PAYMENT_DISABLED_ERROR_MESSAGE);
  }

  @Test
  public void achCcDisabled() {
    Logger.info("To validate that the resident is presented a message on the onetime page that "
        + "their payments are not being accepted after logging in");

    testPrep("tc9");

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertEquals(paymentAmountPage.getPaymentNotAcceptedText(),
        PAYMENT_NOT_ACCEPTED_ERROR_MESSAGE, "Error message should match");
  }

  @Test
  public void createResident() {
    Logger.info("To validate that a resident is created and able to login via SSO");

    testCreateResident("tc10");
  }

  @Test
  public void createResidentYavo() {
    Logger.info("To validate that a resident is created and able to login via SSO - YAVO");

    testCreateResident("yavo_tc10");
  }

  @Test
  public void showDisclosureTest() {
    Logger.info("Verify we see disclosure messages for SSO One-time payment");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc15", PaymentFlow.SCHEDULE_ONE_TIME, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  @Test
  public void showDisclosureWithDebitCardTestFap() {
    Logger.info("Verify we see disclosure messages for SSO Fixed Autopay with debit card");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc16", PaymentFlow.SCHEDULE_FIXED_AUTO, PaymentFlow.STEP_NEW_DEBIT
    );
  }

  @Test
  public void showDisclosureWithCreditCardTestFap() {
    Logger.info("Verify we see disclosure messages for SSO Fixed Autopay with credit card");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc16", PaymentFlow.SCHEDULE_FIXED_AUTO, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  @Test
  public void showDisclosureTestFap() {
    Logger.info("Verify we see disclosure messages for SSO Fixed Autopay");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc18", PaymentFlow.SCHEDULE_FIXED_AUTO, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  @Test
  public void showDisclosureWithDebitCardTestVap() {
    Logger.info("Verify we see disclosure messages for SSO Variable Autopay with debit card");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc19", PaymentFlow.SCHEDULE_VARIABLE_AUTO, PaymentFlow.STEP_NEW_DEBIT
    );
  }

  @Test
  public void showDisclosureWithCreditCardTestVap() {
    Logger.info("Verify we see disclosure messages for SSO Variable Autopay with credit card");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc19", PaymentFlow.SCHEDULE_VARIABLE_AUTO, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  @Test
  public void showDisclosureTestVap() {
    Logger.info("Verify we see disclosure messages for SSO Variable Autopay");

    CreditCardDetails creditCardDetails = new CreditCardDetails();
    creditCardDetails.assertDisclosureMessage(
        REGION, FEATURE, "rc_tc21", PaymentFlow.SCHEDULE_VARIABLE_AUTO, PaymentFlow.STEP_NEW_CREDIT
    );
  }

  @Test
  public void assertDisclosure() {
    Logger.info("Verify we see disclosure messages for SSO new payment method - card");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rc_tc22");
    testSetupPage.open();

    ResidentAddCardAccountPage addCardAccountPage = new ResidentAddCardAccountPage();
    addCardAccountPage.open();

    Assert.assertFalse(
        addCardAccountPage.getDisclosureMessage().isEmpty(), "Missing disclosure message"
    );

    addCardAccountPage.clickDisclosureReadMoreButton();

    Assert.assertTrue(
        addCardAccountPage.isSecondDisclosureMessageVisible(),
        "Second disclosure message not displayed"
    );
  }

  @Test
  public void bankAccountFormNoToken() {
    Logger.info("Open the SSO Bank Account form page but provide no token");

    SsoPaymentMethodLoginPage page = openSsoPaymentMethodLogin(SsoPaymentMethodType.BANK,
        SsoPaymentMethodTokenType.NO_TOKEN);
    Assert.assertEquals(page.getErrorMessage(), "A token was not provided.",
        "Message about missing token should be displayed");

    SsoBankAccountFormPage ssoBankAccountFormPage = new SsoBankAccountFormPage();
    Assert.assertFalse(ssoBankAccountFormPage.pageIsLoaded(),
        "Page should not have loaded without valid token");
  }

  @Test
  public void creditCardFormNoToken() {
    Logger.info("Open the SSO Credit Card form page but provide no token");

    SsoPaymentMethodLoginPage page = openSsoPaymentMethodLogin(SsoPaymentMethodType.CREDIT,
        SsoPaymentMethodTokenType.NO_TOKEN);
    Assert.assertEquals(page.getErrorMessage(), "A token was not provided.",
        "Message about missing token should be displayed");

    SsoCreditCardFormPage ssoCreditCardFormPage = new SsoCreditCardFormPage();
    Assert.assertFalse(ssoCreditCardFormPage.pageIsLoaded(),
        "Page should not have loaded without valid token");
  }

  @Test
  public void bankAccountFormBadToken() {
    Logger.info("Open the SSO Bank Account form page but provide an invalid token");

    SsoPaymentMethodLoginPage page = openSsoPaymentMethodLogin(SsoPaymentMethodType.BANK,
        SsoPaymentMethodTokenType.INVALID_TOKEN);
    Assert.assertEquals(page.getErrorMessage(), "A bad token was provided.",
        "Message about invalid token should be displayed");

    SsoBankAccountFormPage ssoBankAccountFormPage = new SsoBankAccountFormPage();
    Assert.assertFalse(ssoBankAccountFormPage.pageIsLoaded(),
        "Page should not have loaded with invalid token");
  }

  @Test
  public void creditCardFormBadToken() {
    Logger.info("Open the SSO Credit Card form page but provide an invalid token");

    SsoPaymentMethodLoginPage page = openSsoPaymentMethodLogin(SsoPaymentMethodType.CREDIT,
        SsoPaymentMethodTokenType.INVALID_TOKEN);
    Assert.assertEquals(page.getErrorMessage(), "A bad token was provided.",
        "Message about invalid token should be displayed");

    SsoCreditCardFormPage ssoCreditCardFormPage = new SsoCreditCardFormPage();
    Assert.assertFalse(ssoCreditCardFormPage.pageIsLoaded(),
        "Page should not have loaded with invalid token");
  }

  @Test
  public void bankAccountFormValidToken() {
    Logger.info("Open the SSO Bank Account form page with a valid token");

    openSsoPaymentMethodLogin(SsoPaymentMethodType.BANK, SsoPaymentMethodTokenType.VALID_TOKEN);

    SsoBankAccountFormPage ssoBankAccountFormPage = new SsoBankAccountFormPage();
    Assert.assertTrue(ssoBankAccountFormPage.pageIsLoaded(),
        "Page should have loaded with valid token");
  }

  @Test
  public void creditCardFormValidToken() {
    Logger.info("Open the SSO Credit Card form page with a valid token");

    openSsoPaymentMethodLogin(SsoPaymentMethodType.CREDIT, SsoPaymentMethodTokenType.VALID_TOKEN);

    SsoCreditCardFormPage ssoCreditCardFormPage = new SsoCreditCardFormPage();
    Assert.assertTrue(ssoCreditCardFormPage.pageIsLoaded(),
        "Page should have loaded with valid token");
  }

  @Test
  public void tc6047() {
    Logger.info("Given a valid and future timestamp, the resident lands on Resident Dashboard");

    testCreateResident("tc6047");
  }

  @Test
  public void tc6048() {
    Logger.info(
        "Given an expired timestamp, the resident lands on an error page and is not able to login");

    testErrorMessage("tc6048", SSO_LINK_EXPIRED_ERROR_MESSAGE);
  }

  @Test
  public void tc6049() {
    Logger.info(
        "Given an invalid timestamp (Not in valid unix time format), the resident lands on an error"
        + " page and is not able to login");

    testErrorMessage("tc6049", SSO_LINK_EXPIRED_ERROR_MESSAGE);
  }

  //------------------------------------METHODS-----------------------------------------------------

  private void testOnePaymentAmount(String testCase, boolean setAmount) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String ssoUrl = testSetupPage.getString("ssoUrl");
    final String leaseField = testSetupPage.getString("leaseFieldLabel");
    final String leasePaymentAmount = testSetupPage.getString("leasePaymentAmount");

    DriverManager.getDriver().get(ssoUrl);

    PaymentAmount paymentAmount = new PaymentAmount();

    if (setAmount) {
      Assert.assertFalse(paymentAmount.setPaymentFieldAmount(leaseField, "100.05"),
          "Clear worked when not expected");
    }

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(leaseField).equals(leasePaymentAmount),
        "Should have the same amount");
  }

  private void testMultiplePaymentAmount(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String ssoUrl = testSetupPage.getString("ssoUrl");
    final String leaseField = testSetupPage.getString("lease_payment_field_label");
    final String petFeeField = testSetupPage.getString("pet_fee_field_label");
    final String latePaymentField = testSetupPage.getString("late_payment_field_label");
    final String leasePaymentAmount = testSetupPage.getString("leasePaymentAmount");
    final String petFeeAmount = testSetupPage.getString("petFeeAmount");
    final String latePaymentAmount = testSetupPage.getString("lateFeeAmount");

    DriverManager.getDriver().get(ssoUrl);

    PaymentAmount paymentAmount = new PaymentAmount();

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(leaseField).equals(leasePaymentAmount),
        "Should have the same amount");

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(petFeeField).equals(petFeeAmount),
        "Should have the same amount");

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(latePaymentField)
        .equals(latePaymentAmount), "Should have the same amount");
  }

  private void testMultipleInvalidPaymentAmount(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String ssoUrl = testSetupPage.getString("ssoUrl");
    final String amountOwedField = testSetupPage.getString("amount_owed_field_label");
    final String badAmount = testSetupPage.getString("badAmount");
    final String badAmount1 = testSetupPage.getString("badAmount1");
    final String badAmount2 = testSetupPage.getString("badAmount2");

    double badAmountNum = Double.parseDouble(badAmount);
    double badAmount1Num = Double.parseDouble(badAmount1);
    double badAmount2Num = Double.parseDouble(badAmount2);

    double total = badAmountNum + badAmount1Num + badAmount2Num;
    DecimalFormat formatter = new DecimalFormat("###0.00");

    String totalAmount = formatter.format(total);

    DriverManager.getDriver().get(ssoUrl);

    PaymentAmount paymentAmount = new PaymentAmount();

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(amountOwedField).equals(totalAmount),
        "Should have the same amount");
  }

  private void testMultipleValidInvalidPaymentAmount(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String ssoUrl = testSetupPage.getString("ssoUrl");
    final String amountOwedField = testSetupPage.getString("amount_owed_field_label");
    final String leaseField = testSetupPage.getString("lease_payment_field_label");
    final String leasePaymentAmount = testSetupPage.getString("leasePaymentAmount");
    final String badAmount1 = testSetupPage.getString("badAmount1");
    final String badAmount2 = testSetupPage.getString("badAmount2");

    double badAmount1Num = Double.parseDouble(badAmount1);
    double badAmount2Num = Double.parseDouble(badAmount2);

    double total = badAmount1Num + badAmount2Num;
    DecimalFormat formatter = new DecimalFormat("###0.00");

    String totalAmount = formatter.format(total);

    DriverManager.getDriver().get(ssoUrl);

    PaymentAmount paymentAmount = new PaymentAmount();

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(leaseField).equals(leasePaymentAmount),
        "Should have the same amount");

    Assert.assertTrue(paymentAmount.getPaymentFieldValue(amountOwedField).equals(totalAmount),
        "Should have the same amount");
  }

  private void testErrorMessage(String testCase, String errorMessage) {
    testPrep(testCase);

    SsoErrorPage ssoErrorPage = new SsoErrorPage();

    Assert.assertEquals(ssoErrorPage.getErrorText(), errorMessage,
        "Error message should match");
  }

  private void testCreateResident(String testCase) {
    testPrep(testCase);

    ResHomePage resHomePage = new ResHomePage();

    Assert.assertTrue(resHomePage.pageIsLoaded(), "Resident home page should load");
  }

  private void testPrep(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String ssoUrl = testSetupPage.getString("ssoUrl");

    DriverManager.getDriver().get(ssoUrl);
  }

  private SsoPaymentMethodLoginPage openSsoPaymentMethodLogin(SsoPaymentMethodType paymentType,
      SsoPaymentMethodTokenType tokenType) {
    SsoPaymentMethodLoginPage page;
    String testCase;

    switch (paymentType) {
      case BANK:
        page = new SsoBankAccountLoginPage();
        testCase = "bank_account_tc02";
        break;
      case CREDIT:
      default:
        page = new SsoCreditCardLoginPage();
        testCase = "credit_card_tc02";
        break;
    }

    if (SsoPaymentMethodTokenType.NO_TOKEN != tokenType) {
      TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
      testSetupPage.open();

      String token = testSetupPage.getString("token");

      if (SsoPaymentMethodTokenType.INVALID_TOKEN == tokenType) {
        token = token.substring(1); // chop off first character to create invalid token
      }
      page.setToken(token);
    }

    page.open();

    return page;
  }
}
