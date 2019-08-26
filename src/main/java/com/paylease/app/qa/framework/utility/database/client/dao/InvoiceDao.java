package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.Invoice;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class InvoiceDao implements Dao<Invoice> {

  private static final String TABLE_NAME = "invoices";

  @Override
  public boolean create(Connection connection, Invoice invoice) {
    return false;
  }

  @Override
  public ArrayList<Invoice> findById(Connection connection, long id, int limit) {
    return null;
  }

  @Override
  public int update(Connection connection, Invoice invoice) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, Invoice invoice) {
    return false;
  }

  /**
   * Find by invoice_config_id.
   *
   * @param connection Connection
   * @param id invoice config id
   * @return Invoice
   */
  public Invoice findLatestByInvoiceConfigId(Connection connection, long id) {
    try {
      Statement statement = connection.createStatement();
      String tableSql =
          "SELECT * FROM " + TABLE_NAME + " WHERE invoice_config_id = '" + id
              + "' ORDER BY invoice_id desc LIMIT 1";
      ResultSet resultSet = statement.executeQuery(tableSql);

      ArrayList<Invoice> list = processResultSet(resultSet);

      return list.get(0);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  /**
   * Process result set from the query.
   *
   * @param resultSet Set of results from query
   * @return ArrayList of Invoices
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<Invoice> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<Invoice> list = new ArrayList<>();

    while (resultSet.next()) {
      Invoice invoice = new Invoice();
      invoice.setInvoiceId(resultSet.getInt("invoice_id"));

      list.add(invoice);
    }

    return list;
  }
}
