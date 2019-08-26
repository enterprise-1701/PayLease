package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.By;

/**
 * Created by Glenn Tejidor on 07/26/2018.
 */
public class ResidentMakePayment extends PageBase {

  private static final String URL = BASE_URL + "resident/make_payment";

  // ********************************************Action*********************************************

  public boolean pageIsLoaded() {
    return getTitle().equals("ONE-TIME PAYMENT")
        && isElementPresentBySelector(By.className("progress_bar"));
  }
}