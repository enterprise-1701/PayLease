package com.paylease.app.qa.framework.pages.registration;

public class RenterRegistrationPage extends ResRegistrationPage {

  private static final String URL = BASE_URL + "registration/renter";

  public void open() {
    openAndWait(URL);
  }

}
