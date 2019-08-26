package com.paylease.app.qa.framework.utility.demos;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.difftool.ExcelFileDiff;
import com.paylease.app.qa.framework.utility.difftool.PdfFileDiff;
import com.paylease.app.qa.framework.utility.difftool.TextFileDiff;

public class FileDiffDemo {

  public static void main(String[] args) {
    String sampleFilesPath = "src/main/java/com/paylease/app/qa/framework/utility/demos/samplefiles/";

    /**
     * Compare Text Files.
     *
     */
    Logger.info("---------------Text File Comparisons---------------------");
    TextFileDiff textFileDiff = new TextFileDiff();
    Logger.info("Are the text files the same? " + textFileDiff
        .isEqual(sampleFilesPath + "file1.txt", sampleFilesPath + "file2.txt"));//Same (true)
    Logger.info("Are the text files the same? " + textFileDiff
        .isEqual(sampleFilesPath + "file1.txt", sampleFilesPath + "file3.txt"));//Different (false)

    /**
     * Compare Excel Files.
     *
     */
    Logger.info("---------------Excel File Comparisons--------------------");
    ExcelFileDiff excelFileDiff = new ExcelFileDiff();
    Logger.info("Are the Excel files the same? " + excelFileDiff
        .isEqual(sampleFilesPath + "file1.xlsx", sampleFilesPath + "file2.xlsx"));//Same (true)
    Logger.info("Are the Excel files the same? " + excelFileDiff
        .isEqual(sampleFilesPath + "xmlformat1.xls", sampleFilesPath + "xmlfomat.xls"));// (false)
    Logger.info("Are the Excel files the same? " + excelFileDiff
        .isEqual(sampleFilesPath + "file1.xlsx",
            sampleFilesPath + "file3.xlsx"));//Different cell values in Sheet 2 (false)
    Logger.info("Are the Excel files the same? " + excelFileDiff
        .isEqual(sampleFilesPath + "file3.xlsx", sampleFilesPath
            + "file4.xlsx"));//Different sheet name in Workbook 2 (2nd sheet) (false)

    /**
     * Compare PDF Files.
     *
     */
    Logger.info("---------------PDF File Comparisons----------------------");
    PdfFileDiff pdfFileDiff = new PdfFileDiff();
    Logger.info("Are the PDF files the same? " + pdfFileDiff
        .isEqual(sampleFilesPath + "file1.pdf", sampleFilesPath + "file2.pdf"));//Same (true)
    Logger.info("Are the PDF files the same? " + pdfFileDiff
        .isEqual(sampleFilesPath + "file1.pdf", sampleFilesPath + "file3.pdf"));//Different (false)
    Logger.info("Are the PDF files the same? " + pdfFileDiff
        .isEqual(sampleFilesPath + "file3.pdf", sampleFilesPath + "file4.pdf"));//Different (false)
  }
}