package com.paylease.app.qa.framework.utility.database.client.dto;

public class CustomizationsPropertyDTO
{

  public static final int DISALLOW_PRE_PAYMENTS = 284;
  public static final int PAYMENT_METHOD_ACH = 64;
  public static final int FIXED_AMOUNT = 193;
  public static final int CASH_PAY = 42;

  private int id;
  private int propId;
  private int customizationId;
  private String overrideValue;


  //******************
  //Getter Methods
  //******************


  public int getId()
  {
    return id;
  }
  public int getPropId()
  {
    return propId;
  }
  public int getCustomizationId()
  {
    return customizationId;
  }
  public String getOverrideValue()
  {
    return overrideValue;
  }


  //******************
  //Setter Methods
  //******************


  public void setId(int id)
  {
    this.id = id;
  }
  public void setPropId(int propId)
  {
    this.propId = propId;
  }
  public void setCustomizationId(int customizationId)
  {
    this.customizationId = customizationId;
  }
  public void setOverrideValue(String overrideValue)
  {
    this.overrideValue = overrideValue;
  }

}