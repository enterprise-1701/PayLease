package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Customization Page.
 *
 * Used to check the value of a customization for a given PM.
 */
public class CustomizationPage extends PageBase {

  private static final String URL_TEMPLATE =
      BASE_URL + "testing/automated_helper/get_customization?pm_id={pmId}&cust_code={custCode}";

  private String url;

  @FindBy(id = "cust_code")
  private WebElement customizationValue;

  /**
   * Customization Page constructor.
   *
   * @param pmId The Id of the PM to check customizations of
   * @param custCode The Customization Code to look up
   */
  public CustomizationPage(String pmId, String custCode) {
    super();

    url = URL_TEMPLATE.replace("{pmId}", pmId).replace("{custCode}", custCode);
  }

  /**
   * Open the page.
   */
  public void open() {
    openAndWait(url);
  }

  /**
   * Check if the customization is enabled.
   *
   * @return True if the customization is on and false otherwise
   * @throws Exception If the customization doesn't exist
   */
  public boolean isCustomizationEnabled() throws Exception {
    String custValueText = customizationValue.getText();

    switch (custValueText) {
      case "1":

        return true;
      case "0":

        return false;
      default:

        throw new Exception("Invalid Customization Code");
    }
  }
}
