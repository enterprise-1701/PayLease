package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class PmCcGatewayPage extends PageBase {

  public static final String CREATE_LEGAL_ENTITY_SUBMIT = "create_legal_entity_submit";
  public static final String RETRY_LEGAL_ENTITY_SUBMIT = "retry_legal_entity_submit";
  public static final String RETRIEVE_LEGAL_ENTITY_SUBMIT = "retrieve_legal_entity_submit";
  public static final String CREATE_SUB_MERCHANT_SUBMIT = "retry_sub_merchant_submit";

  private static final String[] AVAILABLE_BUTTONS = {CREATE_LEGAL_ENTITY_SUBMIT,
      CREATE_SUB_MERCHANT_SUBMIT, RETRY_LEGAL_ENTITY_SUBMIT, RETRIEVE_LEGAL_ENTITY_SUBMIT};
  private static final String URL = BASE_URL + "admin2.php?action=pm_cc_gateway&user_id={pmId}";
  private static final String LITLE_MODAL_ID = "dialog_modal_litle";
  private static final String LITLE_RESPONSE_MESSAGE = "litle_error_message";

  @FindBy(name = "active_processors[USD]")
  private WebElement usdProcessorDropDown;

  private String url;

  public PmCcGatewayPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  public void open() {
    openAndWait(url);
  }

  public void selectUsdProcessor(String processor) {
    //TODO:  BETTER NAME
    Select processorDropDown = new Select(usdProcessorDropDown);
    processorDropDown.selectByValue(processor);
  }

  public void clickSettingsModal(String currencyCode) {
    String id = "settings_modal_" + currencyCode;
    WebElement element = driver.findElement(By.id(id));
    element.click();
  }

  public void clickLitleModal() {
    WebElement element = driver.findElement(By.id(LITLE_MODAL_ID));
    element.click();
  }

  public void clickSpecifiedButton(String button) {
    WebElement element = driver.findElement(By.id(button));
    element.click();
    waitUntilAjaxEnds();
  }

  public boolean areButtonsVisible() {
    for (String button : AVAILABLE_BUTTONS) {
      if (isElementPresentBySelector(By.id(button))) {
        return true;
      }
    }
    return false;
  }

  public boolean isOnlyThisButtonVisible(String buttonName) {
    for (String button : AVAILABLE_BUTTONS) {
      if (!button.equals(buttonName) && isElementPresentBySelector(By.id(button))) {
        return false;
      }
    }
    return isElementPresentBySelector(By.id(buttonName));
  }

  public String getLitleResponseDescription() {
    return driver.findElement(By.id(LITLE_RESPONSE_MESSAGE)).getText();
  }
}
