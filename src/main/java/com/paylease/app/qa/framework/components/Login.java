package com.paylease.app.qa.framework.components;

import com.paylease.app.qa.framework.pages.PageBase;
import com.paylease.app.qa.framework.pages.admin.AdminMenu;
import com.paylease.app.qa.framework.pages.admin.LoginPageAdmin;
import com.paylease.app.qa.framework.pages.pm.PmLoginPage;
import com.paylease.app.qa.framework.pages.pm.PmLogoutBar;
import com.paylease.app.qa.framework.pages.resident.ResLoginPage;
import com.paylease.app.qa.framework.pages.resident.ResLogoutBar;

public class Login extends PageBase {

  public enum UserType {
    ADMIN("admin"),
    RESIDENT("resident"),
    PM("pm");

    private final String userType;

    UserType(String userType) {
      this.userType = userType;
    }

    String getValue() {
      return userType;
    }
  }

  /* Login to admin UI */
  public void logInAdmin() {
    logInUser("", UserType.ADMIN);
  }

  /**
   * Login with res, pm or admin.
   *
   * @param email email of res or pm, for admin leave blank
   * @param userType UserType type of user
   */
  public void logInUser(String email, UserType userType) {
    switch (userType) {
      case RESIDENT:
        ResLoginPage resLoginPage = new ResLoginPage();

        resLoginPage.open();
        resLoginPage.login(email, null);
        break;
      case PM:
        PmLoginPage pmLoginPage = new PmLoginPage();

        pmLoginPage.open();
        pmLoginPage.login(email);
        break;
      default:
        LoginPageAdmin loginPageAdmin = new LoginPageAdmin();
        loginPageAdmin.login();
        break;
    }
  }

  /* Logout from admin UI*/
  public void logOutAdmin() {
    AdminMenu adminMenu = new AdminMenu();
    adminMenu.clickLogoutButton();
  }

  /* Logout from Res UI */
  public void logOutResident() {
    ResLogoutBar resLogoutBar = new ResLogoutBar();
    resLogoutBar.clickLogoutButton();
  }

  /* Logout from PM UI */
  public void logOutPm() {
    PmLogoutBar pmLogoutBar = new PmLogoutBar();
    pmLogoutBar.clickLogoutButton();
  }

}
