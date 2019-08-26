package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.PmCashPayPage;
import com.paylease.app.qa.framework.pages.pm.PmFapListPage;
import com.paylease.app.qa.framework.pages.pm.PmPaymentHistoryPage;
import com.paylease.app.qa.framework.pages.pm.PmResidentListPage;
import com.paylease.app.qa.framework.pages.pm.PmVapListPage;
import com.paylease.app.qa.framework.pages.pm.ViewPropertiesPage;
import com.paylease.app.qa.framework.pages.pm.ViewSubAccountsPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ElasticSearchTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "elasticSearch";

  //-------------------------------------SUB ACCOUNTS-----------------------------------------------
  @Test
  public void subAccountPropertyName() {
    subAccountSearch("propName", true, true, false);
  }

  @Test
  public void subAccountPropertyCity() {
    subAccountSearch("propCity", false, true, false);
  }

  @Test
  public void subAccountPropertyState() {
    subAccountSearch("propState", false, true, false);
  }

  @Test
  public void subAccountPropertyZip() {
    subAccountSearch("propZip", true, true, false);
  }

  @Test
  public void subAccountPropertyRefId() {
    subAccountSearch("propRefId", true, true, false);
  }

  @Test
  public void subAccountSubName() {
    subAccountSearch("subLastName", true, false, false);
  }

  @Test
  public void subAccountSubEmail() {
    subAccountSearch("subEmail", false, false, false);
  }

  @Test
  public void subAccountSubNameDropDown() {
    subAccountSearch("subLastName", true, false, true);
  }

  @Test
  public void subAccountSubEmailDropDown() {
    subAccountSearch("subEmail", false, false, true);
  }

  @Test
  public void subAccountPropertyNameDropDown() {
    subAccountSearch("propName", true, true, true);
  }

  @Test
  public void subAccountPropertyCityDropDown() {
    subAccountSearch("propCity", false, true, true);
  }

  @Test
  public void subAccountPropertyStateDropDown() {
    subAccountSearch("propState", false, true, true);
  }

  @Test
  public void subAccountPropertyZipDropDown() {
    subAccountSearch("propZip", true, true, true);
  }

  @Test
  public void subAccountPropertyRefIdDropDown() {
    subAccountSearch("propRefId", true, true, true);
  }

  /**
   * Enter the search name, and verify the property is present in the table.
   *
   * @param fieldName search criteria
   * @param isPartial partial search term
   * @param propSearch Using property search?
   * @param dropDown Select item from dropdown
   */
  private void subAccountSearch(String fieldName, boolean isPartial, boolean propSearch,
      boolean dropDown) {
    String useDropDown = dropDown ? " and selecting Dropdown value" : "";
    Logger.info(
        "Elastic search on the PMUI - Sub Accounts page displays the correct values when searched with "
            + fieldName + useDropDown);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1");
    testSetupPage.open();
    String searchTerm = testSetupPage.getString(fieldName);
    final String subAccId = testSetupPage.getString("subAccId");
    String accId;

    if (propSearch) {
      accId = testSetupPage.getString("propRefId");
    } else {
      accId = testSetupPage.getString("subEmail");
    }

    ViewSubAccountsPage viewSubAccountsPage = new ViewSubAccountsPage();
    viewSubAccountsPage.open();

    if (isPartial) {
      searchTerm = searchTerm.substring(0, 3);
    }
    if (!propSearch) {
      viewSubAccountsPage.enterSubSearch(searchTerm);
    } else {
      viewSubAccountsPage.enterPropertySearch(searchTerm);
    }

    if (dropDown) {
      viewSubAccountsPage.clickDropDown(accId, propSearch);
    } else {
      viewSubAccountsPage.submitSearch();
    }
    boolean isPresent = viewSubAccountsPage.isSubAccountPresent(subAccId);
    Assert.assertTrue(isPresent,
        "Sub Account associated with property found by " + fieldName + " should be in results");
  }

  //-------------------------------------PROPERTY LIST----------------------------------------------
  @Test
  public void propertyNameAdvanced() {
    Logger.info(
        "Elastic search on the PMUI - View Properties page displays the correct values when searched with Property-Name/Partial Name");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString("propName");
    searchTerm = searchTerm.substring(searchTerm.length() - 3);
    String refId = testSetupPage.getString("propRefId");

    ViewPropertiesPage viewPropertiesPage = new ViewPropertiesPage();
    viewPropertiesPage.open();
    viewPropertiesPage.openAdvancedSearch();
    viewPropertiesPage.enterPropertyNameSearch(searchTerm);
    viewPropertiesPage.submitAdvancedSearch();

    boolean isPresent = viewPropertiesPage.isPropertyPresent(refId);
    Assert.assertTrue(isPresent,
        "Property found by Property name should be in results");
  }

  @Test
  public void propertyNameBasic() {
    propertySearch("propName", true, false);
  }

  @Test
  public void propertyCityBasic() {
    propertySearch("propCity", false, false);
  }

  @Test
  public void propertyStateBasic() {
    propertySearch("propState", false, false);
  }

  @Test
  public void propertyZipBasic() {
    propertySearch("propZip", true, false);
  }

  @Test
  public void propertyRefIdBasic() {
    propertySearch("propRefId", true, false);
  }

  @Test
  public void propertyNameBasicDropDown() {
    propertySearch("propName", true, true);
  }

  @Test
  public void propertyCityBasicDropDown() {
    propertySearch("propCity", false, true);
  }

  @Test
  public void propertyStateBasicDropDown() {
    propertySearch("propState", false, true);
  }

  @Test
  public void propertyZipBasicDropDown() {
    propertySearch("propZip", true, true);
  }

  @Test
  public void propertyRefIdBasicDropDown() {
    propertySearch("propRefId", true, true);
  }

  /**
   * Enter the search name, and verify the property is present in the table.
   *
   * @param fieldName search criteria
   * @param isPartial partial search term
   * @param dropDown Select item from dropdown
   */
  private void propertySearch(String fieldName, boolean isPartial, boolean dropDown) {
    String useDropDown = dropDown ? " and selecting Dropdown value" : "";
    Logger.info(
        "Elastic search on the PMUI - View Properties page displays the correct values when searched with "
            + fieldName + useDropDown);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (isPartial) {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String refId = testSetupPage.getString("propRefId");

    ViewPropertiesPage viewPropertiesPage = new ViewPropertiesPage();
    viewPropertiesPage.open();
    viewPropertiesPage.enterBasicSearch(searchTerm);
    if (dropDown) {
      viewPropertiesPage.clickDropDown(refId);
    } else {
      viewPropertiesPage.submitBasicSearch();
    }

    boolean isPresent = viewPropertiesPage.isPropertyPresent(refId);
    Assert.assertTrue(isPresent,
        "Property found by " + fieldName + " should be in results");
  }

  //-------------------------------------RESIDENT LIST----------------------------------------------
  @Test
  public void residentSearchLastName() {
    residentSearchBasic("resLastName", true, false);
  }

  @Test
  public void residentSearchAccNum() {
    residentSearchBasic("resRefId", false, false);
  }

  @Test
  public void residentSearchLastNameDropDown() {
    residentSearchBasic("resLastName", true, true);
  }

  @Test
  public void residentSearchAccNumDropDown() {
    residentSearchBasic("resRefId", false, true);
  }

  /**
   * Enter the search name, and verify the resident is present in the table.
   *
   * @param fieldName search criteria
   * @param useFirst use first part of the string
   * @param dropDown Select item from dropdown
   */
  private void residentSearchBasic(String fieldName, boolean useFirst, boolean dropDown) {
    String useDropDown = dropDown ? " and selecting Dropdown value" : "";
    Logger.info(
        "Elastic search on the PMUI - View Residents page displays the correct values when searched with "
            + fieldName + useDropDown);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (useFirst) {
      searchTerm = searchTerm.substring(0, 3);
    } else {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String resRefId = testSetupPage.getString("resRefId");

    PmResidentListPage pmResidentListPage = new PmResidentListPage();
    pmResidentListPage.open();
    pmResidentListPage.enterBasicSearch(searchTerm);
    if (dropDown) {
      pmResidentListPage.clickDropDown(resRefId, false);
    } else {
      pmResidentListPage.submitBasicSearch();
    }

    boolean isPresent = pmResidentListPage.isResidentPresent(resRefId);
    Assert.assertTrue(isPresent,
        "Resident found by " + fieldName + " should be in results");
  }

  @Test
  public void residentAdvSearchPropName() {
    residentAdvSearch("propName", true, false, false);
  }

  @Test
  public void residentAdvSearchResName() {
    residentAdvSearch("resLastName", true, true, false);
  }

  @Test
  public void residentAdvSearchPropNameDropDown() {
    residentAdvSearch("propName", true, false, true);
  }

  @Test
  public void residentAdvSearchPropCityDropDown() {
    residentAdvSearch("propCity", false, false, true);
  }

  @Test
  public void residentAdvSearchPropZipDropDown() {
    residentAdvSearch("propZip", true, false, true);
  }

  @Test
  public void residentAdvSearchPropStateDropDown() {
    residentAdvSearch("propState", false, false, true);
  }

  @Test
  public void residentAdvSearchPropRefIdDropDown() {
    residentAdvSearch("propRefId", true, false, true);
  }

  /**
   * Enter the search name, and verify the resident is present in the table.
   *
   * @param fieldName search criteria
   * @param isPartial partial search term
   * @param searchResident search by resident or property
   * @param dropDown Select item from dropdown
   */
  private void residentAdvSearch(String fieldName, boolean isPartial, boolean searchResident,
      boolean dropDown) {
    String useDropDown = dropDown ? " and selecting Dropdown value" : "";
    Logger.info(
        "Elastic search on the PMUI - View Residents page displays the correct values when searched with "
            + fieldName + useDropDown);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc12");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (isPartial) {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String resRefId = testSetupPage.getString("resRefId");
    String refId = searchResident ? resRefId : testSetupPage.getString("propRefId");

    PmResidentListPage pmResidentListPage = new PmResidentListPage();
    pmResidentListPage.open();
    pmResidentListPage.openAdvancedSearch();
    if (searchResident) {
      pmResidentListPage.enterResidentNameSearch(searchTerm);
    } else {
      pmResidentListPage.enterPropertyNameSearch(searchTerm);
    }
    if (dropDown) {
      pmResidentListPage.clickDropDown(refId, searchResident);
    } else {
      pmResidentListPage.submitAdvancedSearch();
    }

    boolean isPresent = pmResidentListPage.isResidentPresent(resRefId);
    Assert.assertTrue(isPresent,
        "Resident found by " + fieldName + " should be in results");
  }

  //-------------------------------------TRANSACTION LIST-------------------------------------------
  @Test
  public void transactionPropNameSearch() {
    transactionSearch("propName", false, true, false);
  }

  @Test
  public void transactionPropCitySearch() {
    transactionSearch("propCity", false, false, false);
  }

  @Test
  public void transactionPropStateSearch() {
    transactionSearch("propState", false, false, false);
  }

  @Test
  public void transactionPropZipSearch() {
    transactionSearch("propZip", false, true, false);
  }

  @Test
  public void transactionPropRefIdSearch() {
    transactionSearch("propRefId", false, true, false);
  }

  @Test
  public void transactionResNameSearch() {
    transactionSearch("resLastName", true, true, false);
  }

  @Test
  public void transactionResAccountSearch() {
    transactionSearch("resRefId", true, true, false);
  }

  @Test
  public void transactionPropNameSearchDropDown() {
    transactionSearch("propName", false, true, true);
  }

  @Test
  public void transactionPropCitySearchDropDown() {
    transactionSearch("propCity", false, false, true);
  }

  @Test
  public void transactionPropStateSearchDropDown() {
    transactionSearch("propState", false, false, true);
  }

  @Test
  public void transactionPropZipSearchDropDown() {
    transactionSearch("propZip", false, true, true);
  }

  @Test
  public void transactionPropRefIdSearchDropDown() {
    transactionSearch("propRefId", false, true, true);
  }

  @Test
  public void transactionResNameSearchDropDown() {
    transactionSearch("resLastName", true, true, true);
  }

  @Test
  public void transactionResAccountSearchDropDown() {
    transactionSearch("resRefId", true, true, true);
  }

  /**
   * Enter the search name, and verify the transaction is present in the table.
   *
   * @param fieldName search criteria
   * @param searchResident search by resident or property
   * @param isPartial partial search term
   * @param dropDown Select item from dropdown
   */
  private void transactionSearch(String fieldName, boolean searchResident, boolean isPartial,
      boolean dropDown) {
    String useDropDown = dropDown ? " and selecting Dropdown value" : "";
    Logger.info(
        "Elastic search on the PMUI - pm_payment_history page displays the correct values when searched with "
            + fieldName + useDropDown);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc20");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (isPartial) {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String resTransId = testSetupPage.getString("res1TransId");

    String refId =
        searchResident ? testSetupPage.getString("resRefId") : testSetupPage.getString("propRefId");

    PmPaymentHistoryPage pmPaymentHistoryPage = new PmPaymentHistoryPage();
    pmPaymentHistoryPage.open(true);

    if (searchResident) {
      pmPaymentHistoryPage.enterResidentSearch(searchTerm);
    } else {
      pmPaymentHistoryPage.enterPropertySearch(searchTerm);
    }
    if (dropDown) {
      pmPaymentHistoryPage.clickDropDown(refId);
    }
    pmPaymentHistoryPage.submitSearch();

    boolean isPresent = pmPaymentHistoryPage.isTransactionPresent(resTransId);
    Assert.assertTrue(isPresent, "Transaction found by " + fieldName + " should be in results");
  }

  //---------------------------------FIXED AUTOPAY LIST---------------------------------------------
  @Test
  public void fapPropNameSearch() {
    fixedAutopaySearch("propName", false, true, false);
  }

  @Test
  public void fapPropCitySearch() {
    fixedAutopaySearch("propCity", false, false, false);
  }

  @Test
  public void fapPropStateSearch() {
    fixedAutopaySearch("propState", false, false, false);
  }

  @Test
  public void fapPropZipSearch() {
    fixedAutopaySearch("propZip", false, true, false);
  }

  @Test
  public void fapPropRefIdSearch() {
    fixedAutopaySearch("propRefId", false, true, false);
  }

  @Test
  public void fapResNameSearch() {
    fixedAutopaySearch("resLastName", true, true, false);
  }

  @Test
  public void fapResAccountSearch() {
    fixedAutopaySearch("resRefId", true, true, false);
  }

  @Test
  public void fapPropNameSearchDropDown() {
    fixedAutopaySearch("propName", false, true, true);
  }

  @Test
  public void fapPropCitySearchDropDown() {
    fixedAutopaySearch("propCity", false, false, true);
  }

  @Test
  public void fapPropStateSearchDropDown() {
    fixedAutopaySearch("propState", false, false, true);
  }

  @Test
  public void fapPropZipSearchDropDown() {
    fixedAutopaySearch("propZip", false, true, true);
  }

  @Test
  public void fapPropRefIdSearchDropDown() {
    fixedAutopaySearch("propRefId", false, true, true);
  }

  @Test
  public void fapResNameSearchDropDown() {
    fixedAutopaySearch("resLastName", true, true, true);
  }

  @Test
  public void fapResAccountSearchDropDown() {
    fixedAutopaySearch("resRefId", true, true, true);
  }

  /**
   * Enter the search name, and verify the transaction is present in the table.
   *
   * @param fieldName search criteria
   * @param searchResident search by resident or property
   * @param isPartial partial search term
   * @param dropDown Select item from dropdown
   */
  private void fixedAutopaySearch(String fieldName, boolean searchResident, boolean isPartial,
      boolean dropDown) {
    Logger.info(
        "Elastic search on the PMUI - fixed_autopay_list page displays the correct values when searched with "
            + fieldName);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc32");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (isPartial) {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String resRefId = testSetupPage.getString("resRefId");

    String refId = searchResident ? resRefId : testSetupPage.getString("propRefId");

    PmFapListPage pmFapListPage = new PmFapListPage();
    pmFapListPage.open();

    if (searchResident) {
      pmFapListPage.enterResidentSearch(searchTerm);
    } else {
      pmFapListPage.enterPropertySearch(searchTerm);
    }
    if (dropDown) {
      pmFapListPage.clickDropDown(refId);
    }
    pmFapListPage.submitSearch();

    boolean isPresent = pmFapListPage
        .hasRowMatchingData(null, null, null, null, null, resRefId, null, null, null, null, null,
            null);
    Assert.assertTrue(isPresent, "Fixed Autopay found by " + fieldName + " should be in results");
  }

  //---------------------------------VARIABLE AUTOPAY LIST------------------------------------------
  @Test
  public void vapPropNameSearch() {
    variableAutopaySearch("propName", false, true, false);
  }

  @Test
  public void vapPropCitySearch() {
    variableAutopaySearch("propCity", false, false, false);
  }

  @Test
  public void vapPropStateSearch() {
    variableAutopaySearch("propState", false, false, false);
  }

  @Test
  public void vapPropZipSearch() {
    variableAutopaySearch("propZip", false, true, false);
  }

  @Test
  public void vapPropRefIdSearch() {
    variableAutopaySearch("propRefId", false, true, false);
  }

  @Test
  public void vapResNameSearch() {
    variableAutopaySearch("resLastName", true, true, false);
  }

  @Test
  public void vapResAccountSearch() {
    variableAutopaySearch("resRefId", true, true, false);
  }

  @Test
  public void vapPropNameSearchDropDown() {
    variableAutopaySearch("propName", false, true, true);
  }

  @Test
  public void vapPropCitySearchDropDown() {
    variableAutopaySearch("propCity", false, false, true);
  }

  @Test
  public void vapPropStateSearchDropDown() {
    variableAutopaySearch("propState", false, false, true);
  }

  @Test
  public void vapPropZipSearchDropDown() {
    variableAutopaySearch("propZip", false, true, true);
  }

  @Test
  public void vapPropRefIdSearchDropDown() {
    variableAutopaySearch("propRefId", false, true, true);
  }

  @Test
  public void vapResNameSearchDropDown() {
    variableAutopaySearch("resLastName", true, true, true);
  }

  @Test
  public void vapResAccountSearchDropDown() {
    variableAutopaySearch("resRefId", true, true, true);
  }

  /**
   * Enter the search name, and verify the transaction is present in the table.
   *
   * @param fieldName search criteria
   * @param searchResident search by resident or property
   * @param isPartial partial search term
   * @param dropDown Select item from dropdown
   */
  private void variableAutopaySearch(String fieldName, boolean searchResident, boolean isPartial,
      boolean dropDown) {
    Logger.info(
        "Elastic search on the PMUI - variable_autopay_list page displays the correct values when searched with "
            + fieldName);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc39");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (isPartial) {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String resRefId = testSetupPage.getString("resRefId");

    String refId = searchResident ? resRefId : testSetupPage.getString("propRefId");

    PmVapListPage pmVapListPage = new PmVapListPage();
    pmVapListPage.open();

    if (searchResident) {
      pmVapListPage.enterResidentSearch(searchTerm);
    } else {
      pmVapListPage.enterPropertySearch(searchTerm);
    }
    if (dropDown) {
      pmVapListPage.clickDropDown(refId);
    } else {
      pmVapListPage.submitSearch();
    }

    boolean isPresent = pmVapListPage
        .hasRowMatchingData(null, null, null, null, null, resRefId, null, null, null, null, null,
            null);
    Assert
        .assertTrue(isPresent, "Variable Autopay found by " + fieldName + " should be in results");
  }

  //-------------------------------------CASHPAY LIST-----------------------------------------------
  @Test
  public void cashpayPropNameSearch() {
    cashpaySearch("propName", false, true, false);
  }

  @Test
  public void cashpayPropCitySearch() {
    cashpaySearch("propCity", false, false, false);
  }

  @Test
  public void cashpayPropStateSearch() {
    cashpaySearch("propState", false, false, false);
  }

  @Test
  public void cashpayPropZipSearch() {
    cashpaySearch("propZip", false, true, false);
  }

  @Test
  public void cashpayPropRefIdSearch() {
    cashpaySearch("propRefId", false, true, false);
  }

  @Test
  public void cashpayResNameSearch() {
    cashpaySearch("resLastName", true, true, false);
  }

  @Test
  public void cashpayResAccountSearch() {
    cashpaySearch("resRefId", true, true, false);
  }

  @Test
  public void cashpayPropNameSearchDropDown() {
    cashpaySearch("propName", false, true, true);
  }

  @Test
  public void cashpayPropCitySearchDropDown() {
    cashpaySearch("propCity", false, false, true);
  }

  @Test
  public void cashpayPropStateSearchDropDown() {
    cashpaySearch("propState", false, false, true);
  }

  @Test
  public void cashpayPropZipSearchDropDown() {
    cashpaySearch("propZip", false, true, true);
  }

  @Test
  public void cashpayPropRefIdSearchDropDown() {
    cashpaySearch("propRefId", false, true, true);
  }

  @Test
  public void cashpayResNameSearchDropDown() {
    cashpaySearch("resLastName", true, true, true);
  }

  @Test
  public void cashpayResAccountSearchDropDown() {
    cashpaySearch("resRefId", true, true, true);
  }

  /**
   * Enter the search name, and verify the transaction is present in the table.
   *
   * @param fieldName search criteria
   * @param searchResident search by resident or property
   * @param isPartial partial search term
   * @param dropDown Select item from dropdown
   */
  private void cashpaySearch(String fieldName, boolean searchResident, boolean isPartial,
      boolean dropDown) {
    Logger.info(
        "Elastic search on the PMUI - cash_pay page displays the correct values when searched with "
            + fieldName);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc46");
    testSetupPage.open();

    String searchTerm = testSetupPage.getString(fieldName);
    if (isPartial) {
      searchTerm = searchTerm.substring(searchTerm.length() - 3);
    }
    String epsAccountId = testSetupPage.getString("epsAccountId");

    String refId =
        searchResident ? testSetupPage.getString("resRefId") : testSetupPage.getString("propRefId");

    PmCashPayPage pmCashPayPage = new PmCashPayPage();
    pmCashPayPage.open();

    if (searchResident) {
      pmCashPayPage.enterResidentSearch(searchTerm);
    } else {
      pmCashPayPage.enterPropertySearch(searchTerm);
    }
    if (dropDown) {
      pmCashPayPage.waitUntilAjaxEnds();
      pmCashPayPage.clickDropDown(refId);
    }
    pmCashPayPage.submitSearch();

    boolean isPresent = pmCashPayPage.isCardPresent(epsAccountId);
    Assert.assertTrue(isPresent, "Card found by " + fieldName + " should be in results");
  }
}