package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.registration.PasswordResetPage;
import com.paylease.app.qa.framework.pages.registration.RegistrationPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "login";

  private static final String INCORRECT_PASSWORD = "123";

  private static final String WRONG_USER_PASS_ERROR_MESSAGE = "Wrong Username or Password. Please "
      + "try again.";

  private static final String FAILED_ATTEMPT_LOCK_ERROR_MESSAGE = "Please note, one more failed "
      + "login attempt will lock the user from logging in for the next 30 minutes.";

  private static final String ACCOUNT_LOCKED_ERROR_MESSAGE = "We locked your account due to too "
      + "many incorrect login attempts. Try again later. If you would like to have it unlocked "
      + "before then, please call PayLease Support at 866.729.5327";

  //-----------------------------------Login Tests--------------------------------------------------

  @Test(groups = {"smoke"})
  public void loginTestTc1() {
    Logger.info("Verify Login, Property Name and Property Management Name");

    verifyLogin("tc1");
  }

  @Test(groups = {"smoke"})
  public void loginTestOakwoodTc1() {
    Logger.info("Verify Login, Property Name and Property Management Name - Oakwood");

    verifyLogin("oakwood_tc1");
  }

  @Test
  public void payLeaseLogoNavigationTc2() {
    Logger.info("To validate that clicking on PayLease logo redirects user to main PYL website");

    ResLoginPage resLoginPage = testPrepGeneral();

    resLoginPage.clickPayLeaseLogo();

    WebDriver driver = DriverManager.getDriver();

    Assert.assertEquals(driver.getCurrentUrl(), PageBase.BASE_URL + "corp/",
        "PayLease homepage should load");
  }

  @Test
  public void payLeaseFaqNavigationTc3() {
    Logger.info("To validate that clicking the help button redirects the user to PYL FAQ page");

    ResLoginPage resLoginPage = testPrepGeneral();

    resLoginPage.clickFaqLink();

    WebDriver driver = DriverManager.getDriver();

    Assert.assertTrue(driver.getCurrentUrl().contains(
        "https://paylease.zendesk.com/hc/en-us/categories/202633718-Residents"),
        "PayLease support page should load");
  }

  @Test
  public void payLeasePhoneNumberTc4() {
    Logger.info("To validate that PYL support number is listed");

    ResLoginPage resLoginPage = testPrepGeneral();

    Assert.assertTrue(resLoginPage.isPhoneNumberDisplayed(),
        "PayLease phone number should be displayed");
  }

  @Test
  public void payLeaseForgotPasswordNavigationTc5() {
    Logger.info(
        "To validate that clicking on Forgot your password redirects user to Password Reset page");

    ResLoginPage resLoginPage = testPrepGeneral();

    resLoginPage.clickForgotPasswordLink();

    PasswordResetPage passwordResetPage = new PasswordResetPage();

    Assert.assertTrue(passwordResetPage.pageIsLoaded(),
        "Password reset page should load");
  }

  @Test
  public void payLeaseRegistrationPageNavigationTc6() {
    Logger.info(
        "To validate that clicking on create account redirects user to registration page");

    ResLoginPage resLoginPage = testPrepGeneral();

    resLoginPage.clickCreateAccountLink();

    RegistrationPage registrationPage = new RegistrationPage();

    Assert.assertTrue(registrationPage.pageIsLoaded(),
        "Registration page should load");
  }

  @Test
  public void payLeasePmNavigationThroughResidentLoginPageTc7() {
    Logger.info(
        "To validate if property manager credentials are accepted log in credentials on this page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");

    ResLoginPage resLoginPage = testPrepGeneral();

    PmHomePage pmHomePage = resLoginPage.loginPm(pmEmail);

    Assert.assertTrue(pmHomePage.pageIsLoaded(), "PM page should load");
  }

  @Test
  public void invalidLoginCredentialsErrorMessageTc8() {
    Logger.info(
        "To validate that entering invalid log in credentials will give user an error message");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = testPrepGeneral();

    resLoginPage.login(residentEmail, INCORRECT_PASSWORD);

    Assert.assertEquals(resLoginPage.getErrorMessages().get(0), WRONG_USER_PASS_ERROR_MESSAGE,
        "Error message should match");
  }

  @Test
  public void invalidLoginCredentialsFailedAttemptErrorMessageTc9() {
    Logger.info("To validate that entering invalid log in credentials five times gives user an "
        + "error message that they will be locked out");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc9");
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = testPrepGeneral();

    for (int i = 1; i <= 5; i++) {
      resLoginPage.login(residentEmail, INCORRECT_PASSWORD);
    }

    Assert.assertEquals(resLoginPage.getErrorMessages().get(0), FAILED_ATTEMPT_LOCK_ERROR_MESSAGE,
        "Error message should match");

    Assert.assertEquals(resLoginPage.getErrorMessages().get(1), WRONG_USER_PASS_ERROR_MESSAGE,
        "Error message should match");
  }

  @Test
  public void invalidLoginCredentialsAccountLockedErrorMessageTc10() {
    Logger.info("To validate that entering incorrect login credentials six times or more will "
        + "temporarily lock the user from logging in");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc10");
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");

    ResLoginPage resLoginPage = testPrepGeneral();

    for (int i = 1; i <= 6; i++) {
      resLoginPage.login(residentEmail, INCORRECT_PASSWORD);
    }

    Assert.assertEquals(resLoginPage.getErrorMessages().get(0), ACCOUNT_LOCKED_ERROR_MESSAGE,
        "Error message should match");

    Assert.assertEquals(resLoginPage.getErrorMessages().get(1), WRONG_USER_PASS_ERROR_MESSAGE,
        "Error message should match");
  }

  @Test
  public void tc15() {
    Logger.info("Verify Login, Property Name and Property Management Name - YAVO");

    verifyLogin("tc15");
  }

  //------------------------------------Test Method----------------------------------------------

  private void verifyLogin(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String propertyName = testSetupPage.getString("property", "Property Name");
    final String pmName = testSetupPage.getString("pmName");

    ResLoginPage resLoginPage = testPrepGeneral();

    ResHomePage resHomePage = resLoginPage.login(residentEmail, null);

    Assert.assertEquals(resHomePage.getPropertyName(), propertyName,
        "Property Name should match");

    Assert.assertEquals(resHomePage.getPropertyManagementName(), pmName,
        "Property Management Name should match");
  }

  private ResLoginPage testPrepGeneral() {
    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.open();

    return resLoginPage;
  }
}
