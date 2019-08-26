package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OneTimePaymentTypeCcTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "oneTimePaymentTypeCc";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    Logger.info(
        "To verify that a resident is not able to enter a card number for a disabled payment option.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("ccDisabled");
    cardAccountDetailsPage.prepCardType(CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_AMEX_LITLE);
    cardAccountDetailsPage.fillAndSubmitCardDetails();

    Assert.assertEquals(cardAccountDetailsPage.getCardNumberValidationError(),
        "Please note that Amex is not accepted by this management company. Please try another payment method.",
        "Expected error message not found");
  }

  @Test
  public void tc3() {
    Logger.info("To validate error message when Card Number field is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.CARD_NUMBER_BOX);

    Assert
        .assertEquals(cardAccountDetailsPage.getCardNumberErrorMessage(), "Card Number is required",
            "Card Number is required message should show");
  }

  @Test
  public void tc4() {
    Logger.info("To validate error message when CVV2 field is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.CVV2_BOX);

    Assert.assertEquals(cardAccountDetailsPage.getCvv2ErrorMessage(), "CVV2 is required",
        "CVV2 is required message should show");
  }

  @Test
  public void tc5() {
    Logger.info("To validate error message when expiration month is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.EXPIRATION_MONTH_SELECT);

    Assert.assertEquals(cardAccountDetailsPage.getExpirationMonthErrorMessage(),
        "Expiration month is required", "Expiration month is required message should show");
  }

  @Test
  public void tc6() {
    Logger.info("To validate error message when Expiration year is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.EXPIRATION_YEAR_SELECT);

    Assert.assertEquals(cardAccountDetailsPage.getExpirationYearErrorMessage(),
        "Expiration year is required", "Expiration year is required message should show");
  }

  @Test
  public void tc7() {
    Logger.info("To validate error message when First Name is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.FIRST_NAME_BOX);

    Assert.assertEquals(cardAccountDetailsPage.getFirstNameErrorMessage(), "First Name is required",
        "First Name is required message should show");
  }

  @Test
  public void tc8() {
    Logger.info("To validate error message when Last Name is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.LAST_NAME_BOX);

    Assert.assertEquals(cardAccountDetailsPage.getLastNameErrorMessage(), "Last Name is required",
        "Last Name is required message should show");
  }

  @Test
  public void tc9() {
    Logger.info("To validate error message when Billing Address is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.BILLING_ADDRESS_BOX);

    Assert
        .assertEquals(cardAccountDetailsPage.getBillingAddressErrorMessage(),
            "Billing Address is required", "Billing Address is required message should show");
  }

  @Test
  public void tc10() {
    Logger.info("To validate error message when Billing City is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.BILLING_CITY_BOX);

    Assert.assertEquals(cardAccountDetailsPage.getBillingCityErrorMessage(),
        "Billing City is required", "Billing City is required message should show");
  }

  @Test
  public void tc11() {
    Logger.info("To validate error message when Billing State is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.BILLING_STATE_SELECT);

    Assert.assertEquals(cardAccountDetailsPage.getBillingStateErrorMessage(),
        "Billing State is required", "Billing State is required message should show");
  }

  @Test
  public void tc12() {
    Logger.info("To validate error message when Billing Zip is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillExcludeAndSubmitCard(CardAccountDetailsPage.BILLING_ZIP_BOX);

    Assert.assertEquals(cardAccountDetailsPage.getZipErrorMessage(), "Billing Zip is required",
        "Billing Zip is required message should show");
  }

  @Test
  public void tc13() {
    Logger.info("To validate error message when an invalid Card Number is entered.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillAndSubmitCardDetailsWithCardNum("abc");

    Assert.assertEquals(cardAccountDetailsPage.getCardNumberErrorMessage(),
        "Valid Card number is required", "Valid Card number is required message should show");
  }

  @Test
  public void tc14() {
    Logger.info("To validate error message when Zip Code less than 5 digits is entered.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillCardDetailsWithInvalidZipAndSubmit("1234");

    Assert.assertEquals(cardAccountDetailsPage.getZipErrorMessage(),
        "Please enter a valid Billing Zip", "Please enter a valid Billing Zip message should show");
  }

  @Test
  public void tc15() {
    Logger.info("To validate error message when all fields are left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.clickContinueButtonNoWait();

    Assert
        .assertEquals(cardAccountDetailsPage.getCardNumberErrorMessage(), "Card Number is required",
            "Card Number is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getCvv2ErrorMessage(), "CVV2 is required",
        "CVV2 is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getExpirationMonthErrorMessage(),
        "Expiration month is required", "Expiration month is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getExpirationYearErrorMessage(),
        "Expiration year is required", "Expiration year is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getFirstNameErrorMessage(), "First Name is required",
        "First Name is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getLastNameErrorMessage(), "Last Name is required",
        "Last Name is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getBillingAddressErrorMessage(),
        "Billing Address is required", "Billing Address is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getBillingCityErrorMessage(),
        "Billing City is required", "Billing City is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getBillingStateErrorMessage(),
        "Billing State is required", "Billing State is required message should show");
    Assert.assertEquals(cardAccountDetailsPage.getZipErrorMessage(), "Billing Zip is required",
        "Billing Zip is required message should show");
  }

  @Test
  public void tc16() {
    Logger.info(
        "To validate that a resident is able to get to the Review and Submit page when all of the fields are filled with valid data.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.fillAndSubmitCardDetailsWithCardNum();

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertTrue(reviewAndSubmitPage.pageIsLoaded(),
        "Card Details not submitted successfully, Review and Submit page did not load.");
  }

  @Test
  public void tc17() {
    Logger.info("To validate the Payment for text.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "otpCcSetup");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");
    final String paymentFields = testSetupPage.getString("paymentFields");

    PaymentFlow paymentFlow = openStepNewCredit(transId);

    Assert
        .assertTrue(paymentFlow.getPaymentFor().equalsIgnoreCase(paymentFields),
            "Payment for text should match");
  }

  @Test
  public void tc18() {
    Logger.info("To validate the Payment Amount shows the correct payment amount.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "otpCcSetup");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");
    final String totalPaymentAmt = testSetupPage.getString("totalPaymentAmt");

    PaymentFlow paymentFlow = openStepNewCredit(transId);

    Assert.assertTrue(paymentFlow.getPaymentAmount().equalsIgnoreCase(totalPaymentAmt),
        "Amount should match");
  }

  @Test
  public void tc19() {
    Logger.info(
        "To validate that the Hover Bubble images are shown when cursor hovers over the ? for CVV2.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("otpCcSetup");
    cardAccountDetailsPage.mouseHoverCvv2Help();

    Assert.assertTrue(cardAccountDetailsPage.isCvv2ImageDisplayed(), "CVV image should display");
  }

  @Test
  public void tc20() {
    Logger.info(
        "To validate that a disabled Payment Option does not appear in the Card Details page as a logo.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrepPaymentFlow("ccDisabled");

    Assert.assertFalse(cardAccountDetailsPage.isAmexLogoPresent(),
        "AMEX logo should not be present in the card details page");
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private CardAccountDetailsPage testPrepPaymentFlow(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");

    openStepNewCredit(transId);

    return new CardAccountDetailsPage();
  }

  private PaymentFlow openStepNewCredit(String transId) {
    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME,
        transId);

    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    return paymentFlow;
  }
}
