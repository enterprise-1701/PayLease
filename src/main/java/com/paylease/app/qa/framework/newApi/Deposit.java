package com.paylease.app.qa.framework.newApi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Deposit Entity
 *
 * @author Jeffrey Walker
 */
@XmlRootElement(name = "Deposit")
public class Deposit {

  private String payeeId; //provided by PayLease
  private double amount; //(ex: 1000.00)

  //Setter Methods
  @XmlElement(name = "PayeeId")
  public void setPayeeId(String payeeId) {
    this.payeeId = payeeId;
  }

  @XmlElement(name = "Amount")
  public void setAmount(double amount) {
    this.amount = amount;
  }

  //Getter Methods
  public String getPayeeId() {
    return payeeId;
  }

  public double getAmount() {
    return amount;
  }

}

