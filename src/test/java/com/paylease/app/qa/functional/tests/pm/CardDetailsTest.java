package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CardDetailsTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "CardDetails";

  //--------------------------------CARD DETAILS TESTS----------------------------------------------

  @Test
  public void cardDetailsPmTc1() {
    Logger.info("To validate that an error message appears on the Card Details page when "
        + "Card Number field is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.CARD_NUMBER_BOX, "tc1");

    Assert.assertTrue(cardAccountDetailsPage.getCardNumberErrorMessage()
        .equals("Card Number is required"), "Card Number is required message should show");
  }

  @Test
  public void cardDetailsPmTc2() {
    Logger.info("To validate that an error message appears on the Card Details page when CVV2 "
        + "field is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.CVV2_BOX, "tc2");

    Assert.assertTrue(cardAccountDetailsPage.getCvv2ErrorMessage()
        .equals("CVV2 is required"), "CVV2 is required message should show");
  }

  @Test
  public void cardDetailsPmTc3() {
    Logger.info("To validate that an error message appears on the Card Details page when "
        + "Expiration month is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.EXPIRATION_MONTH_SELECT, "tc3");

    Assert.assertTrue(cardAccountDetailsPage.getExpirationMonthErrorMessage()
        .equals("Expiration month is required"), "Expiration month is required message "
        + "should show");
  }

  @Test
  public void cardDetailsPmTc4() {
    Logger.info("To validate that an error message appears on the Card Details page when "
        + "Expiration year is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.EXPIRATION_YEAR_SELECT, "tc4");

    Assert.assertTrue(cardAccountDetailsPage.getExpirationYearErrorMessage()
        .equals("Expiration year is required"), "Expiration year is required message "
        + "should show");
  }

  @Test
  public void cardDetailsPmTc5() {
    Logger.info("To validate that an error message appears on the Card Details page when First "
        + "Name is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.FIRST_NAME_BOX, "tc5");

    Assert.assertTrue(cardAccountDetailsPage.getFirstNameErrorMessage()
        .equals("First Name is required"), "First Name is required message should show");
  }

  @Test
  public void cardDetailsPmTc6() {
    Logger.info("To validate that an error message appears on the Card Details page when Last "
        + "Name is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.LAST_NAME_BOX, "tc6");

    Assert.assertTrue(cardAccountDetailsPage.getLastNameErrorMessage()
        .equals("Last Name is required"), "Last Name is required message should show");
  }

  @Test
  public void cardDetailsPmTc7() {
    Logger.info("To validate that an error message appears on the Card Details page when Billing "
        + "Address is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.BILLING_ADDRESS_BOX, "tc7");

    Assert.assertTrue(cardAccountDetailsPage.getBillingAddressErrorMessage()
        .equals("Billing Address is required"), "Billing Address is required message should "
        + "show");
  }

  @Test
  public void cardDetailsPmTc8() {
    Logger.info("To validate that an error message appears on the Card Details page when Billing "
        + "City is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.BILLING_CITY_BOX, "tc8");

    Assert.assertTrue(cardAccountDetailsPage.getBillingCityErrorMessage()
        .equals("Billing City is required"), "Billing City is required message should show");
  }

  @Test
  public void cardDetailsPmTc9() {
    Logger.info("To validate that an error message appears on the Card Details page when Billing "
        + "State is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.BILLING_STATE_SELECT, "tc9");

    Assert.assertTrue(cardAccountDetailsPage.getBillingStateErrorMessage()
        .equals("Billing State is required"), "Billing State is required message should "
        + "show");
  }

  @Test
  public void cardDetailsPmTc10() {
    Logger.info("To validate that an error message appears on the Card Details page when Billing "
        + "Zip is left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = verifyErrorMessage(
        CardAccountDetailsPage.BILLING_ZIP_BOX, "tc10");

    Assert.assertTrue(cardAccountDetailsPage.getZipErrorMessage()
        .equals("Billing Zip is required"), "Billing Zip is required message should show");
  }

  @Test(groups = {"litle"})
  public void cardDetailsPmTc11() {
    Logger.info("To validate that an error message appears on the Card Details page when an "
        + "invalid Card Number is entered.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrep("tc11");

    cardAccountDetailsPage.fillAndSubmitCardDetailsWithCardNum("445701000000000");

    Assert.assertTrue(cardAccountDetailsPage.getCardNumberErrorMessage()
        .equals("Valid Card number is required"), "Valid Card number is required message "
        + "should show");
  }

  @Test
  public void cardDetailsPmTc12() {
    Logger.info("To validate that an error message appears on the Card Details page when Zip Code "
        + "less than 5 digits is entered.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrep("tc12");

    cardAccountDetailsPage.fillCardDetailsWithInvalidZipAndSubmit("1234");

    Assert.assertTrue(cardAccountDetailsPage.getZipErrorMessage()
        .equals("Please enter a valid Billing Zip"), "Please enter a valid Billing Zip "
        + "message should show");
  }

  @Test
  public void cardDetailsPmTc13() {
    Logger.info("To validate that an error message appears on the Card Details page when all the "
        + "fields are left blank.");

    CardAccountDetailsPage cardAccountDetailsPage = testPrep("tc13");

    cardAccountDetailsPage.clickContinueButtonNoWait();

    Assert.assertTrue(cardAccountDetailsPage.getCardNumberErrorMessage()
        .equals("Card Number is required"), "Card Number is required message should show");

    Assert.assertTrue(cardAccountDetailsPage.getCvv2ErrorMessage()
        .equals("CVV2 is required"), "CVV2 is required message should show");

    Assert.assertTrue(cardAccountDetailsPage.getExpirationMonthErrorMessage()
        .equals("Expiration month is required"), "Expiration month is required message "
        + "should show");

    Assert.assertTrue(cardAccountDetailsPage.getExpirationYearErrorMessage()
        .equals("Expiration year is required"), "Expiration year is required message "
        + "should show");

    Assert.assertTrue(cardAccountDetailsPage.getFirstNameErrorMessage()
        .equals("First Name is required"), "First Name is required message should show");

    Assert.assertTrue(cardAccountDetailsPage.getLastNameErrorMessage()
        .equals("Last Name is required"), "Last Name is required message should show");

    Assert.assertTrue(cardAccountDetailsPage.getBillingAddressErrorMessage()
        .equals("Billing Address is required"), "Billing Address is required message should "
        + "show");

    Assert.assertTrue(cardAccountDetailsPage.getBillingCityErrorMessage()
        .equals("Billing City is required"), "Billing City is required message should show");

    Assert.assertTrue(cardAccountDetailsPage.getBillingStateErrorMessage()
        .equals("Billing State is required"), "Billing State is required message should "
        + "show");

    Assert.assertTrue(cardAccountDetailsPage.getZipErrorMessage()
        .equals("Billing Zip is required"), "Billing Zip is required message should show");
  }

  @Test
  public void cardDetailsPmTc15() {
    Logger.info("To validate the Payment for text");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc15");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String paymentFor = testSetupPage.getString("paymentFor");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    Assert.assertTrue(paymentFlow.getPaymentFor()
        .equalsIgnoreCase(paymentFor), "Payment for text should match");
  }

  @Test
  public void cardDetailsPmTc16() {
    Logger.info("To validate the Payment Amount text");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc16");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String amount = testSetupPage.getString("amount");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    Assert.assertTrue(paymentFlow.getPaymentAmount().equals("$" + amount), "Amount should match");
  }

  @Test
  public void cardDetailsPmTc17() {
    Logger.info("To validate that the Hover Bubble images are shown when cursor hovers over '?' "
        + "for CVV2");

    CardAccountDetailsPage cardAccountDetailsPage = testPrep("tc17");

    cardAccountDetailsPage.mouseHoverCvv2Help();

    Assert.assertTrue(cardAccountDetailsPage.isCvv2ImageDisplayed(), "CVV image should display");
  }

  @Test
  public void cardDetailsPmTc18() {
    Logger.info("To validate that a disabled payment option does not appear in the card details "
        + "page");

    CardAccountDetailsPage cardAccountDetailsPage = testPrep("tc18");

    Assert.assertFalse(cardAccountDetailsPage.isAmexLogoPresent(),
        "AMEX logo should not be present in the card details page");
  }

  @Test(groups = {"litle"})
  public void cardDetailsPmTc19() {
    Logger.info("To validate that a PM is unable to enter a Card Number for a Payment Option "
        + "that is disabled");

    CardAccountDetailsPage cardAccountDetailsPage = testPrep("tc19");

    cardAccountDetailsPage.fillAndSubmitCardDetailsWithNo(CardAccountDetailsPage
        .CARD_TYPE_CREDIT_VALID_AMEX_LITLE);

    Assert.assertTrue(cardAccountDetailsPage.getCardNumberErrorMessage()
        .equals("Please note that Amex is not accepted by this management company. Please try "
            + "another payment method."), "AMEX related error message should show");
  }

  //--------------------------------TEST METHODS----------------------------------------------------

  private CardAccountDetailsPage testPrep(String testCase) {

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_CREDIT);

    return new CardAccountDetailsPage();
  }

  private CardAccountDetailsPage verifyErrorMessage(String excludedField, String testCase) {
    CardAccountDetailsPage cardAccountDetailsPage = testPrep(testCase);

    cardAccountDetailsPage.fillExcludeAndSubmitCard(excludedField);

    return cardAccountDetailsPage;
  }
}
