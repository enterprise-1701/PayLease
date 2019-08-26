package com.paylease.app.qa.functional.tests.resident;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAccountsPage;
import com.paylease.app.qa.framework.pages.resident.ResidentAddCardAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddCardAccountTest extends ScriptBase {

  private static final String REGION = "resident";
  private static final String FEATURE = "addCardAccount";

  //-----------------------------------TESTS--------------------------------------------------------

  @Test(groups = {"litle"})
  public void tc1() {
    Logger.info("Add a card account for resident");
    addCardAccount("tc1", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID, false);
  }

  @Test(groups = {"litle"})
  public void oakwood_tc1() {
    Logger.info("Add a card account for Oakwood resident");

    addCardAccount("oakwood_tc1", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID, false);
  }

  @Test(groups = {"litle"})
  public void tc2() {
    Logger.info("Add a card account for resident - MC");
    addCardAccount("tc2", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_MC_LITLE, false);
  }

  @Test(groups = {"litle"})
  public void tc3() {
    Logger.info("Add a card account for resident - VISA");
    addCardAccount("tc3", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_VISA_LITLE, false);
  }

  @Test(groups = {"litle"})
  public void tc4() {
    Logger.info("Add a card account for resident - AMEX");
    addCardAccount("tc4", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_AMEX_LITLE, false);
  }

  @Test(groups = {"litle"})
  public void tc5() {
    Logger.info("Add a card account for resident - DISCOVER");
    addCardAccount("tc5", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID_DISCOVER_LITLE, false);
  }

  @Test(groups = {"litle"})
  public void tc6() {
    Logger.info("Add a card account for resident");
    addCardAccount("tc6", CardAccountDetailsPage.CARD_TYPE_CREDIT_VALID, true);
  }

  //------------------------------------TEST METHOD-------------------------------------------------

  private void addCardAccount(String testCase, String cardType, Boolean country) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    ResidentAccountsPage residentAccountsPage = new ResidentAccountsPage();

    residentAccountsPage.open();
    residentAccountsPage.clickAddCardAccountButton(true);

    ResidentAddCardAccountPage residentAddCardAccountPage = new ResidentAddCardAccountPage();

    residentAddCardAccountPage.prepCardType(cardType);

    if (!country) {
      residentAddCardAccountPage.fillAndSubmitCardDetails();
    } else {
      residentAddCardAccountPage.fillCardDetailsWithNonUsCountryAndSubmit();
    }

    Assert.assertEquals(residentAccountsPage.getSuccessMessage(),
        "New account has been successfully created",
        "Payment processed message should display");
  }
}
