package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;

public class LogAs extends PageBase {

  private static final String URL_TEMPLATE =
      BASE_URL + "admin2.php?action=pm_logas&user_id={user_id}";

  private String userId;
  private String url;

  public LogAs(String userId) {
    url = URL_TEMPLATE.replace("{user_id}", userId);
  }

  public void open() {
    openAndWait(url);
  }
}
