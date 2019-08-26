package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Class ConfigureIntegrationPage.
 */
public class ConfigureIntegrationPage extends PageBase {

  private static final String URL =
      BASE_URL + "admin2.php?action=integration_configuration&user_id={pmId}";

  private String url;

  @FindBy(id = "integration_type")
  private WebElement integrationType;

  /**
   * ConfigureIntegrationPage constructor set the pm id for the page.
   *
   * @param pmId test pm id
   */
  public ConfigureIntegrationPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  /**
   * open the page.
   */
  public void open() {
    openAndWait(this.url);
  }

  /**
   * select integration type.
   *
   * @param type the type of software
   */
  public void selectIntegrationType(String type) {
    new Select(integrationType).selectByValue(type);
  }

  /**
   * Enter an email address on the form.
   *
   * @param email address to enter
   */
  public void enterEmailAddress(String email) {
    WebElement emailField = driver.findElement(By.id("ftp_email"));
    highlight(emailField);
    emailField.clear();
    emailField.sendKeys(email);
  }

  /**
   * click the submit button on the form.
   */
  public void clickSubmit() {
    WebElement submitButton = driver.findElement(By.name("save_type"));
    clickAndWait(submitButton);
  }

  /**
   * Determine if hasFtp checkbox is visible.
   *
   * @return boolean
   */
  public boolean isHasFtpVisible() {
    return isElementDisplayed(By.name("has_ftp"));
  }

  /**
   * Determine if ftp email input is visible.
   *
   * @return boolean
   */
  public boolean isFtpEmailVisible() {
    return isElementDisplayed(By.id("ftp_email"));
  }

  /**
   * Determine if ftp instructions is visible.
   *
   * @return boolean
   */
  public boolean isFtpInstructionsVisible() {
    return isElementDisplayed(By.id("ftp_instructions"));
  }

  /**
   * Select the Ftp tab.
   */
  public void selectFtpTab() {
    driver.findElement(By.id("tabs-ftp-header")).click();
  }

  /**
   * Checks if the password label is present in the table.
   *
   * @return True if password label is found
   */
  public boolean isPasswordPresentInFtpSettingsTable() {
    List<WebElement> labels = driver.findElements(By.cssSelector("#ftp_settings_table label"));

    for (WebElement label : labels) {
      if (label.getText().equals("FTP Password:")) {
        return true;
      }
    }

    return false;
  }
}
