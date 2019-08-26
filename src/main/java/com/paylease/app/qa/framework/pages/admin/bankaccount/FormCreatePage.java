package com.paylease.app.qa.framework.pages.admin.bankaccount;

public class FormCreatePage extends FormPage {

  private static final String URL = BASE_URL
      + "admin2.php?action=pm_bank_accounts_addnew&user_id={userId}";

  /**
   * Create a Form Page for creating a bank account.
   *
   * @param pmId PM ID for the given bank account
   */
  FormCreatePage(String pmId) {
    super(pmId);
  }

  public void open() {
    String url = URL.replace("{userId}", pmId);
    openAndWait(url);
  }
}
