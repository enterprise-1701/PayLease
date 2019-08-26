package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.database.client.dto.PmFeeTier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PmFeeTierDao implements Dao<PmFeeTier> {

  private static final String TABLE_NAME = "pm_fee_tiers";

  @Override
  public boolean create(Connection connection, PmFeeTier pmFeeTier) {
    return false;
  }

  @Override
  public ArrayList<PmFeeTier> findById(Connection connection, long id, int limit) {
    return null;
  }

  @Override
  public int update(Connection connection, PmFeeTier pmFeeTier) {
    return 0;
  }

  @Override
  public boolean delete(Connection connection, PmFeeTier pmFeeTier) {
    return false;
  }

  /**
   * Returns the count of PM fee tier for specific combination of attributes.
   *
   * @param connection Connection
   * @param pmFeeTier pmFeeTier object
   * @return count
   */
  public int getCountOfPmFeeTier(Connection connection, PmFeeTier pmFeeTier) {
    try {
      PreparedStatement ps = connection
          .prepareStatement("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE "
              + "pm_id=? AND prop_id=? AND payment_type_id=? AND payment_method_id=? AND var_name=? "
              + "AND tier_amount=? AND fee_type=? AND fee_value=? AND fee_incur=?");

      ps.setInt(1, pmFeeTier.getPmId());
      ps.setInt(2, pmFeeTier.getPropId());
      ps.setInt(3, pmFeeTier.getPaymentTypeId());
      ps.setInt(4, pmFeeTier.getPaymentMethodId());
      ps.setString(5, pmFeeTier.getVarName());
      ps.setInt(6, pmFeeTier.getTierAmount());
      ps.setString(7, pmFeeTier.getFeeType());
      ps.setInt(8, pmFeeTier.getFeeValue());
      ps.setString(9, pmFeeTier.getFeeIncur());

      ResultSet resultSet = ps.executeQuery();
      resultSet.next();

      return resultSet.getInt(1);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return -1;
  }

  /**
   * Returns the number of rows in pm_fee_tiers for the specified Pm.
   *
   * @param connection Connection
   * @param pmId pmId
   * @return count
   */
  public int getCountByPmId(Connection connection, int pmId) {
    try {
      PreparedStatement ps = connection
          .prepareStatement("SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE pm_id=?");

      ps.setInt(1, pmId);

      ResultSet resultSet = ps.executeQuery();
      resultSet.next();

      return resultSet.getInt(1);

    } catch (Exception e) {
      Logger.error(e.getMessage());
    }

    return -1;
  }
}
