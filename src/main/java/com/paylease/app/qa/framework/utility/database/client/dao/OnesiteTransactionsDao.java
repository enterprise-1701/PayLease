package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.OnesiteTransactionsDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OnesiteTransactionsDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "onesite_transactions";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************

  public int create(Connection connection, OnesiteTransactionsDto onesiteTransactionsDto) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (trans_id, pm_id, external_batch_id, external_trans_id, request, response, status, processing_status, attempts, timestamp, created_on, reviewed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, onesiteTransactionsDto.getTransId());
      statement.setInt(1, onesiteTransactionsDto.getPmId());
      statement.setString(2, onesiteTransactionsDto.getExternalBatchId());
      statement.setString(3, onesiteTransactionsDto.getExternalTransId());
      statement.setString(4, onesiteTransactionsDto.getRequest());
      statement.setString(5, onesiteTransactionsDto.getResponse());
      statement.setString(6, onesiteTransactionsDto.getStatus());
      statement.setString(7, onesiteTransactionsDto.getProcessingStatus());
      statement.setShort(8, onesiteTransactionsDto.getAttempts());
      statement.setDate(9, onesiteTransactionsDto.getTimestamp());
      statement.setDate(10, onesiteTransactionsDto.getCreatedOn());
      statement.setString(11, onesiteTransactionsDto.getReviewed());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, OnesiteTransactionsDto onesiteTransactionsDto,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET trans_id=? , pm_id=? , external_batch_id=? , external_trans_id=? , request=? , response=? , status=? , processing_status=? , attempts=? , timestamp=? , created_on=? , reviewed=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, onesiteTransactionsDto.getTransId());
      statement.setInt(1, onesiteTransactionsDto.getPmId());
      statement.setString(2, onesiteTransactionsDto.getExternalBatchId());
      statement.setString(3, onesiteTransactionsDto.getExternalTransId());
      statement.setString(4, onesiteTransactionsDto.getRequest());
      statement.setString(5, onesiteTransactionsDto.getResponse());
      statement.setString(6, onesiteTransactionsDto.getStatus());
      statement.setString(7, onesiteTransactionsDto.getProcessingStatus());
      statement.setShort(8, onesiteTransactionsDto.getAttempts());
      statement.setDate(9, onesiteTransactionsDto.getTimestamp());
      statement.setDate(10, onesiteTransactionsDto.getCreatedOn());
      statement.setString(11, onesiteTransactionsDto.getReviewed());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Delete Methods
  //******************

  public int removeByTransId(Connection connection, int transId) {
    return delete(connection, "trans_id", transId, "int");
  }

  public int removeByPmId(Connection connection, int pmId) {
    return delete(connection, "pm_id", pmId, "int");
  }

  public int removeByExternalBatchId(Connection connection, String externalBatchId) {
    return delete(connection, "external_batch_id", externalBatchId, "String");
  }

  public int removeByExternalTransId(Connection connection, String externalTransId) {
    return delete(connection, "external_trans_id", externalTransId, "String");
  }

  public int removeByRequest(Connection connection, String request) {
    return delete(connection, "request", request, "String");
  }

  public int removeByResponse(Connection connection, String response) {
    return delete(connection, "response", response, "String");
  }

  public int removeByStatus(Connection connection, String status) {
    return delete(connection, "status", status, "String");
  }

  public int removeByProcessingStatus(Connection connection, String processingStatus) {
    return delete(connection, "processing_status", processingStatus, "String");
  }

  public int removeByAttempts(Connection connection, short attempts) {
    return delete(connection, "attempts", attempts, "short");
  }

  public int removeByTimestamp(Connection connection, Date timestamp) {
    return delete(connection, "timestamp", timestamp, "Date");
  }

  public int removeByCreatedOn(Connection connection, Date createdOn) {
    return delete(connection, "created_on", createdOn, "Date");
  }

  public int removeByReviewed(Connection connection, String reviewed) {
    return delete(connection, "reviewed", reviewed, "String");
  }

  //******************
  //Read Methods
  //******************

  public ArrayList<OnesiteTransactionsDto> findByTransId(Connection connection, int transId,
      int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByExternalBatchId(Connection connection,
      String externalBatchId, int limit) {
    return read(connection, "external_batch_id", externalBatchId, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByExternalTransId(Connection connection,
      String externalTransId, int limit) {
    return read(connection, "external_trans_id", externalTransId, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByRequest(Connection connection, String request,
      int limit) {
    return read(connection, "request", request, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByResponse(Connection connection, String response,
      int limit) {
    return read(connection, "response", response, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByStatus(Connection connection, String status,
      int limit) {
    return read(connection, "status", status, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByProcessingStatus(Connection connection,
      String processingStatus, int limit) {
    return read(connection, "processing_status", processingStatus, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByAttempts(Connection connection, short attempts,
      int limit) {
    return read(connection, "attempts", attempts, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByTimestamp(Connection connection, Date timestamp,
      int limit) {
    return read(connection, "timestamp", timestamp, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByCreatedOn(Connection connection, Date createdOn,
      int limit) {
    return read(connection, "created_on", createdOn, LIMIT);
  }

  public ArrayList<OnesiteTransactionsDto> findByReviewed(Connection connection, String reviewed,
      int limit) {
    return read(connection, "reviewed", reviewed, LIMIT);
  }
  //******************
  //Helper Methods
  //******************


  public ArrayList<OnesiteTransactionsDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<OnesiteTransactionsDto> resultsArrayList = null;
    try {
      statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE " + columnName + " = '" + columnValue + "' LIMIT "
              + limit;
      resultSet = statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resultsArrayList;
  }

  public int delete(Connection connection, String columnName, Object columnValue,
      String columnType) {
    int rowsDeleted = 0;
    try {
      tableSQL = "DELETE * FROM " + TABLE_NAME + " WHERE " + columnName + " = ?";
      PreparedStatement statement = connection.prepareStatement(tableSQL);
      if (columnType.equals("String")) {
        statement.setString(1, (String) columnValue);
      } else if (columnType.equals("boolean")) {
        statement.setBoolean(1, (boolean) columnValue);
      } else if (columnType.equals("Date")) {
        statement.setDate(1, (Date) columnValue);
      } else if (columnType.equals("double")) {
        statement.setDouble(1, (double) columnValue);
      } else if (columnType.equals("float")) {
        statement.setFloat(1, (float) columnValue);
      } else if (columnType.equals("long")) {
        statement.setLong(1, (long) columnValue);
      } else if (columnType.equals("int")) {
        statement.setInt(1, (int) columnValue);
      } else if (columnType.equals("short")) {
        statement.setShort(1, (short) columnValue);
      } else if (columnType.equals("byte")) {
        statement.setByte(1, (byte) columnValue);
      } else if (columnType.equals("BigDecimal")) {
        statement.setBigDecimal(1, (BigDecimal) columnValue);
      }
      rowsDeleted = statement.executeUpdate(tableSQL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsDeleted;
  }

  private ArrayList<OnesiteTransactionsDto> processResultSet(ResultSet resultSet)
      throws SQLException {
    ArrayList<OnesiteTransactionsDto> resultsArrayList = new ArrayList<OnesiteTransactionsDto>();
    while (resultSet.next()) {
      OnesiteTransactionsDto onesiteTransactionsDto = new OnesiteTransactionsDto();
      onesiteTransactionsDto.setTransId(resultSet.getInt("trans_id"));
      onesiteTransactionsDto.setPmId(resultSet.getInt("pm_id"));
      onesiteTransactionsDto.setExternalBatchId(resultSet.getString("external_batch_id"));
      onesiteTransactionsDto.setExternalTransId(resultSet.getString("external_trans_id"));
      onesiteTransactionsDto.setRequest(resultSet.getString("request"));
      onesiteTransactionsDto.setResponse(resultSet.getString("response"));
      onesiteTransactionsDto.setStatus(resultSet.getString("status"));
      onesiteTransactionsDto.setProcessingStatus(resultSet.getString("processing_status"));
      onesiteTransactionsDto.setAttempts(resultSet.getShort("attempts"));
      onesiteTransactionsDto.setTimestamp(resultSet.getDate("timestamp"));
      onesiteTransactionsDto.setCreatedOn(resultSet.getDate("created_on"));
      onesiteTransactionsDto.setReviewed(resultSet.getString("reviewed"));
      resultsArrayList.add(onesiteTransactionsDto);
    }
    return resultsArrayList;
  }
}