package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.ReturnFilesDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnFilesDao {

  private static final String TABLE_NAME = "return_files";

  /**
   * Create a table row with given parameters.
   *
   * @param connection connection
   * @param fileName file name
   * @param fullName full name
   * @param achProcessor ach processor
   * @return number of rows affected
   */
  public int create(Connection connection, String fileName, String fullName, int achProcessor) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (filename, fullname, ach_processor) VALUES (" + "'" + fileName + "'" + ", " + "'"
          + fullName + "'" + ", " + "'" + achProcessor + "'" + ")";

      Logger.debug(sql);

      PreparedStatement statement = connection.prepareStatement(sql);

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  private ArrayList<ReturnFilesDto> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<ReturnFilesDto> resultsArrayList = new ArrayList<ReturnFilesDto>();
    while (resultSet.next()) {
      ReturnFilesDto returnFilesDto = new ReturnFilesDto();
      returnFilesDto.setReturnFileId(resultSet.getInt("return_file_id"));
      returnFilesDto.setFileName(resultSet.getString("filename"));
      returnFilesDto.setFullName(resultSet.getString("fullname"));
      returnFilesDto.setAchProcessor(resultSet.getInt("ach_processor"));
      returnFilesDto.setHasBeenProcessed(resultSet.getInt("has_been_processed"));
      returnFilesDto.setCreatedOn(resultSet.getDate("created_on"));
      returnFilesDto.setProcessedOn(resultSet.getDate("processed_on"));
      returnFilesDto.setMailSent(resultSet.getByte("mail_sent"));
      returnFilesDto.setGpgName(resultSet.getString("gpgname"));
      returnFilesDto.setCreditAmount(resultSet.getBigDecimal("credit_amount"));
      returnFilesDto.setDebitAmount(resultSet.getBigDecimal("debit_amount"));
      returnFilesDto.setChecked(resultSet.getBoolean("checked"));
      returnFilesDto.setCheckedOn(resultSet.getDate("checked_on"));
      returnFilesDto.setHasBeenAudit(resultSet.getBoolean("has_been_audit"));
      returnFilesDto.setIsError(resultSet.getString("is_error"));
      resultsArrayList.add(returnFilesDto);
    }
    return resultsArrayList;
  }
}
