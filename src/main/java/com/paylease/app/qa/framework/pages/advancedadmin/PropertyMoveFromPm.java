package com.paylease.app.qa.framework.pages.advancedadmin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PropertyMoveFromPm extends PageBase {

  public static final String URL_TEMPLATE =
      BASE_URL + "admin/adv_admin_pm_property_move/displayForm/{pmId}";

  private String url;

  @FindBy(name = "new_pm_id")
  private WebElement newPmId;

  @FindBy(id = "search_btn")
  private WebElement searchBtn;

  @FindBy(name = "JIRA_Ticket")
  private WebElement jiraTicketNumTextbox;

  @FindBy(name = "notes_field")
  private WebElement jiraTicketNotesTextbox;

  @FindBy(name = "action")
  private WebElement movePropSubmitBtn;

  @FindBy(id = "selected_properties")
  private WebElement selectedPropsCheckbox;

  public PropertyMoveFromPm(String pmId) {
    url = URL_TEMPLATE.replace("{pmId}", pmId);
  }

  public void open() {
    openAndWait(url);
  }

  private void checkPropertyToMove() {
    selectedPropsCheckbox.click();
  }

  private void insertPmIdToMovePropertyTo(String pmId) {
    setKeys(newPmId, pmId);
  }

  private void insertJiraTicketNum(String jiraTicketNum) {
    setKeys(jiraTicketNumTextbox, jiraTicketNum);
  }

  private void insertJiraTicketNotes(String notes) {
    setKeys(jiraTicketNotesTextbox, notes);
  }

  private void clickMovePropSubmitBtn() {
    clickAndWait(movePropSubmitBtn);
  }

  /**
   * Fill the form to move property to the pm requested.
   *
   * @param toPmId pm a property to be moved to
   * @param jiraNum JIRA ticket number
   * @param jiraNote notes for JIRA
   */
  public void movePropFromOldPmToNewPm(String toPmId, String jiraNum, String jiraNote) {
    checkPropertyToMove();
    insertPmIdToMovePropertyTo(toPmId);
    insertJiraTicketNum(jiraNum);
    insertJiraTicketNotes(jiraNote);
    clickMovePropSubmitBtn();
  }
}
