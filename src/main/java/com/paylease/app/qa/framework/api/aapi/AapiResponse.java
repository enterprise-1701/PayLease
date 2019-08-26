package com.paylease.app.qa.framework.api.aapi;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.api.Response;

public class AapiResponse extends Response {
  private static final String ROOT_ELEMENT_NAME = "PayLeaseResponse";
  private static final String ACTION_ELEMENT_NAME = "Action";

  @Override
  protected String getActionElementName() {
    return ACTION_ELEMENT_NAME;
  }

  @Override
  protected String getRootElementName() {
    return ROOT_ELEMENT_NAME;
  }

  AapiResponse(XMLBuilder response) {
    super(response);
  }
}
