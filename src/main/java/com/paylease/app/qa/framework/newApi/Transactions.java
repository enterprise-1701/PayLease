package com.paylease.app.qa.framework.newApi;

import com.paylease.app.qa.framework.newApi.newGapi.AccountPayDirectTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.AccountPaymentTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.AccountSearchTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.AchPaymentTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.AchReturnsTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CcPaymentTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CcTransactionTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CreateBankPayeeAccountTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CreateBankPayerAccountTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CreateCreditCardPayerAccountTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CreditReportingOptInTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.CreditReportingOptOutTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.DepositsByDateRangeTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.FeeStructureTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.GetRequestTokenTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.IssueCashPermCardTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.IssueCashRentCardTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.IssueCashTempCardTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.PayDirectTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.RemovePayeeAccountTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.RemovePayerAccountTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.ServerPingTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionDetailTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionRefundTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionSearchDateTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionSearchTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.TransactionVoidTransaction;
import com.paylease.app.qa.framework.newApi.newGapi.UpdateIntegrationIdByTransactionTransaction;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Transactions")
@XmlSeeAlso({AccountPayDirectTransaction.class, AccountPaymentTransaction.class,
    AccountSearchTransaction.class, AchPaymentTransaction.class, AchReturnsTransaction.class,
    CcPaymentTransaction.class, CcTransactionTransaction.class,
    CreateBankPayeeAccountTransaction.class, CreateBankPayerAccountTransaction.class,
    CreateCreditCardPayerAccountTransaction.class, CreditReportingOptInTransaction.class,
    CreditReportingOptOutTransaction.class, DepositsByDateRangeTransaction.class,
    FeeStructureTransaction.class, GetRequestTokenTransaction.class,
    IssueCashPermCardTransaction.class, IssueCashRentCardTransaction.class,
    IssueCashTempCardTransaction.class, PayDirectTransaction.class,
    RemovePayeeAccountTransaction.class, RemovePayerAccountTransaction.class,
    ServerPingTransaction.class, TransactionDetailTransaction.class,
    TransactionRefundTransaction.class, TransactionSearchDateTransaction.class,
    TransactionSearchTransaction.class, TransactionVoidTransaction.class,
    UpdateIntegrationIdByTransactionTransaction.class})
public class Transactions {

  private List<Transaction> transactions;

  @XmlElement(name = "Transaction")
  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }
}


