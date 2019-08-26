package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.GatewayFees;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GatewayFeesDao implements Dao<GatewayFees> {

  private static final String TABLE_NAME = "gateway_fees";

  @Override
  public boolean create(Connection connection, GatewayFees gatewayFees) {
    return false;
  }

  @Override
  public ArrayList<GatewayFees> findById(Connection connection, long id, int limit) {
    try {
      Statement statement = connection.createStatement();
      String tableSql = "SELECT * FROM " + TABLE_NAME + " WHERE gateway_id = '" + id + "'";
      ResultSet resultSet = statement.executeQuery(tableSql);

      return processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return null;
  }

  @Override
  public int update(Connection connection, GatewayFees gatewayFees) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, GatewayFees gatewayFees) {
    return false;
  }

  /**
   * Process result set from the query.
   *
   * @param resultSet Set of results from query
   * @return ArrayList of GatewayFees
   * @throws Exception The exception when reading the resultSet
   */
  private ArrayList<GatewayFees> processResultSet(ResultSet resultSet) throws Exception {
    ArrayList<GatewayFees> list = new ArrayList<>();

    while (resultSet.next()) {
      GatewayFees gatewayFees = new GatewayFees();
      gatewayFees.setGatewayId(resultSet.getInt("gateway_id"));
      gatewayFees.setNsfChkscanFee(resultSet.getDouble("nsf_chkscan_fee"));

      list.add(gatewayFees);
    }

    return list;
  }
}
