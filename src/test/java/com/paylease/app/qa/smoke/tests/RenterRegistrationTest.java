package com.paylease.app.qa.smoke.tests;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.registration.RenterRegistrationPage;
import com.paylease.app.qa.framework.pages.registration.UserDetailsPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RenterRegistrationTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "registration";

  @Test(groups = {"smoke"})
  public void renterRegistrationTest() {
    Logger.info("Verify Renter Registration");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "rent_tc1");
    testSetupPage.open();
    final String validProperty = testSetupPage.getString("validProperty", "Valid property name");

    RenterRegistrationPage renterRegistrationPage = new RenterRegistrationPage();

    renterRegistrationPage.open();
    renterRegistrationPage.searchPropertyName(validProperty);

    UserDetailsPage userDetailsPage = renterRegistrationPage.clickThisIsMyPropertyLink();

    userDetailsPage.fillForm();

    String renterName = userDetailsPage.getFullName();

    userDetailsPage.clickCreateAccount();

    ResHomePage resHomePage = new ResHomePage();

    Assert.assertTrue(resHomePage.getWelcomeMessage().equals("Welcome: " + renterName),
        "Resident not registered");
  }

}
