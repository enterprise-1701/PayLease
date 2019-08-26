package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.paymentflow.BankAccountDetailsPage;
import org.openqa.selenium.By;

public class SsoBankAccountFormPage extends BankAccountDetailsPage {

  @Override
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("sso_bank_account")).isEmpty();
  }
}
