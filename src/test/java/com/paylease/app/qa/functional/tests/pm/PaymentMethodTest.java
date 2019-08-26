package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage.CardType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentMethodTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "PaymentMethod";

  //--------------------------------PAYMENT METHOD TESTS--------------------------------------------

  @Test
  public void validateAchOtpWithExpressPay() {
    Logger.info("To validate that a PM is able to select ACH one time payment with express pay");

    PaymentFlow paymentFlow = testPrepExpressPay("tc1", PaymentMethodPage.NEW_BANK);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_BANK,
        "Should be on Bank Details page");
  }

  @Test
  public void validateCcOtpWithExpressPay() {
    Logger.info("To validate that a PM is able to select debit/credit card one time payment with "
        + "express pay");

    PaymentFlow paymentFlow = testPrepExpressPay("tc2", PaymentMethodPage.NEW_CREDIT);

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_NEW_CREDIT,
        "Should be on Card Details page");
  }

  @Test
  public void validateErrorMessageWhenNoPaymentMethodIsSelected() {
    Logger.info("To validate that an error message appears when no Payment method is selected on "
        + "the Payment Method page. ");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc3");

    paymentMethodPage.clickContinueButtonNoWait();

    Assert.assertEquals(paymentMethodPage.getPaymentMethodErrorMessage(), "Please choose a "
        + "Payment Account", "Error message should show");
  }

  @Test
  public void validatePaymentForText() {
    Logger.info("To validate the 'Payment for' text");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String paymentFor = testSetupPage.getString("paymentFor");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    Assert.assertTrue(paymentFlow.getPaymentFor()
        .equalsIgnoreCase(paymentFor), "Payment for text should be: " + paymentFor);
  }

  @Test
  public void validatePaymentAmount() {
    Logger.info("To validate the 'Payment Amount' text");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String amount = testSetupPage.getString("amount");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    Assert.assertEquals(paymentFlow.getPaymentAmount(), "$" + amount, "Amount should match");
  }

  @Test
  public void validateIfeCheckImageIsDisplayed() {
    Logger.info("To validate eCheck image is displayed next to Bank Account");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc6");

    Assert.assertTrue(paymentMethodPage.isCheckImageDisplayed(), "eCheck image should be present");
  }

  @Test
  public void validateIfCardImagesAreDisplayed() {
    Logger.info("To validate Visa, Master Card, Discover and Amex image is displayed next to "
        + "Debit/Credit card");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc7");

    Assert.assertTrue(paymentMethodPage.isCcImageDisplayed(CardType.AMEX),
        "Amex image should be present");

    Assert.assertTrue(paymentMethodPage.isCcImageDisplayed(CardType.DISCOVER),
        "Discover image should be present");

    Assert.assertTrue(paymentMethodPage.isCcImageDisplayed(CardType.MASTERCARD),
        "Master Card image should be present");

    Assert.assertTrue(paymentMethodPage.isCcImageDisplayed(CardType.VISA),
        "Visa image should be present");
  }

  @Test
  public void validateIfCardImagesAreNotDisplayed() {
    Logger.info("To validate Visa, Master Card, Discover and Amex image is NOT displayed next to "
        + "Debit/Credit card");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc8");

    Assert.assertFalse(paymentMethodPage.isCcImageDisplayed(CardType.AMEX),
        "AMEX logo should not be present in the payment method page");

    Assert.assertFalse(paymentMethodPage.isCcImageDisplayed(CardType.DISCOVER),
        "Discover logo should not be present in the payment method page");

    Assert.assertFalse(paymentMethodPage.isCcImageDisplayed(CardType.MASTERCARD),
        "Mastercard logo should not be present in the payment method page");

    Assert.assertFalse(paymentMethodPage.isCcImageDisplayed(CardType.VISA),
        "Visa logo should not be present in the payment method page");
  }

  @Test
  public void validateBankAccountIsAvailableAndDebitCreditIsNotAvailable() {
    Logger.info(
        "To validate bank account payment method is available and debit/credit method is not "
            + "available");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc9");

    Assert.assertTrue(paymentMethodPage.isPaymentMethodPresent(PaymentMethodPage.NEW_BANK),
        "New Bank payment should be present in the payment method page");

    Assert.assertFalse(paymentMethodPage.isPaymentMethodPresent(PaymentMethodPage.NEW_CREDIT),
        "New Card payment should not be present in the payment method page");
  }

  @Test
  public void validateBankAccountIsNotAvailableAndDebitCreditIsAvailable() {
    Logger.info(
        "To validate bank account payment method is NOT available and debit/credit is available");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc10");

    Assert.assertFalse(paymentMethodPage.isPaymentMethodPresent(PaymentMethodPage.NEW_BANK),
        "New Bank payment should not be present in the payment method page");

    Assert.assertTrue(paymentMethodPage.isPaymentMethodPresent(PaymentMethodPage.NEW_CREDIT),
        "New Card payment should be present in the payment method page");
  }

  @Test
  public void validateIfDebitCreditLabelShowsDebitCreditCardAndBankShowsBankAccounteCheck() {
    Logger.info(
        "To validate that debit/credit label shows 'Debit/Credit' and Bank Account shows "
            + "'Bank Account (e-check)' for PM's in Group B");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc11");

    Assert.assertEquals(paymentMethodPage.getPaymentMethodLabel(PaymentMethodPage.NEW_CREDIT),
        "Debit/Credit Card", "Debit/Credit Card should be displayed as card label");

    Assert.assertEquals(paymentMethodPage.getPaymentMethodLabel(PaymentMethodPage.NEW_BANK),
        "Bank Account (e-check)", "Bank Account (e-check) should be displayed as bank label");
  }

  @Test
  public void validateExpressPayIsUnavailableForPaymentMethods() {
    Logger.info(
        "To validate express pay option is NOT included for Bank Account or Debit/Credit Card "
            + "options");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc12");

    Assert.assertFalse(paymentMethodPage.isExpressPayPresent(PaymentMethodPage.NEW_BANK),
        "Express pay for Bank should not be present in the payment method page");

    Assert.assertFalse(paymentMethodPage.isExpressPayPresent(PaymentMethodPage.NEW_CREDIT),
        "Express pay for Card should not be present in the payment method page");
  }

  @Test
  public void validateExpressPayIsAvailableForPaymentMethods() {
    Logger.info(
        "To validate express pay option is included for Bank Account and Debit/Credit Card "
            + "options");

    PaymentMethodPage paymentMethodPage = testPrepGeneral("tc13");

    Assert.assertTrue(paymentMethodPage.isExpressPayPresent(PaymentMethodPage.NEW_BANK),
        "Express pay for Bank should be present in the payment method page");

    Assert.assertTrue(paymentMethodPage.isExpressPayPresent(PaymentMethodPage.NEW_CREDIT),
        "Express pay for Cards should be present in the payment method page");
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  private PaymentFlow testPrepExpressPay(String testCase, String methodToSelect) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(methodToSelect);
    paymentMethodPage.setExpressPayCheckbox(methodToSelect);
    paymentMethodPage.clickContinueButton();

    return paymentFlow;
  }

  private PaymentMethodPage testPrepGeneral(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    return new PaymentMethodPage();
  }
}

