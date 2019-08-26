package com.paylease.app.qa.framework.utility.database.client.dto;

import com.paylease.app.qa.framework.Logger;
import java.lang.reflect.Field;

public class PmFeeTier {

  private int pmId;
  private int propId;
  private int paymentTypeId;
  private int paymentMethodId;
  private String varName;
  private int tierAmount;
  private String feeType;
  private int feeValue;
  private String feeIncur;

  public int getPmId() {
    return pmId;
  }

  public void setPmId(int pmId) {
    this.pmId = pmId;
  }

  public int getPropId() {
    return propId;
  }

  public void setPropId(int propId) {
    this.propId = propId;
  }

  public int getPaymentTypeId() {
    return paymentTypeId;
  }

  public void setPaymentTypeId(int paymentTypeId) {
    this.paymentTypeId = paymentTypeId;
  }

  public int getPaymentMethodId() {
    return paymentMethodId;
  }

  public void setPaymentMethodId(int paymentMethodId) {
    this.paymentMethodId = paymentMethodId;
  }

  public String getVarName() {
    return varName;
  }

  public void setVarName(String varName) {
    this.varName = varName;
  }

  public int getTierAmount() {
    return tierAmount;
  }

  public void setTierAmount(int tierAmount) {
    this.tierAmount = tierAmount;
  }

  public String getFeeType() {
    return feeType;
  }

  public void setFeeType(String feeType) {
    this.feeType = feeType;
  }

  public int getFeeValue() {
    return feeValue;
  }

  public void setFeeValue(int feeValue) {
    this.feeValue = feeValue;
  }

  public String getFeeIncur() {
    return feeIncur;
  }

  public void setFeeIncur(String feeIncur) {
    this.feeIncur = feeIncur;
  }

  /**
   * Return a string representation of the PmFeeTier.
   *
   * @return String
   */
  public String toString() {
    Field[] fields = PmFeeTier.class.getDeclaredFields();

    StringBuilder convertedString = new StringBuilder("|");

    for (Field field : fields) {
      try {
        String fieldValue = (null != field.get(this)) ? field.get(this).toString() : "null";
        convertedString.append(field.getName()).append("=").append(fieldValue).append("|");
      } catch (IllegalAccessException e) {
        Logger.error(e.getMessage());
      }
    }

    return convertedString.toString();
  }
}
