package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PmSetUpFees extends PageBase {

  private static final String URL_TEMPLATE =
      BASE_URL + "admin2.php?action=set_up_fees&user_id={pm_id}";
  private String url;

  @FindBy(id = "flexible_fee_setup")
  private WebElement flexFeeStructureLink;

  // ********************************************Action*********************************************

  public PmSetUpFees(int pmId) {
    super();
    url = URL_TEMPLATE.replace("{pm_id}", String.valueOf(pmId));
  }

  public void open() {
    openAndWait(url);
  }

  /**
   * Check if the flexible fee structure link is present.
   *
   * @return true if link is present, false otherwise
   */
  public boolean isFlexibleFeeStructureLinkVisible() {
    return isElementDisplayed(By.id("flexible_fee_setup"));
  }

  public void clickFlexibleFeeStructure() {
    clickAndWait(flexFeeStructureLink);
  }
}
