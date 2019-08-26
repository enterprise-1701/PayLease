package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SubAccountsPrivileges extends PageBase {

  private String url = BASE_URL + "pm/sub_accounts/privileges/{subAccId}";

  @FindBy(id = "sub_acc_priv_payments")
  private WebElement paymentHeader;

  /**
   * Constructor.
   *
   * @param subAccId sub-account id
   */
  public SubAccountsPrivileges(String subAccId) {
    super();
    url = url.replace("{subAccId}", subAccId);
  }

  public void open() {
    openAndWait(url);
  }

  /**
   * Return the name attribute of the customization check box.
   *
   * @param drawer drawer name
   * @param label Label of the customization
   * @return check box name attribute
   */
  public String getCustCheckboxNameAttr(String drawer, String label) {
    String viewCustLabelXpath = ".//*[@id='sub_acc_priv_" + drawer
        + "']/following-sibling::div[1]/descendant::*[contains(text(), '" + label + "')]";

    WebElement viewCustChkBox = driver.findElement(
        By.xpath(viewCustLabelXpath + "/preceding::input[1]")
    );

    return viewCustChkBox.getAttribute("name");
  }

  /**
   * Is Customization available on the page.
   *
   * @param code Customization code
   * @return true if found
   */
  public boolean isCustomizationAvailable(String code) {
    return isElementPresentBySelector(By.id(code));
  }

  /**
   * Click on Payments drawer and wait until one element inside payments is displayed.
   */
  public void clickPaymentsDrawerHeader() {
    paymentHeader.click();
    By customizationsInPaymentLocator = By.xpath(
        ".//*[@id='sub_acc_priv_payments']/following-sibling::div[1]");

    wait.until(ExpectedConditions
        .visibilityOfAllElements(driver.findElements(customizationsInPaymentLocator)));
  }
}
