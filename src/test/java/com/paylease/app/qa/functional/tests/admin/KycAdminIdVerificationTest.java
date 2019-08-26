package com.paylease.app.qa.functional.tests.admin;

import static com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.CI11_PASS_RESPONSE;
import static com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.GIACT_ENV_FILE;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.admin.AdminHomePage;
import com.paylease.app.qa.framework.pages.admin.AdminSearchPmResultsPage;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage;
import com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.ElementType;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class KycAdminIdVerificationTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "kycAdminIdVerification";

  private static final String ERROR_TAX_ID = "Tax ID Number is required";
  private static final String URL_VARIABLE_NAME = "GIACT_API_URL";

  private static final String BASE_URL =
      ResourceFactory.getInstance().getProperty(ResourceFactory.STUB_HOST)
          + "/stub-web-service/stub/v2?service=giact";


  @BeforeClass(alwaysRun = true)
  public void setup() {
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    envWriterUtil.replaceEnvFileValue(GIACT_ENV_FILE, URL_VARIABLE_NAME, BASE_URL);
  }

  //--------------------------------ADMIN LOG AS PM KYC TESTS---------------------------------------

  @Test
  public void legalEntityPrivatePublicDropdownOptions() {
    Logger.info("To verify the available options in the Private/Public Company field dropdown");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "kycPmSetup");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");

    adminLogAsPm(pmId);

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    Assert.assertTrue(pmCustIdVerifPage.pageIsLoaded());

    List<String> optionsList = pmCustIdVerifPage.getLegalEntityPublicPrivateDropdownOptions();

    Assert.assertEquals(optionsList.size(), 2, "Expecting 2 options but found " + optionsList.size());

    Assert.assertTrue(optionsList.contains(CustomerIdVerificationPage.PRIVATE_COMPANY_TYPE), "'" + CustomerIdVerificationPage.PRIVATE_COMPANY_TYPE + "' option not found in dropdown,");
    Assert.assertTrue(optionsList.contains(CustomerIdVerificationPage.PUBLIC_COMPANY_TYPE), "'" + CustomerIdVerificationPage.PUBLIC_COMPANY_TYPE + "' option not found in dropdown,");
  }

  @Test
  public void verifyPrivatePublicDropdownIsEnabled() {
    Logger.info("To verify the Public/Private Company field dropdown is enabled for admin");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "kycPmSetup");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    String taxId = testSetupPage.getString("taxId");

    adminLogAsPm(pmId);

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.TAX_ID, taxId);

    Assert.assertTrue(
        pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_ENTITY_PRIVATE_PUBLIC_DROPDOWN),
        "Entity Type is not editable");

    pmCustIdVerifPage
        .selectPrivatePublicCompanyOption(CustomerIdVerificationPage.PUBLIC_COMPANY_TYPE);

    pmCustIdVerifPage.clickSaveAndLogOff();

    adminLogAsPm(pmId);

    pmCustIdVerifPage.open();

    String selectedOption = pmCustIdVerifPage.getLegalEntityPublicPrivateSelectedValue();

    Assert.assertEquals(selectedOption,
        CustomerIdVerificationPage.PUBLIC_COMPANY_TYPE,
        "Expected '" + CustomerIdVerificationPage.PUBLIC_COMPANY_TYPE + "' as selected option but "
            + "found '" + selectedOption + "'.");
  }

  @Test
  public void verifylegalEntityPrivatePublicDropdownDefault() {
    Logger.info("To verify the default value for Public/Private Company field dropdown");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "kycPmSetup");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");

    adminLogAsPm(pmId);

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    Assert.assertTrue(
        pmCustIdVerifPage.getLegalEntityPublicPrivateSelectedValue().equalsIgnoreCase(CustomerIdVerificationPage.PRIVATE_COMPANY_TYPE),
        "Default option is not '" + CustomerIdVerificationPage.PRIVATE_COMPANY_TYPE + "'");
  }

  @Test
  public void tc4097() {
    Logger.info("Verify that Admin Log as PM is able to submit KYC form when Tax id is masked");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4097");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    final Boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");

    adminLogAsPm(pmId);

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "100");
    pmCustIdVerifPage.fillWithValidOfficerInfo();
    pmCustIdVerifPage.clearFormField(ElementType.OFFICER_ADDRESS);
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, CI11_PASS_RESPONSE);
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickMultiSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmitAndWait();
    }

    PmHomePage pmHomePage = new PmHomePage();

    Assert.assertTrue(pmHomePage.pageIsLoaded());
  }

  @Test
  public void tc4098() {
    Logger.info("Verify that an error is shown when no value for tax ID is entered");
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4097");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");

    adminLogAsPm(pmId);

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.clearValueFromTaxIdField();

    pmCustIdVerifPage.clickNextButton();
    String taxIdError = pmCustIdVerifPage
        .getInputElementErrorMessageForCurrentTab(ElementType.TAX_ID);

    Assert.assertEquals(taxIdError, ERROR_TAX_ID, "Tax ID empty error should be shown");
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  private void adminLogAsPm(String pmId) {
    LoginPageAdmin adminLoginPagePm = new LoginPageAdmin();
    adminLoginPagePm.login();

    AdminHomePage adminHomePage = new AdminHomePage();
    adminHomePage.searchPm(pmId);

    AdminSearchPmResultsPage adminSearchPmResultsPage = new AdminSearchPmResultsPage();
    adminSearchPmResultsPage.logAsPm(pmId);
  }

}
