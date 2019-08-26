package com.paylease.app.qa.framework.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FlexibleFeeCustomSettingEditPage extends FlexibleFeeAddAndEdit {

  private static final String URL_TEMPLATE =
      BASE_URL + "admin/flexible_fee_structure/custom_setting/{pmId}/{feeId}";

  private String feeId;

  @FindBy(id = "property_name")
  private WebElement propertyName;

  /**
   * The constructor.
   *
   * @param pmId the PM the fee belongs to
   * @param feeId the feeId to edit
   */
  public FlexibleFeeCustomSettingEditPage(String pmId, String feeId) {
    url = URL_TEMPLATE.replace("{pmId}", pmId).replace("{feeId}", feeId);
    this.pmId = pmId;
    this.feeId = feeId;
  }


  /**
   * Page is loaded if the form contains input elements for the pmId and feeId.
   *
   * @return true if the pmId and feeId form elements are found
   */
  public boolean pageIsLoaded() {
    try {
      WebElement hiddenPmId = form.findElement(By.name("pm_id"));
      WebElement hiddenFeeId = form.findElement(By.name("fee_id"));

      return hiddenPmId.getAttribute("value").equals(pmId)
          && hiddenFeeId.getAttribute("value").equals(feeId);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Get the property name.
   *
   * @return the property name
   */
  public String getPropertyName() {
    return propertyName.getText();
  }
}