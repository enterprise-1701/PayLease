package com.paylease.app.qa.framework.api.gapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class AccountPaymentTransaction extends GapiTransaction {

  private static final String ACTION_NAME = "AccountPayment";

  public static final String PAYMENT_REFERENCE_ID = "PaymentReferenceId";
  public static final String PAYMENT_TRACE_ID = "PaymentTraceId";
  public static final String PAYER_REFERENCE_ID = "PayerReferenceId";
  public static final String PAYEE_ID = "PayeeId";
  public static final String GATEWAY_PAYER_ID = "GatewayPayerId";
  public static final String TOTAL_AMOUNT = "TotalAmount";
  public static final String FEE_AMOUNT = "FeeAmount";
  public static final String INCUR_FEE = "IncurFee";
  public static final String CHECK_SCANNED = "CheckScanned";
  public static final String CURRENCY_CODE = "CurrencyCode";
  public static final String AMOUNT = "Amount";
  public static final String DEPOSIT = "Deposit";
  public static final String SPLIT_DEPOSIT = "SplitDeposit";

  /**
   * Creates an AccountPayment transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public AccountPaymentTransaction(ParameterCollection parameters) {
    super(parameters);

    addElementName(PAYMENT_REFERENCE_ID);
    addElementName(PAYMENT_TRACE_ID);
    addElementName(PAYER_REFERENCE_ID);
    addElementName(PAYEE_ID);
    addElementName(GATEWAY_PAYER_ID);
    addElementName(TOTAL_AMOUNT);
    addElementName(FEE_AMOUNT);
    addElementName(INCUR_FEE);
    addElementName(CHECK_SCANNED);
    addElementName(CURRENCY_CODE);
    addElementName(SPLIT_DEPOSIT);

    setRootActionName(ACTION_NAME);
  }
  
  @Override
  public ArrayList<Element> getElements() {

    ArrayList<Element> elements = super.getElements();

    ArrayList<String> splitDepositElementNames = new ArrayList<>();
    splitDepositElementNames.add(DEPOSIT);
    Element splitDepositElement = new Element(SPLIT_DEPOSIT);

    ArrayList<String> depositElementNames = new ArrayList<>();
    depositElementNames.add(PAYEE_ID);
    depositElementNames.add(AMOUNT);

    ParameterCollection splitDepositParameters = parameters.get(SPLIT_DEPOSIT).getParameters();

    int depositCount = splitDepositParameters.getParamCount(DEPOSIT);
    if (depositCount > 0) {
      for (int i = 0; i < depositCount; i++) {
        Element depositElement = new Element(DEPOSIT);
        ParameterCollection depositParameters = splitDepositParameters.get(DEPOSIT, i)
            .getParameters();

        setSubElements(depositElement, depositElementNames, depositParameters);
        splitDepositElement.addSubElement(depositElement);
      }

      elements.add(splitDepositElement);
    }
    return elements;
  }
}
