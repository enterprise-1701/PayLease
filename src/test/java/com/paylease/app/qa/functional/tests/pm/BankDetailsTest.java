package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BankDetailsTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "BankDetails";

  //--------------------------------BANK DETAILS TESTS----------------------------------------------

  @Test
  public void bankDetailsPmTc1() {
    Logger.info("To validate that an error message appears on the Bank Details page when "
        + "Name on Account is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = verifyErrorMessage(
        BankAccountDetailsPage.NAME_ON_ACCOUNT_BOX, "tc1");

    Assert.assertTrue(bankAccountDetailsPage.getErrorNameOnAccount()
        .equals("Account Name is required"), "Account name is required message should show");
  }

  @Test
  public void bankDetailsPmTc2() {
    Logger.info("To validate that an error message appears on the Bank Details page when "
        + "Bank Name is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = verifyErrorMessage(
        BankAccountDetailsPage.BANK_NAME_BOX, "tc2");

    Assert.assertTrue(bankAccountDetailsPage.getErrorBankName()
        .equals("Bank Name is required"), "Bank Name is required message should show");
  }

  @Test
  public void bankDetailsPmTc3() {
    Logger.info("To validate that an error message appears on the Bank Details page when "
        + "Account type is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = verifyErrorMessage(
        BankAccountDetailsPage.ACCOUNT_TYPE_SELECT, "tc3");

    Assert.assertTrue(bankAccountDetailsPage.getErrorAccountType()
        .equals("Account Type is required"), "Account Type is required message should show");
  }

  @Test
  public void bankDetailsPmTc4() {
    Logger.info("To validate that an error message appears on the Bank Details page when "
        + "Routing Number (9 digits): is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = verifyErrorMessage(
        BankAccountDetailsPage.ROUTING_NUMBER_BOX, "tc4");

    Assert.assertTrue(bankAccountDetailsPage.getErrorRoutingNumber()
        .equals("Routing Number is required"), "Routing Number is required message should "
        + "show");
  }

  @Test
  public void bankDetailsPmTc5() {
    Logger.info("To validate that an error message appears on the Bank Details page when "
        + "Account Number is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrep("tc5");

    bankAccountDetailsPage.fillAndSubmitWithoutAccAndConfAccNo();

    Assert.assertTrue(bankAccountDetailsPage.getErrorAccountNumber()
        .equals("Account Number is required"), "Account Number is required"
        + " message should show");

    Assert.assertTrue(bankAccountDetailsPage.getErrorConfirmAccountNumber()
        .equals("Confirm Account Number is required"), "Confirm Account Number is required"
        + " message should show");
  }

  @Test
  public void bankDetailsPmTc6() {
    Logger.info("To validate that an error message appears on the Bank Details page when "
        + "Confirm Account Number is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = verifyErrorMessage(
        BankAccountDetailsPage.CONFIRM_ACCOUNT_NUMBER_BOX, "tc6");

    Assert.assertTrue(bankAccountDetailsPage.getErrorConfirmAccountNumber()
        .equals("Confirm Account Number is required"), "Confirm Account Number is required"
        + " message should show");
  }

  @Test
  public void bankDetailsPmTc12() {
    Logger.info("To validate the 'Payment for' text");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String paymentFor = testSetupPage.getString("paymentFor");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    Assert.assertTrue(paymentFlow.getPaymentFor()
        .equalsIgnoreCase(paymentFor), "Payment for text should match");
  }

  @Test
  public void bankDetailsPmTc13() {
    Logger.info("To validate the 'Payment Amount' text");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc13");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String amount = testSetupPage.getString("amount");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    Assert.assertTrue(paymentFlow.getPaymentAmount().equals("$" + amount), "Amount should match");
  }

  @Test
  public void bankDetailsPmTc15() {
    Logger.info("To validate that bubble image is displayed for Routing Number");

    BankAccountDetailsPage bankAccountDetailsPage = testPrep("tc15");

    bankAccountDetailsPage.mouseHoverRoutingHelp();

    Assert.assertTrue(bankAccountDetailsPage.isRoutingNumberImageDisplayed(), "Routing number "
        + "image should display");
  }

  @Test
  public void bankDetailsPmTc16() {
    Logger.info("To validate that bubble image is displayed for Account Number");

    BankAccountDetailsPage bankAccountDetailsPage = testPrep("tc16");

    bankAccountDetailsPage.mouseHoverAccountHelp();

    Assert.assertTrue(bankAccountDetailsPage.isAccountNumberImageDisplayed(), "Account number "
        + "image should display");
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  private BankAccountDetailsPage testPrep(String testCase) {

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    return new BankAccountDetailsPage();
  }

  private BankAccountDetailsPage verifyErrorMessage(String excludedField, String testCase) {
    BankAccountDetailsPage bankAccountDetailsPage = testPrep(testCase);

    bankAccountDetailsPage.fillExcludeAndSubmitBank(excludedField);

    return bankAccountDetailsPage;
  }

}
