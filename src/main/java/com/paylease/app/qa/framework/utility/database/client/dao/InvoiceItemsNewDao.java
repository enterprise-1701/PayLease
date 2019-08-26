package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.InvoiceItemsNew;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class InvoiceItemsNewDao implements Dao<InvoiceItemsNew> {

  private static final String TABLE_NAME = "invoice_items_new";

  @Override
  public boolean create(Connection connection, InvoiceItemsNew invoiceItemsNew) {
    return false;
  }

  @Override
  public ArrayList<InvoiceItemsNew> findById(Connection connection, long id, int limit) {
    return null;
  }

  @Override
  public int update(Connection connection, InvoiceItemsNew invoiceItemsNew) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, InvoiceItemsNew invoiceItemsNew) {
    return false;
  }

  /**
   * Find by invoice id and type.
   *
   * @param connection Connection
   * @param id invoice id
   * @param type invoice type id
   * @return ArrayList of InvoiceItemsNew
   */
  public ArrayList<InvoiceItemsNew> findByInvoiceIdAndType(
      Connection connection, long id, int type
  ) {
    try {
      Statement statement = connection.createStatement();
      String tableSql =
          "SELECT * FROM " + TABLE_NAME + " WHERE invoice_id = '" + id + "' AND invoice_type_id = '"
              + type + "'";
      ResultSet resultSet = statement.executeQuery(tableSql);

      return processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return new ArrayList<>();
  }

  /**
   * Process result set from the query.
   *
   * @param resultSet Set of results from query
   * @return ArrayList of InvoiceItemsNew
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<InvoiceItemsNew> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<InvoiceItemsNew> list = new ArrayList<>();

    while (resultSet.next()) {
      InvoiceItemsNew invoiceItemsNew = new InvoiceItemsNew();
      invoiceItemsNew.setItemAmount(resultSet.getDouble("item_amount"));
      invoiceItemsNew.setItemQuantity(resultSet.getInt("item_quantity"));

      list.add(invoiceItemsNew);
    }

    return list;
  }
}
