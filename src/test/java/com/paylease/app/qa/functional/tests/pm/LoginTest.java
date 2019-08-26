package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "login";

  //-----------------------------------Login Tests--------------------------------------------------

  @Test
  public void tc1() {
    Logger.info("To validate that the PM can login");

    PmHomePage pmHomePage = testPrep("tc1");

    Assert.assertTrue(pmHomePage.pageIsLoaded(), "PM home page should load");
  }

  @Test
  public void yavoTc1() {
    Logger.info("To validate that the PM can login - YAVO");

    PmHomePage pmHomePage = testPrep("yavo_tc1");

    Assert.assertTrue(pmHomePage.pageIsLoaded(), "PM home page should load");
  }

  @Test
  public void amsiTc1() {
    Logger.info("To validate that the PM can login - AMSI");

    PmHomePage pmHomePage = testPrep("amsi_tc1");

    Assert.assertTrue(pmHomePage.pageIsLoaded(), "PM home page should load");
  }

  @Test
  public void tc2() {
    Logger.info("To validate that the Sub-account PM can login");

    PmHomePage pmHomePage = testPrep("tc2");

    Assert.assertTrue(pmHomePage.pageIsLoaded(), "PM home page should load");
  }

  @Test
  public void tc3() {
    Logger.info("To validate that the Billing PM can login");

    PmHomePage pmHomePage = testPrep("tc3");

    PmMenu pmMenu = new PmMenu();

    Assert.assertFalse(pmHomePage.pageIsLoaded(), "Different PM home page should load");

    Assert.assertTrue(pmMenu.isBillingTabDisplayed(), "Billing tab should be present");
  }

  //------------------------------------Test Method----------------------------------------------

  private PmHomePage testPrep(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.open();
    pmLoginPage.login(pmEmail);

    return new PmHomePage();
  }
}
