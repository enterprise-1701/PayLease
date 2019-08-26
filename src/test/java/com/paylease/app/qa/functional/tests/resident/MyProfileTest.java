package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.CreditReporting;
import com.paylease.app.qa.framework.pages.admin.AdminHomePage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchUsersResultsPage;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResMyProfileNotificationPage;
import com.paylease.app.qa.framework.pages.resident.ResMyProfilePage;
import com.paylease.app.qa.testbase.dataproviders.MyProfileTestDataProvider;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyProfileTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "myProfile";

  private static final String ERR_MESSAGE_PIN = "Please enter only digits.";
  private static final String ERR_MESSAGE_MOBILE_NUMBER = "Mobile Number is incorrectly formatted";
  private static final String SUCCESS_MESSAGE_PAY_BY_TEXT = "Your Payment Reminder/Pay By Text "
      + "settings have been successfully saved. You can edit these settings below.";
  private static final String ERR_MESSAGE_EMPTY_FIELD = "This field is required.";
  private static final String SUCCESS_MESSAGE_CREDIT_REPORTING = "Credit Reporting settings have "
      + "been successfully updated.";

  //-----------------------------------MyProfile Tests----------------------------------------------
  @Test
  public void myProfileTestTc01() {
    Logger.info("To validate that a resident is able to see a check box for pay by text on the "
        + "My profile page on the RESUI.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    Assert.assertTrue(resMyProfileNotificationPage.isPayByTextCheckboxPresent(),
        "PayByText Checkbox is not present");
  }

  @Test
  public void myProfileTestTc03() {
    Logger.info("To validate that the resident sets up their payment reminders first in order to "
        + "set up the Pay By Text feature in the My Profile screen in the RES UI.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.disableTextMessages();

    Assert.assertFalse(resMyProfileNotificationPage.isPayByTextCheckboxChecked(),
        "Pay by Text is selected");
  }

  @Test
  public void myProfileTestTc04() {
    Logger.info(
        "To validate that the resident enters a valid phone number in the Mobile number field");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.setMobileNumber("+1888 888 8888");

    Assert.assertEquals(resMyProfileNotificationPage.getMobileNumberErrorMessage(),
        ERR_MESSAGE_MOBILE_NUMBER,"The Error message does not match");

  }

  @Test
  public void myProfileTestTc05() {
    Logger.info(
        "To validate that the resident is not able to set a PIN containing non-numeric values.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.enableTextMessage();
    resMyProfileNotificationPage.enablePayByText();
    resMyProfileNotificationPage.setPin("ACF123456");

    Assert.assertEquals(resMyProfileNotificationPage.getPinErrorMessage(), ERR_MESSAGE_PIN,
        "The Error message does not match");
  }

  @Test
  public void myProfileTestTc06() {
    Logger.info("To validate that there is a dropdown for selecting the Payment Account when "
        + "Pay by Text is enabled.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    Assert.assertTrue(resMyProfileNotificationPage.isPaymentAccountDropdownPresent(),
        "Payment account dropdown is not present");
  }

  @Test
  public void myProfileTestTc08() {
    Logger.info("To validate that there is a dropdown for selecting the Payment Field when "
        + "Pay by Text is enabled.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    Assert.assertTrue(resMyProfileNotificationPage.isPaymentFieldDropdownPresent(),
        "Payment Field dropdown is not present");
  }

  @Test
  public void myProfileTestTc10() {
    Logger.info("To validate that there is a field to enter the Payment Amount when the "
        + "Pay by Text feature is enabled.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    Assert.assertTrue(resMyProfileNotificationPage.isPaymentAmountFieldPresent(),
        "payment Amount field is not present");
  }

  @Test
  public void myProfileTestTc11() {
    Logger.info("To validate that the resident is able to setup Pay by Text after entering "
        + "all valid values.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc11");
    testSetupPage.open();
    final String accountId = testSetupPage.getString("accountId");
    final String paymentFieldVarName = testSetupPage.getString("paymentFieldVarName");

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.enableTextMessage();
    resMyProfileNotificationPage.enablePayByText();
    resMyProfileNotificationPage.setPin("123456");
    resMyProfileNotificationPage.selectPaymentAccountBank(accountId);
    resMyProfileNotificationPage.selectPaymentField(paymentFieldVarName);
    resMyProfileNotificationPage.setPaymentAmount("123.00");
    resMyProfileNotificationPage.save();

    Assert.assertEquals(resMyProfileNotificationPage.getSuccessMessage(), SUCCESS_MESSAGE_PAY_BY_TEXT,
        "Pay by Text did not get saved successfully.");
  }

  @Test
  public void myProfileTestTc12() {
    Logger.info("To validate that an error message is displayed when one of the fields "
        + "(PIN/Payment Amount/Payment Method/Payment Field )are left blank.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.enableTextMessage();
    resMyProfileNotificationPage.enablePayByText();

    Assert.assertEquals(resMyProfileNotificationPage.getAmountErrorMessage(), ERR_MESSAGE_EMPTY_FIELD,
        "Error message not displayed for empty fields");

  }

  @Test
  public void myProfileTestTc13() {
    Logger.info("To validate that after selecting the Pay By Text feature, a link will be "
        + "displayed that will direct the users to the Pay By Text Terms & Conditions.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.enableTextMessage();
    resMyProfileNotificationPage.enablePayByText();

    Assert.assertTrue(resMyProfileNotificationPage.isTermsAndConditionAvailable(),
        "Terms and condition is not present");

  }

  @Test
  public void myProfileTestTc17() {
    Logger.info("To validate that if a Payment Method has been deleted/inactive, the resident is "
        + "not able to select that payment method when setting up a payment method for the Pay By Text feature in the Res UI.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc17");
    testSetupPage.open();
    final String accountId = testSetupPage.getString("accountId");
    final String ccId = testSetupPage.getString("ccId");

    ResMyProfileNotificationPage resMyProfileNotificationPage = new ResMyProfileNotificationPage();
    resMyProfileNotificationPage.open();

    resMyProfileNotificationPage.enableTextMessage();
    resMyProfileNotificationPage.enablePayByText();

    Assert.assertFalse(resMyProfileNotificationPage.isSelectablePaymentAccountBank(accountId),
        "Bank Account:" + accountId + " is selectable but deleted");

    Assert.assertFalse(resMyProfileNotificationPage.isSelectablePaymentAccountCc(ccId),
        "Credit Card:" + ccId + " is selectable but deleted");
  }

  @Test
  public void tc151() throws ParseException {
    Logger.info("To validate that a success message is displayed when resident opts out of "
        + "credit reporting ");

    creditReportOpt(ResMyProfilePage.OPT_OUT, "tc151");
  }

  @Test
  public void tc152() throws ParseException {
    Logger.info("To validate that a success message is displayed when resident opts into "
        + "credit reporting ");

    creditReportOpt(ResMyProfilePage.OPT_IN, "tc152");
  }

  @Test(dataProvider = "myProfileData", dataProviderClass = MyProfileTestDataProvider.class, groups =
      {"e2e"})
  public void ideResUiGeneralMyProfile(String testCase, boolean myProfileMenuDisabled) {
    String expected = "the resident can access the My Profile tab";
    if(myProfileMenuDisabled) {
      expected = "the resident is not able to access the MyProfile tab";
    }
    Logger.info(
        "Verify that "+expected+", when 'disable my profile in pay portal' setting is "+myProfileMenuDisabled);

    verifyResidentMyProfileAccess(testCase, myProfileMenuDisabled);
  }

  @Test(dataProvider = "myProfileData", dataProviderClass = MyProfileTestDataProvider.class, groups =
      {"e2e"})
  public void ideResUiGeneralMyProfileAsAdmin(String testCase, boolean myProfileMenuDisabled) {

    Logger.info(
        "Verify that admin can access the My Profile tab, when 'disable my profile in pay portal' setting is "+myProfileMenuDisabled);

    verifyAdminMyProfileAccess(testCase, myProfileMenuDisabled);
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private void creditReportOpt(String optOption, String testCase) throws ParseException {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    ResMyProfilePage resMyProfilePage = new ResMyProfilePage();

    resMyProfilePage.open();
    resMyProfilePage.clickCreditReportingEditButton();
    resMyProfilePage.selectCreditReporting(optOption);

    if (optOption.equals(ResMyProfilePage.OPT_IN)) {
      CreditReporting creditReporting = new CreditReporting();

      creditReporting.fillCreditReportingForm();
    }

    resMyProfilePage.clickCreditReportingSaveButton();

    Assert.assertEquals(resMyProfilePage.getSuccessMessage(), SUCCESS_MESSAGE_CREDIT_REPORTING,
        "Success message should show");
  }

  private void verifyResidentMyProfileAccess(String testCase, boolean isMyProfileRestricted) {
    DataHelper dataHelper = new DataHelper();
    String newUnitNumber;

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String residentEmail = testSetupPage.getString("residentEmail");
    String residentId = testSetupPage.getString("residentId");

    ResLoginPage resLoginPage = new ResLoginPage();
    resLoginPage.open();
    resLoginPage.login(residentEmail, null);

    ResHomePage resHomePage = new ResHomePage();
    resHomePage.open();

    ResMyProfilePage resMyProfilePage = new ResMyProfilePage();
    resMyProfilePage.open();

    if(isMyProfileRestricted) {
      Assert.assertTrue(resHomePage.pageIsLoaded(), "Resident is not on Home page.");
    } else {
      Assert.assertTrue(resMyProfilePage.pageIsLoaded(), "Resident is not on My Profile page.");

      newUnitNumber = dataHelper.generateAlphanumericString(3);

      resMyProfilePage.clickEditResidentDetails();
      resMyProfilePage.setUnitNumber(newUnitNumber);
      resMyProfilePage.clickSave();

      Assert.assertEquals(resMyProfilePage.getUnitNumber(), newUnitNumber, "Expecting value of " + newUnitNumber + " but found " + resMyProfilePage.getUnitNumber());
    }

    ResLogoutBar resLogoutBar = new ResLogoutBar();
    resLogoutBar.clickLogoutButton();
  }

  private void verifyAdminMyProfileAccess(String testCase, boolean isMyProfileRestricted) {
    DataHelper dataHelper = new DataHelper();
    String newUnitNumber;

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String residentId = testSetupPage.getString("residentId");

    LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
    loginPageAdmin.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchResident(residentId);

    AdminSearchUsersResultsPage adminSearchUsersResultsPage = new AdminSearchUsersResultsPage();
    adminSearchUsersResultsPage.logAsUser(residentId);

    ResMyProfilePage resMyProfilePage = new ResMyProfilePage();
    resMyProfilePage.open();

    Assert.assertTrue(resMyProfilePage.pageIsLoaded(), "My profile page did not load.");

    newUnitNumber = dataHelper.generateAlphanumericString(3);

    resMyProfilePage.clickEditResidentDetails();
    resMyProfilePage.setUnitNumber(newUnitNumber);
    resMyProfilePage.clickSave();

    Assert.assertEquals(resMyProfilePage.getUnitNumber(), newUnitNumber, "Expecting value of " + newUnitNumber + " but found " + resMyProfilePage.getUnitNumber());

    if(isMyProfileRestricted) {
      Assert.assertEquals(resMyProfilePage.getAdminAccessMessage(),
          "The page you are viewing is not accessible to the resident.\nYou have been granted access so that you are able to adjust the resident's settings.", "Admin Access message should display.");
    }

    ResLogoutBar resLogoutBar = new ResLogoutBar();
    resLogoutBar.clickLogoutButton();
  }
}
