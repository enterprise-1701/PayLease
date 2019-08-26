package com.paylease.app.qa.e2e.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResidentInvoicesPage;
import com.paylease.app.qa.framework.pages.resident.ResidentManageAccountsPage;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import com.paylease.app.qa.testbase.dataproviders.LinkedAccountsTestDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinkedAccountsTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "linkedAccounts";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(dataProvider = "linkedAccountsDataResident", dataProviderClass =
      LinkedAccountsTestDataProvider.class, groups = {"e2e"})
  public void linkedAccountsTest(String testCase, int linkedAccountOrder) {
    Logger.info("Verify that linked account " + linkedAccountOrder + " is able to login.");

    verifyLinkedAccount(testCase, linkedAccountOrder);
  }

  private void verifyLinkedAccount(String testCase, int linkedAccountOrder) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    String username = testSetupPage.getString("username");
    String linkedAccount1Name = testSetupPage.getString("linkedAccount1FirstName") + " " + testSetupPage.getString("linkedAccount1LastName");
    String linkedAccount2Name = testSetupPage.getString("linkedAccount2FirstName") + " " + testSetupPage.getString("linkedAccount2LastName");
    String account1logAsLinkId = testSetupPage.getString("account1logAsLinkId");
    String account2logAsLinkId = testSetupPage.getString("account2logAsLinkId");

    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.open();

    resLoginPage.login(username, null);

    ResHomePage resHomePage = new ResHomePage();

    Assert.assertTrue(resHomePage.pageIsLoaded());
    Assert.assertTrue(resHomePage.isManageAccountsTablePresent());

    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();
    resPaymentHistoryPage.open();

    Assert.assertTrue(resPaymentHistoryPage.pageIsLoaded());

    ResidentInvoicesPage residentInvoicesPage = new ResidentInvoicesPage();
    residentInvoicesPage.open();

    Assert.assertTrue(residentInvoicesPage.pageIsLoaded());

    ResidentMenuItems residentMenuItems = new ResidentMenuItems();
    residentMenuItems.clickManageAccountsTab();

    ResidentManageAccountsPage residentManageAccountsPage = new ResidentManageAccountsPage();

    Assert.assertTrue(residentManageAccountsPage.pageIsLoaded());

    resHomePage.open();
    resHomePage.clickManageAccountsLink();

    Assert.assertTrue(residentManageAccountsPage.pageIsLoaded());

    switch (linkedAccountOrder) {
      case 1:
        Assert.assertEquals(resHomePage.getWelcomeMessage(), "Welcome: " + linkedAccount1Name);
        resHomePage.clickLogAsLinkAccount(account2logAsLinkId);
        Assert.assertEquals(resHomePage.getWelcomeMessage(), "Welcome: " + linkedAccount2Name);
        resHomePage.clickLogAsLinkAccount(account1logAsLinkId);
        break;
      case 2:
        Assert.assertEquals(resHomePage.getWelcomeMessage(), "Welcome: " + linkedAccount2Name);
        resHomePage.clickLogAsLinkAccount(account1logAsLinkId);
        Assert.assertEquals(resHomePage.getWelcomeMessage(), "Welcome: " + linkedAccount1Name);
        resHomePage.clickLogAsLinkAccount(account2logAsLinkId);
        break;
    }

    ResLogoutBar resLogoutBar = new ResLogoutBar();

    resLogoutBar.clickLogoutButton();
  }

}
