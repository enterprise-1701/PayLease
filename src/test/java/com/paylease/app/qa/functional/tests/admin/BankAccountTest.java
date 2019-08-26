package com.paylease.app.qa.functional.tests.admin;

import static org.testng.Assert.assertEquals;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.UtilityManager;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.admin.bankaccount.FormCreatePage;
import com.paylease.app.qa.framework.pages.admin.bankaccount.FormEditPage;
import com.paylease.app.qa.framework.pages.admin.bankaccount.ListPage;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import java.util.HashMap;
import org.testng.annotations.Test;

public class BankAccountTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "bankAccounts";
  private static final String EXPECTED_NAME = "bankName";
  private static final String EXPECTED_ROUTING_NUMBER = "routingNumber";
  private static final String EXPECTED_ACCOUNT_NUMBER = "accountNumber";
  private static final String EXPECTED_ACCOUNT_TYPE = "accountType";

  @Test
  public void createBankAccount() {
    Logger.info("Create a Bank Account");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final String routingNumber = testSetupPage.getString("routingNumber", "Valid Routing Number");

    LoginPageAdmin adminLoginPagePm = new LoginPageAdmin();
    adminLoginPagePm.login();

    ListPage bankAccountListPage = new ListPage(pmId);
    bankAccountListPage.open();

    FormCreatePage bankAccountFormPage = bankAccountListPage.clickAddNewBankAccountButton();

    Faker faker = new Faker();
    String bankName = UtilityManager.getUniqueString();
    bankAccountFormPage.setBankName(bankName);

    final String accountType = bankAccountFormPage.setAccountType();

    bankAccountFormPage.setRoutingNumber(routingNumber);

    String accountNumber = faker.number().digits(9);
    bankAccountFormPage.setAccountNumber(accountNumber);
    bankAccountFormPage.setAccountNumberConfirm(accountNumber);

    bankAccountListPage = bankAccountFormPage.clickSave();

    HashMap<String, String> accountTableMap = bankAccountListPage.getTableRowByBankName(bankName);
    Logger.trace(
        "Bank Account created with Account ID: " + accountTableMap.get(ListPage.COLUMN_ACCOUNT_ID));

    HashMap<String, String> expectedValues = new HashMap<>();
    expectedValues.put(ListPage.COLUMN_ROUTING_NUMBER, routingNumber);
    expectedValues.put(ListPage.COLUMN_ACCOUNT_NUMBER, accountNumber);
    expectedValues.put(ListPage.COLUMN_ACCOUNT_TYPE, accountType.toLowerCase());

    assertExpectedMap(accountTableMap, expectedValues);
  }

  @Test
  public void editBankAccount() {
    Logger.info("Edit Bank Account");

    class TestDataObj {

      private String pmId;
      private String bankAccountId;
      private String bankName;
      private String routingNumber;
      private String accountNumber;
      private String accountType;
      private String newRoutingNumber;

      private TestDataObj(TestSetupPage testSetupPage) {
        pmId = testSetupPage.getString("pmId");
        bankAccountId = testSetupPage.getString("accountId");
        bankName = testSetupPage.getString("bankName");
        routingNumber = testSetupPage.getString("routingNumber");
        accountNumber = testSetupPage.getString("accountNumber");
        accountType = testSetupPage.getString("accountType");
        newRoutingNumber = testSetupPage.getString("validRoutingNumber");
      }
    }

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc02");
    testSetupPage.open();
    TestDataObj testDataObj = new TestDataObj(testSetupPage);

    LoginPageAdmin adminLoginPagePm = new LoginPageAdmin();
    adminLoginPagePm.login();

    ListPage bankAccountListPage = new ListPage(testDataObj.pmId);
    bankAccountListPage.open();

    HashMap<String, String> expectedTableValues = new HashMap<>();
    expectedTableValues.put(EXPECTED_NAME, testDataObj.bankName);
    expectedTableValues.put(EXPECTED_ROUTING_NUMBER, testDataObj.routingNumber);
    expectedTableValues.put(EXPECTED_ACCOUNT_NUMBER, testDataObj.accountNumber);
    expectedTableValues.put(EXPECTED_ACCOUNT_TYPE, testDataObj.accountType.toLowerCase());

    HashMap<String, String> actualTableValues = bankAccountListPage
        .getTableRowById(testDataObj.bankAccountId);

    assertExpectedMap(actualTableValues, expectedTableValues);

    FormEditPage bankAccountEditPage = bankAccountListPage.clickEditLink(testDataObj.bankAccountId);

    assertEquals(testDataObj.bankName, bankAccountEditPage.getBankName(),
        "Expect form displays Bank Name as given");
    assertEquals(testDataObj.accountType, bankAccountEditPage.getAccountType(),
        "Expect form displays Account Type as given");
    assertEquals(testDataObj.routingNumber, bankAccountEditPage.getRoutingNumber(),
        "Expect form displays Routing Number as given");
    assertEquals(testDataObj.accountNumber, bankAccountEditPage.getAccountNumber(),
        "Expect form displays Account Number as given");
    assertEquals(testDataObj.accountNumber, bankAccountEditPage.getAccountNumberConfirm(),
        "Expect form displays Account Number (confirmed) as given");

    Faker faker = new Faker();
    String newBankName = UtilityManager.getUniqueString();
    bankAccountEditPage.setBankName(newBankName);
    String newAccountType = testDataObj.accountType.equals("Checking") ? "Savings" : "Checking";

    bankAccountEditPage.setAccountType(newAccountType);

    bankAccountEditPage.setRoutingNumber(testDataObj.newRoutingNumber);

    String newAccountNumber = faker.number().digits(9);
    bankAccountEditPage.setAccountNumber(newAccountNumber);
    bankAccountEditPage.setAccountNumberConfirm(newAccountNumber);

    bankAccountListPage = bankAccountEditPage.clickSave();

    expectedTableValues = new HashMap<>();
    expectedTableValues.put(EXPECTED_NAME, newBankName);
    expectedTableValues.put(EXPECTED_ROUTING_NUMBER, testDataObj.newRoutingNumber);
    expectedTableValues.put(EXPECTED_ACCOUNT_NUMBER, newAccountNumber);
    expectedTableValues.put(EXPECTED_ACCOUNT_TYPE, newAccountType.toLowerCase());

    actualTableValues = bankAccountListPage.getTableRowById(testDataObj.bankAccountId);

    assertExpectedMap(actualTableValues, expectedTableValues);
  }
}
