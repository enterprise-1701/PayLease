package com.paylease.app.qa.framework.utility.database.client.dto;

import java.sql.Date;
import java.math.BigDecimal;


public class PropertiesDTO
{

  private int propId;
  private int userId;
  private String pCity;
  private String pZip;
  private String pState;
  private String pCountry;
  private String pAdr;
  private String pName;
  private String pDesc;
  private Date pCreatedon;
  private short pStatus;
  private BigDecimal visaFeeAmount;
  private BigDecimal transFee;
  private byte passTransFee;
  private byte monthlyDay;
  private BigDecimal ccTaxFee;
  private BigDecimal amexTaxFee;
  private boolean deleted;
  private long deletedBy;
  private byte ccPassTransFee;
  private String propNumber;
  private int groupId;
  private byte pinlessIncurredBy;
  private BigDecimal pinlessFee;
  private byte pmIncurAutopay;
  private BigDecimal pmIncurAutopayAmount;
  private byte disableAch;
  private byte disableCc;
  private boolean virtualPayment;
  private boolean ccUserOn;
  private BigDecimal maxCcAmount;
  private boolean isAlternate;
  private int alternatePropId;
  private int divisionId;
  private byte isDeactivated;
  private long unitCount;
  private boolean freqId;
  private String managedDeposits;
  private String fixedAutopayEnabled;
  private String variableAutopayEnabled;
  private String variableAutopayMaxLimit;
  private String variableAutopayVarName;
  private int variableAutopayStartDay;
  private int variableAutopayEndDay;
  private int variableAutopayMaxBalanceAge;
  private String variableAutopayBalanceColumn;
  private BigDecimal variableAutopayFlatFee;
  private BigDecimal debitCardAutopayFee;
  private String ipnLock;
  private String hasBilling;
  private String hasUem;
  private String hasSubmeter;
  private String hasVacantRecovery;
  private Date lastUpdated;


  //******************
  //Getter Methods
  //******************


  public int getPropId()
  {
    return propId;
  }
  public int getUserId()
  {
    return userId;
  }
  public String getPCity()
  {
    return pCity;
  }
  public String getPZip()
  {
    return pZip;
  }
  public String getPState()
  {
    return pState;
  }
  public String getPCountry()
  {
    return pCountry;
  }
  public String getPAdr()
  {
    return pAdr;
  }
  public String getPName()
  {
    return pName;
  }
  public String getPDesc()
  {
    return pDesc;
  }
  public Date getPCreatedon()
  {
    return pCreatedon;
  }
  public short getPStatus()
  {
    return pStatus;
  }
  public BigDecimal getVisaFeeAmount()
  {
    return visaFeeAmount;
  }
  public BigDecimal getTransFee()
  {
    return transFee;
  }
  public byte getPassTransFee()
  {
    return passTransFee;
  }
  public byte getMonthlyDay()
  {
    return monthlyDay;
  }
  public BigDecimal getCcTaxFee()
  {
    return ccTaxFee;
  }
  public BigDecimal getAmexTaxFee()
  {
    return amexTaxFee;
  }
  public boolean getDeleted()
  {
    return deleted;
  }
  public long getDeletedBy()
  {
    return deletedBy;
  }
  public byte getCcPassTransFee()
  {
    return ccPassTransFee;
  }
  public String getPropNumber()
  {
    return propNumber;
  }
  public int getGroupId()
  {
    return groupId;
  }
  public byte getPinlessIncurredBy()
  {
    return pinlessIncurredBy;
  }
  public BigDecimal getPinlessFee()
  {
    return pinlessFee;
  }
  public byte getPmIncurAutopay()
  {
    return pmIncurAutopay;
  }
  public BigDecimal getPmIncurAutopayAmount()
  {
    return pmIncurAutopayAmount;
  }
  public byte getDisableAch()
  {
    return disableAch;
  }
  public byte getDisableCc()
  {
    return disableCc;
  }
  public boolean getVirtualPayment()
  {
    return virtualPayment;
  }
  public boolean getCcUserOn()
  {
    return ccUserOn;
  }
  public BigDecimal getMaxCcAmount()
  {
    return maxCcAmount;
  }
  public boolean getIsAlternate()
  {
    return isAlternate;
  }
  public int getAlternatePropId()
  {
    return alternatePropId;
  }
  public int getDivisionId()
  {
    return divisionId;
  }
  public byte getIsDeactivated()
  {
    return isDeactivated;
  }
  public long getUnitCount()
  {
    return unitCount;
  }
  public boolean getFreqId()
  {
    return freqId;
  }
  public String getManagedDeposits()
  {
    return managedDeposits;
  }
  public String getFixedAutopayEnabled()
  {
    return fixedAutopayEnabled;
  }
  public String getVariableAutopayEnabled()
  {
    return variableAutopayEnabled;
  }
  public String getVariableAutopayMaxLimit()
  {
    return variableAutopayMaxLimit;
  }
  public String getVariableAutopayVarName()
  {
    return variableAutopayVarName;
  }
  public int getVariableAutopayStartDay()
  {
    return variableAutopayStartDay;
  }
  public int getVariableAutopayEndDay()
  {
    return variableAutopayEndDay;
  }
  public int getVariableAutopayMaxBalanceAge()
  {
    return variableAutopayMaxBalanceAge;
  }
  public String getVariableAutopayBalanceColumn()
  {
    return variableAutopayBalanceColumn;
  }
  public BigDecimal getVariableAutopayFlatFee()
  {
    return variableAutopayFlatFee;
  }
  public BigDecimal getDebitCardAutopayFee()
  {
    return debitCardAutopayFee;
  }
  public String getIpnLock()
  {
    return ipnLock;
  }
  public String getHasBilling()
  {
    return hasBilling;
  }
  public String getHasUem()
  {
    return hasUem;
  }
  public String getHasSubmeter()
  {
    return hasSubmeter;
  }
  public String getHasVacantRecovery()
  {
    return hasVacantRecovery;
  }
  public Date getLastUpdated()
  {
    return lastUpdated;
  }


