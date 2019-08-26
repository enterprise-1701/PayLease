package com.paylease.app.qa.functional.tests.admin;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.ScriptBase;
import com.paylease.app.qa.framework.components.datatable.TaxEntitiesUploadError;
import com.paylease.app.qa.framework.pages.automatedhelper.TestSetupPage;
import com.paylease.app.qa.framework.pages.admin.TaxEntitiesUploadPage;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TaxEntitiesTest extends ScriptBase {

  private static final String REGION = "admin";
  private static final String FEATURE = "TaxEntities";

  private static final String SUCCESS_UPLOAD_N_ENTRIES = "Success: {n} profile entries were processed.";
  private static final String ERROR_LEGAL_ENTITY_NAME = "You do not have access to update the Legal "
      + "Entity Name of an existing profile.";
  private static final String ERROR_VALIDATED_RECORD = "A validated profile already exists. Validated profiles cannot be modified.";
  private static final String ERROR_DUPLICATE_RECORD = "Record is a duplicate entry.";
  private static final String ERROR_EXCEEDS_MAX_RECORDS = "This file exceeds the maximum row limit of 1500. Please upload a smaller file.";
  private static final String ERROR_FILE_UPLOAD = "Uploading your file failed. Please try again, or contact support";

  //Happy path
  @Test
  public void tc6135() {
    Logger.info("Verify that when PMs are uploading multiple tax entities with unique TINs"
        + " that they are correctly imported");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6135", "unique_tins.csv");

    String message = taxUploadPage.getSuccessMessage();

    // If the file is altered please update this expected message.
    String expected = returnSuccessMessage(3);
    Assert.assertEquals(message, expected);
  }

  @Test
  public void tc6136() {
    Logger.info("Verify that when non-unrestricted users are uploading multiple tax entities with"
        + "duplicate TINs and different legal entity names that they receive an error message ");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6136", "duplicate_tins_different_legal_entity_names.csv");

    TaxEntitiesUploadError taxEntitiesUploadError = new TaxEntitiesUploadError(taxUploadPage.getErrorMessageTable());
    WebElement row = taxEntitiesUploadError.getRowByRowNum(1);
    String errorMessage = row.findElement(By.className("upload_error_message")).getText();

    // If the file is altered please update this expected message.
    Assert.assertEquals(errorMessage, ERROR_LEGAL_ENTITY_NAME );
  }

  @Test
  public void tc6137() {
    Logger.info("Verify that when non-unrestricted users are uploading multiple tax entities with"
        + "duplicate TINs and different legal entity names that they receive an error message ");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6137", "unique_tins.csv");

    TaxEntitiesUploadError taxEntitiesUploadError = new TaxEntitiesUploadError(taxUploadPage.getErrorMessageTable());
    WebElement row = taxEntitiesUploadError.getRowByRowNum(1);
    String errorMessage = row.findElement(By.className("upload_error_message")).getText();

    // If the file is altered please update this expected message.
    Assert.assertEquals(errorMessage, ERROR_LEGAL_ENTITY_NAME );
  }

  @Test
  public void tc6143() {
    Logger.info("Verify that uploading duplicate Tax Entities with the same data will not upload 2 "
        + "tax entities");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6135", "duplicate_tins_same_legal_entity_names.csv");

    TaxEntitiesUploadError taxEntitiesUploadError = new TaxEntitiesUploadError(taxUploadPage.getErrorMessageTable());
    WebElement row = taxEntitiesUploadError.getRowByRowNum(1);
    String errorMessage = row.findElement(By.className("upload_error_message")).getText();

    // If the file is altered please update this expected message.
    Assert.assertEquals(errorMessage, ERROR_DUPLICATE_RECORD);

  }

  @Test
  public void tc6144() {
    Logger.info("Verify that a user is not able to upload a tax entity that is a duplicate of an "
        + "existing tax entity for the PM");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6144", "unique_tins.csv");

    TaxEntitiesUploadError taxEntitiesUploadError = new TaxEntitiesUploadError(taxUploadPage.getErrorMessageTable());
    WebElement row = taxEntitiesUploadError.getRowByRowNum(1);
    String errorMessage = row.findElement(By.className("upload_error_message")).getText();

    // If the file is altered please update this expected message.
    Assert.assertEquals(errorMessage, ERROR_DUPLICATE_RECORD);
  }

  @Test
  public void tc6148() {
    Logger.info("Verify that uploading tax entities with duplicate tax IDs and legal entity names "
        + "in the file, but have different non-essential information, will update the tax entity");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6135", "duplicate_tins_same_legal_entity_names_different_data.csv");

    String message = taxUploadPage.getSuccessMessage();

    // If the file is altered please update this expected message.
    String expected = returnSuccessMessage(5);
    Assert.assertEquals(message, expected);

  }

  @Test
  public void tc6149() {
    Logger.info("Verify that uploading tax entities with duplicate tax IDs and legal entity names "
        + "in the database, but have different non-essential information, will update the tax entity");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6144", "unique_tins_different_data.csv");

    String message = taxUploadPage.getSuccessMessage();

    // If the file is altered please update this expected message.
    String expected = returnSuccessMessage(3);
    Assert.assertEquals(message, expected);

  }

  @Test
  public void tc6156() {
    Logger.info("Verify that a user is able to upload a file up to 1500 entries");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6135", "file_1500_records.csv");

    String message = taxUploadPage.getSuccessMessage();

    String expected = returnSuccessMessage(1499);
    Assert.assertEquals(message, expected);
  }

  @Test
  public void tc6157() {
    Logger.info("Verify that a user is unable to upload a file exceeding 1500 entries");

    TaxEntitiesUploadPage taxUploadPage = setUpAndUploadCsv("tc6135", "file_5000_records.csv");
             
    String errorMessage = taxUploadPage.getErrorMessages().get(0);

    Assert.assertEquals(errorMessage, ERROR_EXCEEDS_MAX_RECORDS);
  }

  //--------------------------------------Test Methods----------------------------------------------

  private TaxEntitiesUploadPage setUpAndUploadCsv(String testCase, String CsvFile) {
    TestSetupPage testSetupPage = new TestSetupPage(REGION, FEATURE, testCase);
    testSetupPage.open();

    TaxEntitiesUploadPage taxUploadPage = new TaxEntitiesUploadPage();
    taxUploadPage.open();

    File uploadedFile = getUploadTestFile(CsvFile);
    taxUploadPage.chooseTaxEntityFile(uploadedFile);
    taxUploadPage.uploadFile();

    return taxUploadPage;
  }

  private File getUploadTestFile(String fileName) {
    return new File(UPLOAD_DIR_PATH + File.separator + fileName);
  }

  private String returnSuccessMessage(int numberOfEntries) {
    return SUCCESS_UPLOAD_N_ENTRIES.replace("{n}", String.valueOf(numberOfEntries));
  }
}
