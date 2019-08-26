package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"creditReportingId"})

@XmlRootElement(name = "Transaction")
public class CreditReportingOptOutTransaction extends Transaction {

  public static final String CREDIT_REPORTING_OPT_OUT_TRANSACTION = "CreditReportingOptOut";

  private String creditReportingId;

  public String getCreditReportingId() {
    return creditReportingId;
  }

  @XmlElement(name = "CreditReportingId")
  public void setCreditReportingId(String creditReportingId) {
    this.creditReportingId = creditReportingId;
  }
}
