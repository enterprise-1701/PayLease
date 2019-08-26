package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.MriTransactionsDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MriTransactionsDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "mri_transactions";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************

  public int create(Connection connection, MriTransactionsDto mriTransactionsDto) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (trans_id, external_trans_id, pm_id, batch_id, request_msg, response_msg, tracking_value, status, processing_status, attempts, is_on_hold, is_on_hold_processed, timestamp, created_on, reviewed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, mriTransactionsDto.getTransId());
      statement.setString(1, mriTransactionsDto.getExternalTransId());
      statement.setInt(2, mriTransactionsDto.getPmId());
      statement.setString(3, mriTransactionsDto.getBatchId());
      statement.setString(4, mriTransactionsDto.getRequestMsg());
      statement.setString(5, mriTransactionsDto.getResponseMsg());
      statement.setString(6, mriTransactionsDto.getTrackingValue());
      statement.setString(7, mriTransactionsDto.getStatus());
      statement.setString(8, mriTransactionsDto.getProcessingStatus());
      statement.setShort(9, mriTransactionsDto.getAttempts());
      statement.setString(10, mriTransactionsDto.getIsOnHold());
      statement.setString(11, mriTransactionsDto.getIsOnHoldProcessed());
      statement.setDate(12, mriTransactionsDto.getTimestamp());
      statement.setDate(13, mriTransactionsDto.getCreatedOn());
      statement.setString(14, mriTransactionsDto.getReviewed());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, MriTransactionsDto mriTransactionsDto,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET trans_id=? , external_trans_id=? , pm_id=? , batch_id=? , request_msg=? , response_msg=? , tracking_value=? , status=? , processing_status=? , attempts=? , is_on_hold=? , is_on_hold_processed=? , timestamp=? , created_on=? , reviewed=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, mriTransactionsDto.getTransId());
      statement.setString(1, mriTransactionsDto.getExternalTransId());
      statement.setInt(2, mriTransactionsDto.getPmId());
      statement.setString(3, mriTransactionsDto.getBatchId());
      statement.setString(4, mriTransactionsDto.getRequestMsg());
      statement.setString(5, mriTransactionsDto.getResponseMsg());
      statement.setString(6, mriTransactionsDto.getTrackingValue());
      statement.setString(7, mriTransactionsDto.getStatus());
      statement.setString(8, mriTransactionsDto.getProcessingStatus());
      statement.setShort(9, mriTransactionsDto.getAttempts());
      statement.setString(10, mriTransactionsDto.getIsOnHold());
      statement.setString(11, mriTransactionsDto.getIsOnHoldProcessed());
      statement.setDate(12, mriTransactionsDto.getTimestamp());
      statement.setDate(13, mriTransactionsDto.getCreatedOn());
      statement.setString(14, mriTransactionsDto.getReviewed());

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

  public int removeByExternalTransId(Connection connection, String externalTransId) {
    return delete(connection, "external_trans_id", externalTransId, "String");
  }

  public int removeByPmId(Connection connection, int pmId) {
    return delete(connection, "pm_id", pmId, "int");
  }

  public int removeByBatchId(Connection connection, String batchId) {
    return delete(connection, "batch_id", batchId, "String");
  }

  public int removeByRequestMsg(Connection connection, String requestMsg) {
    return delete(connection, "request_msg", requestMsg, "String");
  }

  public int removeByResponseMsg(Connection connection, String responseMsg) {
    return delete(connection, "response_msg", responseMsg, "String");
  }

  public int removeByTrackingValue(Connection connection, String trackingValue) {
    return delete(connection, "tracking_value", trackingValue, "String");
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

  public int removeByIsOnHold(Connection connection, String isOnHold) {
    return delete(connection, "is_on_hold", isOnHold, "String");
  }

  public int removeByIsOnHoldProcessed(Connection connection, String isOnHoldProcessed) {
    return delete(connection, "is_on_hold_processed", isOnHoldProcessed, "String");
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

  public ArrayList<MriTransactionsDto> findByTransId(Connection connection, int transId,
      int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByExternalTransId(Connection connection,
      String externalTransId, int limit) {
    return read(connection, "external_trans_id", externalTransId, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByBatchId(Connection connection, String batchId,
      int limit) {
    return read(connection, "batch_id", batchId, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByRequestMsg(Connection connection, String requestMsg,
      int limit) {
    return read(connection, "request_msg", requestMsg, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByResponseMsg(Connection connection, String responseMsg,
      int limit) {
    return read(connection, "response_msg", responseMsg, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByTrackingValue(Connection connection,
      String trackingValue, int limit) {
    return read(connection, "tracking_value", trackingValue, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByStatus(Connection connection, String status,
      int limit) {
    return read(connection, "status", status, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByProcessingStatus(Connection connection,
      String processingStatus, int limit) {
    return read(connection, "processing_status", processingStatus, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByAttempts(Connection connection, short attempts,
      int limit) {
    return read(connection, "attempts", attempts, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByIsOnHold(Connection connection, String isOnHold,
      int limit) {
    return read(connection, "is_on_hold", isOnHold, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByIsOnHoldProcessed(Connection connection,
      String isOnHoldProcessed, int limit) {
    return read(connection, "is_on_hold_processed", isOnHoldProcessed, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByTimestamp(Connection connection, Date timestamp,
      int limit) {
    return read(connection, "timestamp", timestamp, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByCreatedOn(Connection connection, Date createdOn,
      int limit) {
    return read(connection, "created_on", createdOn, LIMIT);
  }

  public ArrayList<MriTransactionsDto> findByReviewed(Connection connection, String reviewed,
      int limit) {
    return read(connection, "reviewed", reviewed, LIMIT);
  }

  //******************
  //Helper Methods
  //******************

  public ArrayList<MriTransactionsDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<MriTransactionsDto> resultsArrayList = null;
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

  private ArrayList<MriTransactionsDto> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<MriTransactionsDto> resultsArrayList = new ArrayList<MriTransactionsDto>();
    while (resultSet.next()) {
      MriTransactionsDto mriTransactionsDto = new MriTransactionsDto();
      mriTransactionsDto.setTransId(resultSet.getInt("trans_id"));
      mriTransactionsDto.setExternalTransId(resultSet.getString("external_trans_id"));
      mriTransactionsDto.setPmId(resultSet.getInt("pm_id"));
      mriTransactionsDto.setBatchId(resultSet.getString("batch_id"));
      mriTransactionsDto.setRequestMsg(resultSet.getString("request_msg"));
      mriTransactionsDto.setResponseMsg(resultSet.getString("response_msg"));
      mriTransactionsDto.setTrackingValue(resultSet.getString("tracking_value"));
      mriTransactionsDto.setStatus(resultSet.getString("status"));
      mriTransactionsDto.setProcessingStatus(resultSet.getString("processing_status"));
      mriTransactionsDto.setAttempts(resultSet.getShort("attempts"));
      mriTransactionsDto.setIsOnHold(resultSet.getString("is_on_hold"));
      mriTransactionsDto.setIsOnHoldProcessed(resultSet.getString("is_on_hold_processed"));
      mriTransactionsDto.setTimestamp(resultSet.getDate("timestamp"));
      mriTransactionsDto.setCreatedOn(resultSet.getDate("created_on"));
      mriTransactionsDto.setReviewed(resultSet.getString("reviewed"));
      resultsArrayList.add(mriTransactionsDto);
    }
    return resultsArrayList;
  }
}