package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.AmsiTransactions;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AmsiTransactionsDao {

  private static final int LIMIT = 10;
  private static final String TABLE_NAME = "amsi_transactions";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************

  public int create(Connection connection, AmsiTransactions amsiTransactions) {
    int rowsAffected = 0;
    try {
      String sql = "INSERT INTO " + TABLE_NAME
          + " (trans_id, pm_id, evolution_reference, esite_batch_no, esite_bankbook_id, client_journal_no, add_payment_result, add_payment_date, processing_status, attempts, created_on, reviewed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, amsiTransactions.getTransId());
      statement.setInt(1, amsiTransactions.getPmId());
      statement.setInt(2, amsiTransactions.getEvolutionReference());
      statement.setInt(3, amsiTransactions.getEsiteBatchNo());
      statement.setString(4, amsiTransactions.getEsiteBankbookId());
      statement.setInt(5, amsiTransactions.getClientJournalNo());
      statement.setString(6, amsiTransactions.getAddPaymentResult());
      statement.setDate(7, amsiTransactions.getAddPaymentDate());
      statement.setString(8, amsiTransactions.getProcessingStatus());
      statement.setShort(9, amsiTransactions.getAttempts());
      statement.setDate(10, amsiTransactions.getCreatedOn());
      statement.setString(11, amsiTransactions.getReviewed());

      rowsAffected = statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsAffected;
  }

  //******************
  //Update Method
  //******************

  public int update(Connection connection, AmsiTransactions amsiTransactions,
      String filterColumnName) {
    int rowsAffected = 0;
    try {
      String sql = "UPDATE " + TABLE_NAME
          + " SET trans_id=? , pm_id=? , evolution_reference=? , esite_batch_no=? , esite_bankbook_id=? , client_journal_no=? , add_payment_result=? , add_payment_date=? , processing_status=? , attempts=? , created_on=? , reviewed=? WHERE "
          + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0, amsiTransactions.getTransId());
      statement.setInt(1, amsiTransactions.getPmId());
      statement.setInt(2, amsiTransactions.getEvolutionReference());
      statement.setInt(3, amsiTransactions.getEsiteBatchNo());
      statement.setString(4, amsiTransactions.getEsiteBankbookId());
      statement.setInt(5, amsiTransactions.getClientJournalNo());
      statement.setString(6, amsiTransactions.getAddPaymentResult());
      statement.setDate(7, amsiTransactions.getAddPaymentDate());
      statement.setString(8, amsiTransactions.getProcessingStatus());
      statement.setShort(9, amsiTransactions.getAttempts());
      statement.setDate(10, amsiTransactions.getCreatedOn());
      statement.setString(11, amsiTransactions.getReviewed());

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

  public int removeByEvolutionReference(Connection connection, int evolutionReference) {
    return delete(connection, "evolution_reference", evolutionReference, "int");
  }

  public int removeByEsiteBatchNo(Connection connection, int esiteBatchNo) {
    return delete(connection, "esite_batch_no", esiteBatchNo, "int");
  }

  public int removeByEsiteBankbookId(Connection connection, String esiteBankbookId) {
    return delete(connection, "esite_bankbook_id", esiteBankbookId, "String");
  }

  public int removeByClientJournalNo(Connection connection, int clientJournalNo) {
    return delete(connection, "client_journal_no", clientJournalNo, "int");
  }

  public int removeByAddPaymentResult(Connection connection, String addPaymentResult) {
    return delete(connection, "add_payment_result", addPaymentResult, "String");
  }

  public int removeByAddPaymentDate(Connection connection, Date addPaymentDate) {
    return delete(connection, "add_payment_date", addPaymentDate, "Date");
  }

  public int removeByProcessingStatus(Connection connection, String processingStatus) {
    return delete(connection, "processing_status", processingStatus, "String");
  }

  public int removeByAttempts(Connection connection, short attempts) {
    return delete(connection, "attempts", attempts, "short");
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

  public ArrayList<AmsiTransactions> findByTransId(Connection connection, int transId, int limit) {
    return read(connection, "trans_id", transId, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByPmId(Connection connection, int pmId, int limit) {
    return read(connection, "pm_id", pmId, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByEvolutionReference(Connection connection,
      int evolutionReference, int limit) {
    return read(connection, "evolution_reference", evolutionReference, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByEsiteBatchNo(Connection connection, int esiteBatchNo,
      int limit) {
    return read(connection, "esite_batch_no", esiteBatchNo, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByEsiteBankbookId(Connection connection,
      String esiteBankbookId, int limit) {
    return read(connection, "esite_bankbook_id", esiteBankbookId, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByClientJournalNo(Connection connection,
      int clientJournalNo, int limit) {
    return read(connection, "client_journal_no", clientJournalNo, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByAddPaymentResult(Connection connection,
      String addPaymentResult, int limit) {
    return read(connection, "add_payment_result", addPaymentResult, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByAddPaymentDate(Connection connection,
      Date addPaymentDate, int limit) {
    return read(connection, "add_payment_date", addPaymentDate, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByProcessingStatus(Connection connection,
      String processingStatus, int limit) {
    return read(connection, "processing_status", processingStatus, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByAttempts(Connection connection, short attempts,
      int limit) {
    return read(connection, "attempts", attempts, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByCreatedOn(Connection connection, Date createdOn,
      int limit) {
    return read(connection, "created_on", createdOn, LIMIT);
  }

  public ArrayList<AmsiTransactions> findByReviewed(Connection connection, String reviewed,
      int limit) {
    return read(connection, "reviewed", reviewed, LIMIT);
  }

  //******************
  //Helper Methods
  //******************

  public ArrayList<AmsiTransactions> read(Connection connection, String columnName,
      Object columnValue, int limit) {
    ArrayList<AmsiTransactions> resultsArrayList = null;
    try {
      statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE " + columnName + " = '" + columnValue + "' LIMIT "
              + limit;
      Logger.debug("SQL script: " + tableSQL);
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

  private ArrayList<AmsiTransactions> processResultSet(ResultSet resultSet) throws SQLException {
    ArrayList<AmsiTransactions> resultsArrayList = new ArrayList<AmsiTransactions>();
    while (resultSet.next()) {
      AmsiTransactions amsiTransactionsDto = new AmsiTransactions();
      amsiTransactionsDto.setTransId(resultSet.getInt("trans_id"));
      amsiTransactionsDto.setPmId(resultSet.getInt("pm_id"));
      amsiTransactionsDto.setEvolutionReference(resultSet.getInt("evolution_reference"));
      amsiTransactionsDto.setEsiteBatchNo(resultSet.getInt("esite_batch_no"));
      amsiTransactionsDto.setEsiteBankbookId(resultSet.getString("esite_bankbook_id"));
      amsiTransactionsDto.setClientJournalNo(resultSet.getInt("client_journal_no"));
      amsiTransactionsDto.setAddPaymentResult(resultSet.getString("add_payment_result"));
      amsiTransactionsDto.setAddPaymentDate(resultSet.getDate("add_payment_date"));
      amsiTransactionsDto.setProcessingStatus(resultSet.getString("processing_status"));
      amsiTransactionsDto.setAttempts(resultSet.getShort("attempts"));
      amsiTransactionsDto.setCreatedOn(resultSet.getDate("created_on"));
      amsiTransactionsDto.setReviewed(resultSet.getString("reviewed"));
      resultsArrayList.add(amsiTransactionsDto);
    }
    return resultsArrayList;
  }
}