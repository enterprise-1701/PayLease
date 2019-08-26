package com.paylease.app.qa.smoke.tests;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.registration.HomeOwnerRegistrationPage;
import com.paylease.app.qa.framework.pages.registration.UserDetailsPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeOwnerRegistrationTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "registration";

  @Test(groups = {"smoke"})
  public void homeOwnerRegistrationTest() {
    Logger.info("Verify Home Owner Registration");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "home_tc1");
    testSetupPage.open();
    final String validProperty = testSetupPage.getString("validProperty", "Valid property name");
    final String pmName = testSetupPage.getString("pmName");

    HomeOwnerRegistrationPage homeOwnerRegistrationPage = new HomeOwnerRegistrationPage();

    homeOwnerRegistrationPage.open();
    homeOwnerRegistrationPage.searchPropertyName(validProperty);
    homeOwnerRegistrationPage.filterSearchPm(pmName);

    Assert.assertTrue(homeOwnerRegistrationPage.getPropertyNameAndAddress().equals(validProperty),
        "Search results did not match property");
    Assert.assertTrue(homeOwnerRegistrationPage.getPmName().equals(pmName),
        "Search results did not match PM Company");

    UserDetailsPage userDetailsPage = homeOwnerRegistrationPage.clickThisIsMyPropertyLink();

    userDetailsPage.fillForm();

    String homeOwnerName = userDetailsPage.getFullName();

    userDetailsPage.clickCreateAccount();

    ResHomePage resHomePage = new ResHomePage();

    Assert.assertTrue(resHomePage.getWelcomeMessage().equals("Welcome: " + homeOwnerName),
        "Resident not registered");
  }

}