  //******************
  //Setter Methods
  //******************


  public void setPropId(int propId)
  {
    this.propId = propId;
  }
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  public void setPCity(String pCity)
  {
    this.pCity = pCity;
  }
  public void setPZip(String pZip)
  {
    this.pZip = pZip;
  }
  public void setPState(String pState)
  {
    this.pState = pState;
  }
  public void setPCountry(String pCountry)
  {
    this.pCountry = pCountry;
  }
  public void setPAdr(String pAdr)
  {
    this.pAdr = pAdr;
  }
  public void setPName(String pName)
  {
    this.pName = pName;
  }
  public void setPDesc(String pDesc)
  {
    this.pDesc = pDesc;
  }
  public void setPCreatedon(Date pCreatedon)
  {
    this.pCreatedon = pCreatedon;
  }
  public void setPStatus(short pStatus)
  {
    this.pStatus = pStatus;
  }
  public void setVisaFeeAmount(BigDecimal visaFeeAmount)
  {
    this.visaFeeAmount = visaFeeAmount;
  }
  public void setTransFee(BigDecimal transFee)
  {
    this.transFee = transFee;
  }
  public void setPassTransFee(byte passTransFee)
  {
    this.passTransFee = passTransFee;
  }
  public void setMonthlyDay(byte monthlyDay)
  {
    this.monthlyDay = monthlyDay;
  }
  public void setCcTaxFee(BigDecimal ccTaxFee)
  {
    this.ccTaxFee = ccTaxFee;
  }
  public void setAmexTaxFee(BigDecimal amexTaxFee)
  {
    this.amexTaxFee = amexTaxFee;
  }
  public void setDeleted(boolean deleted)
  {
    this.deleted = deleted;
  }
  public void setDeletedBy(long deletedBy)
  {
    this.deletedBy = deletedBy;
  }
  public void setCcPassTransFee(byte ccPassTransFee)
  {
    this.ccPassTransFee = ccPassTransFee;
  }
  public void setPropNumber(String propNumber)
  {
    this.propNumber = propNumber;
  }
  public void setGroupId(int groupId)
  {
    this.groupId = groupId;
  }
  public void setPinlessIncurredBy(byte pinlessIncurredBy)
  {
    this.pinlessIncurredBy = pinlessIncurredBy;
  }
  public void setPinlessFee(BigDecimal pinlessFee)
  {
    this.pinlessFee = pinlessFee;
  }
  public void setPmIncurAutopay(byte pmIncurAutopay)
  {
    this.pmIncurAutopay = pmIncurAutopay;
  }
  public void setPmIncurAutopayAmount(BigDecimal pmIncurAutopayAmount)
  {
    this.pmIncurAutopayAmount = pmIncurAutopayAmount;
  }
  public void setDisableAch(byte disableAch)
  {
    this.disableAch = disableAch;
  }
  public void setDisableCc(byte disableCc)
  {
    this.disableCc = disableCc;
  }
  public void setVirtualPayment(boolean virtualPayment)
  {
    this.virtualPayment = virtualPayment;
  }
  public void setCcUserOn(boolean ccUserOn)
  {
    this.ccUserOn = ccUserOn;
  }
  public void setMaxCcAmount(BigDecimal maxCcAmount)
  {
    this.maxCcAmount = maxCcAmount;
  }
  public void setIsAlternate(boolean isAlternate)
  {
    this.isAlternate = isAlternate;
  }
  public void setAlternatePropId(int alternatePropId)
  {
    this.alternatePropId = alternatePropId;
  }
  public void setDivisionId(int divisionId)
  {
    this.divisionId = divisionId;
  }
  public void setIsDeactivated(byte isDeactivated)
  {
    this.isDeactivated = isDeactivated;
  }
  public void setUnitCount(long unitCount)
  {
    this.unitCount = unitCount;
  }
  public void setFreqId(boolean freqId)
  {
    this.freqId = freqId;
  }
  public void setManagedDeposits(String managedDeposits)
  {
    this.managedDeposits = managedDeposits;
  }
  public void setFixedAutopayEnabled(String fixedAutopayEnabled)
  {
    this.fixedAutopayEnabled = fixedAutopayEnabled;
  }
  public void setVariableAutopayEnabled(String variableAutopayEnabled)
  {
    this.variableAutopayEnabled = variableAutopayEnabled;
  }
  public void setVariableAutopayMaxLimit(String variableAutopayMaxLimit)
  {
    this.variableAutopayMaxLimit = variableAutopayMaxLimit;
  }
  public void setVariableAutopayVarName(String variableAutopayVarName)
  {
    this.variableAutopayVarName = variableAutopayVarName;
  }
  public void setVariableAutopayStartDay(int variableAutopayStartDay)
  {
    this.variableAutopayStartDay = variableAutopayStartDay;
  }
  public void setVariableAutopayEndDay(int variableAutopayEndDay)
  {
    this.variableAutopayEndDay = variableAutopayEndDay;
  }
  public void setVariableAutopayMaxBalanceAge(int variableAutopayMaxBalanceAge)
  {
    this.variableAutopayMaxBalanceAge = variableAutopayMaxBalanceAge;
  }
  public void setVariableAutopayBalanceColumn(String variableAutopayBalanceColumn)
  {
    this.variableAutopayBalanceColumn = variableAutopayBalanceColumn;
  }
  public void setVariableAutopayFlatFee(BigDecimal variableAutopayFlatFee)
  {
    this.variableAutopayFlatFee = variableAutopayFlatFee;
  }
  public void setDebitCardAutopayFee(BigDecimal debitCardAutopayFee)
  {
    this.debitCardAutopayFee = debitCardAutopayFee;
  }
  public void setIpnLock(String ipnLock)
  {
    this.ipnLock = ipnLock;
  }
  public void setHasBilling(String hasBilling)
  {
    this.hasBilling = hasBilling;
  }
  public void setHasUem(String hasUem)
  {
    this.hasUem = hasUem;
  }
  public void setHasSubmeter(String hasSubmeter)
  {
    this.hasSubmeter = hasSubmeter;
  }
  public void setHasVacantRecovery(String hasVacantRecovery)
  {
    this.hasVacantRecovery = hasVacantRecovery;
  }
  public void setLastUpdated(Date lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

}