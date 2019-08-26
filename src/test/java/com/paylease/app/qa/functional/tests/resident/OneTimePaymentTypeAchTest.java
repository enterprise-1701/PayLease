package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OneTimePaymentTypeAchTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "oneTimePaymentTypeAch";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    Logger.info("To validate that an error message appears on the Bank Details page when Name on Account is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = excludeField(
        BankAccountDetailsPage.NAME_ON_ACCOUNT_BOX);

    Assert.assertTrue(bankAccountDetailsPage.getErrorNameOnAccount()
        .equals("Account Name is required"), "Account name is required message should show");
  }

  @Test
  public void tc2() {
    Logger.info("To validate that an error message appears on the Bank Details page when Bank Name is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = excludeField(
        BankAccountDetailsPage.BANK_NAME_BOX);

    Assert.assertTrue(bankAccountDetailsPage.getErrorBankName()
        .equals("Bank Name is required"), "Bank Name is required message should show");
  }

  @Test
  public void tc3() {
    Logger.info("To validate that an error message appears on the Bank Details page when Account type is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = excludeField(
        BankAccountDetailsPage.ACCOUNT_TYPE_SELECT);

    Assert.assertTrue(bankAccountDetailsPage.getErrorAccountType()
        .equals("Account Type is required"), "Account Type is required message should show");
  }

  @Test
  public void tc4() {
    Logger.info(
        "To validate that an error message appears on the Bank Details page when Routing Number (9 digits): is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = excludeField(
        BankAccountDetailsPage.ROUTING_NUMBER_BOX);

    Assert.assertTrue(bankAccountDetailsPage.getErrorRoutingNumber()
        .equals("Routing Number is required"), "Routing Number is required message should show");
  }

  @Test
  public void tc5() {
    Logger.info(
        "To validate that an error message appears on the Bank Details page when Confirm Account Number is left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = excludeField(
        BankAccountDetailsPage.CONFIRM_ACCOUNT_NUMBER_BOX);

    Assert.assertTrue(bankAccountDetailsPage.getErrorConfirmAccountNumber()
            .equals("Confirm Account Number is required"),
        "Confirm Account Number is required message should show");
  }

  @Test
  public void tc6() {
    Logger.info(
        "To validate that an error message appears on the Bank Details page when Account Number and Confirm Account Number do not match.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.setAndConfirmBankAccountNumberNotMatching();
    bankAccountDetailsPage.fillAndSubmitWithoutAccAndConfAccNo();

    Assert.assertTrue(bankAccountDetailsPage.getErrorConfirmAccountNumber()
            .equals("Account Number does not match"),
        "Account Number does not match message should show");
  }

  @Test
  public void tc7() {
    Logger.info("To validate that an error message appears on the Bank Details page when Name on Account does not fulfill the minimum number of characters requirement.");

    BankAccountDetailsPage bankAccountDetailsPage = fillAndSubmitLessThanMinChars(
        BankAccountDetailsPage.NAME_ON_ACCOUNT_BOX, 2);

    Assert.assertTrue(bankAccountDetailsPage.getErrorNameOnAccountCharsRequirement()
            .equals("Name must be between 2 and 60 characters"),
        "Name must be between 2 and 60 characters should show");
  }

  @Test
  public void tc8() {
    Logger.info(
        "To validate that an error message appears on the Bank Details page when Bank Name does not fulfill the minimum number of characters requirement");

    BankAccountDetailsPage bankAccountDetailsPage = fillAndSubmitLessThanMinChars(
        BankAccountDetailsPage.BANK_NAME_BOX, 3);

    Assert.assertTrue(bankAccountDetailsPage.getErrorBankName()
            .equals("Bank Name must be between 3 and 50 characters"),
        "Bank Name must be between 3 and 50 characters should show");
  }

  @Test
  public void tc9() {
    Logger.info("To validate that an error message appears on the Bank Details page when Routing Number is invalid.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.fillAndSubmitWithInvalidRoutingNumber();

    Assert
        .assertTrue(bankAccountDetailsPage.getErrorRoutingNumber().equals("Invalid routing number"),
            "Invalid routing number message should show");
  }

  @Test
  public void tc10() {
    Logger.info("To validate that expected error messages appear on the Bank Details page when all fields are left blank.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.submitAllBankDetailsBlank();

    Assert.assertTrue(
        bankAccountDetailsPage.getErrorNameOnAccount().equals("Account Name is required"),
        "Account Name is required message should show");

    Assert.assertTrue(bankAccountDetailsPage.getErrorBankName().equals("Bank Name is required"),
        "Bank Name is required message should show");

    Assert
        .assertTrue(bankAccountDetailsPage.getErrorAccountType().equals("Account Type is required"),
            "Account Type is required message should show");

    Assert.assertTrue(
        bankAccountDetailsPage.getErrorRoutingNumber().equals("Routing Number is required"),
        "Routing Number is required message should show");

    Assert.assertTrue(
        bankAccountDetailsPage.getErrorAccountNumber().equals("Account Number is required"),
        "Account Number is required message should show");

    Assert.assertTrue(bankAccountDetailsPage.getErrorConfirmAccountNumber()
            .equals("Confirm Account Number is required"),
        "Confirm Account Number is required message should show");
  }

  @Test
  public void tc11() {
    Logger.info("To validate that the 'Payment for' text displays.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");
    final String paymentForText = testSetupPage.getString("paymentForText");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME,
        transId);

    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    Assert.assertTrue(paymentFlow.getPaymentFor().equalsIgnoreCase(paymentForText),
        "Text 'Lease payment, Payment amount' should show");
  }

  @Test
  public void tc12() {
    Logger.info("To validate that the Payment Amount field shows the correct amount.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");
    final String totalPaymentAmount = testSetupPage.getString("totalPaymentAmt");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME,
        transId);

    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    Assert.assertTrue(paymentFlow.getPaymentAmount().equals(totalPaymentAmount), "Incorrect Payment Amount");
  }

  @Test
  public void tc13() {
    Logger.info("To validate that resident can fill bank details and successfully navigate to "
        + "review and submit page.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.fillBankDetailsAndSubmit();

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertTrue(reviewAndSubmitPage.pageIsLoaded());
  }

  @Test
  public void tc14() {
    Logger.info("To validate that bubble image is displayed for Routing Number.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.mouseHoverRoutingHelp();

    Assert.assertTrue(bankAccountDetailsPage.isRoutingNumberImageDisplayed(), "Routing number image should display");
  }

  @Test
  public void tc15() {
    Logger.info("To validate that bubble image is displayed for Account Number.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.mouseHoverAccountHelp();

    Assert.assertTrue(bankAccountDetailsPage.isAccountNumberImageDisplayed(), "Account number image should display");
  }

  @Test
  public void tc16() {
    Logger.info("To validate the text 'All fields are required' message is displayed.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    Assert.assertTrue(bankAccountDetailsPage.getFootnoteText().equals("* All fields are required"), "All fields are required message should show");
  }


  @Test
  public void tc17() {
    Logger.info("To validate that the help box will redirect the user to the resident FAQ page.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    Assert.assertTrue(bankAccountDetailsPage.getFaqUrl().contains(AppConstant.FAQ_LINK),
        "PayLease support page should load");
  }

  @Test
  public void tc18() {
    Logger.info("To validate that the resident can click on Previous and be taken back to payment amount page.");

    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.clickPreviousButton();

    bankAccountDetailsPage.clickYesConfirmNavigation();

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertTrue(paymentMethodPage.pageIsLoaded(), "Payment Methods page not loaded.");
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private BankAccountDetailsPage testPrepGeneral() {

    PaymentFlow paymentFlow = testPrepPaymentFlow("otpAchSetup");

    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    return new BankAccountDetailsPage();
  }

  private PaymentFlow testPrepPaymentFlow(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME,
        transId);

    return paymentFlow;
  }

  private BankAccountDetailsPage excludeField(String excludedField) {
    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage.fillExcludeAndSubmitBank(excludedField);

    return bankAccountDetailsPage;
  }

  private BankAccountDetailsPage fillAndSubmitLessThanMinChars(String field, int minChars) {
    BankAccountDetailsPage bankAccountDetailsPage = testPrepGeneral();

    bankAccountDetailsPage
        .fillAndSubmitLessThanMinimumCharacters(field, minChars);

    return bankAccountDetailsPage;
  }
}
