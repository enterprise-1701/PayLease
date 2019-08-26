package com.paylease.app.qa.framework.pages.resident;

public class SsoCreditCardLoginPage extends SsoPaymentMethodLoginPage {
  private static final String URL = BASE_URL + "sso_credit_card/index/{token}";

  @Override
  protected String getUrlTemplate() {
    return URL;
  }
}
