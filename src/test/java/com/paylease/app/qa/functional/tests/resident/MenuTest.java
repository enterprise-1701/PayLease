package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.resident.ResAutoPayListPage;
import com.paylease.app.qa.framework.pages.resident.ResHomePage;
import com.paylease.app.qa.framework.pages.resident.ResPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage;
import com.paylease.app.qa.framework.pages.resident.ResidentMakePayment;
import com.paylease.app.qa.framework.pages.resident.ResidentMenuItems;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResBillingPage;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResMyProfilePage;
import com.paylease.app.qa.framework.pages.resident.ResidentInvoicesPage;
import com.paylease.app.qa.framework.pages.resident.ResidentManageAccountsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MenuTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "menu";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    Logger.info("Click and navigate to invoices tab");

    clickOnInvoicesTab("tc1");
  }

  @Test
  public void oakwood_tc1() {
    Logger.info("Click and navigate to invoices tab - Oakwood");

    clickOnInvoicesTab("oakwood_tc1");
  }

  @Test
  public void tc2() {
    Logger.info("Click and navigate to manage accounts tab");

    clickOnManageAccountsTab("tc2");
  }

  @Test
  public void oakwood_tc2() {
    Logger.info("Click and navigate to manage accounts tab - Oakwood");

    clickOnManageAccountsTab("oakwood_tc2");
  }

  @Test
  public void tc3() {
    Logger.info("Click and navigate to my profile tab");

    clickOnMyProfileTab("tc3");
  }

  @Test
  public void oakwood_tc3() {
    Logger.info("Click and navigate to my profile tab - Oakwood");

    clickOnMyProfileTab("oakwood_tc3");
  }

  @Test
  public void tc4() {
    Logger.info("Click and navigate to billing tab");

    clickOnBillingTab("tc4");
  }

  @Test
  public void amsi_tc4() {
    Logger.info("Click and navigate to billing tab - AMSI");

    clickOnBillingTab("amsi_tc4");
  }

  @Test
  public void tc5(){
    Logger.info("To validate that user is able to navigate to Make Payment tab from home screen");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc5");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResidentMenuItems residentMenuItems = new ResidentMenuItems();
    ResidentMakePayment residentMakePayment = new ResidentMakePayment();

    resHomePage.open();

    residentMenuItems.clickMakePaymentTab();

    Assert.assertTrue(residentMakePayment.pageIsLoaded(),
        "User did not navigate to Make Payment tab");
  }

  @Test
  public void tc6(){
    Logger.info("To validate that user is able to navigate to AutoPay tab from home screen");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResidentMenuItems residentMenuItems = new ResidentMenuItems();
    ResAutoPayListPage resAutoPayListPage = new ResAutoPayListPage();

    resHomePage.open();

    residentMenuItems.clickAutopayTab();

    Assert.assertTrue(resAutoPayListPage.pageIsLoaded(),
        "User did not navigate to autopay page");
  }

  @Test
  public void tc7(){
    Logger.info("To validate that user is able to navigate to Payment History tab from home screen ");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc7");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResidentMenuItems residentMenuItems = new ResidentMenuItems();
    ResPaymentHistoryPage resPaymentHistoryPage = new ResPaymentHistoryPage();

    resHomePage.open();

    residentMenuItems.clickPaymentHistoryTab();

    Assert.assertTrue(resPaymentHistoryPage.pageIsLoaded(),
        "User did not navigate to payment history page");
  }

  @Test
  public void tc8(){
    Logger.info("To validate that user is able to navigate to My payment methods tab from home screen");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc8");
    testSetupPage.open();

    ResHomePage resHomePage = new ResHomePage();
    ResidentMenuItems residentMenuItems = new ResidentMenuItems();
    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    resHomePage.open();

    residentMenuItems.clickPaymentMethodsTab();

    Assert.assertTrue(residentAccountsPage.pageIsLoaded(),
        "User did not navigate to payment methods page");
  }
  //------------------------------------TEST METHOD-------------------------------------------------

  private ResidentMenuItems testPrep(String testCase) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    ResLoginPage resLoginPage = new ResLoginPage();

    resLoginPage.openResidentPage();

    return new ResidentMenuItems();
  }

  private void clickOnInvoicesTab(String testCase) {
    ResidentMenuItems residentMenuItems = testPrep(testCase);

    residentMenuItems.clickInvoicesTab();

    ResidentInvoicesPage residentInvoicesPage = new ResidentInvoicesPage();

    Assert.assertTrue(residentInvoicesPage.pageIsLoaded(), "Resident invoices page should load");
  }

  private void clickOnManageAccountsTab(String testCase) {
    ResidentMenuItems residentMenuItems = testPrep(testCase);

    residentMenuItems.clickManageAccountsTab();

    ResidentManageAccountsPage residentManageAccountsPage = new ResidentManageAccountsPage();

    Assert.assertTrue(residentManageAccountsPage.pageIsLoaded(),
        "Resident manage accounts page should load");
  }

  private void clickOnMyProfileTab(String testCase) {
    ResidentMenuItems residentMenuItems = testPrep(testCase);

    residentMenuItems.clickMyProfile();

    ResMyProfilePage resMyProfilePage = new ResMyProfilePage();

    Assert.assertTrue(resMyProfilePage.pageIsLoaded(), "Resident my profile page should load");
  }

  private void clickOnBillingTab(String testCase) {
    ResidentMenuItems residentMenuItems = testPrep(testCase);

    residentMenuItems.clickBilling();

    ResBillingPage resBillingPage = new ResBillingPage();

    Assert.assertTrue(resBillingPage.pageIsLoaded(), "Resident billing page should load");
  }
}
