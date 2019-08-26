package com.paylease.app.qa.framework.api.aapi;

import com.jamesmurty.utils.XMLBuilder;
import com.paylease.app.qa.framework.api.Response;

public class RdcResponse extends Response {
  private static final String ROOT_ELEMENT_NAME = "PayLeaseRDCResponse";
  private static final String ACTION_ELEMENT_NAME = "Action";

  @Override
  protected String getActionElementName() {
    return ACTION_ELEMENT_NAME;
  }

  @Override
  protected String getRootElementName() {
    return ROOT_ELEMENT_NAME;
  }

  public RdcResponse(XMLBuilder response) {
    super(response);
  }
}
