package com.paylease.app.qa.functional.tests.pm;

import static com.paylease.app.qa.framework.AppConstant.CHECK_IMAGE_BASE_URL;

import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.DecryptedDataPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReceiptPage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryReceiptPage;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReceiptTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "Receipt";

  //--------------------------------RECEIPT TESTS---------------------------------------------------

  @Test
  public void validateTransactionIdIsDisplayed() {
    Logger.info("To validate that transaction number for payment is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);

    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getTransactionId(), transactionId,
        "Transaction id should be displayed");
  }

  @Test
  public void validateTransactionStatusIsProcessing() {
    Logger.info("To validate that Transaction status is shown as processing");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);

    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getTransactionStatus(), "Processing",
        "Transaction status should show as processing");
  }

  @Test
  public void validateResidentNameIsPresent() {
    Logger.info("To validate that Resident's Name is present on the receipt");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String residentFirstName = testSetupPage.getString("residentFirstName");
    final String residentLastName = testSetupPage.getString("residentLastName");

    String residentName = residentFirstName + " " + residentLastName;

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getResidentName(), residentName,
        "Resident name should be present");
  }

  @Test
  public void validateAccountNumberIsPresentOnTheReceipt() {
    Logger.info("To validate that account number is listed on receipt");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String accountNumber = testSetupPage.getString("accountNumber");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getAccountNumber(), accountNumber,
        "Account number should be listed on the receipt");
  }

  @Test
  public void validateDateAndTimeIsCorrect() throws ParseException {
    Logger.info("To validate that date and time displayed for processing time is correct");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String transactionDate = testSetupPage.getString("transactionDate");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    SimpleDateFormat givenFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy h:mm aa");

    String formattedTransactionDate = newFormat.format(givenFormat.parse(transactionDate))
        .toLowerCase();

    Assert.assertEquals(receiptPage.getTransactionDate(), formattedTransactionDate,
        "Date should match");
  }

  @Test
  public void validateClickingOnPrintReceiptButtonAllowsToPrintReceipt() {
    Logger.info("To validate that Print Receipt allows the PM to print copy of receipt");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    receiptPage.clickPrintReceiptButton();

    PmPaymentHistoryReceiptPage pmPaymentHistoryReceiptPage = new PmPaymentHistoryReceiptPage(
        transactionId);

    WebDriver driver = DriverManager.getDriver();

    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);

      if (driver.getCurrentUrl().startsWith(pmPaymentHistoryReceiptPage.getUrl())) {
        Assert.assertEquals(pmPaymentHistoryReceiptPage.getTransactionId(), transactionId,
            "Transaction Id should match in the print receipt page");
        return;
      }
    }

    Assert.fail("The right window was not found");
  }

  @Test
  public void validatePmSubmittingPaymentNameIsDisplayed() {
    Logger.info("To validate that receipt displays the name of PM submitting payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String pmFirstName = testSetupPage.getString("pmFirstName");
    final String pmLastName = testSetupPage.getString("pmLastName");

    String pmName = pmFirstName + " " + pmLastName;

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getPaymentMadeBy(), pmName, "PM name should match");
  }

  @Test
  public void validatePaymentMethodAndLastFourOfBankAccountMatches() {
    Logger.info(
        "To validate that Payment method and last four digits of bank account number used matches");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String bankName = testSetupPage.getString("bankName");
    final String accountNumber = testSetupPage.getString("accountNumber");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    UtilityManager utilityManager = new UtilityManager();

    String accountNumberLastFour = utilityManager.getLastFourChar(accountNumber);

    String paymentMethod = bankName + " - #" + accountNumberLastFour;

    Assert.assertEquals(receiptPage.getPaymentMethod(), paymentMethod);
  }

  @Test
  public void validateBreakdownOfPayment() {
    Logger.info("To validate that Payment Receipt page displays breakdown of payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String paymentField = testSetupPage.getString("paymentField");
    final String paymentFieldLabel = testSetupPage.getString("paymentFieldLabel");
    final String givenAmount = testSetupPage.getString("amount");
    final String givenFee = testSetupPage.getString("feeAmount");
    final String givenTotal = testSetupPage.getString("totalAmount");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    double payAmount = Double.parseDouble(givenAmount);
    double amountFee = Double.parseDouble(givenFee);
    double total = Double.parseDouble(givenTotal);
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String formattedAmount = formatter.format(payAmount);
    String formattedFee = formatter.format(amountFee);
    String formattedTotal = formatter.format(total);

    Assert.assertTrue(receiptPage.getPaymentAmountLabel(paymentField).equalsIgnoreCase(
        paymentFieldLabel + ":"), "Payment label: " + paymentFieldLabel + " should be present");

    Assert.assertEquals(receiptPage.getPaymentAmount(paymentField), "$" + formattedAmount,
        "Payment amount should match");

    Assert.assertEquals(receiptPage.getProcessingFeeAmount(), "$" + formattedFee, "Processing "
        + "fee should match");

    Assert.assertEquals(receiptPage.getTotalAmount(), "$" + formattedTotal, "Total amount "
        + "should match");
  }

  @Test
  public void validatePropertyDetailsOnTheReceipt() {
    Logger.info("To validate that property details appear on the receipt");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String propertyAddress = testSetupPage.getString("propertyAddress");
    final String propertyCity = testSetupPage.getString("propertyCity");
    final String propertyState = testSetupPage.getString("propertyState");
    final String propertyZip = testSetupPage.getString("propertyZip");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    String address =
        propertyAddress + ", " + propertyCity + ", " + propertyState + ", " + propertyZip;

    Assert.assertEquals(receiptPage.getPropertyAddress(), address, "Address should match");
  }

  @Test
  public void validateResidentUnitNumber() {
    Logger.info("To validate that residents unit number appears on receipt");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String unitNumber = testSetupPage.getString("unitNumber");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getUnitNumber(), unitNumber, "Unit number should match");
  }

  @Test
  public void validateDisplayOfPropertyManagementCompanyName() {
    Logger.info("To validate that the name of property management company is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc13");
    testSetupPage.open();
    final String transactionId = testSetupPage.getString("transactionId");
    final String pmCompany = testSetupPage.getString("pmCompany");

    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_PM, PaymentFlow.SCHEDULE_ONE_TIME,
        transactionId);
    paymentFlow.openStep(PaymentFlow.STEP_RECEIPT);

    ReceiptPage receiptPage = new ReceiptPage();

    Assert.assertEquals(receiptPage.getPmName(), pmCompany, "PM Company name should match");
  }

  @Test
  public void validateGapiSplitPaymentFields() {
    Logger.info(
        "To validate the transaction receipt shows the split transaction payment fields and not the PayeeIds.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc16");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String payment1VarName = testSetupPage.getString("payment1VarName");
    final String payment2VarName = testSetupPage.getString("payment2VarName");

    UtilityManager utilityManager = new UtilityManager();
    String expectedPayment1Label = utilityManager.unslugify(payment1VarName) + ":";
    String expectedPayment2Label = utilityManager.unslugify(payment2VarName) + ":";

    PmPaymentHistoryReceiptPage receiptPage = new PmPaymentHistoryReceiptPage(transId);
    receiptPage.open();

    String actualPayment1Label = receiptPage.getPaymentAmountLabel(payment1VarName);
    String actualPayment2Label = receiptPage.getPaymentAmountLabel(payment2VarName);

    Assert.assertEquals(actualPayment1Label, expectedPayment1Label,
        "Line Item label 1 translated from payeeId");
    Assert.assertEquals(actualPayment2Label, expectedPayment2Label,
        "Line Item label 2 translated from payeeId");
  }

  @Test
  public void validateGapiPaymentField() {
    Logger.info("To validate the transaction receipt shows the payment field and not the PayeeId.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc17");
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String paymentVarName = testSetupPage.getString("paymentVarName");

    UtilityManager utilityManager = new UtilityManager();
    String expectedPaymentLabel = utilityManager.unslugify(paymentVarName) + ":";

    PmPaymentHistoryReceiptPage receiptPage = new PmPaymentHistoryReceiptPage(transId);
    receiptPage.open();

    String actualPaymentLabel = receiptPage.getPaymentAmountLabel(paymentVarName);

    Assert.assertEquals(actualPaymentLabel, expectedPaymentLabel,
        "Line Item label translated from payeeId");
  }

  @Test
  public void validateCheckImagesShowWhenCustOn() {
    Logger.info(
        "To validate the transaction receipt shows the check images when customization is on.");

    PmPaymentHistoryReceiptPage receiptPage = getReceiptPage("tc4206");

    assertFrontAndBackCheckImagesPresent(receiptPage);
  }

  @Test
  public void validateCheckImagesNotShownWhenCustOff() {
    Logger.info(
        "To validate the transaction receipt does not show the check images when customization is off.");

    PmPaymentHistoryReceiptPage receiptPage = getReceiptPage("tc4207");

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(), "Front check image is not present");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(), "Back check image is not present");
  }

  @Test
  public void validateCheckImagesNotShownWhenCustOnForAch() {
    Logger.info(
        "To validate the transaction receipt does not show the check images when customization is on for ACH transaction.");

    PmPaymentHistoryReceiptPage receiptPage = getReceiptPage("tc4213");

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(), "Front check image is not present");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(), "Back check image is not present");
  }

  @Test
  public void validateCheckImagePresentForSubacc() {
    Logger.info("Validate check images option are present for sub-account.");

    PmPaymentHistoryReceiptPage receiptPage = getReceiptPage("tc4216");

    assertFrontAndBackCheckImagesPresent(receiptPage);
  }

  @Test
  public void validateCheckImgNotPresentPmCustOff() {
    Logger.info(
        "Validate check images option are not present for sub-account when customization for PM is disabled.");

    PmPaymentHistoryReceiptPage receiptPage = getReceiptPage("tc4217");

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(),
        "Front image should not be present for a sub account when customization for pm is off");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(),
        "Back image should not be present for a sub account when customization for pm is off");
  }

  @Test
  public void validateCheckImgNotPresentSubaccCustOff() {
    Logger.info(
        "Validate check images option are not present for sub-account when customization for sub-account is disabled.");

    PmPaymentHistoryReceiptPage receiptPage = getReceiptPage("tc4218");

    Assert.assertFalse(receiptPage.isFrontCheckImagePresent(),
        "Front image should not be present for a sub account when customization for sub-account is off");
    Assert.assertFalse(receiptPage.isBackCheckImagePresent(),
        "Back image should not be present for a sub account when customization for sub-account is off");
  }

  /**
   * Run assertions for front and back check images.
   *
   * @param receiptPage receipt page
   */
  public static void assertFrontAndBackCheckImagesPresent(ReceiptPage receiptPage) {
    final String transId = receiptPage.getTransactionId();

    Assert.assertTrue(receiptPage.isFrontCheckImagePresent(), "Front check image is present");
    Assert.assertTrue(receiptPage.isBackCheckImagePresent(), "Back check image is present");

    String frontCheckImageSrc = receiptPage.getFrontCheckImageSrc();
    String backCheckImageSrc = receiptPage.getBackCheckImageSrc();

    String splitFrontCheckImageSrc[] = frontCheckImageSrc.split("=", 2);
    String splitBackCheckImageSrc[] = backCheckImageSrc.split("=", 2);

    Assert.assertEquals(splitFrontCheckImageSrc.length, 2, "Source should contain one equal sign");
    Assert.assertEquals(splitBackCheckImageSrc.length, 2, "Source should contain one equal sign");

    Assert.assertEquals(splitFrontCheckImageSrc[0], CHECK_IMAGE_BASE_URL,
        "Image resource URL correct");
    Assert.assertEquals(splitBackCheckImageSrc[0], CHECK_IMAGE_BASE_URL,
        "Image resource URL correct");

    DecryptedDataPage decryptedDataPage = new DecryptedDataPage();
    decryptedDataPage.open(splitFrontCheckImageSrc[1]);

    Assert
        .assertEquals(decryptedDataPage.getValue("image"), "front", "Front image included in link");
    Assert
        .assertEquals(decryptedDataPage.getValue("transId"), transId, "Trans ID included in link");

    decryptedDataPage.open(splitBackCheckImageSrc[1]);

    Assert.assertEquals(decryptedDataPage.getValue("image"), "back", "Back image included in link");
    Assert
        .assertEquals(decryptedDataPage.getValue("transId"), transId, "Trans ID included in link");
  }

  private PmPaymentHistoryReceiptPage getReceiptPage(String testCaseId) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();

    final String transId = testSetupPage.getString("transId");
    final String pmUsername = testSetupPage.getString("username");

    PmLoginPage pmLoginPage = new PmLoginPage();
    pmLoginPage.open();
    pmLoginPage.login(pmUsername);

    PmPaymentHistoryReceiptPage receiptPage = new PmPaymentHistoryReceiptPage(transId);
    receiptPage.open();

    return receiptPage;
  }
}
