package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TaxEntitiesUploadPage extends PageBase {

  private String url = BASE_URL + "pm/tax_entities/upload";

  public void open() {
    openAndWait(url);
  }

  public void chooseTaxEntityFile(File file) {
    WebElement fileInputField = driver.findElement(By.name("tax_entities_file"));
    String path = file.getAbsolutePath();
    fileInputField.sendKeys(path);
  }

  public void uploadFile() {
    WebElement uploadButton = driver.findElement(By.id("upload_button"));
    clickAndWait(uploadButton);
  }

  public String getSuccessMessage() {
    WebElement message = driver.findElement(By.className("success_message"));
    return message.getText();
  }

  public WebElement getErrorMessageTable() {
    return driver.findElement(By.id("error_feedback"));
  }
}
