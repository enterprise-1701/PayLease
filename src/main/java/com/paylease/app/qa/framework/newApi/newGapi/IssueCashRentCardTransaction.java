package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.CreateIssuePayerTransaction;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateIssueTransaction")
public class IssueCashRentCardTransaction extends CreateIssuePayerTransaction {

  public static final String ISSUE_CASH_RENT_CARD_TRANSACTION = "IssueCashRentCard";

}
