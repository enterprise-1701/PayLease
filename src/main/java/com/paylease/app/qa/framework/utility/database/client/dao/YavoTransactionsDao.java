package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.YavoTransactionsDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class YavoTransactionsDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "yavo_transactions";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************

  public int create(Connection connection, YavoTransactionsDto yavoTransactionsDto) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (trans_id, pm_id, batch_id, request_msg, response_msg, status, processing_status, attempts, timestamp, created_on, reviewed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, yavoTransactionsDto.getTransId());
      statement.setInt(1, yavoTransactionsDto.getPmId());
      statement.setInt(2, yavoTransactionsDto.getBatchId());
      statement.setString(3, yavoTransactionsDto.getRequestMsg());
      statement.setString(4, yavoTransactionsDto.getResponseMsg());
      statement.setString(5, yavoTransactionsDto.getStatus());
      statement.setString(6, yavoTransactionsDto.getProcessingStatus());
      statement.setShort(7, yavoTransactionsDto.getAttempts());
      statement.setDate(8, yavoTransactionsDto.getTimestamp());
      statement.setDate(9, yavoTransactionsDto.getCreatedOn());
      statement.setString(10, yavoTransactionsDto.getReviewed());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, YavoTransactionsDto yavoTransactionsDto,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET trans_id=? , pm_id=? , batch_id=? , request_msg=? , response_msg=? , status=? , processing_status=? , attempts=? , timestamp=? , created_on=? , reviewed=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, yavoTransactionsDto.getTransId());
      statement.setInt(1, yavoTransactionsDto.getPmId());
      statement.setInt(2, yavoTransactionsDto.getBatchId());
      statement.setString(3, yavoTransactionsDto.getRequestMsg());
      statement.setString(4, yavoTransactionsDto.getResponseMsg());
      statement.setString(5, yavoTransactionsDto.getStatus());
      statement.setString(6, yavoTransactionsDto.getProcessingStatus());
      statement.setShort(7, yavoTransactionsDto.getAttempts());
      statement.setDate(8, yavoTransactionsDto.getTimestamp());
      statement.setDate(9, yavoTransactionsDto.getCreatedOn());
      statement.setString(10, yavoTransactionsDto.getReviewed());

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

  public int removeByBatchId(Connection connection, int batchId) {
    return delete(connection, "batch_id", batchId, "int");
  }

  public int removeByRequestMsg(Connection connection, String requestMsg) {
    return delete(connection, "request_msg", requestMsg, "String");
  }

  public int removeByResponseMsg(Connection connection, String responseMsg) {
    return delete(connection, "response_msg", responseMsg, "String");
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

  public ArrayList<YavoTransactionsDto> findByTransId(Connection connection, int transId,
      int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByBatchId(Connection connection, int batchId,
      int limit) {
    return read(connection, "batch_id", batchId, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByRequestMsg(Connection connection, String requestMsg,
      int limit) {
    return read(connection, "request_msg", requestMsg, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByResponseMsg(Connection connection, String responseMsg,
      int limit) {
    return read(connection, "response_msg", responseMsg, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByStatus(Connection connection, String status,
      int limit) {
    return read(connection, "status", status, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByProcessingStatus(Connection connection,
      String processingStatus, int limit) {
    return read(connection, "processing_status", processingStatus, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByAttempts(Connection connection, short attempts,
      int limit) {
    return read(connection, "attempts", attempts, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByTimestamp(Connection connection, Date timestamp,
      int limit) {
    return read(connection, "timestamp", timestamp, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByCreatedOn(Connection connection, Date createdOn,
      int limit) {
    return read(connection, "created_on", createdOn, LIMIT);
  }

  public ArrayList<YavoTransactionsDto> findByReviewed(Connection connection, String reviewed,
      int limit) {
    return read(connection, "reviewed", reviewed, LIMIT);
  }

  //******************
  //Helper Methods
  //******************

  public ArrayList<YavoTransactionsDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<YavoTransactionsDto> resultsArrayList = null;
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

  private ArrayList<YavoTransactionsDto> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<YavoTransactionsDto> resultsArrayList = new ArrayList<YavoTransactionsDto>();
    while (resultSet.next()) {
      YavoTransactionsDto yavoTransactionsDto = new YavoTransactionsDto();
      yavoTransactionsDto.setTransId(resultSet.getInt("trans_id"));
      yavoTransactionsDto.setPmId(resultSet.getInt("pm_id"));
      yavoTransactionsDto.setBatchId(resultSet.getInt("batch_id"));
      yavoTransactionsDto.setRequestMsg(resultSet.getString("request_msg"));
      yavoTransactionsDto.setResponseMsg(resultSet.getString("response_msg"));
      yavoTransactionsDto.setStatus(resultSet.getString("status"));
      yavoTransactionsDto.setProcessingStatus(resultSet.getString("processing_status"));
      yavoTransactionsDto.setAttempts(resultSet.getShort("attempts"));
      yavoTransactionsDto.setTimestamp(resultSet.getDate("timestamp"));
      yavoTransactionsDto.setCreatedOn(resultSet.getDate("created_on"));
      yavoTransactionsDto.setReviewed(resultSet.getString("reviewed"));
      resultsArrayList.add(yavoTransactionsDto);
    }
    return resultsArrayList;
  }
}