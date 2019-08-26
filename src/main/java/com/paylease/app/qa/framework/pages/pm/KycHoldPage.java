package com.paylease.app.qa.framework.pages.pm;

import com.paylease.app.qa.framework.pages.PageBase;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class KycHoldPage extends PageBase {

  private static final String URL = BASE_URL + "pm/kyc/hold";

  // ********************************************Action*********************************************

  /**
   * Determine if page is loaded.
   *
   * @return true if the page title = "KYC Review"
   */
  public boolean pageIsLoaded() {
    return getTitle().equalsIgnoreCase("KYC Review");
  }

  public String getHoldMessage() {
    return getTextBySelector(By.id("hold_message"));
  }

  public String getSuccessMessage() {
    return getTextBySelector(By.id("success_message_content"));
  }

  public boolean isDownloadLittleAgreementButtonPresent() {
    return isElementDisplayed(By.id("download_litle_agreement"));
  }

  public void open() {
    openAndWait(URL);
  }

  public boolean isUploadFormPresent() {
    return isElementDisplayed(By.id("upload_documents"));
  }

  public boolean isOfficerFileUploadFieldPresent(String officerType) {
    return isElementDisplayed(By.id("file-upload-field-" + officerType));
  }

  public String getOfficerUploadHeader(String officerType) {
    return getTextBySelector(
        By.cssSelector("#file-upload-field-" + officerType + " .officer-upload-header"));
  }

  public String getOfficerErrorMessage(String officerType) {
    return getTextBySelector(
        By.cssSelector("#file-upload-field-" + officerType + " .officer-upload-error-message"));
  }

  public String getRequiredDocumentMessage(String officerType) {
    return getTextBySelector(
        By.cssSelector("#file-upload-field-" + officerType + " .officer-upload-solution"));
  }

  public String getUploadedFileMessage(String officerType) {
    return getTextBySelector(
        By.cssSelector("#file-upload-field-" + officerType + " .officer-uploaded-message"));
  }

  public void chooseOfficerFile(String officerType, File file, String solutionKey) {
    WebElement fileInputField = driver.findElement(
        By.cssSelector("#file-upload-field-" + officerType + " [name='" + solutionKey + "']"));
    String path = file.getAbsolutePath();
    fileInputField.sendKeys(path);
  }

  public boolean isUploadButtonDisabled(String officerType) {
    return isElementDisabled(By.id(officerType + "_form_submit"));
  }

  public void clickOfficerFileUpload(String officerType) {
    WebElement button = driver
        .findElement(By.cssSelector("#file-upload-field-" + officerType + " input[type='submit']"));
    clickAndWaitForAjax(button);
  }

  public void clickOfficerRemoveFile(String officerType, String solutionKey) {
    String id = officerType + "_" + solutionKey + "_delete_icon";
    WebElement button = driver.findElement(By.id(id));
    button.click();
  }

  public boolean pageHasPmOfficerWithTwoFileUploads(String officerType) {
    List<WebElement> inputs = driver
        .findElements(By.cssSelector("#file-upload-field-" + officerType + " input[type='file']"));
    return inputs.size() == 2;
  }

  public String getErrorBoxErrorMessage() {
    WebElement message = driver.findElement(By.className("error_item"));
    return message.getText();
  }

  /**
   * Get array of errors on Hold Page.
   *
   * @return array of errors
   */
  public ArrayList<String> getErrors() {
    List<WebElement> errorsArray = driver
        .findElements(By.cssSelector(".officer-upload-error-message"));
    ArrayList<String> errors = new ArrayList<>();
    for (WebElement errorArrayItem : errorsArray) {
      errors.add(errorArrayItem.getText());
    }
    return errors;
  }

  /**
   * Get array of solutions on Hold Page.
   *
   * @return array of solutions
   */
  public ArrayList<String> getSolutions() {
    List<WebElement> solutionsArray = driver
        .findElements(By.cssSelector(".officer-upload-solution"));
    ArrayList<String> solutions = new ArrayList<>();
    for (WebElement solutionArrayItem : solutionsArray) {
      solutions.add(solutionArrayItem.getText());
    }
    return solutions;
  }
}