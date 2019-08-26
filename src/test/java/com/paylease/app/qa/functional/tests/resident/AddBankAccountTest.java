package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAddBankAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddBankAccountTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "addBankAccount";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test
  public void tc1() {
    addBankAccount("tc1");
  }

  @Test
  public void oakwood_tc1() {
    addBankAccount("oakwood_tc1");
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private void addBankAccount(String testCase) {
    Logger.info("Add a bank account for resident");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();
    residentAccountsPage.clickAddBankAccountButton(true);

    ResidentAddBankAccountPage residentAddBankAccountPage = new ResidentAddBankAccountPage();

    residentAddBankAccountPage.fillBankDetailsAndSubmit();

    Assert.assertEquals(residentAccountsPage.getSuccessMessage(),
        "New account has been successfully created", "Success message should display");
  }
}
