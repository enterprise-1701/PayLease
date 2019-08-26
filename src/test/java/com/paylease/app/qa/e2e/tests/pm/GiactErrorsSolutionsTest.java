package com.paylease.app.qa.e2e.tests.pm;

import static com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.GIACT_ENV_FILE;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ResourceFactory;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage;
import com.paylease.app.qa.framework.pages.pm.CustomerIdVerificationPage.ElementType;
import com.paylease.app.qa.framework.pages.pm.KycHoldPage;
import com.paylease.app.qa.framework.utility.envwriter.EnvWriterUtil;
import com.paylease.app.qa.framework.utility.listeners.Retry;
import com.paylease.app.qa.testbase.dataproviders.GiactErrorsSolutionsDataProvider;
import java.util.ArrayList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GiactErrorsSolutionsTest extends ScriptBase {

  private static final String REGION = "pm";
  private static final String FEATURE = "GiactErrorsSolutions";
  private static final String URL_VARIABLE_NAME = "GIACT_API_URL";

  private static final String BASE_URL =
      ResourceFactory.getInstance().getProperty(ResourceFactory.STUB_HOST)
          + "/stub-web-service/stub/v2?service=giact";

  private String originalGiactContent = "";

  @BeforeClass(alwaysRun = true)
  public void setup() {
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();
    originalGiactContent = envWriterUtil
        .replaceEnvFileValue(GIACT_ENV_FILE, URL_VARIABLE_NAME, BASE_URL);
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    EnvWriterUtil envWriterUtil = new EnvWriterUtil();

    try {
     Logger.debug("The contents of the file are: " + envWriterUtil.readFile(GIACT_ENV_FILE));
    } catch (Exception e) {
      Logger.info("Exception: " + e);
    } finally {
      envWriterUtil.replaceEnvFileValue(GIACT_ENV_FILE, null, originalGiactContent);
    }
  }

  @Test(dataProvider = "giactErrorsSolutions", dataProviderClass = GiactErrorsSolutionsDataProvider.class, groups =
      {"e2e", "giact"}, retryAnalyzer = Retry.class)
  public void giactErrorsSolutions(String testVariationNum, String stubResponse,
      String customerResponseCode, String[] consumerAlertMessages, boolean isHardFail,
      String[] expectedErrors, String[] expectedSolutions) {
    Logger.info(testVariationNum + " Verify that when " + customerResponseCode + " with "
        + expectedErrors.length + " errors and " + expectedSolutions.length + " solutions.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "giactErrorsAndSolutions");
    testSetupPage.open();
    SoftAssert softAssert = new SoftAssert();

    CustomerIdVerificationPage customerIdVerificationPage = new CustomerIdVerificationPage();
    customerIdVerificationPage.open();

    customerIdVerificationPage.doContinueToNextTab();
    customerIdVerificationPage.clearFormField(ElementType.OFFICER_ADDRESS);
    customerIdVerificationPage.setFormFieldForCurrentTab(ElementType.OFFICER_ADDRESS, stubResponse);

    if (consumerAlertMessages.length == 2) {
      customerIdVerificationPage.clearFormField(ElementType.OFFICER_LAST_NAME);
      customerIdVerificationPage
          .setFormFieldForCurrentTab(ElementType.OFFICER_LAST_NAME, consumerAlertMessages[1]);
    }
    if (consumerAlertMessages.length > 0) {
      customerIdVerificationPage.clearFormField(ElementType.OFFICER_CITY);
      customerIdVerificationPage
          .setFormFieldForCurrentTab(ElementType.OFFICER_CITY, consumerAlertMessages[0]);
    }

    customerIdVerificationPage.clearFormField(ElementType.OFFICER_FIRST_NAME);
    customerIdVerificationPage
        .setFormFieldForCurrentTab(ElementType.OFFICER_FIRST_NAME, customerResponseCode);

    customerIdVerificationPage.clickSubmit();
    customerIdVerificationPage.clickSummarySubmitAndWait();

    if (!isHardFail) {
      ArrayList<String> errors = customerIdVerificationPage.getErrors();
      softAssert.assertEquals(expectedErrors.length, errors.size(),
          "Should show expected num of errors on submit page when it is not hard fail"
              + testVariationNum);
      for (String expectedError : expectedErrors) {
        softAssert.assertTrue(errors.contains("Control Person: " + expectedError),
            "Should have expected errors on submit page " + testVariationNum);
      }

      customerIdVerificationPage.clickSummarySubmitAndWait();
      customerIdVerificationPage.clickSummarySubmitAndWait();

    }

    KycHoldPage kycHoldPage = new KycHoldPage();

    ArrayList<String> errors = kycHoldPage.getErrors();
    ArrayList<String> solutions = kycHoldPage.getSolutions();

    softAssert.assertEquals(expectedErrors.length, errors.size(),
        "Should show expected num of errors on hold page " + testVariationNum);
    softAssert.assertEquals(expectedSolutions.length, solutions.size(),
        "Should show expected num of solutions on hold page " + testVariationNum);

    for (String expectedError : expectedErrors) {

      softAssert
          .assertTrue(errors.contains(expectedError),
              "Should have expected errors on hold page " + testVariationNum);
    }
    for (String expectedSolution : expectedSolutions) {

      softAssert.assertTrue(solutions.contains(expectedSolution),
          "Should have expected solutions on submit page " + testVariationNum);
    }

    softAssert.assertAll();
  }
}
