package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.pages.PageBase;

public class ResCreateFixedAutopayPage extends PageBase {

  private static final String URL = BASE_URL + "resident/create_fixed_autopay";

  /** Resident fixed autopay page object. */
  public ResCreateFixedAutopayPage() {
    super();
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(URL);
  }

  public boolean pageIsLoaded() {
    return getTitle().equals("Fixed AutoPay");
  }
}
