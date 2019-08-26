package com.paylease.app.qa.framework.pages.resident;

import com.paylease.app.qa.framework.components.DataTable;
import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

class ResViewAutopays extends PageBase {

  @FindBy(id = "autopay_tbl")
  WebElement dataTableElement;

  DataTable dataTable;
}
