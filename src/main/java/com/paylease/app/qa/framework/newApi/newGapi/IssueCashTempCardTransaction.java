package com.paylease.app.qa.framework.newApi.newGapi;

import com.paylease.app.qa.framework.newApi.CreateIssuePayerTransaction;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Transactions")
public class IssueCashTempCardTransaction extends CreateIssuePayerTransaction {

  public static final String ISSUE_CASH_TEMP_CARD_TRANSACTION = "IssueCashTempCard";

}
