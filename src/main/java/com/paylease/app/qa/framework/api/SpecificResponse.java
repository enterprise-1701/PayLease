package com.paylease.app.qa.framework.api;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.Logger;
import javax.xml.xpath.XPathExpressionException;

public class SpecificResponse {

  protected XMLBuilder response;

  public SpecificResponse(XMLBuilder response) {
    this.response = response;
  }

  protected String getElementText(String path) {
    return getElementText(response, path);
  }

  protected String getElementText(XMLBuilder snippet, String xpath) {
    try {
      return snippet.xpathFind(xpath).getElement().getTextContent();
    } catch (XPathExpressionException e) {
      Logger.debug(xpath + " path not found in response");
      return "";
    }
  }
}
