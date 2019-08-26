package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.Transaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = "showIncurredFees")

@XmlRootElement(name = "Transaction")
public class FeeStructureTransaction extends Transaction {

  public static final String FEE_STRUCTURE_TRANSACTION = "FeeStructure";

  private String showIncurredFees;

  public String getShowIncurredFees() {
    return showIncurredFees;
  }

  @XmlElement(name = "ShowIncurredFees")
  public void setShowIncurredFees(String showIncurredFees) {
    this.showIncurredFees = showIncurredFees;
  }
}
