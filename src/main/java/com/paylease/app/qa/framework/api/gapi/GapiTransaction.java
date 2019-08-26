package com.paylease.app.qa.framework.api.gapi;

import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Transaction;
import java.util.HashMap;

public abstract class GapiTransaction extends Transaction {

  private static final String TRANSACTION_ACTION = "TransactionAction";

  public GapiTransaction(HashMap<String, String> parameters) {
    super(parameters);
    addElementName(TRANSACTION_ACTION);
  }

  public GapiTransaction(ParameterCollection parameters)
  {
    super(parameters);
    addElementName(TRANSACTION_ACTION);
  }

  protected void setRootActionName(String name) {
    parameters.put(TRANSACTION_ACTION, name);
  }
}
