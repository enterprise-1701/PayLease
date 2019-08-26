package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.registration.TokenRegistrationPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "registration";

  //------------------------------------REGISTRATION TESTS------------------------------------------

  @Test
  public void oakwoodNewUserRegistration() {
    Logger.info("Oakwood registration as new user");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "oakwood_tc1");
    testSetupPage.open();
    final String tokenValue = testSetupPage.getString("tokenValue");

    userRegistration(tokenValue, null, false);
  }

  @Test
  public void oakwoodExistingUserRegistration() {
    Logger.info("Oakwood registration as an existing user");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "oakwood_tc2");
    testSetupPage.open();
    final String residentEmail = testSetupPage.getString("residentEmail");
    final String tokenValue = testSetupPage.getString("tokenValue");

    userRegistration(tokenValue, residentEmail, true);
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private void userRegistration(String tokenValue, String residentEmail, Boolean existingUser) {
    DataHelper dataHelper = new DataHelper();

    TokenRegistrationPage tokenRegistrationPage = new TokenRegistrationPage(tokenValue);

    tokenRegistrationPage.open();

    if (residentEmail == null) {
      residentEmail = dataHelper.getUniqueEmail();
    }

    tokenRegistrationPage.enterEmailAddress(residentEmail);
    tokenRegistrationPage.clickContinueButton();
    tokenRegistrationPage.enterPassword();

    if (!existingUser) {
      tokenRegistrationPage.enterConfirmPassword();
    }

    tokenRegistrationPage.setTermsCheckbox();
    tokenRegistrationPage.clickContinueButton();

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    Assert.assertTrue(residentAccountsPage.pageIsLoaded(), "Resident accounts page did not load");
  }
}
