package com.paylease.app.qa.framework.pages.admin.bankaccount;

public class FormEditPage extends FormPage {

  private static final String URL = BASE_URL
      + "admin2.php?action=pm_bank_accounts_addnew&user_id={userId}&account_id={accountId}";

  private String accountId;

  /**
   * Create a Form Page for editing a bank account.
   *
   * @param pmId PM ID for the given bank account
   * @param accountId ID for the given bank account
   */
  FormEditPage(String pmId, String accountId) {
    super(pmId);

    this.accountId = accountId;
  }

  public void open() {
    String url = URL.replace("{userId}", pmId).replace("{accountId}", accountId);
    openAndWait(url);
  }


}
