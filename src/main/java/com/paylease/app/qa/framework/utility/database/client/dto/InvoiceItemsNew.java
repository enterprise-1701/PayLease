package com.paylease.app.qa.framework.utility.database.client.dto;

public class InvoiceItemsNew {
  private int invoiceItemId;
  private int invoiceId;
  private int pmId;
  private int invoiceTypeId;
  private double itemAmount;
  private int itemQuantity;

  public int getInvoiceItemId() {
    return invoiceItemId;
  }

  public void setInvoiceItemId(int invoiceItemId) {
    this.invoiceItemId = invoiceItemId;
  }

  public int getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(int invoiceId) {
    this.invoiceId = invoiceId;
  }

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public int getInvoiceTypeId() {
    return invoiceTypeId;
  }

  public void setInvoiceTypeId(int invoiceTypeId) {
    this.invoiceTypeId = invoiceTypeId;
  }

  public double getItemAmount() {
    return itemAmount;
  }

  public void setItemAmount(double itemAmount) {
    this.itemAmount = itemAmount;
  }

  public int getItemQuantity() {
    return itemQuantity;
  }

  public void setItemQuantity(int itemQuantity) {
    this.itemQuantity = itemQuantity;
  }
}
