package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.TransactionPaymentField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Transaction Data Access Object.
 *
 * @author Jeffrey Walker
 */
public class TransactionPaymentFieldDao implements Dao<TransactionPaymentField> {

  private static final String TABLE_NAME = "transaction_payment_field";

  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;
  private ArrayList<TransactionPaymentField> transactionsPaymentFieldList;

  @Override
  public boolean create(Connection connection, TransactionPaymentField transactionPaymentField) {
    return false;
  }

  @Override
  public ArrayList<TransactionPaymentField> findById(Connection connection, long id, int limit) {
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = '"
          + id + "' LIMIT " + limit;
      resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return transactionsPaymentFieldList;
  }

  public TransactionPaymentField findById(Connection connection, long id) {
    return findById(connection, id, 1).get(0);
  }

  public ArrayList<TransactionPaymentField> findByTransId(Connection connection, long transactionId,
      int limit) {
    try {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE trans_id = '" + transactionId + "' LIMIT "
          + limit;
      resultSet = statement.executeQuery(tableSQL);

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return transactionsPaymentFieldList;
  }

  @Override
  public int update(Connection connection, TransactionPaymentField transactionPaymentField) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, TransactionPaymentField transactionPaymentField) {
    return false;
  }

  private void processResultSet(ResultSet resultSet) throws SQLException {
    transactionsPaymentFieldList = new ArrayList<>();
    while (resultSet.next()) {
      TransactionPaymentField transactionPaymentField = new TransactionPaymentField();
      transactionPaymentField.setId(resultSet.getLong("id"));
      transactionPaymentField.setTransactionId(resultSet.getLong("trans_id"));
      transactionPaymentField.setVarName(resultSet.getString("var_name"));
      transactionPaymentField.setExtRefId(resultSet.getString("ext_ref_id"));

      transactionsPaymentFieldList.add(transactionPaymentField);
      transactionsPaymentFieldList.sort(Comparator.comparing(TransactionPaymentField::getVarName));

    }
  }
}
