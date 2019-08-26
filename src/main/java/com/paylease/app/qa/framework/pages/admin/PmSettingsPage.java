package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * PmSettingsPage.
 */
public class PmSettingsPage extends PageBase {

  private static final String URL_TEMPLATE =
      BASE_URL + "admin2.php?action=pm_ach_processor&user_id={pmId}";

  private String url;

  @FindBy(id = "block_payments_select")
  private WebElement blockPaymentDropdown;

  @FindBy(id = "update_settings_btn")
  private WebElement updateBtn;

  /**
   * PmSettingsPage constructor.
   *
   * @param pmId Id of the PM viewing the setup page
   */
  public PmSettingsPage(String pmId) {
    super();

    url = URL_TEMPLATE.replace("{pmId}", pmId);
  }

  /**
   * Open the page.
   */
  public void open() {
    openAndWait(url);
  }

  /**
   * Change the stop accepting payments setting.
   *
   * @param stopAccepting true to stop accepting and false to accept payments as normal
   */
  public void updateStopAcceptingPayment(boolean stopAccepting) {
    Select select = new Select(blockPaymentDropdown);
    if (stopAccepting) {
      select.selectByValue("1");
    } else {
      select.selectByValue("0");
    }
  }

  /**
   * Check if the stop accepting payments option is enabled.
   *
   * @return true if the stop accepting payments option is selected and false otherwise
   */
  public boolean isStopAcceptingPaymentTurnedOn() {
    Select select = new Select(blockPaymentDropdown);
    WebElement selectedValue = select.getFirstSelectedOption();

    String value = selectedValue.getAttribute("value");

    return value.equals("1");
  }

  /**
   * Click the update button.
   */
  public void clickUpdate() {
    clickAndWait(updateBtn);
  }
}
