package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.AutopayTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AutopayTemplateDao implements Dao<AutopayTemplate> {

  private static final String TABLE_NAME = "autopay_templates";

  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;
  private ArrayList<AutopayTemplate> autopayTemplateArrayList;

  @Override
  public boolean create(Connection connection, AutopayTemplate autopayTemplate) {
    return false;
  }

  @Override
  public ArrayList<AutopayTemplate> findById(Connection connection, long id, int limit) {
    try {
      PreparedStatement ps = connection
          .prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE autopay_id = ? LIMIT ?");
      ps.setLong(1, id);
      ps.setInt(2, limit);

      Logger.debug("SQL script: " + ps.toString());
      resultSet = ps.executeQuery();

      processResultSet(resultSet);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return autopayTemplateArrayList;
  }

  @Override
  public int update(Connection connection, AutopayTemplate autopayTemplate) {
    int numOfRowsAffected = 0;

    try {
      PreparedStatement ps = connection
          .prepareStatement("UPDATE " + TABLE_NAME
              + " SET last_executed_date=?, debit_day=?, start_date=?, pm_fee_fixed=? , fee_fixed=? "
              + "WHERE autopay_id=?");

      ps.setString(1, autopayTemplate.getLastExecutedDate());
      ps.setString(2, autopayTemplate.getDebitDay());
      ps.setString(3, autopayTemplate.getStartDate());
      ps.setString(4, autopayTemplate.getPmFeeFixed());
      ps.setString(5, autopayTemplate.getFeeFixed());
      ps.setString(6, autopayTemplate.getAutopayId());

      numOfRowsAffected = ps.executeUpdate();

      Logger.debug(ps.toString());
    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return numOfRowsAffected;
  }

  @Override
  public boolean delete(Connection connection, AutopayTemplate autopayTemplate) {
    return false;
  }

  private ArrayList<AutopayTemplate> processResultSet(ResultSet resultSet) throws SQLException {
    autopayTemplateArrayList = new ArrayList<>();

    while (resultSet.next()) {
      AutopayTemplate autopayTemplate = new AutopayTemplate();

      autopayTemplate.setFeeFixed(resultSet.getString("fee_fixed"));
      autopayTemplate.setPmFeeFixed(resultSet.getString("pm_fee_fixed"));
      autopayTemplate.setStartDate(resultSet.getString("start_date"));
      autopayTemplate.setDebitDay(resultSet.getString("debit_day"));
      autopayTemplate.setLastExecutedDate(resultSet.getString("last_executed_date"));
      autopayTemplate.setAutopayId(resultSet.getString("autopay_id"));
      autopayTemplate.setExpressPay(resultSet.getString("is_express_pay").equals("1"));

      autopayTemplateArrayList.add(autopayTemplate);
    }

    return autopayTemplateArrayList;
  }
}
