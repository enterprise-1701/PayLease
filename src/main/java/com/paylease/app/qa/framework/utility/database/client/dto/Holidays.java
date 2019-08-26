package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;

public class Holidays {

  private Date hDate;
  private String hTitle;

  //******************
  //Getter Methods
  //******************

  public Date getHDate() {
    return hDate;
  }

  public String getHTitle() {
    return hTitle;
  }

  //******************
  //Setter Methods
  //******************

  public void sethDate(Date hDate) {
    this.hDate = hDate;
  }

  public void sethTitle(String hTitle) {
    this.hTitle = hTitle;
  }
}
