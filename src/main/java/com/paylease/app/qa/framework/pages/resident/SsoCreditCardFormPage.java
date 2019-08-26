package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.paymentflow.CardAccountDetailsPage;
import org.openqa.selenium.By;

public class SsoCreditCardFormPage extends CardAccountDetailsPage {

  @Override
  public boolean pageIsLoaded() {
    return !driver.findElements(By.id("sso_credit_card")).isEmpty();
  }
}
