package com.paylease.app.qa.framework.utility.difftool;

import com.paylease.app.qa.framework.Logger;
import de.redsix.pdfcompare.PdfComparator;
import java.io.IOException;

public class PdfFileDiff {

  /**
   * Check if two pdf files are equal.
   */
  public boolean isEqual(String filePath1, String filePath2) {
    boolean isEqual = false;
    try {
      isEqual = new PdfComparator<>(filePath1, filePath2).compare().isEqual();
    } catch (IOException e) {
      Logger.trace(e.toString());
    }

    return isEqual;
  }
}
