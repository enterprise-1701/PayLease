package com.paylease.app.qa.framework.newApi;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SplitDeposit")
public class SplitDeposit {

  List<Deposit> deposit;

  @XmlElement(name = "Deposit")
  public void setDepositList(List<Deposit> deposit) {
    this.deposit = deposit;
  }

  public List<Deposit> getDepositList() {
    return deposit;
  }
}
