package com.paylease.app.qa.framework.api.gapi;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.api.Response;

public class GapiResponse extends Response {

  private static final String ACTION_ELEMENT_NAME = "Transaction";
  private static final String ROOT_ELEMENT_NAME = "PayLeaseGatewayResponse";

  protected String getActionElementName() {
    return ACTION_ELEMENT_NAME;
  }

  @Override
  protected String getRootElementName() {
    return ROOT_ELEMENT_NAME;
  }

  GapiResponse(XMLBuilder response) {
    super(response);
  }
}
