package com.paylease.app.qa.functional.tests.pm;

import static com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.CI11_PASS_RESPONSE;
import static com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.GIACT_ENV_FILE;
import static com.paylease.app.qa.framework.utility.emailtool.EmailProcessor.EMAIL_RETRY_COUNT;
import static com.paylease.app.qa.framework.utility.emailtool.EmailProcessor.EMAIL_RETRY_TIMEOUT;
import static com.paylease.app.qa.testbase.dataproviders.GiactErrorsSolutionsDataProvider.CUSTOMER_RESPONSE_CI21;
import static com.paylease.app.qa.testbase.dataproviders.GiactErrorsSolutionsDataProvider.OFAC_PASS_1_CAM_RESPONSE;
import static com.paylease.app.qa.testbase.dataproviders.GiactErrorsSolutionsDataProvider.OFAC_PASS_NO_CAM_RESPONSE;

import com.paylease.app.qa.framework.AppConstant;
import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.Login;
import com.paylease.app.qa.framework.components.Login.UserType;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage;
import com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.ElementType;
import com.paylease.app.qa.framework.pages.pm.KycHoldPage;
import com.paylease.app.qa.framework.pages.pm.LitleAgreementPage;
import com.paylease.app.qa.framework.pages.pm.PmHomePage;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmMenu;
import com.paylease.app.qa.framework.utility.database.client.dao.PmOfficersDAO;
import com.paylease.app.qa.framework.utility.database.client.dto.PmOfficersDTO;
import com.paylease.app.qa.framework.utility.emailtool.EmailProcessor;
import com.paylease.app.qa.framework.utility.emailtool.MessagePart;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.mail.Message;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class KycIdVerificationTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "Kyc";
  private static final String SSN_SOLUTION_KEY = "kyc_hold_upload_ssn_document";
  private static final String DRIVERS_LICENSE_SOLUTION_KEY = "kyc_hold_upload_drivers_license";
  private static final String PERCENT_OF_OWNERSHIP_ERR_MSG = "Percent of Ownership is required";
  private static final String FIRST_NAME_REQD_ERR_MSG = "First Name is required";
  private static final String LAST_NAME_REQD_ERR_MSG = "Last Name is required";
  private static final String TITLE_REQD_ERR_MSG = "Title is required";
  private static final String TEST_FILE_NAME = "test.pdf";
  private static final String EMAIL_REQD_ERR_MSG = "Email is required";
  private static final String DL_ADDRESS_REQD_ERR_MSG = "Residential Address is required";
  private static final String DL_CITY_REQD_ERR_MSG = "Residential City is required";
  private static final String DL_STATE_REQD_ERR_MSG = "Residential State is required";
  private static final String DL_ZIP_REQD_ERR_MSG = "Residential Zip is required";
  private static final String DL_NUM_REQD_ERR_MSG = "Driver's License # is required";
  private static final String DOB_REQD_ERR_MSG = "DOB is required";
  private static final String SSN_REQD_ERR_MSG = "SSN is required";
  private static final String NEGATIVE_OWNER_PERCENT_ERR_MSG = "Ownership must be a whole number between 0 and 100";
  private static final String NON_FORMATTED_EMAIL_ADDRESS_ERR_MSG = "Email is not formatted correctly";
  private static final String NON_NUMERIC_ZIP_ERR_MSG = "Residential Zip must be a number";
  private static final String NON_NUMERIC_SSN_ERR_MSG = "SSN must contain only numbers";
  private static final String OFFICER_TYPE_PRIMARY = "primary";
  private static final String OFFICER_TYPE_SECONDARY = "secondary";
  private static final String OFFICER_TYPE_TERTIARY = "tertiary";
  private static final String ZIP_CODE_WRONG_LENGTH = "Residential Zip must be 5 characters";
  private static final Integer DOC_TYPE_SSN_PRIMARY_OFFICER_UPLOAD = 23;
  private static final String EMAIL_MANUAL_REVIEW_SUBJECT = "PM KYC Status is in Manual Review";
  private static final String EMAIL_VERIFIED_SUBJECT = "PM KYC Status is Verified";
  private static final String EMAIL_UPLOADED_DOCUMENTS_SUBJECT = "PM has uploaded documents";
  private static final String EMAIL_MANUAL_REVIEW = "The property management company {pmCompany} (pm id: {pmId}) was placed on manual review after failing automated verification";
  private static final String EMAIL_VERIFIED = "{pmCompany} (pm id: {pmId}) was Verified on";
  private static final String EMAIL_UPLOADED_DOCUMENTS = "{pmCompany} (pm id: {pmId}) submitted documents";
  private static final String URL_VARIABLE_NAME = "GIACT_API_URL";

  private static final String BASE_URL =
      ResourceFactory.getInstance().getProperty(ResourceFactory.STUB_HOST)
          + "/stub-web-service/stub/v2?service=giact";

  @BeforeClass(alwaysRun = true)
  public void setup() {
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    envWriterUtil.replaceEnvFileValue(GIACT_ENV_FILE, URL_VARIABLE_NAME, BASE_URL);
  }

  //--------------------------------KYC ID VERIFICATION TESTS---------------------------------------

  @Test
  public void kycTc01() {
    Logger.info(
        "To validate that the PM is taken to the onboarding form when they navigate to /pm/dashboard/customer_id_verification");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    Assert.assertTrue(pmCustIdVerifPage.isFormVisible());
  }

  @Test
  public void kycTc03_Tc10() {
    Logger.info(
        "To validate that the Legal Entity type, Private or Public Company, formation date, name, tax id, address, city, state, zip, and country are read only fields");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc03_tc10");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    SoftAssert softAssert = new SoftAssert();

    softAssert.assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_ENTITY_TYPE),
        "Entity Type is editable");
    softAssert.assertFalse(
        pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_ENTITY_PRIVATE_PUBLIC_DROPDOWN),
        "Entity Type is editable");
    softAssert.assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.FORMATION_DATE),
        "Formation Date is editable");
    softAssert.assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.BUSINESS_NAME),
        "Name is editable");
    softAssert.assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.TAX_ID),
        "Tax Id is editable");
    softAssert
        .assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_BUSINESS_ADDRESS),
            "Address is editable");
    softAssert.assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_BUSINESS_CITY),
        "City is editable");
    softAssert
        .assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_BUSINESS_STATE),
            "State is editable");
    softAssert.assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_BUSINESS_ZIP),
        "Zip is editable");
    softAssert
        .assertFalse(pmCustIdVerifPage.isElementTypeEditable(ElementType.LEGAL_BUSINESS_COUNTRY),
            "Country is editable");

    softAssert.assertAll();
  }

  @Test
  public void kycTc11() {
    Logger.info(
        "To validate that clicking the 'Continue' button on the Legal Entity Tab navigates the PM to the Control Person Tab");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    Assert.assertEquals(pmCustIdVerifPage.getCurrentActiveTabName(),
        CustomerIdVerificationPage.TAB_2_CONTROL_PERSON,
        "The current tab is not the Control Person Tab");

  }

  @Test
  public void kycTc12_Tc23() {
    Logger.info(
        "To validate that Percent of Ownership (Whole Number between 0 – 100), Control Person First Name, "
            +
            "Control Person Last Name, Control Person Title, Control Person Email Address, " +
            "Control Person Residential Address, Control Person Residential City, " +
            "Control Person Residential State, Control Person Residential Zip, " +
            "Control Person Driver's License #, Control Person DOB, Control Person SSN are mandatory fields");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();

    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickNextButton();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_PERCENT),
        PERCENT_OF_OWNERSHIP_ERR_MSG, "Control Person Percent of Ownership is not mandatory");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_FIRST_NAME),
        FIRST_NAME_REQD_ERR_MSG, "Control Person First Name is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_LAST_NAME),
        LAST_NAME_REQD_ERR_MSG, "Control Person Last Name is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_TITLE),
        TITLE_REQD_ERR_MSG, "Control Person Title is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_EMAIL),
        EMAIL_REQD_ERR_MSG, "Control Person Email is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ADDRESS),
        DL_ADDRESS_REQD_ERR_MSG,
        "Control Person Residential Address is not a mandatory field.");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_CITY),
        DL_CITY_REQD_ERR_MSG, "Control Person Residential City is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_STATE),
        DL_STATE_REQD_ERR_MSG, "Control Person Residential State is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ZIP),
        DL_ZIP_REQD_ERR_MSG, "Control Person Residential Zip is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_LICENSE),
        DL_NUM_REQD_ERR_MSG, "Control Person Driver's License # is not a mandatory field");
    softAssert
        .assertEquals(
            pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_DOB),
            DOB_REQD_ERR_MSG,
            "Control Person DOB is not a mandatory field");
    softAssert
        .assertEquals(
            pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_SSN),
            SSN_REQD_ERR_MSG,
            "Control Person SSN is not a mandatory field");
    softAssert.assertAll();
  }

  @Test
  public void kycTc25() {
    Logger.info(
        "To validate that the PM is not able to enter a negative number for Percent of ownership ");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "-25");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_PERCENT),
        NEGATIVE_OWNER_PERCENT_ERR_MSG, "Percent of Ownership can be negative");
  }

  @Test
  public void kycTc26() {
    Logger.info(
        "To validate that the PM gets an error message when the email address is not properly formatted.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_EMAIL, "incorrect@email");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_EMAIL),
        NON_FORMATTED_EMAIL_ADDRESS_ERR_MSG,
        "Email Address does not have to be correctly formatted");
  }

  @Test
  public void kycTc27() {
    Logger.info(
        "To validate that the PM gets an error message when the Control Person Residential Zip is non numeric.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_ZIP, "921ZZ");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ZIP),
        NON_NUMERIC_ZIP_ERR_MSG, "Zip code can be non-numeric");
  }

  @Test
  public void kycTc28() {
    Logger.info(
        "To validate that the PM gets an error message when the Control Person SSN is non numeric.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_SSN, "921ZZ3434");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_SSN),
        NON_NUMERIC_SSN_ERR_MSG, "SSN can be non-numeric");
  }


  @Test
  public void kycTc29() {
    Logger.info(
        "To validate that the PM is navigated to the the Legal Entity tab when the user clicks on the Edit Legal Entity button.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();

    pmCustIdVerifPage.goToPreviousTab();

    Assert.assertEquals(pmCustIdVerifPage.getCurrentActiveTabName(), "Legal Entity",
        "The current tab is not the Legal Entity Tab");
  }

  @Test
  public void kycTc30() {
    Logger.info(
        "To validate that the user is not able to add a new owner if the \"Is there an additional Beneficial Owner with ownership greater than or equal to 25%?\" is selected as NO .");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();

    Assert.assertFalse(pmCustIdVerifPage.isNextOwnerButtonDisplayed(),
        "Next Owner button is present");
    Assert.assertTrue(pmCustIdVerifPage.isSubmitButtonDisplayed(), "Submit Button is not present");
  }

  @Test
  public void kycTc31() {
    Logger.info(
        "To validate that the user is  able to add a new owner if the \"Is there an additional Beneficial Owner with ownership greater than or equal to 25%?\" is selected as YES .");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    Assert.assertTrue(pmCustIdVerifPage.isNextOwnerButtonDisplayed(),
        "Next Owner button is not present");
    Assert.assertFalse(pmCustIdVerifPage.isSubmitButtonDisplayed(), "Submit button is present");
  }

  @Test
  public void kycTc32() {
    Logger.info(
        "To validate that the PM is navigated to the Beneficial Owner tab when they want to add a new beneficial owner.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    Assert.assertEquals(pmCustIdVerifPage.getCurrentTab(), 2, "The current tab is not the 2nd tab");

    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    pmCustIdVerifPage.doContinueToNextTab();
    Assert.assertEquals(pmCustIdVerifPage.getCurrentTab(), 3, "The current tab is not the 3rd Tab");
    Assert.assertEquals(pmCustIdVerifPage.getCurrentActiveTabName(),
        CustomerIdVerificationPage.TAB_3_BENEFICIAL_OWNER,
        "The current tab is not a Beneficial Owner Tab");
  }

  @Test
  public void kycTc33_44() {
    Logger.info(
        "To validate that Percent of Ownership (Whole Number between 0 – 100), Control Person First Name, "
            +
            "Control Person Last Name, Control Person Title, Control Person Email Address, " +
            "Control Person Residential Address, Control Person Residential City, " +
            "Control Person Residential State, Control Person Residential Zip, " +
            "Control Person Driver's License #, Control Person DOB, Control Person SSN are mandatory fields");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    //validation should fail and prevent navigation to next tab
    pmCustIdVerifPage.clickNextButton();
    Assert.assertEquals(pmCustIdVerifPage.getCurrentTab(), 3,
        "The current tab is not Beneficial owner tab");

    SoftAssert softAssert = new SoftAssert();

    softAssert
        .assertEquals(
            pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_PERCENT),
            PERCENT_OF_OWNERSHIP_ERR_MSG, "Beneficial Owner Percent of Ownership is not mandatory");
    softAssert
        .assertEquals(pmCustIdVerifPage
                .getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_FIRST_NAME),
            FIRST_NAME_REQD_ERR_MSG, "Beneficial Owner First Name is not a mandatory field");
    softAssert
        .assertEquals(pmCustIdVerifPage
                .getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_LAST_NAME),
            LAST_NAME_REQD_ERR_MSG, "Beneficial Owner Last Name is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_TITLE),
        TITLE_REQD_ERR_MSG, "Beneficial Owner Title is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_EMAIL),
        EMAIL_REQD_ERR_MSG, "Beneficial Owner Email is not a mandatory field");
    softAssert
        .assertEquals(
            pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ADDRESS),
            DL_ADDRESS_REQD_ERR_MSG,
            "Beneficial Owner Residential Address is not a mandatory field.");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_CITY),
        DL_CITY_REQD_ERR_MSG, "Beneficial Owner Residential City is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_STATE),
        DL_STATE_REQD_ERR_MSG, "Beneficial Owner Residential State is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ZIP),
        DL_ZIP_REQD_ERR_MSG, "Beneficial Owner Residential Zip is not a mandatory field");
    softAssert
        .assertEquals(
            pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_LICENSE),
            DL_NUM_REQD_ERR_MSG, "Beneficial Owner Driver's License # is not a mandatory field");
    softAssert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_DOB),
        DOB_REQD_ERR_MSG, "Beneficial Owner DOB is not a mandatory field");
    softAssert
        .assertEquals(
            pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_SSN),
            SSN_REQD_ERR_MSG,
            "Beneficial Owner SSN is not a mandatory field");

    softAssert.assertAll();
  }

  @Test
  public void kycTc46() {
    Logger.info(
        "To validate that the PM is not able to enter a value less than 25% in the ownership % field");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    final boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.clearFormField(ElementType.OFFICER_PERCENT);
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "20");
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickMultiSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmit();
    }

    Assert.assertTrue(pmCustIdVerifPage.isServerPercentageErrorDisplayed(3),
        "Officer percentage error requiring percentage > 25 should display");
  }

  @Test
  public void kycTc48() {
    Logger.info(
        "To validate that the PM gets an error message when the email address is not properly formatted.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(1);
    pmCustIdVerifPage.doContinueToNextTab();

    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_EMAIL, "incorrect@email");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_EMAIL),
        NON_FORMATTED_EMAIL_ADDRESS_ERR_MSG,
        "Email Address does not have to be correctly formatted");
  }

  @Test
  public void kycTc50() {
    Logger.info(
        "To validate that the PM gets an error message when the Control Person SSN is non numeric.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(1);
    pmCustIdVerifPage.doContinueToNextTab();

    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_SSN, "921ZZ3434");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_SSN),
        NON_NUMERIC_SSN_ERR_MSG, "SSN can be non-numeric");
  }

  @Test
  public void kycTc51() {
    Logger.info(
        "To validate that the PM is navigated to the the Control Person tab when the user clicks on the Previous owner button.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(1);
    pmCustIdVerifPage.doContinueToNextTab();

    pmCustIdVerifPage.goToPreviousTab();
    Assert.assertEquals(pmCustIdVerifPage.getCurrentActiveTabName(), "Control Person",
        "The current tab is not the Control Person Tab");
  }

  @Test
  public void kycTc52() {
    Logger.info(
        "To validate that the user is not able to add a new owner if the 'Is there an additional Beneficial Owner with ownership greater than or equal to 25%?' is selected as NO .");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(1);

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();

    Assert.assertFalse(pmCustIdVerifPage.isNextOwnerButtonDisplayed(),
        "Next Owner button is present");
    Assert.assertTrue(pmCustIdVerifPage.isSubmitButtonDisplayed(), "Submit Button is not present");
  }

  @Test
  public void kycTc53() {
    Logger.info(
        "To validate that the user is  able to add a new owner if the \"Is there an additional Beneficial Owner with ownership greater than or equal to 25%?\" is selected as YES .");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(1);

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    Assert.assertTrue(pmCustIdVerifPage.isNextOwnerButtonDisplayed(),
        "Next Owner button is not present");
    Assert.assertFalse(pmCustIdVerifPage.isSubmitButtonDisplayed(), "Submit button is present");
  }

  @Test
  public void kycTc54() {
    Logger.info(
        "To validate that the PM is able to remove an Owner.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(1);

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(pmCustIdVerifPage.isRemoveOwnerButtonDisplayed(),
        "Remove owner button is not present");

    pmCustIdVerifPage.clickRemoveOwnerButton();

    softAssert.assertEquals(pmCustIdVerifPage.getCurrentTab(), 2);

    softAssert.assertAll();
  }

  @Test
  public void kycTc55() {
    Logger.info(
        "To validate that the PM is able to remove an Owner.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.fillGivenNumberOfOfficerTabs(4);

    pmCustIdVerifPage.doContinueToNextTab();

    Assert.assertFalse(pmCustIdVerifPage.isNextOwnerButtonDisplayed(),
        "Next Owner button is present");
    Assert.assertTrue(pmCustIdVerifPage.isCompletedSubmitButtonDisplayed(),
        "Submit Button is not present");

  }

  @Test
  public void kycTc56() {
    Logger.info(
        "To validate that all the information filled in is successfully saved in the PM_Officers table.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    final String pmId = testSetupPage.getString("pmId");
    final boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    Map<ElementType, String> formValues = pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickMultiSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmit();
    }

    EnumMap<ElementType, String> dbOfficerValues = getPmOfficerRowfromDb(pmId);
    for (Map.Entry<ElementType, String> entry : dbOfficerValues.entrySet()) {
      Assert.assertEquals(formValues.get(entry.getKey()), entry.getValue(),
          "Form field: " + entry.getKey() + " has matching value in database");
    }
  }

  @Test
  public void kycTc65() {
    Logger.info(
        "To validate that the PM with Sole Proprietership entity type has the Percent of Ownership pre-populated with a 100% on the control person tab.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc65_66");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    Assert.assertEquals(pmCustIdVerifPage.getPercentOwnership(), "100%",
        "The percent of ownership is not 100");

  }

  @Test
  public void kycTc66() {
    Logger.info(
        "To validate that the Pm is not able to add a new Beneficial Owner.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc65_66");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();

    Assert.assertFalse(pmCustIdVerifPage.isNextOwnerSolePropPresent(),
        "Next Owner button is present");
    Assert.assertTrue(pmCustIdVerifPage.isSoleProprietorshipSubmitDisplayed(),
        "Submit Button is not present");
  }

  @Test
  public void kycTc67() {
    Logger.info(
        "To validate that the PM is able to enter the Percent of Ownership on the control person tab.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc67_69");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    Assert.assertTrue(pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_PERCENT),
        "Officer Percent is not editable");
  }

  @Test
  public void kycTc68() {
    Logger.info(
        "To validate that the Pm is able to add 1 new Beneficial Owner.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc67_69");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();

    Assert.assertTrue(pmCustIdVerifPage.isNextOwnerDisplayed(),
        "Next Owner button is not present");
    Assert
        .assertFalse(pmCustIdVerifPage.isPartnerSubmitButtonPresent(), "Submit button is present");

    pmCustIdVerifPage.clickPartnerNextOwner();
    Assert.assertEquals(pmCustIdVerifPage.getCurrentActiveTabName(), "Beneficial Owner",
        "The current tab is not the Beneficial Owner Tab");
  }

  @Test
  public void kycTc69() {
    Logger.info(
        "To validate that the PM is able to add more than 1 beneficial owner.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc67_69");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();

    pmCustIdVerifPage.clickPartnerNextOwner();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    Assert.assertTrue(pmCustIdVerifPage.isNextOwnerPartnershipDisplayed(),
        "Next Owner button not present");
  }

  @Test
  public void kycTc70() {
    Logger.info(
        "To validate that the PM is taken to the KYC screens on logging in for the first time.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();

    Assert.assertTrue(pmCustIdVerifPage.pageIsLoaded(),
        "should be navigated to Customer Id Verification page");
  }

  @Test
  public void kycTc71() {
    Logger.info(
        "To validate that the PM is not able to navigate away from the KYC forms and enter the PMUI without filling the forms.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    PmMenu pmMenu = new PmMenu();
    pmMenu.clickOneTimePaymentTab();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();

    Assert.assertTrue(pmCustIdVerifPage.pageIsLoaded(),
        "should be navigated to Customer Id Verification page");
  }

  @Test
  public void kycTc72() {
    Logger.info(
        "To validate that the PM is directed to the Litle agreement after the KYC forms are filled.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc72");
    testSetupPage.open();

    final Boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm(true);
    pmCustIdVerifPage.clickSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmitAndWait();
    }

    LitleAgreementPage agreementPage = new LitleAgreementPage();

    Assert.assertTrue(agreementPage.pageIsLoaded(),
        "should be navigated to Litle Agreement page");
  }

  @Test
  public void kyc73() {
    Logger.info(
        "To validate that once the KYC information is collected,"
            + " the Vantiv agreement is completed and the user is able to proceed to the rest of UI,"
            + " the KYC screen is never displayed again whenever the Property Manager logs in again");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc73");
    testSetupPage.open();
    final Boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm(true);
    pmCustIdVerifPage.clearFormField(ElementType.OFFICER_ADDRESS);
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, CI11_PASS_RESPONSE);
    pmCustIdVerifPage.clickSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmitAndWait();
    }

    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    LitleAgreementPage agreementPage = new LitleAgreementPage();

    Assert.assertFalse(pmCustIdVerifPage.pageIsLoaded(),
        "should not redirect to Customer Id Verification page");

    Assert.assertFalse(agreementPage.pageIsLoaded(),
        "should not redirect to Litle Agreement page");
  }

  @Test
  public void kyc76() {
    Logger.info(
        "To validate that the Welcome verbiage to be displayed on the KYC Screens");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();
    Assert.assertTrue(pmCustIdVerifPage.isWelcomeMessageDisplayed());
  }

  @Test
  public void tc90() {
    this.kycHold(
        "tc90",
        "To validate that the PM processing only CC -litle is not able to proceed into the Paylease system if they have not been onboarded with litle"
    );
  }

  @Test
  public void tc91() {
    this.kycHold(
        "tc91",
        "To validate that the PM processing only profitstars is not able to proceed into  the Paylease system if they have not been onboarded with profitstars"
    );
  }

  @Test
  public void tc92() {
    this.kycHold(
        "tc92",
        "To validate that the PM is not able to proceed into the Paylease system if the PM is just using check scanning."
    );
  }

  @Test
  public void tc95() {
    this.kycHold(
        "tc95",
        "To validate that the PM is not able to navigate into paylease system if using Litle and Profitstars and not onboarded with both."
    );
  }

  @Test
  public void tc96() {
    this.kycHold(
        "tc96",
        "To validate that the PM is not able to navigate into paylease system if using Litle and Check Scanning and not onboarded with both."
    );
  }

  @Test
  public void tc98() {
    this.kycHold(
        "tc98",
        "To validate that the PM is not able to navigate into paylease system if using Litle and profitstars and is onboarded with Litle and is not onboarded with profitstars."
    );
  }

  @Test
  public void tc101() {
    this.kycHold(
        "tc101",
        "To validate that the PM is not able to navigate into paylease system if using Litle and profitstars and is onboarded with Profitstars and is not onboarded with litle."
    );
  }

  @Test
  public void tc108() {
    Logger.info(
        "To validate that a PM is navigated to the KYC screens if they are using only check scanning and are not onboarded with profitstars.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc108");
    testSetupPage.open();

    //Attempt to open dashboard page
    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    //Ensure we are redirected to kyc page
    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();
    Assert.assertTrue(pmCustIdVerifPage.isWelcomeMessageDisplayed(),
        "Should have been redirected to the KYC form.");
  }

  @Test
  public void tc115() {
    Logger.info("To validate that the PM is able to see a Save & log off button on the KYC page");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    Assert
        .assertTrue(pmCustIdVerifPage.isElementTypeVisible(ElementType.ENTITY_PARTIAL_SAVE_BUTTON),
            "Save & Log Off button is visible");
  }

  @Test
  public void tc117() {
    Logger
        .info("To validate that the PM is logged out of the PMUI on clicking \"Save and Log off\" "
            + "on a partially filled form.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_SSN, "979777866");

    pmCustIdVerifPage.clickSaveAndLogOff();

    PmLoginPage pmLoginPage = new PmLoginPage();

    Assert.assertTrue(pmLoginPage.pageIsLoaded(), "PM is redirected to the login page");
  }

  @Test
  public void tc118() {
    Logger.info(
        "To validate that when a PM logs back in after previously clicking 'Save and log off', "
            + "the pre-populated forms are editable.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    Map<ElementType, String> formValues = pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    String expectedOfficerPercent = "45";
    String unmaskedOfficerLicense = "A1244355";
    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage
        .setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, expectedOfficerPercent);
    pmCustIdVerifPage
        .setFormFieldForCurrentTab(ElementType.OFFICER_LICENSE, unmaskedOfficerLicense);

    pmCustIdVerifPage.clickSaveAndLogOff();

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.login(pmEmail);

    pmCustIdVerifPage.doContinueToNextTab();

    for (Map.Entry<ElementType, String> entry : formValues.entrySet().stream()
        .filter(x -> x.getKey() != ElementType.OFFICER_SSN
            && x.getKey() != ElementType.OFFICER_LICENSE) // skip SSN/DL since they're masked
        .collect(Collectors.toSet())) {

      Assert
          .assertEquals(formValues.get(entry.getKey()),
              pmCustIdVerifPage.getFormFieldValueForCurrentTab(entry.getKey()),
              "Form field: " + entry.getKey() + " has matching value after save");
    }

    pmCustIdVerifPage.doContinueToNextTab();
    Assert
        .assertEquals(pmCustIdVerifPage.getFormFieldValueForCurrentTab(ElementType.OFFICER_PERCENT),
            expectedOfficerPercent, "Officer Percent matches value after save");

  }

  @Test
  public void tc120() {
    Logger.info(
        "To validate that when a PM logs back in after previously clicking 'Save and log off', "
            + "the pre-populated forms are editable.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();
    final String pmEmail = testSetupPage.getString("pmEmail");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "45");
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_LICENSE, "A1244355");

    pmCustIdVerifPage.clickSaveAndLogOff();

    PmLoginPage pmLoginPage = new PmLoginPage();

    pmLoginPage.login(pmEmail);

    pmCustIdVerifPage.doContinueToNextTab();

    SoftAssert softAssert = new SoftAssert();

    softAssert.assertTrue(pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_PERCENT),
        "Officer Percent is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_FIRST_NAME)),
        " Officer First name is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_LAST_NAME)),
        " Officer Last name is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_TITLE)),
        " Officer Title is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_EMAIL)),
        " Officer Email is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_LICENSE)),
        " Officer License is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_ADDRESS)),
        " Officer Address is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_CITY)),
        " Officer city is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_STATE)),
        " Officer State is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_ZIP)),
        " Officer Zip is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_ADDRESS)),
        " Officer Address is not editable");
    softAssert.assertTrue((pmCustIdVerifPage.isElementTypeEditable(ElementType.OFFICER_SSN)),
        " Officer SSN is not editable");

    softAssert.assertAll();

  }


  @Test
  public void kycTc121() {
    Logger.info(
        "To validate that when a PM enters an incorrect value in the email address field, "
            + "an error is displayed and the user is not allowed to save and log off");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_EMAIL, "incorrect@email");

    pmCustIdVerifPage.clickSaveAndLogOff();

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_EMAIL),
        NON_FORMATTED_EMAIL_ADDRESS_ERR_MSG,
        "Email Address does not have to be correctly formatted");
  }

  @Test
  public void kycTc122() {
    Logger.info(
        "To validate that when a PM enters an incorrect value in the SSN field, "
            + "an error is displayed and the user is not allowed to save and log off");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_SSN, "921ZZ3434");

    pmCustIdVerifPage.clickSaveAndLogOff();

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_SSN),
        NON_NUMERIC_SSN_ERR_MSG, "SSN can be non-numeric");
  }


  @Test
  public void kycTc123() {
    Logger.info(
        "To validate that when a PM enters a negative value in the stake %, "
            + "an error is displayed and the user is not able to save and log off");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "-25");

    pmCustIdVerifPage.clickSaveAndLogOff();
    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_PERCENT),
        NEGATIVE_OWNER_PERCENT_ERR_MSG, "Percent of Ownership can be negative");
  }

  @Test
  public void kycTc124() {
    Logger.info(
        "To validate that when a PM enters an incorrect Driver's License Zip, "
            + "an error message is displayed and the user is not able to save and log off.    ");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_ZIP, "921ZZ");

    pmCustIdVerifPage.clickSaveAndLogOff();

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ZIP),
        NON_NUMERIC_ZIP_ERR_MSG, "Zip code can be non-numeric");
  }

  @Test
  public void kycTc125() {
    Logger.info(
        "To validate that the PM gets an error message when the Zip code is more than 5 characters long.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.changeMaxLengthOfHtmlField("officer_zip", 10);
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_ZIP, "92111234");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ZIP),
        ZIP_CODE_WRONG_LENGTH, "Zip code must be 5 characters long");
  }

  @Test
  public void kycTc126() {
    Logger.info(
        "To validate that the PM gets an error message when the Zip code is less than 5 characters long.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_ZIP, "921");

    Assert.assertEquals(
        pmCustIdVerifPage.getInputElementErrorMessageForCurrentTab(ElementType.OFFICER_ZIP),
        ZIP_CODE_WRONG_LENGTH, "Zip code must be 5 characters long");
  }

  @Test
  public void holdPageMessageManualReview() {
    this.kycHold(
        "tc1489",
        "To validate the verbiage on the hold page when the PM has failed GIACT validation and has required documents to upload.",
        "Thank you for completing the KYC process and digitally signing the PayLease Sub-Merchant Agreement.",
        "Your account is in manual review as we were unable to automatically verify your due diligence information. Please upload the necessary documents asked for below as it will help with the verification process."
    );
  }

  @Test
  public void holdPageMessageVerified() {
    this.kycHold(
        "tc727",
        "To validate that the following message appears on the hold page when the PM has successfully verified GIACT information",
        "Thank you for completing the KYC process and digitally signing the PayLease Sub-Merchant Agreement.",
        "Your PayLease Representative will contact you regarding next steps to complete onboarding."
    );
  }

  @Test
  public void holdPageDownloadLitleAgreement() {
    KycHoldPage kycHoldPage = this.kycHold(
        "tc727",
        "To validate that the download litle agreement button is present when the PM is on the KYC hold page"
    );

    Assert.assertTrue(kycHoldPage.isDownloadLittleAgreementButtonPresent(),
        "The Download Litle Agreement button is not present");
  }

  @Test
  public void subAccountHoldPageDownloadLitleAgreement() {
    KycHoldPage kycHoldPage = this.kycHold(
        "tc729",
        "To validate that the download litle agreement button is not present when the Sub Account PM is on the KYC hold page"
    );

    Assert.assertFalse(kycHoldPage.isDownloadLittleAgreementButtonPresent(),
        "The Download Litle Agreement button is present");
  }

  @Test
  public void legalEntityTypePopulatesCorrectly() {
    Logger.info(
        "To validate that the legal entity type on the Legal Entity tab gets populated with the correct value.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc65_66");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    Assert.assertEquals(pmCustIdVerifPage.getLegalEntityType(), "INDIVIDUAL SOLE PROPRIETORSHIP",
        "The selected legal entity type is not INDIVIDUAL SOLE PROPRIETORSHIP");
  }

  @Test
  public void legalEntityPrivatePublicDropdownLabel() {
    Logger.info("To validate that the Private/Public Company field dropdown label is correct");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE,
        "legalEntityPrivatePublicDropdownLabel");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    Assert.assertEquals(pmCustIdVerifPage.getLegalEntityPublicPrivateDropdownLabel(),
        "Private or Public Company?", "Expected label 'Private or Public Company?' not found");
  }

  @Test
  public void legalEntityPrivatePublicDropdownDefault() {
    Logger.info("To verify the default value for Public/Private Company field dropdown");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE,
        "legalEntityPrivatePublicDropdownDefault");
    testSetupPage.open();

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    String defualtOption = pmCustIdVerifPage.getLegalEntityPublicPrivateSelectedValue();

    Assert.assertEquals(defualtOption, "PRIVATE",
        "Expecting 'PRIVATE' but found '" + defualtOption + "'.");
  }

  @Test
  public void tc6878() {
    Logger.info("Verify that the 'Remove Owner' button works after submit but not verified");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();

    customerIdVerificationPage.doContinueToNextTab();

    for(int i = 0; i < 2; i++) {

      customerIdVerificationPage.fillOfficerForm(true);
      customerIdVerificationPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "25");
      customerIdVerificationPage.clearFormField(ElementType.OFFICER_ADDRESS);
      customerIdVerificationPage
          .setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, CI11_PASS_RESPONSE);
      customerIdVerificationPage.addAdditionalOfficerForCurrentTab();
      customerIdVerificationPage.clickNextButton();
    }

    customerIdVerificationPage.fillOfficerForm(true);
    customerIdVerificationPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "25");
    customerIdVerificationPage.clearFormField(ElementType.OFFICER_ADDRESS);
    customerIdVerificationPage
        .setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, OFAC_PASS_NO_CAM_RESPONSE);
    customerIdVerificationPage.clearFormField(ElementType.OFFICER_FIRST_NAME);
    customerIdVerificationPage.setFormFieldForCurrentTab(ElementType.OFFICER_FIRST_NAME, CUSTOMER_RESPONSE_CI21);

    customerIdVerificationPage.notAddAdditionalOfficerForCurrentTab();

    customerIdVerificationPage.clickMultiSubmit();
    customerIdVerificationPage.clickSummarySubmitAndWait();

    customerIdVerificationPage.selectReviewOwnerButtonOnSummary(4);

    SoftAssert softAssert = new SoftAssert();

    softAssert.assertTrue(customerIdVerificationPage.isRemoveOwnerButtonDisplayedAfterVerified(), "Remove owner button should be available");

    customerIdVerificationPage.clickRemoveOwnerButton();
    customerIdVerificationPage.clickMultiSubmit();
    customerIdVerificationPage.clickSummarySubmitAndWait();

    PmOfficersDAO pmOfficersDAO = new PmOfficersDAO();
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();
    ArrayList<PmOfficersDTO> pmOfficers = pmOfficersDAO.findByPmId(connection, Integer.parseInt(pmId), 3);

    softAssert.assertTrue(pmOfficers.size() == 2, "Last owners record should be deleted");

    softAssert.assertAll();
  }

  @Test
  public void tc6879() {
    Logger.info("Verify that the 'Remove Owner' button is available when the user saves & log out "
        + "and logs back in and the record is deleted from pm_officers when they submit");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    String pmEmail = testSetupPage.getString("pmEmail");
    String pmId = testSetupPage.getString("pmId");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickNextButton();

    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.clickSaveAndLogOff();

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.clickNextButton();

    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(pmCustIdVerifPage.isRemoveOwnerButtonDisplayed());

    pmCustIdVerifPage.clickRemoveOwnerButton();
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickMultiSubmit();

    softAssert.assertEquals(pmCustIdVerifPage.getNumOfOwnersInSummary(), 1,
        "Should only be 1 owner on Summary Page");
    pmCustIdVerifPage.clickSummarySubmitAndWait();


    PmOfficersDAO pmOfficersDAO = new PmOfficersDAO();
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();
    ArrayList<PmOfficersDTO> pmOfficers = pmOfficersDAO.findByPmId(connection, Integer.parseInt(pmId), 3);

    softAssert.assertTrue(pmOfficers.size() == 1, "Last owners record should be deleted");

    softAssert.assertAll();
  }

  @Test
  public void tc6881() {
    Logger.info("Verify that the 'Remove Owner' button is available when the user saves & log out "
        + "and logs back in and the record is deleted from pm_officers when they save and log out");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    String pmEmail = testSetupPage.getString("pmEmail");
    String pmId = testSetupPage.getString("pmId");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickNextButton();

    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.clickSaveAndLogOff();

    Login login = new Login();
    login.logInUser(pmEmail, UserType.PM);

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.clickNextButton();

    pmCustIdVerifPage.clickRemoveOwnerButton();
    pmCustIdVerifPage.clickSaveAndLogOff();

    PmOfficersDAO pmOfficersDAO = new PmOfficersDAO();
    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();
    ArrayList<PmOfficersDTO> pmOfficers = pmOfficersDAO.findByPmId(connection, Integer.parseInt(pmId), 3);

    Assert.assertTrue(pmOfficers.size() == 1, "Last owners record should be deleted");
  }

  @Test
  public void tc6882() {
    Logger.info("Verify that a pm is not able to remove owners after they are verified by Giact");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6882");
    testSetupPage.open();

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();
    customerIdVerificationPage.doContinueToNextTab();
    customerIdVerificationPage.clickNextButton();
    Assert.assertTrue(!customerIdVerificationPage.isRemoveOwnerButtonDisplayedAfterVerified(),
        "Button should not appear");
  }

  @Test
  public void tc6883() {
    Logger.info(
        "Additional Owner dropdown is not editable if there is a next owner and the user has gone to the next owner tab and returned");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();

    customerIdVerificationPage.doContinueToNextTab();
    customerIdVerificationPage.fillOfficerForm();
    customerIdVerificationPage.addAdditionalOfficerForCurrentTab();
    customerIdVerificationPage.clickNextButton();
    customerIdVerificationPage.goToPreviousTab();

    Assert.assertTrue(!customerIdVerificationPage.isAdditionalOwnerDropdownEditable(),
        "Additional Owner dropdown should not be editable");
  }

  @Test
  public void tc6884() {
    Logger.info("Verify that the fifth owner does not have the Additional Owner dropdown");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();
    customerIdVerificationPage.fillGivenNumberOfOfficerTabs(4);
    customerIdVerificationPage.clickNextButton();

    Assert.assertTrue(!customerIdVerificationPage.isAdditionalOwnerDropdownVisible(),
        "Additional Owner Dropdown should not be visible");
  }

  @Test
  public void tc6885() {
    Logger.info(
        "Verify that only the last owner will have and be able to edit the Additional Owner Dropdown if they are not the fifth owner");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();
    customerIdVerificationPage.fillGivenNumberOfOfficerTabs(3);
    customerIdVerificationPage.clickNextButton();

    SoftAssert softAssert = new SoftAssert();

    softAssert.assertTrue(customerIdVerificationPage.isAdditionalOwnerDropdownVisible(),
        "Additional Officer dropdown should be visible for last owner");
    softAssert.assertTrue(customerIdVerificationPage.isAdditionalOwnerDropdownEditable(),
        "Additional Officer dropdown should be editable for last owner");

    customerIdVerificationPage.goToPreviousTab();
    softAssert.assertTrue(!customerIdVerificationPage.isAdditionalOwnerDropdownEditable(),
        "Additional Officer dropdown should not be editable for third owner");

    customerIdVerificationPage.goToPreviousTab();
    softAssert.assertTrue(!customerIdVerificationPage.isAdditionalOwnerDropdownEditable(),
        "Additional Officer dropdown should not be editable for second owner");

    customerIdVerificationPage.goToPreviousTab();
    softAssert.assertTrue(!customerIdVerificationPage.isAdditionalOwnerDropdownEditable(),
        "Additional Officer dropdown should not be editable for primary owner");

    softAssert.assertAll();
  }

  @Test
  public void tc6887() {
    Logger.info(
        "Verify that only the 2nd to last owner has the remove officer button when the last owner "
            + "is removed");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();
    customerIdVerificationPage.fillGivenNumberOfOfficerTabs(3);
    customerIdVerificationPage.clickNextButton();

    SoftAssert softAssert = new SoftAssert();

    customerIdVerificationPage.clickRemoveOwnerButton();

    softAssert.assertTrue(customerIdVerificationPage.isRemoveOwnerButtonDisplayed(),
        "Remove owner button should be visible on third owner");
    softAssert.assertTrue(customerIdVerificationPage.isAdditionalOwnerDropdownVisible(),
        "Additional owner dropdown is visible on third owner");

    softAssert.assertAll();
  }

  //--------------------------------TEST METHOD-----------------------------------------------------

  @Test
  public void showUploadOnManualReview() {
    Logger.info(
        "To validate that the there is an upload form on the hold page when PM is placed on manual review.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1489");
    testSetupPage.open();

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    Assert.assertTrue(kycHoldPage.isUploadFormPresent(), "The upload section is not present");
  }

  @Test
  public void redirectsToKycHoldPageAfterSigningLitle() {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1492");
    testSetupPage.open();

    LitleAgreementPage litleAgreementPage = new LitleAgreementPage();
    litleAgreementPage.open();
    litleAgreementPage.clickContinueToLitleForm();
    litleAgreementPage.fillOutDob();
    litleAgreementPage.submitLitleForm();

    KycHoldPage kycHoldPage = new KycHoldPage();

    Assert.assertTrue(kycHoldPage.pageIsLoaded(), "The PM is not on the KYC hold page");
  }

  @Test
  public void dontShowUploadOnManualReviewForSubAccount() {
    Logger.info(
        "To validate that the there is not an upload form for the SubAccount PM on the hold page when PM is placed on manual review.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1499");
    testSetupPage.open();

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    Assert.assertFalse(kycHoldPage.isUploadFormPresent(), "The upload section is present");
  }

  @Test
  public void showsFileUploadPerFailedOfficer() {
    Logger.info("To validate that each officer failed has a file field to upload requested file.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1500");
    testSetupPage.open();

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    Assert.assertTrue(kycHoldPage.isOfficerFileUploadFieldPresent(OFFICER_TYPE_PRIMARY),
        "File Upload field for Primary Officer is not present");
    Assert.assertFalse(kycHoldPage.isOfficerFileUploadFieldPresent(OFFICER_TYPE_SECONDARY),
        "File Upload field for Secondary Officer is present");
    Assert.assertTrue(kycHoldPage.isOfficerFileUploadFieldPresent(OFFICER_TYPE_TERTIARY),
        "File Upload field for Tertiary Officer is not present");
  }

  @Test
  public void showRequiredDocumentMessageForCI22() {
    Logger.info(
        "To validate that the SSN document is required when the officer has failed with CI22 error for 1 officer.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1489");
    testSetupPage.open();
    String primaryOfficerName = testSetupPage.getString("primaryOfficerName");
    String expectedError = "TaxID (SSN/ITIN) submitted failed customer authentication. Please review and correct before resubmitting for verification.";
    String expectedSolution = "Please provide an image of a Government Issued SSN Document";


    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    Assert.assertEquals(kycHoldPage.getOfficerUploadHeader(OFFICER_TYPE_PRIMARY),
        "Control Person: " + primaryOfficerName,
        "The Primary Officer Header doesn't match");

    Assert.assertEquals(kycHoldPage.getOfficerErrorMessage(OFFICER_TYPE_PRIMARY),
        expectedError,
        "The Primary Officer Error Message doesn't match");

    Assert.assertEquals(kycHoldPage.getRequiredDocumentMessage(OFFICER_TYPE_PRIMARY),
        expectedSolution,
        "The Primary Officer Required Document Message doesn't match");
  }

  @Test
  public void uploadOfficerVerificationFile() {
    Logger.info(
        "To validate that the Pm is able to upload a file by clicking on the 'choose file' option and submitting the form.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1489");
    testSetupPage.open();

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    kycHoldPage.chooseOfficerFile(OFFICER_TYPE_PRIMARY, getUploadTestFile(TestFileType.PDF),
        SSN_SOLUTION_KEY);
    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Assert.assertEquals(kycHoldPage.getUploadedFileMessage(OFFICER_TYPE_PRIMARY),
        "File has been submitted: " + TEST_FILE_NAME,
        "Uploaded file message did not match.");
  }

  @Test
  public void fileStoredInDatabase() {
    Logger.info(
        "To validate that the file uploaded by the Pm is stored in the database.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1489");
    testSetupPage.open();
    String pmId = testSetupPage.getString("pmId");

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    kycHoldPage.chooseOfficerFile(OFFICER_TYPE_PRIMARY, getUploadTestFile(TestFileType.PDF),
        SSN_SOLUTION_KEY);
    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Assert.assertTrue(isOfficerUploadedDocumentInDb(pmId, DOC_TYPE_SSN_PRIMARY_OFFICER_UPLOAD),
        "The file is not stored in the database.");
  }

  @Test
  public void pmCanReplaceExistingUploadedFile() {
    Logger.info(
        "To validate that the PM is able to replace a previously uploaded file.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1489");
    testSetupPage.open();
    String pmId = testSetupPage.getString("pmId");

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    File uploadedFile1 = getUploadTestFile(TestFileType.JPG);

    kycHoldPage.chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile1, SSN_SOLUTION_KEY);
    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Assert.assertTrue(isOfficerUploadedDocumentInDb(pmId, DOC_TYPE_SSN_PRIMARY_OFFICER_UPLOAD),
        "The first file is not stored in the database.");
    String fileName1 = getOfficerUploadFileName(pmId, DOC_TYPE_SSN_PRIMARY_OFFICER_UPLOAD);

    kycHoldPage.clickOfficerRemoveFile(OFFICER_TYPE_PRIMARY, SSN_SOLUTION_KEY);

    File uploadedFile2 = getUploadTestFile(TestFileType.PDF);

    kycHoldPage.chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile2, SSN_SOLUTION_KEY);
    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Assert.assertTrue(isOfficerUploadedDocumentInDb(pmId, DOC_TYPE_SSN_PRIMARY_OFFICER_UPLOAD),
        "The second file is not stored in the database.");
    String fileName2 = getOfficerUploadFileName(pmId, DOC_TYPE_SSN_PRIMARY_OFFICER_UPLOAD);

    Assert.assertNotEquals(fileName1, fileName2, "File was not replaced");
  }

  @Test
  public void testUploadButtonIsDisabledWhenPmOfficerHasExistingDocument() {
    Logger.info(
        "To ensure the 'Upload' button is disabled if the pm has already uploaded a document");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc1654");
    testSetupPage.open();
    String message = testSetupPage.getString("uploadFileMessage");

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    Assert.assertEquals(kycHoldPage.getUploadedFileMessage(OFFICER_TYPE_PRIMARY),
        message,
        "Uploaded file message did not match.");
    Assert.assertTrue(kycHoldPage.isUploadButtonDisabled(OFFICER_TYPE_PRIMARY),
        "Upload button is NOT disabled");
  }

  @Test
  public void holdPageMessageManualReviewAndHasUploadedDocuments() {
    this.kycHold(
        "tc1654",
        "To validate the verbiage on the hold page when the PM has failed GIACT validation and has uploaded all the required documents.",
        "Thank you for completing the KYC process, digitally signing the PayLease Sub-Merchant Agreement and providing the necessary documents for verification.",
        "Your account is still in manual review but as soon as your PayLease Representative reviews the uploaded documents, you will be able to proceed to the PayLease system."
    );
  }

  @Test
  public void holdPageHasFileUploadsForEachSolution() {
    Logger.info(
        "This test ensures that for each solution a PM has, there exists a 'choose file' button.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4275");
    testSetupPage.open();

    //Attempt to open dashboard page
    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    //Ensure we are redirected to kyc hold page
    KycHoldPage kycHoldPage = new KycHoldPage();
    Assert.assertTrue(kycHoldPage.pageIsLoaded(),
        "Should have been redirected to the KYC hold page.");

    Assert.assertTrue(kycHoldPage.pageHasPmOfficerWithTwoFileUploads(OFFICER_TYPE_PRIMARY),
        "Failed two assert the existence of two file inputs.");
  }

  @Test
  public void holdPageShowsErrorWhenPmAttemptsToUploadOneOfTwoDocuments() {
    Logger
        .info("This test ensures an error showing if a PM attempts to upload one of two documents");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4275");
    testSetupPage.open();

    //Attempt to open dashboard page
    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    //Ensure we are redirected to kyc hold page
    KycHoldPage kycHoldPage = new KycHoldPage();
    Assert.assertTrue(kycHoldPage.pageIsLoaded(),
        "Should have been redirected to the KYC hold page.");

    Assert.assertTrue(kycHoldPage.pageHasPmOfficerWithTwoFileUploads(OFFICER_TYPE_PRIMARY),
        "Failed two assert the existence of two file inputs.");

    File uploadedFile1 = getUploadTestFile(TestFileType.JPG);

    kycHoldPage
        .chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile1, DRIVERS_LICENSE_SOLUTION_KEY);
    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Assert.assertEquals(
        "Please select all requested documents for an officer before uploading.",
        kycHoldPage.getErrorBoxErrorMessage()
    );
  }

  @Test
  public void holdPageShowsSuccessMessageWhenPmUploadsAllNecessaryDocuments() {
    Logger.info("This test ensures that a PM can upload all needed documents");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc4275");
    testSetupPage.open();

    //Attempt to open dashboard page
    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    //Ensure we are redirected to kyc hold page
    KycHoldPage kycHoldPage = new KycHoldPage();
    Assert.assertTrue(kycHoldPage.pageIsLoaded(),
        "Should have been redirected to the KYC hold page.");

    Assert.assertTrue(kycHoldPage.pageHasPmOfficerWithTwoFileUploads(OFFICER_TYPE_PRIMARY),
        "Failed two assert the existence of two file inputs.");

    File uploadedFile1 = getUploadTestFile(TestFileType.JPG);
    kycHoldPage
        .chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile1, DRIVERS_LICENSE_SOLUTION_KEY);
    File uploadedFile2 = getUploadTestFile(TestFileType.PDF);
    kycHoldPage.chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile2, SSN_SOLUTION_KEY);

    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Assert.assertEquals(
        "Thank you for completing the KYC process, digitally signing the PayLease Sub-Merchant Agreement and providing the necessary documents for verification.",
        kycHoldPage.getSuccessMessage()
    );

    Assert.assertEquals(
        "Your account is still in manual review but as soon as your PayLease Representative reviews the uploaded documents, you will be able to proceed to the PayLease system.",
        kycHoldPage.getHoldMessage()
    );
  }

  @Test(groups = {"manual"})
  public void tc6170() {
    Logger.info("Verify that a \"Manual Review\" email is sent when a PM gets into Manual Review"
        + " status");
    EmailProcessor emailProcessor = new EmailProcessor();

    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    envWriterUtil.replaceEnvFileValue("0_testEmail.env", "DEV_SYSTEM_DEVELOPER",
        AppConstant.QA_MAILBOX);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6170");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    String pmCompany = testSetupPage.getString("pmCompany");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm(true);
    pmCustIdVerifPage.clickSubmit();
    pmCustIdVerifPage.clickSummarySubmitAndWait();

    Message test = emailProcessor.waitForSpecifiedEmailToBePresent(EMAIL_MANUAL_REVIEW_SUBJECT, pmId);

    List<MessagePart> testbody = emailProcessor.getBody(test, new ArrayList<>(), false, false);

    String messagePartContent = testbody.get(0).getContent();

    envWriterUtil.replaceEnvFileValue("0_testEmail.env", "DEV_SYSTEM_DEVELOPER", null);

    Assert.assertTrue(
        messagePartContent.contains(expectedEmailMessage(EMAIL_MANUAL_REVIEW, pmCompany, pmId)));
  }

  @Test(groups = {"manual"})
  public void tc6171() {
    Logger.info("Verify that a \"Verified\" email is sent to when a PM is verified");

    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    EmailProcessor emailProcessor = new EmailProcessor();

    envWriterUtil.replaceEnvFileValue("0_testEmail.env", "DEV_SYSTEM_DEVELOPER",
        AppConstant.QA_MAILBOX);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6170");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    String pmCompany = testSetupPage.getString("pmCompany");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillWithValidOfficerInfo();
    pmCustIdVerifPage.clickSubmit();
    pmCustIdVerifPage.clickSummarySubmitAndWait();

    Message email = waitForEmailToBePresent(pmId, EMAIL_VERIFIED_SUBJECT);

    List<MessagePart> emailBody = emailProcessor.getBody(email, new ArrayList<>(), false, false);

    String messagePartContent = emailBody.get(0).getContent();

    envWriterUtil.replaceEnvFileValue("0_testEmail.env", "DEV_SYSTEM_DEVELOPER", null);

    Assert.assertTrue(
        messagePartContent.contains(expectedEmailMessage(EMAIL_VERIFIED, pmCompany, pmId)));
  }

  @Test(groups = {"manual"})
  public void tc6172() {
    Logger.info("Verify that an email is sent out when a PM uploads documents");

    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    EmailProcessor emailProcessor = new EmailProcessor();

    envWriterUtil.replaceEnvFileValue("0_testEmail.env", "DEV_SYSTEM_DEVELOPER",
        AppConstant.QA_MAILBOX);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6172");
    testSetupPage.open();

    String pmId = testSetupPage.getString("pmId");
    String pmCompany = testSetupPage.getString("pmCompany");

    KycHoldPage kycHoldPage = new KycHoldPage();
    kycHoldPage.open();

    File uploadedFile1 = getUploadTestFile(TestFileType.JPG);
    File uploadedFile2 = getUploadTestFile(TestFileType.PDF);

    kycHoldPage
        .chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile1, DRIVERS_LICENSE_SOLUTION_KEY);
    kycHoldPage.chooseOfficerFile(OFFICER_TYPE_PRIMARY, uploadedFile2, SSN_SOLUTION_KEY);
    kycHoldPage.clickOfficerFileUpload(OFFICER_TYPE_PRIMARY);

    Message email = waitForEmailToBePresent(pmId, EMAIL_UPLOADED_DOCUMENTS_SUBJECT);
    List<MessagePart> emailBody = emailProcessor.getBody(email, new ArrayList<>(), false, false);

    String messagePartContent = emailBody.get(0).getContent();

    envWriterUtil.replaceEnvFileValue("0_testEmail.env", "DEV_SYSTEM_DEVELOPER", null);

    Assert.assertTrue(messagePartContent
        .contains(expectedEmailMessage(EMAIL_UPLOADED_DOCUMENTS, pmCompany, pmId)));

  }

  @Test
  public void tc6859() {
    Logger.info(
        "To validate that the control person is able to submit to Giact with 0% ownership");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    final boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");
    String pmId = testSetupPage.getString("pmId");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.clearFormField(ElementType.OFFICER_PERCENT);
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "0");
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickMultiSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmit();
    }

    SoftAssert softAssert = new SoftAssert();

    softAssert.assertTrue(!pmCustIdVerifPage.isServerPercentageErrorDisplayed(3),
        "Primary officer is able to have 0% ownership");

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();

    PmOfficersDAO pmOfficersDAO = new PmOfficersDAO();

    ArrayList<PmOfficersDTO> pmOfficers = pmOfficersDAO
        .findByPmId(connection, Integer.parseInt(pmId), 1);
    softAssert.assertEquals(pmOfficers.get(0).getPercentStake(), 0);

    softAssert.assertAll();
  }

  @Test
  public void tc6861() {
    Logger.info("To validate that the PM is not able to submit 0% in the ownership % field");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc01");
    testSetupPage.open();

    final boolean giactEnabled = testSetupPage.getFlag("giactVerificationEnabled");

    CustomerIdVerificationPage pmCustIdVerifPage = new CustomerIdVerificationPage();
    pmCustIdVerifPage.open();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.addAdditionalOfficerForCurrentTab();

    pmCustIdVerifPage.doContinueToNextTab();
    pmCustIdVerifPage.fillOfficerForm();
    pmCustIdVerifPage.clearFormField(ElementType.OFFICER_PERCENT);
    pmCustIdVerifPage.setFormFieldForCurrentTab(ElementType.OFFICER_PERCENT, "0");
    pmCustIdVerifPage.notAddAdditionalOfficerForCurrentTab();
    pmCustIdVerifPage.clickMultiSubmit();

    if (giactEnabled) {
      pmCustIdVerifPage.clickSummarySubmit();
    }

    Assert.assertTrue(pmCustIdVerifPage.isServerPercentageErrorDisplayed(3),
        "Officer percentage error requiring percentage > 25 should display");
  }

  private KycHoldPage kycHold(String testCase, String info) {
    Logger.info(info);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    //Attempt to open dashboard page
    PmHomePage pmHomePage = new PmHomePage();
    pmHomePage.open();

    //Ensure we are redirected to kyc hold page
    KycHoldPage kycHoldPage = new KycHoldPage();
    Assert.assertTrue(kycHoldPage.pageIsLoaded(),
        "Should have been redirected to the KYC hold page.");

    return kycHoldPage;
  }

  private KycHoldPage kycHold(String testCase, String info, String expectedSuccessMessage,
      String expectedHoldMessage) {
    KycHoldPage kycHoldPage = kycHold(testCase, info);

    Assert.assertEquals(kycHoldPage.getSuccessMessage(), expectedSuccessMessage,
        "The success verbiage does not match");
    Assert.assertEquals(kycHoldPage.getHoldMessage(), expectedHoldMessage,
        "The hold verbiage does not match");

    return kycHoldPage;
  }

  private EnumMap<ElementType, String> getPmOfficerRowfromDb(String pmId) {
    String sqlQuery = "SELECT * "
        + "FROM pm_officers "
        + "WHERE pm_id = " + pmId;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();

    ResultSet result = dataBaseConnector.executeSqlQuery(sqlQuery);

    EnumMap<ElementType, String> officerRow = new EnumMap<>(ElementType.class);

    try {
      if (result.first()) {
        officerRow.put(ElementType.OFFICER_PERCENT, result.getString("percent_stake"));
        officerRow.put(ElementType.OFFICER_FIRST_NAME, result.getString("first_name"));
        officerRow.put(ElementType.OFFICER_LAST_NAME, result.getString("last_name"));
        officerRow.put(ElementType.OFFICER_TITLE, result.getString("title"));
        officerRow.put(ElementType.OFFICER_EMAIL, result.getString("email"));
        officerRow.put(ElementType.OFFICER_ADDRESS, result.getString("address"));
        officerRow.put(ElementType.OFFICER_CITY, result.getString("city"));
        officerRow.put(ElementType.OFFICER_STATE, result.getString("state"));
        officerRow.put(ElementType.OFFICER_ZIP, result.getString("zip"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    dataBaseConnector.closeConnection();

    return officerRow;
  }

  private boolean isOfficerUploadedDocumentInDb(String pmId, int docType) {
    String sqlQuery = "SELECT dfd.file_data "
        + "FROM documents d "
        + "JOIN document_versions dv "
        + "ON d.doc_id = dv.doc_id "
        + "JOIN document_file_data dfd "
        + "ON dv.doc_version_id = dfd.doc_version_id "
        + "WHERE d.pm_id = " + pmId + " "
        + "AND d.doc_type_id = " + docType;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    boolean filePresent = false;
    try {
      dataBaseConnector.createConnection();
      ResultSet result = dataBaseConnector.executeSqlQuery(sqlQuery);

      if (result.first()) {
        filePresent = !result.getString("file_data").isEmpty();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    dataBaseConnector.closeConnection();

    return filePresent;
  }

  private String getOfficerUploadFileName(String pmId, int docType) {
    String sqlQuery = "SELECT orig_filename "
        + "FROM documents d "
        + "WHERE d.pm_id = " + pmId + " "
        + "AND d.doc_type_id = " + docType;

    DataBaseConnector dataBaseConnector = new DataBaseConnector();

    String fileName = "";

    try {
      dataBaseConnector.createConnection();
      ResultSet result = dataBaseConnector.executeSqlQuery(sqlQuery);

      if (result.first()) {
        fileName = result.getString("orig_filename");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    dataBaseConnector.closeConnection();

    return fileName;
  }

  private Message waitForEmailToBePresent(String body, String subject) {
    EmailProcessor emailProcessor = new EmailProcessor();
    emailProcessor.connect();

    for (int i = 0; i < EMAIL_RETRY_COUNT; i++) {
      Logger.trace("Attempt #" + (i + 1) + ": Looking for message.");
      Message[] messages = emailProcessor.getMessagesWhereBodyContains(body);
      if (messages != null) {
        for (Message message : messages) {
          if (emailProcessor.getSubject(message).equals(subject)) {
            return message;
          }
        }
      }

      try {
        Thread.sleep(EMAIL_RETRY_TIMEOUT);
      } catch (InterruptedException e) {
        Logger.debug("Sleep problem: " + e.getMessage());
      }
    }

    return null;
  }

  private String expectedEmailMessage(String message, String pmCompany, String pmId) {
    return message.replace("{pmCompany}", pmCompany).replace("{pmId}", pmId);
  }
}
