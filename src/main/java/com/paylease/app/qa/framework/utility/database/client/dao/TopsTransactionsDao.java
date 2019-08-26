package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.TopsTransactions;
import com.paylease.app.qa.framework.utility.database.client.dto.Transaction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Transaction Data Access Object.
 *
 * @author Glenn Tejidor
 */

public class TopsTransactionsDao implements Dao<TopsTransactionsDao> {

  private static final String TABLE_NAME = "tops_transactions";
  private ArrayList<Transaction> transactionArrayList;
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;
  private ArrayList<TopsTransactions> topsTransactionsList;

  /**
   * Find tops integrated transaction.
   *
   * @param connection connection
   * @param trans transaction
   * @return topsTransactionsList
   */
  public ArrayList<TopsTransactions> findIntegratedTransaction (Connection connection, long trans) {
    try {
      Statement statement = connection.createStatement();
      tableSQL =
          "SELECT * FROM " + TABLE_NAME + " WHERE TRANS_ID = " + trans;

      Logger.debug("SQL script: " + tableSQL);
      resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return topsTransactionsList;
  }

  @Override
  public boolean create(Connection connection, TopsTransactionsDao topsTransactionsDao) {
    return false;
  }

  @Override
  public ArrayList<TopsTransactionsDao> findById(Connection connection, long id, int limit) {
    return null;
  }

  @Override
  public int update(Connection connection, TopsTransactionsDao topsTransactionsDao) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, TopsTransactionsDao topsTransactionsDao) {
    return false;
  }

  private ArrayList<TopsTransactions> processResultSet(ResultSet resultSet) throws SQLException {
    topsTransactionsList = new ArrayList<>();

    while (resultSet.next()) {
      TopsTransactions topsTransactions = new TopsTransactions();
      topsTransactions.setTopsTransactionId(resultSet.getLong("trans_id"));

      topsTransactionsList.add(topsTransactions);
    }

    return topsTransactionsList;
  }

}
