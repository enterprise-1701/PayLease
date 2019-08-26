package com.paylease.app.qa.framework.api.aapi;

import com.paylease.app.qa.framework.api.Element;
import com.paylease.app.qa.framework.api.ParameterCollection;
import java.util.ArrayList;

public class SetInvoicesTransaction extends AapiTransaction {

  private static final String ACTION_NAME = "SetInvoices";

  public static final String SPECIFIC_PATH = "Invoices/Invoice";

  public static final String INVOICES = "Invoices";
  public static final String INVOICE = "Invoice";

  public static final String INVOICE_ID = "InvoiceID";
  public static final String RESIDENT_ID = "ResidentID";
  public static final String INVOICE_DATE = "InvoiceDate";
  public static final String DUE_DATE = "DueDate";
  public static final String INCUR_FEE = "IncurFee";
  public static final String UNIT_TYPE = "UnitType";
  public static final String PREPARED_BY = "PreparedBy";
  public static final String COMMENTS = "Comments";

  public static final String LINE_ITEMS = "LineItems";
  public static final String LINE_ITEM = "LineItem";

  public static final String DESCRIPTION = "Description";
  public static final String PRORATED_AMOUNT = "ProratedAmount";
  public static final String CHARGE_DAYS = "ChargeDays";
  public static final String AMOUNT = "Amount";
  public static final String TYPE = "Type";
  public static final String CHARGE_START_DATE = "ChargeStartDate";
  public static final String CHARGE_END_DATE = "ChargeEndDate";

  public static final String PAYMENT_TYPE = "PaymentType";
  public static final String PAYMENT_DATE = "PaymentDate";

  /**
   * Creates a SetInvoices transaction with the given element values.
   *
   * @param parameters Values for transaction-specific elements
   */
  public SetInvoicesTransaction(ParameterCollection parameters) {
    super(parameters);

    setRootActionAttribute(ACTION_NAME);
  }

  /**
   * Gets an array list of elements to be added.
   *
   */
  public ArrayList<Element> getElements() {

    ArrayList<Element> elements = super.getElements();

    ArrayList<String> invoiceElementNames = new ArrayList<>();
    invoiceElementNames.add(INVOICE_ID);
    invoiceElementNames.add(RESIDENT_ID);
    invoiceElementNames.add(INVOICE_DATE);
    invoiceElementNames.add(DUE_DATE);
    invoiceElementNames.add(INCUR_FEE);
    invoiceElementNames.add(UNIT_TYPE);
    invoiceElementNames.add(PREPARED_BY);
    invoiceElementNames.add(COMMENTS);

    ArrayList<String> lineItemElementNames = new ArrayList<>();
    lineItemElementNames.add(AMOUNT);
    lineItemElementNames.add(TYPE);
    lineItemElementNames.add(DESCRIPTION);
    lineItemElementNames.add(CHARGE_DAYS);
    lineItemElementNames.add(PRORATED_AMOUNT);
    lineItemElementNames.add(CHARGE_START_DATE);
    lineItemElementNames.add(CHARGE_END_DATE);
    lineItemElementNames.add(PAYMENT_TYPE);
    lineItemElementNames.add(PAYMENT_DATE);

    Element invoicesElement = new Element(INVOICES);
    Element invoiceElement = new Element(INVOICE);

    ParameterCollection invoiceParameters = parameters.get(INVOICE).getParameters();
    setSubElements(invoiceElement, invoiceElementNames, invoiceParameters);

    Element lineItemsElement = new Element(LINE_ITEMS);

    ParameterCollection lineItemsParameters = invoiceParameters.get(LINE_ITEMS).getParameters();

    for (int i = 0; i < lineItemsParameters.getParamCount(LINE_ITEM); i++) {
      Element lineItemElement = new Element(LINE_ITEM);
      ParameterCollection lineItemParameters = lineItemsParameters.get(LINE_ITEM, i)
          .getParameters();

      setSubElements(lineItemElement, lineItemElementNames, lineItemParameters);
      lineItemsElement.addSubElement(lineItemElement);
    }

    invoiceElement.addSubElement(lineItemsElement);

    invoicesElement.addSubElement(invoiceElement);

    elements.add(invoicesElement);
    return elements;
  }
}