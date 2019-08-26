package com.paylease.app.qa.framework.newApi;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PaymentFields")
public class PaymentFields {

  private List<PaymentField> paymentFieldList;

  public List<PaymentField> getPaymentFieldList() {
    return paymentFieldList;
  }

  @XmlElement(name = "PaymentField")
  public void setPaymentFieldList( List<PaymentField> paymentFieldList) {
    this.paymentFieldList = paymentFieldList;
  }
}
