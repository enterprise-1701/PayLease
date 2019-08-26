package com.paylease.app.qa.functional.tests.integration;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TransactionProcessingPage;
import com.paylease.app.qa.framework.pages.guestpayment.GuestInformationPage;
import com.paylease.app.qa.framework.pages.guestpayment.ResidentInformationPage;
import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryReceiptPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryReceiptPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GuestPaymentTest extends ScriptBase {

  private static final String REGION = "integration";
  private static final String FEATURE = "AccGuestPayment";

  private static final String VERIFY_ERROR_MESSAGE = "Cannot find a resident match, please contact the resident to verify their information. If information appears correct, please have the resident contact their management office for further assistance.";

  //--------------------------------GUEST PAYMENT TESTS-----------------------------------------

  @Test
  public void verifyCustomizationAndRequiredFields() {
    Logger.info("Verify Customization and Required Fields");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");

    ResidentInformationPage residentInformationPage = new ResidentInformationPage(pmId);

    residentInformationPage.open();

    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(
        residentInformationPage.getErrorPropertyBox().contains("Property Name is required"),
        "Error must be displayed if the user is trying to navigate without inputting information");

    Assert
        .assertTrue(
            residentInformationPage.getErrorFirstNameBox().contains("First Name is required"),
            "Error must be displayed if the user is trying to navigate without inputting information");

    Assert
        .assertTrue(residentInformationPage.getErrorLastNameBox().contains("Last Name is required"),
            "Error must be displayed if the user is trying to navigate without inputting information");

    Assert
        .assertTrue(residentInformationPage.getErrorDobBox().contains("Date of Birth is required"),
            "Error must be displayed if the user is trying to navigate without inputting information");
  }

  @Test
  public void propertySearch() {
    Logger.info("Search property to make Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc02");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String propertyName = testSetupPage.getString("propertyName");

    ResidentInformationPage residentInformationPage = new ResidentInformationPage(pmId);

    residentInformationPage.open();

    String searchString = propertyName.substring(0, Math.min(propertyName.length(), 3));
    String propertySearchResult = residentInformationPage.searchProperty(searchString);

    Assert.assertTrue(propertySearchResult.contains(propertyName),
        "Search box should return matching property name");
  }

  @Test
  public void propertySearchNegative() {
    Logger.info("Search property to make Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc03");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String propId = testSetupPage.getString("propId");
    final String altPmPropertyName = testSetupPage.getString("altPmProperty");

    ResidentInformationPage residentInformationPage = new ResidentInformationPage(pmId);

    residentInformationPage.open();

    Assert.assertFalse(residentInformationPage.isPropertyPresent(altPmPropertyName, propId),
        "Property should not be present as different search term is used");
  }

  @Test
  public void verifyResident() {
    Logger.info("Verify property, DOB, first and Last Name");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc04");
    testSetupPage.open();
    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    GuestInformationPage guestInformationPage = residentInformationPage.clickContinueButton();

    Assert.assertTrue(guestInformationPage.pageIsLoaded(), "Guest information page did not load");
  }

  @Test
  public void verifyWrongDoBEntry() {
    Logger.info("Verify Wrong DOB entry");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc06");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String propertyName = testSetupPage.getString("propertyName");
    final String firstName = testSetupPage.getString("firstName");
    final String lastName = testSetupPage.getString("lastName");

    ResidentInformationPage residentInformationPage = new ResidentInformationPage(pmId);

    residentInformationPage.open();
    residentInformationPage.enterPropertyName(propertyName);
    residentInformationPage.enterResidentFirstName(firstName);
    residentInformationPage.enterResidentLastName(lastName);
    residentInformationPage.enterResidentDob("1859", "01", "01");
    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(residentInformationPage.getErrorMessage().contains(VERIFY_ERROR_MESSAGE),
        "In case of Blocked resident the error message must display");
  }

  @Test
  public void blockedResident() {
    Logger.info("Verify if a resident is Blocked");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc07");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(residentInformationPage.getErrorMessage().contains(VERIFY_ERROR_MESSAGE),
        "In case of Blocked resident the error message must display");
  }

  @Test
  public void verifyInactiveStatus() {
    Logger.info("Verify Resident has MRI Co-Resident status");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc13");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(residentInformationPage.getErrorMessage().contains(VERIFY_ERROR_MESSAGE),
        "In case of Blocked resident the error message must display");
  }

  @Test
  public void verifyImportantMessageOnResidentInformationPage() {
    Logger.info("Verify Important message on Make Guest Payment Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc24");
    testSetupPage.open();

    final String pmId = testSetupPage.getString("pmId");

    ResidentInformationPage residentInformationPage = new ResidentInformationPage(pmId);

    residentInformationPage.open();

    Assert.assertTrue(residentInformationPage.importantMessageInResident().contains(
        "IMPORTANT: If you are making a payment towards a Joint* lease you must enter the Primary/Head of Household as the Resident. Guests will not be able to make payments for other/co-residents. Contact the leasing office if you need assistance."),
        "Guest payer must see the disclaimer before proceeding");

    Assert.assertTrue(residentInformationPage.importantMessageInResident()
            .contains("*A Joint lease has multiple residents with a shared financial responsibility."),
        "Guest payer must see the disclaimer before proceeding");
  }

  @Test
  public void verifyRequiredFields() {
    Logger.info("Verify Required Fields on Guest Information Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "gi_tc2");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    GuestInformationPage guestInformationPage = residentInformationPage.clickContinueButton();

    guestInformationPage.clickContinueButtonToVerify();

    Assert
        .assertTrue(guestInformationPage.getErrorFirstNameBox().contains("First Name is required"),
            "Error must me displayed if the user is trying to navigate without inputting information");

    Assert.assertTrue(guestInformationPage.getErrorLastNameBox().contains("Last Name is required"),
        "Error must me displayed if the user is trying to navigate without inputting information");

    Assert.assertTrue(guestInformationPage.getErrorEmailBox().contains("Email is required"),
        "Error must me displayed if the user is trying to navigate without inputting information");
  }

  @Test
  public void verifyPayersInformation() {
    Logger.info("Verify Payer's Information");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "gi_tc3");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    GuestInformationPage guestInformationPage = residentInformationPage.clickContinueButton();

    guestInformationPage.enterGuestFirstName();
    guestInformationPage.enterGuestLastName();
    guestInformationPage.enterGuestEmail();

    guestInformationPage.clickContinueButton();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME);
    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_AMOUNT,
        "Payment amount page should load");
  }

  @Test
  public void verifyInvalidEmail() {
    Logger.info("Verify invalid Email");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "gi_tc4");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    GuestInformationPage guestInformationPage = residentInformationPage.clickContinueButton();

    guestInformationPage.enterGuestFirstName();
    guestInformationPage.enterGuestLastName();
    guestInformationPage.enterInvalidEmail();
    guestInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(
        guestInformationPage.getErrorEmailBox().contains("Please enter a valid email address"),
        "Invalid email error must me displayed if the user is trying to navigate with invalid email");
  }

  @Test
  public void verifyImportantMessageOnGuestInformationPage() {
    Logger.info("Verify Important message on Make Guest Payment Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "gi_tc5");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    GuestInformationPage guestInformationPage = residentInformationPage.clickContinueButton();

    Assert.assertTrue(guestInformationPage.getImportantMessageInGuest().contains(
        "IMPORTANT: If you are making a payment towards a Joint* lease you must enter the Primary/Head of Household as the Resident. Guests will not be able to make payments for other/co-residents. Contact the leasing office if you need assistance."),
        "Guest payer must see the disclaimer before proceeding");

    Assert.assertTrue(guestInformationPage.getImportantMessageInGuest()
            .contains("*A Joint lease has multiple residents with a shared financial responsibility."),
        "Guest payer must see the disclaimer before proceeding");
  }

  /**
   * This test method is not currently running because it is failing. Please re-add the Test
   * annotation with ticket DEV-40808
   */
  public void verifyPaymentAmountField() {
    Logger.info("Verify Payment Amount Field");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "pa_tc4");
    testSetupPage.open();

    final String amount = testSetupPage.getString("amount");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertTrue(paymentAmountPage.getPaymentFieldAmount("Guest Payment").equals(amount),
        "Should have the same amount");

    paymentAmountPage.setPaymentFieldAmount("Guest Payment", "1025");

    Assert.assertTrue(paymentAmountPage.getPaymentFieldAmount("Guest Payment").equals("1025"),
        "Should have the same amount");
  }

  @Test
  public void verifyNavigationToPaymentMethodPage() {
    Logger.info("Verify Navigation to Payment Method Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "pm_tc1");
    testSetupPage.open();

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME);
    paymentFlow.openStep(PaymentFlow.STEP_AMOUNT);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    paymentAmountPage.setPaymentFieldAmount("Guest Payment", "1025");
    paymentAmountPage.clickContinueButton();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_METHOD,
        "Should be on Payment Method page");
  }

  @Test
  public void verifyAchDetails() {
    Logger.info("Verify ACH Details");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "pm_tc7");
    testSetupPage.open();

    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);

    BankAccountDetailsPage bankAccountDetailsPage = new BankAccountDetailsPage();
    bankAccountDetailsPage.fillBankDetailsAndSubmit();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Review and Submit page should load");
  }

  @Test(groups = {"litle"})
  public void pmTc8Cc() {
    verifyCardDetails("pm_tc8_credit", true);
  }

  @Test(groups = {"litle"})
  public void pmTc8Dc() {
    verifyCardDetails("pm_tc8_debit", false);
  }

  @Test
  public void verifyPaymentConfirmationMessage() {
    Logger.info("Verify Payment Confirmation Message");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc1");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String amount = testSetupPage.getString("amount");
    final String guestName = testSetupPage.getString("guestName");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    double revisedAmount = Double.parseDouble(amount);
    DecimalFormat formatter = new DecimalFormat("#,###.00");
    String newAmount = formatter.format(revisedAmount);

    SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd-yyyy");
    Date date = new Date();
    String dateToday = formatDate.format(date);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertTrue(reviewAndSubmitPage.getPaymentAgreementMessage().contains("I, " + guestName
        + ", confirm that the payment information below is correct and authorize PayLease on "
        + dateToday + " to debit the account below for $" + newAmount + "."));
  }

  @Test
  public void verifyNavigationToReceiptPage() {
    Logger.info("Verify Navigation to Receipt Page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc9");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_REVIEW);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    reviewAndSubmitPage.clickSubmitButton();
    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_RECEIPT,
        "Guest Receipt Page should load");
  }

  @Test
  public void verifyIfAchDetailsSavedInResUi() {
    Logger.info("Verify ACH Details");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc11");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String residentSsoUrl = testSetupPage.getString("residentSsoUrl", "Resident SSO Url");

    DataHelper dataHelper = new DataHelper();

    int accountNumber = dataHelper.getAccountNumber();
    String guestAccountNumber = Integer.toString(accountNumber);

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_NEW_BANK);
    paymentFlow.setAccountNumber(guestAccountNumber);
    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);

    DriverManager.getDriver().get(residentSsoUrl);

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();

    String parsedGuestBankAccountNo = guestAccountNumber.substring(guestAccountNumber.length() - 4);

    Assert
        .assertFalse(residentAccountsPage.getBankAccountsTable().contains(parsedGuestBankAccountNo),
            "The guest bank account should not be saved in Resident UI account");
  }

  @Test(groups = {"litle"})
  public void revTc12Cc() {
    verifyIfCardDetailsSavedInResUi("rev_tc12_credit", true);
  }

  @Test(groups = {"litle"})
  public void revTc12Dc() {
    verifyIfCardDetailsSavedInResUi("rev_tc12_debit", false);
  }

  @Test
  public void verifyReceiptContent() {
    Logger.info("Verify the receipt content");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc13");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String guestName = testSetupPage.getString("guestName");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertTrue(receiptPage.getPaymentMadeBy().contains(guestName),
        "Guest name should match");
  }

  @Test
  public void verifyReceiptData() {
    Logger.info("Verify receipt data");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc14");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String residentName = testSetupPage.getString("residentName");
    final String guestName = testSetupPage.getString("guestName");
    final String unitAmount = testSetupPage.getString("unitAmount", "Amount");
    final String feeAmount = testSetupPage.getString("feeAmount", "Fee Amount");
    final String totalAmount = testSetupPage.getString("totalAmount", "Total Amount");
    final String bankAccountName = testSetupPage.getString("bankAccountName");
    final String bankAccountNumber = testSetupPage.getString("bankAccountNumber");
    final String residentUnit = testSetupPage.getString("residentUnit");
    final String pmName = testSetupPage.getString("pmName");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    double revisedUnitAmount = Double.parseDouble(unitAmount);
    DecimalFormat formatter = new DecimalFormat("#,##0.00");
    String newUnitAmount = formatter.format(revisedUnitAmount);

    double revisedFeeAmount = Double.parseDouble(feeAmount);
    String newFeeAmount = formatter.format(revisedFeeAmount);

    double revisedTotalAmount = Double.parseDouble(totalAmount);
    String newTotalAmount = formatter.format(revisedTotalAmount);

    String newBankAccountNo = bankAccountNumber.substring(bankAccountNumber.length() - 4);

    Assert.assertTrue(receiptPage.getSuccessMessage().equals("Your payment has been processed."),
        "Payment processed message should display");
    Assert.assertTrue(receiptPage.getResidentName().equals(residentName),
        "Resident Name should match");
    Assert.assertTrue(receiptPage.getPaymentMadeBy().equals(guestName), "Guest name should match");
    Assert.assertTrue(receiptPage.guestPaymentAmount().equals("$" + newUnitAmount),
        "Guest Payment amount should be the same");
    Assert.assertTrue(receiptPage.getProcessingFeeAmount().equals("$" + newFeeAmount),
        "Processing fee should be the same");
    Assert.assertTrue(receiptPage.getTotalAmount().equals("$" + newTotalAmount),
        "Total should be the same");
    Assert.assertTrue(
        receiptPage.getPaymentMethod().equals(bankAccountName + " - #" + newBankAccountNo),
        "Bank Account Name and Number should be the same");
    Assert.assertTrue(receiptPage.getPmName().equals(pmName), "PM should be the same");
    Assert.assertTrue(receiptPage.getUnitNumber().equals(residentUnit),
        "Resident Unit number should be the same");
  }

  @Test
  public void verifyReceiptResidentElements() {
    Logger.info(
        "Verify if return home, logout button and Welcome Resident text are present in Guest receipt");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc15");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String residentName = testSetupPage.getString("residentName");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertFalse(receiptPage.isWelcomeTextPresent(residentName),
        "Welcome Resident text should not be present in Guest receipt page");
    Assert.assertFalse(receiptPage.isLogoutButtonPresent(),
        "Logout button should not be present in Guest receipt page");
    Assert.assertFalse(receiptPage.isHomeLinkPresent(),
        "Return home link should not be present in Guest receipt page");
  }

  @Test
  public void verifyReceiptPresenceInResUi() {
    Logger.info("Verify if the Transaction/Receipt is present in Resident UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc16");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String residentSsoUrl = testSetupPage.getString("residentSsoUrl", "Resident SSO Url");

    DriverManager.getDriver().get(residentSsoUrl);

    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();

    resPaymentHistoryPage.open();

    ResPaymentHistoryReceiptPage residentPaymentHistoryReceiptPage = resPaymentHistoryPage
        .clickDetails(transactionId);

    Assert.assertTrue(residentPaymentHistoryReceiptPage.getTransactionId().contains(transactionId),
        "Transaction ID should match");
  }

  @Test
  public void verifyReceiptPresenceInPmUi() {
    Logger.info("Verify if the Transaction/Receipt is present in PM UI");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rev_tc17");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String pmEmail = testSetupPage.getString("username", "PM email");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();

    pmPaymentHistoryPage.open(true);

    PmPaymentHistoryReceiptPage pmPaymentHistoryReceiptPage = pmPaymentHistoryPage
        .clickTransactionNumber(transactionId);

    Assert.assertTrue(pmPaymentHistoryReceiptPage.getTransactionId().contains(transactionId),
        "Transaction ID should match");
  }

  /**
   * this test simulates making a guest payment for a resident that has duplicate records
   * with statuses Resident Current, Resident New, Resident Old, and Applicant Active.
   *
   * verify that the guest payment transaction is made by the resident with status Resident Current.
   */
  @Test
  public void verifyDuplicateResidentsUseResidentCurrentStatus() {
    Logger.info("Verify that when duplicate residents Resident Current status is used");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc01");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("residentId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Resident Current");
  }

  /**
   * this test simulates making a guest payment for a resident that has status Resident New.
   *
   * verify that the resident is able to make a guest payment transaction.
   */
  @Test
  public void verifyResidentNewStatus() {
    Logger.info("Verify that a resident with status Resident New can make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc02");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("residentId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Resident New");
  }

  /**
   * this test simulates making a guest payment for a resident that has status Applicant Active.
   *
   * verify that the resident is able to make a guest payment transaction.
   */
  @Test
  public void verifyApplicantActiveStatus() {
    Logger.info("Verify that a resident with status Applicant Active can make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc03");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("residentId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Applicant Active");
  }

  /**
   * this test simulates trying to make a guest payment for a resident that has duplicate records
   * with status Applicant Active.
   *
   * verify that the resident is not able to make a guest payment transaction.
   */
  @Test
  public void verifyDuplicateApplicantActiveStatusFails() {
    Logger.info("Verify that when there are duplicate residents with status Applicant Active, the most recent can make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc04");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("applicantActiveId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Applicant with most recent LastUpdateDay");
  }

  /**
   * this test simulates making a guest payment for a resident that has the status Resident Old.
   *
   * verify that the resident is able to make a guest payment transaction.
   */
  @Test
  public void verifyResidentOldStatus() {
    Logger.info("Verify that a resident with status Resident Old can make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc05");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("residentId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Resident Old");
  }

  /**
   * this test simulates making a guest payment for a resident that has duplicate records with
   * status Resident Old.
   *
   * verify that the resident with the most recent Resident Vacate Date is able to make a
   * guest payment transaction.
   */
  @Test
  public void verifyDuplicateResidentOldStatus() {
    Logger.info("Verify that duplicate residents with status Resident Old can make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc06");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("residentId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Resident Old with the most recent Resident Vacate Date");
  }

  /**
   * this test simulates trying to make a guest payment for a resident that has duplicate records
   * with status Resident Current.
   *
   * verify that the resident is not able to make a guest payment transaction.
   */
  @Test
  public void verifyDuplicateResidentCurrentStatusFails() {
    Logger.info("Verify that duplicate residents with status Resident Current can not make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc07");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);
    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(residentInformationPage.getErrorMessage().contains(VERIFY_ERROR_MESSAGE),
        "Failed to verify that duplicate residents with status Resident Current can not make a Guest Payment");
  }

  /**
   * this test simulates trying to make a guest payment for a resident that has duplicate records
   * with status Resident New.
   *
   * verify that the resident is not able to make a guest payment transaction.
   */
  @Test
  public void verifyDuplicateResidentNewStatusFails() {
    Logger.info("Verify that duplicate residents with status Resident New can not make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc08");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);
    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(residentInformationPage.getErrorMessage().contains(VERIFY_ERROR_MESSAGE),
        "Failed to verify that duplicate residents with status Resident New can not make a Guest Payment");
  }

  /**
   * this test simulates making a guest payment for a resident that has the status Applicant Inactive.
   *
   * verify that the resident is not able to make a guest payment transaction.
   */
  @Test
  public void verifyApplicantInactiveStatusFails() {
    Logger.info("Verify that duplicate residents with status Applicant Inactive can not make a Guest Payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc09");
    testSetupPage.open();

    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);
    residentInformationPage.clickContinueButtonToVerify();

    Assert.assertTrue(residentInformationPage.getErrorMessage().contains(VERIFY_ERROR_MESSAGE),
        "Failed to verify that duplicate residents with status Applicant Inactive can not make a Guest Payment");
  }

  /**
   * this test simulates making a guest payment for a resident that has duplicate records
   * with statuses Resident New and Applicant Active.
   *
   * verify that the guest payment transaction is made by the resident with status Resident New.
   */
  @Test
  public void verifyDuplicateResidentsUseResidentNewStatus() {
    Logger.info("Verify that when duplicate residents Resident New status is used");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "hierarchy_tc10");
    testSetupPage.open();
    String expectedFromId = testSetupPage.getString("residentId");

    String transId = initializeGuestPayment(testSetupPage);

    String fromId = getTransactionFromId(transId);

    Assert.assertEquals(fromId, expectedFromId, "Failed to verify transaction made by Resident New");
  }

  //--------------------------------METHODS-----------------------------------------------------

  /**
   * Return the user id that made the given transaction.
   *
   * @param transId
   * @return
   */
  private String getTransactionFromId(String transId) {
    TransactionPage transactionPage = new TransactionPage();
    transactionPage.open();
    TransactionProcessingPage transactionProcessingPage = transactionPage.getDataForTransactionId(transId);

    return transactionProcessingPage.getFromId();
  }

  /**
   * Initializes a guest payment based on the given test data, stopping at the Payment Method page,
   * and returns the generated transaction id.
   *
   * @param testSetupPage
   * @return
   */
  private String initializeGuestPayment(TestSetupPage testSetupPage) {
    Logger.info("Initializing Guest Payment");

    // enter the resident information
    ResidentInformationPage residentInformationPage = enterResidentInfo(testSetupPage);

    // enter the guest information
    GuestInformationPage guestInformationPage = residentInformationPage.clickContinueButton();

    enterGuestInfo(guestInformationPage);
    guestInformationPage.clickContinueButton();

    // enter the payment amount and continue to the payment method step
    Faker faker = new Faker();
    int amount = faker.number().numberBetween(500, 2500);

    PaymentFlow paymentFlow = new PaymentFlow();
    paymentFlow.addAmount("Guest Payment", Integer.toString(amount));
    paymentFlow.advanceToStep(PaymentFlow.STEP_METHOD);

    return paymentFlow.getPaymentId();
  }

  private void verifyCardDetails(String testCase, boolean useCredit) {
    Logger.info("Verify Credit and Debit Card details ");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    final String transactionId = testSetupPage.getString("transactionId");

    String cardType;
    int payFlowStep;
    if (useCredit) {
      cardType = CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID;
      payFlowStep = PaymentFlow.STEP_NEW_CREDIT;
    } else {
      cardType = CardAccountDetailsPage.CARD_TYPE_DEBIT_VALID;
      payFlowStep = PaymentFlow.STEP_NEW_DEBIT;
    }

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST,
        PaymentFlow.SCHEDULE_ONE_TIME, transactionId);
    paymentFlow.openStep(payFlowStep);

    CardAccountDetailsPage cardAccountDetailsPage = new CardAccountDetailsPage();

    cardAccountDetailsPage.prepCardType(cardType);
    cardAccountDetailsPage.fillAndSubmitCardDetails();

    Assert.assertEquals(paymentFlow.getCurrentStep(), PaymentFlow.STEP_REVIEW,
        "Review and Submit page should load");
  }

  private void verifyIfCardDetailsSavedInResUi(String testCase, boolean useCredit) {
    Logger.info("Verify Card Details");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String residentSsoUrl = testSetupPage.getString("residentSsoUrl", "Resident SSO Url");

    int payFlowStep;
    String guestAccountNumber;
    if (useCredit) {
      payFlowStep = PaymentFlow.STEP_NEW_CREDIT;
      guestAccountNumber = CardAccountDetailsPage.VALID_CREDIT;
    } else {
      payFlowStep = PaymentFlow.STEP_NEW_DEBIT;
      guestAccountNumber = CardAccountDetailsPage.VALID_DEBIT;
    }

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_GUEST,
        PaymentFlow.SCHEDULE_ONE_TIME, transactionId);
    paymentFlow.openStep(payFlowStep);
    paymentFlow.setAccountNumber(guestAccountNumber);
    paymentFlow.advanceToStep(PaymentFlow.STEP_RECEIPT);

    DriverManager.getDriver().get(residentSsoUrl);

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();

    Assert.assertFalse(residentAccountsPage.getCardAccountsTable().contains(guestAccountNumber),
        "The guest card account should not be saved in Resident UI account");
  }

  /**
   * Populate the guest information page with random information.
   *
   * @param guestInformationPage
   */
  private void enterGuestInfo(GuestInformationPage guestInformationPage) {
    guestInformationPage.enterGuestFirstName();
    guestInformationPage.enterGuestLastName();
    guestInformationPage.enterGuestEmail();
  }

  /**
   * Populate the guest fields based on the given test data.
   *
   * @param testSetupPage
   * @return ResidentInformationPage
   */
  private ResidentInformationPage enterResidentInfo(TestSetupPage testSetupPage) {
    final String pmId = testSetupPage.getString("pmId");
    final String propertyName = testSetupPage.getString("propertyName");
    final String firstName = testSetupPage.getString("firstName");
    final String lastName = testSetupPage.getString("lastName");
    final String yob = testSetupPage.getString("dateOfBirthYear", "Birth year");
    final String mob = testSetupPage.getString("dateOfBirthMonth", "Birth month");
    final String dob = testSetupPage.getString("dateOfBirthDay", "Birth date");

    ResidentInformationPage residentInformationPage = new ResidentInformationPage(pmId);

    residentInformationPage.open();
    residentInformationPage.enterPropertyName(propertyName);
    residentInformationPage.enterResidentFirstName(firstName);
    residentInformationPage.enterResidentLastName(lastName);
    residentInformationPage.enterResidentDob(yob, mob, dob);

    return residentInformationPage;
  }
}
