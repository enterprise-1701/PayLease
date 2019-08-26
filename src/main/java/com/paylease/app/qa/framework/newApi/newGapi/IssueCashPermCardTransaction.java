package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.CreateIssuePayerTransaction;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"cardNumber"})
@XmlRootElement(name = "Transaction")
public class IssueCashPermCardTransaction extends CreateIssuePayerTransaction {

  public static final String ISSUE_CASH_PERM_CARD_TRANSACTION = "IssueCashPermCard";

  private String cardNumber;

  public String getCardNumber() {
    return cardNumber;
  }

  @XmlElement(name = "CardNumber")
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }
}
