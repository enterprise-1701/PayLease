package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class RdcTransactionCallbackTransaction extends AapiTransaction {
  private static final String ACTION_NAME = "RdcTransactionCallback";

  public static final String OUTPUT = "Output";
  public static final String BATCH = "Batch";
  public static final String USER_ID = "UserID";
  public static final String LOCATION_ID = "LocationID";
  public static final String DEPOSIT = "Deposit";
  public static final String DEPOSIT_TID = "DepositTID";
  public static final String DEPOSIT_AMOUNT = "DepositAmount";
  public static final String DEPOSIT_ACCOUNT = "DepositAccount";
  public static final String DEPOSIT_ABA = "DepositABA";
  public static final String SEQUENCE_NUMBER = "SequenceNumber";
  public static final String CHECK = "Check";
  public static final String CHECK_PROPERTY_ID = "CheckPropertyID";
  public static final String CHECK_TENANT_ID = "CheckTenantID";
  public static final String CHECK_RESIDENT_ID = "CheckResidentID";
  public static final String CHECK_FIRST_NAME = "CheckFirstName";
  public static final String CHECK_LAST_NAME = "CheckLastName";
  public static final String CHECK_AMOUNT = "CheckAmount";
  public static final String CHECK_ACCOUNT = "CheckAccount";
  public static final String CHECK_SERIAL = "CheckSerial";
  public static final String CHECK_ABA = "CheckABA";

  /**
   * Creates a RdcTransactionCallback transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public RdcTransactionCallbackTransaction(ParameterCollection parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);
  }

  @Override
  public ArrayList<Element> getElements() {
    ArrayList<Element> elements = super.getElements();

    ArrayList<String> batchElementNames = new ArrayList<>();
    batchElementNames.add(USER_ID);
    batchElementNames.add(LOCATION_ID);

    ArrayList<String> depositElementNames = new ArrayList<>();
    depositElementNames.add(DEPOSIT_TID);
    depositElementNames.add(DEPOSIT_AMOUNT);
    depositElementNames.add(DEPOSIT_ACCOUNT);
    depositElementNames.add(DEPOSIT_ABA);
    depositElementNames.add(SEQUENCE_NUMBER);

    ArrayList<String> checkElementNames = new ArrayList<>();
    checkElementNames.add(CHECK_PROPERTY_ID);
    checkElementNames.add(CHECK_TENANT_ID);
    checkElementNames.add(CHECK_RESIDENT_ID);
    checkElementNames.add(CHECK_FIRST_NAME);
    checkElementNames.add(CHECK_LAST_NAME);
    checkElementNames.add(CHECK_AMOUNT);
    checkElementNames.add(CHECK_ACCOUNT);
    checkElementNames.add(CHECK_SERIAL);
    checkElementNames.add(CHECK_ABA);
    checkElementNames.add(SEQUENCE_NUMBER);

    Element output = new Element(OUTPUT);
    ParameterCollection outputParams = parameters.get(OUTPUT).getParameters();

    for (int i = 0; i < outputParams.getParamCount(BATCH); i++) {
      Element batch = new Element(BATCH);
      ParameterCollection batchParams = outputParams.get(BATCH, i).getParameters();
      setSubElements(batch, batchElementNames, batchParams);

      Element deposit = new Element(DEPOSIT);
      setSubElements(deposit, depositElementNames, batchParams);

      batch.addSubElement(deposit);

      for (int j = 0; j < batchParams.getParamCount(CHECK); j++) {
        Element check = new Element(CHECK);
        ParameterCollection checkParams = batchParams.get(CHECK, j).getParameters();
        setSubElements(check, checkElementNames, checkParams);

        batch.addSubElement(check);
      }

      output.addSubElement(batch);
    }

    elements.add(output);

    return elements;
  }
}
