package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Mahfuz Alam on 09/26/2017.
 */

public class PmApprovalPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=approvals";
  private static final String APPROVE_LINK_TEXT = "Approve";

  @FindBy(id = "approvalMessage")
  private WebElement approvalMessage;

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  /**
   * Click "Approve" link for the PM with the given email address.
   *
   * @param email Email of PM to approve
   * @return Approval message text
   */
  public String clickApprove(String email) {
    WebElement pmTableRow = getPmRow(email);
    WebElement pmApproveLink = pmTableRow.findElement(By.linkText(APPROVE_LINK_TEXT));
    clickAndWait(pmApproveLink);
    highlight(approvalMessage);
    return approvalMessage.getText();
  }

  /**
   * Get the PM ID for the row matching the given email.
   *
   * @param email Email of the PM to find
   * @return PM ID
   */
  public String getPmId(String email) {
    WebElement pmTableRow = getPmRow(email);
    WebElement pmIdCell = pmTableRow.findElement(By.xpath("td/a[1]"));
    highlight(pmIdCell);
    return pmIdCell.getText();
  }

  private WebElement getPmRow(String email) {
    WebElement tableCell = driver.findElement(By.xpath("//td[contains(text(),'" + email + "')]"));
    highlight(tableCell);
    return tableCell.findElement(By.xpath(".."));
  }
}
