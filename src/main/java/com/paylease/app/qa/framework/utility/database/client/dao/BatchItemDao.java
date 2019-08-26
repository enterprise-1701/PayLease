package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.BatchItem;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * BatchItems Data Access Object.
 *
 * @author Jeffrey Walker
 */
public class BatchItemDao implements Dao<BatchItem> {

  private static final String TABLE_NAME = "batch_items";

  private ArrayList<BatchItem> batchItemsList;
  private String tableSQL;

  @Override
  public ArrayList<BatchItem> findById(Connection connection, long procId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE proc_id = '" + procId
          + "' LIMIT " + limit;

      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return batchItemsList;
  }

  public BatchItem findById(Connection connection, int batchItemId) {
    return findById(connection, batchItemId, 1).get(0);
  }

  public ArrayList<BatchItem> findByTransId(Connection connection, long transactionId, int limit) {
    try {
      Statement statement = connection.createStatement();

      String tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE trans_id = '" + transactionId
          + "' LIMIT " + limit;

      Logger.debug("SQL script: " + tableSQL);
      ResultSet resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return batchItemsList;
  }

  @Override
  public boolean create(Connection connection, BatchItem batchItem) {
    return true;
  }

  @Override
  public int update(Connection connection, BatchItem batchItem) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection
          .prepareStatement("UPDATE " + TABLE_NAME
              + " SET file_id=?"
              + "WHERE proc_id=?");

      ps.setString(1, String.valueOf(batchItem.getFileId()));
      ps.setString(2, String.valueOf(batchItem.getProcId()));

      numOfRowsAffected = ps.executeUpdate();

      Logger.debug(ps.toString());

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  public int updateReturnCode(Connection connection, BatchItem batchItem) {
    int numOfRowsAffected = 0;

    try{
      PreparedStatement ps = connection.prepareStatement("UPDATE " + TABLE_NAME
          + " SET return_code=? WHERE proc_id=?");

      ps.setString(1, batchItem.getReturnCode());
      ps.setString(2, String.valueOf(batchItem.getProcId()));

      numOfRowsAffected = ps.executeUpdate();

      Logger.debug(ps.toString());

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, BatchItem batchItem) {
    return true;
  }

  public int removeByTransId(Connection connection, int transId) {
    return delete(connection, "trans_id", transId, "int");
  }


  private void processResultSet(ResultSet resultSet) throws SQLException {
    batchItemsList = new ArrayList<BatchItem>();

    while (resultSet.next()) {
      BatchItem batchItem = new BatchItem();
      batchItem.setTransId(resultSet.getLong("trans_id"));
      batchItem.setFileId(resultSet.getLong("file_id"));
      batchItem.setDepositId(resultSet.getLong("deposit_id"));
      batchItem.setReturnDepositId(resultSet.getLong("return_deposit_id"));
      batchItem.setAmount(resultSet.getDouble("amount"));
      batchItem.setCollectingFee(resultSet.getInt("collecting_fee"));
      batchItem.setProcId(resultSet.getInt("proc_id"));
      batchItem.setAccountPersonName(resultSet.getString("account_person_name"));
      batchItem.setExtRefId(resultSet.getString("external_ref_id"));
      batchItem.setReadyToProcess(resultSet.getInt("ready_to_process"));
      batchItem.setBatchId(resultSet.getInt("batch_id"));
      batchItem.setFromAcctNum(resultSet.getString("from_acc"));
      batchItem.setReturnCode(resultSet.getString("return_code"));
      batchItem.setFromRoutingNum(resultSet.getString("from_rt"));
      batchItem.setTransactionPaymentFieldId(resultSet.getLong("transaction_payment_field_id"));
      batchItem.setReturnFileId(resultSet.getInt("return_file_id"));
      batchItem.setFromRoutingNum(resultSet.getString("from_rt"));
      batchItem.setToRoutingNum(resultSet.getString("to_rt"));
      batchItem.setToBankAcctId(resultSet.getString("to_acc_id"));
      batchItem.setFromBankAcctId(resultSet.getString("from_acc_id"));
      batchItem.setAchProcessor(resultSet.getInt("ach_processor"));
      batchItem.setToAcctNum(resultSet.getString("to_acc"));
      batchItem.setFromAcctNum(resultSet.getString("from_acc"));
      batchItem.setCreditOrDebit(resultSet.getInt("credit_or_debit"));
      batchItem.setCreatedDate(resultSet.getString("created_date"));
      batchItemsList.add(batchItem);
    }
  }

  public int delete(Connection connection, String columnName, Object columnValue,
      String columnType) {
    int rowsDeleted = 0;
    try {
      tableSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + columnName + " = " +columnValue;
      PreparedStatement statement = connection.prepareStatement(tableSQL);
      rowsDeleted = statement.executeUpdate(tableSQL);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rowsDeleted;
  }
}
