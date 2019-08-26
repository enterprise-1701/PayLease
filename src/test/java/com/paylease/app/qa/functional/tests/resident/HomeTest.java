package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.components.PaymentAmount;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentAmountPage;
import com.paylease.app.qa.framework.pages.paymentflow.PaymentMethodPage;
import com.paylease.app.qa.framework.pages.resident.ResAutoPayListPage;
import com.paylease.app.qa.framework.pages.resident.ResCreateFixedAutopayPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import java.text.DecimalFormat;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomeTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "home";

  private static final String SUCCESS_MESSAGE = "You have been successfully enrolled in credit "
      + "reporting.";
  private static final String LEARN_MORE_TEXT = "Build your credit history by opting in to Credit "
      + "Reporting. Learn The Benefits";
  private static final String NO_LONGER_ACCEPTING_PAYMENTS = "Per your Property Management "
      + "Company, this property is no longer accepting payments. Please contact your Property "
      + "Management Company if you should have any questions regarding this matter. Thank you "
      + "for using PayLease.";


  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc2() {
    Logger.info("To validate that resident name is displayed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc2");
    testSetupPage.open();
    final String firstName = testSetupPage.getString("firstName");
    final String lastName = testSetupPage.getString("lastName");
    final String welcomeMessage = "Welcome: " + firstName + " " + lastName;

    ResHomePage resHomePage = new ResHomePage();

    resHomePage.open();

    Assert.assertEquals(resHomePage.getWelcomeMessage(), welcomeMessage,
        "Resident cannot be found");
    }

  @Test
  public void tc3() {
    Logger.info("To validate that non-numeric characters not accepted when entering payment amount");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc3");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    resHomePage.open();

    DataHelper dataHelper = new DataHelper();

    paymentAmountPage.setPaymentFieldAmount("Lease Payment", dataHelper.generateAlphaString(3));

    resHomePage.clickMakeOneTimePayment(false);

    Assert.assertEquals(resHomePage.getPaymentFieldErrorMessage(),
        "Lease Payment Payment amount must be numeric",
        "Lease Payment amount must be numeric"
    );
  }

  @Test
  public void tc4() {
    Logger.info("To validate that after entering a valid payment amount, resident can move forward with making a payment");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    resHomePage.open();

    paymentAmountPage.setPaymentFieldAmount("Lease Payment","100");

    resHomePage.clickMakeOneTimePayment(true);

    Assert.assertTrue(paymentMethodPage.pageIsLoaded(),
          "User did not navigate to payment method page");
  }

  @Test
  public void tc5() {
    Logger.info("To validate that 'Click here to set up a new AutoPay' link redirects user to AutoPay tab");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResCreateFixedAutopayPage resCreateFixedAutopayPage = new ResCreateFixedAutopayPage();

    resHomePage.open();

    resHomePage.clickSetUpNewAutopay();

    Assert.assertTrue(resCreateFixedAutopayPage.pageIsLoaded(),
        "User did not navigate to Fixed AutoPay page");
  }

  @Test
  public void tc6() {
    Logger.info("To validate the error message a resident see when their property has been deleted");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();

    resHomePage.open();

    Assert.assertEquals(resHomePage.getMakePaymentNowMessage(),
        NO_LONGER_ACCEPTING_PAYMENTS,
        "Expected message is not displayed");
  }

  @Test
  public void tc7() {
    Logger.info("To validate that 'Get Started' button redirects user to AutoPay tab");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResAutoPayListPage resAutoPayListPage = new ResAutoPayListPage();

    resHomePage.open();

    resHomePage.clickGetStartedAutoPayButton();

    Assert.assertTrue(resAutoPayListPage.pageIsLoaded(), "User did not navigate to Autopay page");
  }

  @Test
  public void tc8() {
    Logger.info("To validate that 3 most recent payments are visible under Recent Payment History category");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String transaction1 = testSetupPage.getString("transactionId2");
    final String transaction2 = testSetupPage.getString("transactionId3");
    final String transaction3 = testSetupPage.getString("transactionId4");

    ResHomePage resHomePage = new ResHomePage();

    resHomePage.open();

    Assert.assertTrue(resHomePage.isTransactionPresent(transaction1),  "Cannot find "
        + "transaction: " + transaction1);
    Assert.assertTrue(resHomePage.isTransactionPresent(transaction2),  "Cannot find "
        + "transaction: " + transaction2);
    Assert.assertTrue(resHomePage.isTransactionPresent(transaction3),  "Cannot find "
        + "transaction: " + transaction3);
  }

  @Test
  public void tc9() {
    Logger.info("To validate that any active autopays are visible on the home page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();

    final String transactionAmount = testSetupPage.getString("transactionAmount");
    ResHomePage resHomePage = new ResHomePage();

    resHomePage.open();

    double amount = Double.parseDouble(transactionAmount);
    DecimalFormat formatter = new DecimalFormat("#,##0.00");

    String newTransactionAmount = formatter.format(amount);

    Assert.assertTrue(resHomePage.isAmountPresent("$" + newTransactionAmount),
        "Cannot find transaction amount: " + transactionAmount);
  }

  @Test
  public void tc15() {
    Logger.info("To validate that My Profile tab is not visible");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc15");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResidentMenuItems residentMenuItems = new ResidentMenuItems();

    resHomePage.open();

    Assert.assertFalse(residentMenuItems.isMyProfileTabPresent(),
        "My profile tab exists");
  }

  @Test
  public void tc17() {
    Logger.info("To validate that user is able to Logout from session");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc17");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResLoginPage resLoginPage = new ResLoginPage();

    resHomePage.open();

    resHomePage.clickLogoutButton();

    Assert.assertTrue(resLoginPage.pageIsLoaded(),
        "User is not logged out");
  }

  @Test
  public void tc20() throws ParseException {
    Logger.info("Verify that credit reporting optin container and text is present and the "
        + "resident can optin");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc20");
    testSetupPage.open();

    ResHomePage resHomePage = openResHomePage();

    CreditReporting creditReporting = new CreditReporting();

    Assert.assertTrue(creditReporting.isOptinContainerPresent(),
        "Optin container should be present");

    Assert.assertEquals(creditReporting.getLearnMoreText(), LEARN_MORE_TEXT,
        "Learn More text should show");

    creditReporting.setCreditReportingCheckbox();
    creditReporting.fillCreditReportingForm();

    resHomePage.clickMakeOneTimePayment(true);

    Assert.assertEquals(resHomePage.getSuccessMessage(), SUCCESS_MESSAGE,
        "Success message should show");
  }

  @Test
  public void ideTc01() {
    Logger.info(
        "Verify that a resident will not be able to make payments for PM that is not accepting payments.");

    openTestSetupPage("ide_tc01");

    ResHomePage resHomePage = openResHomePage();

    Assert.assertEquals(resHomePage.getPaymentsDisabledErrorMessage(),
        "Payments are not being accepted at this time. Please contact your property management company or HOA for further information.",
        "Payment blocked error message should display.");
  }

  @Test
  public void ideTc02() {
    Logger.info("Verify that prepopulated fields display correctly on home page.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc02");

    String paymentField1Label = testSetupPage.getString("paymentField1Label");
    String paymentField2Label = testSetupPage.getString("paymentField2Label");
    String paymentField1Value = testSetupPage.getString("paymentField1Value");
    String paymentField2Value = testSetupPage.getString("paymentField2Value");

    openResHomePage();

    PaymentAmount paymentAmountComponent = new PaymentAmount();

    String actualpaymentField1Label = paymentAmountComponent.getPaymentFieldValue(paymentField1Label);
    String actualPaymentField2Label = paymentAmountComponent.getPaymentFieldValue(paymentField2Label);

    Assert.assertEquals(actualpaymentField1Label, paymentField1Value, "Expecting payment field " + paymentField1Label + " to have value " + paymentField1Value + " but found " + actualpaymentField1Label);
    Assert.assertEquals(actualPaymentField2Label, paymentField2Value, "Expecting payment field " + paymentField2Label + " to have value " + paymentField2Value + " but found " + actualPaymentField2Label);
  }

  @Test
  public void ideTc03() {
    Logger.info("Verify that user is taken to Payment Method page after clicking on the Make Payment button on home page.");

    openTestSetupPage("ide_tc03");

    ResHomePage resHomePage = openResHomePage();

    resHomePage.clickMakeOneTimePayment(true);

    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();

    Assert.assertTrue(paymentMethodPage.pageIsLoaded());
  }

  @Test
  public void ideTc11() {
    Logger.info("To validate that an error message displays when the resident enters an amount that is over the limit for cc payments - 2 payment fields.");

    verifyOverCcLimitErrorMessage("ide_tc11");
  }

  @Test
  public void ideTc12() {
    Logger.info("To validate that the payment field is prepopulated with an amount.");

    TestSetupPage testSetupPage = openTestSetupPage("ide_tc12");

    String prepopulatedFieldLabel = testSetupPage.getString("paymentField1Label");
    String prepopulatedFieldValue = testSetupPage.getString("paymentField1Value");

    openResHomePage();

    PaymentAmount paymentAmountComponent = new PaymentAmount();

    Assert.assertTrue(paymentAmountComponent.isPaymentFieldPresent(prepopulatedFieldLabel), "Payment field '" + prepopulatedFieldLabel + "' not found.");
    Assert.assertEquals(paymentAmountComponent.getPaymentFieldValue(prepopulatedFieldLabel), prepopulatedFieldValue, "Payment field value does not match expected.");
  }

  @Test
  public void ideTc15() {
    Logger.info("To validate that an error message displays when the resident enters an amount that is over the limit for cc payments - 1 payment field.");

    verifyOverCcLimitErrorMessage("ide_tc15");
  }

  @Test(dataProvider = "resUiTestSetUp", dataProviderClass = PaymentAmountTest.class)
  public void testResUiHomePropertyCustomizations(String testCaseId, String info, boolean canEdit,
      boolean overPayAssertion) {
    Logger.info(info);

    TestSetupPage testSetupPage = new TestSetupPage("resident", "paymentAmount", testCaseId);
    testSetupPage.open();
    final String paymentFieldLabel = testSetupPage.getString("payFieldRent");
    final String amount = testSetupPage.getString("amountRent");

    ResHomePage resHomePage = new ResHomePage();
    PaymentMethodPage paymentMethodPage = new PaymentMethodPage();
    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    resHomePage.open();
    PaymentAmountTest.setAmountInField(paymentAmountPage, paymentFieldLabel, amount);

    resHomePage.clickMakeOneTimePayment(true);

    if (!canEdit && !overPayAssertion) {
      Assert.assertFalse(paymentMethodPage.pageIsLoaded(),
          "User navigated to payment method page");
    } else {
      Assert.assertTrue(paymentMethodPage.pageIsLoaded(),
          "User did not navigate to payment method page");
    }

  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private void verifyOverCcLimitErrorMessage(String testCase) {
    openTestSetupPage(testCase);

    ResHomePage resHomePage = openResHomePage();

    resHomePage.clickMakeOneTimePayment(true);

    PaymentAmountPage paymentAmountPage = new PaymentAmountPage();

    Assert.assertTrue(paymentAmountPage.pageIsLoaded(), "Failed to load payment amount page");

    Assert.assertEquals(paymentAmountPage.getErrorMessage(), "Payment total amount cannot exceed $100000.00");
  }

  private TestSetupPage openTestSetupPage(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    return testSetupPage;
  }

  private ResHomePage openResHomePage() {
    ResHomePage resHomePage = new ResHomePage();
    resHomePage.open();

    return resHomePage;
  }

}
