package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.IntegrationLoggingDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IntegrationLoggingDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "integration_logging";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  public static final String METHOD_REVERSAL = "ImportResidentTransactions_Login";
  public static final String METHOD_CLOSE_BATCH = "PostReceiptBatch";
  public static final String METHOD_REVIEW_BATCH = "ReviewReceiptBatch";
  public static final String METHOD_DELETE_BATCH = "CancelReceiptBatch";

  //******************
  //Create Method
  //******************

  public int create(Connection connection, IntegrationLoggingDto integrationLoggingDto) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (id, pm_id, software_integration_id, method, status, deposit_id, trans_id, proc_id, external_batch_id, request_time, response_time, request_raw, response_raw, host) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(0, integrationLoggingDto.getId());
      statement.setInt(1, integrationLoggingDto.getPmId());
      statement.setInt(2, integrationLoggingDto.getSoftwareIntegrationId());
      statement.setString(3, integrationLoggingDto.getMethod());
      statement.setString(4, integrationLoggingDto.getStatus());
      statement.setInt(5, integrationLoggingDto.getDepositId());
      statement.setInt(6, integrationLoggingDto.getTransId());
      statement.setInt(7, integrationLoggingDto.getProcId());
      statement.setString(8, integrationLoggingDto.getExternalBatchId());
      statement.setDate(9, integrationLoggingDto.getRequestTime());
      statement.setDate(10, integrationLoggingDto.getResponseTime());
      statement.setString(11, integrationLoggingDto.getRequestRaw());
      statement.setString(12, integrationLoggingDto.getResponseRaw());
      statement.setString(13, integrationLoggingDto.getHost());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, IntegrationLoggingDto integrationLoggingDto,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET id=? , pm_id=? , software_integration_id=? , method=? , status=? , deposit_id=? , trans_id=? , proc_id=? , external_batch_id=? , request_time=? , response_time=? , request_raw=? , response_raw=? , host=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(0, integrationLoggingDto.getId());
      statement.setInt(1, integrationLoggingDto.getPmId());
      statement.setInt(2, integrationLoggingDto.getSoftwareIntegrationId());
      statement.setString(3, integrationLoggingDto.getMethod());
      statement.setString(4, integrationLoggingDto.getStatus());
      statement.setInt(5, integrationLoggingDto.getDepositId());
      statement.setInt(6, integrationLoggingDto.getTransId());
      statement.setInt(7, integrationLoggingDto.getProcId());
      statement.setString(8, integrationLoggingDto.getExternalBatchId());
      statement.setDate(9, integrationLoggingDto.getRequestTime());
      statement.setDate(10, integrationLoggingDto.getResponseTime());
      statement.setString(11, integrationLoggingDto.getRequestRaw());
      statement.setString(12, integrationLoggingDto.getResponseRaw());
      statement.setString(13, integrationLoggingDto.getHost());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Read Methods
  //******************

  public ArrayList<IntegrationLoggingDto> findById(Connection connection, long id, int limit) {
    return read(connection, "id", id, limit);
  }

  public ArrayList<IntegrationLoggingDto> findByTransId(Connection connection, int transId,
      int limit) {
    return read(connection, "trans_id", transId, limit);
  }

  public ArrayList<IntegrationLoggingDto> findByProcId(Connection connection, int procId,
      int limit) {
    return read(connection, "proc_id", procId, limit);
  }

  public int getCountOfMethod(Connection connection, long transactionId, int limit, String method) {
    int count = 0;
    try {
      Statement statement = connection.createStatement();

      String tableSQL =
          "SELECT COUNT(*) AS rowcount FROM " + TABLE_NAME + " WHERE trans_id = '" + transactionId
              + "' AND method = '"
              + method + "' LIMIT " + limit + " ";

      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);

      resultSet.next();
      count = resultSet.getInt("rowcount");

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return count;
  }

  public ArrayList<IntegrationLoggingDto> findByTransIdAndMethod(Connection connection,
      long transactionId, int limit, String method) {
    ArrayList<IntegrationLoggingDto> resultsArrayList = null;
    try {
      Statement statement = connection.createStatement();

      String tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE trans_id = '" + transactionId + "' AND method = '"
              + method + "' LIMIT " + limit + " ";

      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);

      resultsArrayList = processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return resultsArrayList;
  }

  public ArrayList<IntegrationLoggingDto> findByDepositIdAndMethod(Connection connection,
      long depositId, int limit, String method) {
    ArrayList<IntegrationLoggingDto> resultsArrayList = null;
    try {
      Statement statement = connection.createStatement();

      String tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE deposit_id = '" + depositId + "' AND method = '"
              + method + "' LIMIT " + limit + " ";

      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);

      resultsArrayList = processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return resultsArrayList;
  }

  //******************
  //Helper Methods
  //******************

  public ArrayList<IntegrationLoggingDto> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<IntegrationLoggingDto> resultsArrayList = null;
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

  private ArrayList<IntegrationLoggingDto> processResultSet(ResultSet resultSet)
      throws SQLException {
    ArrayList<IntegrationLoggingDto> resultsArrayList = new ArrayList<IntegrationLoggingDto>();
    while (resultSet.next()) {
      IntegrationLoggingDto integrationLoggingDto = new IntegrationLoggingDto();
      integrationLoggingDto.setId(resultSet.getLong("id"));
      integrationLoggingDto.setPmId(resultSet.getInt("pm_id"));
      integrationLoggingDto.setSoftwareIntegrationId(resultSet.getInt("software_integration_id"));
      integrationLoggingDto.setMethod(resultSet.getString("method"));
      integrationLoggingDto.setStatus(resultSet.getString("status"));
      integrationLoggingDto.setDepositId(resultSet.getInt("deposit_id"));
      integrationLoggingDto.setTransId(resultSet.getInt("trans_id"));
      integrationLoggingDto.setProcId(resultSet.getInt("proc_id"));
      integrationLoggingDto.setExternalBatchId(resultSet.getString("external_batch_id"));
      integrationLoggingDto.setRequestTime(resultSet.getDate("request_time"));
      integrationLoggingDto.setResponseTime(resultSet.getDate("response_time"));
      integrationLoggingDto.setRequestRaw(resultSet.getString("request_raw"));
      integrationLoggingDto.setResponseRaw(resultSet.getString("response_raw"));
      integrationLoggingDto.setHost(resultSet.getString("host"));
      resultsArrayList.add(integrationLoggingDto);
    }
    return resultsArrayList;
  }
}