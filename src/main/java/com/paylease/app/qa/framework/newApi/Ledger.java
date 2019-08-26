package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ledger Entity
 *
 */
@XmlType(propOrder = {"ledgerType", "chargeCode"})
@XmlRootElement(name = "Ledger")
public class Ledger {

  public static final String LEASE = "lease";
  public static final String SECURITY_DEPOSIT = "security_deposit";
  public static final String SEC = "SEC";
  public static final String CHARGE_CODE_TOO_LONG = "abcdefghijklmnopqrstuvwxyz";

  private String ledgerType;
  private String chargeCode;

  @XmlElement(name = "LedgerType")
  public void setLedgerType(String ledgerType) {
    this.ledgerType = ledgerType;
  }

  @XmlElement(name = "ChargeCode")
  public void setChargeCode(String chargeCode) {
    this.chargeCode = chargeCode;
  }

  public String getLedgerType() {
    return ledgerType;
  }

  public String getChargeCode() {
    return chargeCode;
  }
}
