package com.paylease.app.qa.framework.pages.admin;

import com.github.javafaker.Faker;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Mahfuz Alam on 09/26/2017.
 */

public class PmPrincipalProfileEditPage extends PageBase {

  private static final String URL = BASE_URL
      + "admin2.php?action=user_details_pm&selected_tab=principal_profile_edit&user_id={pmId}";

  private String url;

  @FindBy(id = "saveBtn")
  private WebElement saveBtn;

  @FindBy(id = "taxid")
  private WebElement taxId;

  /**
   * Page object representing the Principal Profile Edit page in admin.
   *
   * @param pmId PM ID of the profile to edit
   */
  public PmPrincipalProfileEditPage(String pmId) {
    super();
    this.url = URL.replace("{pmId}", pmId);
  }

  // ********************************************Action*********************************************

  /**
   * Enter a random value for tax ID.
   */
  public void enterTaxId() {
    highlight(taxId);
    taxId.click();

    Faker faker = new Faker();
    String taxIdValue = String.valueOf(faker.number().numberBetween(100000000, 999999999));

    Logger.trace("Entering Tax Id: " + taxIdValue);
    taxId.sendKeys(taxIdValue);
  }

  public void open() {
    openAndWait(url);
  }

  public void save() {
    highlight(saveBtn);
    clickAndWait(saveBtn);
  }
}
