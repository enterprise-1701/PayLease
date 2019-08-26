package com.paylease.app.qa.framework.utility.database.client.dto;

public class GatewayFees {

  private int gatewayId;
  private double achFee;
  private double achChkscanFee;
  private double mcFee;
  private double amexFee;
  private double ccFeeSurcharge;
  private double visaFlatFee;
  private String isPayleaseManagedFees;
  private String isPmIncursAch;
  private String isPmIncursCc;
  private double nsfChkscanFee;

  public int getGatewayId() {
    return gatewayId;
  }

  public void setGatewayId(int gatewayId) {
    this.gatewayId = gatewayId;
  }

  public double getAchFee() {
    return achFee;
  }

  public void setAchFee(double achFee) {
    this.achFee = achFee;
  }

  public double getAchChkscanFee() {
    return achChkscanFee;
  }

  public void setAchChkscanFee(double achChkscanFee) {
    this.achChkscanFee = achChkscanFee;
  }

  public double getMcFee() {
    return mcFee;
  }

  public void setMcFee(double mcFee) {
    this.mcFee = mcFee;
  }

  public double getAmexFee() {
    return amexFee;
  }

  public void setAmexFee(double amexFee) {
    this.amexFee = amexFee;
  }

  public double getCcFeeSurcharge() {
    return ccFeeSurcharge;
  }

  public void setCcFeeSurcharge(double ccFeeSurcharge) {
    this.ccFeeSurcharge = ccFeeSurcharge;
  }

  public double getVisaFlatFee() {
    return visaFlatFee;
  }

  public void setVisaFlatFee(double visaFlatFee) {
    this.visaFlatFee = visaFlatFee;
  }

  public String getIsPayleaseManagedFees() {
    return isPayleaseManagedFees;
  }

  public void setIsPayleaseManagedFees(String isPayleaseManagedFees) {
    this.isPayleaseManagedFees = isPayleaseManagedFees;
  }

  public String getIsPmIncursAch() {
    return isPmIncursAch;
  }

  public void setIsPmIncursAch(String isPmIncursAch) {
    this.isPmIncursAch = isPmIncursAch;
  }

  public String getIsPmIncursCc() {
    return isPmIncursCc;
  }

  public void setIsPmIncursCc(String isPmIncursCc) {
    this.isPmIncursCc = isPmIncursCc;
  }

  public double getNsfChkscanFee() {
    return nsfChkscanFee;
  }

  public void setNsfChkscanFee(double nsfChkscanFee) {
    this.nsfChkscanFee = nsfChkscanFee;
  }
}
