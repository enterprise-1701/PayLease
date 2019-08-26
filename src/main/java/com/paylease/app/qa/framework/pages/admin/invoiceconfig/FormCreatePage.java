package com.paylease.app.qa.framework.pages.admin.invoiceconfig;

public class FormCreatePage extends FormPage {

  private static final String URL = BASE_URL
      + "admin2.php?action=invoices_config_addnew&user_id={userId}";

  /**
   * Create a Form Page for editing an invoice config.
   *
   * @param pmId PM ID for the given invoice config
   */
  public FormCreatePage(String pmId) {
    super(pmId);
  }

  /**
   * Open the page and initialize the select elements.
   */
  public void open() {
    String url = URL.replace("{userId}", pmId);
    openAndWait(url);
    initSelectElements();
  }
}
