package com.paylease.app.qa.framework.pages.resident;

public class SsoBankAccountLoginPage extends SsoPaymentMethodLoginPage {
  private static final String URL = BASE_URL + "sso_bank_account/index/{token}";

  @Override
  protected String getUrlTemplate() {
    return URL;
  }
}
