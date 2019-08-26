package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditSubAccountPage extends PageBase {

  private static final String URL = BASE_URL + "pm/sub_accounts/edit/{subAcctId}";

  private String url;

  @FindBy(name = "sub_acct_role")
  private WebElement role;

  @FindBy(name = "save")
  private WebElement save;

  // ********************************************Action*********************************************

  public EditSubAccountPage(String subAcctId) {
    this.url = URL.replace("{subAcctId}", subAcctId);
  }

  public void open() {
    openAndWait(this.url);
  }

  /**
   * Enter role in the field.
   */
  public void addRole(String subAccRole) {
    role.clear();
    role.sendKeys(subAccRole);
  }

  /**
   * Click save button.
   */
  public void clickSave() {
    clickAndWait(save);
  }

  /**
   * Get current role saved in the sub-account.
   */
  public String getRole() {
    return role.getAttribute("value");
  }
}
