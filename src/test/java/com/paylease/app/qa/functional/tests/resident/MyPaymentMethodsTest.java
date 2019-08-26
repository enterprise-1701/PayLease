package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage.PaymentTypes;
import com.paylease.app.qa.framework.pages.resident.ResidentAddBankAccountPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAddCardAccountPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyPaymentMethodsTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "myPaymentMethods";

  private static final String CARD_LOGO_FILENAME_AMEX = "amex_icon_sm.png";
  private static final String CARD_LOGO_FILENAME_VISA = "visa_icon_sm.png";
  private static final String CARD_LOGO_FILENAME_DISCOVER = "dc_icon_sm.png";
  private static final String CARD_LOGO_FILENAME_MC = "mc_icon_sm.png";
  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc2() {
    Logger.info("Add new payment warning - Add bank");

    newPaymentWarningAddBankAccount("tc2");
  }

  @Test
  public void oakwood_tc2() {
    Logger.info("Add new payment warning - Add bank - Oakwood");

    newPaymentWarningAddBankAccount("oakwood_tc2");
  }

  @Test
  public void tc3() {
    Logger.info("Add new payment warning - Add bank with existing card account");

    newPaymentWarningAddBankAccount("tc3");
  }

  @Test
  public void oakwood_tc3() {
    Logger.info("Add new payment warning - Add bank with existing card account - Oakwood");

    newPaymentWarningAddBankAccount("oakwood_tc3");
  }

  @Test
  public void tc4() {
    Logger.info("Add new payment warning - Add card");

    newPaymentWarningAddCardAccount("tc4");
  }

  @Test
  public void oakwood_tc4() {
    Logger.info("Add new payment warning - Add card - Oakwood");

    newPaymentWarningAddCardAccount("oakwood_tc4");
  }

  @Test
  public void tc5() {
    Logger.info("Add new payment warning - Add card with existing bank account");

    newPaymentWarningAddCardAccount("tc5");
  }

  @Test
  public void oakwood_tc5() {
    Logger.info("Add new payment warning - Add card with existing bank account - Oakwood");

    newPaymentWarningAddCardAccount("oakwood_tc5");
  }

  @Test
  public void tc6() {
    Logger.info("Add Card - show visa disclosure");

    showDisclosureAddCardAccount();
  }

  @Test
  public void tc7() {
    Logger.info("Add new payment - click Add Bank Account button");

    testPrepBank("tc7");

    ResidentAddBankAccountPage resAddBankAcctPage = new ResidentAddBankAccountPage();

    Assert.assertTrue(resAddBankAcctPage.pageIsLoaded(), "Add Bank Account page not found.");
  }

  @Test
  public void tc8() {
    Logger.info("Add new payment - click Add Card button");

    testPrepCard("tc8");

    ResidentAddCardAccountPage resAddCardAcctPage = new ResidentAddCardAccountPage();

    Assert.assertTrue(resAddCardAcctPage.pageIsLoaded(), "Add Card page not found.");
  }

  @Test
  public void tc9() {
    Logger.info("Delete bank account");

    deleteFirstPaymentMethod("tc9", PaymentTypes.PAYMENT_TYPE_ACH);
  }

  @Test
  public void tc10() {
    Logger.info("Delete bank account linked to an active autopay");

    deletePaymentMethodLinkedAutopay("tc10", PaymentTypes.PAYMENT_TYPE_ACH);
  }

  @Test
  public void tc11() {
    Logger.info("Delete card account");

    deleteFirstPaymentMethod("tc11", PaymentTypes.PAYMENT_TYPE_CC);

  }

  @Test
  public void tc12() {
    Logger.info("Delete card account linked to an active autopay");

    deletePaymentMethodLinkedAutopay("tc12", PaymentTypes.PAYMENT_TYPE_CC);
  }

  @Test
  public void tc13() {
    Logger.info("Verify that Visa logo displays next to saved card");

    assertCardLogo("tc13", CARD_LOGO_FILENAME_VISA);
  }

  @Test
  public void tc14() {
    Logger.info("Verify that Discover logo displays next to saved card");

    assertCardLogo("tc14", CARD_LOGO_FILENAME_DISCOVER);
  }

  @Test
  public void tc15() {
    Logger.info("Verify that Amex logo displays next to saved card");

    assertCardLogo("tc15", CARD_LOGO_FILENAME_AMEX);
  }

  @Test
  public void tc16() {
    Logger.info("Verify that Mastercard logo displays next to saved card");

    assertCardLogo("tc16", CARD_LOGO_FILENAME_MC);
  }

  @Test
  public void tc17() {
    Logger.info("Verify credit reporting optin container is not present");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc17");
    testSetupPage.open();

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();

    CreditReporting creditReporting = new CreditReporting();

    Assert.assertFalse(creditReporting.isOptinContainerPresent(),
        "Optin container should not be present");
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private ResidentAccountsPage testPrepBank(String testCase) {
    ResidentAccountsPage residentAccountsPage = this.testPrepGeneral(testCase);
    residentAccountsPage.clickAddBankAccountButton(false);

    return residentAccountsPage;
  }

  private ResidentAccountsPage testPrepCard(String testCase) {
    ResidentAccountsPage residentAccountsPage = this.testPrepGeneral(testCase);
    residentAccountsPage.clickAddCardAccountButton(false);

    return residentAccountsPage;
  }

  private void newPaymentWarningAddBankAccount(String testCase) {
    ResidentAccountsPage residentAccountsPage = testPrepBank(testCase);

    Assert.assertEquals(residentAccountsPage.getAlertText(),
        "Adding a new Payment Account will delete any currently stored Payment Account. "
            + "Do you wish to continue?", "Alert should display");
  }

  private void newPaymentWarningAddCardAccount(String testCase) {
    ResidentAccountsPage residentAccountsPage = testPrepCard(testCase);

    Assert.assertEquals(residentAccountsPage.getAlertText(),
        "Adding a new Payment Account will delete any currently stored Payment Account. "
            + "Do you wish to continue?", "Alert should display");
  }

  private void showDisclosureAddCardAccount() {
    testPrepCard("tc6");

    CardAccountDetailsPage cardAcctDetailsPage = new CardAccountDetailsPage();

    Assert.assertEquals(
        cardAcctDetailsPage.getDisclosureMessage(),
        "By clicking SUBMIT, you grant PayLease permission to store your card credentials. Read more",
        "Missing disclosure message"
    );

    cardAcctDetailsPage.clickDisclosureReadMoreButton();

    Assert.assertTrue(
        cardAcctDetailsPage.isSecondDisclosureMessageVisible(),
        "Second disclosure message not displayed"
    );

    Assert.assertEquals(cardAcctDetailsPage.getSecondDislosureMessage(),
        "* By adding this payment account, you grant PayLease permission to store your card credentials. The stored card credentials will only be used when you initiate a payment or have a recurring payment scheduled. You may delete this payment account at any time by accessing your PayLease account and navigating to the 'My Payment Accounts' tab. Any changes to these terms will be reflected in PayLease's general terms and conditions.",
        "Second disclosure not found.");
  }

  private void deleteFirstPaymentMethod(String testCase, PaymentTypes paymentType) {
    String acctHolderName = testPrepGetAcctHolder(testCase);
    String acctString = "";

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();

    if (paymentType == PaymentTypes.PAYMENT_TYPE_CC) {
      acctString = "Card";
    } else if (paymentType == PaymentTypes.PAYMENT_TYPE_ACH) {
      acctString = "Bank Account";
    }
    residentAccountsPage.deletePaymentMethod(false, paymentType, acctHolderName);

    Assert.assertEquals(residentAccountsPage.getAlertText(),
        "Are you sure you would like to delete this account? This action cannot be undone!",
        "Alert text not found");

    residentAccountsPage.acceptAlert();

    Assert.assertEquals(residentAccountsPage.getSuccessMessage(),
        acctString + " has been successfully deleted", "Success message not found");
  }

  private void deletePaymentMethodLinkedAutopay(String testCase, PaymentTypes paymentType) {
    String acctHolderName = testPrepGetAcctHolder(testCase);

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();
    residentAccountsPage.deletePaymentMethod(false, paymentType, acctHolderName);
    residentAccountsPage.acceptAlert();

    Assert.assertEquals(residentAccountsPage.getErrorMessage(),
        "This Account is linked to an active AutoPay. Please cancel the AutoPay before retrying this deletion.",
        "Error message not found");
  }

  private void assertCardLogo(String testCase, String cardTypeLogoSrc) {
    String acctHolderName = testPrepGetAcctHolder(testCase);

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();

    Assert.assertTrue(
        residentAccountsPage.getCardLogoSrc(acctHolderName).contains(cardTypeLogoSrc),
        "Logo not found");
  }

  private ResidentAccountsPage testPrepGeneral(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();

    return residentAccountsPage;
  }

  private String testPrepGetAcctHolder(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    return testSetupPage.getString("acctHolderName");
  }
}
