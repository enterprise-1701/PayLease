package com.paylease.app.qa.framework.api;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public abstract class Request {

  protected Credentials credentials;
  protected String mode;

  protected XMLBuilder request;

  public Request(Credentials credentials, String mode) {
    this.credentials = credentials;
    this.mode = mode;
  }

  public abstract Response sendRequest() throws Exception;

  protected abstract String getActionRoot();

  /**
   * Add a new transaction to the request.
   *
   * @param transaction Transaction to be added
   */
  public void addTransaction(Transaction transaction) {
    request = request.e(getActionRoot());

    HashMap<String, String> actionAttributes = transaction.getActionAttributes();
    for (String attribute : actionAttributes.keySet()) {
      request = request.a(attribute, actionAttributes.get(attribute));
    }

    addElements(transaction.getElements());
    request = request.up();
  }

  private void addElements(ArrayList<Element> elements) {
    for (Element element : elements) {
      // If the element value is a simple string, do this that we've always done
      request = request.e(element.getName());
      if (!element.getValue().isEmpty()) {
        request = request.t(element.getValue());
      }

      addElements(element.getSubElements());
      request = request.up();
    }
  }

  protected String getRequestString() throws Exception {
    Properties outputProperties = new Properties();
    outputProperties.put(javax.xml.transform.OutputKeys.INDENT, "yes");
    outputProperties.put("{http://xml.apache.org/xslt}indent-amount", "2");

    String xmlString;

    try {
      xmlString = request.asString(outputProperties);
      Logger.debug(xmlString);
    } catch (Exception e) {
      throw e;
    }
    return xmlString;
  }

  protected String submitRequest(String url, String content) throws Exception {
    PostRequest postRequest = new PostRequest();

    String response = postRequest.submit(url, content);

    Logger.debug("Response string is: " + response);

    return response;
  }
}
