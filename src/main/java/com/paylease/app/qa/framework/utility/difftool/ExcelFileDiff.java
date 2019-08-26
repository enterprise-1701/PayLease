package com.paylease.app.qa.framework.utility.difftool;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

import com.paylease.app.qa.framework.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExcelFileDiff {

  /**
   * Check if two excel files are equal.
   */
  public boolean isEqual(String filePath1, String filePath2) {
    XSSFWorkbook workbook1;
    XSSFWorkbook workbook2;

    try {
      //Create input excel files
      workbook1 = createWorkbook(new File(filePath1));
      workbook2 = createWorkbook(new File(filePath2));

      //The number of sheets in each workbook is different
      if (workbook1.getNumberOfSheets() != workbook2.getNumberOfSheets()) {
        closeResources(workbook1, workbook2);
        return false;
      } else {
        for (int i = 0; i < workbook1.getNumberOfSheets(); i++) {
          XSSFSheet sheetFromWB1 = workbook1.getSheetAt(i);
          XSSFSheet sheetFromWB2 = workbook2.getSheetAt(i);

          //If the sheets' names are NOT the same then return false, otherwise keep going
          if (!sheetFromWB1.getSheetName().equals(sheetFromWB2.getSheetName())) {
            closeResources(workbook1, workbook2);
            return false;
          } else if (!compareTwoSheets(sheetFromWB1, sheetFromWB2)) {
            //If the sheets' content are NOT the same then return false, otherwise keep going
            closeResources(workbook1, workbook2);
            return false;
          }
        }
      }
      closeResources(workbook1, workbook2);
    } catch (Exception e) {
      Logger.trace(e.toString());
    }

    return true;
  }

  /**
   * Close the opened workbooks.
   */
  private void closeResources(XSSFWorkbook workbook1, XSSFWorkbook workbook2) throws IOException {
    workbook1.close();
    workbook2.close();
  }

  /**
   * Compares two excel sheets.
   */
  private boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2) {
    int firstRow1 = sheet1.getFirstRowNum();
    int lastRow1 = sheet1.getLastRowNum();
    boolean equalSheets = true;
    for (int i = firstRow1; i <= lastRow1; i++) {
      Logger.info("\n\nComparing Row " + i);

      XSSFRow row1 = sheet1.getRow(i);
      XSSFRow row2 = sheet2.getRow(i);

      if (!compareTwoRows(row1, row2)) {
        equalSheets = false;
        Logger.info("Row " + i + " - Not Equal");
        break;
      }
    }

    return equalSheets;
  }

  /**
   * Compares two excel rows.
   */
  private static boolean compareTwoRows(XSSFRow row1, XSSFRow row2) {
    if ((row1 == null) && (row2 == null)) {
      return true;
    } else if ((row1 == null) || (row2 == null)) {
      return false;
    }

    int firstCell1 = row1.getFirstCellNum();
    int lastCell1 = row1.getLastCellNum();
    boolean equalRows = true;

    // Compare all cells in a row
    for (int i = firstCell1; i <= lastCell1; i++) {
      XSSFCell cell1 = row1.getCell(i);
      XSSFCell cell2 = row2.getCell(i);
      if (!compareTwoCells(cell1, cell2)) {
        equalRows = false;
        Logger.info("Cell " + i + " - Not Equal");
        break;
      }
    }
    return equalRows;
  }

  /**
   * Compares two excel cells.
   */
  private static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
    if ((cell1 == null) && (cell2 == null)) {
      return true;
    } else if ((cell1 == null) || (cell2 == null)) {
      return false;
    }

    boolean equalCells = false;
    CellType type1 = cell1.getCellTypeEnum();
    CellType type2 = cell2.getCellTypeEnum();
    if (type1 == type2) {
      if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
        // Compare cells based on its type
        switch (cell1.getCellTypeEnum()) {
          case FORMULA:
            if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
              equalCells = true;
            }
            break;
          case NUMERIC:
            if (cell1.getNumericCellValue() == cell2
                .getNumericCellValue()) {
              equalCells = true;
            }
            break;
          case STRING:
            if (cell1.getStringCellValue().equals(cell2
                .getStringCellValue())) {
              equalCells = true;
            }
            break;
          case BLANK:
            if (cell2.getCellTypeEnum() == BLANK) {
              equalCells = true;
            }
            break;
          case BOOLEAN:
            if (cell1.getBooleanCellValue() == cell2
                .getBooleanCellValue()) {
              equalCells = true;
            }
            break;
          case ERROR:
            if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
              equalCells = true;
            }
            break;
          default:
            if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
              equalCells = true;
            }
            break;
        }
      } else {
        return false;
      }
    } else {
      return false;
    }

    return equalCells;
  }

  /**
   * Create workbook from an XML file.
   */
  private XSSFWorkbook createWorkbookFromXml(File file) {
    XSSFWorkbook wb = new XSSFWorkbook();
    try {
      Logger.info("Converting xml to xls...");
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(file);

      XSSFSheet spreadSheet = wb.createSheet("spreadSheet");

      NodeList rowList = document.getElementsByTagName("Row");

      for (int i = 0; i < rowList.getLength(); i++) {
        XSSFRow row = spreadSheet.createRow(i);
        Element rowElement = (Element) rowList.item(i);
        NodeList cellList = rowElement.getElementsByTagName("Data");

        for (int j = 0; j < cellList.getLength(); j++) {
          XSSFCell cell = row.createCell((short) j);
          cell.setCellValue(cellList.item(j).getTextContent());
        }
      }
    } catch (IOException e) {
      Logger.trace("IOException " + e.getMessage());
    } catch (ParserConfigurationException e) {
      Logger.trace("ParserConfigurationException " + e.getMessage());
    } catch (SAXException e) {
      Logger.trace("SAXException " + e.getMessage());
    }
    return wb;
  }

  /**
   * Create Workbook instance holding reference to .xlsx file. If raw file is xml, parse xml first.
   */
  private XSSFWorkbook createWorkbook(File file) {
    XSSFWorkbook workBook = new XSSFWorkbook();
    try {
      InputStream is1 = FileMagic.prepareToCheckMagic(new FileInputStream(file));

      if (FileMagic.valueOf(is1) == FileMagic.XML) {
        workBook = createWorkbookFromXml(file);
      } else {
        FileInputStream excelFile = new FileInputStream(file);
        workBook = new XSSFWorkbook(excelFile);
        excelFile.close();
      }
    } catch (FileNotFoundException e) {
      Logger.trace("FileNotFoundException: " + e.toString());
    } catch (IOException e) {
      Logger.trace("IOException: " + e.toString());
    }
    return workBook;
  }
}