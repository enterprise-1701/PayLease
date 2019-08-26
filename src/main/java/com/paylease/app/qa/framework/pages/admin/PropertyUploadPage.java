package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.DriverManager;
import com.paylease.app.qa.framework.pages.PageBase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PropertyUploadPage extends PageBase {

  private static final String URL = BASE_URL + "admin2.php?action=upload_properties";

  @FindBy(id = "property_upload_pm_id_verification_input")
  private WebElement propertyUploadPmIdVerificationInput;

  @FindBy(id = "property_upload_template_file_input")
  private WebElement propertyUploadTemplateFileInput;

  @FindBy(id = "property_upload_submit_button")
  private WebElement propertyUploadSubmitButton;

  @FindBy(id = "add_payees")
  private WebElement propertyUploadAddPayeesCheckbox;

  @FindBy(id = "property_upload_debug_checkbox")
  private WebElement propertyUploadDebugCheckbox;

  /**
   * PropertyUploadPage constructor
   */
  public PropertyUploadPage() {
    super();
    this.driver = DriverManager.getDriver();
  }

  /**
   * Open the configured URL.
   */
  public void open() {
    openAndWait(URL);
  }

  /**
   *
   * @param pmid - pm id that is used for the PM ID field on the form
   */
  public void setPropertyUploadPmIdVerificationInput(String pmid) {
    enterText(this.propertyUploadPmIdVerificationInput, pmid);
  }

  /**
   *
   * @param fileLocation - location of the tmp file that we are passing into the input field
   */
  public void setFileForTest(String fileLocation) {
    propertyUploadTemplateFileInput.sendKeys(fileLocation);
  }

  /**
   *
   * @return String - html of result page
   */
  public String submit() {
    clickAndWait(propertyUploadSubmitButton);
    return new PropertyUploadResultPage().gatherResultPageHTML();
  }
}
