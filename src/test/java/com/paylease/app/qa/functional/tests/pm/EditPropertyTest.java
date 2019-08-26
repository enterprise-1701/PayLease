package com.paylease.app.qa.functional.tests.pm;

import com.paylease.app.qa.framework.DataBaseConnector;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.pm.EditPropertyPage;
import com.paylease.app.qa.framework.utility.database.client.dao.CustomizationsPropertyDAO;
import com.paylease.app.qa.framework.utility.database.client.dto.CustomizationsPropertyDTO;
import java.sql.Connection;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EditPropertyTest extends ScriptBase {

  private static final String REGION = "Pm";
  private static final String FEATURE = "EditProperty";
  private static final String NOT_SAVED = "Customization was not saved.";

  @DataProvider(name = "DisallowPrePaymentDataProvider")
  public static Object[][] DisallowPrePaymentDataProvider() {
    return new Object[][]{
        // testCaseId, info, customizationOn
        {
            "tc6783",
            "To validate that a PM is able to click the Disallow Pre-Payments Checkbox on the "
                + "Edit Properties page"
        },
        {
            "tc6785",
            "To validate that a PM is able to deselect the Disallow Pre-Payments Checkbox on the "
                + "Edit Properties page"
        }
    };
  }

  @Test(dataProvider = "DisallowPrePaymentDataProvider")
  public void testDisallowPrepaymentsChkBox(String testCaseId, String info) {
    Logger.info(info);

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCaseId);
    testSetupPage.open();
    final String propertyId = testSetupPage.getString("propertyId");

    EditPropertyPage editPropertyPage = new EditPropertyPage();
    editPropertyPage.open(propertyId);
    editPropertyPage.clickLockZeroDollarsCheckBox();

    editPropertyPage.clickSavePropertyInfo();

    Assert.assertTrue(editPropertyPage.checkSuccessMessageExists(), NOT_SAVED);
  }

  @Test
  public void testLockAmountsDoesNotExist() {
    Logger.info("To validate that when the FIXED_AMOUNTS customization is disabled and the "
        + "DISALLOW_PRE_PAYMENTS is enabled, then the Lock Amounts check box "
        + "should not appear on the page.");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6790");
    testSetupPage.open();
    final String propertyId = testSetupPage.getString("propertyId");

    EditPropertyPage editPropertyPage = new EditPropertyPage();
    editPropertyPage.open(propertyId);

    Assert.assertFalse(editPropertyPage.checkLockAmountsExists(), "Lock Amounts Check Box exists.");
  }

  @Test
  public void testSaveDisallowChkBox() {
    Logger.info("To validate that when the FIXED_AMOUNTS customization is disabled and the "
        + "DISALLOW_PRE_PAYMENTS is enabled, then a PM can save the Disallow Pre-Payment customization");

    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, "tc6790");
    testSetupPage.open();
    final String propertyId = testSetupPage.getString("propertyId");

    EditPropertyPage editPropertyPage = new EditPropertyPage();
    editPropertyPage.open(propertyId);
    editPropertyPage.clickLockZeroDollarsCheckBox();
    editPropertyPage.clickSavePropertyInfo();

    DataBaseConnector dataBaseConnector = new DataBaseConnector();
    dataBaseConnector.createConnection();
    Connection connection = dataBaseConnector.getConnection();
    CustomizationsPropertyDAO custDAO = new CustomizationsPropertyDAO();
    CustomizationsPropertyDTO customization = custDAO
        .findByPropIdAndCustomizationId(connection, Integer.valueOf(propertyId),
            CustomizationsPropertyDTO.DISALLOW_PRE_PAYMENTS, 1).get(0);

    // Customization is set to on in the test set up

    Assert.assertEquals(customization.getOverrideValue(), "NO", "Override value is set to on.");
    Assert.assertTrue(editPropertyPage.checkSuccessMessageExists(), NOT_SAVED);
  }

}
