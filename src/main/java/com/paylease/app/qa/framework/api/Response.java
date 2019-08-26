package com.paylease.app.qa.framework.api;

import com.jamesmurty.utils.XMLBuilder;
import javax.xml.xpath.XPathExpressionException;

public abstract class Response {

  private static final String CODE = "Code";
  private static final String STATUS = "Status";
  private static final String MESSAGE = "Message";

  private XMLBuilder response;
  private String index;
  private String specificPath;

  private String xpathMainError = "//Error[1]";

  public Response(XMLBuilder response) {
    this.response = response;
  }

  public XMLBuilder getResponse() {
    return response;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public void setSpecificPath(String specificPath) {
    this.specificPath = specificPath;
  }

  public String getSpecificCode() throws XPathExpressionException {
    return getSpecificElementValue(CODE);
  }

  public String getSpecificStatus() throws XPathExpressionException {
    return getSpecificElementValue(STATUS);
  }

  public String getSpecificMessage() throws XPathExpressionException {
    return getSpecificElementValue(MESSAGE);
  }

  public String getErrorCode() throws XPathExpressionException {
    return getElementValue(CODE, xpathMainError);
  }

  public String getErrorStatus() throws XPathExpressionException {
    return getElementValue(STATUS, xpathMainError);
  }

  public String getErrorMessage() throws XPathExpressionException {
    return getElementValue(MESSAGE, xpathMainError);
  }

  public String getResponseCode() throws XPathExpressionException {
    return getResponseElementValue(CODE);
  }

  public String getResponseStatus() throws XPathExpressionException {
    return getResponseElementValue(STATUS);
  }

  public String getResponseMessage() throws XPathExpressionException {
    return getResponseElementValue(MESSAGE);
  }

  protected abstract String getActionElementName();

  protected abstract String getRootElementName();

  private String getElementValue(String element, String xpathString)
      throws XPathExpressionException {

    XMLBuilder snippet = response.xpathFind(xpathString);
    return snippet.xpathFind(element).getElement().getTextContent();
  }

  /**
   * Get the text for a given element within the response, as identified by the keyName and value.
   *
   * @param element Element Name
   * @return Text content
   * @throws XPathExpressionException If element not found in document
   */
  public String getSpecificElementValue(String element) throws XPathExpressionException {
    String xpathString = getSpecificPathString();
    return getElementValue(element, xpathString);
  }

  public XMLBuilder getSpecificResponseSnippet() throws XPathExpressionException {
    return response.xpathFind(getSpecificPathString());
  }

  /**
   * Searches for given xpath element.
   *
   * @param xpathString xpathString
   * @return boolean value
   */
  public boolean isElementPresent(String xpathString) {
    try {
      response.xpathFind(xpathString);
      return true;
    } catch (XPathExpressionException e) {
      return false;
    }
  }

  private String getResponseElementValue(String element) throws XPathExpressionException {
    String rootElement = getRootElementName();
    String xpathString = "//" + rootElement;
    return getElementValue(element, xpathString);
  }

  private String getSpecificPathString() {
    String actionElement = getActionElementName();
    String xpathString = "//" + actionElement;

    if (specificPath != null) {
      xpathString += "/" + specificPath;
    }

    xpathString += "[" + index + "]";
    return xpathString;
  }
}
