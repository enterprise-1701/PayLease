package com.paylease.app.qa.framework.components.datatable;

import com.paylease.app.qa.framework.components.DataTable;
import org.openqa.selenium.WebElement;

public class TaxEntitiesUploadError extends DataTable {
  public enum Columns {
    LINE_NUM("Line #"),
    ERROR("Error");

    private String label;

    Columns(String label) { this.label = label.toLowerCase(); }

    public String getLabel() {
      return label;
    }
  }

  public TaxEntitiesUploadError(WebElement table) {
    super(table, null);

    setColumnIndices();
  }

}
