package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResMyProfilePage extends PageBase {

  private static final String URL = BASE_URL + "resident/profile";

  public static final String OPT_IN = "Credit Opt In";
  public static final String OPT_OUT = "Credit Opt Out";

  @FindBy(id = "credit_reporting_edit_btn")
  private WebElement creditReportingEditButton;

  @FindBy(id = "credit_reporting_save")
  private WebElement saveButton;

  /**
   * Resident my profile page object.
   */
  public ResMyProfilePage() {
    super();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Check if navigation from old page has occurred and new page has loaded.
   *
   * @return true when page title is correct and table is present
   */
  public boolean pageIsLoaded() {
    return getTitle().equals("MY PROFILE")
        && isElementPresentBySelector(By.id("resident_details_tbl"));
  }

  /**
   * Click on credit reporting edit button.
   */
  public void clickCreditReportingEditButton() {
    highlight(creditReportingEditButton);
    clickAndWait(creditReportingEditButton);

    Logger.trace("Clicked credit reporting edit button");
  }

  /**
   * Select credit opt in or out option.
   *
   * @param option option to select
   */
  public void selectCreditReporting(String option) {
    Logger.trace("Selected reporting option is: " + option);

    String radioValue = getRadioValue(option);

    Logger.trace("Radio button value to select: " + radioValue);

    driver.findElement(By.cssSelector("input[name='cr_status'][value='" + radioValue + "']"))
        .click();
  }

  private String getRadioValue(String method) {
    String radioValue;
    switch (method) {
      case OPT_IN:
        radioValue = "YES";
        break;
      case OPT_OUT:
        radioValue = "NO";
        break;
      default:
        radioValue = method;
    }
    return radioValue;
  }

  /**
   * Click on credit reporting save button.
   *
   */
  public void clickCreditReportingSaveButton() {
    highlight(saveButton);
    clickAndWait(saveButton);
    Logger.trace("Clicked on the save button");
  }

  /**
   * Get success message text.
   */
  public String getSuccessMessage() {
    return getTextBySelector(By.className("success_message"));
  }

  /**
   * Get admin access message.
   *
   * @return String message
   */
  public String getAdminAccessMessage() {
    return getTextBySelector(By.className("admin_access"));
  }

  /**
   * Click on Edit button for Resident Details section.
   *
   */
  public void clickEditResidentDetails() {
    WebElement editResidentDetialsBtn = driver.findElement(By.id("edit_resident_details_btn"));
    highlight(editResidentDetialsBtn);
    clickAndWait(editResidentDetialsBtn);
    Logger.trace("Clicked on Edit button on Resident Details section");
  }

  /**
   * Clear and set the unit number field.
   *
   * @param newUnitNumber String unit number. If null, generate a random string to use instead.
   */
  public void setUnitNumber(String newUnitNumber) {
    if(null==newUnitNumber) {
      DataHelper helper = new DataHelper();
      newUnitNumber = helper.generateAlphanumericString(3);
    }
    WebElement unit = driver.findElement(By.name("unit"));
    unit.clear();
    unit.sendKeys(newUnitNumber);
  }

  /**
   * Click save button.
   *
   */
  public void clickSave() {
    clickAndWait(driver.findElement(By.id("save_details")));
  }

  /**
   * Get unit number from resident details.
   *
   * @return String unit number
   */
  public String getUnitNumber() {
    return getTextBySelector(By.id("unit_number"));
  }

}
