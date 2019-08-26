package com.paylease.app.qa.framework.newApi;

import com.paylease.app.qa.framework.DataHelper;
import com.paylease.app.qa.framework.Logger;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil;
import com.paylease.app.qa.framework.utility.restclienttool.RestClientUtil.ResponseFormat;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Response {

  public static final String RESPONSE_CODE_1 = "1";
  public static final String RESPONSE_CODE_10 = "10";
  public static final String RESPONSE_CODE_12 = "12";
  public static final String RESPONSE_CODE_52 = "52";
  public static final String RESPONSE_CODE_103 = "103";
  public static final String RESPONSE_CODE_111 = "111";
  public static final String RESPONSE_CODE_126 = "126";
  public static final String RESPONSE_CODE_127 = "127";
  public static final String RESPONSE_CODE_128 = "128";
  public static final String RESPONSE_CODE_129 = "129";
  public static final String RESPONSE_CODE_130 = "130";
  public static final String RESPONSE_CODE_131 = "131";
  public static final String RESPONSE_CODE_132 = "132";
  public static final String RESPONSE_CODE_140 = "140";
  public static final String RESPONSE_CODE_142 = "142";
  public static final String RESPONSE_CODE_152 = "152";
  public static final String RESPONSE_CODE_159 = "159";
  public static final String RESPONSE_CODE_193 = "193";
  public static final String RESPONSE_CODE_266 = "266";
  public static final String RESPONSE_CODE_333 = "333";

  public static final String RESPONSE_STATUS_ERROR = "Error";
  public static final String RESPONSE_STATUS_APPROVED = "Approved";
  public static final String RESPONSE_STATUS_API_APPROVED = "The request completed successfully.";
  public static final String RESPONSE_STATUS_API_CHARGE_CODE_ERROR = "The charge code provided is "
      + "not supported";
  public static final String RESPONSE_STATUS_API_CHARGE_CODE_NULL = "Charge code is required when "
      + "defining the security deposit ledger";
  public static final String RESPONSE_STATUS_API_CHARGE_CODE_UNNECESARRY = "Charge code is not "
      + "supported with this Integration/Ledger Type";
  public static final String RESPONSE_STATUS_API_LEDGER_CODE_ERROR = "The ledger type provided is "
      + "not supported";
  public static final String RESPONSE_STATUS_API_LEDGER_CODE_EMPTY = "Empty ledger type is not "
      + "supported";

  public static final HashMap<String, String> RESPONSE_CODE_STATUS_MAP;

  static {
    RESPONSE_CODE_STATUS_MAP = new HashMap<>();
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_1, RESPONSE_STATUS_API_APPROVED);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_10, RESPONSE_STATUS_APPROVED);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_12, RESPONSE_STATUS_APPROVED);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_52, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_103, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_111, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_126, RESPONSE_STATUS_API_LEDGER_CODE_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_127, RESPONSE_STATUS_API_CHARGE_CODE_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_128, RESPONSE_STATUS_API_CHARGE_CODE_NULL);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_129, RESPONSE_STATUS_API_CHARGE_CODE_UNNECESARRY);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_130, RESPONSE_STATUS_API_LEDGER_CODE_EMPTY);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_131, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_132, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_140, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_142, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_152, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_159, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_193, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_266, RESPONSE_STATUS_ERROR);
    RESPONSE_CODE_STATUS_MAP.put(RESPONSE_CODE_333, RESPONSE_STATUS_APPROVED);

  }

  /**
   * Validates an element from GAPI Response.
   *
   * @param restClientUtil RestClientUtil
   * @param httpResponse Response from GAPI
   * @param elementTagName Tag name that needs to be validated
   * @param elementExpectedValue Expected value of element
   * @param expectedCode Expected GAPI response code
   * @param expectedStatus Expected GAPI response code
   * @param doubleEncode Some responses encode the response differently
   */
  public void validatePartOfGapiResponse(RestClientUtil restClientUtil, HttpResponse httpResponse,
      String elementTagName, String elementExpectedValue, String expectedCode,
      String expectedStatus, boolean doubleEncode) {
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.XML);
    String responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    Document responseXmlDocumentObject = (Document) restClientUtil.getResponseBodyObject();
    NodeList rootNodeList = responseXmlDocumentObject
        .getElementsByTagName("PayLeaseGatewayResponse");
    for (int i = 0; i < rootNodeList.getLength(); i++) {
      Node rootNode = rootNodeList.item(i);
      if ((rootNode).getNodeType() == Node.ELEMENT_NODE) {
        Element elementRoot = (Element) rootNode;

        NodeList transactionNodeList = elementRoot.getElementsByTagName("Transactions");
        for (int j = 0; j < transactionNodeList.getLength(); j++) {
          Node transactionsNode = rootNodeList.item(j);
          if (transactionsNode.getNodeType() == Node.ELEMENT_NODE) {
            Element elementTransactions = (Element) transactionsNode;
            Element elementTransaction = (Element) (elementTransactions
                .getElementsByTagName("Transaction").item(0));

            Element elementCode = (Element) (elementTransaction.getElementsByTagName("Code")
                .item(0));
            Element elementStatus = (Element) (elementTransaction.getElementsByTagName("Status")
                .item(0));
            Element elementTag = (Element) (elementTransaction.getElementsByTagName(elementTagName)
                .item(0));
            String responseElementTagText = elementTag.getTextContent();
            if (doubleEncode) {
              DataHelper dataHelper = new DataHelper();
              try {
                responseElementTagText = dataHelper.doubleEncoder(responseElementTagText)
                    .toLowerCase();
              } catch (UnsupportedEncodingException e) {
                Logger.error(e.getMessage());
              }
            }

            Assert.assertEquals(responseElementTagText, elementExpectedValue);
            Assert.assertEquals(elementCode.getTextContent(), expectedCode);
            Assert.assertEquals(elementStatus.getTextContent(), expectedStatus);
          }
        }
      }
    }
  }

  /**
   * Validate AAPI response.
   *
   * @param restClientUtil RestClientUtil
   * @param httpResponse Response from AAPI
   * @param expectedCode Expected AAPI response code
   * @param expectedMessage Expected AAPI response message
   * @param expectedPayee1 Expected AAPI first Payee
   * @param expectedPayee2 Expected AAPI second Payee
   */
  public void validateAddPropertyAapiResponse(RestClientUtil restClientUtil,
      HttpResponse httpResponse,
      String expectedCode, String expectedMessage, Payee expectedPayee1, Payee expectedPayee2) {
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.XML);
    String responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    SoftAssert softAssert = new SoftAssert();

    Document responseXmlDocumentObject = (Document) restClientUtil.getResponseBodyObject();
    NodeList rootNodeList = responseXmlDocumentObject.getElementsByTagName("PayLeaseResponse");
    Node rootNode = rootNodeList.item(0);
    if ((rootNode).getNodeType() == Node.ELEMENT_NODE) {
      Element elementRoot = (Element) rootNode;
      NodeList payeeNodeList = elementRoot.getElementsByTagName("Payees");
      Payee[] expectedPayeeArray = new Payee[]{expectedPayee1, expectedPayee2};
      String elementCode = elementRoot.getElementsByTagName("Code").item(0).getTextContent();
      String elementMessage = elementRoot.getElementsByTagName("Message").item(0).getTextContent();

      softAssert.assertEquals(elementCode, expectedCode, "Did not receive expected code.");
      softAssert.assertEquals(elementMessage, expectedMessage, "Did not receive expected message.");

      for (int i = 0; i < payeeNodeList.getLength(); i++) {
        Node payeesNode = rootNodeList.item(i);
        if (payeesNode.getNodeType() != Node.ELEMENT_NODE) {
          softAssert.fail("Unexpected node type: " + payeesNode.getNodeType());
          continue;
        }
        Element payees = (Element) payeesNode;
        Element payee = (Element) (payees
            .getElementsByTagName("Payee").item(0));

        NodeList fieldNameList = payee.getElementsByTagName("FieldName");
        NodeList varNameList = payee.getElementsByTagName("VarName");
        Element elementFieldName;
        Element elementVarName;

        if (fieldNameList.getLength() > 0) {
          elementFieldName = (Element) fieldNameList.item(0);
          softAssert.assertEquals(elementFieldName.getTextContent(),
              expectedPayeeArray[i].getPayeeIdFieldName());
        } else {
          softAssert.assertTrue(fieldNameList.getLength() == 0);
        }

        if (varNameList.getLength() > 0) {
          elementVarName = (Element) varNameList.item(0);
          softAssert.assertEquals(elementVarName.getTextContent(),
              expectedPayeeArray[i].getPayeeIdVarName());
        } else {
          softAssert.assertTrue(varNameList.getLength() == 0);
        }

        NodeList ledgerTypeList = payee.getElementsByTagName("LedgerType");
        NodeList chargeCodeList = payee.getElementsByTagName("ChargeCode");
        Element elementLedgerType;
        Element elementChargeCode;
        if (ledgerTypeList.getLength() > 0) {
          elementLedgerType = (Element) ledgerTypeList.item(0);
          softAssert.assertEquals(elementLedgerType.getTextContent(),
              expectedPayeeArray[i].getPayeeIdLedgerType());

          if (chargeCodeList.getLength() > 0) {
            elementChargeCode = (Element) chargeCodeList.item(0);
            softAssert.assertEquals(elementChargeCode.getTextContent(),
                expectedPayeeArray[i].getPayeeIdChargeCode());
          }
        } else {
          softAssert.assertTrue(ledgerTypeList.getLength() == 0);
          softAssert.assertTrue(chargeCodeList.getLength() == 0);
        }

        Element elementBankAcct = (Element) (payee.getElementsByTagName("BankAccountNumber")
            .item(0));

        softAssert.assertEquals(elementBankAcct.getTextContent(),
            expectedPayeeArray[i].getBankAccountNumber());
      }
    }
    softAssert.assertAll();
  }

  public void validateSetPropertyCustomizationAapiResponse(RestClientUtil restClientUtil,
      HttpResponse httpResponse, String expectedSuccess, CodeMessage[] errorsArray,
      Customizations expectedCustomization1, Customizations expectedCustomization2,
      String expectedProperty1Success, String expectedProperty2Success, String testVariationNum) {
    restClientUtil.parseResponseBody(httpResponse, ResponseFormat.XML);
    String responseString = restClientUtil.getResponseString();
    Logger.info(responseString);

    Customizations[] expectedCustomizationsArray = new Customizations[]{expectedCustomization1,
        expectedCustomization2};
    String[] expectedPropertySuccessArray = new String[]{expectedProperty1Success,
        expectedProperty2Success};

    SoftAssert softAssert = new SoftAssert();

    Document responseXmlDocumentObject = (Document) restClientUtil.getResponseBodyObject();
    NodeList rootNodeList = responseXmlDocumentObject.getElementsByTagName("PayLeaseResponse");
    Node rootNode = rootNodeList.item(0);
    if ((rootNode).getNodeType() == Node.ELEMENT_NODE) {
      Element elementRoot = (Element) rootNode;
      String elementSuccess = elementRoot.getElementsByTagName("Success").item(0).getTextContent();
      NodeList elementCode = elementRoot.getElementsByTagName("Code");
      NodeList elementMessage = elementRoot.getElementsByTagName("Message");

      for (int i = 0; i < errorsArray.length; i++) {

        softAssert.assertEquals(elementCode.item(i).getTextContent(), errorsArray[i].getCode(), testVariationNum + " Code does not have correct value");
        softAssert
            .assertEquals(elementMessage.item(i).getTextContent(), errorsArray[i].getMessage(), testVariationNum + " Message does not have correct value");
      }

      softAssert.assertEquals(elementSuccess, expectedSuccess, testVariationNum + "Did not receive expected Success");

      Element elementProperties = (Element) elementRoot.getElementsByTagName("Properties").item(0);
      NodeList properties = elementProperties.getChildNodes();
      for (int i = 0; i < properties.getLength(); i++) {
        Element elementProperty = (Element) properties.item(i);
        Customizations expectedCustomizations = expectedCustomizationsArray[i];

        String propertySuccess = elementProperty.getElementsByTagName("Success").item(0)
            .getTextContent();
        softAssert.assertEquals(propertySuccess, expectedPropertySuccessArray[i], testVariationNum + " Property success does not have correct value");

        Element elementCustomizations = (Element) elementProperty
            .getElementsByTagName("Customizations").item(0);
        NodeList customizations = elementCustomizations.getElementsByTagName("Customization");
        for (int j = 0; j < customizations.getLength(); j++) {
          Customization expectedCustomization = expectedCustomizations.getCustomizationList()
              .get(j);
          Element elementCustomization = (Element) customizations.item(j);
          String customizationName = elementCustomization.getElementsByTagName("Name").item(0)
              .getTextContent();
          String customizationValue = elementCustomization.getElementsByTagName("Value").item(0)
              .getTextContent();
          String customizationSuccess = elementCustomization.getElementsByTagName("Success").item(0)
              .getTextContent();

          softAssert.assertEquals(customizationName, expectedCustomization.getName(),
              testVariationNum + " Customization Name");
          softAssert.assertEquals(customizationValue, expectedCustomization.getValue(), testVariationNum + " Value");
          softAssert
              .assertEquals(customizationSuccess, expectedCustomization.getSuccess(), testVariationNum + " Success");
        }
      }

    }
    softAssert.assertAll();
  }
}
