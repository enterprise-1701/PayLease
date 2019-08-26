package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

public abstract class SsoPaymentMethodLoginPage extends PageBase {
  private String token = "";

  public void open() {
    String url = getUrlTemplate().replace("{token}", token);
    openAndWait(url);
  }

  public String getErrorMessage() {
    return getTextBySelector(By.cssSelector(".error"));
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean pageIsLoaded() {
    return false;
  }

  protected abstract String getUrlTemplate();
}
