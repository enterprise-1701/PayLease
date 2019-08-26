package com.paylease.app.qa.framework.pages.automatedhelper;

import com.paylease.app.qa.framework.pages.PageBase;

public class LogInProcPage extends PageBase {

  public static final String URL = BASE_URL + "login/login_proc/";
  private static final String LOGIN_PROC_ADMIN_URL = URL + "admin";
  private static final String LOGIN_PROC_PM_URL = URL + "pm";
  private static final String LOGIN_PROC_RESIDENT_URL = URL + "resident";
  private static final String LOGIN_PROC_INDEX_URL = URL + "index";
  private String url = "";

  /**
   * Determine which login proc page to run.
   *
   * @param type specific login page
   */
  public LogInProcPage(String type) {
    super();

    switch (type) {
      case "admin":
        this.url = LOGIN_PROC_ADMIN_URL;
        break;
      case "pm":
        this.url = LOGIN_PROC_PM_URL;
        break;
      case "resident":
        this.url = LOGIN_PROC_RESIDENT_URL;
        break;
      case "index":
        this.url = LOGIN_PROC_INDEX_URL;
        break;
      default:
        this.url = URL;
        break;
    }
  }

  public void open() {
    openAndWait(url);
  }

}
