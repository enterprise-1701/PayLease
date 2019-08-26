package com.paylease.app.qa.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

  /**
   * Open xlsx file and put data into array.
   *
   * @param fileName File path/name
   * @param sheetName Name of sheet in xlsx file
   * @return an array
   */
  public static String[][] getExcelData(String fileName, String sheetName) {
    return getExcelData(fileName, sheetName, 0);
  }

  /**
   * Open xlsx file and put data into array.
   *
   * @param fileName File path/name
   * @param sheetName Name of sheet in xlsx file
   * @param rowWithMaxCols An integer of the row with the largest number of columns in the sheet.
   * @return an array
   */
  public static String[][] getExcelData(String fileName, String sheetName, int rowWithMaxCols) {
    String[][] arrayExcelData = null;
    try {
      FileInputStream fs = new FileInputStream(fileName);
      Workbook wb = new XSSFWorkbook(fs);
      Sheet sh = wb.getSheet(sheetName);
      int totalNoOfCols = sh.getRow(rowWithMaxCols).getPhysicalNumberOfCells();
      int totalNoOfRows = sh.getPhysicalNumberOfRows();
      arrayExcelData = new String[totalNoOfRows][totalNoOfCols];
      for (int i = 0; i < totalNoOfRows; i++) {
        for (int j = 0; j < totalNoOfCols; j++) {
          CellType cellType;
          try {
            cellType = sh.getRow(i).getCell(j).getCellTypeEnum();
          } catch (Exception exception) {
            cellType = CellType.BLANK;
          }
          switch (cellType) {
            case STRING:
              arrayExcelData[i][j] = sh.getRow(i).getCell(j).getStringCellValue();
              break;
            case NUMERIC:
              arrayExcelData[i][j] = String.valueOf(sh.getRow(i).getCell(j).getNumericCellValue());
              break;
            case BLANK:
            case ERROR:
            case _NONE:
              arrayExcelData[i][j] = "";
              break;
          }
        }
      }
    } catch (FileNotFoundException e) {
      Logger.trace(e.toString());
    } catch (IOException e) {
      Logger.trace(e.toString());
    }
    return arrayExcelData;
  }

  /**
   * Return column index that matches columnName.
   *
   * @return int of column index
   */
  public static int columnNum(String[] excelReport, String columnName) {
    for (int i = 0; i < excelReport.length; i++) {
      if (excelReport[i].equals(columnName)) {
        return i;
      }
    }
    return -1;
  }
}
