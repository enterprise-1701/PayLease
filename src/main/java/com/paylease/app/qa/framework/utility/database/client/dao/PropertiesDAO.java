package com.paylease.app.qa.framework.utility.database.client.dao;

import com.paylease.app.qa.framework.utility.database.client.dto.PropertiesDTO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class PropertiesDAO
{

  private static final int LIMIT=10;
  private static final String TABLE_NAME = "properties";
  private Statement statement;
  private String tableSQL;
  private ResultSet resultSet;

  //******************
  //Create Method
  //******************


  public int create(Connection connection, PropertiesDTO propertiesDTO)
  {
    int rowsAffected = 0;
    try
    {
      String sql = "INSERT INTO " + TABLE_NAME + " (prop_id, user_id, p_city, p_zip, p_state, p_country, p_adr, p_name, p_desc, p_createdon, p_status, visa_fee_amount, trans_fee, pass_trans_fee, monthly_day, cc_tax_fee, amex_tax_fee, deleted, deleted_by, cc_pass_trans_fee, prop_number, group_id, pinless_incurred_by, pinless_fee, pm_incur_autopay, pm_incur_autopay_amount, disable_ach, disable_cc, virtual_payment, cc_user_on, max_cc_amount, is_alternate, alternate_prop_id, division_id, is_deactivated, unit_count, freq_id, managed_deposits, fixed_autopay_enabled, variable_autopay_enabled, variable_autopay_max_limit, variable_autopay_var_name, variable_autopay_start_day, variable_autopay_end_day, variable_autopay_max_balance_age, variable_autopay_balance_column, variable_autopay_flat_fee, debit_card_autopay_fee, ipn_lock, has_billing, has_uem, has_submeter, has_vacant_recovery, last_updated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0,propertiesDTO.getPropId());
      statement.setInt(1,propertiesDTO.getUserId());
      statement.setString(2,propertiesDTO.getPCity());
      statement.setString(3,propertiesDTO.getPZip());
      statement.setString(4,propertiesDTO.getPState());
      statement.setString(5,propertiesDTO.getPCountry());
      statement.setString(6,propertiesDTO.getPAdr());
      statement.setString(7,propertiesDTO.getPName());
      statement.setString(8,propertiesDTO.getPDesc());
      statement.setDate(9,propertiesDTO.getPCreatedon());
      statement.setShort(10,propertiesDTO.getPStatus());
      statement.setBigDecimal(11,propertiesDTO.getVisaFeeAmount());
      statement.setBigDecimal(12,propertiesDTO.getTransFee());
      statement.setByte(13,propertiesDTO.getPassTransFee());
      statement.setByte(14,propertiesDTO.getMonthlyDay());
      statement.setBigDecimal(15,propertiesDTO.getCcTaxFee());
      statement.setBigDecimal(16,propertiesDTO.getAmexTaxFee());
      statement.setBoolean(17,propertiesDTO.getDeleted());
      statement.setLong(18,propertiesDTO.getDeletedBy());
      statement.setByte(19,propertiesDTO.getCcPassTransFee());
      statement.setString(20,propertiesDTO.getPropNumber());
      statement.setInt(21,propertiesDTO.getGroupId());
      statement.setByte(22,propertiesDTO.getPinlessIncurredBy());
      statement.setBigDecimal(23,propertiesDTO.getPinlessFee());
      statement.setByte(24,propertiesDTO.getPmIncurAutopay());
      statement.setBigDecimal(25,propertiesDTO.getPmIncurAutopayAmount());
      statement.setByte(26,propertiesDTO.getDisableAch());
      statement.setByte(27,propertiesDTO.getDisableCc());
      statement.setBoolean(28,propertiesDTO.getVirtualPayment());
      statement.setBoolean(29,propertiesDTO.getCcUserOn());
      statement.setBigDecimal(30,propertiesDTO.getMaxCcAmount());
      statement.setBoolean(31,propertiesDTO.getIsAlternate());
      statement.setInt(32,propertiesDTO.getAlternatePropId());
      statement.setInt(33,propertiesDTO.getDivisionId());
      statement.setByte(34,propertiesDTO.getIsDeactivated());
      statement.setLong(35,propertiesDTO.getUnitCount());
      statement.setBoolean(36,propertiesDTO.getFreqId());
      statement.setString(37,propertiesDTO.getManagedDeposits());
      statement.setString(38,propertiesDTO.getFixedAutopayEnabled());
      statement.setString(39,propertiesDTO.getVariableAutopayEnabled());
      statement.setString(40,propertiesDTO.getVariableAutopayMaxLimit());
      statement.setString(41,propertiesDTO.getVariableAutopayVarName());
      statement.setInt(42,propertiesDTO.getVariableAutopayStartDay());
      statement.setInt(43,propertiesDTO.getVariableAutopayEndDay());
      statement.setInt(44,propertiesDTO.getVariableAutopayMaxBalanceAge());
      statement.setString(45,propertiesDTO.getVariableAutopayBalanceColumn());
      statement.setBigDecimal(46,propertiesDTO.getVariableAutopayFlatFee());
      statement.setBigDecimal(47,propertiesDTO.getDebitCardAutopayFee());
      statement.setString(48,propertiesDTO.getIpnLock());
      statement.setString(49,propertiesDTO.getHasBilling());
      statement.setString(50,propertiesDTO.getHasUem());
      statement.setString(51,propertiesDTO.getHasSubmeter());
      statement.setString(52,propertiesDTO.getHasVacantRecovery());
      statement.setDate(53,propertiesDTO.getLastUpdated());

      rowsAffected =  statement.executeUpdate();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return rowsAffected;
  }


  //******************
  //Update Method
  //******************


  public int update(Connection connection, PropertiesDTO propertiesDTO, String filterColumnName)
  {
    int rowsAffected = 0;
    try
    {
      String sql = "UPDATE " + TABLE_NAME + " SET prop_id=? , user_id=? , p_city=? , p_zip=? , p_state=? , p_country=? , p_adr=? , p_name=? , p_desc=? , p_createdon=? , p_status=? , visa_fee_amount=? , trans_fee=? , pass_trans_fee=? , monthly_day=? , cc_tax_fee=? , amex_tax_fee=? , deleted=? , deleted_by=? , cc_pass_trans_fee=? , prop_number=? , group_id=? , pinless_incurred_by=? , pinless_fee=? , pm_incur_autopay=? , pm_incur_autopay_amount=? , disable_ach=? , disable_cc=? , virtual_payment=? , cc_user_on=? , max_cc_amount=? , is_alternate=? , alternate_prop_id=? , division_id=? , is_deactivated=? , unit_count=? , freq_id=? , managed_deposits=? , fixed_autopay_enabled=? , variable_autopay_enabled=? , variable_autopay_max_limit=? , variable_autopay_var_name=? , variable_autopay_start_day=? , variable_autopay_end_day=? , variable_autopay_max_balance_age=? , variable_autopay_balance_column=? , variable_autopay_flat_fee=? , debit_card_autopay_fee=? , ipn_lock=? , has_billing=? , has_uem=? , has_submeter=? , has_vacant_recovery=? , last_updated=? WHERE " + filterColumnName + "=?";

      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(0,propertiesDTO.getPropId());
      statement.setInt(1,propertiesDTO.getUserId());
      statement.setString(2,propertiesDTO.getPCity());
      statement.setString(3,propertiesDTO.getPZip());
      statement.setString(4,propertiesDTO.getPState());
      statement.setString(5,propertiesDTO.getPCountry());
      statement.setString(6,propertiesDTO.getPAdr());
      statement.setString(7,propertiesDTO.getPName());
      statement.setString(8,propertiesDTO.getPDesc());
      statement.setDate(9,propertiesDTO.getPCreatedon());
      statement.setShort(10,propertiesDTO.getPStatus());
      statement.setBigDecimal(11,propertiesDTO.getVisaFeeAmount());
      statement.setBigDecimal(12,propertiesDTO.getTransFee());
      statement.setByte(13,propertiesDTO.getPassTransFee());
      statement.setByte(14,propertiesDTO.getMonthlyDay());
      statement.setBigDecimal(15,propertiesDTO.getCcTaxFee());
      statement.setBigDecimal(16,propertiesDTO.getAmexTaxFee());
      statement.setBoolean(17,propertiesDTO.getDeleted());
      statement.setLong(18,propertiesDTO.getDeletedBy());
      statement.setByte(19,propertiesDTO.getCcPassTransFee());
      statement.setString(20,propertiesDTO.getPropNumber());
      statement.setInt(21,propertiesDTO.getGroupId());
      statement.setByte(22,propertiesDTO.getPinlessIncurredBy());
      statement.setBigDecimal(23,propertiesDTO.getPinlessFee());
      statement.setByte(24,propertiesDTO.getPmIncurAutopay());
      statement.setBigDecimal(25,propertiesDTO.getPmIncurAutopayAmount());
      statement.setByte(26,propertiesDTO.getDisableAch());
      statement.setByte(27,propertiesDTO.getDisableCc());
      statement.setBoolean(28,propertiesDTO.getVirtualPayment());
      statement.setBoolean(29,propertiesDTO.getCcUserOn());
      statement.setBigDecimal(30,propertiesDTO.getMaxCcAmount());
      statement.setBoolean(31,propertiesDTO.getIsAlternate());
      statement.setInt(32,propertiesDTO.getAlternatePropId());
      statement.setInt(33,propertiesDTO.getDivisionId());
      statement.setByte(34,propertiesDTO.getIsDeactivated());
      statement.setLong(35,propertiesDTO.getUnitCount());
      statement.setBoolean(36,propertiesDTO.getFreqId());
      statement.setString(37,propertiesDTO.getManagedDeposits());
      statement.setString(38,propertiesDTO.getFixedAutopayEnabled());
      statement.setString(39,propertiesDTO.getVariableAutopayEnabled());
      statement.setString(40,propertiesDTO.getVariableAutopayMaxLimit());
      statement.setString(41,propertiesDTO.getVariableAutopayVarName());
      statement.setInt(42,propertiesDTO.getVariableAutopayStartDay());
      statement.setInt(43,propertiesDTO.getVariableAutopayEndDay());
      statement.setInt(44,propertiesDTO.getVariableAutopayMaxBalanceAge());
      statement.setString(45,propertiesDTO.getVariableAutopayBalanceColumn());
      statement.setBigDecimal(46,propertiesDTO.getVariableAutopayFlatFee());
      statement.setBigDecimal(47,propertiesDTO.getDebitCardAutopayFee());
      statement.setString(48,propertiesDTO.getIpnLock());
      statement.setString(49,propertiesDTO.getHasBilling());
      statement.setString(50,propertiesDTO.getHasUem());
      statement.setString(51,propertiesDTO.getHasSubmeter());
      statement.setString(52,propertiesDTO.getHasVacantRecovery());
      statement.setDate(53,propertiesDTO.getLastUpdated());

      rowsAffected =  statement.executeUpdate();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return rowsAffected;
  }


  //******************
  //Delete Methods
  //******************


  public int removeByPropId(Connection connection, int propId)
  {
    return delete(connection, "prop_id", propId, "int");
  }
  public int removeByUserId(Connection connection, int userId)
  {
    return delete(connection, "user_id", userId, "int");
  }
  public int removeByPCity(Connection connection, String pCity)
  {
    return delete(connection, "p_city", pCity, "String");
  }
  public int removeByPZip(Connection connection, String pZip)
  {
    return delete(connection, "p_zip", pZip, "String");
  }
  public int removeByPState(Connection connection, String pState)
  {
    return delete(connection, "p_state", pState, "String");
  }
  public int removeByPCountry(Connection connection, String pCountry)
  {
    return delete(connection, "p_country", pCountry, "String");
  }
  public int removeByPAdr(Connection connection, String pAdr)
  {
    return delete(connection, "p_adr", pAdr, "String");
  }
  public int removeByPName(Connection connection, String pName)
  {
    return delete(connection, "p_name", pName, "String");
  }
  public int removeByPDesc(Connection connection, String pDesc)
  {
    return delete(connection, "p_desc", pDesc, "String");
  }
  public int removeByPCreatedon(Connection connection, Date pCreatedon)
  {
    return delete(connection, "p_createdon", pCreatedon, "Date");
  }
  public int removeByPStatus(Connection connection, short pStatus)
  {
    return delete(connection, "p_status", pStatus, "short");
  }
  public int removeByVisaFeeAmount(Connection connection, BigDecimal visaFeeAmount)
  {
    return delete(connection, "visa_fee_amount", visaFeeAmount, "BigDecimal");
  }
  public int removeByTransFee(Connection connection, BigDecimal transFee)
  {
    return delete(connection, "trans_fee", transFee, "BigDecimal");
  }
  public int removeByPassTransFee(Connection connection, byte passTransFee)
  {
    return delete(connection, "pass_trans_fee", passTransFee, "byte");
  }
  public int removeByMonthlyDay(Connection connection, byte monthlyDay)
  {
    return delete(connection, "monthly_day", monthlyDay, "byte");
  }
  public int removeByCcTaxFee(Connection connection, BigDecimal ccTaxFee)
  {
    return delete(connection, "cc_tax_fee", ccTaxFee, "BigDecimal");
  }
  public int removeByAmexTaxFee(Connection connection, BigDecimal amexTaxFee)
  {
    return delete(connection, "amex_tax_fee", amexTaxFee, "BigDecimal");
  }
  public int removeByDeleted(Connection connection, boolean deleted)
  {
    return delete(connection, "deleted", deleted, "boolean");
  }
  public int removeByDeletedBy(Connection connection, long deletedBy)
  {
    return delete(connection, "deleted_by", deletedBy, "long");
  }
  public int removeByCcPassTransFee(Connection connection, byte ccPassTransFee)
  {
    return delete(connection, "cc_pass_trans_fee", ccPassTransFee, "byte");
  }
  public int removeByPropNumber(Connection connection, String propNumber)
  {
    return delete(connection, "prop_number", propNumber, "String");
  }
  public int removeByGroupId(Connection connection, int groupId)
  {
    return delete(connection, "group_id", groupId, "int");
  }
  public int removeByPinlessIncurredBy(Connection connection, byte pinlessIncurredBy)
  {
    return delete(connection, "pinless_incurred_by", pinlessIncurredBy, "byte");
  }
  public int removeByPinlessFee(Connection connection, BigDecimal pinlessFee)
  {
    return delete(connection, "pinless_fee", pinlessFee, "BigDecimal");
  }
  public int removeByPmIncurAutopay(Connection connection, byte pmIncurAutopay)
  {
    return delete(connection, "pm_incur_autopay", pmIncurAutopay, "byte");
  }
  public int removeByPmIncurAutopayAmount(Connection connection, BigDecimal pmIncurAutopayAmount)
  {
    return delete(connection, "pm_incur_autopay_amount", pmIncurAutopayAmount, "BigDecimal");
  }
  public int removeByDisableAch(Connection connection, byte disableAch)
  {
    return delete(connection, "disable_ach", disableAch, "byte");
  }
  public int removeByDisableCc(Connection connection, byte disableCc)
  {
    return delete(connection, "disable_cc", disableCc, "byte");
  }
  public int removeByVirtualPayment(Connection connection, boolean virtualPayment)
  {
    return delete(connection, "virtual_payment", virtualPayment, "boolean");
  }
  public int removeByCcUserOn(Connection connection, boolean ccUserOn)
  {
    return delete(connection, "cc_user_on", ccUserOn, "boolean");
  }
  public int removeByMaxCcAmount(Connection connection, BigDecimal maxCcAmount)
  {
    return delete(connection, "max_cc_amount", maxCcAmount, "BigDecimal");
  }
  public int removeByIsAlternate(Connection connection, boolean isAlternate)
  {
    return delete(connection, "is_alternate", isAlternate, "boolean");
  }
  public int removeByAlternatePropId(Connection connection, int alternatePropId)
  {
    return delete(connection, "alternate_prop_id", alternatePropId, "int");
  }
  public int removeByDivisionId(Connection connection, int divisionId)
  {
    return delete(connection, "division_id", divisionId, "int");
  }
  public int removeByIsDeactivated(Connection connection, byte isDeactivated)
  {
    return delete(connection, "is_deactivated", isDeactivated, "byte");
  }
  public int removeByUnitCount(Connection connection, long unitCount)
  {
    return delete(connection, "unit_count", unitCount, "long");
  }
  public int removeByFreqId(Connection connection, boolean freqId)
  {
    return delete(connection, "freq_id", freqId, "boolean");
  }
  public int removeByManagedDeposits(Connection connection, String managedDeposits)
  {
    return delete(connection, "managed_deposits", managedDeposits, "String");
  }
  public int removeByFixedAutopayEnabled(Connection connection, String fixedAutopayEnabled)
  {
    return delete(connection, "fixed_autopay_enabled", fixedAutopayEnabled, "String");
  }
  public int removeByVariableAutopayEnabled(Connection connection, String variableAutopayEnabled)
  {
    return delete(connection, "variable_autopay_enabled", variableAutopayEnabled, "String");
  }
  public int removeByVariableAutopayMaxLimit(Connection connection, String variableAutopayMaxLimit)
  {
    return delete(connection, "variable_autopay_max_limit", variableAutopayMaxLimit, "String");
  }
  public int removeByVariableAutopayVarName(Connection connection, String variableAutopayVarName)
  {
    return delete(connection, "variable_autopay_var_name", variableAutopayVarName, "String");
  }
  public int removeByVariableAutopayStartDay(Connection connection, int variableAutopayStartDay)
  {
    return delete(connection, "variable_autopay_start_day", variableAutopayStartDay, "int");
  }
  public int removeByVariableAutopayEndDay(Connection connection, int variableAutopayEndDay)
  {
    return delete(connection, "variable_autopay_end_day", variableAutopayEndDay, "int");
  }
  public int removeByVariableAutopayMaxBalanceAge(Connection connection, int variableAutopayMaxBalanceAge)
  {
    return delete(connection, "variable_autopay_max_balance_age", variableAutopayMaxBalanceAge, "int");
  }
  public int removeByVariableAutopayBalanceColumn(Connection connection, String variableAutopayBalanceColumn)
  {
    return delete(connection, "variable_autopay_balance_column", variableAutopayBalanceColumn, "String");
  }
  public int removeByVariableAutopayFlatFee(Connection connection, BigDecimal variableAutopayFlatFee)
  {
    return delete(connection, "variable_autopay_flat_fee", variableAutopayFlatFee, "BigDecimal");
  }
  public int removeByDebitCardAutopayFee(Connection connection, BigDecimal debitCardAutopayFee)
  {
    return delete(connection, "debit_card_autopay_fee", debitCardAutopayFee, "BigDecimal");
  }
  public int removeByIpnLock(Connection connection, String ipnLock)
  {
    return delete(connection, "ipn_lock", ipnLock, "String");
  }
  public int removeByHasBilling(Connection connection, String hasBilling)
  {
    return delete(connection, "has_billing", hasBilling, "String");
  }
  public int removeByHasUem(Connection connection, String hasUem)
  {
    return delete(connection, "has_uem", hasUem, "String");
  }
  public int removeByHasSubmeter(Connection connection, String hasSubmeter)
  {
    return delete(connection, "has_submeter", hasSubmeter, "String");
  }
  public int removeByHasVacantRecovery(Connection connection, String hasVacantRecovery)
  {
    return delete(connection, "has_vacant_recovery", hasVacantRecovery, "String");
  }
  public int removeByLastUpdated(Connection connection, Date lastUpdated)
  {
    return delete(connection, "last_updated", lastUpdated, "Date");
  }
  //******************
  //Read Methods
  //******************


  public ArrayList<PropertiesDTO> findByPropId(Connection connection, int propId, int limit)
  {
    return read(connection, "prop_id", propId, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByUserId(Connection connection, int userId, int limit)
  {
    return read(connection, "user_id", userId, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPCity(Connection connection, String pCity, int limit)
  {
    return read(connection, "p_city", pCity, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPZip(Connection connection, String pZip, int limit)
  {
    return read(connection, "p_zip", pZip, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPState(Connection connection, String pState, int limit)
  {
    return read(connection, "p_state", pState, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPCountry(Connection connection, String pCountry, int limit)
  {
    return read(connection, "p_country", pCountry, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPAdr(Connection connection, String pAdr, int limit)
  {
    return read(connection, "p_adr", pAdr, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPName(Connection connection, String pName, int limit)
  {
    return read(connection, "p_name", pName, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPDesc(Connection connection, String pDesc, int limit)
  {
    return read(connection, "p_desc", pDesc, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPCreatedon(Connection connection, Date pCreatedon, int limit)
  {
    return read(connection, "p_createdon", pCreatedon, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPStatus(Connection connection, short pStatus, int limit)
  {
    return read(connection, "p_status", pStatus, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVisaFeeAmount(Connection connection, BigDecimal visaFeeAmount, int limit)
  {
    return read(connection, "visa_fee_amount", visaFeeAmount, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByTransFee(Connection connection, BigDecimal transFee, int limit)
  {
    return read(connection, "trans_fee", transFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPassTransFee(Connection connection, byte passTransFee, int limit)
  {
    return read(connection, "pass_trans_fee", passTransFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByMonthlyDay(Connection connection, byte monthlyDay, int limit)
  {
    return read(connection, "monthly_day", monthlyDay, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByCcTaxFee(Connection connection, BigDecimal ccTaxFee, int limit)
  {
    return read(connection, "cc_tax_fee", ccTaxFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByAmexTaxFee(Connection connection, BigDecimal amexTaxFee, int limit)
  {
    return read(connection, "amex_tax_fee", amexTaxFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByDeleted(Connection connection, boolean deleted, int limit)
  {
    return read(connection, "deleted", deleted, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByDeletedBy(Connection connection, long deletedBy, int limit)
  {
    return read(connection, "deleted_by", deletedBy, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByCcPassTransFee(Connection connection, byte ccPassTransFee, int limit)
  {
    return read(connection, "cc_pass_trans_fee", ccPassTransFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPropNumber(Connection connection, String propNumber, int limit)
  {
    return read(connection, "prop_number", propNumber, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByGroupId(Connection connection, int groupId, int limit)
  {
    return read(connection, "group_id", groupId, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPinlessIncurredBy(Connection connection, byte pinlessIncurredBy, int limit)
  {
    return read(connection, "pinless_incurred_by", pinlessIncurredBy, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPinlessFee(Connection connection, BigDecimal pinlessFee, int limit)
  {
    return read(connection, "pinless_fee", pinlessFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPmIncurAutopay(Connection connection, byte pmIncurAutopay, int limit)
  {
    return read(connection, "pm_incur_autopay", pmIncurAutopay, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByPmIncurAutopayAmount(Connection connection, BigDecimal pmIncurAutopayAmount, int limit)
  {
    return read(connection, "pm_incur_autopay_amount", pmIncurAutopayAmount, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByDisableAch(Connection connection, byte disableAch, int limit)
  {
    return read(connection, "disable_ach", disableAch, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByDisableCc(Connection connection, byte disableCc, int limit)
  {
    return read(connection, "disable_cc", disableCc, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVirtualPayment(Connection connection, boolean virtualPayment, int limit)
  {
    return read(connection, "virtual_payment", virtualPayment, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByCcUserOn(Connection connection, boolean ccUserOn, int limit)
  {
    return read(connection, "cc_user_on", ccUserOn, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByMaxCcAmount(Connection connection, BigDecimal maxCcAmount, int limit)
  {
    return read(connection, "max_cc_amount", maxCcAmount, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByIsAlternate(Connection connection, boolean isAlternate, int limit)
  {
    return read(connection, "is_alternate", isAlternate, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByAlternatePropId(Connection connection, int alternatePropId, int limit)
  {
    return read(connection, "alternate_prop_id", alternatePropId, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByDivisionId(Connection connection, int divisionId, int limit)
  {
    return read(connection, "division_id", divisionId, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByIsDeactivated(Connection connection, byte isDeactivated, int limit)
  {
    return read(connection, "is_deactivated", isDeactivated, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByUnitCount(Connection connection, long unitCount, int limit)
  {
    return read(connection, "unit_count", unitCount, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByFreqId(Connection connection, boolean freqId, int limit)
  {
    return read(connection, "freq_id", freqId, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByManagedDeposits(Connection connection, String managedDeposits, int limit)
  {
    return read(connection, "managed_deposits", managedDeposits, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByFixedAutopayEnabled(Connection connection, String fixedAutopayEnabled, int limit)
  {
    return read(connection, "fixed_autopay_enabled", fixedAutopayEnabled, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayEnabled(Connection connection, String variableAutopayEnabled, int limit)
  {
    return read(connection, "variable_autopay_enabled", variableAutopayEnabled, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayMaxLimit(Connection connection, String variableAutopayMaxLimit, int limit)
  {
    return read(connection, "variable_autopay_max_limit", variableAutopayMaxLimit, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayVarName(Connection connection, String variableAutopayVarName, int limit)
  {
    return read(connection, "variable_autopay_var_name", variableAutopayVarName, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayStartDay(Connection connection, int variableAutopayStartDay, int limit)
  {
    return read(connection, "variable_autopay_start_day", variableAutopayStartDay, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayEndDay(Connection connection, int variableAutopayEndDay, int limit)
  {
    return read(connection, "variable_autopay_end_day", variableAutopayEndDay, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayMaxBalanceAge(Connection connection, int variableAutopayMaxBalanceAge, int limit)
  {
    return read(connection, "variable_autopay_max_balance_age", variableAutopayMaxBalanceAge, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayBalanceColumn(Connection connection, String variableAutopayBalanceColumn, int limit)
  {
    return read(connection, "variable_autopay_balance_column", variableAutopayBalanceColumn, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByVariableAutopayFlatFee(Connection connection, BigDecimal variableAutopayFlatFee, int limit)
  {
    return read(connection, "variable_autopay_flat_fee", variableAutopayFlatFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByDebitCardAutopayFee(Connection connection, BigDecimal debitCardAutopayFee, int limit)
  {
    return read(connection, "debit_card_autopay_fee", debitCardAutopayFee, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByIpnLock(Connection connection, String ipnLock, int limit)
  {
    return read(connection, "ipn_lock", ipnLock, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByHasBilling(Connection connection, String hasBilling, int limit)
  {
    return read(connection, "has_billing", hasBilling, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByHasUem(Connection connection, String hasUem, int limit)
  {
    return read(connection, "has_uem", hasUem, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByHasSubmeter(Connection connection, String hasSubmeter, int limit)
  {
    return read(connection, "has_submeter", hasSubmeter, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByHasVacantRecovery(Connection connection, String hasVacantRecovery, int limit)
  {
    return read(connection, "has_vacant_recovery", hasVacantRecovery, LIMIT);
  }
  public ArrayList<PropertiesDTO> findByLastUpdated(Connection connection, Date lastUpdated, int limit)
  {
    return read(connection, "last_updated", lastUpdated, LIMIT);
  }
  //******************
  //Helper Methods
  //******************


  public ArrayList<PropertiesDTO> read(Connection connection, String columnName, Object columnValue, int limit)
  {
    ArrayList<PropertiesDTO> resultsArrayList = null;
    try
    {
      statement = connection.createStatement();
      tableSQL = "SELECT * FROM " + TABLE_NAME + " WHERE "+columnName+" = '"+columnValue+"' LIMIT " + limit;
      resultSet =  statement.executeQuery(tableSQL);
      resultsArrayList = processResultSet(resultSet);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return resultsArrayList;
  }


  public int delete(Connection connection, String columnName, Object columnValue, String columnType)
  {
    int rowsDeleted = 0;
    try
    {
      tableSQL = "DELETE * FROM " + TABLE_NAME + " WHERE "+columnName+" = ?";
      PreparedStatement statement = connection.prepareStatement(tableSQL);
      if(columnType.equals("String"))
      {
        statement.setString(1, (String)columnValue);
      }
      else if(columnType.equals("boolean"))
      {
        statement.setBoolean(1, (boolean)columnValue);
      }
      else if(columnType.equals("Date"))
      {
        statement.setDate(1, (Date)columnValue);
      }
      else if(columnType.equals("double"))
      {
        statement.setDouble(1, (double)columnValue);
      }
      else if(columnType.equals("float"))
      {
        statement.setFloat(1, (float)columnValue);
      }
      else if(columnType.equals("long"))
      {
        statement.setLong(1, (long)columnValue);
      }
      else if(columnType.equals("int"))
      {
        statement.setInt(1, (int)columnValue);
      }
      else if(columnType.equals("short"))
      {
        statement.setShort(1, (short)columnValue);
      }
      else if(columnType.equals("byte"))
      {
        statement.setByte(1, (byte)columnValue);
      }
      else if(columnType.equals("BigDecimal"))
      {
        statement.setBigDecimal(1, (BigDecimal)columnValue);
      }
      rowsDeleted =  statement.executeUpdate(tableSQL);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return rowsDeleted;
  }


  private ArrayList<PropertiesDTO>  processResultSet(ResultSet resultSet) throws SQLException
  {
    ArrayList<PropertiesDTO> resultsArrayList = new ArrayList<PropertiesDTO>();
    while (resultSet.next())
    {
      PropertiesDTO propertiesDTO = new PropertiesDTO();
      propertiesDTO.setPropId(resultSet.getInt("prop_id"));
      propertiesDTO.setUserId(resultSet.getInt("user_id"));
      propertiesDTO.setPCity(resultSet.getString("p_city"));
      propertiesDTO.setPZip(resultSet.getString("p_zip"));
      propertiesDTO.setPState(resultSet.getString("p_state"));
      propertiesDTO.setPCountry(resultSet.getString("p_country"));
      propertiesDTO.setPAdr(resultSet.getString("p_adr"));
      propertiesDTO.setPName(resultSet.getString("p_name"));
      propertiesDTO.setPDesc(resultSet.getString("p_desc"));
      propertiesDTO.setPCreatedon(resultSet.getDate("p_createdon"));
      propertiesDTO.setPStatus(resultSet.getShort("p_status"));
      propertiesDTO.setVisaFeeAmount(resultSet.getBigDecimal("visa_fee_amount"));
      propertiesDTO.setTransFee(resultSet.getBigDecimal("trans_fee"));
      propertiesDTO.setPassTransFee(resultSet.getByte("pass_trans_fee"));
      propertiesDTO.setMonthlyDay(resultSet.getByte("monthly_day"));
      propertiesDTO.setCcTaxFee(resultSet.getBigDecimal("cc_tax_fee"));
      propertiesDTO.setAmexTaxFee(resultSet.getBigDecimal("amex_tax_fee"));
      propertiesDTO.setDeleted(resultSet.getBoolean("deleted"));
      propertiesDTO.setDeletedBy(resultSet.getLong("deleted_by"));
      propertiesDTO.setCcPassTransFee(resultSet.getByte("cc_pass_trans_fee"));
      propertiesDTO.setPropNumber(resultSet.getString("prop_number"));
      propertiesDTO.setGroupId(resultSet.getInt("group_id"));
      propertiesDTO.setPinlessIncurredBy(resultSet.getByte("pinless_incurred_by"));
      propertiesDTO.setPinlessFee(resultSet.getBigDecimal("pinless_fee"));
      propertiesDTO.setPmIncurAutopay(resultSet.getByte("pm_incur_autopay"));
      propertiesDTO.setPmIncurAutopayAmount(resultSet.getBigDecimal("pm_incur_autopay_amount"));
      propertiesDTO.setDisableAch(resultSet.getByte("disable_ach"));
      propertiesDTO.setDisableCc(resultSet.getByte("disable_cc"));
      propertiesDTO.setVirtualPayment(resultSet.getBoolean("virtual_payment"));
      propertiesDTO.setCcUserOn(resultSet.getBoolean("cc_user_on"));
      propertiesDTO.setMaxCcAmount(resultSet.getBigDecimal("max_cc_amount"));
      propertiesDTO.setIsAlternate(resultSet.getBoolean("is_alternate"));
      propertiesDTO.setAlternatePropId(resultSet.getInt("alternate_prop_id"));
      propertiesDTO.setDivisionId(resultSet.getInt("division_id"));
      propertiesDTO.setIsDeactivated(resultSet.getByte("is_deactivated"));
      propertiesDTO.setUnitCount(resultSet.getLong("unit_count"));
      propertiesDTO.setFreqId(resultSet.getBoolean("freq_id"));
      propertiesDTO.setManagedDeposits(resultSet.getString("managed_deposits"));
      propertiesDTO.setFixedAutopayEnabled(resultSet.getString("fixed_autopay_enabled"));
      propertiesDTO.setVariableAutopayEnabled(resultSet.getString("variable_autopay_enabled"));
      propertiesDTO.setVariableAutopayMaxLimit(resultSet.getString("variable_autopay_max_limit"));
      propertiesDTO.setVariableAutopayVarName(resultSet.getString("variable_autopay_var_name"));
      propertiesDTO.setVariableAutopayStartDay(resultSet.getInt("variable_autopay_start_day"));
      propertiesDTO.setVariableAutopayEndDay(resultSet.getInt("variable_autopay_end_day"));
      propertiesDTO.setVariableAutopayMaxBalanceAge(resultSet.getInt("variable_autopay_max_balance_age"));
      propertiesDTO.setVariableAutopayBalanceColumn(resultSet.getString("variable_autopay_balance_column"));
      propertiesDTO.setVariableAutopayFlatFee(resultSet.getBigDecimal("variable_autopay_flat_fee"));
      propertiesDTO.setDebitCardAutopayFee(resultSet.getBigDecimal("debit_card_autopay_fee"));
      propertiesDTO.setIpnLock(resultSet.getString("ipn_lock"));
      propertiesDTO.setHasBilling(resultSet.getString("has_billing"));
      propertiesDTO.setHasUem(resultSet.getString("has_uem"));
      propertiesDTO.setHasSubmeter(resultSet.getString("has_submeter"));
      propertiesDTO.setHasVacantRecovery(resultSet.getString("has_vacant_recovery"));
      propertiesDTO.setLastUpdated(resultSet.getDate("last_updated"));
      resultsArrayList.add(propertiesDTO);
    }
    return resultsArrayList;
  }

}
