package com.paylease.app.qa.api.tests.aapi.testcase;

import com.paylease.app.qa.api.tests.ExpectedResponse;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.api.Parameter;
import com.paylease.app.qa.framework.api.ParameterCollection;
import com.paylease.app.qa.framework.api.Request;
import com.paylease.app.qa.framework.api.Response;
import com.paylease.app.qa.framework.api.aapi.AapiTransaction;
import com.paylease.app.qa.framework.api.aapi.SetInvoicesTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.xpath.XPathExpressionException;

public class SetInvoices extends BasicTestCase {

  private static final String RESPONSE_CODE = "Code";
  private static final String RESPONSE_MESSAGE = "Message";

  private String invoiceId;
  private String residentId;
  private String invoiceDate;
  private String dueDate;
  private String incurFee;
  private String unitType;
  private String preparedBy;
  private String comments;

  private ArrayList<HashMap<String, String>> listItems;

  /**
   * Create a basic SetInvoices test case with given values, and nulls for other fields. By
   * itself, this will not create a valid request.
   *
   * @param summary Summary of this test case
   * @param expectedResponse response expected to be returned by AAPI
   */
  public SetInvoices(
      String summary, ExpectedResponse expectedResponse
  ) {
    this(summary, expectedResponse, null, null, null, null,
        null, null, null, null);
  }

  private SetInvoices(String summary, ExpectedResponse expectedResponse,
      String invoiceId, String residentId, String invoiceDate, String dueDate, String incurFee,
      String unitType, String preparedBy, String comments) {
    super(summary, expectedResponse);

    this.invoiceId = invoiceId;
    this.residentId = residentId;
    this.invoiceDate = invoiceDate;
    this.dueDate = dueDate;
    this.incurFee = incurFee;
    this.unitType = unitType;
    this.preparedBy = preparedBy;
    this.comments = comments;
    this.listItems = new ArrayList<>();
  }

  /**
   * InvoiceId setter.
   *
   * @param invoiceId invoiceId
   * @return invoiceId instance
   */
  public SetInvoices setInvoiceId(String invoiceId) {
    this.invoiceId = invoiceId;

    return this;
  }

  /**
   * ResidentId setter.
   *
   * @param residentId residentId
   * @return residentId instance
   */
  public SetInvoices setResidentId(String residentId) {
    this.residentId = residentId;

    return this;
  }

  /**
   * InvoiceDate setter.
   *
   * @param invoiceDate invoiceDate
   * @return invoiceDate instance
   */
  public SetInvoices setInvoiceDate(String invoiceDate) {
    this.invoiceDate = invoiceDate;

    return this;
  }

  /**
   * DueDate setter.
   *
   * @param dueDate dueDate
   * @return dueDate instance
   */
  public SetInvoices setDueDate(String dueDate) {
    this.dueDate = dueDate;

    return this;
  }

  /**
   * IncurFee setter.
   *
   * @param incurFee incurFee
   * @return incurFee instance
   */
  public SetInvoices setIncurFee(String incurFee) {
    this.incurFee = incurFee;

    return this;
  }

  /**
   * UnitType setter.
   *
   * @param unitType unitType
   * @return unitType instance
   */
  public SetInvoices setUnitType(String unitType) {
    this.unitType = unitType;

    return this;
  }

  /**
   * PreparedBy setter.
   *
   * @param preparedBy preparedBy
   * @return preparedBy instance
   */
  public SetInvoices setPreparedBy(String preparedBy) {
    this.preparedBy = preparedBy;

    return this;
  }

  /**
   * Comments setter.
   *
   * @param comments comments
   * @return comments instance
   */
  public SetInvoices setComments(String comments) {
    this.comments = comments;

    return this;
  }

