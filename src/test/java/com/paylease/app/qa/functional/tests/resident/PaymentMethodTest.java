package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.features.PaymentFlow;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.paymentflow.ReviewAndSubmitPage;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentMethodTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "paymentMethod";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void ideTc06() {
    Logger.info("To validate that correct processing fee is displayed for CC payment.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc06");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");

    assertFeeAmount(transId, savedPaymentMethod);
  }

  @Test
  public void ideTc07() {
    Logger.info("To validate that correct processing fee is displayed for ACH payment.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc07");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("achId");

    assertFeeAmount(transId, savedPaymentMethod);
  }

  @Test
  public void ideTc08() {
    Logger.info("To validate that total shows the sum of payment amount values and fees.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc08");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");
    float paymentAmount = Float.valueOf(testSetupPage.getString("paymentAmt"));

    openStepPaymentMethod(transId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    float feeAmt = Float.valueOf(paymentMethodPage.getFeeAmount(savedPaymentMethod).replace(" Fee", "").replace("$", ""));
    float totalAmount = paymentAmount + feeAmt;

    String totalAmountStr = "$" + String.format("%.2f", totalAmount);

    ReviewAndSubmitPage reviewAndSubmitPage = payWithSavedPaymentMethod(savedPaymentMethod, false);

    Assert.assertEquals(reviewAndSubmitPage.getTotalAmount(), totalAmountStr);
  }

  @Test
  public void ideTc09() {
    Logger.info("To validate that a saved cc payment method can be used for one time payment.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc09");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");
    String savedCardType = testSetupPage.getString("savedCardType");
    String savedCardLastFour = testSetupPage.getString("savedCardLastFour");

    openStepPaymentMethod(transId);

    ReviewAndSubmitPage reviewAndSubmitPage = payWithSavedPaymentMethod(savedPaymentMethod, false);

    Assert.assertTrue(reviewAndSubmitPage.getCardType().equalsIgnoreCase(savedCardType));
    Assert.assertEquals(reviewAndSubmitPage.getCardNumberLastFour(), savedCardLastFour);
  }

  @Test
  public void ideTc10() {
    Logger.info("To validate that Express pay fee and cc processing fee gets added to the total amount in Review and Submit page.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc10");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");
    float paymentAmount = Float.valueOf(testSetupPage.getString("paymentAmt"));

    openStepPaymentMethod(transId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    float feeAmt = Float.valueOf(paymentMethodPage.getFeeAmount(savedPaymentMethod).replace(" Fee", "").replace("$", ""));
    float expressPayFeeAmt = Float.valueOf(paymentMethodPage.getExpressPayText(savedPaymentMethod).replace(" Additional", "").replace("$", ""));

    float totalAmount = paymentAmount + feeAmt + expressPayFeeAmt;

    String totalAmountStr = "$" + String.format("%.2f", totalAmount);

    ReviewAndSubmitPage reviewAndSubmitPage = this
        .payWithSavedPaymentMethod(savedPaymentMethod, true);

    Assert.assertEquals(reviewAndSubmitPage.getTotalAmount(), totalAmountStr);
  }

  @Test
  public void ideTc13() {
    Logger.info("To validate that the Payment for field shows the correct payment field.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc13");

    String transId = testSetupPage.getString("transactionId");
    String fieldLabel = testSetupPage.getString("fieldLabel");

    PaymentFlow paymentFlow = openStepPaymentMethod(transId);

    Assert.assertTrue(paymentFlow.getPaymentFor().equalsIgnoreCase(fieldLabel), "Expected " + fieldLabel + " but found " + paymentFlow.getPaymentFor());
  }

  @Test
  public void ideTc14() {
    Logger.info("To validate that the Payment amount field shows the correct amount.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc14");

    String transId = testSetupPage.getString("transactionId");
    String paymentAmt = "$" + testSetupPage.getString("paymentAmt");

    PaymentFlow paymentFlow = openStepPaymentMethod(transId);

    Assert.assertTrue(paymentFlow.getPaymentAmount().equalsIgnoreCase(paymentAmt), "Expected " + paymentAmt + " but found " + paymentFlow.getPaymentAmount());
  }

  @Test (groups = {"litle"})
  public void ideTc16() {
    Logger.info("To validate that an error message displays when a cc with insufficient funds is used for one time payment.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc16");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");

    openStepPaymentMethod(transId);

    ReviewAndSubmitPage reviewAndSubmitPage = payWithSavedPaymentMethod(savedPaymentMethod, false);

    reviewAndSubmitPage.clickSubmitButton();

    List<String> errorList = reviewAndSubmitPage.getErrorMessages();

    Assert.assertTrue(errorList.contains("Your payment did not process successfully. Response: 110 Insufficient Funds"), "Expected error message, 'Your payment did not process successfully. Response: 110 Insufficient Funds' was not found.");
    Assert.assertTrue(errorList.contains("Please contact your card issuing bank for further information regarding this decline."), "Expected error message, 'Please contact your card issuing bank for further information regarding this decline.' was not found.");
  }

  @Test
  public void ideTc17() {
    Logger.info("To validate that the total shows the sum of payment amount values and fees for non-expresspay ach transactions.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc17");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("achId");
    float paymentAmount = Float.valueOf(testSetupPage.getString("paymentAmt"));

    openStepPaymentMethod(transId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    float feeAmt = Float.valueOf(paymentMethodPage.getFeeAmount(savedPaymentMethod).replace(" Fee", "").replace("$", ""));

    float totalAmount = paymentAmount + feeAmt;

    String totalAmountStr = "$" + String.format("%.2f", totalAmount);

    ReviewAndSubmitPage reviewAndSubmitPage = payWithSavedPaymentMethod(savedPaymentMethod, false);

    Assert.assertEquals(reviewAndSubmitPage.getTotalAmount(), totalAmountStr);
  }

  @Test
  public void ideTc18() {
    Logger.info("To validate that a saved ach payment method can be used for one time payment.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc18");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("achId");
    String savedBankName = testSetupPage.getString("savedBankName");

    openStepPaymentMethod(transId);

    ReviewAndSubmitPage reviewAndSubmitPage = payWithSavedPaymentMethod(savedPaymentMethod, false);

    Assert.assertTrue(reviewAndSubmitPage.getBankName().equalsIgnoreCase(savedBankName),"Expecting bank name " + savedBankName + " but found " +  reviewAndSubmitPage.getBankName());
  }

  @Test
  public void ideTc19() {
    Logger.info("To validate that Express pay fee and cc processing fee gets added to the total amount in Review and Submit page.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc19");

    String transId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("achId");
    float paymentAmount = Float.valueOf(testSetupPage.getString("paymentAmt"));

    openStepPaymentMethod(transId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    float feeAmt = Float.valueOf(paymentMethodPage.getFeeAmount(savedPaymentMethod).replace(" Fee", "").replace("$", ""));
    float expressPayFeeAmt = Float.valueOf(paymentMethodPage.getExpressPayText(savedPaymentMethod).replace(" Additional", "").replace("$", ""));

    float totalAmount = paymentAmount + feeAmt + expressPayFeeAmt;

    String totalAmountStr = "$" + String.format("%.2f", totalAmount);

    ReviewAndSubmitPage reviewAndSubmitPage = this
        .payWithSavedPaymentMethod(savedPaymentMethod, true);

    Assert.assertEquals(reviewAndSubmitPage.getTotalAmount(), totalAmountStr);
  }

  @Test
  public void ideTc20() {
    Logger.info("To validate that an error message displays when the user enters amounts on multiple payment fields that will total to an amount over the ach limit for an ach payment.");

    verifyOverAchLimitErrorMessage("ide_tc20");
  }

  @Test
  public void ideTc21() {
    Logger.info("To validate that an error message displays when the user enters amount on one payment field that is over the ach limit for an ach payment.");

    verifyOverAchLimitErrorMessage("ide_tc21");
  }

  @Test
  public void ideTc22() {
    Logger.info("To validate that Express Pay column shows Included for ACH.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc22");

    String transactionId = testSetupPage.getString("transactionId");
    String savedPaymentMethod =  testSetupPage.getString("achId");

    openStepPaymentMethod(transactionId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertTrue(paymentMethodPage.getExpressPayText(savedPaymentMethod).equals("Included"));
  }

  @Test
  public void ideTc23() {
    Logger.info("To validate that Express Pay column shows Included for CC.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc23");

    String transactionId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");

    openStepPaymentMethod(transactionId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertTrue(paymentMethodPage.getExpressPayText(savedPaymentMethod).equals("Included"));
  }

  @Test
  public void ideTc24() {
    Logger.info("To validate that resident is only charged 9.95 for express pay when PM incurs standard ACH fee.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc24");

    String transactionId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("achId");

    openStepPaymentMethod(transactionId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertEquals(paymentMethodPage.getExpressPayText(savedPaymentMethod), ("$9.95 Additional"));

    selectSavedPaymentMethodAndContinue(savedPaymentMethod, true);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertEquals(reviewAndSubmitPage.getFeeAmount(), "$9.95");
  }

  @Test
  public void ideTc25() {
    Logger.info("To validate that resident is only charged 9.95 for express pay when PM incurs standard CC fee.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc25");

    String transactionId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");

    openStepPaymentMethod(transactionId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertEquals(paymentMethodPage.getExpressPayText(savedPaymentMethod), "$9.95 Additional");

    selectSavedPaymentMethodAndContinue(savedPaymentMethod, true);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertEquals(reviewAndSubmitPage.getFeeAmount(), "$9.95");
  }

  @Test
  public void ideTc26() {
    Logger.info("To validate that Express Pay is default for ACH.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc26");

    String transactionId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("achId");

    openStepPaymentMethod(transactionId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertTrue(paymentMethodPage.getxpressPayColumnHeaderText().equals("Standard Processing\nPayment posts in 1 business day"));

    selectSavedPaymentMethodAndContinue(savedPaymentMethod, false);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertFalse(reviewAndSubmitPage.isFeeTextPresent());
  }

  @Test
  public void ideTc27() {
    Logger.info("To validate that Express Pay is default for CC.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc27");

    String transactionId = testSetupPage.getString("transactionId");
    String savedPaymentMethod = testSetupPage.getString("ccId");

    openStepPaymentMethod(transactionId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertTrue(paymentMethodPage.getxpressPayColumnHeaderText().equals("Standard Processing\nPayment posts in 1 business day"));

    selectSavedPaymentMethodAndContinue(savedPaymentMethod, false);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertFalse(reviewAndSubmitPage.isFeeTextPresent());
  }

  //------------------------------------TEST METHOD-------------------------------------------------


  private TestSetupPage openTestSetupPage(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    return testSetupPage;
  }

  private PaymentFlow openStepPaymentMethod(String transId) {
    PaymentFlow paymentFlow = new PaymentFlow(PaymentFlow.UI_RES, PaymentFlow.SCHEDULE_ONE_TIME,
        transId);

    paymentFlow.openStep(PaymentFlow.STEP_METHOD);

    return paymentFlow;
  }

  private void assertFeeAmount(String transId, String savedPaymentMethod) {
    openStepPaymentMethod(transId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    String transactionFee = paymentMethodPage.getFeeAmount(savedPaymentMethod).replace(" Fee", "");

    ReviewAndSubmitPage reviewAndSubmitPage = payWithSavedPaymentMethod(savedPaymentMethod, false);

    Assert.assertEquals(reviewAndSubmitPage.getFeeAmount(), transactionFee);
  }

  private ReviewAndSubmitPage payWithSavedPaymentMethod(String savedPaymentMethod, boolean isExpressPay) {

    selectSavedPaymentMethodAndContinue(savedPaymentMethod, isExpressPay);

    ReviewAndSubmitPage reviewAndSubmitPage = new ReviewAndSubmitPage();

    Assert.assertTrue(reviewAndSubmitPage.pageIsLoaded());

    return reviewAndSubmitPage;
  }

  private void selectSavedPaymentMethodAndContinue(String savedPaymentMethod, boolean isExpressPay) {
    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(savedPaymentMethod);

    if (isExpressPay) {
      paymentMethodPage.setExpressPayCheckbox(savedPaymentMethod);
    }

    paymentMethodPage.clickContinueButton();
  }

  private void verifyOverAchLimitErrorMessage(String testcase) {
    TestSetupPage testSetupPage = openTestSetupPage(testcase);

    final String transId = testSetupPage.getString("transactionId");
    final String savedPaymentMethod = testSetupPage.getString("achId");

    openStepPaymentMethod(transId);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    paymentMethodPage.selectPaymentMethod(savedPaymentMethod);

    paymentMethodPage.clickContinueButtonNoWait();

    Assert.assertEquals(paymentMethodPage.getErrorMessage(), "Payment total amount cannot exceed $7500.00");
  }

}
