package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditPropertyPage extends PageBase {

  private static String URL = BASE_URL + "pm/prop/edit";

  @FindBy(id = "p_name")
  private WebElement propertyCommunityNameTxtBox;

  @FindBy(id = "p_country")
  private WebElement countrySelect;

  @FindBy(id = "p_city")
  private WebElement cityTxtBox;

  @FindBy(id = "p_state")
  private WebElement stateSelect;

  @FindBy(id = "p_zip")
  private WebElement zipTxtBox;

  @FindBy(id = "unit_count")
  private WebElement unitCountTxBox;

  @FindBy(id = "refid")
  private WebElement refIdtxtBox;

  @FindBy(id = "pymt_freq")
  private WebElement paymentFreqSelect;

  @FindBy(id = "lock_amounts")
  private WebElement lockAmountsChkBox;

  @FindBy(id = "lock_zero_dollars")
  private WebElement disallowPrePaymensChkBox;

  @FindBy(name = "save_prop_info")
  private WebElement savePropertyInfoBtn;

  @FindBy(className = "success_message")
  private WebElement successSaveMessage;

  public void open(String propertyId) {
    openAndWait(URL + "/" + propertyId);
  }

  /**
   * Click save button.
   */
  public void clickSavePropertyInfo() {
    clickAndWait(savePropertyInfoBtn);
  }

  /**
   * Click Disallow Pre-Payments Checkbox.
   */
  public void clickLockZeroDollarsCheckBox() {
    disallowPrePaymensChkBox.click();
  }

  /**
   * Check if the success message is displayed after saving property info
   * @return boolean
   */
  public boolean checkSuccessMessageExists() {
    return successSaveMessage.isDisplayed();
  }

  /**
   * Check if lock amounts exists
   * @return
   */
  public boolean checkLockAmountsExists() {
    return driver.findElements(By.id("lock_amounts")).size() > 0;
  }

  /**
   * Check if the lock zero check box is selected.
   *
   * @return boolean
   */
  public boolean LockZeroDollarsIsSelected() {
    return disallowPrePaymensChkBox.isSelected();
  }

}
