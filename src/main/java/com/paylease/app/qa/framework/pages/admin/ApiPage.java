package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ApiPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=pm_api&user_id={pmId}";

  private String url;

  @FindBy(id = "check_fee_label")
  private WebElement checkFeeLabel;

  @FindBy(id = "nsf_check_fee_label")
  private WebElement nsfCheckFeeLabel;

  @FindBy(id = "nsf_check_fee_input")
  private WebElement nsfCheckFeeInput;

  @FindBy(id = "save_settings")
  private WebElement saveSettingsBtn;

  /**
   * ApiPage constructor set the pm id for the page.
   *
   * @param pmId test pm id
   */
  public ApiPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  /**
   * Open the configured URL.
   */
  public void open() {
    openAndWait(url);
  }

  /**
   * Get the Check Fee label.
   *
   * @return the label
   */
  public String getCheckFeeLabel() {
    return getTextBySelector(By.id("check_fee_label"));
  }

  /**
   * Get the NSF Check Fee label.
   *
   * @return the label
   */
  public String getNsfCheckFeeLabel() {
    return getTextBySelector(By.id("nsf_check_fee_label"));
  }

  /**
   * Determine if NSF Check Fee input field displayed.
   *
   * @return true if input field is displayed
   */
  public boolean isNsfCheckFeeInputPresent() {
    return nsfCheckFeeInput.isDisplayed();
  }

  /**
   * Returns NSF Check Fee amount set in the input field.
   *
   * @return text amount
   */
  public String getNsfCheckFeeInputValue() {
    return nsfCheckFeeInput.getAttribute("value");
  }

  /**
   * Enter value in Nsf Check Fee input field.
   *
   * @param value value to enter
   */
  public void enterNsfCheckFeeValue(String value) {
    nsfCheckFeeInput.clear();
    enterText(nsfCheckFeeInput, value);
  }

  /**
   * Click the save settings button.
   */
  public void clickSaveSettingsBtn() {
    clickAndWait(saveSettingsBtn);
  }
}
