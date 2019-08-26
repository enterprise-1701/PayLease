package com.paylease.app.qa.framework.pages.admin;

import com.paylease.app.qa.framework.pages.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InvoiceDetails extends PageBase {

  private static final String URL =
      BASE_URL + "admin2.php?action=invoice_details&invoice_id={invoiceId}";
  private static final String EXPORT_FILE_NAME = "pm-fees-invoice-{invoiceId}.xlsx";

  @FindBy(id = "download_excel")
  private WebElement downloadExcel;

  private String url;
  private String exportFileName;

  /**
   * Change URL to point to specific invoice.
   *
   * @param invoiceId Invoice ID from test setup page
   */
  public InvoiceDetails(String invoiceId) {
    super();
    this.url = URL.replace("{invoiceId}", invoiceId);
    this.exportFileName = EXPORT_FILE_NAME.replace("{invoiceId}", invoiceId);
  }

  // ********************************************Action*********************************************

  public void open() {
    openAndWait(url);
  }

  public void clickDownload(String downloadPath) {
    downloadExcel.click();
    waitForFileToDownload(downloadPath, exportFileName);
  }

}