  @Override
  public void addTransaction(Request apiRequest) {

    Parameter invoice = new Parameter();

    invoice.addParameter(SetInvoicesTransaction.INVOICE_ID, invoiceId);
    invoice.addParameter(SetInvoicesTransaction.RESIDENT_ID, residentId);
    invoice.addParameter(SetInvoicesTransaction.INVOICE_DATE, invoiceDate);
    invoice.addParameter(SetInvoicesTransaction.DUE_DATE, dueDate);
    invoice.addParameter(SetInvoicesTransaction.INCUR_FEE, incurFee);
    invoice.addParameter(SetInvoicesTransaction.UNIT_TYPE, unitType);
    invoice.addParameter(SetInvoicesTransaction.PREPARED_BY, preparedBy);
    invoice.addParameter(SetInvoicesTransaction.COMMENTS, comments);

    Parameter lineItems = new Parameter();

    for (HashMap<String, String> item : listItems) {
      Parameter lineItem = new Parameter();

      lineItem.addParameter(SetInvoicesTransaction.TYPE, item.get("type"));
      lineItem.addParameter(SetInvoicesTransaction.AMOUNT, item.get("amount"));
      lineItem.addParameter(SetInvoicesTransaction.DESCRIPTION, item.get("description"));
      lineItem.addParameter(SetInvoicesTransaction.CHARGE_DAYS, item.get("chargeDays"));
      lineItem.addParameter(SetInvoicesTransaction.PRORATED_AMOUNT, item.get("proratedAmount"));
      lineItem.addParameter(SetInvoicesTransaction.CHARGE_START_DATE, item.get("chargeStartDate"));
      lineItem.addParameter(SetInvoicesTransaction.CHARGE_END_DATE, item.get("chargeEndDate"));
      lineItem.addParameter(SetInvoicesTransaction.PAYMENT_TYPE, item.get("paymentType"));
      lineItem.addParameter(SetInvoicesTransaction.PAYMENT_DATE, item.get("paymentDate"));

      lineItems.addParameter(SetInvoicesTransaction.LINE_ITEM, lineItem);
    }

    invoice.addParameter(SetInvoicesTransaction.LINE_ITEMS, lineItems);

    ParameterCollection parameters = getTransactionParameterCollection();
    parameters.put(SetInvoicesTransaction.INVOICE, invoice);

    AapiTransaction transaction = new SetInvoicesTransaction(parameters);
    apiRequest.addTransaction(transaction);
  }

  @Override
  public boolean test(Response response) {
    response.setIndex(String.valueOf(getIndex()));
    response.setSpecificPath(SetInvoicesTransaction.SPECIFIC_PATH);

    try {

      String code = response.getSpecificElementValue(RESPONSE_CODE);
      String message = response.getSpecificElementValue(RESPONSE_MESSAGE);

      String expectedCode = expectedResponse.getCode();
      String expectedMessage = expectedResponse.getMessage();

      boolean resultCode = expectedCode.equals(code);
      boolean resultMessage = expectedMessage.equals(message);

      boolean result = resultCode && resultMessage;
      if (!result) {
        Logger.error(summary);
        if (!resultCode) {
          Logger.error("Expected code " + expectedCode + ", found " + code);
        }
        if (!resultMessage) {
          Logger.error("Expected message " + expectedMessage + ", found " + message);
        }
      }

      return result;

    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Function to add Line item elements to ListItems.
   *
   * @param description description
   * @param proratedAmount proratedAmount
   * @param chargeDays chargeDays
   * @param amount amount
   * @param type type
   * @param chargeStartDate chargeStartDate
   * @param chargeEndDate chargeEndDate
   * @param paymentType paymentType
   * @param paymentDate paymentDate
   * @return a Hashmap of list items
   */
  public SetInvoices addLineItem(String description, String proratedAmount, String chargeDays,
      String amount, String type, String chargeStartDate, String chargeEndDate,
      String paymentType, String paymentDate) {

    HashMap<String, String> lineItemValues = new HashMap<>();
    lineItemValues.put("description", description);
    lineItemValues.put("proratedAmount", proratedAmount);
    lineItemValues.put("chargeDays", chargeDays);
    lineItemValues.put("amount", amount);
    lineItemValues.put("type", type);
    lineItemValues.put("chargeStartDate", chargeStartDate);
    lineItemValues.put("chargeEndDate", chargeEndDate);
    lineItemValues.put("paymentType", paymentType);
    lineItemValues.put("paymentDate", paymentDate);

    listItems.add(lineItemValues);

    return this;
  }

}