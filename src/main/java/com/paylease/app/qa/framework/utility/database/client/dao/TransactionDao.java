package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Transaction Data Access Object.
 *
 * @author Jeffrey Walker
 */
public class TransactionDao implements Dao<Transaction> {

  private static final String TABLE_NAME = "transactions";

  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;
  private ArrayList<Transaction> transactionsList;

  @Override
  public ArrayList<Transaction> findById(Connection connection, long transactionId, int limit) {
    try {
      PreparedStatement ps = connection
          .prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE trans_id = ? LIMIT ?");
      ps.setLong(1, transactionId);
      ps.setInt(2, limit);

      Logger.debug("SQL script: " + ps.toString());
      resultSet = ps.executeQuery();

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return transactionsList;
  }

  /**
   * Find using parent transaction id.
   *
   * @param connection connection
   * @param parentId parentId
   * @param limit limit
   * @return result set
   */
  public ArrayList<Transaction> findByParentId(Connection connection, long parentId, int limit) {
    try {
      statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE parent_trans_id = '" + parentId + "' LIMIT "
              + limit;

      Logger.debug("SQL script: " + tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return transactionsList;
  }

  public ArrayList<Transaction> findByAutopayId(Connection connection, long autopayId, int limit) {
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE autopay_id = '" + autopayId + "' LIMIT "
          + limit;

      Logger.debug("SQL script: " + tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return transactionsList;

  }

  public ArrayList<Transaction> findByPaymentMadeBy(Connection connection, long autopayId, int limit) {
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE payment_made_by = '" + "-"+autopayId + "' LIMIT "
          + limit;

      Logger.debug("SQL script: " + tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return transactionsList;

  }

  /**
   * Find latest transaction from Resident to PM.
   *
   * @param connection connection
   * @param residentId residentId
   * @param pmId pmId
   * @return result set
   */
  public Transaction findLatestByResidentAndPm(
      Connection connection, int residentId, int pmId
  ) {
    try {
      statement = connection.createStatement();

      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE from_id = '" + residentId + "' AND to_id = '"
              + pmId + "' ORDER BY trans_id DESC LIMIT 1";

      Logger.debug("SQL script: " + tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      ArrayList<Transaction> list = processResultSet(resultSet);

      return list.get(0);
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  /**
   * Find the count of refunds for the parent transaction.
   *
   * @param connection connection
   * @return result set
   */
  public int findCountOfRefundsandReversals(
      Connection connection, long parentId, int typeOfTransaction
  ) {
    int count = 0;
    ArrayList<Transaction> list = null;
    try {
      statement = connection.createStatement();

      tableSQL =
          "SELECT count(*) AS rowcount FROM " + TABLE_NAME + " WHERE parent_trans_id = '" + parentId
              + "' AND type_of_transaction = '" + typeOfTransaction + "'";

      Logger.debug("SQL script: " + tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      resultSet.next();
      count = resultSet.getInt("rowcount");

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return count;
  }

  @Override
  public boolean create(Connection connection, Transaction t) {
    return true;
  }

  @Override
  public int update(Connection connection, Transaction transaction) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection
          .prepareStatement("UPDATE " + TABLE_NAME
              + " SET transaction_date=?, total_amount=?, description=?, unit_amount=? , auto_pay_start_date=?, auto_pay_debit_day=?, status=? "
              + "WHERE trans_id=?");

      ps.setString(1, transaction.getTransactionDate());
      ps.setString(2, transaction.getTotalAmount());
      ps.setString(3, transaction.getDescription());
      ps.setString(4, transaction.getUnitAmount());
      ps.setString(5, transaction.getAutoPayStartDate());
      ps.setString(6, transaction.getAutoPayDebitDay());
      ps.setString(7, transaction.getStatus());
      ps.setLong(8, transaction.getTransactionId());

      numOfRowsAffected = ps.executeUpdate();

      Logger.debug(ps.toString());

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }
    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, Transaction t) {
    return true;
  }

  private ArrayList<Transaction> processResultSet(ResultSet resultSet) throws SQLException {
    transactionsList = new ArrayList<>();

    while (resultSet.next()) {
      Transaction transaction = new Transaction();
      transaction.setTransactionId(resultSet.getLong("trans_id"));
      transaction.setTransactionDate(resultSet.getString("transaction_date"));
      transaction.setStatus(resultSet.getString("status"));
      transaction.setTypeOfTransaction(resultSet.getInt("type_of_transaction"));
      transaction.setParentTransactionId(resultSet.getString("parent_trans_id"));
      transaction.setToId(resultSet.getString("to_id"));
      transaction
          .setTypeOfTransaction(Integer.parseInt(resultSet.getString("type_of_transaction")));
      transaction.setFromId(resultSet.getString("from_id"));
      transaction.setPaymentTypeId(resultSet.getString("payment_type_id"));
      transaction.setTotalAmount(resultSet.getString("total_amount"));
      transaction.setDescription(resultSet.getString("description"));
      transaction.setUnitAmount(resultSet.getString("unit_amount"));
      transaction.setPayleaseFee(resultSet.getString("paylease_fee"));
      transaction.setPmFeeAmount(resultSet.getString("pm_fee_amount"));
      transaction.setAutoPayStartDate(resultSet.getString("auto_pay_start_date"));
      transaction.setAutoPayDebitDay(resultSet.getString("auto_pay_debit_day"));
      transaction.setAutopayId(resultSet.getString("autopay_id"));
      transaction.setExpressPay(resultSet.getString("is_express_pay").equals("1"));

      transactionsList.add(transaction);
    }

    return transactionsList;
  }
}
